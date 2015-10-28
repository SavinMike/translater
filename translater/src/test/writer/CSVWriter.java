package test.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Date: 19.10.2015
 * Time: 11:07
 *
 * @author Savin Mikhail
 */
public class CSVWriter<T>
{
	private static final char NEW_LINE_SEPARATOR = '\n';
	private String mDelimiter;
	private Class<T> mTClass;

	public CSVWriter(final String delimiter, final Class<T> tClass)
	{
		mTClass = tClass;
		mDelimiter = delimiter;
	}

	public void writeToFile(final List<T> tList, final String file)
	{
		FileWriter fileWriter = null;

		try
		{
			fileWriter = new FileWriter(file);

			Field[] fields = mTClass.getDeclaredFields();
			for (T t : tList)
			{
				for (Field field : fields)
				{
					field.setAccessible(true);
					try
					{

						Object o = field.get(t);
						fileWriter.append(String.valueOf(o));
						fileWriter.append(mDelimiter);
					} catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
				}
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
		} catch (Exception e)
		{
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally
		{

			try
			{
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e)
			{
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}
}
