package net.sf.RecordEditor.edit.display.SaveAs;

import java.io.IOException;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.edit.open.StartEditor;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.DisplayType;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.ScriptData;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.utils.FieldWriter;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.FileChooser;

public abstract class SaveAsPnlBase {
	
	public final static int SINGLE_TABLE = 1;
	public final static int TABLE_PER_ROW = 2;
	public final static int TREE_TABLE = 3;
	public final static String[] FIXED_COL_NAMES = {"Field Name", "Include", "Length"};
	
	public final static String[] TITLES = {
		"Data",
		"CSV",
		"Fixed",
		"Xml",
		"Html",
		"Script",
		"XSLT Transform",
		"Velocity"
	};
	
//	public final static String[] XSLT_OPTIONS = {
//		"",
//		"net.sf.saxon.TransformerFactoryImpl",
//		"org.apache.xalan.processor.TransformerFactoryImpl"
//	};
	
	
	private final static int NULL_INT = Constants.NULL_INTEGER;
	private final static int COL_NAME = 0;
	private final static int COL_INCLUDE = 1;
	private final static int COL_LENGTH = 2;
	
	private final static int[] FMT_TO_NUMBER_OF_COLS = {1, 2, 3};
	

	public final String extension;
	public final int panelFormat, extensionType;

	protected final CommonSaveAsFields commonSaveAsFields;

    
	public final BaseHelpPanel panel = new BaseHelpPanel(this.getClass().getSimpleName());
	
    public final JComboBox delimiterCombo  = new JComboBox(Common.FIELD_SEPARATOR_LIST1);
    public final JComboBox quoteCombo = new JComboBox(Common.QUOTE_LIST);
    public final JCheckBox quoteAllTextFields = new JCheckBox();
    public final JTextField xsltTxt  = new JTextField();
   
    public final JTextField font = new JTextField();
    
    private   final ButtonGroup grp = new ButtonGroup();
    protected final JRadioButton singleTable = generateRadioButton("Single Table");
    protected final JRadioButton tablePerRow = generateRadioButton("Table per Row");
    protected final JRadioButton treeTable   = generateRadioButton("Tree Table");
   
    
    public final JCheckBox onlyData   = new JCheckBox();
    public final JCheckBox showBorder = new JCheckBox();
    public final JCheckBox namesFirstLine = new JCheckBox();
    public final JCheckBox spaceBetweenFields = new JCheckBox();
    public final FileChooser template;
    
    protected JTable fieldTbl;
    protected FldTblMdl fixedModel;

    
    private AbstractRecordDetail<?> record;
    private int[] fieldLengths, suppliedFieldLengths;
    private boolean[] includeFields;

    
    public int rowCount;
    
    private FileView file;

    
    
    public SaveAsPnlBase(CommonSaveAsFields commonSaveAsFields, String extension, int panelFormat, int extensionType,
			FileChooser template) {
		super();

		this.commonSaveAsFields = commonSaveAsFields;
		this.extension = extension;
		this.panelFormat = panelFormat;
		this.extensionType = extensionType;
		this.template = template;
		file = commonSaveAsFields.getRecordFrame().getFileView();
		
        String f = file.getLayout().getFontName();
		if (f != null && f.toLowerCase().startsWith("utf")) {
   			font.setText(f);
   		}
	}
    
    
    protected final void addDescription(String s) {
		JTextArea area = new JTextArea(s);
		
		panel.addComponent(1, 5,BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                area);
	}

    protected final void addHtmlFields() {
    	panel.addLine("Only Data Column", onlyData);
    	panel.addLine("Show Table Border", showBorder);
    }


	private JRadioButton generateRadioButton(String s) {
    	JRadioButton btn = new JRadioButton(s);
    	
    	grp.add(btn);
    	return btn;
    }

   public int getTableOption() {
    	int ret = SINGLE_TABLE;
    	
    	if (this.tablePerRow.isSelected()) {
    		ret = TABLE_PER_ROW;
    	} else if (this.treeTable.isSelected()) {
    		ret = TREE_TABLE;
    	}
    	
    	return ret;
    }
    
    public void setTableOption(int opt) {
    	switch (opt) {
    	case SINGLE_TABLE:  this.singleTable.setSelected(true); break;
    	case TABLE_PER_ROW: this.tablePerRow.setSelected(true); break;
    	case TREE_TABLE:    this.treeTable.setSelected(true);   break;
    	}
    }

	public String getTitle() {
		return TITLES[this.panelFormat];
	}
	
	public String getQuote() {
		String ret = "";
		int idx = quoteCombo.getSelectedIndex();
		
		if (idx >= 0) {
			ret = Common.QUOTE_VALUES[idx];
		}
		
		return ret;
	}


