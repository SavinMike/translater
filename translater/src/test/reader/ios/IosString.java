package test.reader.ios;

import java.util.List;

/**
 * Date: 10.02.2016
 * Time: 16:06
 *
 * @author Savin Mikhail
 */
public class IosString
{
	public final String key;
	public final String value;
	public final List<String> commentLines;

	public IosString(final String key, final String value, final List<String> commentLines)
	{
		this.key = key;
		this.value = value;
		this.commentLines = commentLines;
	}
}
