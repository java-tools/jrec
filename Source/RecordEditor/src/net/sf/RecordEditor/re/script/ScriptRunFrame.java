/**
 *
 */
package net.sf.RecordEditor.re.script;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ScriptRunFrame extends ReFrame {

	private JComboBox languageCombo;
	private FileChooser templateFC = new FileChooser();
	private JButton runBtn = SwingUtils.newButton("Run !!!", Common.getRecordIcon(Common.ID_SCRIPT_ICON));
	private JTextArea msg = new JTextArea();

	private ReFrame activeFrame;

    private KeyAdapter listner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	switch (event.getKeyCode()) {
        	case KeyEvent.VK_ENTER:		run();    					        		break;
        	case KeyEvent.VK_ESCAPE:	ScriptRunFrame.this.doDefaultCloseAction();	break;
        	}
        }
    };
	/**
	 * Display a frame where users can run
	 */
	public ScriptRunFrame() {
		super("", "Run Script Screen", null);
		activeFrame = ReFrame.getActiveFrame();
		if (activeFrame == null || ! (activeFrame.getDocument() instanceof FileView)) {
			msg.setText(" >>> Warning  >>>  can not retrive File details, this could affect the script !!!");
		}

		init_200_layout();
		init_300_listner();
	}

	private void init_200_layout() {
		BaseHelpPanel pnl = new BaseHelpPanel();
		List<String>  langs = net.sf.RecordEditor.re.script.ScriptMgr.getLanguages();

		languageCombo = new JComboBox(
				langs.toArray(new String[langs.size()])
		);
		templateFC.setText(Common.OPTIONS.DEFAULT_SCRIPT_DIRECTORY.get()
				+ " ================================== ");

		pnl.addLine("Script Language", languageCombo)
		   .setGap(BaseHelpPanel.GAP1);
		pnl.addLine("Script", templateFC, templateFC.getChooseFileButton())
		   .setGap(BaseHelpPanel.GAP1);
		pnl.addLine("", null, runBtn)
		   .setGap(BaseHelpPanel.GAP2);

		pnl.addMessage(new JScrollPane(msg));
		pnl.setHeight(BaseHelpPanel.GAP5 * 2);

		pnl.addReKeyListener(listner);

		this.addMainComponent(pnl);
		templateFC.setText(Common.OPTIONS.DEFAULT_SCRIPT_DIRECTORY.get());
		this.setVisible(true);
	}


	private void init_300_listner() {
		runBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				run();
			}
		});

		templateFC.addFcFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				checkLanguage();
			}
		});
	}

	private void checkLanguage() {
		try {
        	String ext = Parameters.getExtensionOnly(templateFC.getText());

        	String lang = (new net.sf.RecordEditor.re.script.ScriptMgr()).getEngineNameByExtension(ext);

        	if (! "".equals(lang)) {
        		languageCombo.setSelectedItem(lang);
        	}

		} catch (Exception e2) {
			// do nothing
		}
	}
	private void run() {

		try {
			ScriptData data =  ScriptData.getScriptData( activeFrame);
			String s = "";
			(new net.sf.RecordEditor.re.script.ScriptMgr())
					.runScript(templateFC.getText(), data);

			if (data != null && ! "".equals(data.outputFile)) {
				s = "\n " +  LangConversion.convert("Output File:") + " " + data.outputFile;
			}
			msg.setText(LangConversion.convert("Script {0} run  !!!", templateFC.getText()) + s);
		} catch (Exception e) {
			String s = LangConversion.convert("Error:") + " " + e.getClass().getName() + " " + e.getMessage();
			msg.setText(s);
			Common.logMsgRaw(s, e);
		}
	}
}
