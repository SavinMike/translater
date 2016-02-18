package test.replace.rules;

import test.reader.ReaderRules;
import test.replace.UpdateReader;

public class AndroidWriterActionListener implements ReaderRules.RulesActionsListener
{
	private String id = "";
	private boolean updated = false;
	private final UpdateReader<?> mUpdateReader;

	public AndroidWriterActionListener(UpdateReader<?> updateReader)
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
				updated = false;
				break;
			case ID:
				updated = false;
				id = next;
				break;
			case VALUE:
				if (updated)
				{
					return;
				}

				String updatedString = mUpdateReader.getUpdatedString(id, realString);

				if (updatedString == null || !updatedString.equals(realString))
				{
					updated = true;
				}

				mUpdateReader.addToList(updatedString);
				break;
			case EMPTY:
				mUpdateReader.addToList("");
				break;
			case END_OF_FILE:
				mUpdateReader.addNewTranslate(next);
				return;
			case DONE:
				break;
		}
	}
}