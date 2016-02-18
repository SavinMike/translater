package test.model;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 16.02.2016
 * Time: 13:10
 *
 * @author Savin Mikhail
 */
public class Config
{
	private Map<PlatformVariants, String> mPathMap = new HashMap<>();
	private Map<PlatformVariants, String[]> mExcludesMap = new HashMap<>();
	private Map<PlatformVariants, String[]> mIncludesMap = new HashMap<>();

	private Map<String, Charset> mFileCharset = new HashMap<>();

	private String mCsvPath;
	private Map<PlatformVariants, String> mDefaultLocaleMap = new HashMap<>();

	public Map<PlatformVariants, String[]> getExcludesMap()
	{
		return mExcludesMap;
	}

	public Config addExcludesMap(final PlatformVariants platformVariants, String... excludes)
	{
		mExcludesMap.put(platformVariants, excludes);
		return this;
	}

	public Map<PlatformVariants, String[]> getIncludesMap()
	{
		return mIncludesMap;
	}

	public Config addIncludesMap(final PlatformVariants platformVariants, String... includesMap)
	{
		mIncludesMap.put(platformVariants, includesMap);
		return this;
	}

	public Map<String, Charset> getFileCharset()
	{
		return mFileCharset;
	}

	public Config addFileCharset(final String fileName, Charset charset)
	{
		mFileCharset.put(fileName, charset);
		return this;
	}

	public Map<PlatformVariants, String> getPathMap()
	{
		return mPathMap;
	}

	public Config addPath(final PlatformVariants platformVariants, String pathMap)
	{
		mPathMap.put(platformVariants, pathMap);
		return this;
	}

	public Map<PlatformVariants, String> getDefaultLocaleMap()
	{
		return mDefaultLocaleMap;
	}

	public Config addDefaultLocaleMap(final PlatformVariants platformVariants, String pathMap)
	{
		mDefaultLocaleMap.put(platformVariants, pathMap);
		return this;
	}

	public String getCsvPath()
	{
		return mCsvPath;
	}

	public Config setCsvPath(final String csvPath)
	{
		this.mCsvPath = csvPath;
		return this;
	}
}
