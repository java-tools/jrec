/*
 * @Author Bruce Martin
 * Created on 24/07/2005
 *
 * Purpose:
 *   Allow the user to create a filtered view of the file being
 *  editted.
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 *   - add Check / uncheck fields buttons
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Changed to use standard ComboBoxRender (instead of internal version
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Removed call to BasePanel.done() (done automatically)
 *   - hex and Text updates
 *   - Creating views from selected records
 *   - JRecord Spun off
 *
 * # Version 0.62 Bruce Martin 2007/04/30
 *   - adding support for enter key
 **/
package net.sf.RecordEditor.re.script.bld;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.editProperties.CommonCode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.IEditor;
import net.sf.RecordEditor.re.script.RunVelocity;
import net.sf.RecordEditor.re.script.runScreen.ScriptRunFrame;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.JFlipBtn;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileTreeComboItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;



/**
 * Class to display / update Filter details
 * (i.e. which records are to be displayed).
 *
 * @author Bruce Martin
 *
 * @version 0.90
 */
@SuppressWarnings("serial")
public final class ScriptBld extends BaseHelpPanel
implements ActionListener {

    private   static final int FIRST_COLUMN_WIDTH     = SwingUtils.STANDARD_FONT_WIDTH * 35;
    private   static final int SECOND_COLUMN_WIDTH    = SwingUtils.STANDARD_FONT_WIDTH * 9;

    protected static final int FIELD_VALUE_ROW_HEIGHT = SwingUtils.COMBO_TABLE_ROW_HEIGHT;
    protected static final int FIELD_NAME_WIDTH       = SwingUtils.STANDARD_FONT_WIDTH * 22;

    private   static final List<FileTreeComboItem> SCRIPT_FILES = new ArrayList<FileTreeComboItem>();
    
    private   static final String[] englishDataSrc = {
    	"File",
    	"View",
    	"Selection"
    };
    private   static final String[] DATA_SRC = LangConversion.convertComboItms("scriptUpdateSrc", englishDataSrc);
 
    private TreeComboFileSelect fileCombo = new TreeComboFileSelect(true, false, true, SCRIPT_FILES, ScriptRunFrame.getRecent().getDirectoryList());
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox dataSrcCombo = new JComboBox(DATA_SRC);
    
    private JEditorPane tips;

    private JPanel recordOptionPanel = new JPanel();
    private JPanel fieldOptionPanel  = new JPanel();
    protected final JTable recordTbl = new JTable();
    private JTable fieldTbl  = new JTable();
//    private JTable filterFieldTbl = new JTable();
    private AbstractTableModel recordMdl;
    private AbstractTableModel fieldMdl;

    private JLabel fieldHeadingLbl = new JLabel();

    private JTextField msgTxt     = new JTextField();

    private JFlipBtn checkAllRecordsBtn   = SwingUtils.newFlipButton("Check Records", "Uncheck Records");
//    private JButton uncheckAllRecordsBtn = SwingUtils.newButton("Uncheck Records");

    private JFlipBtn checkAllFieldsBtn    = SwingUtils.newFlipButton("Check Select-On Fields", "Uncheck Select-On Fields");
    private JFlipBtn checkAllUpdateFieldsBtn    = SwingUtils.newFlipButton("Check Update Fields", "Uncheck Update Fields");
//    private JButton uncheckAllFieldsBtn  = SwingUtils.newButton("Uncheck Fields");
//    private JButton showHideFieldBtn     = new JButton(showFldBtnText);

    private ScriptBdDetails filter;
	private AbstractLayoutDetails recordLayout;
	
	private JButton buildBtn = SwingUtils.newButton("Build");

//    private final SaveLoadPnl<EditorTask> savePnl;

   // private boolean addExecute;
	private final String scriptTemplate;
    //private boolean toInit = true;
    private final IEditor editor;
    
    private final ReFrame activeDisplay;
    private final AbstractFileDisplay activeFileDisplay;
    private ReFrame frame;
    private final ScriptOption scriptOption;
    

//    private boolean addFieldFilter = true;

//	private final MouseAdapter fieldMouseListner = new MouseAdapter() {
//
//	  /**
//       * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
//       */
//		public void mousePressed(MouseEvent m) {
//			try {
//				int col = filterFieldTbl.columnAtPoint(m.getPoint());
//				int row = filterFieldTbl.rowAtPoint(m.getPoint());
//				int tblCol = filterFieldTbl.getColumnModel().getColumn(col).getModelIndex();
//
//				if (tblCol == FilterField.FLD_AND_VAL || tblCol == FilterField.FLD_OR_VAL) {
//					FilterField rec = filterFieldMdl.getFilterField(row);
//
//					//rec.setBooleanOperator(1 - rec.getBooleanOperator());
//					//filterFieldMdl.clearRecordSelection();
//					filterFieldMdl.setValueAt(1 - rec.getBooleanOperator(), row, tblCol);
//
//					filterFieldMdl.fireTableRowsUpdated(row, row);
//					System.out.print(" ## " + row);
//				}
//			} catch (Exception e) {
//				// if it does not work so what
//				e.printStackTrace();
//			}
//		}
//	};


//    public FilterPnl2(boolean pAddExecute) {
//    	addExecute = pAddExecute;
//
//    	addFieldFilter = false;
//    }


    /**
     * Display Filter on the screen for the user to update
     *
     * @param fileTbl file to be filtered
     */
    public ScriptBld(
    		ReFrame activeDisplay,
    		AbstractFileDisplay activeFileDisplay, 
    		IEditor editor,
    		String scriptTemplate,
    		String scriptName,
    		String scriptType) {
    	super();

    	this.scriptTemplate = scriptTemplate;
    	this.editor = editor;
    	this.activeDisplay = activeDisplay;
    	this.activeFileDisplay = activeFileDisplay;
    	this.recordLayout = activeFileDisplay.getFileView().getLayout();
		this.scriptOption = ScriptOptionMgr.getInstance().get(scriptName);

    	
    	String destDir = Common.OPTIONS.DEFAULT_SCRIPT_DIRECTORY.getSlashNoStar() + "UserScripts" + Common.FILE_SEPERATOR;
    	File destFile = new File(destDir);
		String tmp = destDir + scriptName;
    	if (activeFileDisplay != null) {
    		tmp = tmp + "_" + activeFileDisplay.getFileView().getLayout().getLayoutName();
    	}
    	
    	if (! destFile.exists()) {
    		destFile.mkdirs();
    	}
    	
    	fileCombo.setText(tmp + scriptType);
    	dataSrcCombo.setSelectedIndex(1);

    	tips = new JEditorPane(
		    				"text/html",
		    				LangConversion.convertId(LangConversion.ST_MESSAGE, "ScriptBldTip", 
		    							"<h3>Script Build</h3>"
		    						+   "This option will build a sample {0} script."
		    						+    "<br>Just select the appropriate options",
		    						scriptName)
    						+ "<p><b>Script Description:</b>" + scriptOption.description);
    	

        init_100_setupFields();

    	init_200_layoutScreen(0);
    	
    	init_300_finalise(scriptName);
    }


	/**
     * define screen fields
     */
    private void init_100_setupFields() {

        filter = new ScriptBdDetails(recordLayout, scriptOption); 
        filter.setMessageFld(msgTxt);
//        filter.set2Layouts(false);


        fieldMdl       = filter.getFieldListMdl();
 //       filterFieldMdl = filter.getFilterFieldListMdl();
        recordMdl      = filter.getLayoutListMdl();

    	recordTbl.setModel(recordMdl);
        fieldTbl.setModel(fieldMdl);

        //if (addFieldFilter) {
        //	filterFieldTbl.setModel(filterFieldMdl);
        //	buildFieldFilterTable();
        //}

        setTableDetails(recordTbl);
        setTableDetails(fieldTbl);

 //       recordOptionPanel.add(uncheckAllRecordsBtn);
        recordOptionPanel.add(checkAllRecordsBtn);

 //       fieldOptionPanel.add(uncheckAllFieldsBtn);
        if (scriptOption.selectOn) {
        	fieldOptionPanel.add(checkAllFieldsBtn);
        }
        
        if (scriptOption.update) {
        	fieldOptionPanel.add(checkAllUpdateFieldsBtn);
        }

		recordTbl.addMouseListener(new MouseAdapter() {
				public void mousePressed(final MouseEvent m) {
				    int idx = recordTbl.getSelectedRow();

				    if (idx >= 0 && idx < recordTbl.getRowCount()) {
				        //if (addFieldFilter && idx != filter.getLayoutIndex()) {
				        //	Common.stopCellEditing(filterFieldTbl);
				        //}
				        filter.setLayoutIndex(idx);
				        fieldMdl.fireTableDataChanged();
				        //if (addFieldFilter) {
				        //	filterFieldMdl.fireTableDataChanged();
				        //}

				        fieldHeadingLbl.setText(UtMessages.FIELD_SELECTION.get(recordLayout.getRecord(idx).getRecordName()));
				    }
				}
			});


			checkAllRecordsBtn.addActionListener(this);
			checkAllFieldsBtn.addActionListener(this);
			checkAllUpdateFieldsBtn.addActionListener(this);
			buildBtn.addActionListener(this);
			
			if (present(scriptOption.selectName)) {
				checkAllFieldsBtn.setTextValues("Check all " + scriptOption.selectName, "UnCheck all " + scriptOption.selectName);
			}
			
			if (present(scriptOption.updateName)) {
				checkAllUpdateFieldsBtn.setTextValues("Check all " + scriptOption.updateName, "UnCheck all " + scriptOption.updateName);
			}
			
//			showHideFieldBtn.addActionListener(this);

//			registerComponent(uncheckAllRecordsBtn);
//			registerComponent(checkAllRecordsBtn);
//			registerComponent(uncheckAllFieldsBtn);
//			registerComponent(checkAllFieldsBtn);
		
//			registerComponent(showHideFieldBtn);
//	    } else {
//	    	recordMdl.fireTableDataChanged();
//	    	fieldMdl.fireTableDataChanged();
//
//	    	//if (addFieldFilter) {
//	    	//	filterFieldMdl.fireTableDataChanged();
//	    	//}
//	    }
//		//execute.addActionListener(this);
    }

    private boolean present(String s) {
    	return s != null && s.length() > 0;
    }
    
	private void init_200_layoutScreen(int heightOverhead) {
 

//        if (toInit) {
			//JPanel pnl4 = new JPanel();

        	int height;
        	int maxTblColWidth = ReFrame.getDesktopWidth() / 4;
        	int desktopHeight = ReFrame.getDesktopHeight() - 50 - heightOverhead
        	                    - 2 * ((int) BasePanel.GAP1);
        	//JScrollPane scrollPane = new JScrollPane(pnl2);

        	desktopHeight -= SwingUtils.BUTTON_HEIGHT + 6;

        	//if (addExecute) {
        	desktopHeight -= SwingUtils.BUTTON_HEIGHT * 3;
        	//}


        	this.addComponentRE(1, 5, CommonCode.TIP_HEIGHT / 2, BasePanel.GAP1,
    		        BasePanel.FULL, BasePanel.FULL,
    				tips);
        	
        	addLineRE("Script File", fileCombo);
        	addLineRE("Update", dataSrcCombo);
        	if (recordLayout.getRecordCount() > 1) {
        		//int maxHeight = desktopHeight / 3;
        		//if (addFieldFilter) {
        		int	maxHeight = desktopHeight / 4;
        		//}
        		//pnl3.addHelpBtn(Common.getHelpButton());

        		//pnl2.addLine("", recordOptionPanel);
        		this.addComponentRE(1, 3,
        				 recordOptionPanel.getPreferredSize().getHeight(),
				         BasePanel.GAP,
				         BasePanel.FULL, BasePanel.FULL,
				         recordOptionPanel);
				height = SwingUtils.calculateTableHeight(recordTbl.getRowCount(), maxHeight);
				this.setHeightRE(SwingUtils.BUTTON_HEIGHT + 6);


				desktopHeight -= height + SwingUtils.BUTTON_HEIGHT + 6;
				this.addComponentRE(1, 3,
						 height,
				         BasePanel.GAP1,
				         BasePanel.FULL, BasePanel.FULL,
						 recordTbl);
				this.setComponentName(recordTbl, "RecordSelection");
				Common.calcColumnWidths(recordTbl, 0, maxTblColWidth);
	        }



    		this.addComponentRE(1, 3,
    				fieldOptionPanel.getPreferredSize().getHeight(),
			        BasePanel.GAP,
			        BasePanel.FULL, BasePanel.FULL,
			        fieldOptionPanel);
			this.setHeightRE(SwingUtils.BUTTON_HEIGHT + 6);



			int rows = fieldTbl.getRowCount();
			for (int i =0; i < recordLayout.getRecordCount(); i++) {
				rows = Math.max(rows, recordLayout.getRecord(i).getFieldCount());
			}
			height = SwingUtils.calculateTableHeight(rows, desktopHeight / 2);
			desktopHeight -= height;

			this.addComponentRE(1, 3, height, BasePanel.GAP1,
			         BasePanel.FULL, BasePanel.FULL,
			         fieldTbl);

			this.setComponentName(fieldTbl, "FieldSelection");

//			JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this, new JScrollPane(filterFieldTbl));

			Common.calcColumnWidths(fieldTbl, 0, maxTblColWidth);


			setGapRE(BasePanel.GAP0);
			addLineRE(null, null, buildBtn);

			addMessage(msgTxt);
	}
	
	private void init_300_finalise(String scriptName) {
		
		
		FileView baseFile = activeFileDisplay.getFileView().getBaseFile();
		
		if (! scriptOption.update) {
			 TableColumnModel columnModel = fieldTbl.getColumnModel();
			 columnModel.removeColumn(columnModel.getColumn(2));
		}
		
		fieldHeadingLbl.setText(UtMessages.FIELD_SELECTION.get(recordLayout.getRecord(0).getRecordName()));

		frame = new ReFrame(baseFile.getFileNameNoDirectory(), "Build Script", baseFile);
		frame.addMainComponent(this);
		frame.setToMaximum(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		frame.setToMaximum(false);
    }



//    /**
//     * Set Record Table
//     * @param tbl table to update
//     */
//    protected void setRecordTableDetails(JTable tbl) {
//    	setTableDetails(tbl);
//    }
//
//    /**
//     * Set Record Table
//     * @param tbl table to update
//     */
//    protected void setFieldTableDetails(JTable tbl) {
//    	setTableDetails(tbl);
//    }

    /**
     * Allocate Table attributes for the 2 include tables
     *
     * @param tbl table to have attributes set
     */
    private void setTableDetails(JTable tbl) {
        TableColumn tc;

        setTableDetailsCol0(tbl);

        for (int i = 1; i < tbl.getColumnCount(); i++) {
			tc = tbl.getColumnModel().getColumn(i);
		  	tc.setCellRenderer(new CheckBoxTableRender());
			tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));
			tc.setPreferredWidth(SECOND_COLUMN_WIDTH);
        }
    }

    protected final void setTableDetailsCol0(JTable tbl) {

        tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbl.getColumnModel().getColumn(0).setPreferredWidth(FIRST_COLUMN_WIDTH);
    }

