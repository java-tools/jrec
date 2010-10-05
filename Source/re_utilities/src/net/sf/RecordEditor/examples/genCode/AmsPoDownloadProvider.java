/*
 * @Author Generate by XmplLineBuilder
 *
 * Purpose: line to access AmsPoDownload records
 */
package net.sf.RecordEditor.examples.genCode;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;

/**
 * Create line provider for AmsPoDownload
 *
 *
 * @author Generate by XmplLineBuilder
 *
 */
public class AmsPoDownloadProvider implements LineProvider<LayoutDetail> {

    /**
     * @see record.LineProvider#getLine(net.sf.JRecord.lineDetail.LayoutDetail)
     */
    public AbstractLine getLine(LayoutDetail recordDescription) {
        return new AmsPoDownload(recordDescription);
    }


    /**
     * @see record.LineProvider#getLine(net.sf.JRecord.lineDetail.LayoutDetail, byte[])
     */
    public AbstractLine getLine(LayoutDetail recordDescription, byte[] lineBytes) {
        return new AmsPoDownload(recordDescription, lineBytes);
    }


    /**
     * @see record.LineProvider#getLine(net.sf.JRecord.lineDetail.LayoutDetail, java.lang.String)
     */
    public AbstractLine getLine(LayoutDetail recordDescription, String linesText) {
        return new AmsPoDownload(recordDescription, linesText);
    }
}
