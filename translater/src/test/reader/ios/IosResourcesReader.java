package test.reader.ios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import test.reader.Reader;

/**
 * Date: 09.02.2016
 * Time: 15:50
 *
 * @author Savin Mikhail
 */
public class IosResourcesReader implements Reader<List<IosString>>
{
	@Override
	public List<IosString> readFile(final String file)
	{

		List<IosString> result = new ArrayList<>();
		try
		{
			Scanner scanner = new Scanner(new FileInputStream(file), "UTF-8");
			List<String> comments = new ArrayList<>();
			while (scanner.hasNextLine())
			{
				String next = scanner.nextLine();
				if (Pattern.matches("/\\*.*", next))
				{
					comments.add(next);
				}
				else if (Pattern.matches("//.*", next))
				{
					comments.add(next);
				}
				else if (Pattern.matches(".*\\*/$", next))
				{
					comments.add(next);
				}
				else if (Pattern.matches("\".*\" = \".*\";", next))
				{
					String[] split = next.split("\"");
					result.add(new IosString(split[1], split[3], new ArrayList<>(comments)));
					comments.clear();
				} else if(!next.isEmpty()){
					comments.add(next);
				}
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return result;
	}
}
