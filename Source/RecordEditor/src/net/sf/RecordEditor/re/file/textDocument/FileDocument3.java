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

import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.AbstractRecordDetail.FieldDetails;
import net.sf.RecordEditor.re.file.DataStoreContent;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;


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
public class FileDocument3 extends AbstractDocument implements IReDocument, Element {

    /**
     * Name of the attribute that specifies the tab
     * size for tabs contained in the content.  The
     * type for the value is Integer.
     */
   public static final String tabSizeAttribute = "tabSize";


	private final DataStoreContent dsContent;
//	private final boolean colorFields;

//    /**
//     * Constructs a plain text document.  A default model using
//     * <code>GapContent</code> is constructed and set.
//     */
//    public FileDocument3(FileView f, ITextInterface c) {
//        this(new DataStoreContent(f, c), false);
//    }
//
    /**
     * Constructs a plain text document.  A default root element is created,
     * and the tab size set to 8.
     *
     * @param dsc  the container for the content
     */
    public FileDocument3(DataStoreContent dsc, boolean colorFields) {
    	super(dsc);
        putProperty(tabSizeAttribute, Integer.valueOf(8));
        dsContent = dsc;
        dsContent.setDocumentUpdateListner(this);
//        this.colorFields = colorFields;
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
    public Element getParagraphElement(int pos) {
    	IDataStorePosition p = dsContent.getLinePositionByOffset(pos);
        return new LineElement(this, p);
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

//	/* (non-Javadoc)
//	 * @see javax.swing.text.Document#render(java.lang.Runnable)
//	 */
//	@Override
//	public void render(Runnable r) {
//		r.run();
//	}




//    private Vector<Element> added = new Vector<Element>();
//    private Vector<Element> removed = new Vector<Element>();

//    private static boolean isComposedTextAttributeDefined(AttributeSet as) {
//        return ((as != null) &&
//                (as.isDefined(StyleConstants.ComposedTextAttribute)));
//    }


//    static boolean isComposedTextElement(Element elem) {
//        AttributeSet as = elem.getAttributes();
//        return isComposedTextAttributeDefined(as);
//    }

	@Override
	public final DataStoreContent getDataStoreContent() {
		return dsContent;
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
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.Element#getAttributes()
	 */
	@Override
	public AttributeSet getAttributes() {
		return null;
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
//		if (colorFields) {
//			return new ColorLineElement(this, dsContent.getLinePosition(index));
//		}
		return new LineElement(this, dsContent.getPositionByLineNumber(index));
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




	private static class LineElement implements Element {

		protected FileDocument3 doc;
		protected IDataStorePosition pos;

		public LineElement(FileDocument3 doc, IDataStorePosition pos) {
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
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getAttributes()
		 */
		@Override
		public AttributeSet getAttributes() {
			return doc.getAttributes();
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

	private static class ColorLineElement extends LineElement {

		private AbstractLayoutDetails schema;
		public ColorLineElement(FileDocument3 doc, IDataStorePosition pos) {
			super(doc, pos);
			schema = doc.dsContent.getFileView().getLayout();
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

			for (int i = 0; i < rec.getFieldCount(); i++) {
				field = rec.getField(i);
				if (offset >= field.getPos() && offset < field.getPos() + field.getLen()) {
					return i;
				}
			}
			return 0;
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
			return schema.getRecord(schemaIndex).getFieldCount();
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.file.FileDocument3.LineElement#getElement(int)
		 */
		@Override
		public Element getElement(int index) {
			int schemaIndex = getSchemaIndex();
			if (schemaIndex < 0 || schemaIndex >= schema.getRecordCount()
			|| index < 0 || index >= schema.getRecord(schemaIndex).getFieldCount()) {
				return null;
			}
			return new FieldElement(this, schema.getRecord(schemaIndex).getField(index), index);
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.file.FileDocument3.LineElement#isLeaf()
		 */
		@Override
		public boolean isLeaf() {
			return getElementCount() > 0;
		}


		private int getSchemaIndex() {
			FileView v = doc.dsContent.getFileView();
			return v.getLine(pos.getLineNumberRE()).getPreferredLayoutIdx();
		}
	}

	private static Color[] colors = {Color.BLACK, Color.BLUE, Color.DARK_GRAY, Color.ORANGE};
	private static class FieldElement implements Element {

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

			attr.addAttribute(StyleConstants.Foreground, colors[index % colors.length]);
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
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getAttributes()
		 */
		@Override
		public AttributeSet getAttributes() {
			return attr;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getStartOffset()
		 */
		@Override
		public int getStartOffset() {
			return field.getPos();
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getEndOffset()
		 */
		@Override
		public int getEndOffset() {
			return field.getPos() + field.getLen();
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getElementIndex(int)
		 */
		@Override
		public int getElementIndex(int offset) {
			return 0;
		}

		/* (non-Javadoc)
		 * @see javax.swing.text.Element#getElementCount()
		 */
		@Override
		public int getElementCount() {
			return 0;
		}

		/* (non-Javadoc)
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
}