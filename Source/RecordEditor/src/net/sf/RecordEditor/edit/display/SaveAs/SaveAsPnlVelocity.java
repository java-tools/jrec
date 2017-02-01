/**
 *
 */
package net.sf.RecordEditor.edit.display.SaveAs;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.script.RunVelocity;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;

/**
 * @author Bruce Martin
 *
 */
public class SaveAsPnlVelocity extends SaveAsPnlBase {


	/**
	 * @param commonSaveAsFields common screen fields
	 */
	public SaveAsPnlVelocity(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".html", CommonSaveAsFields.FMT_VELOCITY, RecentFiles.RF_VELOCITY,
				new FileSelectCombo(Parameters.VELOCITY_SKELS_LIST, 25, true, false), false);
//				new FileChooser(true, "get Template"));

		addHtmlFields(panel); 


        template.setText(Common.OPTIONS.DEFAULT_VELOCITY_DIRECTORY.get());
        panel.addLineRE("Velocity Template", template);

        template.addFcFocusListener(commonSaveAsFields.templateListner);
//        template.addTextChangeListner(new ChangeListener() {
//			@Override public void stateChanged(ChangeEvent e) {
//				SaveAsPnlVelocity.this.commonSaveAsFields.templateListner.focusLost(null);
//			}
//		});
        		//commonSaveAsFields.templateListner);
    }


	public void save(String selection, String outFile) throws Exception {
	       RunVelocity velocity = RunVelocity.getInstance();

	       BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf8"));;
	       try {
	    	   velocity.genSkel(template.getText(),
	        		getScriptData(selection, outFile),
	        		w);
	       } finally {
	    	   w.close();
	       }
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#isActive()
	 */
	@Override
	public boolean isActive() {
		return Common.isVelocityAvailable();
	}



}
