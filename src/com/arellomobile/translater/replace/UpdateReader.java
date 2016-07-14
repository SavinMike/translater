package com.arellomobile.translater.replace;

import com.arellomobile.translater.reader.RuleReaderList;
import com.arellomobile.translater.replace.rules.RulesFactory;
import com.arellomobile.translater.model.PlatformVariants;
import com.arellomobile.translater.reader.ReaderRules;

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
		mReaderRules = RulesFactory.getRules(platformVariants, this);
	}


	public U getUpdate()
	{
		return update;
	}

	public void setUpdate(final U update)
	{
		this.update = update;
	}

	public abstract String getUpdatedString(final String key, final String realString);

	@Override
	public ReaderRules getReaderRules()
	{
		return mReaderRules;
	}

	public abstract void addNewTranslate(final String next);


}
