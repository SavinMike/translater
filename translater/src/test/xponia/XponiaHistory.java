package test.xponia;

import java.util.Set;

import test.writer.CsvHistory;

/**
 * Date: 10.02.2016
 * Time: 19:23
 *
 * @author Savin Mikhail
 */
public class XponiaHistory implements CsvHistory<LanguageEnum>
{
	private String id;
	private Set<LanguageEnum> mLanguageEnums;

	public XponiaHistory(final String id, final Set<LanguageEnum> languageEnums)
	{
		this.id = id;
		mLanguageEnums = languageEnums;
	}

	public XponiaHistory(final Set<LanguageEnum> languageEnums)
	{
		mLanguageEnums = languageEnums;
	}

	public String getId()
	{
		return id;
	}

	public Set<LanguageEnum> getLanguageEnums()
	{
		return mLanguageEnums;
	}

	@Override
	public Set<LanguageEnum> getEnumSet()
	{
		return mLanguageEnums;
	}

	@Override
	public String createHistory(final String delimiter)
	{
		StringBuilder stringBuilder = new StringBuilder().append("id").append(delimiter);

		for (LanguageEnum languageEnum : mLanguageEnums)
		{
			String name = languageEnum.name().replaceAll("\\t", "    ");
			stringBuilder.append(name).append(delimiter);
		}

		return stringBuilder.toString();
	}

	@Override
	public boolean contains(final LanguageEnum anEnum)
	{
		return mLanguageEnums.contains(anEnum);
	}
}
