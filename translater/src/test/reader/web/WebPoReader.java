package test.reader.web;

import java.util.ArrayList;
import java.util.List;

import test.reader.ReaderRules;
import test.reader.RuleReaderList;

/**
 * Date: 10.02.2016
 * Time: 17:09
 *
 * @author Savin Mikhail
 */
public class WebPoReader extends RuleReaderList<WebString>
{

	private final ReaderRules mReaderRules;

	public WebPoReader()
	{
		mReaderRules = new PoRules();
		mReaderRules.setRulesActionsListener(new PoRules.RulesActionsListener()
		{
			final List<String> comments = new ArrayList<>();

			final StringBuilder msgid = new StringBuilder();
			final StringBuilder msgstr = new StringBuilder();

			@Override
			public void onAction(final PoRules.ActionType actionType, final String next, final String realString)
			{
				switch (actionType)
				{

					case COMMENT:
						comments.add(next);
						break;
					case ID:
						msgid.append(next);
						break;
					case VALUE:
						msgstr.append(next);
						break;
					case DONE:
						WebString webString = new WebString(new ArrayList<>(comments), msgid.toString(), msgstr.toString());
						addToList(webString);
						System.out.println(webString.toString());

						msgid.setLength(0);
						msgstr.setLength(0);
						comments.clear();
						break;
				}
			}
		});
	}

	@Override
	protected ReaderRules getReaderRules()
	{
		return mReaderRules;
	}
}
