package com.arellomobile.translater.reader.web;

import com.arellomobile.translater.reader.ReaderRules;
import com.arellomobile.translater.reader.RuleReaderList;

/**
 * Date: 11.02.2016
 * Time: 10:55
 *
 * @author Savin Mikhail
 */
public class PoRules extends ReaderRules
{
	private boolean startId = false;
	private boolean startStr = false;

	@Override
	public void checkStringLine(String next)
	{
		if(next.equals(RuleReaderList.END_OF_FILE)){
			notifyListener(ActionType.END_OF_FILE, null);
			return;
		}

		if (next.trim().isEmpty())
		{
			notifyListener(ActionType.DONE, next);
			startId = false;
			startStr = false;
		}
		else if (next.startsWith("#"))
		{
			startId = false;
			startStr = false;

			notifyListener(ActionType.COMMENT, next);
		}
		else if (next.startsWith(WebString.TRANSLATE) || startStr)
		{
			startStr = true;
			startId = false;

			try
			{
				notifyListener(ActionType.VALUE, next.substring(next.indexOf("\"") + 1, next.lastIndexOf("\"")).replaceAll("\\\\\\\"", ReaderRules.QUOT), next);
			}catch (StringIndexOutOfBoundsException e){
				System.out.println(next);
			}
		}
		else if (next.startsWith(WebString.ID) || startId)
		{
			startId = true;
			startStr = false;

			notifyListener(ActionType.ID, next.substring(next.indexOf("\"") + 1, next.lastIndexOf("\"")).replaceAll("\\\\\\\"", ReaderRules.QUOT), next);
		}
	}
}
