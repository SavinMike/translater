package test.replace.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.model.TranslateItem;

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
	public <E > String updateString(final TranslateItem<E> translateItem, final E language, final String realString)
	{
		if (realString == null)
		{
			return null;
		}

		String value = translateItem.getValue(language);
		String s = value == null ? "" : value;
		s = s.replaceAll("\\\\'", AMPERSAND).replaceAll("'", "\\\\'").replaceAll(AMPERSAND, "\\\\'");

		Pattern compile;
		if (Pattern.matches("(.*<.*string.*name=\\\".*\\\".*)/>", realString))
		{
			compile = Pattern.compile("(.*<.*string.*name=\\\".*\\\".*)/>");
		}
		else
		{
			compile = Pattern.compile("(.*<.*string.*name=\\\".*\\\".*)>.+");
		}

		Matcher matcher = compile.matcher(realString);
		String result = realString;
		while (matcher.find())
		{
			result = matcher.group(1) + ">" + s + "</string>";
		}

		return result;
	}

	@Override
	public <E > String updateId(final TranslateItem<E> translateItem, final E language)
	{
		return null;
	}

	@Override
	public String getComment(final String commentString)
	{
		return String.format("\t<!--%s-->", commentString);
	}
}
