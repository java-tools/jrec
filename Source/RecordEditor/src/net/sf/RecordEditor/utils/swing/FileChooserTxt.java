package net.sf.RecordEditor.utils.swing;

import javax.swing.JTextField;



@SuppressWarnings("serial")
public class FileChooserTxt extends FileChooserBtnSelect<JTextField> implements UpdatableTextValue {


	public FileChooserTxt(boolean isOpen) {
		super(new JTextField(), isOpen, false, OPEN_FILE_CHOOSER_POSITION);

	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.UpdatableTextValue#getText()
	 */
	@Override
	public String getText() {
	    return component.getText();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.UpdatableTextValue#setText(java.lang.String)
	 */
    @Override
	public void setText(String text) {
        component.setText(text);
    }
}