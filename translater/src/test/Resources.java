package test;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.LinkedHashMap;

/**
 * Date: 19.10.2015
 * Time: 10:18
 *
 * @author Savin Mikhail
 */
@Root
public class Resources
{
	@ElementMap(entry="string", key="name", attribute=true, inline=true)
	public LinkedHashMap<String, String> map;

	public Resources () {/*do nothing*/}

	public Resources(final LinkedHashMap<String, String> map)
	{
		this.map = map;
	}

	@Override
	public String toString()
	{
		return "Resources{" +
				"mStringModels=" + map +
				'}';
	}
}
