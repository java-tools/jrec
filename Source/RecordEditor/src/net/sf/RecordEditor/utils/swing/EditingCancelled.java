package net.sf.RecordEditor.utils.swing;

import net.sf.JRecord.Common.RecordException;

@SuppressWarnings("serial")
public class EditingCancelled extends RecordException {

	public EditingCancelled() {
		super("Editing canceled");
	}

}
