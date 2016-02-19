package com.arellomobile.translater.path;

import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.model.PlatformVariants;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 16.02.2016
 * Time: 11:32
 *
 * @author Savin Mikhail
 */
public class PathHelper
{
	private final Map<PlatformVariants, String> mVariantsStringMap = new HashMap<>();
	private final Map<PlatformVariants, String> mDefaultLocation = new HashMap<>();
	private final Map<PlatformVariants, String[]> mIncludesMap = new HashMap<>();
	private final Map<PlatformVariants, String[]> mExcludesMap = new HashMap<>();

	private String mCsvPath;

	public PathHelper(Config config)
	{
		mVariantsStringMap.putAll(config.getPathMap());

		for (Map.Entry<PlatformVariants, String[]> includes : config.getIncludesMap().entrySet())
		{
			addIncludes(includes.getKey(), includes.getValue());
		}

		for (Map.Entry<PlatformVariants, String[]> excludes : config.getExcludesMap().entrySet())
		{
			addExcludes(excludes.getKey(), excludes.getValue());
		}

		mDefaultLocation.putAll(config.getDefaultLocaleMap());
		mCsvPath = config.getCsvPath();

		File file = new File(mCsvPath);

		if (!file.exists())
		{
			//noinspection ResultOfMethodCallIgnored
			file.mkdirs();
		}
	}

	public void addIncludes(PlatformVariants platformVariants, String... includes)
	{
		mIncludesMap.put(platformVariants, includes);
	}

	public void addExcludes(PlatformVariants platformVariants, String... excludes)
	{
		mExcludesMap.put(platformVariants, excludes);
	}

	public Map<PlatformVariants, List<PlatformsPath>> getLocations()
	{
		Map<PlatformVariants, List<PlatformsPath>> result = new HashMap<>();
		for (Map.Entry<PlatformVariants, String> entry : mVariantsStringMap.entrySet())
		{
			LocationPathFinder locationPathFinder = LocationPathFinderFactory.create(entry.getKey(), entry.getValue());

			if (mIncludesMap.containsKey(entry.getKey()))
			{
				locationPathFinder.setIncludes(mIncludesMap.get(entry.getKey()));
			}

			if (mDefaultLocation.containsKey(entry.getKey()))
			{
				locationPathFinder.setDefault(mDefaultLocation.get(entry.getKey()));
			}

			if (mExcludesMap.containsKey(entry.getKey()))
			{
				locationPathFinder.setExcludes(mExcludesMap.get(entry.getKey()));
			}

			result.put(entry.getKey(), locationPathFinder.getLocations());
		}

		return result;
	}

	public File getCsvFile()
	{
		File csvDir = new File(mCsvPath);
		if (!csvDir.exists())
		{
			throw new IllegalArgumentException("Incorrect path for csv folder!");
		}

		return csvDir;
	}

//	public Map<PlatformVariants, List<PlatformsPath>> getLocationsFromCsv()
//	{
//		Map<PlatformVariants, List<PlatformsPath>> result = new HashMap<>();
//		File csvDir = new File(mCsvPath);
//
//		if (!csvDir.exists())
//		{
//			throw new IllegalArgumentException("Incorrect path for csv folder!");
//		}
//
//		for (String fileName : csvDir.list())
//		{
//			for (PlatformVariants platformVariants : PlatformVariants.values())
//			{
//				if (fileName.toLowerCase().startsWith(platformVariants.name().toLowerCase()))
//				{
//					List<PlatformsPath> platformsPaths;
//					if (result.containsKey(platformVariants))
//					{
//						platformsPaths = result.get(platformVariants);
//					}
//					else
//					{
//						platformsPaths = new ArrayList<>();
//						result.put(platformVariants, platformsPaths);
//					}
//
////					PlatP
//				}
//			}
//		}
//	}
}
