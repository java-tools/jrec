/*
 * Created on 18/11/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - Added Sort method
 */
package net.sf.RecordEditor.utils.jdbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;


/**
 * @author Bruce Martin
 *
 * Table model using a AbsDB to get the records to be
 * displayed in the Table
 */
@SuppressWarnings("serial")
public abstract class AbsDBTableModel<record extends AbsRecord> extends AbstractTableModel {


	protected AbsDB<record> dataBase;

	protected ArrayList<record> lines = null;
	protected ArrayList<record> deletedLines = new ArrayList<record>();
	protected AbsRecord[] copyLines;

	protected boolean toLoad = true;
	protected boolean cellEditable = false;
//	protected boolean changed = false;

	protected JFrame frame = null;

	private Comparator<record> comparator = null;

	/**
	 * Create Table model from a Database Interface
	 *
	 * @param jframe parent frame
	 * @param db Database Interface used as a source for Table rows
	 */
	public AbsDBTableModel(final JFrame jframe, final AbsDB<record> db) {
		super();

		dataBase = db;
		frame = jframe;
	}



	/**
	 * Create Table model from a Database Interface
	 *
	 * @param db Database Interface used as a source for Table rows
	 */
	public AbsDBTableModel(final AbsDB<record> db) {
		super();

		dataBase = db;
	}


	/**
	 * this method loads all the rows into internal storage
	 *
	 */
	public void load() {

		lines = dataBase.fetchAll();
	    if (comparator != null) {
	        Collections.sort(lines, comparator);
	    }
		toLoad = false;
//		changed = false;
	}


	/**
	 * This Method gets a column heading name
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public abstract String getColumnName(int col);


	/**
	 * Check if array should be retrieved from DB and retrieve it if need be
	 */
	protected void checkLoad() {
		if (toLoad) {
			load();
		}
	}


	/**
	 * Get a specific record
	 *
	 * @param row row to be retrieved
	 * @return requested row
	 */
	public record getRecord(int row) {

		checkLoad();

		if ((row >= 0) && (row < lines.size())) {
			return lines.get(row);
		}
		return null;
	}


	/**
	 * Get a specific record
	 *
	 * @param row row to be retrieved
	 * @return requested row
	 */
	public java.util.List<record> getRecords() {

		checkLoad();


		return lines;
	}

	/**
	 * This method updates the value of a row
	 *
	 * @param row row to update
	 * @param val new value
	 */
	public void  setRecord(int row, record val) {

		checkLoad();

		if ((row >= 0) && (row < lines.size())) {
			lines.set(row, val);
		} else {
			lines.add(val);
		}
	}



	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public abstract int getRowCount();


	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public abstract int getColumnCount();


	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public abstract Object getValueAt(int row, int col);


	/**
	 * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
	 */
	public abstract void setValueAt(Object val, int row, int col);


	/**
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col) {

		return cellEditable;
	}

	/**
	 * Set wethear table cells are editable
	 *
	 * @param editCells The cellEditable to set.
	 */
	public void setCellEditable(boolean editCells) {
		this.cellEditable = editCells;
	}


	/**
	 * Insert a specified number of rows
	 *
	 * @param row row where the value is to be inserted
	 * @param val value to be inserted
	 */
	public void addRow(int row, record val) {

		if (row >= lines.size()) {
			lines.add(val);
		} else {
			lines.add(row, val);
		}
	}


	/**
	 * Delete selected rows from the tables
	 *
	 * @param rows rows to be deleted
	 */
	public void deleteRows(int[] rows) {
		int i, r;

		for (i = rows.length - 1; i >= 0; i--) {
			r = rows[i];
			record rec = lines.get(r);

			if (! rec.isNew()) {
				deletedLines.add(rec);
			}

			lines.remove(r);
		}
	}


