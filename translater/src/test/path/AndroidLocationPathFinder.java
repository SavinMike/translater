package test.path;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 15.02.2016
 * Time: 13:27
 *
 * @author Savin Mikhail
 */
public class AndroidLocationPathFinder implements LocationPathFinder
{
	public static final String VALUES = "values";
	public static final String RES = "res";
	public static final String ANDROID_TEST = "androidTest";

	private String mProjectPath;
	private String[] mIncludes;

	public AndroidLocationPathFinder(final String projectPath)
	{
		mProjectPath = projectPath;
	}

	@Override
	public List<PlatformsPath> getLocations()
	{
		File localeDir = new File(mProjectPath);

		List<PlatformsPath> platformsPaths = new ArrayList<>();
		if (isGradle(localeDir))
		{
			localeDir = new File(mProjectPath + "/src");
			for (String flavors : localeDir.list())
			{
				if (flavors.equals(ANDROID_TEST))
				{
					continue;
				}

				File flavorsDir = new File(mProjectPath + "/src/" + flavors + "/" + RES);
				addFromRes(flavorsDir.getAbsoluteFile(), platformsPaths, flavors);
			}
		}
		else
		{
			localeDir = new File(mProjectPath + "/" + RES);
			addFromRes(localeDir, platformsPaths, "");
		}
		return platformsPaths;
	}

	private void addFromRes(final File resFolder, final List<PlatformsPath> platformsPaths, String flavors)
	{
		for (String fileName : resFolder.list())
		{
			String name;
			if (fileName.equals(VALUES))
			{
				name = AndroidPlatformPath.DEFAULT;
			}
			else if (fileName.contains(VALUES))
			{
				String baseName = FilenameUtils.getBaseName(fileName);
				name = baseName.substring(VALUES.length() + 1, baseName.length());
			}
			else
			{
				continue;
			}

			AndroidPlatformPath androidPlatformPath = null;
			for (PlatformsPath platformPath : platformsPaths)
			{
				if (platformPath.name().equals(name))
				{
					androidPlatformPath = (AndroidPlatformPath) platformPath;
					break;
				}
			}

			if (androidPlatformPath == null)
			{
				androidPlatformPath = new AndroidPlatformPath(name);
				if (name.equals(AndroidPlatformPath.DEFAULT))
				{
					androidPlatformPath.setDefault(true);
				}

				androidPlatformPath.setIncludes(mIncludes);
				if (androidPlatformPath.generateFileNames(resFolder.getAbsolutePath()))
				{
					platformsPaths.add(androidPlatformPath);
				}
			} else {
				androidPlatformPath.generateFileNames(resFolder.getAbsolutePath());
			}

			androidPlatformPath.setProjectPath(mProjectPath + "/src/");
			androidPlatformPath.addFlavors(flavors);
		}
	}

	private boolean isGradle(File localeDir)
	{
		for (String filename : localeDir.list())
		{
			if (filename.contains(".gradle"))
			{
				return true;
			}
		}

		return false;
	}

	public void setIncludes(final String... includes)
	{
		this.mIncludes = includes;
	}
}
