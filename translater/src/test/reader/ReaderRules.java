package test.reader;

/**
 * Date: 11.02.2016
 * Time: 11:04
 *
 * @author Savin Mikhail
 */
public abstract class ReaderRules
{
	public static final String QUOT = "&quot;";

	private RulesActionsListener mRulesActionsListener;

	public abstract void checkStringLine(String next);

	protected void notifyListener(final ActionType done, final String next)
	{
		if (mRulesActionsListener != null)
		{
			mRulesActionsListener.onAction(done, next, next);
		}
	}

	protected void notifyListener(final ActionType done, final String next, String realString)
	{
		if (mRulesActionsListener != null)
		{
			mRulesActionsListener.onAction(done, next, realString);
		}
	}

	public void setRulesActionsListener(final RulesActionsListener rulesActionsListener)
	{
		mRulesActionsListener = rulesActionsListener;
	}

	public interface RulesActionsListener
	{
		void onAction(ActionType actionType, String next, final String realString);
	}

	public enum ActionType
	{
		COMMENT, ID, VALUE, DONE, EMPTY, END_OF_FILE
	}
}
