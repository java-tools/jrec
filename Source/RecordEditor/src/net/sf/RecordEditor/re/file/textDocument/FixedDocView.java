package net.sf.RecordEditor.re.file.textDocument;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.AbstractRecordDetail.FieldDetails;
import net.sf.RecordEditor.re.file.DataStoreContent;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;
import net.sf.RecordEditor.utils.swing.color.ColorGroupManager;
import net.sf.RecordEditor.utils.swing.color.IColorGroup;

public class FixedDocView extends PlainView {

//	private static final Font FIXED_SIZED_FONT = SwingUtils.getMonoSpacedFont();
				//new Font("Monospaced", Font.PLAIN,  SwingUtils.STANDARD_FONT_HEIGHT);

//	private static Color[] colors = {Color.MAGENTA,  Color.BLUE, Color.DARK_GRAY, Color.RED, Color.BLACK,/* Color.PINK, Color.ORANGE*/};
	IColorGroup colorGrp = ColorGroupManager.getInstance().get(IColorGroup.FIELDS);
	private IReDocument doc = null;
	private DataStoreContent dsContent;
	private int[][] start, len;
	

	public FixedDocView(Element elem) {
		super(elem);
	}
	
	private void setDocument() {
		
		Document document;
		if (doc == null && (document = getDocument()) instanceof FileDocument5) {
			doc = (IReDocument) document;
			dsContent = doc.getDataStoreContent();
			
			AbstractLayoutDetails layout = dsContent.getFileView().getLayout();
			AbstractRecordDetail record;
			FieldDetails field;
			int fieldCount;
			
			start = new int[layout.getRecordCount()][];
			len = new int[start.length][];
			
			for (int i = 0; i < start.length; i++) {
				record = layout.getRecord(i);
				fieldCount = record.getFieldCount();
				start[i] = new int[fieldCount];
				len[i] = new int[fieldCount];
				for (int j = 0; j < fieldCount; j++) {
					field = record.getField(j);
					start[i][j] = field.getPos() - 1;
					len[i][j] = field.getLen();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.PlainView#drawUnselectedText(java.awt.Graphics, int, int, int, int)
	 */
	@Override
	protected int drawUnselectedText(Graphics g, int x, int y, int p0, int p1)
			throws BadLocationException {

		setDocument();
		if (doc == null) return x;
		
        Segment segment = getLineBuffer();
        IDataStorePosition pos = dsContent.createTempPosition(p0);
        AbstractLine line;
        
        if (pos == null || (line = pos.getLineRE()) == null) {
        	return x;
        }
        
        int t1 = p0;
        int initialOffset;
        int idx = 0;
        int recId = 0;
        long lineStart = pos.getLineStartRE();
        
        
        doc.getText(p0, p1 - p0, segment);
 //       JTextComponent t = (JTextComponent) getContainer();
        initialOffset = segment.offset;
//        g.setFont(FIXED_SIZED_FONT);
        
        if (start.length > 1) {
        	recId  = line.getPreferredLayoutIdx();
        }
        
//		System.out.println("--> " + t1 + " " + p1 + " " + lineStart + " " + recId + " " + pos.getLineNumber() + " " + p0 + " " + p1);
        while (idx < start[recId].length && (p0 - lineStart >= start[recId][idx] + len[recId][idx])) {
        	idx += 1;
        }
    
 //       System.out.println();
//        System.out.println("Screen: ");
        while (idx < start[recId].length && (p1 - lineStart > start[recId][idx])) {
        	if (t1 - p0 < start[recId][idx] ) {
        		g.setColor(Color.BLACK);
        		segment.offset = initialOffset + t1 - p0;
        		segment.count = start[recId][idx] - t1;
        		
        		x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
        		//System.out.print("##... ");
        		t1 = start[recId][idx];
        	}
        	
        	//System.out.print("\t" + idx);
        	int colorIdx = idx % colorGrp.size();
			Color backgroundColor = colorGrp.getBackgroundColor(colorIdx);

			segment.offset = initialOffset + t1 - p0;
    		segment.count = Math.min(p1 - t1, len[recId][idx]);
    		
    		x = writeText(g, x, y, t1, segment, colorGrp.getForegroundColor(colorIdx), backgroundColor);
//			if (backgroundColor != null/* && dh != null*/) {
//				g.setColor(backgroundColor);
//				FontMetrics fontMetrics = g.getFontMetrics();
//				int fontHeight = fontMetrics.getHeight();
//
//				
//				//Rectangle r = new Rectangle(x, y, Utilities.getTabbedTextWidth(segment, fontMetrics, idx, this, t1), fontMetrics.getHeight());
//				//super.paint(g, new Rectangle(x, y, Utilities.getTabbedTextWidth(segment, fontMetrics, idx, this, t1), fontMetrics.getHeight()));
//				//dh.paintLayeredHighlights(g, p0, p1, r, host, this);
//				int tabbedTextWidth = Utilities.getTabbedTextWidth(segment, fontMetrics, x, this, t1);
////				System.out.print("> " + idx + " " + y + " ~ " + fontHeight + " / " + tabbedTextWidth);
//				g.fillRect(x, y - y % fontHeight, tabbedTextWidth, fontHeight);
//			}
//        	g.setColor(colorGrp.getForegroundColor(colorIdx));
//    	
////    		int oldx = x;
//    
//    		x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
//    		System.out.print("; " + (x - oldx));
    		
    		t1 += segment.count;
        	idx += 1;
        }
        
		segment.offset = initialOffset + t1 - p0;
        if (t1 < p1 && (p1 - t1 > 1 || segment.charAt(0) != '\n') ) {
        	//System.out.print("- " + t1 + " " + p1);
    		segment.count = p1 - t1;
    		g.setColor(Color.RED);
    		//System.out.println("##--- " + t1 + " " + p1 + (segment.charAt(0) == '\n') + " " + (segment.charAt(0) == '\r') );
    		x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
    	}
//        System.out.println(" " + start[recId].length);
        
//        g.setColor(colors[idx % 4]);
//        
//        doc.getText(t1, t2 - t1, segment);
////        System.out.println();
////        System.out.println("  ==} " + x + " " + y + " " + t1 + " " + t2 + " ~ " + segment.toString());
//        x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
//        
//        while (t2 < p1) {
//        	idx += 1;
//        	t1 = idx * 4;
//        	t2 = Math.min(p1, t1 + 4);
//            doc.getText(t1, t2 - t1, segment);
//            g.setColor(colors[idx % 4]);
// //           g.setFont(font);
// //           System.out.println("  ==> " + x + " " + y + " " + t1 + " " + t2  + " ~ " + segment.toString());
//            x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
//        }
        return x;
	}
	
	
	
	private int  writeText(Graphics g, int x, int y, int t1, Segment segment, Color foreground, Color backgroundColor) {

		if (backgroundColor != null/* && dh != null*/) {
			g.setColor(backgroundColor);
			FontMetrics fontMetrics = g.getFontMetrics();
			int fontHeight = fontMetrics.getHeight();

			int tabbedTextWidth = Utilities.getTabbedTextWidth(segment, fontMetrics, x, this, t1);
			g.fillRect(x, y - y % fontHeight, tabbedTextWidth, fontHeight);
		}
		
		
		if (foreground == null) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(foreground);
		}
		x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
		return x;
	}

}
