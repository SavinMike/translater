package com.arellomobile.translater.reader.web;

import java.util.List;

/**
 * Date: 10.02.2016
 * Time: 17:10
 *
 * @author Savin Mikhail
 */
public class WebString
{
	public static final String ID = "msgid";
	public static final String TRANSLATE = "msgstr";

	public List<String> comments;
	public String msgid;
	public String msgstr;

	public WebString(final List<String> comments, final String msgid, final String msgstr)
	{
		this.comments = comments;
		this.msgid = msgid;
		this.msgstr = msgstr;
	}

	@Override
	public String toString()
	{
		StringBuilder comment = new StringBuilder();
		for(String s: comments){
			comment.append(s).append("\n");
		}
		return "WebString{" +
				"comments=" + comment +
				", msgid='" + msgid + '\'' +
				", msgstr='" + msgstr + '\'' +
				'}';
	}
}
