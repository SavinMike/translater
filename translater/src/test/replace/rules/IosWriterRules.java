package test.replace.rules;

import test.model.TranslateItem;

/**
 * Date: 11.02.2016
 * Time: 18:10
 *
 * @author Savin Mikhail
 */
public class IosWriterRules implements WriterRules
{

	@Override
	public <E extends Enum<E>> String updateString(final TranslateItem<E> translateItem, final E language)
	{
		return new StringBuilder("\"").append(translateItem.key).append("\" = \"").append(translateItem.getValue(language)).append("\";").toString();
	}
}
