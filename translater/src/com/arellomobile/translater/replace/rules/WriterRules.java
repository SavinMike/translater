package com.arellomobile.translater.replace.rules;

import com.arellomobile.translater.model.TranslateItem;

/**
 * Date: 11.02.2016
 * Time: 13:03
 *
 * @author Savin Mikhail
 */
public interface WriterRules
{
	<E> String updateString(TranslateItem<E> translateItem, E language, final String realString);

	<E> String updateId(TranslateItem<E> translateItem, E language);

	String getComment(String commentString);
}
