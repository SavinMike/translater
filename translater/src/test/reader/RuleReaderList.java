package test.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Date: 11.02.2016
 * Time: 11:10
 *
 * @author Savin Mikhail
 */
public abstract class RuleReaderList<T> implements Reader<List<T>>
{
	private List<T> result = new ArrayList<>();

	@Override
	public List<T> readFile(final String file)
	{
		result.clear();

		try
		{
			Scanner scanner = new Scanner(new FileInputStream(file), "UTF-8");

			while (scanner.hasNextLine())
			{
				String next = scanner.nextLine();
				getReaderRules().checkStringLine(next);

				if(!scanner.hasNextLine()){
					getReaderRules().checkStringLine("");
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
		result.add(item);
	}
}
