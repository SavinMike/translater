package test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.model.PlatformVariants;
import test.model.TranslateItem;
import test.path.PathConvector;
import test.path.PlatformsPath;
import test.reader.CSVReader;
import test.replace.TranslateUpdateReader;
import test.replace.ValueChanger;
import test.writer.CsvHistory;

/**
 * Date: 10.02.2016
 * Time: 19:09
 *
 * @author Savin Mikhail
 */
public class TranlaterFromCsv<E extends PlatformsPath, H extends CsvHistory<E>>
{

	private final String mBasePath;
	private final List<E> mPlatformsPath;

	private Map<String, Charset> mCharsetMap = new HashMap<>();

	public void setCharsetMap(final Map<String, Charset> charsetMap)
	{
		mCharsetMap.putAll(charsetMap);
	}

	public TranlaterFromCsv(final String basePath, final List<E> platformsPath)
	{
		mBasePath = basePath;
		mPlatformsPath = platformsPath;
	}

	public void readFromCsv(CSVReader<TranslateItem<E>, H> csvReader, PlatformVariants platformVariants)
	{
		for (E platformPath : mPlatformsPath)
		{
			int counter = 0;
			for (String path : platformPath.getPaths())
			{
				String filename = platformPath.getFileNames()[counter];
				String file = platformPath.getRootPath() + path;

				String csvPath = PathConvector.getCsvPath(mBasePath, platformVariants, filename);
				if (new File(file).exists() && new File(csvPath).exists())
				{
					List<TranslateItem<E>> translateItems = csvReader.readFile(csvPath);
					ValueChanger<List<TranslateItem<E>>> listValueChanger = new ValueChanger<>(new TranslateUpdateReader<>(platformVariants, platformPath));

					if (mCharsetMap.containsKey(filename))
					{
						listValueChanger.setCharset(mCharsetMap.get(filename));
					}

					listValueChanger.updateValue(file, translateItems);
				}
				counter++;
			}
		}

	}
}
