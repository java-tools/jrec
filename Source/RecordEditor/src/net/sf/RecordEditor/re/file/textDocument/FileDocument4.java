package net.sf.RecordEditor.re.file.textDocument;
/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import java.awt.Color;
import java.awt.Font;

import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.AbstractRecordDetail.FieldDetails;
import net.sf.RecordEditor.re.file.DataStoreContent;
import net.sf.RecordEditor.re.file.DocumentUpdateListner;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;
import net.sf.RecordEditor.utils.fileStorage.ITextInterface;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * A plain document based on a FileView (and its DataStore)
 * A DataStore is essentially a list of lines; This implements
 * a document and the RootElement
 *
 *
 * @author  Bruce Martin
 * @see     Document
 * @see     AbstractDocument
 */
@SuppressWarnings("serial")
public class FileDocument4 extends AbstractDocument implements StyledDocument, Element, DocumentUpdateListner {

	private static int callcount = 0;
    /**
     * Name of the attribute that specifies the tab
     * size for tabs contained in the content.  The
     * type for the value is Integer.
     */
    public static final String tabSizeAttribute = "tabSize";
    private static StyleContext styleContext = null;
    private static final String mainStyleName = "MainStyle";
    private static final String colorStyle    = "Style";
    private static Style mainStyle ;

    private static Color[] colors = {Color.BLACK, Color.BLUE, Color.DARK_GRAY, Color.ORANGE};
    private static Style[] fieldStyles = new Style[colors.length];
	private final DataStoreContent dsContent;
	private final boolean colorFields;
	
	static SimpleAttributeSet[] stdAttr = {new SimpleAttributeSet(), new SimpleAttributeSet(), new SimpleAttributeSet(), new SimpleAttributeSet()};
	static {
		StyleConstants.setForeground(stdAttr[0], Color.GREEN);
		StyleConstants.setForeground(stdAttr[1], Color.BLUE);
		StyleConstants.setForeground(stdAttr[2], Color.RED);
		StyleConstants.setForeground(stdAttr[3], Color.BLACK);
		StyleConstants.setFontFamily(stdAttr[0], "Courier New");
		StyleConstants.setFontFamily(stdAttr[1], "Courier New");
		StyleConstants.setFontFamily(stdAttr[2], "Courier New");
		StyleConstants.setFontFamily(stdAttr[3], "Courier New");
	}
	
	
    /**
     * Constructs a plain text document.  A default model using
     * <code>GapContent</code> is constructed and set.
     */
    public FileDocument4(FileView f, @SuppressWarnings("rawtypes") ITextInterface c) {
        this(new DataStoreContent(f, c), false);
    }

    /**
     * Constructs a plain text document.  A default root element is created,
     * and the tab size set to 8.
     *
     * @param dsc  the container for the content
     */
    public FileDocument4(DataStoreContent dsc, boolean colorFields) {
    	super(dsc, getAttributeContext(colorFields));
        putProperty(tabSizeAttribute, Integer.valueOf(8));
        dsContent = dsc;
        dsContent.setDocumentUpdateListner(this);
        this.colorFields = colorFields;

    }

    public static AttributeContext getAttributeContext(boolean colorFields) {
    	if (colorFields) {
    		if (styleContext == null ) {
    			styleContext = new StyleContext();
	    	    Style defaultStyle = styleContext.getStyle(StyleContext.DEFAULT_STYLE);

	    	    // Create and add the main document style
	    	    mainStyle = styleContext.addStyle(mainStyleName, defaultStyle);
//	    	    StyleConstants.setLeftIndent(mainStyle, 16);
//	    	    StyleConstants.setRightIndent(mainStyle, 16);
//	    	    StyleConstants.setFirstLineIndent(mainStyle, 16);
	    	    StyleConstants.setFontFamily(mainStyle, "monospaced");
	    	    StyleConstants.setFontSize(mainStyle, SwingUtils.STANDARD_FONT_HEIGHT);

	    	    for (int j = 0; j < 4; j++) {
		    	    Style cwStyle = styleContext.addStyle(colorStyle + j, null);
		    	    StyleConstants.setFontFamily(cwStyle, "monospaced");
		    	    StyleConstants.setForeground(cwStyle, colors[j]);
		    	    if (j % 2 == 1) {
		    	    	StyleConstants.setBackground(cwStyle, Color.YELLOW);
		    	    }

		    	    fieldStyles[j] = cwStyle;
	    	    }
    		}
    		return styleContext;
    	}
    	return StyleContext.getDefaultStyleContext();
    }

