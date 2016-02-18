package test.xponia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import test.PropertiesReader;
import test.TranlaterFromCsv;
import test.exception.IncorrectLineException;
import test.model.Config;
import test.model.PlatformVariants;
import test.model.TranslateItem;
import test.path.PathHelper;
import test.path.PlatformsPath;
import test.reader.CSVReader;
import test.reader.LineConverterReader;
import test.reader.ReaderRules;
import test.writer.LocationHistory;

/**
 * Date: 10.02.2016
 * Time: 18:28
 *
 * @author Savin Mikhail
 */
public class XponiaTranlaterFromCsv
{

	public static void main(String[] args)
	{
		Config config = new PropertiesReader().readFile("config_android.properties");
		if (config == null)
		{
			return;
		}

		PathHelper pathHelper = new PathHelper(config);

		Map<PlatformVariants, List<PlatformsPath>> locations = pathHelper.getLocations();

		for (final Map.Entry<PlatformVariants, List<PlatformsPath>> entry : locations.entrySet())
		{
			CSVReader<TranslateItem<PlatformsPath>, LocationHistory> csvReader = new CSVReader<TranslateItem<PlatformsPath>, LocationHistory>("\t")
			{
				@Override
				public TranslateItem<PlatformsPath> getByLine(final String[] strings, final LocationHistory history) throws IncorrectLineException
				{
					TranslateItem<PlatformsPath> translateItem = new TranslateItem<>(updateCsvString(strings[0]));
					int counter = 1;
					for (PlatformsPath platformsPath : entry.getValue())
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

			csvReader.setLineConverterReader(new LineConverterReader<LocationHistory>()
			{
				@Override
				public LocationHistory convertHistory(final String[] history)
				{
					List<PlatformsPath> result = new ArrayList<>();
					for (String s : history)
					{
						for (PlatformsPath platformsPath : entry.getValue())
						{
							if (platformsPath.name().equals(s))
							{
								result.add(platformsPath);
							}
						}
					}

					return new LocationHistory(result);
				}
			});

			TranlaterFromCsv<PlatformsPath, LocationHistory> tranlaterFromCsv = new TranlaterFromCsv<>(config.getCsvPath(), entry.getValue());
			tranlaterFromCsv.setCharsetMap(config.getFileCharset());
			tranlaterFromCsv.readFromCsv(csvReader, entry.getKey());
		}
	}
}
