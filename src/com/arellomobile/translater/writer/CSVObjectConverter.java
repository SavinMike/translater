package com.arellomobile.translater.writer;

/**
 * Date: 09.02.2016
 * Time: 16:17
 *
 * @author Savin Mikhail
 */
public interface CSVObjectConverter<E>
{
	<H extends CsvHistory<E>> String convert(String delimiter, H history);
}
