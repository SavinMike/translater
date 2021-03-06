package com.arellomobile.translater.creator;

import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.model.PlatformVariants;
import com.arellomobile.translater.path.PathHelper;
import com.arellomobile.translater.path.PlatformsPath;
import com.arellomobile.translater.path.WebPlatformPath;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Date: 19.02.2016
 * Time: 9:19
 *
 * @author Savin Mikhail
 */
public class WebCreatorLocalizeFile implements CreatorLocalizeFile
{
	private Config mConfig;
	private Collection<File> mDefaultPoFiles;

	public WebCreatorLocalizeFile(final Config config)
	{
		mConfig = config;
		PathHelper pathHelper = new PathHelper(mConfig);
		List<PlatformsPath> platformsPaths = pathHelper.getLocations().get(PlatformVariants.WEB);
		if (platformsPaths != null)
		{
			for (PlatformsPath item : platformsPaths)
			{
				WebPlatformPath cast = WebPlatformPath.class.cast(item);
				if (cast.isDefault())
				{
					mDefaultPoFiles = FileUtils.listFiles(new File(cast.getRootPath() + cast.getPrefix()), null, false);
				}
			}
		}
	}

	@Override
	public boolean createFile(final File file, String charset)
	{
		try
		{
			//noinspection ResultOfMethodCallIgnored
			file.getParentFile().mkdirs();

			for (File item : mDefaultPoFiles)
			{
				if (item.getName().equals(file.getName()))
				{
					FileUtils.copyFile(item, file);
					return true;
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return false;
	}
}
