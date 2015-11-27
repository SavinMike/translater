package test.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 27.11.2015
 * Time: 15:33
 *
 * @author Savin Mikhail
 */
public abstract class PatternReader implements Reader<Collection<String>>
{
	@Override
	public Collection<String> readFile(final String fileString)
	{
		Collection<String> result = new HashSet<>();
		File file = new File(fileString);
		if (!file.isDirectory())
		{
			return getStrings(file, result);
		}

		Collection<File> files = getFiles(file);
		for (File item : files)
		{
			getStrings(item, result);
		}

		return result;
	}

	protected abstract Collection<File> getFiles(final File file);

	private Collection<String> getStrings(final File file, final Collection<String> result)
	{
		BufferedReader br = null;
		String line;
		try
		{
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null)
			{
				for (Pattern pattern : getPatterns())
				{
					Matcher matcher = pattern.matcher(line);
					if (matcher.find())
					{
						add(result, matcher);
						break;
					}
				}
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	private void add(final Collection<String> result, final Matcher matcher)
	{
		String group = matcher.group(0);
		String resource = group.substring(0, group.length() - 1);
		result.add(resource);
		System.out.println(resource);
	}

	public abstract Pattern[] getPatterns();
}
