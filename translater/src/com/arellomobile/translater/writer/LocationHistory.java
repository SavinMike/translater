package com.arellomobile.translater.writer;

import com.arellomobile.translater.path.PlatformsPath;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Date: 15.02.2016
 * Time: 14:00
 *
 * @author Savin Mikhail
 */
public class LocationHistory implements CsvHistory<PlatformsPath>
{
	private Set<PlatformsPath> mPlatformPaths;

	public LocationHistory(final Collection<PlatformsPath> platformPaths)
	{
		mPlatformPaths = new LinkedHashSet<>(platformPaths);
	}

	@Override
	public Set<PlatformsPath> getEnumSet()
	{
		return mPlatformPaths;
	}

	@Override
	public String createHistory(final String delimiter)
	{
		StringBuilder stringBuilder = new StringBuilder().append("id").append(delimiter);

		for (PlatformsPath platformPath : getEnumSet())
		{
			String name = platformPath.name().replaceAll("\\t", "    ");
			stringBuilder.append(name).append(delimiter);
		}

		return stringBuilder.toString();
	}

	@Override
	public boolean contains(final PlatformsPath platformPath)
	{
		return getEnumSet().contains(platformPath);
	}
}
