/*
 * Created on 9/05/2004
 *
 * This class displays the file one record at a time going down the sceen with the
 * fields in each record going across page in a column layout like a spreadsheet.
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use general ButtonTableRendor instead of private
 *     local class
 *   - Added Fixed column support in file list table
 *   - Added right mouse button click popup menu on file list table
 *
 * # Version 0.60 Bruce Martin 2007/01/24
 *   - Bug fixes on Correct column size popup
 *   - added sorting via column header click
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Support for Full line mode
 *   - JRecord Spun off, code reorg
 *
 *  # Version 0.61b Bruce Martin 2007/05/07
 *   - Changed heading to allow testing with Marathon
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - code moved to BaseDisplay so it can be used by TreeList panel
 */
package net.sf.RecordEditor.edit.display;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LineCompare;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.display.common.AbstractRowChanged;
import net.sf.RecordEditor.edit.display.util.RowChangeListner;
import net.sf.RecordEditor.edit.file.FieldMapping;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.jibx.compare.FieldSequence;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReAction;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.ButtonTableRendor;
import net.sf.RecordEditor.utils.swing.FixedColumnScrollPane;
import net.sf.RecordEditor.utils.swing.HexGenericRender;
import net.sf.RecordEditor.utils.swing.HexOneLineRender;
import net.sf.RecordEditor.utils.swing.HexTableCellEditor;
import net.sf.RecordEditor.utils.swing.HexThreeLineField;
import net.sf.RecordEditor.utils.swing.HexTwoLineField;
import net.sf.RecordEditor.utils.swing.HexTwoLineFieldAlt;
import net.sf.RecordEditor.utils.swing.LayoutCombo;
import net.sf.RecordEditor.utils.swing.MonoSpacedRender;
import net.sf.RecordEditor.utils.swing.HexThreeLineRender;
import net.sf.RecordEditor.utils.swing.HexTwoLineRender;




/**
 * This class displays the file one record at a time going down the sceen with the
 * fields in each record going across page in a column layout like a spreadsheet.
 *
 * @author Bruce Martin
 * @version 0.51
 *
 */
