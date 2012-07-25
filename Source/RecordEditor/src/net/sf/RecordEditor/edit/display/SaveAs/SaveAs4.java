package net.sf.RecordEditor.edit.display.SaveAs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public class SaveAs4 extends ReFrame {

	//public final JButton saveBtn;
	public JFileChooser fileChooser = new JFileChooser();


	private CommonSaveAsFields commonSaveFields;
	private SaveAsPnlBase saveAsPnl;


	public final KeyAdapter keyListner = new KeyAdapter() {
	        /**
	         * @see java.awt.event.KeyAdapter#keyReleased
	         */
	        public final void keyReleased(KeyEvent event) {

	        	if (event.getKeyCode() == KeyEvent.VK_ENTER) {
	        		saveFile();
	         	} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
	         		SaveAs4.this.doDefaultCloseAction();
	         	}
	        }
	};

	public SaveAs4(final AbstractFileDisplay recordFrame,
    		final FileView<?> fileView) {
		super(fileView.getFileNameNoDirectory(), "Save As",
	              fileView.getBaseFile());

		BaseHelpPanel pnl = new BaseHelpPanel();
        String fname = fileView.getFileName();
        if ("".equals(fname)) {
        	fname = Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get();
        }
		commonSaveFields = new CommonSaveAsFields(recordFrame, fileView, null);
		saveAsPnl = new SaveAsPnlBasic.Data(commonSaveFields);


		fileChooser.setApproveButtonText("Save");

		if (fname != null) {
			fileChooser.setSelectedFile(new File(fname + "$"));
		}

		SwingUtils.addKeyListnerToContainer(pnl, keyListner);
		pnl.addReKeyListener(keyListner);

		this.addKeyListener(keyListner);

        pnl.addLine("What to Save", commonSaveFields.saveWhat);

		pnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP2,
		         BasePanel.FULL, BasePanel.FULL,
		         fileChooser);

    	pnl.setGap(BasePanel.GAP2);
    	pnl.addMessage(commonSaveFields.message);

		pnl.done();

		addMainComponent(new JScrollPane(pnl));


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fileChooser.addActionListener(new ActionListener() {

			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent evt) {

		        if (JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())) {
		        	saveFile();
		        } else if (JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand())) {
		        	SaveAs4.this.setVisible(false);
		        }
			}
        });

        this.setToMaximum(false);
        this.setVisible(true);
 	}



	public final void saveFile() {
		try {
			String outFile = fileChooser.getSelectedFile().getCanonicalPath();
			String selection = commonSaveFields.saveWhat.getSelectedItem().toString();

			saveAsPnl.save(selection, outFile);

			this.doDefaultCloseAction();
		} catch (Exception e) {
			String s = LangConversion.convert("Save Failed:") + " " + e.getClass().getName() + ": " + e.getMessage();
			commonSaveFields.message.setText(s);
			Common.logMsgRaw(s, e);
		}
	}


}
