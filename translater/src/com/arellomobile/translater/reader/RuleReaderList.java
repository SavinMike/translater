package com.arellomobile.translater.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Date: 11.02.2016
 * Time: 11:10
 *
 * @author Savin Mikhail
 */
public abstract class RuleReaderList<T> implements Reader<List<T>>
{
	public static final String END_OF_FILE = "${END_OF_FILE}";
	private List<T> result = new ArrayList<>();
	private Charset mCharset = Charset.forName("UTF-8");

	private Map<String, Charset> mCharsetMap = new HashMap<>();

	public void setCharsetMap(final Map<String, Charset> charsetMap)
	{
		mCharsetMap = charsetMap;
	}

	public RuleReaderList setCharset(final Charset charset)
	{
		mCharset = charset;
		return this;
	}

	@Override
	public List<T> readFile(final String file)
	{
		result.clear();

		try
		{
			Charset charset = mCharset;
			for (Map.Entry<String, Charset> entry : mCharsetMap.entrySet())
			{
				if (file.contains(entry.getKey()))
				{
					charset = entry.getValue();
					break;
				}
			}

			Scanner scanner = new Scanner(new FileInputStream(file), charset.name());

			while (scanner.hasNextLine())
			{
				String next = scanner.nextLine();
				getReaderRules().checkStringLine(next);

				if (!scanner.hasNextLine())
				{
					getReaderRules().checkStringLine(END_OF_FILE);
				}
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return new ArrayList<>(result);
	}

	protected abstract ReaderRules getReaderRules();

	public void addToList(T item)
	{
		if (item != null)
		{
			result.add(item);
		}
	}
}
