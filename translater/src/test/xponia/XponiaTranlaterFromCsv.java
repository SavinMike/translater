package test.xponia;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import test.exception.IncorrectLineException;
import test.model.PlatformVariants;
import test.model.TranslateItem;
import test.reader.CSVReader;
import test.reader.LineConverterReader;
import test.replace.TranslateUpdateReader;
import test.replace.ValueChanger;
import test.replace.rules.WebWriterRules;

/**
 * Date: 10.02.2016
 * Time: 18:28
 *
 * @author Savin Mikhail
 */
public class XponiaTranlaterFromCsv
{

	public static void main(String[] args)
	{

		CSVReader<TranslateItem<LanguageEnum>, XponiaHistory> csvReader = new CSVReader<TranslateItem<LanguageEnum>, XponiaHistory>("\t")
		{
			@Override
			public TranslateItem<LanguageEnum> getByLine(final String[] strings, final XponiaHistory history) throws IncorrectLineException
			{
				TranslateItem<LanguageEnum> translateItem = new TranslateItem<>(updateCsvString(strings[0]), LanguageEnum.class);
				Iterator<LanguageEnum> languageEnums = history.getLanguageEnums().iterator();
				for (int i = 1; i < strings.length; i++)
				{
					translateItem.addColumn(languageEnums.next(), updateCsvString(strings[i]));
				}
				return translateItem;
			}

			private String updateCsvString(final String string)
			{
				if (string.startsWith("\""))
				{
					String s = string.replaceFirst("\"", "");
					return s.substring(0, s.lastIndexOf("\"")).replaceAll("\"\"", "\\\\\"");
				}

				return string;
			}
		};

		csvReader.setLineConverterReader(new LineConverterReader<XponiaHistory>()
		{
			@Override
			public XponiaHistory convertHistory(final String[] history)
			{
				Set<LanguageEnum> enumSet = new LinkedHashSet<>();
				for (String s : history)
				{
					for (LanguageEnum languageEnum : LanguageEnum.values())
					{
						if (languageEnum.name().equals(s))
						{
							enumSet.add(languageEnum);
						}
					}
				}

				return new XponiaHistory(enumSet);
			}
		});

		List<TranslateItem<LanguageEnum>> translateItems = csvReader.readFile("Xponia/csv/web.csv");

		new ValueChanger<>(new TranslateUpdateReader<>(PlatformVariants.WEB, new WebWriterRules(), LanguageEnum.DE)).updateValue("/home/arello/Work/translaterGit/translater/Xponia/web/django_de_test.po", translateItems);

//		List<TranslateItem<LanguageEnum>> translateItems = csvReader.readFile("Xponia/csv/ios.csv");
//
//		new ValueChanger<>(new TranslateUpdateReader<>(PlatformVariants.IOS, new IosWriterRules(), LanguageEnum.DE)).updateValue("/home/arello/Work/translaterGit/translater/Xponia/ios/Localizable_de_test.txt", translateItems);

	}
}
