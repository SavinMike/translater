package test.parser;

import test.model.PlatformVariants;

/**
 * Date: 18.02.2016
 * Time: 16:02
 *
 * @author Savin Mikhail
 */
public class CsvFilenameInfo
{
	public final PlatformVariants platformVariants;
	public final String fileName;
	public final String csvName;

	public CsvFilenameInfo(final PlatformVariants platformVariants, final String fileName, final String csvName)
	{
		this.platformVariants = platformVariants;
		this.fileName = fileName;
		this.csvName = csvName;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final CsvFilenameInfo that = (CsvFilenameInfo) o;

		if (platformVariants != that.platformVariants)
		{
			return false;
		}
		return !(fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) && !(csvName != null ? !csvName.equals(that.csvName) : that.csvName != null);

	}

	@Override
	public int hashCode()
	{
		int result = platformVariants != null ? platformVariants.hashCode() : 0;
		result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
		result = 31 * result + (csvName != null ? csvName.hashCode() : 0);
		return result;
	}
}