//    /**
//     * build Field Filter Table
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//	private void buildFieldFilterTable() {
//        TableColumnModel tcm;
//        TableColumn tc;
//        DefaultComboBoxModel operatorMdl = new DefaultComboBoxModel
//                (Compare.OPERATOR_STRING_FOREIGN_VALUES);
//        ComboBoxRender operatorRendor = new ComboBoxRender(operatorMdl);
//        TableCellRenderer fieldRendor = filter.getFilterFieldListMdl().getTableCellRender();
//
//        filterFieldTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        filterFieldTbl.setRowHeight(FIELD_VALUE_ROW_HEIGHT);
//        tcm = filterFieldTbl.getColumnModel();
//
//		tc = tcm.getColumn(FilterField.FLD_OR_VAL);
//		tc.setPreferredWidth(AND_WIDTH);
//		tc = tcm.getColumn(FilterField.FLD_AND_VAL);
//		tc.setPreferredWidth(AND_WIDTH);
//
//		tc = tcm.getColumn(FilterField.FLD_FIELD_NUMBER);
//		tc.setPreferredWidth(FIELD_NAME_WIDTH);
//	  	tc.setCellRenderer(fieldRendor);
//		tc.setCellEditor(filter.getFilterFieldListMdl().getTableCellEditor());
//
//		tc = tcm.getColumn(FilterField.FLD_CASE_SENSITIVE);
//		tc.setPreferredWidth(CASE_SENSTIVE_WIDTH);
//	  	tc.setCellRenderer(new CheckBoxTableRender());
//		tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));
//
//
//		int opId = FilterField.FLD_OPERATOR;
//		int valueId = FilterField.FLD_VALUE;
//
//		if (filter.getFilterType() == FilterDetails.FT_GROUP) {
//			opId = FilterField.FLD_GROUP_OPERATOR;
//			valueId = FilterField.FLD_GROUP_VALUE;
//
//			tc = tcm.getColumn(FilterField.FLD_GROUPING);
//			tc.setPreferredWidth(OPERATOR_WIDTH);
//		  	tc.setCellRenderer(new ComboBoxRender(Compare.GROUPING_OPERATORS));
//			tc.setCellEditor(
//			    new DefaultCellEditor(
//			        new JComboBox(Compare.GROUPING_OPERATORS)));
//		}
//		tc = tcm.getColumn(opId);
//		tc.setPreferredWidth(OPERATOR_WIDTH);
//	  	tc.setCellRenderer(operatorRendor);
//		tc.setCellEditor(
//		    new DefaultCellEditor(
//		        new JComboBox(Compare.OPERATOR_STRING_FOREIGN_VALUES)));
//
//		tcm.getColumn(valueId).setPreferredWidth(VALUE_WIDTH);
//    }

    /**
     * Build filtered view of the data
     *
     * @see java.awt.event.ActionListner#actionPerformed
     */
    public void actionPerformed(ActionEvent event) {

    	stopTblEdit();

        if (event.getSource() == checkAllFieldsBtn) {
            updateIncludeFlag(1, Boolean.valueOf(checkAllFieldsBtn.isNormalState()));
            checkAllFieldsBtn.flipText();
        } else if (event.getSource() == checkAllUpdateFieldsBtn) {
            updateIncludeFlag(2, Boolean.valueOf(checkAllUpdateFieldsBtn.isNormalState()));
            checkAllUpdateFieldsBtn.flipText();
       } else if (event.getSource() == checkAllRecordsBtn) {
        	updateRecordFlag(Boolean.valueOf(checkAllRecordsBtn.isNormalState()));
        	checkAllRecordsBtn.flipText();
        } else if (event.getSource() == buildBtn) {
        	RunVelocity velocity = RunVelocity.getInstance();
			String filename = fileCombo.getText();
			
			if ("".equals(filename)) {
				
			} else {
	        	try {
					BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
					try {
						velocity.genSkel(scriptTemplate, recordLayout, "scriptSchema", 
								filter.getSchemaDetails(this.dataSrcCombo.getSelectedIndex()), 
								filename, w);
					} finally {
						w.close();
					}

					IEditor ed = editor;
					if (editor == null) {
						ed = new ScriptRunFrame(activeDisplay);
					}
					ed.loadFileName(filename);
					frame.setVisible(false);
				} catch (IOException e) {
					msgTxt.setText(e.toString());
					e.printStackTrace();
				} catch (Exception e) {
					msgTxt.setText(e.toString());
					e.printStackTrace();
				}
			}
        }
    }



