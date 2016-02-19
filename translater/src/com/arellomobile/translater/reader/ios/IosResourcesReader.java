package com.arellomobile.translater.reader.ios;

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
public class IosResourcesReader extends RuleReaderList<IosString>
{

	private final ReaderRules iosReaderRules;

	public IosResourcesReader()
	{
		iosReaderRules = new IosRule();
		iosReaderRules.setRulesActionsListener(new ReaderRules.RulesActionsListener()
		{
			List<String> comments = new ArrayList<>();
			String id;
			String value;

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
						value = next.contains(ReaderRules.QUOT) ? "\"" + next.replaceAll(ReaderRules.QUOT, "\"\"") + "\"" : next;
						break;
					case DONE:
						addToList(new IosString(id, value, comments));
						comments.clear();
						break;
				}
			}
		});
	}


	@Override
	protected ReaderRules getReaderRules()
	{
		return iosReaderRules;
	}
}
