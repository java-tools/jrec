package net.sf.RecordEditor.utils.swing.ComboBoxs;

import javax.swing.JComboBox;

import net.sf.RecordEditor.utils.swing.Combo.ComboStdOption;
import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption;

@SuppressWarnings("serial")
public class EnglishCombo<Key extends Object> extends JComboBox {

	private final ComboStdOption<Key> empty;
	public EnglishCombo(ComboStdOption<Key> emptyVal) {
		super();

		empty = emptyVal;
	}



	public final Key getSelectedKey() {
		return getSelectedOption().key;
	}

	public final Key getKey(int idx) {
		return getOption(idx).key;
	}

	public final String getSelectedText() {
		return getSelectedOption().toString();
	}

	public final String getSelectedEnglish() {
		return getSelectedOption().getEnglish();
	}

	public final int getAddEnglish(String englishText, boolean add) {
		for (int i = 0; i < this.getItemCount(); i++) {
			if (getOption(i).getEnglish().equals(englishText)) {
				this.setSelectedIndex(i);
				return i;
			}
		}
		int ret = -1;

		if (add) {
			ret = this.getItemCount();
			this.addItem(new ComboStrOption(englishText, englishText, englishText));
			this.setSelectedIndex(ret);
		}
		return ret;
	}

	public final void setEnglish(String englishText) {
		for (int i = 0; i < this.getItemCount(); i++) {
			if (getOption(i).getEnglish().equals(englishText)) {
				this.setSelectedIndex(i);
				break;
			}
		}
	}

	public final void setKey(String key) {
		for (int i = 0; i < this.getItemCount(); i++) {
			if (getOption(i).key.equals(key)) {
				this.setSelectedIndex(i);
				break;
			}
		}
	}

	private ComboStdOption<Key> getSelectedOption() {
		return getOption((ComboStdOption<Key>) this.getSelectedItem());
	}

	private ComboStdOption<Key> getOption(int idx) {
		return getOption((ComboStdOption<Key>) this.getItemAt(idx));
	}

	private ComboStdOption<Key> getOption(ComboStdOption<Key> ret) {
		if (ret == null) {
			ret = empty;
		}
		return ret;
	}

}