package test.model.converter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import test.reader.ios.IosString;

public class IosWriteConverter implements WriteConverter<List<IosString>>
{

	@Override
	public Map<String, String> convert(final List<IosString> obj, final Object... params)
	{
		Map<String, String> newValues = new LinkedHashMap<>();
		for (IosString iosString : obj)
		{
			newValues.put(iosString.key, iosString.value);
		}

		return newValues;
	}
}