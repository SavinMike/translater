package test.xponia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.TranslaterToCsv;
import test.model.PlatformVariants;
import test.model.converter.AndroidWriteConverter;
import test.model.converter.IosWriteConverter;
import test.model.converter.WebWriteConverter;
import test.path.AndroidLocationPathFinder;
import test.path.LocationPathFinder;
import test.path.LocationPathFinderFactory;
import test.path.PlatformsPath;
import test.reader.XMLReader;
import test.reader.ios.IosResourcesReader;
import test.reader.web.WebPoReader;
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
		Map<PlatformVariants, String> map = new HashMap<>();
		map.put(PlatformVariants.WEB, "/home/arello/Work/Xponia/Web/");
		map.put(PlatformVariants.ANDROID, "/home/arello/Work/Xponia/android/Xponia/app/");
		map.put(PlatformVariants.IOS, "/home/arello/Work/Xponia/ios/Xponia/");

		for(Map.Entry<PlatformVariants, String> entry: map.entrySet())
		{
			LocationPathFinder locationPathFinder = LocationPathFinderFactory.create(entry.getKey(), entry.getValue());

			if(locationPathFinder instanceof AndroidLocationPathFinder)
			{
				((AndroidLocationPathFinder)locationPathFinder).setIncludes("strings.xml", "service.xml");
			}

			List<PlatformsPath> locations = locationPathFinder.getLocations();

			TranslaterToCsv<PlatformsPath> translaterToCsv = new TranslaterToCsv<>(locations, "Xponia/");
			translaterToCsv.setHistory(new LocationHistory(locations));
			switch (entry.getKey())
			{
				case ANDROID:
					translaterToCsv.readAndWriteToCsv(new XMLReader(), new AndroidWriteConverter(), entry.getKey());
					break;
				case IOS:
					translaterToCsv.readAndWriteToCsv(new IosResourcesReader(), new IosWriteConverter(), entry.getKey());
					break;
				case WEB:
					translaterToCsv.readAndWriteToCsv(new WebPoReader(), new WebWriteConverter(), entry.getKey());
					break;
			}
		}
	}
}
