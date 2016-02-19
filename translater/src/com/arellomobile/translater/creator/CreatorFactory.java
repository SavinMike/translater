package com.arellomobile.translater.creator;

import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.model.PlatformVariants;

/**
 * Date: 18.02.2016
 * Time: 17:31
 *
 * @author Savin Mikhail
 */
public class CreatorFactory
{
	public static CreatorLocalizeFile getCreator(PlatformVariants platformVariants, Config config)
	{
		switch (platformVariants){

			case ANDROID:
				return new AndroidCreatorLocalizeFile();
			case IOS:
				return new IosCreatorLocalizeFile();
			case WEB:
				return new WebCreatorLocalizeFile(config);
		}

		return null;
	}
}
