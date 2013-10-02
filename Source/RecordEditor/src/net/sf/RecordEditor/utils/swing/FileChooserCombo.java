package net.sf.RecordEditor.utils.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;




@SuppressWarnings({ "serial", "rawtypes" })
public class FileChooserCombo extends FileChooserBtnSelect<JComboBox> implements UpdatableTextValue {

	private final int maxListSize;
	private final List<String> list;


	public FileChooserCombo(boolean isOpen, boolean isDirectory, String[] items, int itemCount) {
		this(isOpen, isDirectory, items, itemCount, OPEN_FILE_CHOOSER_POSITION);
	}


	@SuppressWarnings("unchecked")
	public FileChooserCombo(boolean isOpen, boolean isDirectory, String[] items, int itemCount, JButton... btns) {
		super(new JComboBox(items), isOpen, isDirectory, btns);

		this.maxListSize = Math.max(itemCount, items.length);
		this.list = new ArrayList<String>(maxListSize);


		for (String s : items) {
			list.add(s);
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.UpdatableTextValue#getText()
	 */
	@Override
	public String getText() {
	    return component.getSelectedItem().toString();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.UpdatableTextValue#setText(java.lang.String)
	 */
    @SuppressWarnings("unchecked")
	@Override
	public void setText(String text) {
        component.setSelectedItem(text);
        list.remove(text);
        list.add(0, text);

        if (list.size() >= maxListSize) {
        	list.remove(list.size() - 1);
        }

        component.setModel(new DefaultComboBoxModel(list.toArray()));
    }
}