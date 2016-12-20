package net.sf.RecordEditor.utils.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.Utilities;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.layoutWizard.ColumnDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboRendor;

/**
 * Provide Field selection  Swing Object
 * 
 * This class display a file on the screen with a 
 * table of fields. The user can select/un-select fields by
 * clicking on them in the file display 
 *  
 * @author Bruce Martin
 *
 */
public class FixedWidthFieldSelection {
	
	public static final int FIELD_NAME_COLUMN = 0;
	public static final int POSITION_COLUMN = 1;
	public static final int TYPE_COLUMN = 2;
	public static final int DECIMAL_COLUMN = 3;
	public static final int INCLUDE_COLUMN = 4;

	
	private static final Font MONOSPACED_FONT = new Font("Monospaced", Font.PLAIN,  SwingUtils.STANDARD_FONT_HEIGHT * 5 / 4);
			//SwingUtils.getMonoSpacedFont();

	private static String[] columnNames = {"Field Name", "Position", "Type", "Decimal", "Include"};
	
//	private final TreeComboItem[] typeComboTree;

//	private TreeMap<Integer, FieldSelection> columns = new TreeMap<Integer, FieldSelection>();
//	private volatile FieldSelection[] fields = null;
	private final FieldsTableMdl fieldTblMdl;

	//JFrame frame = new JFrame();
	private final JTextPane fieldSelectionTxt = new JTextPane();
	private final JTable fieldTbl;
	private final JScrollPane fieldSelectionPane, fieldTblPane;
	private final JSplitPane splitPanel;
	//JTextArea ta = new JTextArea();
	
	private final IFileSummaryModel fileDtls;
	private final FieldListManager fieldListMgr;
	
	private int maxLineLength;
	
	private final Segment viewSegment;
	
	private MouseAdapter mouseAction = new MouseAdapter() {
		
		@Override
		//public void mouseReleased(MouseEvent e) {
		public void mouseClicked(MouseEvent e) { 
			int cpos = fieldSelectionTxt.getCaretPosition();
			Element paragraphElement = fieldSelectionTxt.getStyledDocument().getParagraphElement(cpos);
			if (paragraphElement != null ) {					
				int lineStartOffset = paragraphElement.getStartOffset();
				int col = cpos - lineStartOffset;
//				System.out.println("==> " + e.getY() + " " + cpos
//						+ " " + lineStartOffset
//						+ " : " + col);
				
				if (col != 0) {
					fieldListMgr.swapColumnSelection(col, fileDtls);
					col += 1;
					ColumnDetails[] fieldArray = fieldListMgr.getFieldArray();
					for (int i = 0; i < fieldArray.length; i++) {
						if (fieldArray[i].getStart() == col) {
							fieldTblMdl.setHeadingUp(i, fieldArray);
							if (i > 0) {
								fieldTblMdl.setHeadingUp(i-1, fieldArray);
							}
							setDocText();
							break;
						} else if (fieldArray[i].getStart() > col && i> 0) {
							fieldTblMdl.setHeadingUp(i-1, fieldArray);
							setDocText();
							break;
						}
					}
					fieldSelectionTxt.repaint();
					fieldTblMdl.fireTableDataChanged();
				}
			}
		}
	};
	
	/**
	 * Provide Field selection  Swing Object
	 * @param typeComboTree list of types
	 * @param fileDtls File Content Details
	 */
	public FixedWidthFieldSelection(TreeComboItem[] typeComboTree, IFileSummaryModel fileDtls) {

//		this.typeComboTree = typeComboTree;
		this.fileDtls = fileDtls;
		this.fieldTblMdl = new FieldsTableMdl();
		this.viewSegment = new Segment(new char[1], 0, 0);
		this.fieldListMgr = fileDtls.getFieldListManager();
		
		setMaxLineLength();
		fieldListMgr.setupColumns( fileDtls.getColumnDetails());
		
		init_400_setupSelectionText();
		
			
		fieldTbl = new JTable(fieldTblMdl);

		fieldTbl.getColumnModel().getColumn(FIELD_NAME_COLUMN).setPreferredWidth(SwingUtils.STANDARD_FONT_WIDTH * 30);

		TableColumn typeColumn = fieldTbl.getColumnModel().getColumn(TYPE_COLUMN);
		typeColumn.setCellRenderer(new TreeComboRendor(typeComboTree));
		typeColumn.setCellEditor(new TreeComboRendor(typeComboTree));
		typeColumn.setPreferredWidth(SwingUtils.STANDARD_FONT_WIDTH * 15);
		
		TableColumn includeColumn = fieldTbl.getColumnModel().getColumn(INCLUDE_COLUMN);
		includeColumn.setCellRenderer(new CheckBoxTableRender());
		includeColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
		
		fieldTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Common.stopCellEditing(fieldTbl);

		//setSize(fieldSelectionTxt, fieldTbl);
		fieldSelectionPane = new JScrollPane(fieldSelectionTxt);
		fieldTblPane = new JScrollPane(fieldTbl);
		
		setSize(fieldTblPane, fieldSelectionPane);

		splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				this.fieldTblPane,
				this.fieldSelectionPane );
		
