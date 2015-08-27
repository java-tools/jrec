package net.sf.RecordEditor.utils.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

@SuppressWarnings("serial")
public class DirectoryFrame extends ReFrame {

	private static final String[] FILE_ACTIONS = LangConversion.convertArray(LangConversion.ST_BUTTON, "File Load/Save",
							new String[]{"Save", "Load"});

	private static final String[] DIR_ACTION = LangConversion.convertArray(LangConversion.ST_BUTTON, "Directory Load/Save",
						new String[] {"Save to Directory", "Load From Directory"});

	//public final JButton saveBtn;
	private JFileChooser fileChooser = new JFileChooser();
	//public final JTextArea msgTxt;

	public final BasePanel panel = new BaseHelpPanel();


	private ActionListener actionListner = null;

	public final KeyAdapter keyListner = new KeyAdapter() {
	        /**
	         * @see java.awt.event.KeyAdapter#keyReleased
	         */
	        public final void keyReleased(KeyEvent event) {

	        	if (event.getKeyCode() == KeyEvent.VK_ENTER
	        	&&  actionListner != null) {
	        		SwingUtils.clickOpenBtn(fileChooser, false);
	        		actionListner.actionPerformed(new ActionEvent(DirectoryFrame.this, 0, "Open"));
	         	} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
	         		DirectoryFrame.this.doDefaultCloseAction();
	         	}
	        }
	};

	public DirectoryFrame(String name, String dir, boolean displayMsg, boolean directorySelection, boolean saveAction) {
		super("", name, null);

        String[] dirAction = DIR_ACTION;
        String[] fileAction = FILE_ACTIONS;
        String[] btnTxt = fileAction;

		if (directorySelection) {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			btnTxt = dirAction;
		}

		if (saveAction) {
//			saveBtn= new JButton(btnTxt[0], Common.getRecordIcon(Common.ID_SAVE_ICON));
			fileChooser.setApproveButtonText(btnTxt[0]);
		} else {
//			saveBtn= new JButton(btnTxt[1]);
			fileChooser.setApproveButtonText(btnTxt[1]);
		}

		if (dir != null) {
			fileChooser.setSelectedFile(new File(dir));
		}
		//fileChooser.setControlButtonsAreShown(false);

		panel.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP1,
		         BasePanel.FULL, BasePanel.FULL,
		         fileChooser);

		SwingUtils.addKeyListnerToContainer(panel, keyListner);
		//pnl.setGap(BasePanel.GAP1);
		//pnl.addLine("", null, saveBtn);

        if (displayMsg) {
        	JTextArea msgTxt = new JTextArea();
        	panel.setGapRE(BasePanel.GAP1);
        	panel.addMessage(msgTxt);

        	SwingUtils.addKeyListnerToContainer(msgTxt, keyListner);

        }

		panel.done();

		addMainComponent(panel);

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
		        	DirectoryFrame.this.setVisible(false);
		        }
			}
        });
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
