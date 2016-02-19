package test.model;

public enum PlatformVariants
{
	ANDROID("android/")
			{
				@Override
				public String getExtension()
				{
					return XML;
				}
			}, IOS("ios/")
		{
			@Override
			public String getExtension()
			{
				return STRINGS;
			}
		}, WEB("web/")
		{
			@Override
			public String getExtension()
			{
				return PO;
			}
		};

	public static final String PO = "po";
	public static final String XML = "xml";
	public static final String STRINGS = "strings";

	public String basePath;

	PlatformVariants(final String basePath)
	{
		this.basePath = basePath;
	}

	public abstract String getExtension();

}