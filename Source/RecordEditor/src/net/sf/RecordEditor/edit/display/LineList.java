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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JMenu;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.edit.display.Action.AutofitAction;
import net.sf.RecordEditor.edit.display.Action.CsvUpdateLayoutAction;
import net.sf.RecordEditor.edit.display.Action.GotoLineAction;
import net.sf.RecordEditor.edit.display.common.AbstractFieldSequencePnl;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.display.util.Code;
import net.sf.RecordEditor.edit.display.util.RowChangeListner;
import net.sf.RecordEditor.edit.util.ReMessages;
import net.sf.RecordEditor.jibx.compare.FieldSequence;
import net.sf.RecordEditor.re.file.FieldMapping;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReAction;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.ButtonTableRendor;
import net.sf.RecordEditor.utils.swing.FixedColumnScrollPane;
import net.sf.RecordEditor.utils.swing.HexGenericRender;
import net.sf.RecordEditor.utils.swing.HexOneLineRender;
import net.sf.RecordEditor.utils.swing.HexTableCellEditor;
import net.sf.RecordEditor.utils.swing.HexThreeLineField;
import net.sf.RecordEditor.utils.swing.HexThreeLineRender;
import net.sf.RecordEditor.utils.swing.HexTwoLineField;
import net.sf.RecordEditor.utils.swing.HexTwoLineFieldAlt;
import net.sf.RecordEditor.utils.swing.HexTwoLineRender;
import net.sf.RecordEditor.utils.swing.LayoutCombo;
import net.sf.RecordEditor.utils.swing.MonoSpacedRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;




/**
 * This class displays the file one record at a time going down the screen with the
 * fields in each record going across page in a column layout like a spreadsheet.
 *
 * @author Bruce Martin
 * @version 0.51
 *
 */