		setSize(fieldTblPane, fieldSelectionPane);
		//splitPanel.set
		splitPanel.setResizeWeight(0.50);

	}

	private void setSize(JComponent component1, JComponent component2) {
		Dimension tblPref = component1.getPreferredSize();
		Dimension txtPref = component2.getPreferredSize();
//		System.out.println("@@@@@ > " 
//				+ tblPref.height 
//				+ " " + txtPref.height);
		int h = ( Math.max(0, tblPref.height)
				+ Math.max(0, txtPref.height)) / 2;
		
		component1.setPreferredSize(new Dimension(tblPref.width, h));
		component2.setPreferredSize(new Dimension(txtPref.width, h));
	}

//	private void setSize() {
//		Dimension tblPref = fieldTblPane.getPreferredSize();
//		Dimension txtPref = fieldSelectionPane.getPreferredSize();
////		System.out.println("@@@@@ > " 
////				+ tblPref.height 
////				+ " " + txtPref.height);
//		int h = ( Math.max(0, tblPref.height)
//				+ Math.max(0, txtPref.height)) / 2;
//		
//		fieldTblPane.setPreferredSize(new Dimension(tblPref.width, h));
//		fieldSelectionPane.setPreferredSize(new Dimension(txtPref.width, h));
//	}


	private void init_400_setupSelectionText() {
		
		DefaultCaret dc = new DefaultCaret();
		dc.setSize(0, 0);

		setupViewSegment();
		fieldSelectionTxt.setFont(MONOSPACED_FONT);
		fieldSelectionTxt.setEditorKit(new FieldDisplayEditorKit());
		
		fieldSelectionTxt.setHighlighter(null);
		fieldSelectionTxt.setEditable(false);
		fieldSelectionTxt.setCaret(dc);
		
		setDocText();
		
		fieldSelectionTxt.addMouseListener(mouseAction);
	}
	
	public final void reloadFromFileModel() {
		fieldListMgr.setupColumns(fileDtls.getColumnDetails());
		setupViewSegment();
		setDocText();
		
		fieldSelectionTxt.repaint();
		this.fieldTblMdl.fireTableDataChanged();
		setSize(fieldTblPane, fieldSelectionPane);
	}


	private void setupViewSegment() {
		setMaxLineLength();
		
		String v = this.fileDtls.getFileDisplay();
		int len = v.length() + maxLineLength * 2 + 2;
		viewSegment.array = new char[len];
		viewSegment.count = len;
		
		StringBuilder b = newRulerSB(maxLineLength);
		for (int i = 0; i < maxLineLength; i++) {
			viewSegment.array[maxLineLength + i + 1] = b.charAt(i);
		}
		
		System.arraycopy(v.toCharArray(), 0, viewSegment.array, maxLineLength*2 + 2, v.length());
		Arrays.fill(viewSegment.array, 0, maxLineLength, ' ');
		viewSegment.array[maxLineLength] = '\n';
		viewSegment.array[maxLineLength*2+1] = '\n';
	}

	private void setMaxLineLength() {
		this.maxLineLength = fileDtls.getMaxLineLength();
		fieldListMgr.maxLineLength =  maxLineLength;
	}




	private void setDocText() {
		fieldSelectionTxt.setText(viewSegment.toString());
		fieldSelectionTxt.setCaretPosition(0);
	}
	
	/**
	 * Get the fields selected by the user
	 * @return fields selected by the user
	 */
	public final ColumnDetails[] getFieldSelection() {
		return fieldListMgr.getFieldSelection();
	}

	
	/**
	 * Get Display Pane
	 * @return Display Pane
	 */
	public JSplitPane getPane() {
		
		return splitPanel;
	}
	

	@SuppressWarnings("serial")
	private class FieldDisplayEditorKit extends StyledEditorKit {
		
		public static final String NAME = "color";

		private ViewFactory viewFactory = new ViewFactory() {
			@Override public View create(Element elem) {
				return new FieldDisplayDocView(elem); 
			}
		};
		
	    @Override
		public ViewFactory getViewFactory() {
		    return viewFactory;
		}

		@Override
		public String getContentType() {
		    return NAME;
		}

	}
	
	
	private static final Color HEADER_BACKGROUND = new Color(255, 255, 230); //Color.YELLOW;
	private static final Color HEADER_HIGHLIGHTED_BACKGROUND = new Color(235, 235, 255); //Color.CYAN;
	/**
	 * Highlight the Text for the various fields
	 * @author Bruce Martin
	 *
	 */
	private final class FieldDisplayDocView extends PlainView {


		public FieldDisplayDocView(Element elem) {
			super(elem);
		}
		
		@Override
		protected int drawSelectedText(Graphics g, int x, int y, int p0, int p1) throws BadLocationException {
			return drawUnselectedText(g, x, y, p0, p1);
		}

		protected int drawUnselectedText(Graphics g, int x, int y, int p0, int p1)
				throws BadLocationException {

			try {
				Document doc = super.getDocument();
				Element element = super.getElement();
				int elIdx = element.getElementIndex(p0);
				if (elIdx < 0 || elIdx >= element.getElementCount() || elIdx == 1) {
					return super.drawSelectedText(g, x, y, p0, p1);
				}
				int startOffset = element.getElement(elIdx).getStartOffset();
				int pos = p0 - startOffset; 
				
				Segment segment = getLineBuffer();     
				   
				int t1 = p0;
				doc.getText(p0 - pos, p1 - p0 + pos, segment);       
				  

				int count = segment.count;

				
				boolean highLight = true;
				//Iterator<Integer> iterator = columns.keySet().iterator();
				ColumnDetails[] flds = fieldListMgr.getFieldArray();
				int idx = 0;
				
				int next = 0;
				int last = 0;
				Color highlightColor = Color.CYAN;
				Color highlightBackgroundColor = Color.BLUE;
				Color normalColor = Color.BLACK;
				Color normalBackgroundColor = null;
				if (startOffset == 0) {
					highlightColor = Color.BLUE;
					highlightBackgroundColor = HEADER_HIGHLIGHTED_BACKGROUND;
					normalColor = Color.BLACK;
					normalBackgroundColor = HEADER_BACKGROUND;
				}
				if (flds.length > 0) {
					next = flds[idx++].getStart() - 1;
					while (next < pos && idx < flds.length) {
						next = flds[idx++].getStart() - 1;
					}
					while (pos < count) {
						if (highLight) {
							x = writeText(g, x, y, t1, next - last, segment, highlightColor, highlightBackgroundColor);
						} else {
							x = writeText(g, x, y, t1, next - last, segment, normalColor, normalBackgroundColor);						
						}
						
						highLight = ! highLight;
						if (idx >= flds.length) { break; }
						last = next;
						next = flds[idx++].getStart() - 1;
					}
				}
				if (highLight) {
					x = writeText(g, x, y, t1, count - next, segment, highlightColor, highlightBackgroundColor);
				} else {
					x = writeText(g, x, y, t1, count - next, segment, normalColor, normalBackgroundColor);						
				}

				return x;
			} catch (Exception e) {
				return super.drawUnselectedText(g, x, y, p0, p1);
			}
		}
		
		
		private int  writeText(Graphics g, int x, int y, int t1, int count, Segment segment, Color foreground, Color backgroundColor) {

			if (count > 0) {
				segment.count = count;
				//System.out.print((backgroundColor != null) + " " + y + ", " + x + " : " + segment.offset + " " + count);
				if (backgroundColor != null/* && dh != null*/) {
					g.setColor(backgroundColor);
					FontMetrics fontMetrics = g.getFontMetrics();
					int fontHeight = fontMetrics.getHeight();
		
					int tabbedTextWidth = Utilities.getTabbedTextWidth(segment, fontMetrics, x, this, t1);
					g.fillRect(x, y - Math.max(0, y - 1) % fontHeight, tabbedTextWidth, fontHeight);
					//System.out.println(" ~ " + tabbedTextWidth + " ~ " + x + ", " + y + " ~ " + (y - (y - 1) % fontHeight) + " " + fontHeight);
				}
				
				if (foreground == null) {
					g.setColor(Color.BLACK);
				} else {
					g.setColor(foreground);
				}
				//System.out.println();
				x = Utilities.drawTabbedText(segment, x, y, g, this, t1);
				segment.offset += count;
			}
			return x;
		}
	}
	
