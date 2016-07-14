package com.arellomobile.translater.path;

import com.arellomobile.translater.model.PlatformVariants;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 15.02.2016
 * Time: 13:33
 *
 * @author Savin Mikhail
 */
public class AndroidPlatformPath extends PlatformPath
{

	public static final String DEFAULT = "default";
	public static final String START_FLAVORS = "#flavors_";
	public static final String END_FLAVORS = "_flavors#_";

	private List<String> flavorsNames = new ArrayList<>();

	public AndroidPlatformPath(final String localeName)
	{
		super(localeName, PlatformVariants.ANDROID);
	}

	@Override
	protected String getPrefix()
	{
		if (isDefault())
		{
			return "/" + AndroidLocationPathFinder.VALUES + "/";
		}

		return "/" + AndroidLocationPathFinder.VALUES + "-" + name() + "/";
	}

	@Override
	public String[] getFileNames()
	{
		String[] result = new String[getPaths().length];
		int counter = 0;
		for (String fileName : fileNames)
		{
			for (String flavor : flavorsNames)
			{
				result[counter] = START_FLAVORS + flavor + END_FLAVORS + fileName;
				counter++;
			}
		}
		return result;
	}

	@Override
	public String[] getPaths()
	{

		List<String> result = new ArrayList<>();

		for (String fileName : fileNames)
		{
			for (String flavors : flavorsNames)
			{
				result.add(flavors + "/" + AndroidLocationPathFinder.RES + getPrefix() + fileName);
			}
		}

		return result.toArray(new String[result.size()]);
	}

	public void addFlavors(String flavors)
	{
		flavorsNames.add(flavors);
	}
}
