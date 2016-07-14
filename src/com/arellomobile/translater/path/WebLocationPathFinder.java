package com.arellomobile.translater.path;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 15.02.2016
 * Time: 13:27
 *
 * @author Savin Mikhail
 */
public class WebLocationPathFinder extends BaseLocationPathFinder
{
	public static final String LOCALE_PLACE = "/src/locale/";
	private String mProjectPath;

	public WebLocationPathFinder(final String projectPath)
	{
		mProjectPath = projectPath;
	}

	@Override
	public List<PlatformsPath> getLocations()
	{
		if (mProjectPath == null)
		{
			return Collections.emptyList();
		}
		File localeDir = new File(mProjectPath + LOCALE_PLACE);
		List<PlatformsPath> platformsPaths = new ArrayList<>();

		for (String fileName : localeDir.list())
		{
			WebPlatformPath webPlatformPath = new WebPlatformPath(fileName);

			webPlatformPath.setDefault(fileName.equals(getDefault()));
			webPlatformPath.setExcludes(getExcludes());
			webPlatformPath.setIncludes(getIncludes());

			if (webPlatformPath.generateFileNames(localeDir.getAbsolutePath()))
			{
				platformsPaths.add(webPlatformPath);
			}
		}
		return platformsPaths;
	}
}
