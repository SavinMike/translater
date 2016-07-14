package com.arellomobile.translater.writer;

import java.util.Set;

/**
 * Date: 10.02.2016
 * Time: 19:25
 *
 * @author Savin Mikhail
 */
public interface CsvHistory<E>
{
	Set<E> getEnumSet();

	String createHistory(String delimiter);

	boolean contains(E e);

}
