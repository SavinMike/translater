package test.replace;

import java.util.ArrayList;
import java.util.List;

import test.model.PlatformVariants;
import test.model.TranslateItem;
import test.replace.rules.RulesFactory;
import test.replace.rules.WriterRules;

/**
 * Date: 11.02.2016
 * Time: 12:14
 *
 * @author Savin Mikhail
 */
public class TranslateUpdateReader<T extends Enum<T>> extends UpdateReader<List<TranslateItem<T>>>
{
	public static final String DEFAULT_STRING = "    <string name=\"%1$s\"></string>";
	private WriterRules mWriterRules;

	private List<TranslateItem<T>> mTranlatedList = new ArrayList<>();
	private final T language;

	public TranslateUpdateReader(PlatformVariants platformVariants, final T language)
	{
		super(platformVariants);

		mWriterRules = RulesFactory.getWriteRules(platformVariants);
		this.language = language;
		setAddEmptyInEnd(false);
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
	public void addNewTranslate(final String next)
	{
		List<TranslateItem<T>> copy = new ArrayList<>(getUpdate());
		copy.removeAll(mTranlatedList);
		boolean isFirst = true;
		for (int i = 0; i < copy.size(); i++)
		{
			TranslateItem<T> item = copy.get(i);
			if (!item.contains(language))
			{
				continue;
			}

			if (!item.getValue(language).isEmpty())
			{
				if (isFirst)
				{
					addToList("\n");
					addToList(mWriterRules.getComment("Added translation"));
					addToList("\n");
					isFirst = false;
				}

				String id = mWriterRules.updateId(item, language);
				if (id != null && !id.isEmpty())
				{
					addToList(id);
				}

				String updateString = mWriterRules.updateString(item, language, String.format(DEFAULT_STRING, item.key));
				addToList(updateString);
				if (i != copy.size() - 1)
				{
					addToList("\n");
				}
			}
		}

		if (next != null)
		{
			addToList(next);
		}
	}
}
