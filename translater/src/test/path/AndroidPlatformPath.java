package test.path;

import java.util.ArrayList;
import java.util.List;

import test.model.PlatformVariants;

/**
 * Date: 15.02.2016
 * Time: 13:33
 *
 * @author Savin Mikhail
 */
public class AndroidPlatformPath extends PlatformPath
{

	public static final String XML = "xml";
	public static final String DEFAULT = "default";

	private List<String> flavorsNames = new ArrayList<>();

	public AndroidPlatformPath()
	{
		super(DEFAULT, PlatformVariants.ANDROID);
	}

	public AndroidPlatformPath(final String localeName)
	{
		super(localeName, PlatformVariants.ANDROID);
	}

	@Override
	protected String getPrefix()
	{
		if (name().equals(DEFAULT))
		{
			return "/" + AndroidLocationPathFinder.VALUES + "/";
		}

		return "/" + AndroidLocationPathFinder.VALUES + "-" + name() + "/";
	}

	@Override
	protected String getStringExtension()
	{
		return XML;
	}

	@Override
	public String[] getFileNames(final PlatformVariants platformVariants)
	{
		String[] result = new String[getPaths(platformVariants).length];
		int counter = 0;
		for (String fileName : fileNames)
		{
			for (String flavor : flavorsNames)
			{
				result[counter] = flavor + "_" + fileName;
				counter++;
			}
		}
		return result;
	}

	@Override
	public String[] getPaths(final PlatformVariants platformVariants)
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
