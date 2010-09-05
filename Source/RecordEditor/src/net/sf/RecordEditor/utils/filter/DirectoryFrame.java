package net.sf.RecordEditor.utils.filter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;

@SuppressWarnings("serial")
public class DirectoryFrame extends ReFrame {
	private static final int WIDTH_INCREASE = 150;
	
	public final JButton saveBtn;
	public final FileChooser file = new FileChooser();
	public final JTextArea msg;
	
	public DirectoryFrame(String name, String dir, boolean displayMsg, boolean directorySelection, boolean saveAction) {
		super("", name, null);
        ReMainFrame f = ReMainFrame.getMasterFrame();
        int actionIdx = 0;
        String[] dirAction = {"Save Directory", "Load From Directory"};
        String[] fileAction = {"Save File", "Load File"};

		int width  = f.getDesktop().getWidth() - 1;
		BasePanel pnl = new BaseHelpPanel();
		
		if (saveAction) {
			saveBtn= new JButton("Save", Common.getRecordIcon(Common.ID_SAVE_ICON));
		} else {
			saveBtn= new JButton("Load");		
			actionIdx = 1;
		}
		
		file.setText(dir);
		
		if (directorySelection) {
			pnl.addComponent(dirAction[actionIdx], file, file.getChooseFileButton());
			file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else {
			pnl.addComponent(fileAction[actionIdx], file, file.getChooseFileButton());			
		}
		pnl.setGap(BasePanel.GAP1);
		pnl.addComponent("", null, saveBtn);
       
        if (displayMsg) {
        	msg = new JTextArea();
        	pnl.setGap(BasePanel.GAP1);
        	pnl.addMessage(msg);
        } else {
        	msg = null;
        }
		
		pnl.done();
		
		addMainComponent(pnl);

        setBounds(getX(), getY(), 
        		Math.min(width, getWidth() + WIDTH_INCREASE),
        		getHeight());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 	}
}
