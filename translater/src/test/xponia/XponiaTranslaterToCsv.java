package test.xponia;

import java.util.EnumSet;

import test.TranslaterToCsv;
import test.model.PlatformVariants;
import test.model.converter.AndroidWriteConverter;
import test.model.converter.IosWriteConverter;
import test.model.converter.WebWriteConverter;
import test.reader.XMLReader;
import test.reader.ios.IosResourcesReader;
import test.reader.web.WebPoReader;

/**
 * Date: 19.10.2015
 * Time: 9:56
 *
 * @author Savin Mikhail
 */
public class XponiaTranslaterToCsv
{

	public static void main(String[] args)
	{
		TranslaterToCsv<LanguageEnum, XponiaHistory> translaterToCsv = new TranslaterToCsv<>(LanguageEnum.class, "Xponia/");
		translaterToCsv.setHistory(new XponiaHistory(EnumSet.of(LanguageEnum.DE)));
		translaterToCsv.readAndWriteToCsv(new XMLReader(), new AndroidWriteConverter(), PlatformVariants.ANDROID);
		translaterToCsv.readAndWriteToCsv(new IosResourcesReader(), new IosWriteConverter(), PlatformVariants.IOS);
		translaterToCsv.readAndWriteToCsv(new WebPoReader(), new WebWriteConverter(), PlatformVariants.WEB);
	}
}
