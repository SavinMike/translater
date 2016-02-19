package com.arellomobile.translater.creator;

import com.arellomobile.translater.replace.rules.AndroidWriterRules;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Date: 19.02.2016
 * Time: 9:15
 *
 * @author Savin Mikhail
 */
public class AndroidCreatorLocalizeFile implements CreatorLocalizeFile
{

	@Override
	public boolean createFile(final File file, String charset)
	{
		try
		{
			//noinspection ResultOfMethodCallIgnored
			file.getParentFile().mkdirs();
			boolean result = file.createNewFile();
			if (result)
			{
				PrintWriter printWriter = new PrintWriter(file, charset);
				printWriter.println(new AndroidWriterRules().getComment(HEADER).trim());
				printWriter.println("<resources>");
				printWriter.println("</resources>");
				printWriter.close();
			}
			return result;
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return false;
	}
}
