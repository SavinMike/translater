package test.xponia;

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

//		CSVReader<TranslateItem<LanguageEnum>, XponiaHistory> csvReader = new CSVReader<TranslateItem<LanguageEnum>, XponiaHistory>("\t")
//		{
//			@Override
//			public TranslateItem<LanguageEnum> getByLine(final String[] strings, final XponiaHistory history) throws IncorrectLineException
//			{
//				TranslateItem<LanguageEnum> translateItem = new TranslateItem<>(updateCsvString(strings[0]), LanguageEnum.class);
//				Iterator<LanguageEnum> languageEnums = history.getLanguageEnums().iterator();
//				for (int i = 1; i < strings.length; i++)
//				{
//					translateItem.addColumn(languageEnums.next(), updateCsvString(strings[i]));
//				}
//				return translateItem;
//			}
//
//			private String updateCsvString(final String string)
//			{
//				if (string.startsWith("\""))
//				{
//					String s = string.replaceFirst("\"", "");
//					return s.substring(0, s.lastIndexOf("\"")).replaceAll("\"\"", ReaderRules.QUOT);
//				}
//
//				return string;
//			}
//		};
//
//		csvReader.setLineConverterReader(new LineConverterReader<XponiaHistory>()
//		{
//			@Override
//			public XponiaHistory convertHistory(final String[] history)
//			{
//				Set<LanguageEnum> enumSet = new LinkedHashSet<>();
//				for (String s : history)
//				{
//					for (LanguageEnum languageEnum : LanguageEnum.values())
//					{
//						if (languageEnum.name().equals(s))
//						{
//							enumSet.add(languageEnum);
//						}
//					}
//				}
//
//				return new XponiaHistory(enumSet);
//			}
//		});
//
//		TranlaterFromCsv<LanguageEnum, XponiaHistory> tranlaterFromCsv = new TranlaterFromCsv<>("Xponia/", LanguageEnum.class);
//		for (PlatformVariants platformVariants : EnumSet.allOf(PlatformVariants.class))
//		{
//			tranlaterFromCsv.readFromCsv(csvReader, platformVariants);
//		}
	}
}
