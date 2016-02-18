package test.replace.rules;

import test.reader.ReaderRules;
import test.replace.UpdateReader;

public class WebWriterActionListener implements ReaderRules.RulesActionsListener
{
	private StringBuilder id = new StringBuilder();
	private StringBuilder str = new StringBuilder();
	private boolean updated = false;
	private final UpdateReader<?> mUpdateReader;

	public WebWriterActionListener(UpdateReader<?> updateReader)
	{
		mUpdateReader = updateReader;
	}

	@Override
	public void onAction(final ReaderRules.ActionType actionType, final String next, final String realString)
	{
		switch (actionType)
		{
			case COMMENT:
				updated = false;
				break;
			case ID:
				updated = false;
				id.append(next);
				break;
			case VALUE:
				if (updated)
				{
					return;
				}

				String updatedString = mUpdateReader.getUpdatedString(id.toString(), realString);

				if (updatedString != null)
				{
					mUpdateReader.addToList(updatedString);
					updated = true;
					return;
				}

				str.append(next);
				break;
			case EMPTY:
			case END_OF_FILE:
				mUpdateReader.addNewTranslate(next);
				return;
			case DONE:
				id.setLength(0);
				str.setLength(0);
				break;
		}

		if (realString != null)
		{
			if (!realString.isEmpty())
			{
				mUpdateReader.addToList(realString);
			}
			else
			{
				mUpdateReader.addToList("");
			}
		}
	}
}