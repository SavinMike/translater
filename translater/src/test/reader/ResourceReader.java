package test.reader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Date: 18.11.2015
 * Time: 20:00
 *
 * @author Savin Mikhail
 */
public class ResourceReader extends PatternReader
{
	private final Resource mResource;

	public ResourceReader(final Resource resource)
	{
		mResource = resource;
	}

	@Override
	protected Collection<File> getFiles(File file)
	{
		return FileUtils.listFiles(file, new String[]{"java", "xml"}, true);
	}

	@Override
	public Pattern[] getPatterns()
	{
		return new Pattern[]{
				Pattern.compile("(.*\\.)*R\\." + mResource.getLowerName() + "\\.frag_[\\w|\\d]*,"),
				Pattern.compile("(.*\\.)*R\\." + mResource.getLowerName() + "\\.frag_[\\w|\\d]+\\)")
		};
	}

	public enum Resource
	{
		STRING, LAYOUT, DIMENS;

		public String getLowerName()
		{
			return name().toLowerCase();
		}
	}
}
