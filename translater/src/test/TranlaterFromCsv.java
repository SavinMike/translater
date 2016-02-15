package test;

import java.util.EnumSet;
import java.util.List;

import test.model.PlatformVariants;
import test.path.PlatformsPath;
import test.model.TranslateItem;
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
public class TranlaterFromCsv<E extends Enum<E> & PlatformsPath, H extends CsvHistory<E>>
{

	private String mBasePath;
	private Class<E> mEClass;

	public TranlaterFromCsv(final String basePath, final Class<E> eClass)
	{
		mBasePath = basePath;
		mEClass = eClass;
	}

	public void readFromCsv(CSVReader<TranslateItem<E>, H> csvReader, PlatformVariants platformVariants)
	{
		for (E languageEnum : EnumSet.allOf(mEClass))
		{
			for (String path : languageEnum.getPaths(platformVariants))
			{
				List<TranslateItem<E>> translateItems = csvReader.readFile(mBasePath + "csv/" + languageEnum.getCsvName(platformVariants, path));
				new ValueChanger<>(new TranslateUpdateReader<>(platformVariants, languageEnum)).updateValue(mBasePath + platformVariants.basePath + path, translateItems);
			}
		}

	}
}
