package test.model.converter;

import java.util.Map;

import test.model.Resources;

public class AndroidWriteConverter implements WriteConverter<Resources>
{

	@Override
	public Map<String, String> convert(final Resources obj, final Object... params)
	{
		return obj.strings;
	}
}