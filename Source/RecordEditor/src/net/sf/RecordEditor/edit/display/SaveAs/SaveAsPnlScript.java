/**
 *
 */
package net.sf.RecordEditor.edit.display.SaveAs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComboBox;

import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.script.ScriptMgr;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;

/**
 * @author Bruce Martin
 *
 */
public class SaveAsPnlScript extends SaveAsPnlBase {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox languageCombo = new JComboBox(
				ScriptMgr.getLanguages().toArray(new String[ScriptMgr.getLanguages().size()])
	);

	private ScriptMgr scriptMgr = new ScriptMgr();

	/**
	 * @param commonSaveAsFields common screen fields
	 */
	public SaveAsPnlScript(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".txt", CommonSaveAsFields.FMT_SCRIPT, RecentFiles.RF_SCRIPT, new FileChooser(true, "get Script"));

        template.setText(Common.OPTIONS.DEFAULT_SCRIPT_EXPORT_DIRECTORY.get());

        panel.addLine("Script Language", languageCombo)
             .setGap(BasePanel.GAP2);
        panel.addLine("Script", template, template.getChooseFileButton())
        	 .setGap(BasePanel.GAP2);

 		addHtmlFields(panel);

        template.addFcFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {

				checkLanguage();
				SaveAsPnlScript.this.commonSaveAsFields.templateListner.focusLost(e);
			}
		});

        template.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkLanguage();
			}
		});
    }


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#setTemplateText(java.lang.String)
	 */
	@Override
	public void setTemplateText(String text) {
		super.setTemplateText(text);
		checkLanguage();
	}


	private void checkLanguage() {
		try {
        	String ext = Parameters.getExtensionOnly(template.getText());

        	String lang = scriptMgr.getEngineNameByExtension(ext);

        	if (! "".equals(lang)) {
        		languageCombo.setSelectedItem(lang);
        	}

		} catch (Exception e2) {
			// do nothing
		}
	}


	public void save(String selection, String outFile) throws Exception {
	       (new ScriptMgr()).runScript(
	    		   languageCombo.getSelectedItem().toString(),
	    		   template.getText(),
	    		   getScriptData(selection, outFile));

	}


	/**
	 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#isActive()
	 */
	@Override
	public boolean isActive() {
		return languageCombo.getItemCount() > 0;
	}
}
