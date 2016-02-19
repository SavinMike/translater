package test.creator;

import java.io.File;

/**
 * Date: 18.02.2016
 * Time: 17:30
 *
 * @author Savin Mikhail
 */
public interface CreatorLocalizeFile
{
	String HEADER = "New File";

	boolean createFile(File file, String charset);
}
