package net.sf.RecordEditor.utils.swing.ComboBoxs;

import javax.swing.JComboBox;

import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption;
import net.sf.RecordEditor.utils.swing.Combo.IComboOption;

@SuppressWarnings("serial")
public class EnglishCombo<Key extends Object> extends JComboBox {

	private final IComboOption<Key> empty;
	public EnglishCombo(IComboOption<Key> emptyVal) {
		super();

		empty = emptyVal;
	}



	public final Key getSelectedKey() {
		return getSelectedOption().getKey();
	}

	public final Key getKey(int idx) {
		return getOption(idx).getKey();
	}

	public final String getSelectedText() {
		return getSelectedOption().toString();
	}

	public final String getSelectedEnglish() {
		return getSelectedOption().getEnglish();
	}

	public final int getAddEnglish(String englishText, boolean add) {
		for (int i = 0; i < this.getItemCount(); i++) {
			if (getOption(i).getKey().equals(englishText)) {
				this.setSelectedIndex(i);
				return i;
			}
		}
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
			if (getOption(i).getKey().equals(key)) {
				this.setSelectedIndex(i);
				break;
			}
		}
	}

	private IComboOption<Key> getSelectedOption() {
		return getOption((IComboOption<Key>) this.getSelectedItem());
	}

	private IComboOption<Key> getOption(int idx) {
		return getOption((IComboOption<Key>) this.getItemAt(idx));
	}

	private IComboOption<Key> getOption(IComboOption<Key> ret) {
		if (ret == null) {
			ret = empty;
		}
		return ret;
	}

}