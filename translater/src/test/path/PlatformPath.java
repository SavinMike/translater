package test.path;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import test.model.PlatformVariants;

/**
 * Date: 15.02.2016
 * Time: 13:36
 *
 * @author Savin Mikhail
 */
public abstract class PlatformPath implements PlatformsPath
{
	private final String localeName;
	private final PlatformVariants mPlatformVariants;
	protected List<String> fileNames = new ArrayList<>();
	private String projectPath;
	private boolean isDefault;
	private List<String> mExcludes = new ArrayList<>();
	private List<String> mIncludes = new ArrayList<>();

	public PlatformPath(final String localeName, PlatformVariants platformVariants)
	{
		this.localeName = localeName;
		mPlatformVariants = platformVariants;
	}

	public boolean isDefault()
	{
		return isDefault;
	}

	public PlatformPath setDefault(final boolean aDefault)
	{
		isDefault = aDefault;
		return this;
	}

	@Override
	public String[] getPaths()
	{

		String[] result = fileNames.toArray(new String[fileNames.size()]);

		for (int i = 0; i < result.length; i++)
		{
			result[i] = getPrefix() + result[i];
		}

		return result;
	}

	protected abstract String getPrefix();

	public void addFileNames(String filename)
	{
		fileNames.add(filename);
	}

	public boolean generateFileNames(String dir)
	{
		boolean isAdded = false;
		if (projectPath == null)
		{
			projectPath = dir;
		}
		Collection<File> localeDir = FileUtils.listFiles(new File(dir + getPrefix()), null, false);

		for (File file : localeDir)
		{
			if (FilenameUtils.getExtension(file.getAbsolutePath()).equals(mPlatformVariants.getExtension()) && !mExcludes.contains(file.getName()) && (mIncludes.isEmpty() || mIncludes.contains(file.getName())))
			{
				isAdded = true;
				fileNames.add(file.getName());
			}
		}

		return isAdded;
	}

	public void setProjectPath(final String projectPath)
	{
		this.projectPath = projectPath;
	}

	@Override
	public String[] getFileNames()
	{
		return fileNames.toArray(new String[fileNames.size()]);
	}

	@Override
	public String name()
	{
		return localeName;
	}

	@Override
	public String getRootPath()
	{
		return projectPath;
	}

	public void setExcludes(final String[] excludes)
	{
		if (excludes != null)
		{
			mExcludes = Arrays.asList(excludes);
		}
	}

	public void setIncludes(final String[] includes)
	{
		if (includes != null)
		{
			mIncludes = Arrays.asList(includes);
		}
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

		final PlatformPath that = (PlatformPath) o;

		return !(localeName != null ? !localeName.equals(that.localeName) : that.localeName != null) && !(projectPath != null ? !projectPath.equals(that.projectPath) : that.projectPath != null);

	}

	@Override
	public int hashCode()
	{
		int result = localeName != null ? localeName.hashCode() : 0;
		result = 31 * result + (projectPath != null ? projectPath.hashCode() : 0);
		return result;
	}

	public PlatformVariants getPlatformVariants()
	{
		return mPlatformVariants;
	}
}
