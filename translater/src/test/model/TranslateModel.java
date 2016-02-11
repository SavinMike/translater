package test.model;

/**
 * Date: 19.10.2015
 * Time: 9:54
 *
 * @author Savin Mikhail
 */
public class TranslateModel
{
	public final String english;
	public final String fi;

	public TranslateModel(final String english, final String fi)
	{
		this.english = english;
		this.fi = fi;
	}

	@Override
	public String toString()
	{
		return "test.model.TranslateModel{" +
				"english='" + english + '\'' +
				", fi='" + fi + '\'' +
				"}\n";
	}
}
