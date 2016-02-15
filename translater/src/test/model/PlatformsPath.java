package test.model;

/**
 * Date: 10.02.2016
 * Time: 19:02
 *
 * @author Savin Mikhail
 */
public interface PlatformsPath
{
	String[] getPaths(PlatformVariants platformVariants);

	String[] getFileNames(PlatformVariants platformVariants);

	String getCsvName(PlatformVariants platformVariants, String path);
}
