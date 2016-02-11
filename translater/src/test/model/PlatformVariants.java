package test.model;

public enum PlatformVariants
{
	ANDROID("android/"), IOS("ios/"), WEB("web/");

	public String basePath;

	PlatformVariants(final String basePath)
	{
		this.basePath = basePath;
	}
}