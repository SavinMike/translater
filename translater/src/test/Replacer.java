package test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.reader.ResourceReader;

/**
 * Date: 27.11.2015
 * Time: 15:25
 *
 * @author Savin Mikhail
 */
public class Replacer
{
	private String mJavaClassesPath;
	private String mXmlPath;
	private String mPrefixName;

	public Replacer(final String javaClassesPath, final String xmlPath, final String prefixName)
	{
		mJavaClassesPath = javaClassesPath;
		mXmlPath = xmlPath;
		mPrefixName = prefixName;
	}

	public void replace(ResourceReader.Resource resource)
	{
		List<String> strings = new ArrayList<>(new ResourceReader(resource).readFile(mJavaClassesPath));
		Collection<File> xmlFiles = FileUtils.listFiles(new File(mXmlPath), new String[]{"xml"}, true);
		Map<String, String> replaceMap = new HashMap<>();
		for (File file : xmlFiles)
		{
			for (String s : strings)
			{
				if (s.split("\\.")[2].equals(FilenameUtils.removeExtension(file.getName())))
				{
					String value = replaceFile(file);
					replaceMap.put(s, FilenameUtils.removeExtension(value));
				}

			}
		}

		for (File file : xmlFiles)
		{
			for (Map.Entry<String, String> entry : replaceMap.entrySet())
			{
				replaceIntoFile(file, String.format("@%s/%s", resource.name().toLowerCase(), entry.getKey().split("\\.")[2]), String.format("@%s/%s", resource.name().toLowerCase(), entry.getValue()));
			}
		}

		Collection<File> javaFiles = FileUtils.listFiles(new File(mJavaClassesPath), new String[]{"java"}, true);
		for (File file : javaFiles)
		{
			for (Map.Entry<String, String> entry : replaceMap.entrySet())
			{
				replaceIntoFile(file, entry.getKey(), String.format("R.%s.%s", resource.name().toLowerCase(), entry.getValue()));
			}
		}
	}

	private void replaceIntoFile(final File file, String regex, String replacement)
	{
		Path path = Paths.get(file.getAbsolutePath());
		try
		{
			String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			content = content.replaceAll(regex, replacement);
			Files.write(path, content.getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String replaceFile(final File file)
	{
		String[] split = file.getName().split("frag_");
		StringBuilder filename = new StringBuilder("fragment_");
		for (int i = 0; i < split.length; i++)
		{
			filename.append(split[i]);
//			addDivider(split, filename, i);
//			if (i == 0)
//			{
//				filename.append(mPrefixName);
//				addDivider(split, filename, i);
//			}
		}
		try
		{
			if(file.exists())
			{
				File destFile = new File(file.getParentFile().getPath() + "/" + filename);
				FileUtils.copyFile(file, destFile);
				file.delete();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		System.out.println(filename.toString());
		return filename.toString();
	}

	private void addDivider(final String[] split, final StringBuilder filename, final int i)
	{
		if (i != split.length - 1)
		{
			filename.append("_");
		}
	}
}
