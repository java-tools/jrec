package net.sf.RecordEditor.re.openFile;

import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;

public class LayoutSelectionCobol extends AbstractLayoutSelection {

	private static final String SEPERATOR = "~";

	private static final int MODE_NORMAL = 0;
//	private static final int MODE_1ST_COBOL = 1;
//	private static final int MODE_2ND_COBOL = 2;
//
//
//    private static final int TAB_CSV_IDX = 8;
//
//    private FileSelectCombo	 copybookDir;
//    private ZOld_TreeComboFileSelect2 copybookFile;
//
//	private JTextArea message = null;
//
//	private BasePanel parentPnl;
//
//	private String lastFileName = "";
//	private String lastLayoutDetails = "";
//	private File lastCopybookDir = null;
//	private AbstractLayoutDetails lastLayout;

	//private boolean isCob = false;

	private int mode = MODE_NORMAL;

	private FocusListener copybookFocusListner;

	public LayoutSelectionCobol() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addLayoutSelection(BasePanel pnl, TreeComboFileSelect file,
			JPanel goPanel, JButton layoutCreate1, JButton layoutCreate2) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getDataBaseNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDatabaseIdx(int idx) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDatabaseIdx() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDatabaseName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setLayoutName(String layoutName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getLayoutName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractLayoutDetails getRecordLayout(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractLayoutDetails getRecordLayout(String name, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMessage(JTextArea message) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isFileBasedLayout() {
		// TODO Auto-generated method stub
		return false;
	}

}
