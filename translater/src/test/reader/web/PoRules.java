package test.reader.web;

import test.reader.ReaderRules;

/**
 * Date: 11.02.2016
 * Time: 10:55
 *
 * @author Savin Mikhail
 */
public class PoRules extends ReaderRules
{
	public static final String NEW_LINE = "\\n";

	private boolean startId = false;
	private boolean startStr = false;

	@Override
	public void checkStringLine(String next)
	{
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
				notifyListener(ActionType.VALUE, next.substring(next.indexOf("\"") + 1, next.lastIndexOf("\"")), next);
			}catch (StringIndexOutOfBoundsException e){
				System.out.println(next);
			}
		}
		else if (next.startsWith(WebString.ID) || startId)
		{
			startId = true;
			startStr = false;

			notifyListener(ActionType.ID, next.substring(next.indexOf("\"") + 1, next.lastIndexOf("\"")), next);
		}
	}
}