//	/**
//	 * Field Details
//	 * @author Bruce Martin
//	 *
//	 */
//	public final static class FieldSelection {
//		public final int start; 
//		public int type, decimal, length;
//		public String name = "";
//		public boolean include = true;
//		
//		public FieldSelection(int column, int type) {
//			super();
//			this.getStart() = column;
//			this.type = type;
//		}
//		
//	}
	
	@SuppressWarnings({ "serial" })
	private final class FieldsTableMdl extends AbstractTableModel {

		@Override
		public int getRowCount() {
			return fieldListMgr.columns.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			ColumnDetails fld = fieldListMgr.getFieldArray()[rowIndex];
			switch (columnIndex) {
			case FIELD_NAME_COLUMN:		return fld.name;
			case POSITION_COLUMN:		return fld.getStart();
			case TYPE_COLUMN:			return fld.type;
			case INCLUDE_COLUMN:		return fld.include;
		}
			return fld.decimal;
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex != POSITION_COLUMN;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			ColumnDetails fld = fieldListMgr.getFieldArray()[rowIndex];
			switch (columnIndex) {
			case FIELD_NAME_COLUMN:		
				setValueAt_FieldName(aValue, rowIndex);
				return;
			case TYPE_COLUMN:			fld.type  = getInt(aValue, fld.type);			break;
			case DECIMAL_COLUMN:		fld.decimal = getInt(aValue, fld.decimal);
			case INCLUDE_COLUMN:		
				if (aValue instanceof Boolean) {
					fld.include = ((Boolean) aValue).booleanValue();
				}
			
			}
		}
		
		private int getInt(Object o, int defaultVal) {
			int ret = defaultVal;
			if (o instanceof Integer) {
				ret = (Integer) o;
			} else if (o instanceof TreeComboItem) {
				ret = ((TreeComboItem) o).key;
			} else if (o != null) {
				ret = Integer.parseInt(o.toString());
			}
			return ret;
		}
			
	
		
		public void setValueAt_FieldName(Object aValue, int rowIndex) {
			ColumnDetails[] flds = fieldListMgr.getFieldArray();
			String valStr = aValue.toString();
			
			flds[rowIndex].name = valStr;
			
			setHeadingUp(rowIndex, flds);
			
			setDocText();
			fieldSelectionTxt.repaint();
		}

		private void setHeadingUp(int rowIndex, ColumnDetails[] flds) {
			int end =   maxLineLength;
			int adj = 0;
			if (rowIndex < flds.length - 1) {
				end =  flds[rowIndex+1].getStart() - 2;
				adj = 1;
			}

			
			String valStr = flds[rowIndex].name;
			int column = flds[rowIndex].getStart() - 1;
			int nameEnd = Math.min(column + valStr.length() - 1, end - 1);
			for (int i = column; i <= nameEnd; i++) {
				viewSegment.array[i] = valStr.charAt(i - column);
			}
			if ( nameEnd < end  - 1 + adj) {
				Arrays.fill(viewSegment.array, nameEnd + 1, end + adj, ' ');
			}
		}
		
	}
	
	/**
	 * Class to be used in testing this FixedWidthFieldSelection
	 * 
	 * @author Bruce Martin
	 *
	 */
	public static class TestDetails {
		public final FixedWidthFieldSelection fieldSelection;

		public TestDetails(FixedWidthFieldSelection fieldSelection) {
			super();
			this.fieldSelection = fieldSelection;
		}
		
		public TableModel getFieldTableMdl() {
			return fieldSelection.fieldTblMdl;
		}
		
		public String getFieldDisplayText() {
			return fieldSelection.fieldSelectionTxt.getText();
		}
		
//		public Document getFieldDocument() {
//			return fieldSelection.fieldSelectionTxt.getDocument();
//		}
		
		public void doClickOnField(int caretPosition) {
			fieldSelection.fieldSelectionTxt.setCaretPosition(caretPosition);
			fieldSelection.mouseAction.mouseClicked(null);
		}
	}
	
	/**
	 * Holds File Summary details used by the FixedWidthFieldSelection class
	 * @author Bruce Martin
	 *
	 */
	public static interface IFileSummaryModel {
		public int[][] getColumnDetails();
		
		public String getFileDisplay();
		
		public int getMaxLineLength();
		
		public int getType(int col, int len);
		
		public FieldListManager getFieldListManager();
		
//		public int getFieldCount();
//		
//		public void swapColumnSelection(int col);
//		
//		public void setupColumns();
//
//		public ColumnDetails[] getFieldArray();
	}
	
	public static class FieldListManager {
		private TreeMap<Integer, ColumnDetails> columns = new TreeMap<Integer, ColumnDetails>();
		private ColumnDetails[] fields = null;
		private int maxLineLength;


		private void swapColumnSelection(int col, IFileSummaryModel parent) {
			
			col = col + 1;
			Integer key = col;
			fields = null;
			
			if (columns.containsKey(key)) {
				columns.remove(key);
			} else {
				Entry<Integer, ColumnDetails> higherEntry = columns.higherEntry(key);
				int end = higherEntry== null? maxLineLength : higherEntry.getValue().getStart();
				
				columns.put(key, new ColumnDetails(col, parent.getType(col, end - col)));
			}
		}
		
		private void setupColumns(int[][] cols) {
			//int[][] cols = parent.getColumnDetails();
			
			fields = null;
			
			this.columns.clear();
			if (cols == null || cols.length <  2 || cols[0].length == 0) {
				this.columns.put(1, new ColumnDetails(1, Type.ftChar));
			} else {
				for (int i = 0; i < cols[0].length; i++) {
					int pos = cols[0][i] + 1;
					this.columns.put(pos, new ColumnDetails(pos, cols[1][i]));
				}
			}
		}
		
		private final ColumnDetails[] getFieldArray() {
			if (fields == null) {
				fields = columns.values().toArray(new ColumnDetails[columns.size()]);
			}
			return fields;
		}
		
		/**
		 * Get the fields selected by the user
		 * @return fields selected by the user
		 */
		public final ColumnDetails[] getFieldSelection() {
			ColumnDetails[] ret = getFieldArray();
			
			if (ret.length > 0) {
				for (int i = 1; i < ret.length; i++) {
					ret[i-1].length = ret[i].getStart() - ret[i-1].getStart();
				}
				ret[ret.length - 1].length = maxLineLength - ret[ret.length - 1].getStart() + 1;
			}
			return ret;
		}

	}
	
	public static String newRuler(int length) {
		return newRulerSB(length).toString();
	}
	
	private static StringBuilder newRulerSB(int length) {
		String rule = "----+---- ";
		
		StringBuilder b = new StringBuilder(length);
		int i = 1;
		String iStr;
		int ruleLen = rule.length() - 1;
		while (b.length() + ruleLen < length) {
			iStr = Integer.toString(i++);
			b.append(rule.substring(0, ruleLen - iStr.length() + 1))
			 .append(iStr);
		}

		if (b.length() < length) {
			b.append(rule.substring(0, length - b.length()));
		}
		return b;
	}
	/**
	 * Create a New 'Test' object
	 * 
	 * @param typeComboTree Type-Combo Details
	 * @param fileDetails file details
	 * 
	 * @return Testing interface class
	 */
	public static TestDetails newTestDetails(TreeComboItem[] typeComboTree, IFileSummaryModel fileDetails) {
		return new TestDetails(new FixedWidthFieldSelection(typeComboTree, fileDetails));
	}
	
