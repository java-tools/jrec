/**
 * 
 */
package net.sf.RecordEditor.utils.openFile;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.utils.edit.ReIOProvider;

/**
 * @author Bruce Martin
 *
 */
public class ReadLayout {

	protected  boolean loadFromFile = false;
	
	
	public final AbstractLayoutDetails buildLayoutFromSample(int fileStruc, boolean isCSV,
			String fontname, String delimiter, String filename) {
		
    	int layoutType = Constants.rtDelimited;
    	if (fileStruc == Constants.IO_XML_BUILD_LAYOUT) {
    		layoutType = Constants.RT_XML;
    	}
    	
    	LayoutDetail ret = new LayoutDetail("", new RecordDetail[0], "",
    					layoutType,
    					null, "", fontname, null,
    					fileStruc
                  );
   		if (isCSV) {
   			ret.setDelimiter(delimiter);
   		}
     	return getFileBasedLayout(filename, ret);
	}
	
	/**
	 * get layout including layouts based on a file content (like XML or CSV
	 * files with names on the first line)
	 * 
	 * @param fileName sample data file
	 * @param layout initial record layout
	 * 
	 * @return actual layout
	 */
	public final AbstractLayoutDetails getFileBasedLayout(String fileName, AbstractLayoutDetails layout) {
		AbstractLayoutDetails ret = layout;

		AbstractLineIOProvider ioProvider =  ReIOProvider.getInstance();
		
		System.out.println(" == " + loadFromFile
				+ " " + ((loadFromFile && fileName != null))	
				+ " " + (! "".equals(fileName))
				+ " " + (! ioProvider.isCopyBookFileRequired(layout.getFileStructure()))
				+ " " + layout.getFileStructure()
				);
		if (loadFromFile && fileName != null && ! "".equals(fileName)
		&&  ! ioProvider.isCopyBookFileRequired(layout.getFileStructure())) {
			AbstractLineReader reader =  ioProvider.getLineReader(layout.getFileStructure());
			try {
				reader.open(fileName, layout);
				reader.read();
				if (layout.isXml()) {
					int i =0;
					while (reader.read() != null && i++ < 1000) {
						
					}
				}
				ret = reader.getLayout();
			} catch (Exception e) {
			}
		}
		return ret;
	}



	/**
	 * @param newLoadFromFile the loadFromFile to set
	 */
	public final ReadLayout setLoadFromFile(boolean newLoadFromFile) {
		this.loadFromFile = newLoadFromFile;
		return this;
	}

}
