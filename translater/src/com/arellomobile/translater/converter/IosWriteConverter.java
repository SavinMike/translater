package com.arellomobile.translater.converter;

import com.arellomobile.translater.reader.ios.IosString;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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