//	public static void main(String[] args) {
//		IFileSummaryModel fDtls = new IFileSummaryModel() {
//			FieldListManager mgr = new FieldListManager();
//			@Override
//			public int[][] getColumnDetails() {
//				return null;
//			}
//
//			@Override
//			public String getFileDisplay() {
//				return 	  "123456789 123456789 123456789 123456789 123456789 \n"
//						+ "123456789 123456789 123456789 123456789 123456789 \n"
//						+ "123456789 123456789 123456789 123456789 123456789 \n"
//						+ "123456789 123456789 123456789 123456789 123456789 \n"
//						+ "123456789 123456789 123456789 123456789 123456789 \n";
//			}
//
//			@Override
//			public int getMaxLineLength() {
//				return 51;
//			}
//
//			@Override
//			public int getType(int col, int len) {
//				return Type.ftChar;
//			}
//
//			@Override
//			public FieldListManager getFieldListManager() {
//				return mgr;
//			}
//			
//		};
//
//		AbsRowList       typeList = new AbsRowList(0, 1, false, false).loadData(
//	    		ExternalConversion.getTypes(0)
//	    );
//		FixedWidthFieldSelection fw = new FixedWidthFieldSelection(
//				BuildTypeComboList.getTextTypes(typeList), fDtls); 
//		JFrame frame = new JFrame();
//		
//		
//		frame.add(fw.getPane());
//
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.pack();
//		frame.setVisible(true);
//	}

}
