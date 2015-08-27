/**
 * 
 */
package net.sf.JRecord.External;

import net.sf.JRecord.Log.AbsSSLogger;

/**
 * @author Bruce01
 *
 */
public abstract class BaseCopybookLoader implements CopybookLoader {

	/* (non-Javadoc)
	 * @see net.sf.JRecord.External.CopybookLoader#loadCopyBook(java.lang.String, int, int, java.lang.String, int, int, int, net.sf.JRecord.Log.AbsSSLogger)
	 */
	@Override
	public ExternalRecord loadCopyBook(String copyBookFile,
			int splitCopybookOption, int dbIdx, String font,
			int copybookFormat, int binFormat, int systemId, AbsSSLogger log)
			throws Exception {
		return loadCopyBook(copyBookFile, splitCopybookOption, dbIdx, font, binFormat, systemId, log);
	}

	
	
}
