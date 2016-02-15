package test.replace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Date: 10.02.2016
 * Time: 20:09
 *
 * @author Savin Mikhail
 */
public class ValueChanger<U>
{
	private final UpdateReader<U> mReader;

	public ValueChanger(final UpdateReader<U> reader)
	{
		mReader = reader;
	}

	public <E extends Enum<E>> void updateValue(String filePath, U translateItems)
	{
		Path path = Paths.get(filePath);
		mReader.setUpdate(translateItems);
		try
		{
			List<String> content = mReader.readFile(filePath);
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < content.size(); i++)
			{
				String line = content.get(i);
				stringBuilder.append(line);

				if (!"\n".equals(line) && !line.isEmpty() && i < content.size() - 1)
				{
					stringBuilder.append("\n");
				}
			}
			Files.write(path, stringBuilder.toString().getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
