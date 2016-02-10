package test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import test.exception.IncorrectLineException;
import test.reader.CSVReader;
import test.reader.Reader;
import test.reader.XMLReader;
import test.writer.CSVWriter;
import test.writer.XmlWriter;

/**
 * Date: 19.10.2015
 * Time: 13:25
 *
 * @author Savin Mikhail
 */
public class Analyzer
{
	public void analize(String csvFile, String xmlFile)
	{
		Reader<List<TranslateModel>> csvReader = new CSVReader<TranslateModel>(";")
		{
			@Override
			public TranslateModel getByLine(final String[] strings) throws IncorrectLineException
			{
				if(strings.length > 1)
				{
					return new TranslateModel(strings[0], strings[1]);
				}

				throw new IncorrectLineException();
			}
		};
		List<TranslateModel> translateModels = csvReader.readFile(csvFile);
		Resources resources = new XMLReader().readFile(xmlFile);

		int successCounter = 0;
		int totalCounter = 0;
		LinkedHashMap<String, String> successMap = new LinkedHashMap<>();
		LinkedHashMap<String, String> unSuccessMap = new LinkedHashMap<>();
		unSuccessMap.put("", "");
		List<TranslateModel> unSuccessTranslateModel = new ArrayList<>(translateModels);
		for (Map.Entry<String, String> entry : resources.strings.entrySet())
		{
			String value = entry.getValue();
			if (value == null)
			{
				continue;
			}

			boolean success = false;

			for (TranslateModel translateModel : translateModels)
			{
				String english = translateModel.english;
				if (english == null)
				{
					continue;
				}
				value = value.replaceAll("<*\\W*\\w>", "").replaceAll(" ", "").replaceAll("\\\\", "");
				english = english.replaceAll(" ", "");
				if (english.toLowerCase().trim().equals(value.toLowerCase().trim()))
				{
					successCounter++;
					successMap.put(entry.getKey(), translateModel.fi.replaceAll("'", "\\\\'"));
					success = true;
					unSuccessTranslateModel.remove(translateModel);
					break;
				}
			}
			if (!success)
			{
				unSuccessMap.put(entry.getKey(), entry.getValue());
				System.out.println("Unsuccess " + entry.getValue());
			}
			totalCounter++;
		}

		successMap.putAll(unSuccessMap);
		Resources resourcesResult = new Resources(successMap);

		XmlWriter<Resources> resourcesXmlWriter = new XmlWriter<>(Resources.class);
		resourcesXmlWriter.writeToFile(resourcesResult, "Result.xml");

		CSVWriter<TranslateModel> csvWriter = new CSVWriter<>("*", TranslateModel.class);
		csvWriter.writeToFile(unSuccessTranslateModel, "unused.scv");

		System.out.println("total: " + totalCounter + " success: " + successCounter);
	}
}
