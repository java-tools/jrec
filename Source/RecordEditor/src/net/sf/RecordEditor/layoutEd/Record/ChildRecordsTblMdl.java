/**
 *
 */
package net.sf.RecordEditor.layoutEd.Record;

import java.util.HashMap;
import java.util.HashSet;

import javax.swing.ComboBoxModel;

import net.sf.RecordEditor.re.db.Record.ChildRecordsDB;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.RecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.utils.common.AbsConnection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.swing.Combo.ComboObjOption;
import net.sf.RecordEditor.utils.swing.Combo.KeyedComboMdl;
import net.sf.RecordEditor.utils.swing.Combo.ListListner;


/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ChildRecordsTblMdl extends DBtableModel<ChildRecordsRec> {

	public static final int RECORD_COLUMN = 2;
	public static final int PARENT_COLUMN = 6;

	public final KeyedComboMdl<Integer> recordKeys;
	//public final int recordId;

	private final HashMap<Integer, ParentItem> parentItems = new HashMap<Integer, ChildRecordsTblMdl.ParentItem>();
	private final HashMap<Integer, String> recordNameLookup = new HashMap<Integer, String>();
	private AbsConnection dbConnection;

	private ParentItem nullParent = new ParentItem(-1);
	private ChildRecordsDB childDb;

	public ChildRecordsTblMdl(ChildRecordsDB db, KeyedComboMdl<Integer> recordKeys) {
		super(db);

		this.recordKeys = recordKeys;
		dbConnection = db.getConnect();
		childDb = db;


		RecordDB recDb = new RecordDB();
		recDb.setConnection(dbConnection);

		for (int i = 0; i < getRowCount(); i++) {
			lookupRecord(recDb, getRecord(i).getChildRecordId());
		}
	}


	public String getName4record(int recordId) {
		String s = "";
		Integer recordIdKey = Integer.valueOf(recordId);

		if (! recordNameLookup.containsKey(recordIdKey)) {
			RecordDB recDb = new RecordDB();
			recDb.setConnection(dbConnection);

			lookupRecord(recDb, recordId);
		}
		if (recordNameLookup.containsKey(recordIdKey)) {
			s = recordNameLookup.get(recordIdKey);
			//System.out.println("Found Record Name: " + recordId + " " + s);
		}

		return s;
	}

	private void lookupRecord(RecordDB recDb, int recordId) {
		recDb.resetSearch();
		recDb.setSearchRecordId(RecordDB.opEquals, recordId);
		recDb.open();
		RecordRec rec = recDb.fetch();

		//System.out.println(" Record Lookup: " + recordId + " " + (rec == null));
		if (rec != null) {
			recordNameLookup.put(recordId, rec.getRecordName());
		}
	}

	private ParentItem getParentItem(int id) {

		//System.out.print("Get Parent Item: " + id + " ");
		if (id < 0) {
			return nullParent;
		}

		Integer key = Integer.valueOf(id);
		ParentItem ret = parentItems.get(key);

		//System.out.print(ret == null);
		if (ret == null) {
			ret = new ParentItem(id);
			parentItems.put(key, ret);
			//System.out.println("putParent: " + id);
		}
		//System.out.println(" " + ret.childId + " >" + ret.toString());
		return ret;

	}


	public final ComboBoxModel<ParentItem> getParentModel() {
		return new ParentComboModel();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.jdbc.DBtableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int col) {
		if (Common.TEST_MODE) {
			switch (col) {
			case 0: return "sl";
			case 1: return "rs";
			}
		}

		return super.getColumnName(col);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.jdbc.DBtableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {
		Object ret = super.getValueAt(row, col);
		int val;

		switch (col) {
		case RECORD_COLUMN:
			//recordKeys
			ComboObjOption<Integer> rRet = recordKeys.nullItem;


			if (ret instanceof Integer && (val = ((Integer) ret).intValue()) >= 0) {
				rRet = recordKeys.items.get(val);
			} else if (ret instanceof Number && (val = ((Number) ret).intValue()) >= 0) {
				rRet = recordKeys.items.get(((Number) ret).intValue());
			}
			if (rRet == null) {
				//System.out.println(">> GetValue: " + row + " " + col + " ~ " + ret +" > " + rRet);
				rRet = recordKeys.nullItem;
//			} else {
//				System.out.println("GetValue: " + row + " " + ret +" > " + rRet.index + " " + rRet
//						+ " " + (ret instanceof Number) + " " );
			}
			ret = rRet;
			break;
		case PARENT_COLUMN:
			ParentItem iRet = nullParent;

			if (ret instanceof Number && (val = ((Number) ret).intValue()) >= 0) {
				iRet = getParentItem(val);
			}
//			System.out.println("GetValue: " + row + " " + ret +" > " + iRet.childId + " " + iRet
//					+ " " + (ret instanceof Number) + " " );
			ret = iRet;
			break;
		}
		return ret;
	}




	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.jdbc.DBtableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object val, int row, int col) {
		//System.out.print("Set Value: " + col + " " + val + " " + (val instanceof ParentItem) + "> ");

		switch (col) {
		case RECORD_COLUMN:
			if (val instanceof ComboObjOption) {
				val = ((ComboObjOption) val).index;
			}
			break;
		case PARENT_COLUMN:
			if (val instanceof ParentItem) {
				val = Integer.valueOf(((ParentItem) val).childId);
			}
		}
		//System.out.println(val);
		super.setValueAt(val, row, col);
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.jdbc.AbsDBTableModel#addRow(int, net.sf.RecordEditor.utils.jdbc.AbsRecord)
	 */
	@Override
	public void addRow(int row, ChildRecordsRec val) {
		super.addRow(row, val);

		if (val.getChildId() < 0) {
			if (childDb.getRecordId() >= 0) {
				childDb.insert(val);
			} else {
				int maxKey = 0,
					maxId = 0;
				for (int i = 0; i < getRowCount(); i++) {
					maxKey = Math.max(maxKey, getRecord(i).getChildKey());
					maxId  = Math.max(maxId,  getRecord(i).getChildId());
				}
				val.setChildKey(maxKey + 1);
				val.setChildId(maxId + 1);
			}
		}
	}

	/**
	 * @return
	 * @see net.sf.RecordEditor.re.db.Record.ChildRecordsDB#getRecordId()
	 */
	public int getRecordId() {
		return childDb.getRecordId();
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.jdbc.AbsDBTableModel#load()
	 */
	@Override
	public void load() {
		boolean error = false;
		int parentId, id, i, j;
		HashSet<Integer> used = new HashSet<Integer>();

		super.load();
		recordNameLookup.clear();

		for (i = 0; i < getRowCount(); i++) {
			id = getRecord(i).getChildId();
			if (id >= 0 && used.contains(id)) {
				error = true;
				break;
			}
			used.add(id);
		}

		if (error) {
			for (i = 0; i < getRowCount(); i++) {
				parentId = getRecord(i).getParentRecord();
				if (parentId >= 0) {
					for (j = 0; j < getRowCount(); j++) {
						if (parentId == getRecord(j).getChildId()) {
							 getRecord(i).setParentRecord(j+1);
							 break;
						}
					}
				}

			}
			for (i = 0; i < getRowCount(); i++) {
				getRecord(i).setChildId(i+1);
			}
			super.updateDB();
		}
	}

	public void clearRecordLookup() {
		recordNameLookup.clear();
	}


	/* ------------------------------------------------------------------------------
	 * Class definitions - Combo Items for the combo Box
	 * ------------------------------------------------------------------------------ */
	private final class ParentComboModel extends ListListner implements  ComboBoxModel<ParentItem> {

		private int currIdx = 0;

		/* (non-Javadoc)
		 * @see javax.swing.ListModel#getElementAt(int)
		 */
		@Override
		public ParentItem getElementAt(int row) {
			//System.out.println("Get Element: : " + row + " " + getRowCount());
			if (row < 1 || row > getRowCount()) {
				return nullParent;
			}
			return getParentItem(getRecord(row - 1).getChildId());
		}

		/* (non-Javadoc)
		 * @see javax.swing.ListModel#getSize()
		 */
		@Override
		public int getSize() {
			return getRowCount() + 1;
		}



		/* (non-Javadoc)
		 * @see javax.swing.ComboBoxModel#getSelectedItem()
		 */
		@Override
		public Object getSelectedItem() {
			return getElementAt(currIdx);
		}

		/* (non-Javadoc)
		 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
		 */
		@Override
		public void setSelectedItem(Object item) {
			int lastIdx = currIdx;

			currIdx = 0;

			if (item instanceof ParentItem) {
				ParentItem parentItem = (ParentItem) item;
				for (int i = 0; i < getRowCount(); i++) {
					if (getRecord(i).getChildId() == parentItem.childId) {
						currIdx = i + 1;

						break;
					}
				}
			}

			if (lastIdx != currIdx) {
				tellOfUpdates(currIdx);
			}
		}

	}


	private final class ParentItem {
		public final int childId;

		public ParentItem(int childId) {
			super();
			this.childId = childId;
		}

		public String toString() {
			if (childId >= 0) {
				for (int i = 0; i < getRowCount(); i++) {
					//System.out.println(" Parent: " + i + " " + childId + " " + getRecord(i).getChildId());
					if (getRecord(i).getChildId() == childId) {
						ChildRecordsRec child = getRecord(i);
						StringBuilder b = new StringBuilder();
						if (child.getChildName() != null && ! "".equals(child.getChildName().trim())) {
							b.append(child.getChildName())
							 .append(": ");
						}

						//System.out.println(" Find name: " + child.getChildRecordId());
						return b.append(getName4record(child.getChildRecordId())).toString();
					}
				}
			}

			return "";
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof ParentItem) {
				return childId == ((ParentItem) obj).childId;
			}
			return super.equals(obj);
		}
	}
}
