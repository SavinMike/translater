package test.creator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import test.replace.rules.IosWriterRules;

/**
 * Date: 19.02.2016
 * Time: 9:18
 *
 * @author Savin Mikhail
 */
public class IosCreatorLocalizeFile implements CreatorLocalizeFile
{

	@Override
	public boolean createFile(final File file, String charset)
	{
		try
		{
			//noinspection ResultOfMethodCallIgnored
			file.getParentFile().mkdirs();
			boolean result = file.createNewFile();
			if(result){
				PrintWriter printWriter = new PrintWriter(file, charset);
				printWriter.println(new IosWriterRules().getComment("HEADER"));
				printWriter.close();
			}
			return result;
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return false;
	}
}
