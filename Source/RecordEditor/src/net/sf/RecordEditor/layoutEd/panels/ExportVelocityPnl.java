package net.sf.RecordEditor.layoutEd.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.RecordEditor.layoutEd.utils.LayoutVelocity;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;


@SuppressWarnings("serial")
public class ExportVelocityPnl extends ReFrame {


	private BaseHelpPanel pnl = new BaseHelpPanel();

    private FileSelectCombo fileNameFC = new FileSelectCombo(Parameters.LIST_SAVE_FILES, 16, false, false, true);
    	//= new TreeComboFileSelect(false, false, true, INPUT_FILES);
    		//new FileChooser(false);
    private FileSelectCombo templateFC = new FileSelectCombo(Parameters.VELOCITY_SCHEMA_SKELS_LIST, 9, false, false);
    		//new FileChooser(false);

    private final JCheckBox keepOpenChk = new JCheckBox();
    private JButton saveFileBtn = SwingUtils.newButton(
    				"Save File",
    				Common.getRecordIcon(Common.ID_SAVE_ICON));
    public final JTextArea message = new JTextArea();

    private final int dbIdx;
    private final RecordRec layoutRecord;

    private KeyAdapter listner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	switch (event.getKeyCode()) {
        	case KeyEvent.VK_ENTER:		saveFile();									break;
        	case KeyEvent.VK_ESCAPE:	ExportVelocityPnl.this.doDefaultCloseAction();		break;
        	}
        }
    };

	public ExportVelocityPnl(int dbIdx, String template, RecordRec layoutRec) {
		super(layoutRec.getRecordName(), "Via Velocity", "Export", null);

		this.dbIdx = dbIdx;
		this.layoutRecord = layoutRec;

		fileNameFC.setText(Common.OPTIONS.layoutExportDirectory.getNoStar() + layoutRecord.getRecordName() + ".html");
		if (template != null && ! "".equals(template)) {
			templateFC.setText(template);
		}


		init_200_layoutScreen();

		init_300_ShowScreen();
    }


    private void init_200_layoutScreen() {

	//	pnl.setHelpURL(Common.formatHelpURL(Common.HELP_SAVE_AS));
    	pnl.addReKeyListener(listner);

    	pnl.setGapRE(BaseHelpPanel.GAP2);
        pnl.addLineRE("Export File Name", fileNameFC)
           .setGapRE(BasePanel.GAP2);
        pnl.addLineRE("Velocity Template", templateFC)
           .setGapRE(BaseHelpPanel.GAP2);

        pnl.addLineRE("Keep screen open", keepOpenChk, saveFileBtn);
        pnl.setGapRE(BasePanel.GAP3);
        pnl.addMessage(new JScrollPane(message));
        pnl.setHeightRE(BasePanel.GAP5 * 2);
    }


    private void init_300_ShowScreen() {

    	super.addMainComponent(pnl);
    	super.setVisible(true);

    	saveFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
	}


    private void saveFile() {
    	try {
			(new LayoutVelocity(dbIdx)).run(templateFC.getText(), layoutRecord, fileNameFC.getText());

			if (! keepOpenChk.isSelected()) {
				super.doDefaultCloseAction();
			}
		} catch (Exception e) {
			String s = LangConversion.convert("Velocity Export Failed:") + " " + e.getClass().getName() + " " + e.getMessage();

			message.setText(s);
			Common.logMsgRaw(s, e);
			e.printStackTrace();
		}
    }
}
