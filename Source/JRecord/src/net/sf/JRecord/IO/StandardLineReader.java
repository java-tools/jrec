/*
 * @Author Bruce Martin
 * Created on 26/08/2005
 *
 * Purpose:  reading Record Orientated files
 */
package net.sf.JRecord.IO;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;



/**
 * This abstract class is the base class for all <b>Line~Reader</b>
 * classes. A LineReader reads a file as a series of AbstractLines.
 *
 * <pre>
 * <b>Usage:</b>
 *
 *         CopybookLoader loader = <font color="brown"><b>new</b></font> RecordEditorXmlLoader();
 *         LayoutDetail layout = loader.loadCopyBook(copybookName, 0, 0, "", 0, 0, <font color="brown"><b>null</b></font>).asLayoutDetail();
 *
 *         <b>AbstractLineReader</b> reader = LineIOProvider.getInstance().getLineReader(layout.getFileStructure());
 * </pre>
 *
 * @author Bruce Martin
 *
 */
public abstract class StandardLineReader extends AbstractLineReader<LayoutDetail> {

	/**
	 *
	 */
	public StandardLineReader() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param provider
	 */
	public StandardLineReader(LineProvider provider) {
		super(provider);
		// TODO Auto-generated constructor stub
	}

}
