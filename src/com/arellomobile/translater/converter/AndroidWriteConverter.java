package com.arellomobile.translater.converter;

import com.arellomobile.translater.reader.android.AndroidString;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AndroidWriteConverter implements WriteConverter<List<AndroidString>>
{

	@Override
	public Map<String, String> convert(final List<AndroidString> obj, final Object... params)
	{
		Map<String, String> newValues = new LinkedHashMap<>();
		for (AndroidString iosString : obj)
		{
			newValues.put(iosString.key, iosString.value);
		}

		return newValues;
	}
}