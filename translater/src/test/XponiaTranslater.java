package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import test.reader.XMLReader;
import test.reader.ios.IosResourcesReader;
import test.reader.ios.IosString;
import test.reader.web.WebPoReader;
import test.reader.web.WebString;
import test.writer.CSVObjectConverter;
import test.writer.CSVWriter;

/**
 * Date: 19.10.2015
 * Time: 9:56
 *
 * @author Savin Mikhail
 */
public class XponiaTranslater
{

	public static void main(String[] args)
	{
		readAndroidFiles();
		readIosFiles();
		readWebFiles();
//
//		analyzeDifferent(androidTranslateItems, iosTranlaterItems);


	}

	private static List<TranslateItem<LanguageWebEnum>> readWebFiles()
	{
		WebPoReader webPoReader = new WebPoReader();

		List<TranslateItem<LanguageWebEnum>> result = new ArrayList<>();

		for (LanguageWebEnum languageEnum : LanguageWebEnum.values())
		{
			List<WebString> webStrings = new ArrayList<>();
			for (String path : languageEnum.getPaths())
			{
				webStrings.addAll(webPoReader.readFile(path));
			}

			Map<String, String> newValues = new LinkedHashMap<>();
			for (WebString webString : webStrings)
			{
				if (webString.msgid.isEmpty())
				{
					continue;
				}
				newValues.put(webString.msgid, webString.msgstr.isEmpty() && languageEnum.equals(LanguageWebEnum.EN) ? webString.msgid : webString.msgstr);
			}

			addTranslation(languageEnum, newValues, result, LanguageWebEnum.class);
		}

		writeCsv(result, "Xponia/web.csv", LanguageWebEnum.class);

		return result;
	}

	private static void analyzeDifferent(final List<TranslateItem<LanguageEnum>> androidTranslateItems, final List<TranslateItem<LanguageEnum>> iosTranlaterItems)
	{
		List<TranslateItem<LanguageEnum>> equalStrings = new ArrayList<>();
		List<TranslateItem<Platform>> nonEqualStrings = new ArrayList<>();

		LanguageEnum en = LanguageEnum.EN;
		for (TranslateItem<LanguageEnum> translaterItem : androidTranslateItems)
		{
			boolean isEqual = false;
			for (TranslateItem<LanguageEnum> iosItem : iosTranlaterItems)
			{
				if (translaterItem.getValue(en).equals(iosItem.getValue(en)))
				{
					equalStrings.add(iosItem);
					isEqual = true;
				}
			}

			if (!isEqual)
			{
				addTranslation(Platform.ANDROID, Collections.singletonMap(translaterItem.key, translaterItem.getValue(en)), nonEqualStrings, Platform.class);
			}
		}

		iosTranlaterItems.removeAll(equalStrings);

		for (TranslateItem<LanguageEnum> translaterItem : iosTranlaterItems)
		{
			addTranslation(Platform.IOS, Collections.singletonMap(translaterItem.key, translaterItem.getValue(en)), nonEqualStrings, Platform.class);
		}

		writeCsv(equalStrings, "Xponia/equal_en.csv", LanguageEnum.class);

		writeCsv(nonEqualStrings, "Xponia/not_equal.csv", Platform.class);
	}

	private static List<TranslateItem<LanguageEnum>> readAndroidFiles()
	{
		XMLReader xmlReader = new XMLReader();
		List<TranslateItem<LanguageEnum>> result = new ArrayList<>();

		for (LanguageEnum languageEnum : LanguageEnum.values())
		{
			Map<String, String> languageValue = new LinkedHashMap<>();
			for (String path : languageEnum.getPaths(PlatformVariants.ANDROID))
			{
				languageValue.putAll(xmlReader.readFile(path).strings);
			}

			addTranslation(languageEnum, languageValue, result, LanguageEnum.class);
		}

		writeCsv(result, "Xponia/android.csv", LanguageEnum.class);

		return result;
	}

	private static List<TranslateItem<LanguageEnum>> readIosFiles()
	{
		IosResourcesReader iosResourcesReader = new IosResourcesReader();
		List<TranslateItem<LanguageEnum>> result = new ArrayList<>();

		for (LanguageEnum languageEnum : LanguageEnum.values())
		{
			List<IosString> languageValue = new ArrayList<>();
			for (String path : languageEnum.getPaths(PlatformVariants.IOS))
			{
				languageValue.addAll(iosResourcesReader.readFile(path));
			}
			Map<String, String> newValues = new LinkedHashMap<>();
			for (IosString iosString : languageValue)
			{
				newValues.put(iosString.key, iosString.value);
			}

			addTranslation(languageEnum, newValues, result, LanguageEnum.class);
		}

		writeCsv(result, "Xponia/ios.csv", LanguageEnum.class);

		return result;
	}

