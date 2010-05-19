package net.sf.RecordEditor.utils.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;

public class ExecuteSavedFile<details> extends ReFrame implements ActionListener {

	private static final int WIDTH_INCREASE = 150;

	private FileChooser file = new FileChooser();
	private	JButton runBtn = new JButton("Run");
	private	JButton runDialogBtn = new JButton("Run Dialog");
	private AbstractExecute<details> action;
	
	private JibxCall<details>  jibx = null;
	@SuppressWarnings("unchecked")
	private Class dtlsClass;

	/**
	 * Execute a saved File
	 * @param docName
	 * @param formName
	 * @param data
	 * @param dir
	 * @param executeAction
	 * @param detailsClass
	 */
	@SuppressWarnings("unchecked")
	public ExecuteSavedFile(final String docName, final String formName, final Object data, 
			String dir,
			AbstractExecute<details> executeAction, Class detailsClass) {
		super(docName, formName, data);
        ReMainFrame f = ReMainFrame.getMasterFrame();
		int width  = f.getDesktop().getWidth() - 1;
		BasePanel pnl = new BaseHelpPanel();
		
		action    = executeAction;
		dtlsClass = detailsClass;
		
		file.setText(dir);
		
		pnl.addComponent("File to Load", file, file.getChooseFileButton());
		pnl.setGap(BasePanel.GAP3);
		pnl.addComponent("", null, runDialogBtn);
		pnl.setGap(BasePanel.GAP1);
		pnl.addComponent("", null, runBtn);
		pnl.setGap(BasePanel.GAP2);
			
		getContentPane().add(pnl);
		pack();

        setBounds(getX(), getY(), 
        		Math.min(width, getWidth() + WIDTH_INCREASE),
        		getHeight());

        runBtn.addActionListener(this);
        runDialogBtn.addActionListener(this);
        
        setVisible(true);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		details saveDetails;
		
		
		try {
			if (jibx == null) {
				jibx = new JibxCall<details>(dtlsClass);
			}
			
			//System.out.println("jibx filename: " + file.getText());
			saveDetails = jibx.marshal(file.getText());
			
			if (e.getSource() == runBtn) {
				action.execute(saveDetails);
			} else {
				action.executeDialog(saveDetails);
			}
			this.setClosed(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			Common.logMsg("Execute Error ", ex);
		}
	}
}