    /**
     * Gets the default root element for the document model.
     *
     * @return the root
     * @see Document#getDefaultRootElement
     */
    public Element getDefaultRootElement() {
        return this;
    }



    /**
     * Get the paragraph element containing the given position.  Since this
     * document only models lines, it returns the line instead.
     */
    public Element getParagraphElement(int pos){
    	IDataStorePosition p = dsContent.createTempPosition(pos);
    	return getElement(p.getLineNumberRE());
//    	IDataStorePosition p = dsContent.getLinePosition(pos);
//		if (p == null) {
//			System.out.println("Error Getting LineNo: " + pos + " " + dsContent.numberOfLines());
//			return null;
//		}
//
//        return new LineElement(this, p);
    }



    /* (non-Javadoc)
	 * @see javax.swing.text.Document#getLength()
	 */
	@Override
	public int getLength() {
		return dsContent.length();
	}


//	/* (non-Javadoc)
//	 * @see javax.swing.text.Document#getText(int, int)
//	 */
//	@Override
//	public String getText(int offset, int length) throws BadLocationException {
//		return dsContent.getString(offset, length);
//	}

//	/**
//	 * @see javax.swing.text.Document#getText(int, int, javax.swing.text.Segment)
//	 */
//	@Override
//	public void getText(int offset, int length, Segment txt)
//			throws BadLocationException {
//		dsContent.getChars(offset, length, txt);
//	}


	/* (non-Javadoc)
	 * @see javax.swing.text.Document#createPosition(int)
	 */
	@Override
	public Position createPosition(int offs) throws BadLocationException {
		if (dsContent != null) {
			return dsContent.createPosition(offs);
		}
		return super.createPosition(offs);//dsContent.createPosition(offs));
	}

//	private DataStorePosition register(DataStorePosition pos) {
//		return pos;
//	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Document#getRootElements()
	 */
	@Override
	public Element[] getRootElements() {
        Element[] e = new Element[1];
        e[0] = getDefaultRootElement();
		return e;
	}