	private static <T extends Enum<T>> void writeCsv(final List<TranslateItem<T>> result, String path, final Class<T> tClass)
	{
		CSVWriter<TranslateItem<T>> csvWriter = new CSVWriter<>("\t", new TypeToken<TranslateItem<T>>()
		{
		}.getType());
		csvWriter.setColumnNameRow(new CSVObjectConverter()
		{
			@Override
			public String convert(final String delimiter)
			{
				StringBuilder stringBuilder = new StringBuilder().append("id").append(delimiter);

				for (T languageEnum : tClass.getEnumConstants())
				{
					String name = languageEnum.name().replaceAll("\\t", "    ");
					stringBuilder.append(name).append(delimiter);
				}

				return stringBuilder.toString();
			}
		});

		csvWriter.writeToFile(result, path);
	}

	private static <T extends Enum<T>> void addTranslation(T language, final Map<String, String> strings, final List<TranslateItem<T>> result, Class<T> tClass)
	{
		for (Map.Entry<String, String> entry : strings.entrySet())
		{
			boolean added = false;
			for (TranslateItem<T> translateItem : result)
			{
				if (translateItem.key.equals(entry.getKey()))
				{
					added = true;
					translateItem.addColumn(language, entry.getValue());
				}
			}

			if (!added)
			{
				TranslateItem<T> item = new TranslateItem<>(entry.getKey(), tClass);
				item.addColumn(language, entry.getValue());
				result.add(item);
			}
		}
	}

	public enum LanguageWebEnum
	{
		EN
				{
					@Override
					public String[] getPaths()
					{
						return new String[]{PlatformVariants.WEB.basePath + "django_en.po", PlatformVariants.WEB.basePath + "djangojs_en.po"};
					}
				},
		DE
				{
					@Override
					public String[] getPaths()
					{
						return new String[]{PlatformVariants.WEB.basePath + "django_de.po", PlatformVariants.WEB.basePath + "djangojs_de.po"};
					}
				};

		public abstract String[] getPaths();
	}

	public enum LanguageEnum
	{
		EN
				{
					@Override
					public String[] getPaths(final PlatformVariants platformVariants)
					{
						switch (platformVariants)
						{
							case IOS:
								return new String[]{platformVariants.basePath + "Localizable_en"};
							case ANDROID:
								return new String[]{platformVariants.basePath + "strings_en.xml"};
							case WEB:
								return new String[]{platformVariants.basePath + "django_en.po"};
							default:
								return new String[]{};
						}
					}
				},
		HU
				{
					@Override
					public String[] getPaths(final PlatformVariants platformVariants)
					{
						switch (platformVariants)
						{
							case IOS:
								return new String[]{platformVariants.basePath + "Localizable_hu"};
							case ANDROID:
								return new String[]{platformVariants.basePath + "strings_hu.xml"};
							case WEB:
							default:
								return new String[]{};
						}
					}
				},
		DE
				{
					@Override
					public String[] getPaths(final PlatformVariants platformVariants)
					{
						switch (platformVariants)
						{
							case IOS:
								return new String[]{platformVariants.basePath + "Localizable_de"};
							case ANDROID:
								return new String[]{platformVariants.basePath + "strings_de.xml"};
							case WEB:
							default:
								return new String[]{};
						}
					}
				};
//
//		DEMO_DE
//				{
//					@Override
//					public String[] getPaths(final PlatformVariants platformVariants)
//					{
//						switch (platformVariants)
//						{
//							case IOS:
//								return new String[]{platformVariants.basePath + "Demo_de.strings"};
//							case ANDROID:
//							case WEB:
//							default:
//								return new String[]{};
//						}
//					}
//				},
//		ALLEE_DE
//				{
//					@Override
//					public String[] getPaths(final PlatformVariants platformVariants)
//					{
//						switch (platformVariants)
//						{
//							case IOS:
//								return new String[]{platformVariants.basePath + "Allee_de.strings"};
//							case ANDROID:
//							case WEB:
//							default:
//								return new String[]{};
//						}
//					}
//				},
//		DEMO_EN
//				{
//					@Override
//					public String[] getPaths(final PlatformVariants platformVariants)
//					{
//						switch (platformVariants)
//						{
//							case IOS:
//								return new String[]{platformVariants.basePath + "Demo_en.strings"};
//							case ANDROID:
//							case WEB:
//							default:
//								return new String[]{};
//						}
//					}
//				},
//		ALLEE_EN
//				{
//					@Override
//					public String[] getPaths(final PlatformVariants platformVariants)
//					{
//						switch (platformVariants)
//						{
//							case IOS:
//								return new String[]{platformVariants.basePath + "Allee_en.strings"};
//							case ANDROID:
//							case WEB:
//							default:
//								return new String[]{};
//						}
//					}
//				},;

		public abstract String[] getPaths(final PlatformVariants platformVariants);
	}

	public enum PlatformVariants
	{
		ANDROID("Xponia/android/"), IOS("Xponia/ios/"), WEB("Xponia/web/");

		public String basePath;

		PlatformVariants(final String basePath)
		{
			this.basePath = basePath;
		}
	}

	public enum Platform
	{
		ANDROID, IOS
	}
}
