package com.arellomobile.translater.path;

import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.model.PlatformVariants;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 16.02.2016
 * Time: 12:09
 *
 * @author Savin Mikhail
 */
public class PathConvector
{
	public static String getCsvPath(String basePath, PlatformVariants platformVariants, String fileNames)
	{
		return basePath + platformVariants.name().toLowerCase() + "_" + FilenameUtils.getBaseName(fileNames) + ".tsv";
	}


	public static Matcher getFromCsv(Config config, String csvFileName, PlatformVariants platformVariants)
	{
		Pattern compile = null;
		if (platformVariants.equals(PlatformVariants.ANDROID))
		{
			if (AndroidLocationPathFinder.isGradle(new File(config.getPathMap().get(platformVariants))))
			{
				compile = Pattern.compile(platformVariants.name().toLowerCase() + "_" + AndroidPlatformPath.START_FLAVORS + "(.*)" + AndroidPlatformPath.END_FLAVORS + "([\\w\\d_]*)\\.[tc]sv");
			}
		}

		if (compile == null)
		{
			compile = Pattern.compile(platformVariants.name().toLowerCase() + "_(.*)\\.[tc]sv");
		}
		return compile.matcher(csvFileName);
	}
}
