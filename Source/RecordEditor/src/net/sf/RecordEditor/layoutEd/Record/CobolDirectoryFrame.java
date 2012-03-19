package net.sf.RecordEditor.layoutEd.Record;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.re.openFile.ComputerOptionCombo;
import net.sf.RecordEditor.re.openFile.SplitCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public class CobolDirectoryFrame extends ReFrame {
	
	//public final JButton saveBtn;
	private JFileChooser fileChooser = new JFileChooser();
	
	public final SplitCombo  splitOptions  = new SplitCombo();
	public BmKeyedComboBox fileStructure;
	public final JTextField  fontName      = new JTextField();
	public final ComputerOptionCombo
	                    binaryOptions = new ComputerOptionCombo();
    public BmKeyedComboBox      system;

	public JTextArea msg;
	
	private ActionListener actionListner = null;
	
	public final KeyAdapter keyListner = new KeyAdapter() {
	        /**
	         * @see java.awt.event.KeyAdapter#keyReleased
	         */
	        public final void keyReleased(KeyEvent event) {
	        	
	        	if (event.getKeyCode() == KeyEvent.VK_ENTER 
	        	&&  actionListner != null) {
	        		actionListner.actionPerformed(new ActionEvent(CobolDirectoryFrame.this, 0, "Open"));
	         	}
	        }
	};
	
	public CobolDirectoryFrame(String name, String dir, boolean displayMsg, boolean directorySelection, int dbIdx) {
		super("", name, null);
 
		init(dbIdx);
        //String[] dirAction = {"Save to Directory", "Load From Directory"};
        //String[] fileAction = {"Save", "Load"};
        //String[] btnTxt = fileAction;


		BasePanel pnl = new BaseHelpPanel();
		
		if (directorySelection) {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		

//			saveBtn= new JButton(btnTxt[1]);	
		fileChooser.setApproveButtonText("Load");

		if (dir != null) {
			fileChooser.setSelectedFile(new File(dir));
		}
		//fileChooser.setControlButtonsAreShown(false);
		
		pnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP1,
		         BasePanel.FULL, BasePanel.FULL,
		         fileChooser);

		SwingUtils.addKeyListnerToContainer(pnl, keyListner);
		//pnl.setGap(BasePanel.GAP1);
		//pnl.addLine("", null, saveBtn);
       
		pnl.addLine("Split Copybook", splitOptions);
		pnl.addLine("Font Name", fontName);
		pnl.addLine("Binary Format", binaryOptions);

		pnl.addLine("File Structure", fileStructure);
		pnl.addLine("System", system);

        if (displayMsg) {
        	msg = new JTextArea();
        	pnl.setGap(BasePanel.GAP1);
        	pnl.addMessage(msg);

        	SwingUtils.addKeyListnerToContainer(msg, keyListner);
        } else {
        	msg = null;
        }
		
		pnl.done();
		
		addMainComponent(new JScrollPane(pnl));

//        setBounds(getY(), getX(), 
//        		Math.min(width, getWidth() + WIDTH_INCREASE),
//        		getHeight());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        fileChooser.addActionListener(new ActionListener() {

			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent evt) {

		        if (actionListner != null
		        && JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())) {
		        	actionListner.actionPerformed(evt);
		        } else if (JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand())) {
		        	CobolDirectoryFrame.this.setVisible(false);
		        }
			}
        });
 	}
	
	
	private void init(int dbIdx) {
	    splitOptions.setSelectedIndex(0);
	    
	    TableDB         systemTable = new TableDB();
	    DBComboModel<TableRec>    systemModel
			= new DBComboModel<TableRec>(systemTable, 0, 1, true, false);
	    
	    BmKeyedComboModel structureModel = new BmKeyedComboModel(new ManagerRowList(
				LineIOProvider.getInstance(), false));
	    
		systemTable.setConnection(new ReConnection(dbIdx));

		systemTable.setParams(Common.TI_SYSTEMS);

		fileStructure  = new BmKeyedComboBox(structureModel, false);
		system         = new BmKeyedComboBox(systemModel, false);


	}
	
	public String getFileName() {
		return fileChooser.getSelectedFile().getPath();
	}
	
	
	public File getFile() {
		return fileChooser.getSelectedFile();
	}

	/**
	 * @param actionListner the actionListner to set
	 */
	public void setActionListner(ActionListener actionListner) {
		this.actionListner = actionListner;
	}
}
