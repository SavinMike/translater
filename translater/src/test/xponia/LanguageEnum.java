package test.xponia;

import test.model.PlatformVariants;
import test.model.PlatformsPath;
import test.replace.rules.LangageChaecker;
import test.utils.FilenameUtils;

public enum LanguageEnum implements PlatformsPath, LangageChaecker
{
	EN,	HU,	DE;

	@Override
	public String[] getPaths(final PlatformVariants platformVariants)
	{
		String[] result = getFileNames(platformVariants);

		for (int i = 0; i < result.length; i++)
		{
			result[i] = getPrefix() + result[i];
		}

		return result;
	}

	@Override
	public String[] getFileNames(final PlatformVariants platformVariants)
	{
		String[] result;
		switch (platformVariants)
		{
			case IOS:
				result = new String[]{"Localizable"};
				break;
			case ANDROID:
				result = new String[]{"strings.xml"};
				break;
			case WEB:
				result = new String[]{"django.po", "djangojs.po"};
				break;
			default:
				result = new String[]{};
				break;
		}
		return result;
	}

	@Override
	public String getCsvName(final PlatformVariants platformVariants, final String path)
	{
		return platformVariants.name().toLowerCase() + "_" + FilenameUtils.getBaseName(path) + ".tsv";
	}

	public String getPrefix()
	{
		return name().toLowerCase() + "/";
	}

	@Override
	public boolean isDefault()
	{
		return this == EN;
	}


}