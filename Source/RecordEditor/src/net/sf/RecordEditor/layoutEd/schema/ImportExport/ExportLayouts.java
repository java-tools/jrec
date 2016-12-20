package net.sf.RecordEditor.layoutEd.schema.ImportExport;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.External.base.CopybookWriter;
import net.sf.JRecord.External.base.RecordEditorXmlWriter;
import net.sf.RecordEditor.layoutEd.utils.LeMessages;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.common.SuppliedSchemas;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;


/**
 * Export File-Schema's as Xml files
 * 
 * @author Bruce Martin
 *
 */
public class ExportLayouts {

	
	private static final Set<String> SUPPLIED_SCHEMAS = new HashSet<String>(SuppliedSchemas.getLcSuppliedSchemas());
	
//	private static final String[] listOptions = {"", "Yes", "No"};
	private static final String[] COLUMN_HEADINGS = {"Extract", "Schema Name"};
    private static final int PROGRAM_DESCRIPTION_HEIGHT
    	= Math.min(
    			SwingUtils.NORMAL_FIELD_HEIGHT * 5,
    			Toolkit.getDefaultToolkit().getScreenSize().height * 3 / 5);

	private final ReFrame frame = new ReFrame("", "Export Layouts", null);
	private final BaseHelpPanel panel = new BaseHelpPanel();
	
	private JEditorPane programDescription = new JEditorPane("text/html", LeMessages.SCHEMA_EXPORT_TIP.get());
	
	private  JTextField  sfRecordName  = new JTextField();
	
	private FileSelectCombo outputDir = new FileSelectCombo(Parameters.SCHEMA_DIRS_LIST, 9, true, true, true);
	private BmKeyedComboBox sfSystem;
//	private JComboBox  sfList = new BmComboBox(listOptions);
	
	private JTable schemaTbl;
	
	private JButton selectAllBtn   = SwingUtils.newButton("Select All");
	private JButton deselectAllBtn = SwingUtils.newButton("Deselect All");
	private JButton go = SwingUtils.newButton("Export", Common.getRecordIcon(Common.ID_EXPORT_ICON));

	private JTextArea msg = new JTextArea();
	
	private CheckBoxTableRender checkBoxRendor = new CheckBoxTableRender();

	private TableDB systemTable = new TableDB();
	private DBComboModel<TableRec>  systemModel = new DBComboModel<TableRec>(systemTable, 0, 1 , true, true);

	private ExtendedRecordDB dbRecord = new ExtendedRecordDB();
	private Boolean[] selected;
 	private ArrayList<RecordRec> selectedRecords;
	
	private SchemaTblMdl schemaTblMdl = new SchemaTblMdl();
	
	private String  oldRecordName = "";
	private int oldSystem = -11;
//	private int oldList = -11;

	
	private SfListner listner = new SfListner();
	
