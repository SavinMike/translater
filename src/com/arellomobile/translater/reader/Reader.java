package com.arellomobile.translater.reader;

/**
 * Date: 19.10.2015
 * Time: 9:51
 *
 * @author Savin Mikhail
 */
public interface Reader<T>
{
	T readFile(String file);
}
