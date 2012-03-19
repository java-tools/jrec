/**
 * 
 */
package net.sf.RecordEditor.re.openFile;

import java.io.IOException;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.DefaultLineProvider;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.ToExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.re.util.csv.GenericCsvReader;

/**
 * @author Bruce Martin
 *
 */
public class CopybookLoaderFactoryExtended extends CopybookLoaderFactory {

	private static CopybookLoaderFactory instance = new CopybookLoaderFactoryExtended();
	
	
	/**
	 * 
	 */
	public CopybookLoaderFactoryExtended() {

	}


	
	/**
	 * register copybook loaders
	 */
	protected void registerAll() {

		registerStandardLoaders1();
		register("Generic Csv", CopybookLoaderFactoryExtended.GenericCsv.class, "");

		registerStandardLoaders2();
		csv1 -= 1;
	}
	
	/**
	 * @return the instance
	 */
	public final static CopybookLoaderFactory getInstance() {
		return instance;
	}
	
	/**
	 * Generic CSV loader
	 * @author Bruce Martin
	 *
	 */
	public static class GenericCsv implements CopybookLoader   {
	
		/**
		 * @see net.sf.JRecord.External.CopybookLoader#loadCopyBook(java.lang.String, int, int, java.lang.String, int, int, net.sf.JRecord.Log.AbsSSLogger)
		 */
		public ExternalRecord loadCopyBook(String copyBookFile, int splitCopybookOption, int dbIdx, String font, int binFormat, int systemId, AbsSSLogger log) 
		throws IOException, RecordException {
			
			GenericCsvReader r = new GenericCsvReader(new DefaultLineProvider());
			r.open(copyBookFile);
			
			r.read();
			
			r.close();
			
			return ToExternalRecord.getInstance()
					.getExternalRecord(r.getLayout(), Conversion.getCopyBookId(copyBookFile), systemId);
		}

	}


}
