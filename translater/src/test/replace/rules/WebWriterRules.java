package test.replace.rules;

import test.model.TranslateItem;
import test.reader.web.WebString;

/**
 * Date: 11.02.2016
 * Time: 16:38
 *
 * @author Savin Mikhail
 */
public class WebWriterRules implements WriterRules
{
	@Override
	public <E extends Enum<E>> String updateString(final TranslateItem<E> translateItem, final E language)
	{
		return WebString.TRANSLATE + " \"" + translateItem.getValue(language) + "\"";
	}
}
