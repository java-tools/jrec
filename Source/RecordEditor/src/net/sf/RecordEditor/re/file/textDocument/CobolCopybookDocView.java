package net.sf.RecordEditor.re.file.textDocument;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;


public class CobolCopybookDocView extends PlainView {

//	private static final Font FIXED_SIZED_FONT = SwingUtils.getMonoSpacedFont();
				//new Font("Monospaced", Font.PLAIN,  SwingUtils.STANDARD_FONT_HEIGHT);

//	private static Color[] colors = {Color.MAGENTA,  Color.BLUE, Color.DARK_GRAY, Color.RED, Color.BLACK,/* Color.PINK, Color.ORANGE*/};
//	IColorGroup colorGrp = ColorGroupManager.getInstance().get(IColorGroup.FIELDS);
	
	private static final int TBL_SIZE = 360;
	private static final byte BLACK = 0;
	private static final byte NUM = 1;
	private static final byte PIC = 2;
	private static final byte OTHER = 3;
	private static final byte KEYWORD1 = 4;
	private static final byte KEYWORD2 = 5;
	private static final byte KEYWORD3 = 6;
	
	private static final HashMap<String, Byte> KEYWORD_MAP = new HashMap<String, Byte>();
	
	static {
		KEYWORD_MAP.put("pic", PIC);
		KEYWORD_MAP.put("picture", PIC);
		KEYWORD_MAP.put("comp", KEYWORD1);
		KEYWORD_MAP.put("computational", KEYWORD1);
		KEYWORD_MAP.put("comp-1", KEYWORD1);
		KEYWORD_MAP.put("computational-1", KEYWORD1);
		KEYWORD_MAP.put("comp-2", KEYWORD1);
		KEYWORD_MAP.put("computational-2", KEYWORD1);
		KEYWORD_MAP.put("comp-3", KEYWORD1);
		KEYWORD_MAP.put("computational-3", KEYWORD1);
		KEYWORD_MAP.put("comp-4", KEYWORD1);
		KEYWORD_MAP.put("computational-4", KEYWORD1);
		KEYWORD_MAP.put("comp-5", KEYWORD1);
		KEYWORD_MAP.put("computational-5", KEYWORD1);
		KEYWORD_MAP.put("occurs", KEYWORD2);
		KEYWORD_MAP.put("times", KEYWORD2);
		KEYWORD_MAP.put("depending", KEYWORD3);
		KEYWORD_MAP.put("on", KEYWORD3);
	}
	
	private final Color[] COLORS = {Color.BLACK, Color.RED, Color.BLUE, Color.DARK_GRAY,
			 new Color(145, 44, 238), new Color(34, 139, 34), Color.PINK};
	Color COMMENT_COLOR = new Color(205, 38,   38);
	private int startCol=6, endCol=71;
	
	

	public CobolCopybookDocView(Element elem) {
		super(elem);
	}
	

