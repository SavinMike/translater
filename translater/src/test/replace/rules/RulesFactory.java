package test.replace.rules;

import test.model.PlatformVariants;
import test.reader.ReaderRules;
import test.reader.android.AndroidRules;
import test.reader.ios.IosRule;
import test.reader.web.PoRules;
import test.replace.UpdateReader;

/**
 * Date: 11.02.2016
 * Time: 16:57
 *
 * @author Savin Mikhail
 */
public class RulesFactory
{
	public static ReaderRules getRules(PlatformVariants platformVariants, UpdateReader<?> translateUpdateReader)
	{
		ReaderRules readerRules = null;
		switch (platformVariants)
		{

			case ANDROID:
				readerRules = new AndroidRules();
				break;
			case IOS:
				readerRules = new IosRule();
				break;
			case WEB:
				readerRules = new PoRules();
				break;
		}

		readerRules.setRulesActionsListener(getActionListener(platformVariants, translateUpdateReader));

		return readerRules;
	}

	public static WriterRules getWriteRules(PlatformVariants platformVariants)
	{
		WriterRules writerRules = null;
		switch (platformVariants)
		{

			case ANDROID:
				writerRules = new AndroidWriterRules();
				break;
			case IOS:
				writerRules = new IosWriterRules();
				break;
			case WEB:
				writerRules = new WebWriterRules();
				break;
		}

		return writerRules;
	}

	private static ReaderRules.RulesActionsListener getActionListener(PlatformVariants platformVariants, UpdateReader<?> translateUpdateReader)
	{
		switch (platformVariants)
		{

			case ANDROID:
				return new AndroidWriterActionListener(translateUpdateReader);
			case IOS:
				return new IosWriterActionListener(translateUpdateReader);
			case WEB:
				return new WebWriterActionListener(translateUpdateReader);
		}

		return null;
	}
}
