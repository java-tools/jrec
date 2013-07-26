/**
 *
 */
package net.sf.RecordEditor.po;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.RecordEditor.tip.PropertiesLineReader;
import net.sf.RecordEditor.tip.PropertiesLineWriter;
import net.sf.RecordEditor.tip.TipLineProvider;

/**
 * LineIO provider for GetText-PO files
 *
 * @author Bruce Martin
 *
 */
public class PoLineIOProvider implements AbstractLineIOProvider {

	private static final String PO_STRUCTURE_NAME  = "GetText PO/POT";
	private static final String TIP_STRUCTURE_NAME = "Tip Properties";
	private static final String EXTERNAL_PO_STRUCTURE_NAME  = "GetText_PO";
	private static final String EXTERNAL_TIP_STRUCTURE_NAME = "Tip_Properties";
	private static final PoLineProvider provider = new PoLineProvider();
	private static final TipLineProvider tipProvider = new TipLineProvider();


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractManager#getNumberOfEntries()
	 */
	@Override
	public int getNumberOfEntries() {
		return 2;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractManager#getManagerName()
	 */
	@Override
	public String getManagerName() {
		return "Po Line Provider";
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractManager#getKey(int)
	 */
	@Override
	public int getKey(int idx) {
		switch (idx) {
		case 0: return Constants.IO_GETTEXT_PO;
		case 1: return Constants.IO_TIP;

		default:
			break;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractManager#getName(int)
	 */
	@Override
	public String getName(int idx) {
		switch (idx) {
		case 0: return PO_STRUCTURE_NAME;
		case 1: return TIP_STRUCTURE_NAME;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(int, net.sf.JRecord.Details.LineProvider)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLineReader getLineReader(int fileStructure, LineProvider lineProvider) {
		switch (fileStructure) {
		case Constants.IO_GETTEXT_PO: 			return new PoMessageLineReader();
		case Constants.IO_TIP:		 			return new PropertiesLineReader("tip");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLineReader getLineReader(int fileStructure) {
		return getLineReader(fileStructure, null);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineWriter(int)
	 */
	@Override
	public AbstractLineWriter getLineWriter(int fileStructure) {
		return getLineWriter(fileStructure, null);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineWriter(int, java.lang.String)
	 */
	@Override
	public AbstractLineWriter getLineWriter(int fileStructure, String charset) {
		switch (fileStructure) {
		case Constants.IO_TIP:		 			return new PropertiesLineWriter("tip");
		case Constants.IO_GETTEXT_PO: 			return new PoMessageLineWriter();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#isCopyBookFileRequired(int)
	 */
	@Override
	public boolean isCopyBookFileRequired(int fileStructure) {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getStructureName(int)
	 */
	@Override
	public String getStructureName(int fileStructure) {
		switch (fileStructure) {
		case Constants.IO_GETTEXT_PO: 	return EXTERNAL_PO_STRUCTURE_NAME;
		case Constants.IO_TIP:			return EXTERNAL_TIP_STRUCTURE_NAME;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getStructureNameForIndex(int)
	 */
	@Override
	public String getStructureNameForIndex(int index) {
		return EXTERNAL_PO_STRUCTURE_NAME;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineProvider(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public LineProvider getLineProvider(int fileStructure) {
		switch (fileStructure) {
		case Constants.IO_GETTEXT_PO: 	return provider;
		case Constants.IO_TIP:			return tipProvider;
		}
		return null;
	}

}
