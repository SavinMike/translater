package com.arellomobile.translater.replace;

import com.arellomobile.translater.model.PlatformVariants;
import com.arellomobile.translater.model.TranslateItem;
import com.arellomobile.translater.replace.rules.RulesFactory;
import com.arellomobile.translater.replace.rules.WriterRules;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 11.02.2016
 * Time: 12:14
 *
 * @author Savin Mikhail
 */
public class TranslateUpdateReader<T> extends UpdateReader<List<TranslateItem<T>>>
{
	public static final String DEFAULT_STRING = "\t<string name=\"%1$s\"></string>";
	private WriterRules mWriterRules;

	private List<TranslateItem<T>> mTranlatedList = new ArrayList<>();
	private final T language;

	public TranslateUpdateReader(PlatformVariants platformVariants, final T language)
	{
		super(platformVariants);

		mWriterRules = RulesFactory.getWriteRules(platformVariants);
		this.language = language;
	}

	@Override
	public String getUpdatedString(final String key, final String realString)
	{
		for (TranslateItem<T> translateItem : getUpdate())
		{
			if (translateItem.key.equals(key) && translateItem.contains(language))
			{
				mTranlatedList.add(translateItem);
				return mWriterRules.updateString(translateItem, language, realString);
			}
		}

		return null;
	}

	@Override
	public boolean containsKey(final String key)
	{
		for (TranslateItem<T> translateItem : getUpdate())
		{
			if (translateItem.key.equals(key) && translateItem.contains(language))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void addNewTranslate(final String next)
	{
		List<TranslateItem<T>> copy = new ArrayList<>(getUpdate());
		copy.removeAll(mTranlatedList);
		boolean isFirst = true;
		for (TranslateItem<T> item : copy)
		{
			if (!item.contains(language))
			{
				continue;
			}

			if (!item.getValue(language).isEmpty())
			{
				if (isFirst)
				{
					addToList("");
					addToList(mWriterRules.getComment("Added translation"));
					addToList("");
					isFirst = false;
				}

				String id = mWriterRules.updateId(item, language);
				String result = "";
				if (id != null && !id.isEmpty())
				{
					result += id;
				}

				result += mWriterRules.updateString(item, language, String.format(DEFAULT_STRING, item.key)) + mWriterRules.addStringSuffix(item, language);
				addToList(result);
			}
		}

		if (next != null)
		{
			addToList(next);
		}
	}
}
