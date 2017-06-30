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
public class IosLocationPathFinder extends BaseLocationPathFinder
{
	public static final String LPROJ = ".lproj";
	private String mProjectPath;

	public IosLocationPathFinder(final String projectPath)
	{
		mProjectPath = projectPath;
	}

	@Override
	public List<PlatformsPath> getLocations()
	{
		if(mProjectPath == null){
			return Collections.emptyList();
		}
		File localeDir = new File(mProjectPath);
		List<PlatformsPath> platformsPaths = new ArrayList<>();

		findAllLprojFiles(localeDir, platformsPaths);
		return platformsPaths;
	}

	private void findAllLprojFiles(final File localeDir, final List<PlatformsPath> platformsPaths)
	{
		if(localeDir.isDirectory())
		{
			String[] list = localeDir.list();
			if(list == null){
				return;
			}

			for (String fileName : list)
			{
				if (fileName.contains(LPROJ))
				{
					IosPlatformPath iosPlatformPath = new IosPlatformPath(fileName.substring(0, fileName.indexOf(LPROJ)));

					iosPlatformPath.setExcludes(getExcludes());
					iosPlatformPath.setIncludes(getIncludes());

					if (iosPlatformPath.generateFileNames(localeDir.getAbsolutePath()))
					{
						platformsPaths.add(iosPlatformPath);
					}
				}else {
					findAllLprojFiles(new File(localeDir,fileName), platformsPaths);
				}
			}
		}
	}
}
