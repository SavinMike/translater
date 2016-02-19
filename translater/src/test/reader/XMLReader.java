package test.reader;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import test.model.Resources;
import test.reader.android.AndroidRules;


/**
 * Date: 19.10.2015
 * Time: 10:09
 *
 * @author Savin Mikhail
 */
public class XMLReader implements Reader<Resources>
{

	private Resources mResources;

	@Override
	public Resources readFile(final String file)
	{
		Serializer serializer = new Persister();

		try
		{
			mResources = serializer.read(Resources.class, new File(file));
			return mResources;
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

}
