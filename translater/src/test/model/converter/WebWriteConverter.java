package test.model.converter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import test.path.PlatformPath;
import test.reader.web.WebString;

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
			newValues.put(webString.msgid, webString.msgstr.isEmpty() && params[0] instanceof PlatformPath && ((PlatformPath)params[0]).isDefault() ? webString.msgid : webString.msgstr);
		}

		return newValues;
	}
}