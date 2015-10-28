package test.writer;

/**
 * Date: 19.10.2015
 * Time: 11:06
 *
 * @author Savin Mikhail
 */
public abstract class Writer<T>
{
	private Class<T> mTClass;

	public Writer(final Class<T> TClass)
	{
		mTClass = TClass;
	}

	protected Class<T> getTClass()
	{
		return mTClass;
	}

	public abstract void writeToFile(T t, String file);
}
