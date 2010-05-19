/**
 * 
 */
package net.sf.RecordEditor.diff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JTextArea;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelectCreator;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;

/**
 * @author Bruce Martin
 *
 */
public class RunSavedCompare extends ReFrame {

    private static final int WIDTH_INCREASE  = 175;
    private static final int HEIGHT_INCREASE = 7;

	private FileChooser xmlFileName = new FileChooser();
	private BaseHelpPanel pnl = new BaseHelpPanel();
	
	private JButton runCompareDialogBtn = new JButton("Run Compare Dialog");
	private JButton runCompareBtn       = new JButton("Run Compare");
	private JButton runHtmlCompareBtn   = new JButton("Run Html Compare");
	
	private JTextArea message        = new JTextArea();
	private int dbIdx;;
	
	
	private String rFiles;
//	private AbstractLayoutSelection layoutReader, layoutReader2;
	
	private AbstractLayoutSelectCreator<AbstractLayoutSelection> layoutCreator;
	
	private ActionListener listner = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == runCompareDialogBtn) {
				runCompareDialog();
			} else {
				runCompare(e.getSource() == runHtmlCompareBtn);
			}
		}
		
	};
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public RunSavedCompare(AbstractLayoutSelectCreator creator, int databaseIdx, String recentFiles) {
		super("Run Saved Compare File:", "", null);

		rFiles = recentFiles;

        ReMainFrame frame = ReMainFrame.getMasterFrame();
		int height = frame.getDesktop().getHeight() - 1;
		int width  = frame.getDesktop().getWidth() - 1;
		
		String fname =  Parameters.getFileName(Parameters.COMPARE_SAVE_FILE);

		layoutCreator = creator;
		dbIdx = databaseIdx;
		
		runCompareDialogBtn.addActionListener(listner);
		runCompareBtn.addActionListener(listner);
		runHtmlCompareBtn.addActionListener(listner);
		
		if (fname == null || "".equals(fname)) {
			fname = Parameters.getFileName(Parameters.COMPARE_SAVE_DIRECTORY);
		}
		xmlFileName.setText(fname);
		
		pnl.setGap(BasePanel.GAP3);
		pnl.addComponent("New File", xmlFileName, xmlFileName.getChooseFileButton());
		pnl.setGap(BasePanel.GAP5);
		
		pnl.addComponent("", null, runCompareDialogBtn);
		pnl.setGap(BasePanel.GAP1);
		pnl.addComponent("", null, runCompareBtn);
		pnl.setGap(BasePanel.GAP1);
		pnl.addComponent("", null, runHtmlCompareBtn);
		pnl.setGap(BasePanel.GAP5);
		
		pnl.addMessage(message);
		
		super.getContentPane().add(pnl);
		
		super.pack();
        this.setBounds(getX(), getY(), 
        		Math.min(width, getWidth() + WIDTH_INCREASE),
        		Math.min(height, getHeight()+HEIGHT_INCREASE));

		super.setVisible(true);
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
				
				if (! s.equals(p.getProperty(Parameters.COMPARE_SAVE_FILE))) {
					p.setProperty(Parameters.COMPARE_SAVE_FILE, s);
					
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
	 * Run the Compare Dialogue from saved XML Compare Definition
	 */
	private void runCompareDialog() {
		DiffDefinition def = readXml();
		
		runCompareDialog(def);
	}
	
	/**
	 * Run the Compare Dialogue with supplied Definition
	 * @param def Compare definition
	 */
	private void runCompareDialog(DiffDefinition def) {
		if (def != null) {
			if (DiffDefinition.TYPE_SINGLE_LAYOUT.equals(def.type)) {
				new CompareSingleLayout(createLayout(), def, rFiles);
			} else {
				new CompareTwoLayouts(createLayout(), createLayout(), def, rFiles);
			}
		}
	}

	
	/**
	 * Run the Compare from saved XML Compare Definition
	 */
	private void runCompare(boolean html) {
		DiffDefinition def = readXml();
		
		if (def != null) {
			if (! "Yes".equalsIgnoreCase(def.complete)) {
				runCompareDialog(def);
			} else {
				try {
					if (html) {
						if (def.htmlFile != null && ! "".equals(def.htmlFile)) {
							DoCompare.getInstance().writeHtml(createLayout(), createLayout(), def);
						} else {
							runCompareDialog(def);
						}
					} else {
						DoCompare.getInstance().compare(createLayout(), createLayout(), def);
					}
				} catch (Exception e) {
					String s = "Error Running Compare";
					e.printStackTrace();
					message.setText(s);
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


	
	private DiffDefinition readXml() {
		DiffDefinition def = null;
		JibxCall<DiffDefinition> jibx = new JibxCall<DiffDefinition>(DiffDefinition.class);
		String filename = xmlFileName.getText();
		
		if (filename == null && "".equals(filename)) {
			message.setText("You must Enter a filename");
		} else {		
			try {
				def = jibx.marshal(filename);
				def.saveFile = filename;
			} catch (Exception e) {
				String s = "Error Loading Record Layout";
				e.printStackTrace();
				message.setText(s);
				Common.logMsg(s, e);
			}
		}
		
		return def;
	}
}
