package com.arellomobile.translater.parser;

import com.arellomobile.translater.model.PlatformVariants;

/**
 * Date: 18.02.2016
 * Time: 16:04
 *
 * @author Savin Mikhail
 */
public class AndroidCsvFilenameInfo extends CsvFilenameInfo
{
	public final String flavors;

	public AndroidCsvFilenameInfo(final PlatformVariants platformVariants, final String fileName, final String flavors, final String csvName)
	{
		super(platformVariants, fileName, csvName);
		this.flavors = flavors;
	}
}
