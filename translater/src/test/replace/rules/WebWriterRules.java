package test.replace.rules;

import test.model.TranslateItem;
import test.reader.ReaderRules;
import test.reader.web.WebString;

/**
 * Date: 11.02.2016
 * Time: 16:38
 *
 * @author Savin Mikhail
 */
public class WebWriterRules implements WriterRules
{

	public static final int MAX_LENGTH = 80;

	@Override
	public <E extends Enum<E>> String updateString(final TranslateItem<E> translateItem, final E language, final String realString)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(WebString.TRANSLATE).append(" \"");

		String value = translateItem.getValue(language);

		if (language instanceof LangageChaecker && ((LangageChaecker) language).isDefault() && value.equals(translateItem.key))
		{
			return stringBuilder.append("\"").toString();
		}

		return getString(stringBuilder, value);
	}

	private String getString(final StringBuilder stringBuilder, String value)
	{
		value = value.replaceAll(ReaderRules.QUOT, "\\\\\"");
		if (value.length() + stringBuilder.toString().length() > MAX_LENGTH)
		{
			stringBuilder.append("\"\n");
			String[] split = value.split(" ");
			StringBuilder localStringBuilder = new StringBuilder();
			for (String s : split)
			{
				if (localStringBuilder.length() == 0)
				{
					localStringBuilder.append("\"");
				}

				if (s.length() + localStringBuilder.toString().length() > MAX_LENGTH)
				{
					localStringBuilder.append("\"\n");
					stringBuilder.append(localStringBuilder);
					localStringBuilder.setLength(0);
					localStringBuilder.append("\"");
				}

				localStringBuilder.append(s).append(" ");
			}

			if (!localStringBuilder.toString().isEmpty())
			{
				String result = localStringBuilder.toString();
				result = result.substring(0, result.length() - 1);
				stringBuilder.append(result).append("\"");
			}
		}
		else
		{
			stringBuilder.append(value);
			stringBuilder.append("\"");
		}

		return stringBuilder.toString();
	}

	@Override
	public <E extends Enum<E>> String updateId(final TranslateItem<E> translateItem, final E language)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(WebString.ID).append(" \"");
		getString(stringBuilder, translateItem.key);
		return stringBuilder.toString();
	}

	@Override
	public String getComment(final String commentString)
	{
		return "#" + commentString;
	}
}
