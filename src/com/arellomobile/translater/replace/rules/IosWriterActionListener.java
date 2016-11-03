package com.arellomobile.translater.replace.rules;

import com.arellomobile.translater.reader.ReaderRules;
import com.arellomobile.translater.replace.UpdateReader;

public class IosWriterActionListener implements ReaderRules.RulesActionsListener
{
	private String id = "";
	private String str = "";
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
				String updatedString = mUpdateReader.getUpdatedString(id, realString);
				str = updatedString == null ? String.format("\"%s\" = \"%s\";", id, next) : updatedString;
				mUpdateReader.addToList(str);
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