package com.arellomobile.translater.replace.rules;

import com.arellomobile.translater.model.TranslateItem;

/**
 * Date: 12.02.2016
 * Time: 12:46
 *
 * @author Savin Mikhail
 */
public class AndroidWriterRules implements WriterRules
{
	public static final String AMPERSAND = "&ampersand;";


	@Override
	public <E> String updateString(final TranslateItem<E> translateItem, final E language, final String realString)
	{
		if (realString == null)
		{
			return null;
		}

		String value = translateItem.getValue(language);
		String s = value == null ? "" : value;
		s = s.replaceAll("\\\\'", AMPERSAND).replaceAll("'", "\\\\'").replaceAll(AMPERSAND, "\\\\'");

		return s;
	}

	@Override
	public <E> String updateId(final TranslateItem<E> translateItem, final E language)
	{
		return "\t<string name=\"" + translateItem.key + "\">";
	}

	@Override
	public String getComment(final String commentString)
	{
		return String.format("\t<!--%s-->", commentString);
	}

	@Override
	public <E> String addStringSuffix(final TranslateItem<E> translateItem, final E language)
	{
		return "</string>";
	}
}
