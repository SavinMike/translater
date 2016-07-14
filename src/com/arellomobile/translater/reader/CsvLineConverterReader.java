package com.arellomobile.translater.reader;

import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.parser.CsvFilenameInfo;
import com.arellomobile.translater.path.PlatformsPath;
import com.arellomobile.translater.path.PlatformsPathFactory;
import com.arellomobile.translater.writer.LocationHistory;

import java.util.ArrayList;
import java.util.List;

public class CsvLineConverterReader implements LineConverterReader<LocationHistory>
{
	private LocationHistory mLocationHistory;
	private CsvFilenameInfo mCsvFilenameInfo;
	private Config mConfig;

	public CsvLineConverterReader(final CsvFilenameInfo csvFilenameInfo, final Config config)
	{
		mCsvFilenameInfo = csvFilenameInfo;
		mConfig = config;
	}

	@Override
	public LocationHistory convertHistory(final String[] history)
	{
		List<PlatformsPath> result = new ArrayList<>();
		for (int i = 1; i < history.length; i++)
		{
			String s = history[i];
			if (!s.isEmpty())
			{
				PlatformsPath platformsPath = PlatformsPathFactory.createPlatformsPath(mCsvFilenameInfo, mConfig, s);
				result.add(platformsPath);
			}
		}

		mLocationHistory = new LocationHistory(result);
		return mLocationHistory;
	}

	public LocationHistory getLocationHistory()
	{
		return mLocationHistory;
	}
}