@SuppressWarnings("serial")
public class LineList extends BaseLineDisplay
implements AbstractFileDisplayWithFieldHide, TableModelListener, AbstractCreateChildScreen, AbstractFieldSequencePnl {

    //private static final int ROW_WIDTH = 40;
    //private static final int CHAR_WIDTH = 8;
    private static final int NUMBER_OF_CONTROL_COLUMNS = 2;

    private HeaderRender headerRender = new HeaderRender();

    private ButtonTableRendor tableBtn = new ButtonTableRendor();
    private FixedColumnScrollPane tblScrollPane = null;

    private JMenu copyMenu = SwingUtils.newMenu("Copy Column");
    private JMenu moveMenu = SwingUtils.newMenu("Move Column");

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
    private RowChangeListner keyListner;

    private FieldMapping fieldMapping = null;

    private LineFrame childFrame;

    protected static LineList newLineList(final AbstractLayoutDetails<?, ?> group,
            final FileView<?> viewOfFile,
            final FileView<?> masterFile) {
    	return new LineList(group, viewOfFile, masterFile);
    }

    /**
     *
     * @param group - Record Layout
     * @param viewOfFile - current file view
     * @param masterFile - Internal Representation of the file
     */
    private LineList(final AbstractLayoutDetails<?, ?> group,
                    final FileView<?> viewOfFile,
                    final FileView<?> masterFile) {
        super("Table:", viewOfFile, viewOfFile == masterFile, ! viewOfFile.getLayout().isXml(),
        		 true, group.isBinary(), true);

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
        AbstractAction editRecord
        	= new ReAbstractAction(
        		"Edit Record",
        		Common.getRecordIcon(Common.ID_EDIT_RECORD_ICON)) {
            public void actionPerformed(ActionEvent e) {
            	newLineFrame(fileView, popupRow);
           }
        };
        AbstractAction gotoLine   = new GotoLineAction(this, fileView);


        MenuPopupListener mainPopup;
        AbstractAction[] mainActions = {
                sort,
                null,
                editRecord,
                gotoLine,
                null,
                new AutofitAction(this),
                null,
                new ReAbstractAction("Fix Column") {
                    public void actionPerformed(ActionEvent e) {
                        //int col = tblDetails.getSelectedColumn();

                        tblScrollPane.doFixColumn(mainPopupCol);
                    };
                },
                new ReAbstractAction("Hide Column") {
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
                new ReAbstractAction("Unfix Column") {
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

	            checkForRowChange(row);
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
        if (fileMaster.isSimpleCsvFile()) {
        	mainPopup.getPopup().add(init_110_GetCsvMenu());
        }

        viewOfFile.setFrame(ReMainFrame.getMasterFrame());

//        tblDetails = new JTable(viewOfFile);
        tableDetails.addMouseListener(mainPopup);

        initToolTips(2);

        tableDetails.getTableHeader().addMouseListener(new HeaderSort());

        super.setAlternativeTbl(tblScrollPane.getFixedTable());

        actualPnl.registerComponent(tableDetails);
        actualPnl.registerComponent(tblScrollPane.getFixedTable());
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


    private JMenu init_110_GetCsvMenu() {
    	JMenu m = SwingUtils.newMenu("CSV Options");

    	copyMenu.setIcon(Common.getRecordIcon(Common.ID_COLUMN_COPY_ICON));
    	moveMenu.setIcon(Common.getRecordIcon(Common.ID_COLUMN_MOVE_ICON));

    	m.add(new AddColumn("Add Column before", false));
    	m.add(new AddColumn("Add Column after", true));
    	m.add(new DeleteColumn());
    	m.addSeparator();
    	m.add(copyMenu);
    	m.add(moveMenu);

    	m.add(new CsvUpdateLayoutAction(true));
    	buildDestinationMenus();

    	return m;
    }

    private void init_200_LayoutScreen() {

    	super.actualPnl.addReKeyListener(new DelKeyWatcher());
        actualPnl.setHelpURL(Common.formatHelpURL(Common.HELP_RECORD_TABLE));

        actualPnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
                         BasePanel.FULL, BasePanel.FULL,
                         tblScrollPane);



        if (layout.getRecordCount() > 1 && Common.usePrefered()) {
        	LayoutCombo combo = getLayoutCombo();
        	combo.setSelectedIndex(combo.getPreferedIndex());
        	setTableFormatDetails(combo.getPreferedIndex());
        	fireLayoutIndexChanged();
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

    }



	@Override
	public void setScreenSize(boolean mainframe) {

		DisplayFrame parentFrame = getParentFrame();
		parentFrame.bldScreen();

        parentFrame.setBounds(1, 1,
                  screenSize.width  - 1,
                  screenSize.height - 1);


        parentFrame.setToMaximum(true);
		parentFrame.setVisible(true);

		Common.calcColumnWidths(tblDetails, 2);
	}



	/*
	 * (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#newLayout(net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@SuppressWarnings({"rawtypes" })
	@Override
    public void setNewLayout(AbstractLayoutDetails newLayout) {
		int idx;

		super.setNewLayout(newLayout);

	    tblScrollPane.clearHiddenCols();
	    fieldMapping.resetMapping(getFieldCounts());
        if (layout.getRecordCount() > 1 && Common.usePrefered()) {
        	LayoutCombo combo = getLayoutCombo();
        	combo.setSelectedIndex(combo.getPreferedIndex());
        	//setTableFormatDetails(combo.getPreferedIndex());
        } else {
        	setLayoutIdx();
        }
        idx = getLayoutIndex();
       	fileView.setCurrLayoutIdx(idx);
       	setTableFormatDetails(idx);

		fileView.fireTableStructureChanged();
	    defColumns();
    }

	private void buildDestinationMenus() {

		if (layout.getRecordCount() == 1 && layout.getRecord(0).getFieldCount() > 0) {
			AbstractRecordDetail<?> rec = layout.getRecord(0);
			String s;
			copyMenu.removeAll();
			moveMenu.removeAll();

			s = ReMessages.BEFORE_FIELD.get(rec.getField(0).getName());
				//LangConversion.convert(LangConversion.ST_ACTION, "Before {0}", rec.getField(0).getName());
			copyMenu.add(new MoveCopyColumn(s, true, 0));
			moveMenu.add(new MoveCopyColumn(s, false, 0));

			for (int i=0; i < rec.getFieldCount(); i++) {
				s = ReMessages.AFTER_FIELD.get(rec.getField(i).getName());
					//LangConversion.convert(LangConversion.ST_ACTION, "After {0}", rec.getField(i).getName());
				copyMenu.add(new MoveCopyColumn(s, true, i+1));
				moveMenu.add(new MoveCopyColumn(s, false, i+1));
			}
		}
	}


    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.AbstractRowChanged#checkRowChange(int)
	 */
    public final void checkForRowChange(int row) {
    	//System.out.println("Check Row: " + isPrefered + "   " + lastRow + " " + row);
    	if (lastRow != row) {
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
	    	}

	    	if (childFrame != null) {
	    		childFrame.setCurrRow(row);
	    	}
    	}
    	lastRow = row;
    }


	/**
	 * @see net.sf.RecordEditor.edit.display.AbstractCreateChildScreen#createChildScreen()
	 */
	@Override
	public AbstractFileDisplay createChildScreen(int pos) {

		childFrame = new LineFrame("ChildRecord:", super.fileView, Math.max(0, getCurrRow()), false);
		setKeylistner(tblDetails);
		return childFrame;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getChildFrame()
	 */
	@Override
	public AbstractFileDisplay getChildScreen() {
		return childFrame;
	}


	@Override
	public void removeChildScreen() {
		if (childFrame != null) {
			childFrame.doClose();
		}
		childFrame = null;
		setKeylistner(tblDetails);
	}

	/**
     * Changes the record layout used to display the file
     * @see BaseDisplay#fireLayoutIndexChanged()
     */
    public void fireLayoutIndexChanged() {
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
    	defColumns(getJTable(), fileView, tblScrollPane);
    }

    private JTable defColumns(
    		JTable tbl,
    		@SuppressWarnings("rawtypes") FileView view,
    		FixedColumnScrollPane scrollPane) {
        TableColumnModel tcm = tbl.getColumnModel();

        defineColumns(tbl, view.getColumnCount(), 2, 0);

        for (int i = 2; i < tcm.getColumnCount(); i++) {
            tcm.getColumn(i).setHeaderRenderer(headerRender);
        }
  //       System.out.println("Full Line Test " + getLayoutIndex()
 //       		+ " >= " + fullLineIndex);

        int layoutIdx = getLayoutIndex();
        int lineHeight =  tbl.getRowHeight();
        String font = layout.getFontName();

        if (scrollPane != null && scrollPane.STD_LINE_HEIGHT > lineHeight) {
        	lineHeight = scrollPane.STD_LINE_HEIGHT;
        }
        //System.out.println(" ~~> LayoutIdx ~~> " + layoutIdx + " " + fullLineIndex + " " + (fullLineIndex + 2));

        //System.out.println("check prefered:  " + layoutIdx + " " + getLayoutCombo().getPreferedIndex());

        int fullLineIndex = getLayoutCombo().getFullLineIndex();
        isPrefered = layoutIdx == getLayoutCombo().getPreferedIndex();

        setKeylistner(tbl);
        if (isPrefered) {

        } else if (layoutIdx == fullLineIndex) {
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
            lineHeight = scrollPane.TWO_LINE_HEIGHT;
        } else if (layoutIdx == fullLineIndex + 3) {
            if (hex3Rendor == null) {
                hex3Rendor = new HexThreeLineRender(font);
                hex3Editor = new HexTableCellEditor(new HexThreeLineField(font));
            }

            tcm.getColumn(2).setCellRenderer(hex3Rendor);
            tcm.getColumn(2).setCellEditor(hex3Editor);
            lineHeight = scrollPane.THREE_LINE_HEIGHT;
        } else if (layoutIdx == fullLineIndex + 4) {
            if (hex2RendorA == null) {
                hex2RendorA = new HexGenericRender(font, new HexTwoLineFieldAlt(font));
                hex2EditorA = new HexTableCellEditor(new HexTwoLineFieldAlt(font));
            }

            tcm.getColumn(2).setCellRenderer(hex2RendorA);
            tcm.getColumn(2).setCellEditor(hex2EditorA);
            lineHeight = scrollPane.TWO_LINE_HEIGHT;
        }

        setRowHeight(lineHeight);

        if (scrollPane != null) {
    	   int rowCount = view.getRowCount();
    	   int width = 5;
    	   //System.out.println("Setup fixed columns ... ");
           scrollPane.setFixedColumns(NUMBER_OF_CONTROL_COLUMNS);
           TableColumn tc = scrollPane.getFixedTable().getColumnModel().getColumn(0);
           tc.setCellRenderer(tableBtn);
           tc.setPreferredWidth(5);
           if ( scrollPane.getFixedTable().getColumnModel().getColumnCount() <= 1) {
        	   Common.logMsg("Error No Fields defined in the layout !!!", null);
           } else {
	           tc = scrollPane.getFixedTable().getColumnModel().getColumn(1);
	           if (rowCount > 10000000) {
	        	   width = 8;
	           } else if (rowCount > 1000000) {
	        	   width = 7;
	           } else if (rowCount > 100000) {
	        	   width = 6;
	           }
	           tc.setPreferredWidth(SwingUtils.STANDARD_FONT_WIDTH * width);
	           tc.setResizable(false);

	           scrollPane.correctFixedSize();
           }
        }

       if (layoutIdx >= fullLineIndex) {
    	   Common.calcColumnWidths(tbl, 1);
    	   copyMenu.removeAll();
    	   moveMenu.removeAll();
       } else {
    	   buildDestinationMenus();
       }
       return tbl;
    }


    private void setKeylistner(JTable tbl) {

        if (isPrefered || childFrame != null) {
        	if (tbl == tblDetails) {
        		tbl.addKeyListener(keyListner);
        	}
        	lastRow = - 1;
        	checkForRowChange(tbl.getSelectedRow());
        } else {
        	tbl.removeKeyListener(keyListner);
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

            if (fieldNum >= 0 && isCurrLayoutIdx(layoutId))  {
                stopCellEditing();
                tblDetails.editCellAt(newRow, FieldMapping.getAdjColumn(fieldMapping, layoutId, fieldNum));
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
    		checkForRowChange(table.getSelectedRow());
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
						fileView.newLine(startRow, 0);
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
			|| action == ReActionHandler.AUTOFIT_COLUMNS
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


    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.FieldSequencePnl#getFieldSequence()
	 */
    @Override
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
    	//System.out.println(" ~~~ size: " + ret.length);
    	for (int i = 0; i < fields.length; i++) {
    		idx = fields[i];
    		//System.out.print(" " + j + " " + i + " " + idx);
    		if (idx >= st) {
	    		ret[j++] = fileView.getRealColumnName(
	    				layoutIdx,
	    				fileView.getRealColumn(
	    						layoutIdx,
	    						idx - st));
	    		//System.out.print(" - " + ret[j-1]);
    		}
    		//System.out.println(" ");
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

    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.FieldSequencePnl#setFieldSequence(net.sf.RecordEditor.jibx.compare.FieldSequence)
	 */
    @Override
	public void setFieldSequence(FieldSequence seq) {
    	int[][] fields = new int[2][];
    	HashMap<String, Integer> map = new HashMap<String, Integer>();
    	int st = FileView.LINE_NUMER_COLUMN+1;
    	int layoutIdx = layout.getRecordIndex(seq.name);

    	if (layoutIdx >= 0) {
    		setLayoutIndex(layoutIdx);
	    	for (int i = st; i < fileView.getColumnCount(); i++) {
//	    		System.out.println(" ~~> " + i + " " + (i-st)
//	    				+ " " + fileView.getRealColumn(layoutIdx, i - st)
//	    				+ " : " + fileView.getRealColumnName(
//			    						layoutIdx,
//			    						fileView.getRealColumn(layoutIdx, i - st))
//	    				);
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

//    		System.out.println(" ==> " + i
//    				+ " " + name + " " + map.containsKey(name)
//    				+ " : " + ret[i]
//    				);
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

		DisplayFrame parentFrame = getParentFrame();
		if (parentFrame != null) {
			parentFrame.setToActiveFrame();
		}
	}











    //  /**
//  *
//  * @author Bruce Martin
//  *
//  * This class supplies Column Tips (displayed
//  * when the cursor is held over a column).
//  */
// private class HeaderToolTips extends JTableHeader {
//     private String[] tips;
//
//     /**
//      *
//      * @param columnModel column model to use
//      */
//     public HeaderToolTips(final TableColumnModel columnModel) {
//         super(columnModel);
//     }
//
//     /**
//      * Define the Column Heading Tips
//      *
//      * @param strings - List of strings Tips
//      */
//     public void setTips(String[] strings) {
//         tips = strings;
//     }
//
//     /**
//      * @see javax.swing.table.JTableHeader#getToolTipText
//      */
//     public String getToolTipText(MouseEvent m) {
//         String tip = super.getToolTipText(m);
//         int col = tblDetails.columnAtPoint(m.getPoint());
//         if ((tips != null) && ((col - 2 < tips.length) && (col > 1))) {
//             tip = tips[col - 2];
//         }
//
//         return tip;
//     }
//}

    private class AddColumn extends ReAbstractAction {
    	private boolean afterDest;

		public AddColumn(String name, boolean after) {
			super(name, Common.getRecordIcon(Common.ID_COLUMN_INSERT_ICON));
			this.afterDest = after;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				int col = tblDetails.getColumnModel().getColumn(mainPopupCol).getModelIndex()
						- FileView.SPECIAL_FIELDS_AT_START;


				if (afterDest) {
					col += 1;
				}

				stopCellEditing();
				Code.addColumn(fileMaster, (LayoutDetail) layout, col, Constants.NULL_INTEGER);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }


    private class MoveCopyColumn extends AbstractAction {
    	private boolean copyFunc;
    	private int dest;

		public MoveCopyColumn(String name, boolean copy, int destination) {
			super(name);
			this.copyFunc = copy;
			this.dest = destination;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				int col = tblDetails.getColumnModel().getColumn(mainPopupCol).getModelIndex()
						- FileView.SPECIAL_FIELDS_AT_START;
				LayoutDetail l = (LayoutDetail) layout;

				stopCellEditing();
				if (copyFunc) {
					Code.addColumn(fileMaster, l, dest, col);
				} else {
					Code.moveColumn(fileMaster, l, dest, col);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }

    private class DeleteColumn extends ReAbstractAction {

		public DeleteColumn() {
			super("Delete Column",
				  Common.getRecordIcon(Common.ID_COLUMN_DELETE_ICON));
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				int col = tblDetails.getColumnModel().getColumn(mainPopupCol).getModelIndex()
						- FileView.SPECIAL_FIELDS_AT_START;

				stopCellEditing();
				Code.deleteColumn(fileMaster, (LayoutDetail) layout, col);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getNewDisplay(net.sf.RecordEditor.edit.file.FileView)
	 */
	@Override
	protected BaseDisplay getNewDisplay(@SuppressWarnings("rawtypes") FileView view) {

		return new LineList(view.getLayout(), view, this.fileMaster);
	}
}
