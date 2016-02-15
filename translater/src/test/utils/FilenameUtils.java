package test.utils;

import java.io.File;

/**
 * Date: 15.02.2016
 * Time: 12:15
 *
 * @author Savin Mikhail
 */
public class FilenameUtils
{
	/**
	 * The Unix separator character.
	 */
	private static final char UNIX_SEPARATOR = '/';

	/**
	 * The Windows separator character.
	 */
	private static final char WINDOWS_SEPARATOR = '\\';
	/**
	 * The extension separator character.
	 *
	 * @since Commons IO 1.4
	 */
	public static final char EXTENSION_SEPARATOR = '.';

	private static final char SYSTEM_SEPARATOR = File.separatorChar;

	private static final char OTHER_SEPARATOR;

	static
	{
		if (isSystemWindows())
		{
			OTHER_SEPARATOR = UNIX_SEPARATOR;
		}
		else
		{
			OTHER_SEPARATOR = WINDOWS_SEPARATOR;
		}
	}

	static boolean isSystemWindows() {
		return SYSTEM_SEPARATOR == WINDOWS_SEPARATOR;
	}

	/**
	 * The extension separator String.
	 *
	 * @since Commons IO 1.4
	 */
	public static final String EXTENSION_SEPARATOR_STR = (new Character(EXTENSION_SEPARATOR)).toString();

	public static char getSeparator(){
		return OTHER_SEPARATOR;
	}

	public static int indexOfLastSeparator(String filename)
	{
		if (filename == null)
		{
			return -1;
		}
		int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
		int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
		return Math.max(lastUnixPos, lastWindowsPos);
	}


	public static String getBaseName(String filename)
	{
		return removeExtension(getName(filename));
	}

	public static String getName(String filename)
	{
		if (filename == null)
		{
			return null;
		}
		int index = indexOfLastSeparator(filename);
		return filename.substring(index + 1);
	}

	public static String removeExtension(String filename)
	{
		if (filename == null)
		{
			return null;
		}
		int index = indexOfExtension(filename);
		if (index == -1)
		{
			return filename;
		}
		else
		{
			return filename.substring(0, index);
		}
	}

	public static int indexOfExtension(String filename)
	{
		if (filename == null)
		{
			return -1;
		}
		int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
		int lastSeparator = indexOfLastSeparator(filename);
		return (lastSeparator > extensionPos ? -1 : extensionPos);
	}
}
