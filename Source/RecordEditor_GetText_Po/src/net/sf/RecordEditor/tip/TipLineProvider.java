package net.sf.RecordEditor.tip;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.ArrayListLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.Details.RecordDetail;


/**
 * Line creator (or provider) for GetText PO files
 * @author Bruce Martin
 *
 */
public class TipLineProvider implements LineProvider<LayoutDetail> {

	@Override
	public AbstractLine<LayoutDetail> getLine(LayoutDetail recordDescription) {
		return new ArrayListLine<FieldDetail, RecordDetail, LayoutDetail>(recordDescription, 0);
	}

	@Override
	public AbstractLine<LayoutDetail> getLine(LayoutDetail recordDescription, String linesText) {
		throw new RecordRunTimeException("Not Supported");
	}

	@Override
	public AbstractLine<LayoutDetail> getLine(LayoutDetail recordDescription, byte[] lineBytes) {
		throw new RecordRunTimeException("Not Supported");
	}

}
