package test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.creator.CreatorFactory;
import test.creator.CreatorLocalizeFile;
import test.model.Config;
import test.model.PlatformVariants;
import test.model.TranslateItem;
import test.path.PlatformsPath;
import test.replace.TranslateUpdateReader;
import test.replace.ValueChanger;

/**
 * Date: 10.02.2016
 * Time: 19:09
 *
 * @author Savin Mikhail
 */
public class TranlaterFromCsv<E extends PlatformsPath>
{

	private final Collection<E> mPlatformsPath;

	private Map<String, Charset> mCharsetMap = new HashMap<>();

	public void setCharsetMap(final Map<String, Charset> charsetMap)
	{
		mCharsetMap.putAll(charsetMap);
	}

	public TranlaterFromCsv(final Collection<E> platformsPath)
	{
		mPlatformsPath = platformsPath;
	}

	public void updateTranslation(List<TranslateItem<E>> translateItems, PlatformVariants platformVariants, Config config)
	{
		for (E platformPath : mPlatformsPath)
		{
			boolean hasValue = false;
			for (TranslateItem<E> translateItem : translateItems)
			{
				String value = translateItem.getValue(platformPath);
				if (value != null && !value.isEmpty())
				{
					hasValue = true;
					break;
				}
			}

			if (!hasValue)
			{
				break;
			}

			int counter = 0;
			for (String path : platformPath.getPaths())
			{
				String filename = platformPath.getFileNames()[counter];
				String filePath = platformPath.getRootPath() + path;

				ValueChanger<List<TranslateItem<E>>> listValueChanger = new ValueChanger<>(new TranslateUpdateReader<>(platformVariants, platformPath));

				Charset charset = Charset.defaultCharset();
				if (mCharsetMap.containsKey(filename))
				{
					charset = mCharsetMap.get(filename);
					listValueChanger.setCharset(charset);
				}
				File file = new File(filePath);
				counter++;
				CreatorLocalizeFile creator = CreatorFactory.getCreator(platformVariants, config);
				if (!file.exists())
				{
					if (creator == null || !creator.createFile(file, charset.name()))
					{
						continue;
					}
				}

				listValueChanger.updateValue(filePath, translateItems);
			}
		}

	}
}
