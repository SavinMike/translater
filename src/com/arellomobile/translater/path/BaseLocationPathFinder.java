package com.arellomobile.translater.path;

/**
 * Date: 16.02.2016
 * Time: 11:38
 *
 * @author Savin Mikhail
 */
public abstract class BaseLocationPathFinder implements LocationPathFinder
{
	private String[] mIncludes;
	private String[] mExcludes;
	private String mDefault;

	@Override
	public void setDefault(final String isDefault)
	{
		mDefault = isDefault;
	}

	protected String getDefault()
	{
		return mDefault;
	}

	@Override
	public void setIncludes(final String... includes)
	{
		if(includes == null){
			mIncludes = new String[1];
		} else {
			mIncludes = includes;
		}
	}

	@Override
	public void setExcludes(final String... excludes)
	{
		if(excludes == null){
			mExcludes = new String[1];
		} else {
			mExcludes = excludes;
		}
	}

	protected String[] getIncludes()
	{
		return mIncludes;
	}

	protected String[] getExcludes()
	{
		return mExcludes;
	}
}
