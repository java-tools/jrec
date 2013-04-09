/**
 *
 */
package net.sf.RecordEditor.copy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JTextArea;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class RunSavedCopy extends ReFrame {

    private static final int WIDTH_INCREASE  = 175;
    private static final int HEIGHT_INCREASE = 7;

	private FileChooser xmlFileName = new FileChooser();
	private BaseHelpPanel pnl = new BaseHelpPanel();

	private JButton runCompareDialogBtn = SwingUtils.newButton("Run Copy Dialog");
	private JButton runCompareBtn       = SwingUtils.newButton("Run Copy");
//	private JButton runHtmlCompareBtn   = SwingUtils.newButton("Run Html Copy");

	//private JTextArea message        = new JTextArea();
	private int dbIdx;;


	private String rFiles;
//	private AbstractLayoutSelection layoutReader, layoutReader2;

	@SuppressWarnings("unchecked")
	private AbstractLayoutSelectCreator<AbstractLayoutSelection> layoutCreator;

	private ActionListener listner = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == runCompareDialogBtn) {
				runCopyDialog();
			} else {
				runCopy();
			}
		}

	};
	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public RunSavedCopy(AbstractLayoutSelectCreator creator, int databaseIdx, String recentFiles) {
		super("", "Run Saved Copy File:", "", null);

		rFiles = recentFiles;

        ReMainFrame frame = ReMainFrame.getMasterFrame();
		int height = frame.getDesktop().getHeight() - 1;
		int width  = frame.getDesktop().getWidth() - 1;

		String fname =  Parameters.getFileName(Parameters.COPY_SAVE_FILE);

		layoutCreator = creator;
		dbIdx = databaseIdx;

		xmlFileName.setDefaultDirectory(Parameters.getFileName(Parameters.COMPARE_SAVE_DIRECTORY));

		if (fname == null || "".equals(fname)) {
			fname = Parameters.getFileName(Parameters.COPY_SAVE_DIRECTORY);
		}
		xmlFileName.setText(fname);

		pnl.setGap(BasePanel.GAP3);
		pnl.addLine("New File", xmlFileName, xmlFileName.getChooseFileButton());
		pnl.setGap(BasePanel.GAP5);

		pnl.addLine("", null, runCompareDialogBtn);
		pnl.setGap(BasePanel.GAP1);
		pnl.addLine("", null, runCompareBtn);
		pnl.setGap(BasePanel.GAP5);

		pnl.addMessage(new JTextArea());

		super.getContentPane().add(pnl);

		super.pack();
        this.setBounds(getY(), getX(),
        		Math.min(width, getWidth() + WIDTH_INCREASE),
        		Math.min(height, getHeight()+HEIGHT_INCREASE));

		super.setVisible(true);

		runCompareDialogBtn.addActionListener(listner);
		runCompareBtn.addActionListener(listner);

	}



	/**
	 * @see net.sf.RecordEditor.utils.screenManager.ReFrame#windowClosing()
	 */
	@Override
	public final void windowClosing() {
		String s = xmlFileName.getText();
		if (! "".equals(s)) {
			try {
				Properties p = Parameters.getProperties();

				if (! s.equals(p.getProperty(Parameters.COPY_SAVE_FILE))) {
					p.setProperty(Parameters.COPY_SAVE_FILE, s);

		            p.store(
		                    new FileOutputStream(Parameters.getPropertyFileName()),
		                    "RecordEditor");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.windowClosing();
	}



	/**
	 * Run the Copy Dialogue from saved XML Copy Definition
	 */
	private void runCopyDialog() {
		CopyDefinition def = readXml();

		runCopyDialog(def);
	}

	/**
	 * Run the Copy Dialogue with supplied Definition
	 * @param def Copy definition
	 */
	private void runCopyDialog(CopyDefinition def) {
		if (def != null) {
			if (CopyDefinition.STANDARD_COPY.equals(def.type)) {
				new CopyTwoLayouts(createLayout(), createLayout(), def, rFiles);
			} else if (CopyDefinition.COBOL_COPY.equals(def.type)) {
				new CobolCopy(def);
			} else if (CopyDefinition.DELIM_COPY.equals(def.type)) {
				new Copy2Delim(createLayout(), def);
			} else if (CopyDefinition.VELOCITY_COPY.equals(def.type)) {
				new Copy2Delim(createLayout(), def);
			} else if (CopyDefinition.XML_COPY.equals(def.type)) {
				new Copy2Xml(createLayout(), def, rFiles);
			}
		}
	}


	/**
	 * Run the Copy from saved XML Copy Definition
	 */
	private void runCopy() {
		CopyDefinition def = readXml();

		if (def != null) {
			if (! "Yes".equalsIgnoreCase(def.complete)) {
				runCopyDialog(def);
			} else {
				try {
						DoCopy.copy(createLayout(), createLayout(), def);

				} catch (Exception e) {
					String s = "Error Running Copy";
					e.printStackTrace();
					pnl.setMessageTxt(s);
					Common.logMsg(s, e);
					return;
				}
			}
		}
	}

	private AbstractLayoutSelection createLayout() {
		AbstractLayoutSelection ret = layoutCreator.create();
		ret.setDatabaseIdx(dbIdx);
		return ret;
	}



	private CopyDefinition readXml() {
		CopyDefinition def = null;
		JibxCall<CopyDefinition> jibx = new JibxCall<CopyDefinition>(CopyDefinition.class);
		String filename = xmlFileName.getText();

		if (filename == null || "".equals(filename)) {
			pnl.setMessageTxt("You must Enter a filename");
		} else {
			try {
				def = jibx.marshal(filename);
				def.saveFile = filename;
			} catch (Exception e) {
				String s = "Error Loading Record Layout";
				e.printStackTrace();
				pnl.setMessageTxt(s);
				Common.logMsg(s, e);
			}
		}

		return def;
	}
}
