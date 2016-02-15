package test.xponia;

import java.util.List;

import test.TranslaterToCsv;
import test.model.PlatformVariants;
import test.model.converter.AndroidWriteConverter;
import test.model.converter.IosWriteConverter;
import test.model.converter.WebWriteConverter;
import test.path.AndroidLocationPathFinder;
import test.path.IosLocationPathFinder;
import test.path.PlatformsPath;
import test.path.WebLocationPathFinder;
import test.reader.XMLReader;
import test.reader.ios.IosResourcesReader;
import test.reader.web.WebPoReader;
import test.writer.LocationHistory;

/**
 * Date: 15.02.2016
 * Time: 13:52
 *
 * @author Savin Mikhail
 */
public class PathChecker
{

	public static void main(String[] args)
	{
		WebLocationPathFinder webLocationPathFinder = new WebLocationPathFinder("/home/arello/Work/Xponia/Web/");
		List<PlatformsPath> locations = webLocationPathFinder.getLocations();

		TranslaterToCsv<PlatformsPath> translaterToCsv = new TranslaterToCsv<>(locations, "Xponia/");
		translaterToCsv.setHistory(new LocationHistory(locations));
		translaterToCsv.readAndWriteToCsv(new WebPoReader(), new WebWriteConverter(), PlatformVariants.WEB);

		IosLocationPathFinder iosLocationPathFinder = new IosLocationPathFinder("/home/arello/Work/Xponia/ios/Xponia/");
		locations = iosLocationPathFinder.getLocations();

		TranslaterToCsv<PlatformsPath> iosTranlater = new TranslaterToCsv<>(locations, "Xponia/");
		iosTranlater.setHistory(new LocationHistory(locations));
		iosTranlater.readAndWriteToCsv(new IosResourcesReader(), new IosWriteConverter(), PlatformVariants.IOS);

		AndroidLocationPathFinder androidLocationPathFinder = new AndroidLocationPathFinder("/home/arello/Work/Xponia/android/Xponia/app/");
		androidLocationPathFinder.setIncludes("strings.xml", "service.xml");
		locations = androidLocationPathFinder.getLocations();

		TranslaterToCsv<PlatformsPath> androidTranlater = new TranslaterToCsv<>(locations, "Xponia/");
		androidTranlater.setHistory(new LocationHistory(locations));
		androidTranlater.readAndWriteToCsv(new XMLReader(), new AndroidWriteConverter(), PlatformVariants.ANDROID);
	}
}