	/* (non-Javadoc)
	 * @see javax.swing.text.PlainView#drawUnselectedText(java.awt.Graphics, int, int, int, int)
	 */
	@Override
	protected int drawUnselectedText(Graphics g, int x, int y, int p0, int p1)
			throws BadLocationException {

		try {
			Document doc = super.getDocument();
			Element element = super.getElement();
			int elIdx = element.getElementIndex(p0);
			if (elIdx < 0 || elIdx >= element.getElementCount()) {
				return super.drawSelectedText(g, x, y, p0, p1);
			}
			int pos = p0 - element.getElement(elIdx).getStartOffset(); 
			
			Segment segment = getLineBuffer();
      
			   
			int t1 = p0;
			doc.getText(p0 - pos, p1 - p0 + pos, segment);       
			  
			int startCol = getStartCol();
			int count = segment.count;
			int endCol = Math.min(getEndCol() + 1, count);
			int idx = Math.max(startCol, pos);
			int[] txtStart = new int[TBL_SIZE];
			int[] txtEnd = new int[TBL_SIZE];
			byte[] type = new byte[TBL_SIZE];
			int txtNum = 0;
			
			Arrays.fill(txtEnd, 0);
			
			txtStart[0] = 0;
			txtEnd[0] = idx-1;
			type[0] = BLACK;
			idx = skipWhiteSpace(idx, segment);
			while (idx < endCol) {
				txtNum += 1;
				if (txtNum >= txtStart.length) {
					return super.drawSelectedText(g, txtNum, y, p0, p1);
				}
				txtStart[txtNum] = idx;
				do {
					idx += 1;
				} while (idx < endCol && ! isWhiteSpace(segment.charAt(idx)));
				txtEnd[txtNum] = idx - 1;
				type[txtNum] = getType(segment, txtStart[txtNum], txtEnd[txtNum]);
				idx = skipWhiteSpace(idx, segment);
			}
			
			
			if (idx < segment.count) {
				txtNum+=1;
				txtStart[txtNum] = idx;
				txtEnd[txtNum] = segment.count - 1;
				type[txtNum] = BLACK;
			}
      
			if (txtNum == 0) {
				return super.drawSelectedText(g, txtNum, y, p0, p1);
			}
			
			segment.offset += pos;
			segment.count  -= pos;
			if (segment.charAt(txtStart[1]) == '*') {
				x = writeText(g, x, y, t1, txtStart[1], segment,  COLORS[0], null);
				return writeText(g, x, y, t1, count - txtStart[1] - pos, segment,  COMMENT_COLOR, null);
			}
			int st = 0;
//			System.out.print(txtNum + "   >>>>");
			for (int i = 0; i <= txtNum; i++) {
				x = writeText(g, x, y, t1, txtEnd[i] - st + 1, segment,  COLORS[type[i]], null);
//				System.out.print("  " + (txtEnd[i] - st+1));
				
				st = txtEnd[i] + 1;
				t1 += segment.count;
			}
//			System.out.print("   I   ");
//			for (int i = 0; i <= txtNum; i++) {
//				System.out.print(txtStart[i] + " " + txtEnd[i] +", ");
//			}
	//		System.out.println("<<<<");
			
			//x = writeText(g, x, y, t1, txtEnd[i] - st, segment,  COLORS[type[i]], null);

			return x;
		} catch (Exception e) {
			return super.drawUnselectedText(g, x, y, p0, p1);
		}
	}
	
	
	
	private int  writeText(Graphics g, int x, int y, int t1, int count, Segment segment, Color foreground, Color backgroundColor) {

		if (count > 0) {
			segment.count = count;
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
			segment.offset += count;
		}
		return x;
	}
	
	private byte getType(Segment seg, int start, int end) {
		
		if ( end < start) {
			return OTHER;
		}
		String s = seg.subSequence(start, end+1).toString().toLowerCase();
		if (s.endsWith(".")) {
			s = s.substring(0, s.length() - 1);
		}
		Byte b = KEYWORD_MAP.get(s);
		//System.out.println(">>" + s + "<< " + b);
		if (b != null ) {
			return b;
		}
		
		for (int i = start; i<= end; i++) {
			if (! isNumeric(seg.charAt(i))) {
				return OTHER;
			}
		}
		
		return NUM;
	}

	private int skipWhiteSpace(int i, Segment segment) {
	    int endCol2 = getEndCol();
		while (i < endCol2 && i < segment.length() && isWhiteSpace(segment.charAt(i))) {
	      	i += 1;
	    }
		return i;
	}
	
	private boolean isNumeric(char ch) {
		return ch >= '0' && ch <= '9';
	}
	private boolean isWhiteSpace(char ch) {
		return ch == ' ' || ch == '\t';
	}
	
	private int getStartCol() {
		return startCol;
	}
	

	private int getEndCol() {
		return endCol;
	}

}
