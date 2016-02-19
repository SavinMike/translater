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
		if(next.equals(RuleReaderList.END_OF_FILE)){
			return;
		}

		if (Pattern.matches("[ ]*<[ ]*/[ ]*resources[ ]*>[ ]*", next))
		{
			notifyListener(ActionType.END_OF_FILE, next);
			return;
		}

		if (Pattern.matches(".*<[ ]*string.*", next))
		{
			isMessage = true;
			Pattern pattern = Pattern.compile("name[ ]*=[ ]*\\\"([\\w\\d_]*)\\\"");
			notifyIfContains(ActionType.ID, next, pattern);
		}

		if (next.isEmpty())
		{
			notifyListener(ActionType.EMPTY, next);
			return;
		}

		if (Pattern.matches(".*<[ ]*/[ ]*string[ ]*>[ ]*", next))
		{
			isMessage = false;
			Pattern pattern = Pattern.compile("name[ ]*=[ ]*\\\".*\\\".*>(.*)</string>");
			notifyIfContains(ActionType.VALUE, next, pattern);
			notifyListener(ActionType.DONE, next);
		}
		else
		{
			if (isMessage)
			{
				if (Pattern.matches(".*< *string.*/>", next))
				{
					notifyListener(ActionType.VALUE, "", next);
					notifyListener(ActionType.DONE, next);
					isMessage = false;
				}
				else
				{
					Pattern pattern = Pattern.compile("name[ ]*=[ ]*\\\".*\\\".*>(.*)");
					notifyIfContains(ActionType.VALUE, next, pattern);
				}
			}
			else
			{
				notifyListener(ActionType.COMMENT, next);
			}
		}
	}

	private void notifyIfContains(ActionType actionType, final String next, final Pattern pattern)
	{
		Matcher matcher = pattern.matcher(next);
		while (matcher.find())
		{
			notifyListener(actionType, matcher.group(1), next);
		}
	}


}
