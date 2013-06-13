package net.sf.RecordEditor.edit.display;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;


import net.sf.JRecord.Details.AbstractRecordDetail.FieldDetails;
import net.sf.RecordEditor.edit.display.Action.GotoLineAction;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.file.DataStoreContent;
import net.sf.RecordEditor.re.file.FileDocument3;
import net.sf.RecordEditor.re.file.FileDocument4;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.TextLineNumber;

public class DocumentScreen extends BaseDisplay implements DocumentListener, AbstractCreateChildScreen {

	private final JTextComponent textDisplay;
	private final DataStoreContent content;
	private int popupRow;
	private final boolean colorFields;

	private int lastRow = -1;
	private LineFrame childFrame;

	private KeyListener rowChangeListner = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {

    		checkForTblRowChange(getCurrRow());
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}
	};

	public DocumentScreen(FileView view, boolean colorFields) {
		super("Document View", view, false, false, false, false, false, NO_LAYOUT_LINE);

		setAllowPaste(false);
		setJTable(new JTable());

		this.content = view.asDocumentContent();
		this.colorFields = colorFields;

		if (content == null) {
			throw new RuntimeException("File does not support Text Editting");
		}

		if (colorFields) {
			JTextPane jEditorPane = new JTextPane();
			FileDocument4 doc = new FileDocument4(content, colorFields);
			printElements(doc.getRootElements(), 0);

			textDisplay = jEditorPane;
			jEditorPane.setDocument(doc);
		} else {
			textDisplay = new JTextArea();
			textDisplay.setDocument(new FileDocument3(content, colorFields));
			textDisplay.setFont(SwingUtils.getMonoSpacedFont());
			//printElements(textDisplay.getDocument().getRootElements(), 0);
		}

		textDisplay.getDocument().addDocumentListener(this);

	    actualPnl.addComponent(1, 3, BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                TextLineNumber.getTextLineNumber(textDisplay, Math.min(3, (int) Math.log(view.getRowCount() + 1))));

	    defPopup();

	    //printElements(textDisplay.getDocument().getRootElements(), 0);
	}

	private void defPopup() {

		MenuPopupListener popup = //MenuPopupListener.getEditMenuPopupListner(mainActions);
        new MenuPopupListener(null, true, null) {

        	/* (non-Javadoc)
			 * @see net.sf.RecordEditor.utils.MenuPopupListener#mouseReleased(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseReleased(MouseEvent e) {

        		IDataStorePosition p = getPosition(e.getPoint());
        		if (p != null) {
        			checkForTblRowChange(p.getLineNumber());
        		}
				super.mouseReleased(e);
			}

			protected final boolean isOkToShowPopup(MouseEvent e) {

        		IDataStorePosition p = getPosition(e.getPoint());
        		popupRow = -1;
        		if (p != null) {
        			//mainPopupCol = tblDetails.columnAtPoint(e.getPoint());
        			popupRow = p.getLineNumber();
        		}

        		textDisplay.getHighlighter().removeAllHighlights();
                return true;
            }

         };
        popup.getPopup().add( new GotoLineAction(this, fileView));

        textDisplay.addMouseListener(popup);
	}


	@Override
	public void setScreenSize(boolean mainframe) {

		DisplayFrame parentFrame = getParentFrame();
		parentFrame.bldScreen();

        parentFrame.setToMaximum(false);
		parentFrame.setVisible(true);

	}


   	private IDataStorePosition getPosition(Point p) {
		int where = textDisplay.viewToModel(p);

		if (where >= 0) {
			try {
				return content.createTempPosition(where);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	@Override
	protected int getInsertAfterPosition() {
		return getCurrRow();
	}

	@Override
	public void fireLayoutIndexChanged() {

	}

	@Override
	public int getCurrRow() {
		return getLineFromPos(textDisplay.getCaretPosition());
	}

    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getPopupPosition()
	 */
	@Override
	protected int getPopupPosition() {
		return popupRow;
	}



	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum)  {
		if (newRow >= 0 && newRow < fileView.getRowCount()) {
			IDataStorePosition p = content.getLinePosition(newRow, false);
			int fpos = 0;
			int len = 1;


			if (layoutId >= 0 && layoutId < layout.getRecordCount()
			&& fieldNum >= 0 && fieldNum < layout.getRecord(layoutId).getFieldCount()) {
				FieldDetails field = layout.getRecord(layoutId).getField(fieldNum);
				if (field.isFixedFormat()) {
					fpos = field.getPos() - 1;
					len = field.getLen();
				} else if (p.getLine() != null){
					int next = 0;
					for (int i = 0; i <= fieldNum; i++) {
						fpos = next;
						len = p.getLine().getFieldValue(0, i).asString().length();
						next += len;
					}
					fpos += fieldNum;
				}
			}
			int start = p.getOffset() + fpos;
			Highlighter h = textDisplay.getHighlighter();

			textDisplay.getCaret().setDot(start);
			checkForTblRowChange(newRow);

			h.removeAllHighlights();

			try {
				h.addHighlight( start ,
								start  + len,
								DefaultHighlighter.DefaultPainter);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		return new DocumentScreen(view, colorFields);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getSelectedRowCount()
	 */
	@Override
	public int getSelectedRowCount() {
		int en = getLineFromPos(textDisplay.getSelectionEnd());
		if (en < 0) return 0;
		return 	en 	- getLineFromPos(textDisplay.getSelectionStart()) + 1;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getSelectedRows()
	 */
	@Override
	public int[] getSelectedRows() {
		int en = getLineFromPos(textDisplay.getSelectionEnd());
		int st = getLineFromPos(textDisplay.getSelectionStart());

		if (en < 0) {
			return new int[0];
		}
		int[] ret = new int[en - st + 1];

		for (int i = st; i <= en; i++) {
			ret[i-st] = i;
		}

		return ret;
	}




	private int getLineFromPos(int pos) {
		try {
			IDataStorePosition p = content.createPosition(pos);
			return p.getLineNumber();
		} catch (Exception e) {
		}
		return -1;

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {

		textDisplay.getHighlighter().removeAllHighlights();
		switch (action) {
		case ReActionHandler.PASTE_RECORD_POPUP:
			fileView.pasteLines(getInsertPos(true));												break;
		case ReActionHandler.PASTE_RECORD_PRIOR_POPUP:	fileView.pasteLines(getInsertPos(true) - 1);break;
    	case ReActionHandler.DELETE_BUTTON:				deleteBtn();								break;
    	case ReActionHandler.DELETE_RECORD_POPUP:		fileView.deleteLine(popupRow);				break;
    	case ReActionHandler.REPEAT_RECORD_POPUP:
    		if (popupRow >= 0) {
    			fileView.repeatLine(popupRow);
    		}
    		break;
		case ReActionHandler.INSERT_RECORDS_POPUP:	insertLine(0, true);							break;
		case ReActionHandler.INSERT_RECORD_PRIOR_POPUP:	insertLine(-1, true);						break;

//		case ReActionHandler.FIND:
//			if (textDisplay instanceof JXEditorPane) {
//				ReFrame ff = new ReFrame(fileView.getFileNameNoDirectory(), "Text Find", fileView);
//				ff.getContentPane().add(new JXFindBar(((JXEditorPane) textDisplay).getSearchable()));
//				ff.setDefaultCloseOperation(ReFrame.DISPOSE_ON_CLOSE);
//				ff.pack();
//				try {
//					ff.setMaximum(false);
//				} catch (PropertyVetoException e) {
//				}
//				ff.setVisible(true);
//			}
//			break;
		default: super.executeAction(action);
		}
	}

	private void deleteBtn() {
		int st = textDisplay.getSelectionStart();
		int en = textDisplay.getSelectionEnd();

		try {
			content.remove(st, Math.max(1, en - st));
		} catch (BadLocationException e) {
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#insertLine(int)
	 */
	@Override
	public void insertLine(int adj, boolean popup) {
		if (fileMaster.getTreeTableNotify() == null) {
			insertLine_101_FlatFile(adj, popup);
		} else {
			super.insertLine(adj);
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
		switch (action) {
		case ReActionHandler.REPEAT_RECORD_POPUP:
			return true;
		}
		return super.isActionAvailable(action);
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		textDisplay.repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		textDisplay.repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}


	/*
	 * -----------------------------------------------------------------------------------------
	 *   Start of AbstractCreateChildScreen methods
	 * -----------------------------------------------------------------------------------------
	 */

	/**
	 * @see net.sf.RecordEditor.edit.display.AbstractCreateChildScreen#createChildScreen()
	 */
	@Override
	public AbstractFileDisplay createChildScreen(int pos) {

		childFrame = new LineFrame("ChildRecord:", super.fileView, Math.max(0, getCurrRow()), false);
		textDisplay.removeKeyListener(rowChangeListner);
		textDisplay.addKeyListener(rowChangeListner);
		return childFrame;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getChildFrame()
	 */
	@Override
	public AbstractFileDisplay getChildScreen() {
		return childFrame;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#removeChildScreen()
	 */
	@Override
	public void removeChildScreen() {
		if (childFrame != null) {
			childFrame.doClose();
		}
		childFrame = null;
		textDisplay.removeKeyListener(rowChangeListner);
		lastRow = -1;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.common.AbstractRowChangedListner#checkForRowChange(int)
	 */
	@Override
	public void checkForTblRowChange(int row) {

		if (lastRow != row) {
			if (childFrame != null) {
	    		childFrame.setCurrRow(row);
	    	}

			lastRow = row;
		}
	}

	/*
	 * -----------------------------------------------------------------------------------------
	 *   End of AbstractCreateChildScreen methods
	 * -----------------------------------------------------------------------------------------
	 */


	private static void printElements(Element[] el, int id) {
		for (int i = 0; i < el.length; i++) {
			System.out.println("-");
			printElement(el[i], id+1);
		}
	}

	private static void printElement(Element el, int id) {

		String ss = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, id+1);
		System.out.println();
		System.out.println(ss + el.getName() + " ! " + el.getStartOffset() + " " + el.getEndOffset());
		System.out.print(ss +". ");

		if (el.getAttributes() != null) {
			Enumeration<?> ee = el.getAttributes().getAttributeNames();

			while (ee.hasMoreElements()) {
				Object nextElement = ee.nextElement();
				System.out.print(" " + nextElement + "~" + el.getAttributes().getAttribute(nextElement)
						//+ '/' + el.getAttributes().getAttribute(nextElement).getClass().getName()
						);
			}
		}

		for (int i = 0; i < el.getElementCount(); i++) {
			printElement(el.getElement(i), id+1);
		}
	}

}
