package test.reader.ios;

import java.util.regex.Pattern;

import test.reader.ReaderRules;
import test.reader.RuleReaderList;

/**
 * Date: 11.02.2016
 * Time: 11:04
 *
 * @author Savin Mikhail
 */
public class IosRule extends ReaderRules
{

	@Override
	public void checkStringLine(final String next)
	{
		if(next.equals(RuleReaderList.END_OF_FILE)){
			notifyListener(ActionType.END_OF_FILE, null);
			return;
		}

		if (Pattern.matches("/\\*.*", next))
		{
			notifyListener(ActionType.COMMENT, next);
		}
		else if (Pattern.matches("//.*", next))
		{
			notifyListener(ActionType.COMMENT, next);
		}
		else if (Pattern.matches(".*\\*/$", next))
		{
			notifyListener(ActionType.COMMENT, next);
		}
		else if (Pattern.matches("\".*\"[ ]*=[ ]*\".*\";[ ]*[//]*.*", next))
		{
			String[] split = next.replaceAll("\\\\\\\"", IosRule.QUOT).split("\"");

			notifyListener(ActionType.ID, split[1], null);
			notifyListener(ActionType.VALUE, split[3], null);
			notifyListener(ActionType.DONE, next);
		}
		else if (!next.isEmpty())
		{
			notifyListener(ActionType.COMMENT, next);
		} else {
			notifyListener(ActionType.EMPTY, next);
		}
	}
}
