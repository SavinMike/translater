package com.arellomobile.translater.main;

import com.arellomobile.translater.PropertiesReader;
import com.arellomobile.translater.TranslaterToCsv;
import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.model.PlatformVariants;
import com.arellomobile.translater.converter.AndroidWriteConverter;
import com.arellomobile.translater.converter.IosWriteConverter;
import com.arellomobile.translater.converter.WebWriteConverter;
import com.arellomobile.translater.path.PathHelper;
import com.arellomobile.translater.path.PlatformsPath;
import com.arellomobile.translater.reader.RuleReaderList;
import com.arellomobile.translater.reader.android.AndroidResourcesReader;
import com.arellomobile.translater.reader.android.AndroidString;
import com.arellomobile.translater.reader.ios.IosResourcesReader;
import com.arellomobile.translater.reader.ios.IosString;
import com.arellomobile.translater.reader.web.WebPoReader;
import com.arellomobile.translater.reader.web.WebString;
import com.arellomobile.translater.writer.LocationHistory;

import java.util.List;
import java.util.Map;

/**
 * Date: 19.10.2015
 * Time: 9:56
 *
 * @author Savin Mikhail
 */
public class MainTranslaterToCsv
{

	public static void main(String[] args)
	{
		Config config = new PropertiesReader().readFile(args[0]);

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
