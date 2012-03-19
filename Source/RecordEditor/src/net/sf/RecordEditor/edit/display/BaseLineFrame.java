package net.sf.RecordEditor.edit.display;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
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
import net.sf.RecordEditor.edit.display.util.ChooseCellEditor;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.HexThreeLineField;
import net.sf.RecordEditor.utils.swing.HexThreeLineRender;

@SuppressWarnings("serial")
public abstract class BaseLineFrame extends BaseLineAsColumn  
implements TableModelListener, TreeModelListener {


	protected int stdCellHeight;
	protected int threeLineHeight;
	protected JCheckBox oneLineHex;
	protected JTextField lineNum = new JTextField();
	protected JButton[] btn; 
	private String[] moveHints = {
		        	"Start of File", "Previous Record",
		        	"Next Record",   "Last Record"};
	private String[] moveHints6 = {
			moveHints[0], moveHints[1],
			"Parent Record", "Child Record",
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
	

	public BaseLineFrame(String formType, @SuppressWarnings("rawtypes") FileView viewOfFile, boolean primary,
			boolean fullLine) {
		super(formType, viewOfFile, primary, fullLine);
		
		AbstractAction[] actions = {
	            new AbstractAction("Hide Column") {
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


	protected void init_200_setupFields(JPanel btnPanel, ImageIcon[] icon, ActionListener btnActions) {
	    int i, currLayout;
	    listner = btnActions;
		
		btn = new JButton[icon.length];
		
		if (icon.length > 4) {
			moveHints = moveHints6;
		}
	
		init_210_setupTable();
		
		setFullLine();
	
		currLayout = record.getCurrentLayout();
	
		if (currLayout == Common.NULL_INTEGER) {
			currLayout = fileView.getCurrLayoutIdx();
		}
	
		setLayoutIndex(currLayout);
		record.setCurrentLayout(currLayout);
	
		fileView.addTableModelListener(this);
		fileView.addTreeModelListener(this);
		setColWidths();
	
		pnl.setHelpURL(Common.formatHelpURL(Common.HELP_SINGLE_RECORD));
	
		for (i = 0; i < btn.length; i++) {
			btn[i] = new JButton("", icon[i]);
			btnPanel.add(btn[i]);
			btn[i].addActionListener(listner);
			btn[i].setToolTipText(moveHints[i]);
		}
		setDirectionButtonStatus();
	}

	/**
	 * Setup line table
	 */
	protected void init_210_setupTable() {
	
				
		setJTable(new JTable(record));
		
		
		tblDetails.addMouseListener(popupListner);
		popupListner.setTable(tblDetails);
		setRowHeight();
		
		if (fileMaster.isBinaryFile()) {
		    TableColumn tc = tblDetails.getColumnModel().getColumn(LineModel.HEX_COLUMN);
	
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
	public void init_300_setupScreen(JPanel btnPanel) {
		int preferedWidth = java.lang.Math.min(this.screenSize.width - 2,
		        fileMaster.isBinaryFile() ? 780 : 615);
	
		if (! isTree()) {
			pnl.addLine("Record", lineNum);
		}
		
		if (fileMaster.isBinaryFile()) {
		    pnl.addLine("1 line Hex", oneLineHex);
		}
		//pnl.setGap(BasePanel.GAP1);
	
		pnl.addComponent(1, 5, BasePanel.FILL,
		        BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        tblDetails);
	
		
	
		if (! this.layout.isXml()) {
		    pnl.addComponent(1, 5, fullLineHeight, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL, new JScrollPane(fullLine));
		}
	
		pnl.addComponent(1, 5, BasePanel.PREFERRED, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL, btnPanel);
	
		addMainComponent(pnl);
	
		setBounds(getY(), getX(), preferedWidth,
		        Math.min(getHeight(), this.screenSize.height - 5));
	}

	/**
	 * Action performed when frame is closed
	 */
	public void closeWindow() {
		@SuppressWarnings("rawtypes")
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
	    	setStandardColumnWidths();
	    	TableColumn tc =  table.getColumnModel().getColumn(3);
	
	        if (cellRenders != null) {
	        	//System.out.println("BaseLineFrame Rendor 3 ~~> 1 ");
	            tc.setCellRenderer(render);
	        }
	        if (cellEditors != null) {
	            tc.setCellEditor(new ChooseCellEditor(table, cellEditors));
	        }
	
	        tc = table.getColumnModel().getColumn(4);
	        tc.setPreferredWidth(180);
	
	        if (fileView != null && fileView.isBinaryFile()) {
	            tc = table.getColumnModel().getColumn(5);
	            tc.setPreferredWidth(180);
	        }
	    }
	}

	/**
	 * Set the Hex display format
	 *
	 */
	protected void ap_100_setHexFormat() {
		JTable table = getJTable();
	    TableColumn tc = table.getColumnModel().getColumn(LineModel.HEX_COLUMN);

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
	
	    @SuppressWarnings("rawtypes")
		AbstractLine l = record.getCurrentLine();
	    if (l == null) {
	    } else if (fileMaster.isBinaryFile() || layout.isBinCSV()) {
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
	public void treeNodesRemoved(TreeModelEvent event) {
		if (isThisLineDeleted(event.getSource())) {
			return;
		}
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
				super.doDefaultCloseAction();
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