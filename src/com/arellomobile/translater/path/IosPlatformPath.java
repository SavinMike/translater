package com.arellomobile.translater.path;

import com.arellomobile.translater.model.PlatformVariants;

/**
 * Date: 15.02.2016
 * Time: 13:33
 *
 * @author Savin Mikhail
 */
public class IosPlatformPath extends PlatformPath
{


	public IosPlatformPath(final String localeName)
	{
		super(localeName, PlatformVariants.IOS);
	}

	@Override
	protected String getPrefix()
	{
		return "/" + name() + IosLocationPathFinder.LPROJ + "/";
	}
}
