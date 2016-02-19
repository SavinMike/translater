package com.arellomobile.translater.path;

import com.arellomobile.translater.model.PlatformVariants;

/**
 * Date: 15.02.2016
 * Time: 13:33
 *
 * @author Savin Mikhail
 */
public class WebPlatformPath extends PlatformPath
{

	public WebPlatformPath(final String localeName)
	{
		super(localeName, PlatformVariants.WEB);
	}

	@Override
	public String getPrefix()
	{
		return "/" + name() + "/LC_MESSAGES/";
	}
}
