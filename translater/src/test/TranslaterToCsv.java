package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import test.model.PlatformVariants;
import test.model.PlatformsPath;
import test.model.TranslateItem;
import test.model.converter.WriteConverter;
import test.reader.Reader;
import test.writer.CSVWriter;
import test.writer.CsvHistory;

/**
 * Date: 19.10.2015
 * Time: 9:56
 *
 * @author Savin Mikhail
 */
public class TranslaterToCsv<E extends Enum<E> & PlatformsPath, H extends CsvHistory<E>>
{
	private final Class<E> mEClass;
	private final String mBasePath;
	private H mHistory;

	public TranslaterToCsv(final Class<E> EClass, final String basePath)
	{
		mEClass = EClass;
		mBasePath = basePath;
	}

	public <T> List<TranslateItem<E>> readAndWriteToCsv(Reader<T> reader, WriteConverter<T> writeConverter, PlatformVariants platformVariants)
	{
		List<TranslateItem<E>> result = new ArrayList<>();

		for (E languageEnum : mEClass.getEnumConstants())
		{
			if (mHistory != null && !mHistory.contains(languageEnum))
			{
				continue;
			}

			List<T> languageValue = new ArrayList<>();
			for (String path : languageEnum.getPaths(platformVariants))
			{
				T read = reader.readFile(mBasePath + platformVariants.basePath + path);
				if (read == null)
				{
					continue;
				}

				languageValue.addAll(Collections.singletonList(read));
			}
			Map<String, String> newValues = new LinkedHashMap<>();

			for (T item : languageValue)
			{
				newValues.putAll(writeConverter.convert(item, languageEnum));
			}

			addTranslation(languageEnum, newValues, result);
		}

		writeCsv(result, mBasePath + "csv/" + platformVariants.name().toLowerCase() + ".csv");

		return result;
	}

	public void writeCsv(final List<TranslateItem<E>> result, String path)
	{
		CSVWriter<TranslateItem<E>> csvWriter = new CSVWriter<>("\t", new TypeToken<TranslateItem<E>>()
		{
		}.getType());
		csvWriter.setCsvHistory(mHistory);

		csvWriter.writeToFile(result, path);
	}

	private void addTranslation(E language, final Map<String, String> strings, final List<TranslateItem<E>> result)
	{
		if (mHistory != null && !mHistory.contains(language))
		{
			return;
		}

		for (Map.Entry<String, String> entry : strings.entrySet())
		{
			boolean added = false;
			for (TranslateItem<E> translateItem : result)
			{
				if (translateItem.key.equals(entry.getKey()))
				{
					added = true;
					translateItem.addColumn(language, entry.getValue());
				}
			}

			if (!added)
			{
				TranslateItem<E> item = new TranslateItem<>(entry.getKey(), mEClass);
				item.addColumn(language, entry.getValue());
				result.add(item);
			}
		}
	}

	public void setHistory(final H history)
	{
		mHistory = history;
	}
}