	/**
	 * @return the fieldLengths
	 */
	public int[] getFieldLengths() {
	
		if (this.namesFirstLine.isSelected() && record != null) {
			for (int i = 0; i < fieldLengths.length; i++) {
				if (fieldLengths[i] < 0) {
					fieldLengths[i] = Math.max(
							suppliedFieldLengths[i], 
							record.getField(i).getName().length());
				}
			}
		} else {
			for (int i = 0; i < fieldLengths.length; i++) {
				if (fieldLengths[i] < 0) {
					fieldLengths[i] = suppliedFieldLengths[i];
				}
			}
		}

		return fieldLengths;
	}

	/**
	 * @return the includeFields
	 */
	public boolean[] getIncludeFields() {
		return includeFields;
	}
	
	
    
    public abstract void save(String selection, String outFile)  throws Exception ; 


    protected final void save_writeFile(FieldWriter writer, String selection) 
    throws IOException{

    	if (commonSaveAsFields.treeExportChk.isSelected()) {
    		commonSaveAsFields.flatFileWriter.writeTree(
        			writer, commonSaveAsFields.getTreeFrame().getRoot(),  
        			namesFirstLine.isSelected(), 
        			! commonSaveAsFields.nodesWithDataChk.isSelected(),
        			commonSaveAsFields.getRecordFrame().getLayoutIndex());
        } else {
        	commonSaveAsFields.flatFileWriter.writeFile(
        			writer, namesFirstLine.isSelected(), commonSaveAsFields.getWhatToSave(selection));
        }
   	}
    
    public void edit(String outFile, String ext) {
    	@SuppressWarnings("rawtypes")
		AbstractLayoutDetails layout = getEditLayout(ext);
    	String lcExt = ext.toLowerCase();
    	StandardLayouts genLayout = StandardLayouts.getInstance();
    	
    	if (layout == null) {
	    	if (".xml".equals(lcExt) || ".xsl".equals(lcExt)) {
	    		layout = genLayout.getXmlLayout();
	    	} else if (".csv".equals(lcExt)) {
	        	layout = genLayout.getGenericCsvLayout();
	        }
    	}
    	
    	if (layout == null) {
    		throw new RuntimeErrorException(null, "Can not edit the File: Can not determine the format");
    	} else {
    		FileView newFile = new FileView(layout,
    				ReIOProvider.getInstance(),
        			false);
    		StartEditor startEditor = new StartEditor(newFile, outFile, false, commonSaveAsFields.message ,0);
    		
    		startEditor.doEdit();

    	}
    }
   
    @SuppressWarnings("rawtypes")
	public AbstractLayoutDetails getEditLayout(String ext) {
    	return null;
    }
    
    @SuppressWarnings("rawtypes")
	protected final List<AbstractLine> saveFile_getLines(String selection) {
    	return commonSaveAsFields.getViewToSave(selection).getLines();
    }
    
    
    protected final ScriptData getScriptData(String selection, String outFile) {
        AbstractLineNode root = null;
         
        if (commonSaveAsFields.getTreeFrame() != null) {
        	root = commonSaveAsFields.getTreeFrame().getRoot();
         }
        
	   	return new ScriptData(
	   				saveFile_getLines(selection), 
	   				commonSaveAsFields.file,
	        		root,
	        		onlyData.isSelected(), showBorder.isSelected(),
	        		commonSaveAsFields.getRecordFrame().getLayoutIndex(),
	        		outFile);
    }
	
	
	/**
	 * @param layout the layout to set
	 */
	public void setRecordDetails(int[] fldLengths) {
		this.record = commonSaveAsFields.printRecordDetails;
		this.suppliedFieldLengths = fldLengths;
		
		includeFields = commonSaveAsFields.flatFileWriter.getFieldsToInclude();
		
		if (fldLengths != null) {
			rowCount = suppliedFieldLengths.length;
			fieldLengths = new int[rowCount];
							
			for (int i = 0; i < fieldLengths.length; i++) {
				fieldLengths[i] = NULL_INT;
			}
			
		}
		
		if (record != null) {
			rowCount = record.getFieldCount();
			fixedModel = new FldTblMdl();
			fieldTbl.setModel(fixedModel);
			
			TableColumnModel tcm = fieldTbl.getColumnModel();
			tcm.getColumn(COL_INCLUDE).setCellRenderer(new CheckBoxTableRender());
			tcm.getColumn(COL_INCLUDE).setCellEditor(new DefaultCellEditor(new JCheckBox()));

			if (includeFields == null) {
				includeFields = new boolean[rowCount];
				for (int i = 0; i < rowCount; i++) {
					includeFields[i] = true;
				}
			}
			namesFirstLine.addChangeListener(new ChangeListener() {

				/* (non-Javadoc)
				 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
				 */
				@Override
				public void stateChanged(ChangeEvent arg0) {
					fixedModel.fireTableDataChanged();
				}
				
			});
		}
	}

    
	@SuppressWarnings("serial")
	protected final  class FldTblMdl extends AbstractTableModel {

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int row) {

			return FIXED_COL_NAMES[row];
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int row, int col) {

			return col != COL_NAME;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object val, int row, int col) {
			if (val != null) {
			switch (col) {
				case COL_INCLUDE: 
					if (val instanceof Boolean) {
						includeFields[row] = ((Boolean) val).booleanValue();
					}
					break;
				case COL_LENGTH: 
					try {
						int v = new Integer(val.toString().trim());
						if (v > 0) {
							fieldLengths[row] = v;
						}
					} catch (Exception e) {

					}

					break;
				}
			}
			
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return FMT_TO_NUMBER_OF_COLS[panelFormat];
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return includeFields.length;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int col) {
			Object ret = null;
			
			try {
			switch (col) {
			case COL_NAME: 
				if (record == null) {
					ret = "Column " + row;
				} else {
					ret = record.getField(row).getName();
				} 
				break;
			case COL_INCLUDE: 
				if (includeFields != null) {
					ret = Boolean.valueOf(includeFields[row]);
				}
				break;
			case COL_LENGTH: 
				if (suppliedFieldLengths != null) {
					int v = suppliedFieldLengths[row];
					
					if (fieldLengths[row] > 0) {
						v = fieldLengths[row];
					} else if (namesFirstLine.isSelected() && record != null) {
						v = Math.max(
								suppliedFieldLengths[row], 
								record.getField(row).getName().length());
					} 
					ret = Integer.valueOf(v);
				}
				break;
			}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return ret;
		}
		
	}
	
