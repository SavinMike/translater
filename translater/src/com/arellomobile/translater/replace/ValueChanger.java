package com.arellomobile.translater.replace;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Date: 10.02.2016
 * Time: 20:09
 *
 * @author Savin Mikhail
 */
public class ValueChanger<U>
{
	private final UpdateReader<U> mReader;
	private Charset mCharset = Charset.forName("UTF-8");

	public ValueChanger(final UpdateReader<U> reader)
	{
		mReader = reader;
	}

	public void setCharset(final Charset charset)
	{
		mCharset = charset;
	}

	public void updateValue(String filePath, U translateItems)
	{
		Path path = Paths.get(filePath);
		mReader.setUpdate(translateItems);
		mReader.setCharset(mCharset);
		try
		{
			List<String> content = mReader.readFile(filePath);
			Files.write(path, content, mCharset);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
