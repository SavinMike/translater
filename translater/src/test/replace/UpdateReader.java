package test.replace;

import test.model.PlatformVariants;
import test.reader.ReaderRules;
import test.reader.RuleReaderList;
import test.replace.rules.ActionListenerFactory;

/**
 * Date: 11.02.2016
 * Time: 12:13
 *
 * @author Savin Mikhail
 */
public abstract class UpdateReader<U> extends RuleReaderList<String>
{
	private U update;
	private ReaderRules mReaderRules;

	public UpdateReader(PlatformVariants platformVariants)
	{
		mReaderRules = ActionListenerFactory.getRules(platformVariants, this);
	}


	public U getUpdate()
	{
		return update;
	}

	public void setUpdate(final U update)
	{
		this.update = update;
	}

	public abstract String getUpdatedString(final String key);

	@Override
	public ReaderRules getReaderRules()
	{
		return mReaderRules;
	}


}
