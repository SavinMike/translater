package com.arellomobile.translater.reader.android;

import java.util.List;

/**
 * Date: 10.02.2016
 * Time: 16:06
 *
 * @author Savin Mikhail
 */
public class AndroidString
{
	public final String key;
	public final String value;
	public final List<String> commentLines;

	public AndroidString(final String key, final String value, final List<String> commentLines)
	{
		this.key = key;
		this.value = value;
		this.commentLines = commentLines;
	}
}
