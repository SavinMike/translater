package test.replace;

import java.util.List;

import test.model.PlatformVariants;
import test.model.TranslateItem;
import test.replace.rules.WriterRules;

/**
 * Date: 11.02.2016
 * Time: 12:14
 *
 * @author Savin Mikhail
 */
public class TranslateUpdateReader<T extends Enum<T>> extends UpdateReader<List<TranslateItem<T>>>
{
	private WriterRules mWriterRules;

	private final T language;

	public TranslateUpdateReader(PlatformVariants platformVariants, final WriterRules writerRules, final T language)
	{
		super(platformVariants);

		mWriterRules = writerRules;
		this.language = language;
	}

	@Override
	public String getUpdatedString(final String key)
	{
		for (TranslateItem<T> translateItem : getUpdate())
		{
			if (translateItem.key.equals(key) && translateItem.contains(language))
			{
				return mWriterRules.updateString(translateItem, language);
			}
		}

		return null;
	}
}
