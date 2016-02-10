package test.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
	private Class<? extends T> mTClass;
	private Type mTypeOfT;
	private CSVObjectConverter mColumnNameRow;

	public CSVWriter(final String delimiter, final Class<? extends T> tClass)
	{
		mTClass = tClass;
		mDelimiter = delimiter;
	}

	public CSVWriter(final String delimiter, final Type typeofT)
	{
		mTypeOfT = typeofT;
		mTClass = (Class<? extends T>) ((ParameterizedType) typeofT).getRawType();
		mDelimiter = delimiter;
	}

	public void writeToFile(final List<T> tList, final String file)
	{
		FileWriter fileWriter = null;

		try
		{
			fileWriter = new FileWriter(file);

			if (mColumnNameRow != null)
			{
				fileWriter.append(mColumnNameRow.convert(mDelimiter)).append(NEW_LINE_SEPARATOR);
			}

			Field[] fields = mTClass.getDeclaredFields();
			for (T t : tList)
			{
				if (t instanceof CSVObjectConverter)
				{
					fileWriter.append(((CSVObjectConverter) t).convert(mDelimiter));
				}
				else
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
				if(fileWriter!=null)
				{
					fileWriter.flush();
					fileWriter.close();
				}
			} catch (IOException e)
			{
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

	public CSVWriter setColumnNameRow(final CSVObjectConverter columnNameRow)
	{
		mColumnNameRow = columnNameRow;
		return this;
	}
}
