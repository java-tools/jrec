package net.sf.RecordEditor.edit.display;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.Action.GotoLineAction;
import net.sf.RecordEditor.edit.display.models.LineModel;
import net.sf.RecordEditor.edit.display.util.MovementBtnPnl;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.HexThreeLineField;
import net.sf.RecordEditor.utils.swing.HexThreeLineRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public abstract class BaseLineFrame extends BaseLineAsColumn
implements TableModelListener, TreeModelListener {


	protected int stdCellHeight;
	protected int threeLineHeight;
	protected JCheckBox oneLineHex;
	protected JTextField lineNum = new JTextField();
	//protected JButton[] btn;
	protected MovementBtnPnl btnPanel;
	private String[] moveHints = LangConversion.convertArray(LangConversion.ST_FIELD_HINT, "RecordBtns", new String[] {
		        	"Start of File", "Previous Record",
		        	"Next Record",   "Last Record"});
	private String[] moveHints6 = {
			moveHints[0], moveHints[1],
			 LangConversion.convert(LangConversion.ST_FIELD_HINT, "Parent Record"),
			 LangConversion.convert(LangConversion.ST_FIELD_HINT, "Child Record"),
			moveHints[2], moveHints[3]};
	protected JTextArea fullLine;
	private HexThreeLineField hexLine;
	private double fullLineHeight;
	private ActionListener listner;
	protected LineModel record;
	private TableCellRenderer defaultHexRendor;
	private TableCellRenderer hexRendor = null;

	private int popupRow = 0;
//	private JMenu[] showFieldMenus;
//	private JMenu currentShowFields = null;
//	private MenuPopupListener popupListner;



	public BaseLineFrame(String formType, FileView viewOfFile, boolean primary,
			boolean fullLine, final boolean changeRow) {
		super(formType, viewOfFile, primary, fullLine, changeRow);

		AbstractAction[] actions = {
	            new ReAbstractAction("Hide Column") {
                    public void actionPerformed(ActionEvent e) {
                    	hideRow(popupRow);
                      }
                },
                new GotoLineAction(this, fileView)
		};

		popupListner = new MenuPopupListener(actions, true, getJTable()) {
		   /**
		     * @see MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		     */
		    public void mousePressed(MouseEvent e) {
		    	super.mousePressed(e);
		    	JTable table = getJTable();

	      		int col = table.columnAtPoint(e.getPoint());
	            popupRow = table.rowAtPoint(e.getPoint());

	            if (popupRow >= 0 && popupRow != table.getEditingRow()
	        	&&  col >= 0 && col != table.getEditingColumn()
	        	&& cellEditors != null && col < cellEditors.length && cellEditors[col] != null
	        	) {
	            	table.editCellAt(popupRow, col);
	            }

		    }
		};
	}


	protected void init_200_setupFields(ImageIcon[] icon, ActionListener btnActions,
   		 	final boolean changeRow) {
	    int currLayout;
	    listner = btnActions;

	    btnPanel = new MovementBtnPnl(icon, changeRow, btnActions);

		if (icon.length > 4) {
			moveHints = moveHints6;
		}

		init_210_setupTable();

		setFullLine();

		currLayout = record.getCurrentLayout();

		if (currLayout == Common.NULL_INTEGER) {
			currLayout = fileView.getCurrLayoutIdx();
		}

		if (currLayout != record.getCurrentLayout()) {
			record.setCurrentLayout(currLayout);
			record.fireTableDataChanged();
		}
		setLayoutIndex(currLayout);

		fileView.addTableModelListener(this);
		fileView.addTreeModelListener(this);
		setColWidths();

		actualPnl.setHelpURLre(Common.formatHelpURL(Common.HELP_SINGLE_RECORD));


//		if (changeRow) {
//			for (i = 0; i < btn.length; i++) {
//				btn[i] = new JButton("", icon[i]);
//				btnPanel.add(btn[i]);
//				btn[i].addActionListener(listner);
//				btn[i].setToolTipText(moveHints[i]);
//			}
//			setDirectionButtonStatus();
//		} else {
//			for (i = 0; i < btn.length; i++) {
//				btn[i] = new JButton("", icon[i]);
//			}
//		}
	}

	/**
	 * Setup line table
	 */
	protected void init_210_setupTable() {


		setJTable(new LineAsColTbl(record, record.firstDataColumn, true));


		tblDetails.addMouseListener(popupListner);
		popupListner.setTable(tblDetails);
		setRowHeight();

		if (super.showHexOptions(layout, fileMaster)) {
		    TableColumn tc = tblDetails.getColumnModel().getColumn(record.hexColumn);

		    stdCellHeight   = tblDetails.getRowHeight();
		    threeLineHeight = (stdCellHeight + 3) * 3;

		    oneLineHex = new JCheckBox();
		    oneLineHex.setSelected(true);
		    oneLineHex.addActionListener(listner);

			defaultHexRendor = tc.getCellRenderer();
			//defaultHexEditor = tc.getCellEditor();
			hexLine = new HexThreeLineField(this.layout.getFontName());
			fullLineHeight = BasePanel.GAP5;
			fullLine = hexLine;
		} else if (layout.isBinCSV()) {
			hexLine = new HexThreeLineField(this.layout.getFontName());
			fullLineHeight = BasePanel.GAP5;
			fullLine = hexLine;
		} else {
		    fullLine = new JTextArea();
		    fullLineHeight = BasePanel.GAP3;
		}
		fullLine.setEditable(false);
	}

	/**
	 * Layout the screen
	 * @param btnPanel panel of buttons
	 */
	public void init_300_setupScreen(final boolean changeRow) {

		if (! isTree()) {
			actualPnl.addLineRE("Record", lineNum);
		}

		if (fileMaster.isBinaryFile()) {
		    actualPnl.addLineRE("1 line Hex", oneLineHex);
		}
		//pnl.setGap(BasePanel.GAP1);

		actualPnl.addComponentRE(1, 5, BasePanel.FILL,
		        BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        tblDetails);


		if (changeRow) {
			if (! this.layout.isXml()) {
			    actualPnl.addComponentRE(1, 5, fullLineHeight, BasePanel.GAP,
			        BasePanel.FULL, BasePanel.FULL, new JScrollPane(fullLine));
			}

			setDirectionButtonStatus();
			actualPnl.addComponentRE(1, 5, BasePanel.PREFERRED, BasePanel.GAP,
					BasePanel.FULL, BasePanel.FULL, btnPanel);
		} else {
			Common.calcColumnWidths(tblDetails, 0);

			int w1 =  84 * SwingUtils.CHAR_FIELD_WIDTH;
			int w2 = tblDetails.getPreferredSize().width;
			if (w2 > 0 && w1 > w2) {
				w1 = w2;
			}
			Dimension d = new Dimension(w1 +  6 * SwingUtils.CHAR_FIELD_WIDTH, actualPnl.getPreferredSize().height);
			this.actualPnl.setPreferredSize(d);
//			System.out.println("@@@  >>> " + w1 + " " + w2 + " " + d.width + this.actualPnl.getPreferredSize().width);
		}
	}



	@Override
	public void setScreenSize(boolean mainframe) {

		if (mainframe) {
			DisplayFrame parentFrame = getParentFrame();
			int preferedWidth = java.lang.Math.min(this.screenSize.width - 2,
			        (fileMaster.isBinaryFile() ? 111 : 88) * SwingUtils.CHAR_FIELD_WIDTH );

			parentFrame.bldScreen();
			parentFrame.setBounds(parentFrame.getY(), parentFrame.getX(), preferedWidth,
			        Math.min(parentFrame.getHeight(), this.screenSize.height - 5));
			parentFrame.show();
			parentFrame.setToMaximum(false);
			parentFrame.addCloseOnEsc(actualPnl);
		} else {
			this.actualPnl.done();
		}
	}


	/**
	 * Action performed when frame is closed
	 */
	public void closeWindow() {
		FileView fv;

		if ((fv = getFileView()) != null) {
			fv.removeTableModelListener(this);
			fv.removeTreeModelListener(this);
		}

		super.closeWindow();
	}

	/**
	 * This method sets the column sizes in the record layout
	 */
	@Override
	protected final void setColWidths() {

		JTable table = getJTable();
	    if (table != null) {
	    	int col = record.firstDataColumn;
	    	setStandardColumnWidths(col);
	    	TableColumn tc =  table.getColumnModel().getColumn(col++);

//	        if (cellRenders != null) {
//	        	//System.out.println("BaseLineFrame Rendor 3 ~~> 1 ");
//	            tc.setCellRenderer(render);
//	        }
//	        if (cellEditors != null) {
//	            tc.setCellEditor(new ChooseCellEditor(table, cellEditors));
//	        }

	        tc = table.getColumnModel().getColumn(col++);
	        tc.setPreferredWidth(180);

	        if (fileView != null && fileView.isBinaryFile()) {
	        	if (col + 1 < table.getColumnModel().getColumnCount()) {
		            tc = table.getColumnModel().getColumn(col++);
		            tc.setPreferredWidth(180);
	        	}
	        }
	    }
	}

	/**
	 * Set the Hex display format
	 *
	 */
	protected void ap_100_setHexFormat() {
		JTable table = getJTable();
	    TableColumn tc = table.getColumnModel().getColumn(record.hexColumn);

	    record.setOneLineHex(oneLineHex.isSelected());
	    if (oneLineHex.isSelected()) {
			tc.setCellRenderer(defaultHexRendor);

		    table.setRowHeight(stdCellHeight);
	    } else {
	        if (hexRendor == null) {
	            hexRendor = new HexThreeLineRender(getFileView().getLayout().getFontName());
	        }
	        table.setRowHeight(threeLineHeight);
			tc.setCellRenderer(hexRendor);
			//tc.setCellEditor(hexEditor);
	    }
	}

	/**
	 * Setup the full line data
	 */
	protected void setFullLine() {

	    AbstractLine l = record.getCurrentLine();
	    if (l == null) {
	    } else if (hexLine != null && (fileMaster.isBinaryFile() || layout.isBinCSV())) {
	    	hexLine.setHex(l.getData());
	    } else {
	        fullLine.setText(l.getFullLine());
	    }
	}

	protected abstract void setDirectionButtonStatus();

	protected boolean isTree() {
		return this.layout.hasChildren();
	}




	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#setCurrRow(int, int, int)
	 */
	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
		// TODO Auto-generated method stub

	}


	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesChanged(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeNodesChanged(TreeModelEvent arg0) {
		record.fireTableDataChanged();
	}


	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesInserted(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeNodesInserted(TreeModelEvent arg0) {
		record.fireTableDataChanged();
	}


	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesRemoved(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public final void treeNodesRemoved(TreeModelEvent event) {

//		if (isThisLineDeleted(event.getSource())) {
//			return;
//		}
		Object[] c = event.getChildren();
		for (int i =0; i < c.length; i++) {
			if (isThisLineDeleted(c[i])) {
				return;
			}
		}

		record.fireTableDataChanged();
	}


	private boolean isThisLineDeleted(Object o) {
		if (o instanceof AbstractLineNode) {
			AbstractLineNode n = (AbstractLineNode) o;
			if (n.getLine() == record.getCurrentLine()) {
				getParentFrame().close(this);

				return true;
			}
		}
		return false;
	}


	/**
	 * @see javax.swing.event.TreeModelListener#treeStructureChanged(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeStructureChanged(TreeModelEvent arg0) {
		record.fireTableDataChanged();
	}


	/**
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent arg0) {
		record.fireTableDataChanged();
	}
}