package test.reader;

/**
 * Date: 10.02.2016
 * Time: 19:20
 *
 * @author Savin Mikhail
 */
public interface LineConverterReader<T>
{
	T convertHistory(String[] history);
}
