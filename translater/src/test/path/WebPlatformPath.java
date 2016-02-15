package test.path;

import test.model.PlatformVariants;

/**
 * Date: 15.02.2016
 * Time: 13:33
 *
 * @author Savin Mikhail
 */
public class WebPlatformPath extends PlatformPath
{

	public static final String PO = "po";

	public WebPlatformPath(final String localeName)
	{
		super(localeName, PlatformVariants.WEB);
	}

	@Override
	protected String getPrefix()
	{
		return "/" + name() + "/LC_MESSAGES/";
	}

	@Override
	protected String getStringExtension()
	{
		return PO;
	}
}
