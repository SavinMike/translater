package test.reader.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import test.reader.Reader;

/**
 * Date: 10.02.2016
 * Time: 17:09
 *
 * @author Savin Mikhail
 */
public class WebPoReader implements Reader<List<WebString>>
{
	@Override
	public List<WebString> readFile(final String file)
	{

		List<WebString> result = new ArrayList<>();
		try
		{
			Scanner scanner = new Scanner(new FileInputStream(file), "UTF-8");
			List<String> comments = new ArrayList<>();

			StringBuilder msgid = new StringBuilder();
			StringBuilder msgstr = new StringBuilder();

			boolean startId = false;
			boolean startStr = false;
			while (scanner.hasNextLine())
			{
				String next = scanner.nextLine();
				if (next.trim().isEmpty())
				{
					WebString webString = new WebString(new ArrayList<>(comments), msgid.toString(), msgstr.toString());
					result.add(webString);
					System.out.println(webString.toString());

					msgid = new StringBuilder();
					msgstr = new StringBuilder();
					comments.clear();
					startId = false;
					startStr = false;
				} else if (next.startsWith("#"))
				{
					startId = false;
					startStr = false;

					comments.add(next);
				}
				else if (next.startsWith(WebString.TRANSLATE) || startStr)
				{
					startStr = true;
					startId = false;

					msgstr.append(next.substring(next.indexOf("\"") + 1, next.lastIndexOf("\"")));
				}
				else if (next.startsWith(WebString.ID) || startId)
				{
					startId = true;
					startStr = false;

					msgid.append(next.substring(next.indexOf("\"") + 1, next.lastIndexOf("\"")));
				}
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return result;
	}
}
