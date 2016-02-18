package test.path;

/**
 * Date: 10.02.2016
 * Time: 19:02
 *
 * @author Savin Mikhail
 */
public interface PlatformsPath
{
	String[] getPaths();

	String[] getFileNames();

	String getRootPath();

	String name();

	void setIncludes(final String... includes);

	void setExcludes(final String... excludes);

}
