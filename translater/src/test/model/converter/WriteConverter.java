package test.model.converter;

import java.util.Map;

/**
 * Date: 10.02.2016
 * Time: 18:34
 *
 * @author Savin Mikhail
 */
public interface WriteConverter<T>
{
	Map<String, String> convert(T obj, Object... params);
}
