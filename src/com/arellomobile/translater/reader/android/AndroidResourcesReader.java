package com.arellomobile.translater.reader.android;

import com.arellomobile.translater.reader.ReaderRules;
import com.arellomobile.translater.reader.RuleReaderList;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 09.02.2016
 * Time: 15:50
 *
 * @author Savin Mikhail
 */
public class AndroidResourcesReader extends RuleReaderList<AndroidString>
{

	private final ReaderRules mAndroidRules;

	public AndroidResourcesReader()
	{
		mAndroidRules = new AndroidRules();
		mAndroidRules.setRulesActionsListener(new ReaderRules.RulesActionsListener()
		{
			List<String> comments = new ArrayList<>();
			String id;
			StringBuilder value = new StringBuilder();

			@Override
			public void onAction(final ReaderRules.ActionType actionType, final String next, final String realString)
			{
				switch (actionType)
				{

					case COMMENT:
						comments.add(next);
						break;
					case ID:
						id = next.contains(ReaderRules.QUOT) ? "\"" + next.replaceAll(ReaderRules.QUOT, "\"\"") + "\"" : next;
						break;
					case VALUE:
						if (!value.toString().isEmpty())
						{
							value.append(" ");
						}
						value.append(next.contains(ReaderRules.QUOT) ? "\"" + next.replaceAll(ReaderRules.QUOT, "\"\"") + "\"" : next);
						break;
					case DONE:
						addToList(new AndroidString(id, value.toString(), comments));
						value = new StringBuilder();
						comments.clear();
						break;
				}
			}
		});
	}


	@Override
	protected ReaderRules getReaderRules()
	{
		return mAndroidRules;
	}
}
