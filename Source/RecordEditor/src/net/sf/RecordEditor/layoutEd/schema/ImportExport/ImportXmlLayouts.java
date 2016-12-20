package net.sf.RecordEditor.layoutEd.schema.ImportExport;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.layoutEd.utils.LeMessages;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.common.SuppliedSchemas;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;


/**
 * 
 * @author Bruce0
 *
 */
public class ImportXmlLayouts {

	private final static Set<String> SUPPLIED_SCHEMAS = getSuppliedSchemas();

	private static final String[] COLUMN_HEADINGS = {"Import", "Schema File"};
    private static final int PROGRAM_DESCRIPTION_HEIGHT
    	= Math.min(
    			SwingUtils.NORMAL_FIELD_HEIGHT * 5,
    			Toolkit.getDefaultToolkit().getScreenSize().height * 3 / 5);
    
	private static FileFilter STANDARD_FILTER = new FileFilter() {
		@Override public boolean accept(File pathname) {
			return ! pathname.isDirectory();
		}
	};

	private final ReFrame frame = new ReFrame("", "Export Layouts", null);
	private final BaseHelpPanel panel = new BaseHelpPanel();
	
	private JEditorPane programDescription = new JEditorPane("text/html", LeMessages.SCHEMA_IMPORT_TIP.get());
	
	private  JTextField  sfFileNameFilter  = new JTextField();
	
	private FileSelectCombo sfInputDir = new FileSelectCombo(Parameters.SCHEMA_DIRS_LIST, 12, true, true);

	private JTable schemaTbl;
	
	private JButton selectAllBtn   = SwingUtils.newButton("Select All");
	private JButton deselectAllBtn = SwingUtils.newButton("Deselect All");
	private JButton go = SwingUtils.newButton("Import");

	private JTextArea msg = new JTextArea();
	
	private CheckBoxTableRender checkBoxRendor = new CheckBoxTableRender();

	private File[] listFiles;
	private Boolean[] selected;

	private int connectionIdx;
	private String oldDir = "",
				   oldFileFilter = "";
	private SchemaTblMdl schemaTblMdl = new SchemaTblMdl();

	private SfListner listner = new SfListner();
	
 

	private ActionListener btnAction = new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
			if (e.getSource() == go) {
				doImport();
			} else {
				Boolean val = e.getSource() == selectAllBtn;
				for (int i = 0; i < selected.length; i++) {
					selected[i] = val;
				}
				schemaTblMdl.fireTableDataChanged();
			}
		}
	};

	public ImportXmlLayouts(int connectionIdx) {

		this.connectionIdx = connectionIdx;
		
		init_100_setupScreen();
		init_200_layoutScreen();
		init_300_showScreen();
	}

	private void init_100_setupScreen() {

		schemaTbl  = new JTable(schemaTblMdl);
		
		sfInputDir.setText(Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get());
		sfFileNameFilter.setText(".*\\.xml");
	}
	

	private void init_200_layoutScreen() {
		JPanel btnPnl = new JPanel(new BorderLayout());
		btnPnl.add(selectAllBtn, BorderLayout.EAST);
		btnPnl.add(deselectAllBtn, BorderLayout.WEST);
		panel
			.addComponentRE(1, 3, PROGRAM_DESCRIPTION_HEIGHT, BasePanel.GAP,
	                BasePanel.FULL, BasePanel.FULL,
	                new JScrollPane(programDescription))
	                
			.addLineRE(  "Input Directory", sfInputDir)
				.setGapRE(BasePanel.GAP1)
				
	    	.addLineRE("Schema Name Filter", sfFileNameFilter)
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

		sfInputDir.addFcFocusListener(listner);
		sfFileNameFilter.addFocusListener(listner);
		sfFileNameFilter.addActionListener(listner);

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


	private static Set<String> getSuppliedSchemas() {
		HashSet<String> ret = new HashSet<String>();
		List<String> schemas = SuppliedSchemas.getLcSuppliedSchemas();

		for (int i = 0; i < schemas.size(); i++) {
			ret.add(
					ExternalConversion.fixFileName( schemas.get(i) ));
		}
		
		return ret;
	}
	
	private void redisplay() {
		
		String inDir = sfInputDir.getText();
		String fileFilter = sfFileNameFilter.getText();
		if (! ("".equals(inDir) || (oldDir.equals(inDir) && oldFileFilter.equals(fileFilter)))) {
			FileFilter filter = STANDARD_FILTER;
			
			if (! "".equals(fileFilter)) {
				filter = new RegexFilter(fileFilter) ;
			}
			listFiles = (new File(Parameters.dropStar(inDir))).listFiles(filter);
			
			setSelected();

			schemaTblMdl.fireTableDataChanged();
			
			oldDir = inDir;
			oldFileFilter = fileFilter;
		}
	}
	
	private void setSelected() {
		if (listFiles == null) {
			selected = new Boolean[0];
			return;
		}
		selected = new Boolean[listFiles.length];
		Boolean val;
		
		for (int i = 0; i < selected.length; i++) {
			val = Boolean.TRUE;
			if (SUPPLIED_SCHEMAS.contains(toLayoutName(listFiles[i]))
			|| (! listFiles[i].getName().toLowerCase().endsWith(".xml"))) {
				val = Boolean.FALSE;
			}
			selected[i] = val;
		}
	}
	
	private String toLayoutName(File f) {
		String s = f.getName().toLowerCase();
		int pos = s.lastIndexOf('.');
		if (pos > 0) {
			s = s.substring(0, pos);
		}

		return ExternalConversion.fixFileName(s);
	}
	
	private void doImport() {
		ExtendedRecordDB dbTo = new ExtendedRecordDB();
		dbTo.setConnection(new ReConnection(connectionIdx));
		boolean free = dbTo.isSetDoFree(false);
		StringBuffer m = new StringBuffer();
		int imported = 0, failed = 0;

		try {
	        ExternalRecord rec;
			CopybookLoader loader = CopybookLoaderFactoryDB.getInstance()
				.getLoader(CopybookLoaderFactoryDB.RECORD_EDITOR_XML_LOADER);
			
			File xmlFile;
			for (int i = 0; i < listFiles.length; i++) {
				if (selected[i]) {
					xmlFile = listFiles[i];
					try {
						rec = loader.loadCopyBook(xmlFile.getPath(),
							CopybookLoader.SPLIT_NONE,
							connectionIdx,
			                "",
			                0,
			                0,
			                Common.getLogger());
						
						dbTo.updateSystemCode(rec);
						dbTo.checkAndUpdate(rec);
	
						Common.logMsg(AbsSSLogger.SHOW, "Loading", xmlFile.getPath(), null);
						imported += 1;
					} catch (Exception e) {
						Common.logMsgRaw(e.toString(), e);
						failed += 1;
					}
				}
			}
			m.append(LeMessages.SCHEMA_IMPORT_MSG.get(imported, failed));
			
			msg.setText(m.toString());
		} catch (Exception e) {
			Common.logMsgRaw(e.toString(), e);
		}	finally {
			dbTo.setDoFree(free);
		}
		
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
	
	private class RegexFilter implements FileFilter {

		Pattern pattern;
		RegexFilter(String patternStr) {
			pattern =  Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		}
		@Override public boolean accept(File pathname) {
			Matcher matcher = pattern.matcher(pathname.getName());
			return matcher.find();
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
			return listFiles[rowIndex].getName();
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