	protected final void setupPrintDetails(boolean isFixed) {
			AbstractLayoutDetails<?,?> l = file.getLayout();
			int layoutIdx = file.getCurrLayoutIdx();
			int[] colLengths = null;
			commonSaveAsFields.printRecordDetails = null;
			
			switch (DisplayType.displayType(l, commonSaveAsFields.getRecordFrame().getLayoutIndex())) {
			case DisplayType.NORMAL:
				commonSaveAsFields.printRecordDetails = l.getRecord(layoutIdx);
				if (isFixed) {
					colLengths = getFieldWidths();
				}
				break;
			case DisplayType.PREFFERED:
				commonSaveAsFields.printRecordDetails = l.getRecord(DisplayType.getRecordMaxFields(l));
				if (isFixed) {
					colLengths = getFieldWidthsPrefered();
				}
				break;  				
			case DisplayType.HEX_LINE:
				colLengths = new int[1];
				colLengths[0] = l.getMaximumRecordLength() * 2;
				break;  				
			}

			setRecordDetails(colLengths);

	}
	
	public boolean isActive() {
		return true;
	}
	
	private int[] getFieldWidths() {
		AbstractLayoutDetails<?,?> l = file.getLayout();
		int layoutIdx = file.getCurrLayoutIdx();
		int[] ret = new int[l.getRecord(layoutIdx).getFieldCount()];
		int en = Math.min(3000, file.getRowCount());

		for (int i = 0; i < en; i++) {
			calcColLengthsForLine(ret, file.getTempLine(i), l, layoutIdx);
		}

		return ret;
	}


	private int[] getFieldWidthsPrefered() {
		AbstractLayoutDetails<?,?> l = file.getLayout();
		int layoutIdx = DisplayType.getRecordMaxFields(l);
		int[] ret = new int[l.getRecord(layoutIdx).getFieldCount()];
		int en = Math.min(3000, file.getRowCount());
		AbstractLine<?> line;

		for (int i = 0; i < en; i++) {
			line = file.getTempLine(i);
			calcColLengthsForLine(ret, line, l, line.getPreferredLayoutIdx());
		}

		return ret;
	}

	/**
	 * @param text
	 * @see javax.swing.text.JTextComponent#setText(java.lang.String)
	 */
	public void setTemplateText(String text) {
		
		if (template != null) {
			template.setText(text);
		}
	}


	private void calcColLengthsForLine(
			int[] ret, AbstractLine<?> line, 
			AbstractLayoutDetails<?,?> layout, int layoutIdx) {
		Object o;
		int colCount = layout.getRecord(layoutIdx).getFieldCount();
		for (int j = 0; j < colCount; j++) {
			o = line.getField(layoutIdx,  j);

			if (o != null) {
				ret[j] = Math.max(ret[j], o.toString().length());
			}
		}
	}
}
