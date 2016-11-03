package com.arellomobile.translater.main;

import com.arellomobile.translater.PropertiesReader;
import com.arellomobile.translater.TranlaterFromCsv;
import com.arellomobile.translater.TranslaterToCsv;
import com.arellomobile.translater.converter.AndroidWriteConverter;
import com.arellomobile.translater.converter.IosWriteConverter;
import com.arellomobile.translater.converter.WebWriteConverter;
import com.arellomobile.translater.exception.IncorrectLineException;
import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.model.PlatformVariants;
import com.arellomobile.translater.model.TranslateItem;
import com.arellomobile.translater.parser.CsvFilenameInfo;
import com.arellomobile.translater.parser.CsvFilenameParserFactory;
import com.arellomobile.translater.path.PathHelper;
import com.arellomobile.translater.path.PlatformsPath;
import com.arellomobile.translater.reader.CSVReader;
import com.arellomobile.translater.reader.CsvLineConverterReader;
import com.arellomobile.translater.reader.ReaderRules;
import com.arellomobile.translater.reader.RuleReaderList;
import com.arellomobile.translater.reader.android.AndroidResourcesReader;
import com.arellomobile.translater.reader.android.AndroidString;
import com.arellomobile.translater.reader.ios.IosResourcesReader;
import com.arellomobile.translater.reader.ios.IosString;
import com.arellomobile.translater.reader.web.WebPoReader;
import com.arellomobile.translater.reader.web.WebString;
import com.arellomobile.translater.writer.LocationHistory;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Date: 19.02.2016
 * Time: 11:37
 *
 * @author Savin Mikhail
 */
public class Main
{
	public static void main(String[] args)
	{
		if(args == null || args.length < 2){
			throw new IllegalArgumentException("First arguments should be path to properties. Second arguments should be one of the FROM_CSV or TO_CSV");
		}
		final Config config = new PropertiesReader().readFile(args[0]);
		if (config == null)
		{
			return;
		}

		switch (args[1])
		{
			case "FROM_CSV":
				fromCsv(config);
				return;
			case "TO_CSV":
				toCsv(config);
				return;
		}

		throw new IllegalArgumentException("Second arguments should be one of the FROM_CSV or TO_CSV");
	}

	public static void fromCsv(Config config)
	{
		PathHelper pathHelper = new PathHelper(config);

		String[] list = pathHelper.getCsvFile().list();
		List<CsvFilenameInfo> csvFilenameInfoList = new ArrayList<>();
		for (String csvFileName : list)
		{
			String extension = FilenameUtils.getExtension(csvFileName);
			if (extension.equals("tsv") || extension.equals("csv"))
			{
				CsvFilenameInfo csvFilenameInfo = CsvFilenameParserFactory.getCsvFilenameInfo(config, csvFileName, pathHelper.getCsvFile());

				if (csvFilenameInfo != null)
				{
					csvFilenameInfoList.add(csvFilenameInfo);
				}
			}
		}

		CSVReader<TranslateItem<PlatformsPath>, LocationHistory> csvReader = new CSVReader<TranslateItem<PlatformsPath>, LocationHistory>("\t")
		{
			@Override
			public TranslateItem<PlatformsPath> getByLine(final String[] strings, final LocationHistory history) throws IncorrectLineException
			{
				TranslateItem<PlatformsPath> translateItem = new TranslateItem<>(updateCsvString(strings[0]));
				int counter = 1;
				for (PlatformsPath platformsPath : history.getEnumSet())
				{
					try
					{
						translateItem.addColumn(platformsPath, updateCsvString(strings[counter]));
					} catch (ArrayIndexOutOfBoundsException e)
					{
						System.out.println(platformsPath.name() + " ; " + platformsPath.getRootPath() + " ; " + counter);
					}
					counter++;
				}
				return translateItem;
			}

			private String updateCsvString(final String string)
			{
				if (string.startsWith("\""))
				{
					String s = string.replaceFirst("\"", "");
					int endIndex = s.lastIndexOf("\"");
					if(endIndex!=-1)
					{
						return s.substring(0, endIndex).replaceAll("\"\"", ReaderRules.QUOT);
					}
				} else {
					return string.replaceAll("\"", ReaderRules.QUOT);
				}

				return string;
			}
		};

		for (final CsvFilenameInfo csvFilenameInfo : csvFilenameInfoList)
		{
			CsvLineConverterReader lineConverterReader = new CsvLineConverterReader(csvFilenameInfo, config);
			csvReader.setLineConverterReader(lineConverterReader);

			List<TranslateItem<PlatformsPath>> translateItems = csvReader.readFile(csvFilenameInfo.csvName);
			TranlaterFromCsv<PlatformsPath> tranlaterFromCsv = new TranlaterFromCsv<>(lineConverterReader.getLocationHistory().getEnumSet());
			tranlaterFromCsv.setCharsetMap(config.getFileCharset());
			tranlaterFromCsv.updateTranslation(translateItems, csvFilenameInfo.platformVariants, config);
		}
	}

	public static void toCsv(Config config)
	{

		PathHelper pathHelper = new PathHelper(config);

		Map<PlatformVariants, List<PlatformsPath>> locations = pathHelper.getLocations();

		for (Map.Entry<PlatformVariants, List<PlatformsPath>> entry : locations.entrySet())
		{
			TranslaterToCsv<PlatformsPath> translaterToCsv = new TranslaterToCsv<>(entry.getValue(), config.getCsvPath());
			translaterToCsv.setHistory(new LocationHistory(entry.getValue()));
			switch (entry.getKey())
			{
				case ANDROID:
				{
					RuleReaderList<AndroidString> reader = new AndroidResourcesReader();
					reader.setCharsetMap(config.getFileCharset());
					translaterToCsv.readAndWriteToCsv(reader, new AndroidWriteConverter(), entry.getKey());
				}
				break;
				case IOS:
				{
					RuleReaderList<IosString> reader = new IosResourcesReader();
					reader.setCharsetMap(config.getFileCharset());
					translaterToCsv.readAndWriteToCsv(reader, new IosWriteConverter(), entry.getKey());
				}
				break;
				case WEB:
				{
					RuleReaderList<WebString> reader = new WebPoReader();
					reader.setCharsetMap(config.getFileCharset());
					translaterToCsv.readAndWriteToCsv(reader, new WebWriteConverter(), entry.getKey());
				}
				break;
			}
		}
	}
}
