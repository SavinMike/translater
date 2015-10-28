package test.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import test.exception.IncorrectLineException;

/**
 * Date: 19.10.2015
 * Time: 9:52
 *
 * @author Savin Mikhail
 */
public abstract class CSVReader<T> implements Reader<List<T>>
{
	private final String mDelimiter;
	private List<T> mResult = new ArrayList<>();

	public CSVReader(final String delimiter)
	{
		mDelimiter = delimiter;
	}

	@Override
	public List<T> readFile(final String file)
	{
		BufferedReader br = null;
		String line;
		try
		{
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null)
			{
				String[] strings = line.split(mDelimiter);
				try
				{
					T byLine = getByLine(strings);
					mResult.add(byLine);
				} catch (IncorrectLineException e)
				{
					e.printStackTrace();
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
			return mResult;
		}
	}

	public abstract T getByLine(String[] strings) throws IncorrectLineException;
}
