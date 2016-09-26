package com.arellomobile.translater.replace.rules;

import com.arellomobile.translater.reader.ReaderRules;
import com.arellomobile.translater.replace.UpdateReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidWriterActionListener implements ReaderRules.RulesActionsListener
{
	private String id = "";
	private final UpdateReader<?> mUpdateReader;
	private boolean containsTranslate;
	private String result;

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
				break;
			case ID:
				id = next;
				containsTranslate = mUpdateReader.containsKey(id);
				Matcher matcher = Pattern.compile("(\\s*<\\s*string[\\sA-Za-z_\\\"=]*>)(.*)").matcher(realString);
				while (matcher.find())
				{
					result = matcher.group(1);
				}
				break;
			case VALUE:
				if (!containsTranslate)
				{
					result += next;
				}
				break;
			case EMPTY:
				mUpdateReader.addToList("");
				break;
			case END_OF_FILE:
				mUpdateReader.addNewTranslate(next);
				return;
			case DONE:
				if (containsTranslate)
				{
					result += mUpdateReader.getUpdatedString(id, realString);
				}

				Matcher matches = Pattern.compile("(.*)(<\\s*/\\s*string\\s*>)").matcher(realString);
				while (matches.find())
				{
					result += matches.group(2);
				}

				mUpdateReader.addToList(result);
				break;
		}
	}
}