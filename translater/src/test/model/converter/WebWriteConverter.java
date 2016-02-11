package test.model.converter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import test.reader.web.WebString;
import test.xponia.LanguageEnum;

public class WebWriteConverter implements WriteConverter<List<WebString>>
{

	@Override
	public Map<String, String> convert(final List<WebString> obj, final Object... params)
	{
		Map<String, String> newValues = new LinkedHashMap<>();
		for (WebString webString : obj)
		{
			if (webString.msgid.isEmpty())
			{
				continue;
			}
			newValues.put(webString.msgid, webString.msgstr.isEmpty() && LanguageEnum.EN.equals(params[0]) ? webString.msgid : webString.msgstr);
		}

		return newValues;
	}
}