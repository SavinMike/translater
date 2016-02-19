package com.arellomobile.translater.path;

import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.parser.AndroidCsvFilenameInfo;
import com.arellomobile.translater.parser.CsvFilenameInfo;

/**
 * Date: 18.02.2016
 * Time: 16:19
 *
 * @author Savin Mikhail
 */
public class PlatformsPathFactory
{
	public static PlatformPath createPlatformsPath(CsvFilenameInfo csvFilenameInfo, final Config config, String locationName)
	{
		PlatformPath platformsPath = null;
		switch (csvFilenameInfo.platformVariants)
		{
			case ANDROID:
				AndroidPlatformPath androidPlatformPath = new AndroidPlatformPath(locationName);
				androidPlatformPath.addFlavors(((AndroidCsvFilenameInfo) csvFilenameInfo).flavors);
				platformsPath = androidPlatformPath;
				platformsPath.setProjectPath(config.getPathMap().get(csvFilenameInfo.platformVariants) + "src/");
				break;
			case IOS:
				platformsPath = new IosPlatformPath(locationName);
				platformsPath.setProjectPath(config.getPathMap().get(csvFilenameInfo.platformVariants));
				break;
			case WEB:
				platformsPath = new WebPlatformPath(locationName);
				platformsPath.setProjectPath(config.getPathMap().get(csvFilenameInfo.platformVariants) + WebLocationPathFinder.LOCALE_PLACE);
				break;
		}

		platformsPath.setExcludes(config.getExcludesMap().get(csvFilenameInfo.platformVariants));
		platformsPath.setIncludes(config.getIncludesMap().get(csvFilenameInfo.platformVariants));
		platformsPath.setDefault(locationName.equals(config.getDefaultLocaleMap().get(csvFilenameInfo.platformVariants)));
		platformsPath.addFileNames(csvFilenameInfo.fileName);

		return platformsPath;
	}
}
