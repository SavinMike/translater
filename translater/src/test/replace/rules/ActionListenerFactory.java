package test.replace.rules;

import test.model.PlatformVariants;
import test.reader.ReaderRules;
import test.reader.ios.IosRule;
import test.reader.web.PoRules;
import test.replace.UpdateReader;

/**
 * Date: 11.02.2016
 * Time: 16:57
 *
 * @author Savin Mikhail
 */
public class ActionListenerFactory
{
	public static ReaderRules getRules(PlatformVariants platformVariants, UpdateReader<?> translateUpdateReader)
	{
		ReaderRules readerRules = null;
		switch (platformVariants)
		{

			case ANDROID:
				break;
			case IOS:
				readerRules = new IosRule();
				break;
			case WEB:
				readerRules = new PoRules();
				break;
		}

		if (readerRules != null)
		{
			readerRules.setRulesActionsListener(getActionListener(platformVariants, translateUpdateReader));
		}

		return readerRules;
	}

	private static ReaderRules.RulesActionsListener getActionListener(PlatformVariants platformVariants, UpdateReader<?> translateUpdateReader)
	{
		switch (platformVariants)
		{

			case ANDROID:
				break;
			case IOS:
				return new IosWriterActionListener(translateUpdateReader);
			case WEB:
				return new WebWriterActionListener(translateUpdateReader);
		}

		return null;
	}
}
