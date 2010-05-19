package net.sf.RecordEditor.layoutWizard;

import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;

public class RecordComboMgr {
    public final JComboBox recordCombo = new JComboBox();
  	private AbstractAction recordAction;
	/**
	 * @param recordAction
	 */
	public RecordComboMgr(AbstractAction recordAction) {
		this.recordAction = recordAction;
	}

	public void load(ArrayList<RecordDefinition> recordDtls) {
        recordCombo.removeActionListener(recordAction);
        recordCombo.removeAllItems();
        
        if (recordDtls.size() > 0) {
	        for (RecordDefinition def : recordDtls) {
	        	recordCombo.addItem(def.name);
	        }
	        recordCombo.setSelectedIndex(0);
        }
        recordCombo.addActionListener(recordAction);
	}
}