@SuppressWarnings("serial")
public class LineList extends BaseLineDisplay 
implements AbstractFileDisplayWithFieldHide, TableModelListener, AbstractRowChanged {

    //private static final int ROW_WIDTH = 40;
    private static final int CHAR_WIDTH = 8;
    private static final int NUMBER_OF_CONTROL_COLUMNS = 2;

    private HeaderRender headerRender = new HeaderRender();
    
    private ButtonTableRendor tableBtn = new ButtonTableRendor();
    private FixedColumnScrollPane tblScrollPane = null;

    private int fixedPopupCol = 1;
    private int mainPopupCol = 1;
    private int popupRow = 1;

    private int lastRow;
    boolean isPrefered = false;
    
    private MonoSpacedRender  charRendor = null;

    private DefaultCellEditor charEditor;
    
    private HexOneLineRender hex1Rendor = null;
    private HexGenericRender hex2RendorA = null;  
    private HexTwoLineRender hex2Rendor = null;  
    private HexThreeLineRender hex3Rendor = null;
    private TableCellEditor hex1Editor, hex2Editor, hex2EditorA, hex3Editor;
    
 //   private JMenu showColumnsMenu = new JMenu("Show Column");
    //private ArrayList<TableColumn> hiddenColumns = new ArrayList<TableColumn>();
    private KeyListener keyListner; 
    
    private FieldMapping fieldMapping = null;

    /**
     *
     * @param group - Record Layout
     * @param viewOfFile - current file view
     * @param masterFile - Internal Representation of the file
     */
    public LineList(final AbstractLayoutDetails<?, ?> group,
                    final FileView<?> viewOfFile,
                    final FileView<?> masterFile) {
        super("Table: ", viewOfFile, viewOfFile == masterFile, ! viewOfFile.getLayout().isXml(),
        		 true, group.isBinary());

        fieldMapping = new FieldMapping(getFieldCounts());
        
        init_100_SetupJtables(viewOfFile);
        
        init_200_LayoutScreen();
    }
    

    
    /**
     * Setup the JTables etc
     * @param viewOfFile current file view
     */
    private void init_100_SetupJtables(final FileView<?> viewOfFile) {

        ReAction sort = new ReAction(ReActionHandler.SORT, this);
        AbstractAction editRecord = new AbstractAction("Edit Record") {
            public void actionPerformed(ActionEvent e) {
            	newLineFrame(fileView, popupRow);
           }
        };
        AbstractAction gotoLine = new AbstractAction("Goto Line") {
            public void actionPerformed(ActionEvent e) {
            	startGotoLineNumber();
           }
        };
        MenuPopupListener mainPopup;
        AbstractAction[] mainActions = {
                sort,
                null,
                editRecord,
                gotoLine,
                null,
                new AbstractAction("Autofit Columns") {
                    public void actionPerformed(ActionEvent e) {
                        Common.calcColumnWidths(tblDetails, 1);
                    }
                },
                null,
               new AbstractAction("Fix Column") {
                    public void actionPerformed(ActionEvent e) {
                        //int col = tblDetails.getSelectedColumn();

                        tblScrollPane.doFixColumn(mainPopupCol);
                    };
                },
                new AbstractAction("Hide Column") {
                    public void actionPerformed(ActionEvent e) {
                    	hideColumn(mainPopupCol);
                    }
                },
         };

        AbstractAction[] fixedActions = {
                sort,
                null,
                editRecord,
                gotoLine,
                null,
                new AbstractAction("Unfix Column") {
                    public void actionPerformed(ActionEvent e) {
                       // int col = tblScrollPane.getFixedTable().getSelectedColumn();

                        if (fixedPopupCol > 0) {
                            //System.out.println("Un-Fix ~~ " + tblDetails.getSelectedColumn());
                            tblScrollPane.doUnFixColumn(fixedPopupCol);
                        }
                    }
                }
        };
       
        JTable tableDetails = new JTable(viewOfFile);
        super.setJTable(tableDetails);
        tblScrollPane = new FixedColumnScrollPane(tableDetails);

        keyListner =    new RowChangeListner(tableDetails, this);

        mainPopup = //MenuPopupListener.getEditMenuPopupListner(mainActions);
        new MenuPopupListener(mainActions, true, tableDetails) {          
 		   /**
		     * @see MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		     */
		    public void mousePressed(MouseEvent e) {
		    	super.mousePressed(e);
		    	JTable table = getJTable();
		    	
	      		int col = table.columnAtPoint(e.getPoint());
	            int row = table.rowAtPoint(e.getPoint());
	            
	            checkRowChange(row);
	            if (row >= 0 && row != table.getEditingRow() 
	        	&&  col >= 0 && col != table.getEditingColumn()
	        	&& cellEditors != null && col < cellEditors.length && cellEditors[col] != null
	        	) {
	            	table.editCellAt(row, col);
	            }
		    }
 
        	protected final boolean isOkToShowPopup(MouseEvent e) {
        		JTable tblDetails = getJTable();
        		
                mainPopupCol = tblDetails.columnAtPoint(e.getPoint());
                popupRow = tblDetails.rowAtPoint(e.getPoint());

                return true;
            }
        };
        mainPopup.getPopup().add(tblScrollPane.getShowFieldsMenu());

        viewOfFile.setFrame(ReMainFrame.getMasterFrame());

//        tblDetails = new JTable(viewOfFile);
        tableDetails.addMouseListener(mainPopup);        

        initToolTips();

        tableDetails.getTableHeader().addMouseListener(new HeaderSort());

        super.setAlternativeTbl(tblScrollPane.getFixedTable());
        
        pnl.registerComponent(tableDetails);
        pnl.registerComponent(tblScrollPane.getFixedTable());
        defColumns();

        tblScrollPane.getFixedTable().getTableHeader().addMouseListener(new HeaderSort());
        tblScrollPane.getFixedTable().addMouseListener(new MenuPopupListener(fixedActions, true, null) {
            public void mousePressed(MouseEvent m) {
                JTable tbl = tblScrollPane.getFixedTable();
                int col = tbl.columnAtPoint(m.getPoint());

                popupRow = getJTable().rowAtPoint(m.getPoint());

                if (col == 0) {
                	newLineFrame(fileView, popupRow);
                } else {
                	super.mousePressed(m);
                }
            }

            protected boolean isOkToShowPopup(MouseEvent e) {
                JTable tbl = tblScrollPane.getFixedTable();
                 fixedPopupCol = tbl.columnAtPoint(e.getPoint());
                 popupRow = tbl.rowAtPoint(e.getPoint());

                return fixedPopupCol > 0;
            }
            
        });
        
       // if (viewOfFile.getLayout().isOkToAddAttributes()) {
       	viewOfFile.getBaseFile().addTableModelListener(this);
       // }
    }

    
    private void init_200_LayoutScreen() {
    	
        pnl.setHelpURL(Common.formatHelpURL(Common.HELP_RECORD_TABLE));

        pnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
                         BasePanel.FULL, BasePanel.FULL,
                         tblScrollPane);


        addMainComponent(pnl);


        setBounds(1, 1,
                  screenSize.width  - 1,
                  screenSize.height - 1);
        
        if (layout.getRecordCount() > 1 && Common.usePrefered()) {
        	LayoutCombo combo = getLayoutCombo();
        	combo.setSelectedIndex(combo.getPreferedIndex());
        	setTableFormatDetails(combo.getPreferedIndex());
        	changeLayout();
        } else {
        	setLayoutIdx();
        }
//        if (layout.getRecordCount() > 1 && Common.usePrefered()) {
//        	LayoutCombo combo = getLayoutCombo();
//        	combo.setSelectedIndex(combo.getPreferedIndex());
//        	setTableFormatDetails(combo.getPreferedIndex());
//        	changeLayout();
//        } else {
//        	setLayoutIdx();
//        }

        setVisible(true);
    }

  
	
	/**
	 * Get the number of fields for each record
	 * @return field counts for for each record
	 */
	private int[] getFieldCounts() {
       int[] rows = new int[layout.getRecordCount()];
        
        for (int i = 0; i < layout.getRecordCount(); i++) {
        	rows[i] = fileView.getLayoutColumnCount(i);
        }

        return rows;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#newLayout(net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@SuppressWarnings("unchecked")
	@Override
    protected void newLayout(AbstractLayoutDetails newLayout) {
    	
	    tblScrollPane.clearHiddenCols();
	    fieldMapping.resetMapping(getFieldCounts());
        if (layout.getRecordCount() > 1 && Common.usePrefered()) {
        	LayoutCombo combo = getLayoutCombo();
        	combo.setSelectedIndex(combo.getPreferedIndex());
        	setTableFormatDetails(combo.getPreferedIndex());
        } else {
        	setLayoutIdx();
        }
       	fileView.setCurrLayoutIdx(getLayoutIndex());
	    
		fileView.fireTableStructureChanged();
	    defColumns();
    }

    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.AbstractRowChanged#checkRowChange(int)
	 */
    public final void checkRowChange(int row) {
    	//System.out.println("Check Row: " + isPrefered + "   " + lastRow + " " + row);
    	if (isPrefered && lastRow != row) {
    		int recordIdx = fileView.getLine(row).getPreferredLayoutIdx();
    		if (recordIdx != fileView.getDefaultPreferredIndex()) {
	    		fileView.setDefaultPreferredIndex(recordIdx);
	    		
	    		TableColumnModel columns = tblDetails.getColumnModel();
	    		int last = Math.min(fileView.getColumnCount(), columns.getColumnCount());
	    		for (int i = 0; i < last; i++) {
	    			//System.out.print("  " + i + " " + fileView.getColumnName(i));
	    			columns.getColumn(i).setHeaderValue(fileView.getColumnName(columns.getColumn(i).getModelIndex()));
	    		}
	    		tblScrollPane.correctFixedSize();
	    	}
    		lastRow = row;
    	}
    }

    /**
     * Changes the record layout used to display the file
     * @see BaseDisplay#changeLayout()
     */
    public void changeLayout() {
    	int idx = getLayoutIndex();
    	//Common.logMsg("LineList: Change Layout " + fileView.getCurrLayoutIdx() + " ~ " + getLayoutIndex(), null);
    	
//    	System.out.print("LineList: Change Layout " + fileView.getCurrLayoutIdx() 
//    			+ " " + idx);
        if (fileView.getCurrLayoutIdx() != idx) {
        	fileView.setCurrLayoutIdx(idx);
	       // System.out.print("LineList: Change Layout " + fileView.getCurrLayoutIdx() );
	        fileView.fireTableStructureChanged();
	        tblScrollPane.clearHiddenCols();
	
	        defColumns();
	        
	        if (idx < layout.getRecordCount()) {
	        	setFieldVisibility(idx, fieldMapping.getFieldVisibility(idx));
	        }
        }
    }
    

    private void hideColumn(int col) {
     	tblScrollPane.doHideColumn(col);
    }

    /**
     * This method defines the Column Headings
     */
    private void defColumns() {
    	//Common.logMsg("LineList: Define Columns " + getLayoutIndex(), null);
    	JTable tblDetails = getJTable();

        TableColumnModel tcm = tblDetails.getColumnModel();
        
        defineColumns(fileView.getColumnCount(), 2, 0);
        
        for (int i = 2; i < tcm.getColumnCount(); i++) {
            tcm.getColumn(i).setHeaderRenderer(headerRender);
        }
        
 //       System.out.println("Full Line Test " + getLayoutIndex() 
 //       		+ " >= " + fullLineIndex);
     
        int layoutIdx = getLayoutIndex();
        int lineHeight = Math.max(tblScrollPane.STD_LINE_HEIGHT, tblDetails.getRowHeight());
        String font = layout.getFontName();
        //System.out.println(" ~~> LayoutIdx ~~> " + layoutIdx + " " + fullLineIndex + " " + (fullLineIndex + 2));
        
        //System.out.println("check prefered:  " + layoutIdx + " " + getLayoutCombo().getPreferedIndex());
        
        int fullLineIndex = getLayoutCombo().getFullLineIndex();
        isPrefered = layoutIdx == getLayoutCombo().getPreferedIndex();
        if (isPrefered) {
        	tblDetails.addKeyListener(keyListner);
        	lastRow = - 1;
        	checkRowChange(tblDetails.getSelectedRow());
        } else {
        	tblDetails.removeKeyListener(keyListner);
        	if (layoutIdx == fullLineIndex) {
	            if (charRendor == null) {
	                charRendor = new MonoSpacedRender();
	                charEditor = new DefaultCellEditor(new MonoSpacedRender());
	            }
	            //System.out.println("~~ " + "setting Rendor");
	            tcm.getColumn(2).setCellRenderer(charRendor);
	            tcm.getColumn(2).setCellEditor(charEditor);
	       } else if (layoutIdx == fullLineIndex + 1) {
	            if (hex1Rendor == null) {
	                hex1Rendor = new HexOneLineRender();
	                hex1Editor = new HexTableCellEditor(new HexOneLineRender());
	            }
	
	            tcm.getColumn(2).setCellRenderer(hex1Rendor);
	            tcm.getColumn(2).setCellEditor(hex1Editor);
	        } else if (layoutIdx == fullLineIndex + 2) {
	            if (hex2Rendor == null) {
	                hex2Rendor = new HexTwoLineRender(font);
	                hex2Editor = new HexTableCellEditor(new HexTwoLineField(font));
	            }
	
	            tcm.getColumn(2).setCellRenderer(hex2Rendor);
	            tcm.getColumn(2).setCellEditor(hex2Editor);
	            lineHeight = tblScrollPane.TWO_LINE_HEIGHT;
	        } else if (layoutIdx == fullLineIndex + 3) {      	 
	            if (hex3Rendor == null) {
	                hex3Rendor = new HexThreeLineRender(font);
	                hex3Editor = new HexTableCellEditor(new HexThreeLineField(font));
	            }
	
	            tcm.getColumn(2).setCellRenderer(hex3Rendor);
	            tcm.getColumn(2).setCellEditor(hex3Editor);
	            lineHeight = tblScrollPane.THREE_LINE_HEIGHT;
	        } else if (layoutIdx == fullLineIndex + 4) {
	            if (hex2RendorA == null) {
	                hex2RendorA = new HexGenericRender(font, new HexTwoLineFieldAlt(font));
	                hex2EditorA = new HexTableCellEditor(new HexTwoLineFieldAlt(font));
	            }
	
	            tcm.getColumn(2).setCellRenderer(hex2RendorA);
	            tcm.getColumn(2).setCellEditor(hex2EditorA);
	            lineHeight = tblScrollPane.TWO_LINE_HEIGHT;
	        } 
        }
        setRowHeight(lineHeight);

       if (tblScrollPane != null) {
    	   int rowCount = this.fileView.getRowCount();
    	   int width = 5;
    	   //System.out.println("Setup fixed columns ... ");
           tblScrollPane.setFixedColumns(NUMBER_OF_CONTROL_COLUMNS);
           TableColumn tc = tblScrollPane.getFixedTable().getColumnModel().getColumn(0);
           tc.setCellRenderer(tableBtn);
           tc.setPreferredWidth(5);
           tc = tblScrollPane.getFixedTable().getColumnModel().getColumn(1);
           if (rowCount > 10000000) {
        	   width = 8;
           } else if (rowCount > 1000000) {
        	   width = 7;
           } else if (rowCount > 100000) {
        	   width = 6;
           }
           tc.setPreferredWidth(CHAR_WIDTH * width + 4);
           tc.setResizable(false);
           
           tblScrollPane.correctFixedSize();
        }
       
       if (layoutIdx >= fullLineIndex) {
    	   Common.calcColumnWidths(tblDetails, 1);
       }
    }

    
    /**
     * Get the Current Row to display
     *
     * @return Current Row
     *
     * @see net.sf.RecordEditor.edit.BaseLineDisplay.getCurrRow
     */
    public int getCurrRow() {
        if (tblDetails.getSelectedRowCount() > 0) {
            return tblDetails.getSelectedRow();
        }
        return -1;
    }

    /**
     * Set the row to display
     *
     * @param newRow new current Row
     * @param layoutId layout the field was found on
     * @param fieldNum new field number
     *
     * @see net.sf.RecordEditor.edit.BaseLineDisplay.setCurrRow
     */
    public void setCurrRow(int newRow, int layoutId, int fieldNum) {
        if ((newRow >= 0) && newRow <= fileView.getRowCount()) {
            if ((getCurrRow() != newRow)) {
                fileView.fireTableDataChanged();
                tblDetails.changeSelection(newRow, 1, false, false);
            }
            if (fieldNum > 0  && getLayoutIndex() == layoutId) {
                stopCellEditing();
                tblDetails.editCellAt(newRow, fieldNum);
                //System.out.println("Found " + newRow + " " + fieldNum);
            }
        }
    }


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {
    	JTable table = getJTable();
    	
    	switch (action) {
    	case (ReActionHandler.REPEAT_RECORD):
            fileView.repeatLine(popupRow);
    	break;
    	case (ReActionHandler.DELETE_RECORD):
    		super.executeAction(action);
    		checkRowChange(table.getSelectedRow());
    	break;
    	case(ReActionHandler.PASTE_TABLE_INSERT):
			int startRow = table.getSelectedRow();
			int startCol = table.getSelectedColumn();
			if (startRow >= 0) {
				Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
				try {
					String pasteStr =(String) (system.getContents(this)
									.getTransferData(DataFlavor.stringFlavor));
					int numRows = (new StringTokenizer(pasteStr, "\n")).countTokens();
					for (int i = 0; i < numRows; i++) {
						fileView.newLine(startRow);
					}
					
					pasteTable(startRow + 1, startCol, pasteStr	);
				} catch (Exception e) {
				}
			}

    	break;
        default:
            super.executeAction(action);
        }
    }
    

	/**
	 * @see net.sf.RecordEditor.edit.display.BaseLineDisplay#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {

		return action == ReActionHandler.PASTE_TABLE_INSERT
				|| super.isActionAvailable(action);
	}
    /**
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent event) {
		
		//System.out.print("LineList: Table Changed: " + event.getType() + " " + TableModelEvent.ALL_COLUMNS);
		if (super.hasTheFormatChanged(event)) {
			fileView.fireTableStructureChanged();
			defColumns();
		}
		//System.out.println();
	}



    protected void setColWidths() {
	}


    public FieldSequence getFieldSequence() {
    	FieldSequence rec = null;
    	int layoutIdx = getLayoutIndex();
    	
    	if (layoutIdx < layout.getRecordCount()) {
	       	int[][] fieldIdx = tblScrollPane.getColumnSequence();
	       	int st = FileView.LINE_NUMER_COLUMN+1;
	    	
	       	rec = new FieldSequence();
	    	rec.name = layout.getRecord(layoutIdx).getRecordName();
	    	rec.fields = getNames(
	    			layoutIdx, 
	    			st, 
	    			fieldIdx[FixedColumnScrollPane.MAIN_INDEX]);

	    	rec.fixedFields = getNames(
	    			layoutIdx, 
	    			st, 
	    			fieldIdx[FixedColumnScrollPane.FIXED_INDEX]);
    	}

    	return rec;
    }
    
    private String[] getNames(int layoutIdx, int st, int[] fields) {
    	int idx;
    	int j = 0;
    	String ret[] = new String[getSize(fields)];
    	System.out.println(" ~~~ size: " + ret.length);
    	for (int i = 0; i < fields.length; i++) {
    		idx = fields[i];
    		System.out.print(" " + j + " " + i + " " + idx);
    		if (idx >= st) {
	    		ret[j++] = fileView.getRealColumnName(
	    				layoutIdx,
	    				fileView.getRealColumn(
	    						layoutIdx, 
	    						idx - st));
	    		System.out.print(" - " + ret[j-1]);
    		}
    		System.out.println(" ");
    	}
    	return ret;
    }

    
    private int getSize(int[] array) {
    	int size = array.length;
    	
    	for (int a: array) {
    		if (a <= FileView.LINE_NUMER_COLUMN) {
    			size -= 1;
    		}
    	}
    	return size;
    }

    public void setFieldSequence(FieldSequence seq) {
    	int[][] fields = new int[2][];
    	HashMap<String, Integer> map = new HashMap<String, Integer>();
    	int st = FileView.LINE_NUMER_COLUMN+1;
    	int layoutIdx = layout.getRecordIndex(seq.name);
    	
    	if (layoutIdx >= 0) { 
    		setLayoutIndex(layoutIdx);
	    	for (int i = st; i < fileView.getColumnCount(); i++) {
	    		System.out.println(" ~~> " + i + " " + (i-st)
	    				+ " " + fileView.getRealColumn(layoutIdx, i - st)
	    				+ " : " + fileView.getRealColumnName(
			    						layoutIdx,
			    						fileView.getRealColumn(layoutIdx, i - st))
	    				);
	    		map.put(fileView.getRealColumnName(
		    				layoutIdx,
		    				fileView.getRealColumn(layoutIdx, i - st)).toLowerCase(),
		    			i);
	    	}
   	
	    	fields[FixedColumnScrollPane.MAIN_INDEX] 
	    	       = getFieldIndex(seq.fields, map);
	    	fields[FixedColumnScrollPane.FIXED_INDEX] 
	    	       = getFieldIndex(seq.fixedFields, map);
	    	
	    	tblScrollPane.setColumnSequence(fields, st);
      	}
    	 
    }

    private int[] getFieldIndex(String[] names, HashMap<String, Integer> map) {
    	int[] ret = new int[names.length];
    	int i = 0;
    	
    	for (String name : names) {
    		name = name.toLowerCase();
    		ret[i] = -1;
    		if (map.containsKey(name)) {
    			ret[i] = map.get(name).intValue();
    		}
    		
    		System.out.println(" ==> " + i
    				+ " " + name + " " + map.containsKey(name)
    				+ " : " + ret[i]
    				);
    		i +=1;
    	}
    	
    	return ret;
    }
    
    
	/**
	 * @see net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide#getFieldVisibility(int)
	 */
	@Override
	public boolean[] getFieldVisibility(int recordIndex) {
		boolean[] ret = null;
		if (recordIndex == getLayoutIndex()) {
			ret = tblScrollPane.getFieldVisibility(NUMBER_OF_CONTROL_COLUMNS);
		} else {
			ret = fieldMapping.getFieldVisibility(recordIndex);
		}

		return ret;
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide#setFieldVisibility(int, boolean[])
	 */
	@Override
	public void setFieldVisibility(int recordIndex, boolean[] fieldVisibility) {
		fieldMapping.setFieldVisibilty(recordIndex, fieldVisibility);
		
		if (recordIndex == getLayoutIndex()) {
			tblScrollPane.setFieldVisibility(NUMBER_OF_CONTROL_COLUMNS, fieldVisibility);
		}
		
		setActiveFrame(this);
	}






	/**
     *
     * @author Bruce Martin
     *
     * This column formats the Column Headings
     */
    private class HeaderRender extends JPanel implements TableCellRenderer {
   
        /**
         * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
         * 		(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
         */
        public Component getTableCellRendererComponent(
            JTable tbl,
            Object value,
            boolean isFldSelected,
            boolean hasFocus,
            int row,
            int column) {

            removeAll();
            setLayout(new GridLayout(2, 1));

            if (column >= 0 && value != null) {

                String s = (String) value;
                String first = s;
                String second = "";
                int pos = s.indexOf(Common.COLUMN_LINE_SEP);
                if (pos > 0) {
                    first = s.substring(pos + 1);
                    second = s.substring(0, pos);
                }
                JLabel label = new JLabel(first);
                add(label);
                if ((!second.equals(""))) {
                    label = new JLabel(second);
                    
  //                  System.out.println("Header Render");
                    if (getLayoutIndex() >= getLayoutCombo().getFullLineIndex()) {
                    	label.setFont(Common.getMonoSpacedFont());
                    }
                 
                    add(label);
                }
            }
            this.setBorder(BorderFactory.createEtchedBorder());

            return this;
        }
    }


//    /**
//     *
//     * @author Bruce Martin
//     *
//     * This class supplies Column Tips (displayed
//     * when the cursor is held over a column).
//     */
//    private class HeaderToolTips extends JTableHeader {
//        private String[] tips;
//
//        /**
//         *
//         * @param columnModel column model to use
//         */
//        public HeaderToolTips(final TableColumnModel columnModel) {
//            super(columnModel);
//        }
//
//        /**
//         * Define the Column Heading Tips
//         *
//         * @param strings - List of strings Tips
//         */
//        public void setTips(String[] strings) {
//            tips = strings;
//        }
//
//        /**
//         * @see javax.swing.table.JTableHeader#getToolTipText
//         */
//        public String getToolTipText(MouseEvent m) {
//            String tip = super.getToolTipText(m);
//            int col = tblDetails.columnAtPoint(m.getPoint());
//            if ((tips != null) && ((col - 2 < tips.length) && (col > 1))) {
//                tip = tips[col - 2];
//            }
//
//            return tip;
//        }
//   }


    /**
     * Class to sort a table on a header click to a table
     * @param table to be sorted
     */
    private class HeaderSort extends MouseAdapter {

        private int lastCol = -1;

        /**
         * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
         */
        public void mousePressed(MouseEvent e) {
            if (e.getClickCount() == 2) {
                JTableHeader header = (JTableHeader) e.getSource();
                int col = header.columnAtPoint(e.getPoint());
                int layoutIndex = getLayoutIndex();

                col = fileView.getRealColumn(layoutIndex,
                		header.getColumnModel().getColumn(col).getModelIndex() - 2);

                if (col >= 0) {
                    int[] cols = {col};
                    boolean[] descending = {lastCol == col};

                    fileView.sort(
                            new LineCompare(fileView.getLayout(),
                            		layoutIndex,
                                    cols,
                                    descending
                            ));

                    if (lastCol == col) {
                        lastCol = -1;
                    } else {
                        lastCol = col;
                    }
                }
            }
        }
    }

//    
//    private class ShowFieldAction extends JMenuItem implements ActionListener {
//    	TableColumn colDef ;
//    	public ShowFieldAction(String s, TableColumn colDefinition) {
//    		super(s);
//    		colDef = colDefinition;
//    		
//    		super.addActionListener(this);
//    	}
//    	
//    	@Override
//		public void actionPerformed(ActionEvent e) {
//			int col = tblScrollPane.doShowColumn(colDef);
//			
//			if (col >= 0) {
//				if (getLayoutIndex() < layout.getRecordCount()) {
//					fieldMapping.showColumn(getLayoutIndex(), colDef.getModelIndex() - NUMBER_OF_CONTROL_COLUMNS);
//				}
//				showColumnsMenu.remove(this);
//			}
//		}
//    }
}
