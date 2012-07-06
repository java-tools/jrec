package net.sf.RecordEditor.edit.display.util;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.ComboBoxs.DelimitierCombo;


//TODO Create Builder & seperate panel classes + include write logic from SaveAsNew
//TODO Create Builder & seperate panel classes + include write logic from SaveAsNew
//TODO Create Builder & seperate panel classes + include write logic from SaveAsNew

@SuppressWarnings("serial")
public class SaveAsPnl extends BaseHelpPanel {

	public final static int FMT_DATA = 0;
	public final static int FMT_CSV = 1;
	public final static int FMT_FIXED = 2;
	public final static int FMT_XML = 3;
	public final static int FMT_HTML = 4;
	public final static int FMT_XSLT = 5;
	public final static int FMT_VELOCITY = 6;

	public final static int SINGLE_TABLE = 1;
	public final static int TABLE_PER_ROW = 2;
	public final static int TREE_TABLE = 3;
	public final static String[] FIXED_COL_NAMES = LangConversion.convertColHeading(
			"Export_Fixed_Field_Selection",
			new String[] {"Field Name", "Include", "Length"});

	public final static String[] TITLES = {
		"Data",
		"CSV",
		"Fixed",
		"Xml",
		"Html",
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

    public final DelimitierCombo delimiterCombo  = DelimitierCombo.NewDelimCombo();
    public final JComboBox quoteCombo = new JComboBox(Common.QUOTE_LIST);
    public final JCheckBox quoteAllTextFields = new JCheckBox();
    public final JTextField xsltTxt  = new JTextField();

    public final JTextField font = new JTextField();

    private final ButtonGroup grp = new ButtonGroup();
    private final JRadioButton singleTable = generateRadioButton("Single Table");
    private final JRadioButton tablePerRow = generateRadioButton("Table per Row");
    private final JRadioButton treeTable = generateRadioButton("Tree Table");


    public final JCheckBox onlyData   = new JCheckBox();
    public final JCheckBox showBorder = new JCheckBox();
    public final JCheckBox namesFirstLine = new JCheckBox();
    public final JCheckBox spaceBetweenFields = new JCheckBox();
    public final FileChooser template;

    private AbstractRecordDetail<?> record;
    private int[] fieldLengths, suppliedFieldLengths;
    private boolean[] includeFields;
    private FldTblMdl fixedModel;
    private JTable fieldTbl;

    public int rowCount;


	public SaveAsPnl(int format) {
		FileChooser templ = null;
		String ext = "$";
		int extType = RecentFiles.RF_NONE;

        this.setGap(BasePanel.GAP1);
		switch (format) {
		case FMT_DATA:
			addDescription("Export data in native format\n\nChange the tab to change Data format");
			break;
		case FMT_XML:
			addDescription("Export data as an XML file");
			ext = ".xml";
			break;
		case FMT_FIXED:
			ext = ".txt";
			layoutFixedPnl();
			break;
		case FMT_CSV:
			ext = ".csv";
			layoutCsvPnl();
			break;
		case FMT_HTML:
			ext = ".html";
			this.addLine("Table Type:", singleTable);
			this.addLine("", tablePerRow);
			this.addLine("", treeTable);
			this.setGap(GAP0);
			addHtmlFields();
			break;
		case FMT_XSLT:
			ext = ".xml";
			extType = RecentFiles.RF_XSLT;

			this.addLine("Xslt Engine (leave blank for default)", xsltTxt);

			xsltTxt.setText(Common.OPTIONS.XSLT_ENGINE.get());
			templ = new FileChooser(true, "get Xslt");
			templ.setText(Common.OPTIONS.DEFAULT_XSLT_DIRECTORY.get());
            this.addLine("Xslt File", templ, templ.getChooseFileButton());
			break;
		case FMT_VELOCITY:
			extType = RecentFiles.RF_VELOCITY;
			addHtmlFields();
			templ = new FileChooser(true, "get Template");

            templ.setText(Common.OPTIONS.DEFAULT_VELOCITY_DIRECTORY.get());
            this.addLine("Velocity Template", templ, templ.getChooseFileButton());
		}

		template = templ;
		extension = ext;
		panelFormat = format;
		extensionType = extType;
	}

	private void layoutCsvPnl() {
		BasePanel pnl1 = new BasePanel();
		BasePanel pnl2 = new BasePanel();

		pnl1.addLine("Delimiter", delimiterCombo);
		pnl1.addLine("Quote", quoteCombo);
		pnl1.addLine("names on first line", namesFirstLine);
		pnl1.addLine("Add Quote to all Text Fields", quoteAllTextFields);

		fieldTbl = new JTable();
		pnl2.addComponent(1, 5, 130, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,fieldTbl);


		addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnl1, pnl2));
	}


	private void layoutFixedPnl() {
		BasePanel pnl1 = new BasePanel();
		BasePanel pnl2 = new BasePanel();

		pnl1.addLine("names on first line", namesFirstLine);
		pnl1.addLine("space between fields", spaceBetweenFields);

		fieldTbl = new JTable();
		pnl2.addComponent(1, 5, 130, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL, fieldTbl);


		addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnl1, pnl2));
	}


	private void addDescription(String s) {
		JTextArea area = new JTextArea(s);

		this.addComponent(1, 5,BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                area);
	}
    private void addHtmlFields() {
        this.addLine("Only Data Column", onlyData);
        this.addLine("Show Table Border", showBorder);
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
	 * @param layout the layout to set
	 */
	public void setRecordDetails(
			AbstractRecordDetail<?> recordDef,
			int[] fldLengths,
			boolean[] incFields) {
		this.record = recordDef;
		this.suppliedFieldLengths = fldLengths;

		includeFields = incFields;

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

	private class FldTblMdl extends AbstractTableModel {

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
				ret = Boolean.valueOf(includeFields[row]);
				break;
			case COL_LENGTH:
				int v = suppliedFieldLengths[row];

				if (fieldLengths[row] > 0) {
					v = fieldLengths[row];
				} else if (namesFirstLine.isSelected() && record != null) {
					v = Math.max(
							suppliedFieldLengths[row],
							record.getField(row).getName().length());
				}
				ret = Integer.valueOf(v);
				break;
			}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return ret;
		}

	}
}