//    /**
//	 * @see net.sf.RecordEditor.utils.swing.saveRestore.ISaveDetails#getSaveDetails()
//	 */
//	@Override
//	public EditorTask getSaveDetails() {
//		stopTblEdit();
//
//		return (
//			    new net.sf.RecordEditor.jibx.compare.EditorTask())
//			    .setFilter(getFilter().getExternalLayout());
//	}
//
//
//
//	public final void update(Layout values) {
//		getFilter().updateFromExternalLayout(values);
//		fireDataChanged();
//	}
	
	
	/**
     * Updates the include flag in the field list table
     *
     * @param val new value for the field table
     */
    private void updateRecordFlag(Boolean val) {
        int i;

        for (i = recordTbl.getRowCount() - 1; i >= 0; i--) {
        	recordTbl.setValueAt(val, i, 1);
        }
        recordMdl.fireTableDataChanged();
    }


    /**
     * Updates the include flag in the field list table
     *
     * @param val new value for the field table
     */
    private void updateIncludeFlag(int idx, Boolean val) {
        int i;

        for (i = fieldTbl.getRowCount() - 1; i >= 0; i--) {
            fieldMdl.setValueAt(val, i, idx);
        }
        fieldMdl.fireTableDataChanged();
    }

//    public final void setBooleanValue() {
//    	boolBtn.setValue(filter.getOp());
//    }

	/**
	 * @return the messageFld
	 */
	public final JTextField getMessageFld() {
		return msgTxt;
	}


//	/**
//	 * @return the filter
//	 */
//	public final FilterDetails getFilter() {
//		stopTblEdit();
//
//		if (groupStartCombo != null) {
//			filter.setGroupHeader(getGroupRecordId());
////			filter.setOp(getBooleanOp());
//		}
//
//		return filter;
//	}

	public final void fireDataChanged() {
		if (fieldMdl != null) {
	        fieldMdl.fireTableDataChanged();
//			filterFieldMdl.fireTableDataChanged();

	        recordMdl.fireTableDataChanged();
		}
	}

	private void stopTblEdit() {


        Common.stopCellEditing(recordTbl);
        Common.stopCellEditing(fieldTbl);

        //if (addFieldFilter) {
        //	Common.stopCellEditing(filterFieldTbl);
        //}
	}


//	public final int getBooleanOp() {
//		if (boolBtn != null) {
//			return boolBtn.getValue();
//		}
//		return Common.BOOLEAN_OPERATOR_AND;
//	}


//	public final int getGroupRecordId() {
//		if (groupStartCombo != null) {
//			return groupStartCombo.getLayoutIndex();
//		}
//		return 0;
//	}
//
//
//	/**
//	 * @return the execute
//	 */
//	public final JButton getExecute() {
//		return execute;
//	}
}
