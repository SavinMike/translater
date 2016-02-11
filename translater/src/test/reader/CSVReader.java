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
public abstract class CSVReader<T, H> implements Reader<List<T>>
{
	private final String mDelimiter;
	private List<T> mResult = new ArrayList<>();
	private LineConverterReader<H> mLineConverterReader;

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
			boolean firstLine = true;
			H history = null;
			while ((line = br.readLine()) != null)
			{
				List<String> stringsList = new ArrayList<>();
				String copyLine = line;
				while (copyLine.contains(mDelimiter)){
					int endIndex = copyLine.indexOf(mDelimiter);
					stringsList.add(copyLine.substring(0, endIndex));
					copyLine = copyLine.substring(endIndex+1);
				}
				String[] strings = stringsList.toArray(new String[stringsList.size()]);
				if (firstLine)
				{
					history = mLineConverterReader.convertHistory(strings);
					firstLine = false;
					continue;
				}
				try
				{
					T byLine = getByLine(strings, history);
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

	public abstract T getByLine(String[] strings, H history) throws IncorrectLineException;

	public CSVReader setLineConverterReader(final LineConverterReader<H> lineConverterReader)
	{
		mLineConverterReader = lineConverterReader;
		return this;
	}
}