	/**
	 * Update the DB with changes made to internal copy
	 *
	 */
	public void updateDB() {
		int i;
		int num = deletedLines.size();
		record rec;

		boolean autoCommitOff = dataBase.setAutoCommit(false);
		try {
			for (i = num - 1; i >= 0; i--) {
				dataBase.delete(deletedLines.get(i));
				deletedLines.remove(i);
			}

			num = getRowCount();
			for (i = 0; i < num; i++) {
				rec = getRecord(i);
				if (rec.hasTheKeyChanged()
				&& !rec.isNew()
				&&  rec.getUpdateStatus() == AbsRecord.UPDATED) {
					dataBase.delete(rec);
					rec.setNew(true);
				}
			}

			for (i = 0; i < num; i++) {
				dataBase.checkAndUpdate(getRecord(i));
			}
		} catch (Exception e) {
			if (autoCommitOff) {
				dataBase.rollback();
			}
			throw new RuntimeException(e);
		} finally {
			if (autoCommitOff) {
				dataBase.setAutoCommit(true);
			}
		}

	}


	/**
	 * Delete a record from the source Database
	 *
	 * @param row Row to be deleted
	 *
	 * @return error message
	 */
	public String deleteRecordFromDB(int row) {

		checkLoad();

		dataBase.delete(getRecord(row));
		String s = dataBase.getMessage();

		if ("".equals(s)) {
			lines.remove(row);
		}

		return s;
	}


	/**
	 * This method updates the value of a row
	 *
	 * @param row row to update
	 * @param val new value
	 *
	 * @return Error message
	 */
	public String  setRecordAndDB(int row, record val) {

		checkLoad();

		dataBase.checkAndUpdate(val);
		String s = dataBase.getMessage();

		if ("".equals(s)) {
			if ((row >= 0) && (row < lines.size())) {
				lines.set(row, val);
			} else {
				lines.add(val);
			}
		}

		return s;
	}


	/**
	 * Adds a record to the Database
	 *
	 * @param val Record to add
	 *
	 * @return Error Message
	 */
	public String  addRecordtoDB(record val) {

		checkLoad();

		val.setNew(true);

		return setRecordAndDB(lines.size() + 128, val);
	}


	/**
	 *   This method save lines which will be pasted at a later stage (using the
	 * paste method
	 *
	 * @param linesToBeCopied The lines to be copied (saved)
	 */
	@SuppressWarnings("unchecked")
	public void setCopyLines(int[] linesToBeCopied) {
		int i;

		copyLines = new AbsRecord[linesToBeCopied.length];

		for (i = 0; i < linesToBeCopied.length; i++) {
			copyLines[i] = (record) getRecord(linesToBeCopied[i]).clone();
		}
	}


	/**
	 *   This method copies the copy lines to the specified position
	 * in the current list
	 *
	 * @param pos pastes the previously saved lines
	 */
	@SuppressWarnings("unchecked")
	public void pasteLines(int pos) {
		int i;

		if (copyLines == null) {
			System.out.println("Nothing to paste");
		} else {
			for (i = copyLines.length - 1; i >= 0; i--) {
				//lines.add(pos + 1, (record) copyLines[i].clone());
				addRow(pos+1, (record) copyLines[i].clone());
			}
		}
	}


	/**
	 * @return the read limit
	 */
	public int getReadLimit() {
		return dataBase.getReadLimit();
	}

	/**
	 * Remove all lines from DB
	 *
	 */
	public void removeAll() {
		lines = new ArrayList<record>();
	}


	/**
	 * @param readLimit set the read limit
	 */
	public void setReadLimit(int readLimit) {
		dataBase.setReadLimit(readLimit);
	}


	/**
	 * Sort the list
	 * @param c comparator to use in the sort
	 */
	public void sort(Comparator<record> c) {

	    comparator = c;
	    if (lines != null) {
	        Collections.sort(lines, c);
	    }
	}



	/**
	 * @param arg0 record to search for;
	 * @return index of requested record
	 * @see java.util.ArrayList#indexOf(java.lang.Object)
	 */
	public int indexOf(record arg0) {
		return lines.indexOf(arg0);
	}



	/**
	 * @return
	 * @see java.util.ArrayList#size()
	 */
	public int size() {
		return lines.size();
	}



}
