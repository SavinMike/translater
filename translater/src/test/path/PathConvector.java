package test.path;

import org.apache.commons.io.FilenameUtils;

import test.model.PlatformVariants;

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
		return basePath + "csv/" + platformVariants.name().toLowerCase() + "_" + FilenameUtils.getBaseName(fileNames) + ".tsv";
	}
}
