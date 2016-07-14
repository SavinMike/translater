package com.arellomobile.translater.path;

import com.arellomobile.translater.model.PlatformVariants;

/**
 * Date: 15.02.2016
 * Time: 18:42
 *
 * @author Savin Mikhail
 */
public class LocationPathFinderFactory
{
	public static LocationPathFinder create(PlatformVariants platformVariants, String projectPath){
		LocationPathFinder locationPathFinder = null;
		switch (platformVariants){

			case ANDROID:
				locationPathFinder = new AndroidLocationPathFinder(projectPath);
				break;
			case IOS:
				locationPathFinder = new IosLocationPathFinder(projectPath);
				break;
			case WEB:
				locationPathFinder = new WebLocationPathFinder(projectPath);
				break;
		}

		return locationPathFinder;
	}
}
