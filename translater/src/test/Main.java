package test;

import test.reader.ResourceReader;

/**
 * Date: 19.10.2015
 * Time: 9:56
 *
 * @author Savin Mikhail
 */
public class Main
{

	public static void main(String[] args)
	{
		new Replacer("/home/arello/Work/Nandos/android/Nandos2/Nandos/Nandos/src/main/java/com/","/home/arello/Work/Nandos/android/Nandos2/Nandos/Nandos/src/main/res/","takeaway").replace(ResourceReader.Resource.LAYOUT);
	}
}
