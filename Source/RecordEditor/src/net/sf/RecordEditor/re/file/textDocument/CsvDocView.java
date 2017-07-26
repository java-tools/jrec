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
import net.sf.RecordEditor.re.file.DataStoreContent;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;
import net.sf.RecordEditor.utils.swing.color.ColorGroupManager;
import net.sf.RecordEditor.utils.swing.color.IColorGroup;

public class CsvDocView extends PlainView {

//	private static final Font FIXED_SIZED_FONT = SwingUtils.getMonoSpacedFont();
				//new Font("Monospaced", Font.PLAIN,  SwingUtils.STANDARD_FONT_HEIGHT);

//	private static Color[] colors = {Color.MAGENTA,  Color.BLUE, Color.DARK_GRAY, Color.RED, Color.BLACK,/* Color.PINK, Color.ORANGE*/};
	private IColorGroup colorGrp = ColorGroupManager.getInstance().get(IColorGroup.FIELDS);
	private IColorGroup spclColorGrp = ColorGroupManager.getInstance().get(IColorGroup.SPECIAL);
	private IReDocument doc = null;
	private DataStoreContent dsContent;
//	private Color backGroundColor = Color.WHITE;
	private AbstractLayoutDetails schema;
	private String quote;
	

	public CsvDocView(Element elem) {
		super(elem);
	}
	
	private void setDocument() {
		
		Document document;
		if (doc == null && (document = getDocument()) instanceof FileDocument5) {
//			Container container = this.getContainer();
//			if (container != null) {
//				backGroundColor = container.getBackground();
//			}
			
			doc = (IReDocument) document;
			dsContent = doc.getDataStoreContent();
			schema = dsContent.getFileView().getLayout();
			quote = schema.getQuoteDetails().asString();
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
        int initialOffset, initialCount;
        int idx = 0;
        int recId = 0;
        int soFar = 0;
        long lineStart = pos.getLineStartRE();
        AbstractLayoutDetails layout = pos.getLineRE().getLayout();
        int[] lengths;
        int sepLength = layout.getDelimiterDetails().asString().length();
        
        doc.getText(p0, p1 - p0, segment);
        initialOffset = segment.offset;
        initialCount = segment.count;
//        g.setFont(FIXED_SIZED_FONT);
        
        if (layout.getRecordCount() > 1) {
        	recId  = line.getPreferredLayoutIdx();
        }
        
        lengths = new int[layout.getRecord(recId).getFieldCount()];
        
        if (quote == null || "".equals(quote)) {
        	for (int i = 0; i < lengths.length; i++) {
        		lengths[i] = line.getFieldValue(recId, i).asString().length(); 
        	}
        } else {
        	String delimiter = schema.getDelimiterDetails().asString();
			String chk = quote + delimiter;
        	String tl = line.getFullLine();
        	boolean quoteLen1  = quote.length() == 1;
        	String tf;
        	int st = 0;
        	int fl, search;
        	for (int i = 0; i < lengths.length; i++) {
        		tf = line.getFieldValue(recId, i).asString();
        		fl = tf.length(); 
        		if (st + fl < tl.length() && tl.charAt(st) == quote.charAt(0) 
        		&& (quoteLen1 || tl.substring(st).startsWith(quote))) {
        			search = tl.indexOf(chk, st + fl);
        			if (search >= st) {
        				fl  = search - st + quote.length();
        			} else {
        				fl =  tl.length() - st;
        			}
        		}
        		fl = Math.min(fl, tl.length() - st);
        		lengths[i] = fl; 
        		st =  Math.min(tl.length(), st + fl +  delimiter.length()); 
        	}
        }
        
//		System.out.println("--> " + t1 + " " + p1 + " " + lineStart);
        while (idx < lengths.length && (p0 - lineStart >= soFar + lengths[idx])) {
        	soFar += lengths[idx] + sepLength;
        	idx += 1;
        }

        soFar = 0;
        while (idx < lengths.length && (p1 - lineStart > t1 - p0)) {
//        	if (t1 < soFar ) {
//        		g.setColor(Color.BLACK);
//        		segment.offset = initialOffset + t1 - p0;
//        		segment.count = soFar - t1;
//        		x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
//        		t1 = soFar;
//        	}
//        	
        	int colorIdx = idx % colorGrp.size();
			//setColorDtls(g, colorGrp.getForegroundColor(colorIdx), Color.YELLOW /*colorGrp.getBackgroundColor(colorIdx)*/);
       		segment.offset = initialOffset + t1 - p0;
    		segment.count = Math.min(p1 - t1, lengths[idx]);
    		x = writeText(g, x, y, t1, segment, colorGrp.getForegroundColor(colorIdx), colorGrp.getBackgroundColor(colorIdx));
//    		Color backgroundColor = colorGrp.getBackgroundColor(colorIdx);
//			if (backgroundColor != null/* && dh != null*/) {
//				g.setColor(backgroundColor);
//				FontMetrics fontMetrics = g.getFontMetrics();
//				int fontHeight = fontMetrics.getHeight();
//
//				int tabbedTextWidth = Utilities.getTabbedTextWidth(segment, fontMetrics, x, this, t1);
//				g.fillRect(x, y - y % fontHeight, tabbedTextWidth, fontHeight);
//			}
//    		x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
    		t1 += segment.count;
        	idx += 1;
 //          	soFar += sepLength;;

        	if (soFar < initialCount) {
        		//setColorDtls(g, 
        		//		spclColorGrp.getForegroundColor(IColorGroup.SPECIAL_FIELD_SEPERATOR),
        		//		spclColorGrp.getBackgroundColor(IColorGroup.SPECIAL_FIELD_SEPERATOR));
	       		segment.offset = initialOffset + t1 - p0;
	    		segment.count = Math.min(p1 - t1, sepLength);

	       		x = writeText(g, x, y, t1, segment, 
	       				spclColorGrp.getForegroundColor(IColorGroup.SPECIAL_FIELD_SEPERATOR), 
	       				spclColorGrp.getBackgroundColor(IColorGroup.SPECIAL_FIELD_SEPERATOR));
	    		//x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
	    		//TODO  SwingUtilities2. ....
	    		t1 += sepLength;
	//    		soFar += sepLength;
        	}
    	}
        
        if (t1 < p1 ) {
    		g.setColor(Color.RED);
    		segment.offset = initialOffset + t1 - p0;
    		segment.count = p1 - t1;
  //  		System.out.println("## " + t1 + " " + p1);
    		x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
    	}
        
//        setColorDtls(g, null, null);
        
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
	
//	private void setColorDtls(Graphics g, Color foreground, Color background) {
//		if (foreground != null) {
//			g.setColor(foreground);
//		}
//		
//		Container container = this.getContainer();
//		if (container != null) {
//			if (background != null) {
//				container.setBackground(background);
//			} else {
//				container.setBackground(backGroundColor);
//			}
//		}
//	}
}
