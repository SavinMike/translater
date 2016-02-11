package test.xponia;

import test.model.PlatformVariants;
import test.model.PlatformsPath;

public enum LanguageEnum implements PlatformsPath
{
	EN
			{
				@Override
				public String[] getPaths(final PlatformVariants platformVariants)
				{
					switch (platformVariants)
					{
						case IOS:
							return new String[]{"Localizable_en"};
						case ANDROID:
							return new String[]{"strings_en.xml"};
						case WEB:
							return new String[]{"django_en.po", "djangojs_en.po"};
						default:
							return new String[]{};
					}
				}
			},
	HU
			{
				@Override
				public String[] getPaths(final PlatformVariants platformVariants)
				{
					switch (platformVariants)
					{
						case IOS:
							return new String[]{"Localizable_hu"};
						case ANDROID:
							return new String[]{"strings_hu.xml"};
						case WEB:
						default:
							return new String[]{};
					}
				}
			},
	DE
			{
				@Override
				public String[] getPaths(final PlatformVariants platformVariants)
				{
					switch (platformVariants)
					{
						case IOS:
							return new String[]{"Localizable_de"};
						case ANDROID:
							return new String[]{"strings_de.xml"};
						case WEB:
							return new String[]{"django_de.po", "djangojs_de.po"};
						default:
							return new String[]{};
					}
				}
			},

	DEMO_DE
			{
				@Override
				public String[] getPaths(final PlatformVariants platformVariants)
				{
					switch (platformVariants)
					{
						case IOS:
							return new String[]{"Demo_de.strings"};
						case ANDROID:
						case WEB:
						default:
							return new String[]{};
					}
				}
			},
	ALLEE_DE
			{
				@Override
				public String[] getPaths(final PlatformVariants platformVariants)
				{
					switch (platformVariants)
					{
						case IOS:
							return new String[]{"Allee_de.strings"};
						case ANDROID:
						case WEB:
						default:
							return new String[]{};
					}
				}
			},
	DEMO_EN
			{
				@Override
				public String[] getPaths(final PlatformVariants platformVariants)
				{
					switch (platformVariants)
					{
						case IOS:
							return new String[]{"Demo_en.strings"};
						case ANDROID:
						case WEB:
						default:
							return new String[]{};
					}
				}
			},
	ALLEE_EN
			{
				@Override
				public String[] getPaths(final PlatformVariants platformVariants)
				{
					switch (platformVariants)
					{
						case IOS:
							return new String[]{"Allee_en.strings"};
						case ANDROID:
						case WEB:
						default:
							return new String[]{};
					}
				}
			},;

	public abstract String[] getPaths(final PlatformVariants platformVariants);
}