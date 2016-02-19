package com.arellomobile.translater.main;

import com.arellomobile.translater.PropertiesReader;
import com.arellomobile.translater.TranlaterFromCsv;
import com.arellomobile.translater.exception.IncorrectLineException;
import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.model.TranslateItem;
import com.arellomobile.translater.parser.CsvFilenameInfo;
import com.arellomobile.translater.parser.CsvFilenameParserFactory;
import com.arellomobile.translater.path.PathHelper;
import com.arellomobile.translater.path.PlatformsPath;
import com.arellomobile.translater.reader.CSVReader;
import com.arellomobile.translater.reader.CsvLineConverterReader;
import com.arellomobile.translater.reader.ReaderRules;
import com.arellomobile.translater.writer.LocationHistory;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 10.02.2016
 * Time: 18:28
 *
 * @author Savin Mikhail
 */
public class MainTranlaterFromCsv
{

	public static void main(String[] args)
	{
		final Config config = new PropertiesReader().readFile(args[0]);
		if (config == null)
		{
			return;
		}

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
					return s.substring(0, s.lastIndexOf("\"")).replaceAll("\"\"", ReaderRules.QUOT);
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

}
