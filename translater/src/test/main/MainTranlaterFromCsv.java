package test.main;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

import test.PropertiesReader;
import test.TranlaterFromCsv;
import test.exception.IncorrectLineException;
import test.model.Config;
import test.model.TranslateItem;
import test.parser.CsvFilenameInfo;
import test.parser.CsvFilenameParserFactory;
import test.path.PathHelper;
import test.path.PlatformsPath;
import test.reader.CSVReader;
import test.reader.CsvLineConverterReader;
import test.reader.ReaderRules;
import test.writer.LocationHistory;

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
