package test.replace.rules;

import test.model.TranslateItem;
import test.reader.ReaderRules;

/**
 * Date: 11.02.2016
 * Time: 18:10
 *
 * @author Savin Mikhail
 */
public class IosWriterRules implements WriterRules
{

	@Override
	public <E> String updateString(final TranslateItem<E> translateItem, final E language, final String realString)
	{
		String value = translateItem.getValue(language);
		value = value.replaceAll(ReaderRules.QUOT, "\\\\\"");

		return String.format("\"%s\" = \"%s\";", translateItem.key, value);
	}

	@Override
	public <E> String updateId(final TranslateItem<E> translateItem, final E language)
	{
		return null;
	}

	@Override
	public String getComment(final String commentString)
	{
		return "/*" + commentString + "*/";
	}
}
