package com.arellomobile.translater.reader.android;

import com.arellomobile.translater.reader.ReaderRules;
import com.arellomobile.translater.reader.RuleReaderList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 12.02.2016
 * Time: 9:53
 *
 * @author Savin Mikhail
 */
public class AndroidRules extends ReaderRules
{

	private boolean isMessage = false;

	@Override
	public void checkStringLine(final String next)
	{
		boolean id = false;
		if (next.equals(RuleReaderList.END_OF_FILE))
		{
			return;
		}

		if (Pattern.matches("\\s*<\\s*/\\s*resources\\s*>\\s*", next))
		{
			notifyListener(ActionType.END_OF_FILE, next);
			return;
		}

		if (Pattern.matches(".*<\\s*string.*", next))
		{
			isMessage = true;
			id = true;
			Pattern pattern = Pattern.compile("name\\s*=\\s*\\\"([\\w\\d_]*)\\\"");
			notifyIfContains(ActionType.ID, next, pattern);
		}

		if (next.isEmpty())
		{
			notifyListener(ActionType.EMPTY, next);
			return;
		}

		if (Pattern.matches(".*<\\s*/\\s*string\\s*>\\s*", next))
		{
			isMessage = false;
			Pattern pattern;

			if (id)
			{
				pattern = Pattern.compile("name\\s*=[\\w\\d_\"]*>(.*)</string>");
			}
			else
			{
				pattern = Pattern.compile("(.*)</string>");
			}

			notifyIfContains(ActionType.VALUE, next, pattern);
			notifyListener(ActionType.DONE, next);
		}
		else
		{
			if (isMessage)
			{
				if (Pattern.matches(".*<\\s*string.*/>", next))
				{
					notifyListener(ActionType.VALUE, "", next);
					notifyListener(ActionType.DONE, next);
					isMessage = false;
				}
				else
				{
					if (!id)
					{
						notifyListener(ActionType.VALUE, next);
					}
					else
					{
						notifyIfContains(ActionType.VALUE, next, Pattern.compile("\\s*<\\s*string [\\w\\d_\"=\\s]*>(.*)"));
					}
				}
			}
			else
			{
				notifyListener(ActionType.COMMENT, next);
			}
		}
	}

	private boolean notifyIfContains(ActionType actionType, final String next, final Pattern pattern)
	{
		Matcher matcher = pattern.matcher(next);
		boolean updated = false;
		while (matcher.find())
		{
			notifyListener(actionType, matcher.group(1), next);
			updated = true;
		}

		return updated;
	}


}
