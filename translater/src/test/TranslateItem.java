package test;

import java.util.HashMap;
import java.util.Map;

import test.writer.CSVObjectConverter;

/**
 * Date: 09.02.2016
 * Time: 15:56
 *
 * @author Savin Mikhail
 */
public class TranslateItem<T extends Enum<T>> implements CSVObjectConverter
{
	public final String key;
	private final Map<T, String> values = new HashMap<>();
	private final Class<T> enumClass;

	public TranslateItem(final String key, Class<T> enumClass)
	{
		this.key = key;
		this.enumClass = enumClass;
	}


	public void addColumn(T language, String value)
	{
		values.put(language, value);
	}

	@Override
	public String convert(final String delimiter)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(key).append(delimiter);
		for (T t : enumClass.getEnumConstants())
		{
			if (values.containsKey(t))
			{
				stringBuilder.append(values.get(t).replaceAll("\\t", "    "));
			}
			else
			{
				stringBuilder.append("");
			}

			stringBuilder.append(delimiter);
		}

		return stringBuilder.toString();
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

		final TranslateItem<?> that = (TranslateItem<?>) o;

		return !(key != null ? !key.equals(that.key) : that.key != null);

	}

	public String getValue(T languageEnum)
	{
		String s = values.get(languageEnum);
		return s == null ? "" : s.trim().toLowerCase();
	}

	@Override
	public int hashCode()
	{
		return key != null ? key.hashCode() : 0;
	}
}
