package test.replace.rules;

import test.reader.ReaderRules;
import test.replace.UpdateReader;

public class IosWriterActionListener implements ReaderRules.RulesActionsListener
{
	private String id = new String();
	private String str = new String();
	private final UpdateReader<?> mUpdateReader;

	public IosWriterActionListener(UpdateReader<?> updateReader)
	{
		mUpdateReader = updateReader;
	}

	@Override
	public void onAction(final ReaderRules.ActionType actionType, final String next, final String realString)
	{
		switch (actionType)
		{
			case COMMENT:
				mUpdateReader.addToList(realString);
				break;
			case ID:
				id = next;
				break;
			case VALUE:
				String updatedString = mUpdateReader.getUpdatedString(id);
				str = updatedString == null ? next : updatedString;
				mUpdateReader.addToList(str);
				break;
			case EMPTY:
				mUpdateReader.addToList("\n");
			case DONE:
				break;
		}
	}
}