package net.sf.RecordEditor.utils.edit;

import java.util.ArrayList;

import net.sf.JRecord.Common.AbstractManager;
import net.sf.JRecord.Common.BasicKeyedField;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.AbsRowList;

public class ManagerRowList extends AbsRowList {

	private AbstractManager functionManager;

	/**
	 * Create a list from a manager
	 * @param manager A object manager
	 * @param sort wether to sort the list
	 */
	public ManagerRowList(final AbstractManager manager, final boolean sort) {
		super(0, 1, sort, false);

		functionManager = manager;
	}

	/**
	 * @see net.sf.RecordEditor.utils.swing.AbsRowList#loadData()
	 */
	@Override
	protected void loadData() {
		int key;
		String name;
		BasicKeyedField fld;
		ArrayList<BasicKeyedField> rows = new ArrayList<BasicKeyedField>();

		String mgrName = functionManager.getManagerName();

		for (int i = 0; i < functionManager.getNumberOfEntries(); i++) {
			key  = functionManager.getKey(i);
			name = functionManager.getName(i);
			if (name != null && ! "".equals(name)) {
				fld = new BasicKeyedField();
				fld.key  = key;
				fld.name = LangConversion.convertId(
						LangConversion.ST_EXTERNAL,
						mgrName + "_" + key, name);
				rows.add(fld);
			}
		}

		super.loadData(rows);
	}

}
