package test.writer;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

/**
 * Date: 19.10.2015
 * Time: 11:31
 *
 * @author Savin Mikhail
 */
public class XmlWriter<T> extends Writer<T>
{
	public XmlWriter(final Class<T> TClass)
	{
		super(TClass);
	}

	@Override
	public void writeToFile(final T t, final String fileName)
	{
		File file = new File(fileName);
		Serializer serializer = new Persister();
		try
		{
			serializer.write(t, file);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