    /*  ------------------------------------------------------------------------------
     *    Styled Document methods
     *  ------------------------------------------------------------------------------  */


	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#addStyle(java.lang.String, javax.swing.text.Style)
	 */
	@Override
	public Style addStyle(String nm, Style parent) {
        StyleContext styles = (StyleContext) getAttributeContext();
        return styles.addStyle(nm, parent);
    }

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#removeStyle(java.lang.String)
	 */
	@Override
	public void removeStyle(String nm) {
        StyleContext styles = (StyleContext) getAttributeContext();
        styles.removeStyle(nm);
    }

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#getStyle(java.lang.String)
	 */
	@Override
	public Style getStyle(String nm) {
        StyleContext styles = (StyleContext) getAttributeContext();
        return styles.getStyle(nm);
    }

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#setCharacterAttributes(int, int, javax.swing.text.AttributeSet, boolean)
	 */
	@Override
	public void setCharacterAttributes(int offset, int length, AttributeSet s,
			boolean replace) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#setParagraphAttributes(int, int, javax.swing.text.AttributeSet, boolean)
	 */
	@Override
	public void setParagraphAttributes(int offset, int length, AttributeSet s,
			boolean replace) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#setLogicalStyle(int, javax.swing.text.Style)
	 */
	@Override
	public void setLogicalStyle(int pos, Style s) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#getLogicalStyle(int)
	 */
	@Override
	public Style getLogicalStyle(int p) {
//        Style s = null;
//        Element paragraph = getParagraphElement(p);
//        if (paragraph != null) {
//            AttributeSet a = paragraph.getAttributes();
//            AttributeSet parent = a.getResolveParent();
//            if (parent instanceof Style) {
//                s = (Style) parent;
//            }
//        }
//        return s;

		return mainStyle;
    }

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#getCharacterElement(int)
	 */
	@Override
	public Element getCharacterElement(int pos) {
		int ei1 = getElementIndex(pos);
		if (ei1 >= 0) {
			int ei2 =  getElement(ei1).getElementIndex(pos) ;
			if (ei2 >= 0) {
				System.out.println(" ~~> Color Element " + pos + " / " + ei1 + " " + ei2);
				return getElement(ei1).getElement(ei2);
			}
		}
		System.out.println(" ~~> Color Element " + pos + " / " + ei1 + "  not found ");
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#getForeground(javax.swing.text.AttributeSet)
	 */
	@Override
	public Color getForeground(AttributeSet attr) {
        StyleContext styles = (StyleContext) getAttributeContext();
		System.out.println(" ~~> getForeground " + styles.getForeground(attr));
        return styles.getForeground(attr);
    }

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#getBackground(javax.swing.text.AttributeSet)
	 */
	@Override
	public Color getBackground(AttributeSet attr) {
		StyleContext styles = (StyleContext) getAttributeContext();
		return styles.getBackground(attr);

//		return (Color) attr.getAttribute(StyleConstants.Background);
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.StyledDocument#getFont(javax.swing.text.AttributeSet)
	 */
	@Override
	public Font getFont(AttributeSet attr) {
		StyleContext styles = (StyleContext) getAttributeContext();
		return styles.getFont(attr);
	}

	/*  ------------------------------------------------------------------------------
     *    Root Element methods
     *  ------------------------------------------------------------------------------  */



	/* (non-Javadoc)
	 * @see javax.swing.text.Element#getDocument()
	 */
	@Override
	public Document getDocument() {
		return this;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Element#getParentElement()
	 */
	@Override
	public Element getParentElement() {
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Element#getName()
	 */
	@Override
	public String getName() {
		return AbstractDocument.SectionElementName;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Element#getAttributes()
	 */
	@Override
	public AttributeSet getAttributes() {
		return SimpleAttributeSet.EMPTY;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Element#getStartOffset()
	 */
	@Override
	public int getStartOffset() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Element#getEndOffset()
	 */
	@Override
	public int getEndOffset() {
		return dsContent.length() + 1;
	}

	/**
	 * @see javax.swing.text.Element#getElementIndex(int)
	 */
	@Override
	public int getElementIndex(int offset) {
		if (offset < 0) {
			//System.out.println("~~ a) ~~ -1 " );
			return -1;
		}
//		try {
			IDataStorePosition pos = dsContent.createTempPosition(offset);
			if (pos == null) {
//				System.out.println("~~ b) ~~ " + (dsContent.length() - 1));
				return dsContent.numberOfLines() - 1;
			} else {
//				System.out.println("~~ c) ~~ " + pos.lineNumber + " " + offset);
				return pos.getLineNumberRE();
			}
//		} catch (BadLocationException e) {
//		}

		//System.out.println("~~ d) ~~ 0 " );
//		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Element#getElementCount()
	 */
	@Override
	public int getElementCount() {
		return dsContent.numberOfLines();
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Element#getElement(int)
	 */
	@Override
	public Element getElement(int index) {
		IDataStorePosition linePosition = dsContent.getPositionByLineNumber(index);
		if (linePosition == null) {
			System.out.println("Error Getting LineNo: " + index + " " + dsContent.numberOfLines());
			return null;
		}
		if (colorFields) {
			return new ColorLineElement(this, linePosition);
		}
		System.out.println(" ~  + lineElement");
		return new LineElement(this, linePosition);
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Element#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		return false;
	}



	@Override
	public void fireUpdate(int type, int firstLine, int lastLine) {
		int where, count;

		IDataStorePosition lastPos = dsContent.getPositionByLineNumber(lastLine, false);
		if (firstLine < 0 || lastLine < 0) {
			where = 0;
			count = dsContent.length();
		} else {
			IDataStorePosition startPos = dsContent.getPositionByLineNumber(firstLine, false);

			where = startPos.getOffset();
			count = lastPos.getOffset() + lastPos.getLineRE().getFullLine().length() - where + 2;
		}

		//System.out.println(" &&& " + type + " " + where + " " + count);
		switch (type) {
		case DataStoreContent.INSERT: super.fireInsertUpdate(new DefaultDocumentEvent(where, count, DocumentEvent.EventType.INSERT)); break;
		case DataStoreContent.DELETE: super.fireRemoveUpdate(new DefaultDocumentEvent(where, count, DocumentEvent.EventType.REMOVE)); break;
		case DataStoreContent.UPDATE:
//			count = lastPos.getOffset() + lastPos.getLine().getFullLine().length() - where + 2;
			super.fireChangedUpdate(new DefaultDocumentEvent(where, Math.min(dsContent.length() - where, count+20), DocumentEvent.EventType.CHANGE));
			break;
		}
	}




	private abstract static class LeafElement implements Element {

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getAttributes()
		 */
		@Override
		public AttributeSet getAttributes() {
			return SimpleAttributeSet.EMPTY;
		}


		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getElementIndex(int)
		 */
		@Override
		public int getElementIndex(int offset) {
			return -1;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getElementCount()
		 */
		@Override
		public int getElementCount() {
			return 0;
		}

		/**
		 * @see javax.swing.text.Element#getElement(int)
		 */
		@Override
		public Element getElement(int index) {
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#isLeaf()
		 */
		@Override
		public boolean isLeaf() {
			return true;
		}
	}

	private static class LineElement extends LeafElement {

		protected FileDocument4 doc;
		protected IDataStorePosition pos;

		public LineElement(FileDocument4 doc, IDataStorePosition pos) {
			super();
			this.doc = doc;
			this.pos = pos;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getDocument()
		 */
		@Override
		public Document getDocument() {
			return doc;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getParentElement()
		 */
		@Override
		public Element getParentElement() {
			return doc;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getName()
		 */
		@Override
		public String getName() {
			return AbstractDocument.ParagraphElementName;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getAttributes()
		 */
		@Override
		public AttributeSet getAttributes() {
			return SimpleAttributeSet.EMPTY;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getStartOffset()
		 */
		@Override
		public int getStartOffset() {
			return (int) pos.getLineStartRE();
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getEndOffset()
		 */
		@Override
		public int getEndOffset() {
			return (int) pos.getLineStartRE() + pos.getLineRE().getFullLine().length() + 1;
		}

	}


	private static class ColorLineElement extends LineElement {

		private AbstractLayoutDetails schema;
		SimpleAttributeSet attr;

		public ColorLineElement(FileDocument4 doc, IDataStorePosition pos) {
			super(doc, pos);
			schema = doc.dsContent.getFileView().getLayout();

			attr = new SimpleAttributeSet();

			attr.addAttributes(mainStyle);
			if (pos.getLineNumberRE() % 2 == 1) {
				StyleConstants.setBackground(attr, Color.YELLOW);
			}
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.file.FileDocument3.LineElement#getElementIndex(int)
		 */
		@Override
		public int getElementIndex(int offset) {
			int schemaIndex = getSchemaIndex();
			if (schemaIndex < 0 || schemaIndex >= schema.getRecordCount()) {
				return 0;
			}

			AbstractRecordDetail rec = schema.getRecord(schemaIndex);
			FieldDetails field;
			int fieldCount = rec.getFieldCount();

			for (int i = 0; i < fieldCount; i++) {
				field = rec.getField(i);
				if (offset >= field.getPos() && offset < field.getPos() + field.getLen()) {
					return i;
				}
			}
			return fieldCount;
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.file.FileDocument4.LineElement#getAttributes()
		 */
		@Override
		public AttributeSet getAttributes() {
			//System.out.println(" ~~> Get Paragraph Attribute ");
			return attr;
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.file.FileDocument3.LineElement#getElementCount()
		 */
		@Override
		public int getElementCount() {
			int schemaIndex = getSchemaIndex();
			if (schemaIndex < 0 || schemaIndex >= schema.getRecordCount()) {
				return 0;
			}
			return schema.getRecord(schemaIndex).getFieldCount() + 1;
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.file.FileDocument3.LineElement#getElement(int)
		 */
		@Override
		public Element getElement(int index) {
			int schemaIndex = getSchemaIndex();
			if (schemaIndex < 0 || schemaIndex >= schema.getRecordCount()
			|| index < 0) {
				//System.out.println(" ~~> Get Field Element (nok)" + index + " " + schemaIndex);
				return null;
			} else if (index >= schema.getRecord(schemaIndex).getFieldCount()) {
				FieldDetails field = schema.getRecord(schemaIndex).getField(schema.getRecord(schemaIndex).getFieldCount() -1);
				int st = getStartOffset() + field.getPos() + field.getLen() - 1;
				//System.out.println(" ~~> Get Field End Element ( ok)" + index + " " + schemaIndex);
				return new LineEndElement(this, st, getEndOffset());
			}
			//System.out.println(" ~~> Get Field Element ( ok)" + index + " " + schemaIndex);
			return new FieldElement(this, schema.getRecord(schemaIndex).getField(index), index);
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.file.FileDocument3.LineElement#isLeaf()
		 */
		@Override
		public boolean isLeaf() {
			return false;
		}


		private int getSchemaIndex() {
			FileView v = doc.dsContent.getFileView();
			return v.getLine(pos.getLineNumberRE()).getPreferredLayoutIdx();
		}
	}


	private static class FieldElement extends LeafElement {

		private SimpleAttributeSet attr;
		private final ColorLineElement parent;
		private final IFieldDetail field;
		private final int index;
		//private final Mock



		public FieldElement(ColorLineElement parent, IFieldDetail field, int idx) {
			super();
			this.parent = parent;
			this.field = field;
			this.index = idx;

			attr = new SimpleAttributeSet();

			//attr.addAttribute(StyleConstants.Foreground, colors[index % colors.length]);
			//attr.addAttributes(fieldStyles[idx % fieldStyles.length]);
			attr.addAttributes(stdAttr[idx % stdAttr.length]);
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getDocument()
		 */
		@Override
		public Document getDocument() {
//			System.out.print("1. ");
			return parent.doc;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getParentElement()
		 */
		@Override
		public Element getParentElement() {
//			System.out.print("2. ");
			return parent;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getName()
		 */
		@Override
		public String getName() {
//			System.out.print("3. ");
			return AbstractDocument.ContentElementName;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getAttributes()
		 */
		@Override
		public AttributeSet getAttributes() {
//			System.out.println(" ~~> Get Field Attribute "
//					+ parent.pos.getLineNumber()
//					+ " " + index + " / " + getStartOffset() + " " + getEndOffset());
//			if (callcount++ % 1000 == 0 && callcount > 0) {
//				System.out.println();
//			}
			return attr;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getStartOffset()
		 */
		@Override
		public int getStartOffset() {
//			if (callcount % 150 == 0) System.out.println();
//			System.out.print("4. " + parent.pos.getLineNumber() + " " + index
//					+ " ~ " + parent.getStartOffset() + " " +  field.getPos() + " \\ " + field.getLen()
//					+ "\t");
//			if (callcount++ > 100000) throw new RuntimeException();
			return parent.getStartOffset() + field.getPos() - 1;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getEndOffset()
		 */
		@Override
		public int getEndOffset() {
//			System.out.print("5. ");
			return getStartOffset() + field.getLen();
		}
	}


	private static class LineEndElement extends LeafElement {

		private final ColorLineElement parent;
		private final int where,end;
		//private final Mock



		public LineEndElement(ColorLineElement parent, int where, int end) {
			super();
			this.parent = parent;
			this.where = where;
			this.end = end;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getDocument()
		 */
		@Override
		public Document getDocument() {
			return parent.doc;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getParentElement()
		 */
		@Override
		public Element getParentElement() {
			return parent;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getName()
		 */
		@Override
		public String getName() {
			return AbstractDocument.ContentElementName;
		}


		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getStartOffset()
		 */
		@Override
		public int getStartOffset() {
			return where;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getEndOffset()
		 */
		@Override
		public int getEndOffset() {
			return end;
		}
	}

}