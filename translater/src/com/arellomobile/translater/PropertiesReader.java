package com.arellomobile.translater;

import com.arellomobile.translater.model.Config;
import com.arellomobile.translater.model.PlatformVariants;
import com.arellomobile.translater.reader.Reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Date: 16.02.2016
 * Time: 13:09
 *
 * @author Savin Mikhail
 */
public class PropertiesReader implements Reader<Config>
{
	public static final String LOCATION = "%s_LOCATION";
	public static final String EXCLUDING = "%s_EXCLUDING";
	public static final String INCLUDING = "%s_INCLUDING";
	public static final String CHARSET = "%s_CHARSET";
	public static final String CSV_PATH = "CSV_PATH";
	public static final String DELIMITER = ";";
	public static final String DEFAULT_LOCATION = "%s_DEFAULT_LOCATION";


	@Override
	public Config readFile(final String file)
	{
		Properties prop = new Properties();
		InputStream input = null;

		try
		{
			Config config = new Config();
			input = new FileInputStream(file);
			prop.load(input);

			config.setCsvPath(prop.getProperty(CSV_PATH));

			if (config.getCsvPath() == null)
			{
				throw new IllegalArgumentException("You must set up CSV_PATH property");
			}
			for (PlatformVariants platformVariants : PlatformVariants.values())
			{
				String location = prop.getProperty(String.format(LOCATION, platformVariants.name()));
				if (location == null || location.isEmpty())
				{
					continue;
				}
				config.addPath(platformVariants, location);

				String defaultLocation = prop.getProperty(String.format(DEFAULT_LOCATION, platformVariants.name()));
				if (defaultLocation != null)
				{
					config.addDefaultLocaleMap(platformVariants, defaultLocation);
				}

				String excluding = prop.getProperty(String.format(EXCLUDING, platformVariants.name()));
				if (excluding != null)
				{
					String[] split = excluding.split(DELIMITER + "[ ]*");

					if(split.length == 0 && !excluding.isEmpty()){
						config.addExcludesMap(platformVariants, excluding);
					} else
					{
						config.addExcludesMap(platformVariants, split);
					}
				}

				String including = prop.getProperty(String.format(INCLUDING, platformVariants.name()));
				if (including != null)
				{
					String[] split = including.split(DELIMITER + "[ ]*");
					if(split.length == 0 && !including.isEmpty()){
						config.addIncludesMap(platformVariants, including);
					} else
					{
						config.addIncludesMap(platformVariants, split);
					}
				}

				String charset = prop.getProperty(String.format(CHARSET, platformVariants.name()));
				if (charset != null)
				{
					if (!charset.isEmpty() && !charset.contains(DELIMITER))
					{
						updateCharset(config, charset);
					}
					else
					{
						String[] split = charset.split(DELIMITER + "[ ]*");

						for (String s : split)
						{
							updateCharset(config, s);
						}
					}
				}

			}
			return config;
		} catch (IOException ex)
		{
			ex.printStackTrace();
		} finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	private void updateCharset(final Config config, final String s)
	{
		if (s.contains(","))
		{
			String[] split = s.split(",");
			if (split.length == 2)
			{
				config.addFileCharset(split[0], Charset.forName(split[1]));
			}
		}
	}
}
