package test.model;

import java.util.HashMap;
import java.util.Map;

import test.writer.CSVObjectConverter;
import test.writer.CsvHistory;

/**
 * Date: 09.02.2016
 * Time: 15:56
 *
 * @author Savin Mikhail
 */
public class TranslateItem<T> implements CSVObjectConverter<T>
{
	public final String key;
	private final Map<T, String> values = new HashMap<>();

	public TranslateItem(final String key)
	{
		this.key = key;
	}

	public void addColumn(T language, String value)
	{
		values.put(language, value);
	}

	public boolean contains(T language)
	{
		return values.containsKey(language);
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
		return s == null ? "" : s;
	}

	@Override
	public int hashCode()
	{
		return key != null ? key.hashCode() : 0;
	}

	@Override
	public <H extends CsvHistory<T>> String convert(final String delimiter, final H history)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(key).append(delimiter);
		for (T t : history.getEnumSet())
		{
			if (values.containsKey(t))
			{
				String s = values.get(t);
				stringBuilder.append(s == null ? "" : s.replaceAll("\\t", "    "));
			}
			else
			{
				stringBuilder.append("");
			}

			stringBuilder.append(delimiter);
		}

		return stringBuilder.toString();
	}
}
