package test.xponia;

import java.util.List;
import java.util.Map;

import test.PropertiesReader;
import test.TranslaterToCsv;
import test.model.Config;
import test.model.PlatformVariants;
import test.model.converter.AndroidWriteConverter;
import test.model.converter.IosWriteConverter;
import test.model.converter.WebWriteConverter;
import test.path.PathHelper;
import test.path.PlatformsPath;
import test.reader.RuleReaderList;
import test.reader.XMLReader;
import test.reader.ios.IosResourcesReader;
import test.reader.ios.IosString;
import test.reader.web.WebPoReader;
import test.reader.web.WebString;
import test.writer.LocationHistory;

/**
 * Date: 19.10.2015
 * Time: 9:56
 *
 * @author Savin Mikhail
 */
public class XponiaTranslaterToCsv
{

	public static void main(String[] args)
	{
		Config config = new PropertiesReader().readFile("config.properties");

		if (config == null)
		{
			return;
		}

		PathHelper pathHelper = new PathHelper(config);

		Map<PlatformVariants, List<PlatformsPath>> locations = pathHelper.getLocations();

		for (Map.Entry<PlatformVariants, List<PlatformsPath>> entry : locations.entrySet())
		{
			TranslaterToCsv<PlatformsPath> translaterToCsv = new TranslaterToCsv<>(entry.getValue(), config.getCsvPath());
			translaterToCsv.setHistory(new LocationHistory(entry.getValue()));
			switch (entry.getKey())
			{
				case ANDROID:
					translaterToCsv.readAndWriteToCsv(new XMLReader(), new AndroidWriteConverter(), entry.getKey());
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
