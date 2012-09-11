package net.sf.RecordEditor.po;

import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.RecordEditor.po.def.PoLine;

/**
 * Line creator (or provider) for GetText PO files
 * @author Bruce Martin
 *
 */
public class PoLineProvider implements LineProvider<LayoutDetail> {

	@Override
	public AbstractLine<LayoutDetail> getLine(LayoutDetail recordDescription) {
		return new PoLine();
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
