package net.sf.RecordEditor.re.file.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

//import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public class ExecuteSavedFile<details> extends ReFrame implements ActionListener {

	//private static final int WIDTH_INCREASE = SwingUtils.STANDARD_FONT_WIDTH * 16;

	private JFileChooser fileChooser = new JFileChooser();
	//private FileChooser file = new FileChooser();
	private	JButton runBtn = new JButton("Run");
	private	JButton runDialogBtn = new JButton("Run Dialog");
	private AbstractExecute<details> action;


	@SuppressWarnings("rawtypes")
	private Class dtlsClass;
	
	
	public final KeyAdapter keyListner = new KeyAdapter() {
	        /**
	         * @see java.awt.event.KeyAdapter#keyReleased
	         */
	        public final void keyReleased(KeyEvent event) {
	        	
	        	if (event.getKeyCode() == KeyEvent.VK_ENTER) {
	        		fileChooser.approveSelection();
	        		    		
	        		execAction(true);					
	         	} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
	         		ExecuteSavedFile.this.doDefaultCloseAction();
	         	}
	        }
	};

	/**
	 * Execute a saved File
	 * @param docName
	 * @param formName
	 * @param data
	 * @param dir
	 * @param executeAction
	 * @param detailsClass
	 */
	public ExecuteSavedFile(final String docName, final String formName, final Object data, 
			String dir,
			AbstractExecute<details> executeAction, @SuppressWarnings("rawtypes") Class detailsClass) {
		super(docName, formName, data);
		BasePanel pnl = new BaseHelpPanel();
		
		action    = executeAction;
		dtlsClass = detailsClass;
		
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		if (dir != null) {
			if (dir.endsWith("/") || dir.endsWith("\\")) {
				dir += "*";
			}
			fileChooser.setSelectedFile(new File(dir));
			
		}
		fileChooser.setControlButtonsAreShown(false);

		pnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP1,
		         BasePanel.FULL, BasePanel.FULL,
		         fileChooser);
		
		SwingUtils.addKeyListnerToContainer(pnl, keyListner);
		
		pnl.setGap(BasePanel.GAP3);
		pnl.addLine("", null, runDialogBtn);
		pnl.setGap(BasePanel.GAP1);
		pnl.addLine("", null, runBtn);
		pnl.setGap(BasePanel.GAP2);
			
		getContentPane().add(pnl);
		pack();

        runBtn.addActionListener(this);
        runDialogBtn.addActionListener(this);
        
        setVisible(true);
        
        super.setToMaximum(false);
        
        fileChooser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Action Listner ... " + e.getActionCommand());
			}
		});
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//fileChooser.approveSelection(); 
		//fileChooser.processEvent(evt);
		//fileChooser.getActionMap().get(FilePane.ACTION_APPROVE_SELECTION).actionPerformed(null);

		try {
			fileChooser.approveSelection();
			
			fileChooser.getActionForKeyStroke(
							KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0))
					   .actionPerformed(null);
		} catch (Exception ex) {
		//	ex.printStackTrace();
		}
		//System.out.println("$$$$$$ " + fileChooser.getSelectedFile().getPath());
		execAction(e.getSource() == runBtn);
	}
	
	public void execAction(boolean run) {
		details saveDetails;
				
		try {

			net.sf.RecordEditor.jibx.JibxCall<details> jibx
				= new net.sf.RecordEditor.jibx.JibxCall<details>(dtlsClass);

			System.out.println(fileChooser.getSelectedFile().getPath());
			saveDetails = jibx.marshal(fileChooser.getSelectedFile().getPath());
			
			if (run) {
				action.execute(saveDetails);
			} else {
				action.executeDialog(saveDetails);
			}
			this.setClosed(true);
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
			Common.logMsg("JibxCall could not be loaded ", null);
		} catch (Exception ex) {
			ex.printStackTrace();
			Common.logMsg("Execute Error ", ex);
		}
	}
}
