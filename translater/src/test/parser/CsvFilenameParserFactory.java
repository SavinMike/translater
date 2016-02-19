package test.parser;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;

import test.model.Config;
import test.model.PlatformVariants;
import test.path.AndroidLocationPathFinder;
import test.path.PathConvector;

/**
 * Date: 18.02.2016
 * Time: 16:06
 *
 * @author Savin Mikhail
 */
public class CsvFilenameParserFactory
{
	@Nullable
	public static CsvFilenameInfo getCsvFilenameInfo(Config config, String csvFileName, final File csvFileParent)
	{
		PlatformVariants currentPlatformVariants = null;
		for (PlatformVariants platformVariants : config.getPathMap().keySet())
		{
			if (csvFileName.toLowerCase().startsWith(platformVariants.name().toLowerCase()))
			{
				currentPlatformVariants = platformVariants;
			}
		}

		if (currentPlatformVariants == null)
		{
			return null;
		}

		Matcher fromCsv = PathConvector.getFromCsv(config, csvFileName, currentPlatformVariants);

		if (fromCsv.find())
		{
			switch (currentPlatformVariants)
			{
				case ANDROID:
					Map<PlatformVariants, String> pathMap = config.getPathMap();
					if (pathMap.containsKey(currentPlatformVariants) && AndroidLocationPathFinder.isGradle(new File(pathMap.get(currentPlatformVariants))))
					{
						return new AndroidCsvFilenameInfo(currentPlatformVariants, String.format("%s.%s", fromCsv.group(2), currentPlatformVariants.getExtension()), fromCsv.group(1), csvFileParent.getAbsolutePath() + "/" + csvFileName);
					}
				case IOS:
				case WEB:
					return new CsvFilenameInfo(currentPlatformVariants, String.format("%s.%s", fromCsv.group(1), currentPlatformVariants.getExtension()), csvFileParent.getAbsolutePath() + "/" + csvFileName);
			}
		}

		throw new IllegalArgumentException("Incorrect csvFile name");
	}
}
