package net.sf.RecordEditor.edit.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.open.DisplayBuilderFactory;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.ComboBoxs.DelimiterCombo;

public class NewCsvFile {

	private static int[][] STRUCTURES = {
		{Constants.IO_BIN_TEXT, Constants.IO_BIN_NAME_1ST_LINE},
		{Constants.IO_UNICODE_TEXT, Constants.IO_UNICODE_NAME_1ST_LINE},
	};

	public final ReFrame frame;
	public final BaseHelpPanel panel = new BaseHelpPanel();

	@SuppressWarnings("rawtypes")
	private BmKeyedComboModel styleModel = new BmKeyedComboModel(new ManagerRowList(
			ParserManager.getInstance(), false));

	private ColumnNameMdl colNameMdl = new ColumnNameMdl();

	private NumField   rowFld		 = new NumField("row", 5, null);
	private NumField   colFld		 = new NumField("row", 3, colNameMdl);

	private JCheckBox  namesChk		 = new JCheckBox();
	private JCheckBox  unicodeChk	 = new JCheckBox();
	private JTextField fontTxt		 = new JTextField();
	private DelimiterCombo fieldSep = DelimiterCombo.NewDelimCombo();
	private JComboBox  quote		 = new JComboBox(Common.QUOTE_LIST);
   private BmKeyedComboBox parser   = new BmKeyedComboBox(styleModel, false);

	//private JLabel     colNamesLbl   = new JLabel("Column Names:");
	private JTable     colNamesTbl	 = new JTable(colNameMdl);

	private JButton    goBtn	     = SwingUtils.newButton("Create");

	private JTextField msgTxt	     = new JTextField();

	private ArrayList<String> columnNames = new ArrayList<String>();

	public NewCsvFile() {
		this(new ReFrame("","New Csv File", null));
	}

	public NewCsvFile(ReFrame displayFrame) {
		frame = displayFrame;
		init_100_Setup();
		init_200_LayoutScreen();
		init_300_Listners();

		frame.setVisible(true);
	}

	private void init_100_Setup() {

		namesChk.setSelected(true);
	}

	private void init_200_LayoutScreen() {

		panel.setGap(BasePanel.GAP2);
		panel.addLine("Rows", rowFld.field);
		panel.addLine("Cols", colFld.field);
		panel.addLine("Names on First Line", namesChk);
		panel.addLine("Unicode", unicodeChk);
		panel.addLine("Font", fontTxt);
		panel.addLine("Field Seperator", fieldSep);
		panel.addLine("Quote", quote);
		panel.addLine("Parser", parser);
		panel.setGap(BasePanel.GAP1);

		//panel.addLine(colNamesLbl, null);

		panel.addComponent(1, 3, SwingUtils.TABLE_ROW_HEIGHT * 10,
		        BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        colNamesTbl);

		//panel.setGap(BasePanel.GAP1);
		panel.addLine(null, null, goBtn);
		panel.setGap(BasePanel.GAP1);
		panel.addMessage(msgTxt);
		panel.setHeight(BasePanel.HEIGHT_1P6);

		frame.addMainComponent(panel);
		frame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
	}



	private void init_300_Listners() {

		namesChk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean visible = namesChk.isSelected();

				colNamesTbl.setVisible(visible);
			}
		});

		goBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.stopCellEditing(colNamesTbl);
				editFile();

				frame.setVisible(false);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void editFile() {
		LayoutDetail layout = getLayout();
		AbstractLine<LayoutDetail> l;
		DataStoreStd<AbstractLine<LayoutDetail>> store = new DataStoreStd<AbstractLine<LayoutDetail>>(layout);
		LineProvider<LayoutDetail> p = LineIOProvider.getInstance()
										.getLineProvider(layout.getFileStructure());
		for (int i = 0; i < rowFld.value; i++) {
			l = p.getLine(layout);
			try {
				l.setField(0, colFld.value - 1, "");
			} catch (Exception e) {
			}
			store.add(l);
		}

		FileView file = new FileView(
				store,
				null,
				null,
				false);
		DisplayBuilderFactory.getInstance().newDisplay(DisplayBuilderFactory.ST_INITIAL_EDIT, "", null, file.getLayout(), file, 0);
		//DisplayBuilder.doOpen(file, 0, false);

	}

	private LayoutDetail getLayout() {
		LayoutDetail layout;
		int i;
		String s;
		String font = fontTxt.getText();
		String q   = Common.QUOTE_VALUES[quote.getSelectedIndex()];
		String sep = fieldSep.getSelectedEnglish();
		int structure = STRUCTURES[toInt(unicodeChk.isSelected())]
		                          [toInt(namesChk.isSelected())];
		FieldDetail[] flds = new FieldDetail[colFld.value];
	    RecordDetail[] recs = new RecordDetail[1];

	    for (i = 0; i < colFld.value; i++) {
		    s = columnNames.get(i);
            flds[i] = new FieldDetail(s, s, Type.ftChar, 0,
                        font, 0, "");
            flds[i].setPosOnly(i + 1);
	    }

        recs[0] = new RecordDetail("GeneratedCsvRecord", "", "", Constants.rtDelimited,
        		sep, q, font, flds,
        		((Integer)parser.getSelectedItem()).intValue(), 0);

        layout  =
            new LayoutDetail("GeneratedCsv", recs, "",
                Constants.rtDelimited,
                Common.SYSTEM_EOL_BYTES, "", font, null,
                structure
            );

		return layout;
	}

	private int toInt(boolean b) {
		int ret = 0;
		if (b) {
			ret = 1;
		}
		return ret;
	}
//  ===========================================================

	private class NumField extends FocusAdapter {
		public int value;
		public JTextField field = new JTextField();
		private final String name;
		private final AbstractTableModel mdl;

		public NumField(String fieldName, int initialValue, AbstractTableModel model) {
			name = fieldName;
			value = initialValue;
			field.addFocusListener(this);
			mdl = model;
			field.setText(Integer.toString(value));
		}

		/* (non-Javadoc)
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent evt) {
			try {
				int lastValue = value;
				value = Integer.parseInt(field.getText());

				if (mdl != null && lastValue != value && namesChk.isSelected()) {
					mdl.fireTableDataChanged();
				}
			} catch (Exception ex) {
				msgTxt.setText(LangConversion.convert("Invalid number in {0}, msg=", name) + ex.getMessage());
			}
		}
	}



	@SuppressWarnings("serial")
	private class ColumnNameMdl extends AbstractTableModel {

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 1;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return colFld.value;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String s = "";

			if (rowIndex < columnNames.size()) {
				s = columnNames.get(rowIndex);
			}

			return s;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return "Column Name";
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			String s  = "";
			if (value != null) {
				s = value.toString();
			}
			while (rowIndex >= columnNames.size()) {
				columnNames.add("");
			}
			columnNames.set(rowIndex, s);
		}


	}
}