	private ActionListener btnAction = new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
			if (e.getSource() == go) {
				writeSchemas();
			} else {
				Boolean val = e.getSource() == selectAllBtn;
				for (int i = 0; i < selected.length; i++) {
					selected[i] = val;
				}
				schemaTblMdl.fireTableDataChanged();
			}
		}
	};
	private Comparator<RecordRec> sortComparator = new Comparator<RecordRec>() {
	    public int compare(RecordRec r1, RecordRec r2) {
	        //RecordRec r1 = (RecordRec) o1;
	        //RecordRec r2 = (RecordRec) o2;
	        return r1.getRecordName()
	        		 .compareToIgnoreCase(r2.getRecordName());
	    }
	};
	
	/**
	 * Export file-schemas as Xml-Files
	 *  
	 * @param connectionIdx connection index
	 */
	public ExportLayouts(int connectionIdx) {
		
		init_100_setupScreen(connectionIdx);
		init_200_layoutScreen();
		init_300_showScreen();
	}

	private void init_100_setupScreen(int connectionIdx) {
	    ReConnection dbConnection = new ReConnection(connectionIdx);

	    
	    systemTable.setConnection(dbConnection);
	    systemTable.setParams(Common.TI_SYSTEMS);
	    
	    dbRecord.setConnection(dbConnection);
	    sfSystem = new BmKeyedComboBox(systemModel, false);
	    

		schemaTbl  = new JTable(schemaTblMdl);
		
		outputDir.setText(Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get());
	}

	private void init_200_layoutScreen() {
		JPanel btnPnl = new JPanel(new BorderLayout());
		btnPnl.add(selectAllBtn, BorderLayout.EAST);
		btnPnl.add(deselectAllBtn, BorderLayout.WEST);
		panel
			.addComponentRE(1, 3, PROGRAM_DESCRIPTION_HEIGHT, BasePanel.GAP1,
	                BasePanel.FULL, BasePanel.FULL,
	                new JScrollPane(programDescription))
	                
			.addLineRE(  "Output Directory", outputDir)
				.setGapRE(BasePanel.GAP1)
				
	    	.addLineRE("Schema Name Filter", sfRecordName)
	    	.addLineRE(            "System", sfSystem)
//	    	.addLine(              "List", sfList)
				.setGapRE(BasePanel.GAP1)

			.addLine1to3(btnPnl)
	    	.addComponentRE(1, 3, BasePanel.FILL, BasePanel.GAP1,
	  	         BasePanel.FULL, BasePanel.FULL,  schemaTbl)
    		.addComponentRE(1, 3, BasePanel.PREFERRED, BasePanel.GAP1,
	  	         BasePanel.RIGHT, BasePanel.FULL, go)
	  	         
	  	    .addComponentRE(1, 3, PROGRAM_DESCRIPTION_HEIGHT, BasePanel.GAP,
	                BasePanel.FULL, BasePanel.FULL,
	                new JScrollPane(msg))
	    	;
	}

	private void init_300_showScreen() {
		
		frame.addMainComponent(panel);
		redisplay();
		
		frame.setVisible(true);
		
		sfRecordName.addFocusListener(listner);
		sfSystem.addFocusListener(listner);
//		sfList.addFocusListener(listner);

		sfSystem.addActionListener(listner);
//		sfList.addActionListener(listner);
		go.addActionListener(btnAction);
		selectAllBtn.addActionListener(btnAction);
		deselectAllBtn.addActionListener(btnAction);
		
		Dimension preferredSize = frame.getPreferredSize();
		int desktopHeight = ReFrame.getDesktopHeight();
		int desktopWidth = ReFrame.getDesktopWidth();
		
		int width = (int) preferredSize.getWidth();
		int height = (int) preferredSize.getHeight();
		
		if (desktopHeight < height || desktopWidth < width) {
			frame.setSize(
					new Dimension(
							Math.min(desktopWidth, width),
							Math.min(desktopHeight, height)));
		}
		schemaTbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		schemaTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Common.calcColumnWidths(schemaTbl, 2);
		
		schemaTbl.getColumnModel().getColumn(0).setCellRenderer(checkBoxRendor);
		schemaTbl.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
	}
	
	
	private void redisplay() {
		int sysIdx = sfSystem.getSelectedIndex();
//		int listIdx = sfList.getSelectedIndex();
		
		if (sysIdx  != oldSystem
//		||  listIdx != oldList
		||  ! oldRecordName.equals(sfRecordName.getText())) {
//			int listId = sfList.getSelectedIndex();

			dbRecord.resetSearch();

			String s = sfRecordName.getText();
			if (! s.equals("")) {
				dbRecord.setSearchRecordName(AbsDB.opLike, s);
			}

			if (sfSystem.getSelectedIndex() >= 0) {
				dbRecord.setSearchSystem(
				        AbsDB.opEquals,
				        ((Integer) sfSystem.getSelectedItem()).intValue());
			}

//			if (listId > 0) {
//				dbRecord.setSearchListChar(AbsDB.opEquals, listId == 1 ? "Y" : "N");
//			}

			selectedRecords = dbRecord.fetchAll();
			Collections.sort(selectedRecords, sortComparator);
			
			setSelected();

	
			schemaTblMdl.fireTableDataChanged();
	
			oldSystem = sysIdx;
			oldRecordName = sfRecordName.getText();
//			oldList = listIdx;
		}
	}
	
	private void setSelected() {
		selected = new Boolean[selectedRecords.size()];
		Boolean val;
		
		for (int i = 0; i < selected.length; i++) {
			val = Boolean.TRUE;
			if ((! "Y".equalsIgnoreCase(selectedRecords.get(i).getValue().getListChar())
			||  SUPPLIED_SCHEMAS.contains(selectedRecords.get(i).getRecordName().toLowerCase()))) {
				val = Boolean.FALSE;
			}
			selected[i] = val;
		}
	}
	
	private void writeSchemas() {
		CopybookWriter writer = new RecordEditorXmlWriter();
		String dir = outputDir.getText();
		StringBuffer m = new StringBuffer();
		int exported = 0, failed = 0;
		
		if (dir != null && (dir.endsWith("/*") || dir.endsWith("\\*"))) {
			dir = dir.substring(0,dir.length() - 1);
		}
		if (dir != null && (! "".equals(dir)) && (! dir.endsWith("/")) && (! dir.endsWith("\\"))) {
			dir = dir + Common.FILE_SEPERATOR;
		}
		
		if (! Parameters.exists(dir)) {
			try {
				Common.createDirectory(new File(dir));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < selected.length; i++) {
			if (selected[i] == Boolean.TRUE) {	
				try {
					writer.writeCopyBook(dir, selectedRecords.get(i).getValue(), Common.getLogger());

					m.append("\n      ").append(selectedRecords.get(i).getRecordName());
					
					exported += 1;
				} catch (Exception e) {
					m.append("\n      ").append(LeMessages.SCHEMA_EXPORT_FAILURE.get())
					 .append(selectedRecords.get(i).getRecordName())
					 .append(": ").append(e);
					
					e.printStackTrace();
					
					failed += 1;
				}
			}
		}
		m.append(LeMessages.SCHEMA_EXPORT_MSG.get(exported, failed));
		
		msg.setText(m.toString());
	}
 
	
	private class SfListner implements FocusListener, ActionListener {

		@Override public void actionPerformed(ActionEvent e) {
			redisplay();
		}
		
		@Override public void focusLost(FocusEvent e) {
			redisplay();
		}

		@Override public void focusGained(FocusEvent e) {
		}
	}
	
	@SuppressWarnings("serial")
	public class SchemaTblMdl extends AbstractTableModel {

		@Override
		public int getRowCount() {
			if (selected == null) {
				return 0;
			}
			return selected.length;
		}

		@Override
		public int getColumnCount() {
			return COLUMN_HEADINGS.length;
		}

		@Override
		public String getColumnName(int column) {
			return COLUMN_HEADINGS[column];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return selected[rowIndex];
			}
			return selectedRecords.get(rowIndex).getRecordName();
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex == 0;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			Boolean val = Boolean.FALSE;
			if (aValue != null & (Boolean.TRUE.equals(aValue) || "true".equalsIgnoreCase(aValue.toString()))) {
				val = Boolean.TRUE;
			}
			selected[rowIndex] = val;
		}
		
	}
}
