package test.path;

import java.util.List;

/**
 * Date: 15.02.2016
 * Time: 13:26
 *
 * @author Savin Mikhail
 */
public interface LocationPathFinder
{
	List<PlatformsPath> getLocations();

	void setIncludes(final String... includes);

	void setExcludes(final String... excludes);

	void setDefault(String isDefault);

}
