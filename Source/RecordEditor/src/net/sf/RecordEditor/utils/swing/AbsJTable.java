/*
 * Created on 21/09/2004
 *
 */
package net.sf.RecordEditor.utils.swing;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * Abstract extension of a JTable
 * @author Bruce Martin
 */
@SuppressWarnings("serial")
public class AbsJTable extends JTable {


    /**
     * Define Table
     *
     */
	public AbsJTable() {
		super();
	}

	/**
	 * Define Table
	 *
	 * @param mdl Table model
	 */
	public AbsJTable(final TableModel mdl) {
		super(mdl);
		super.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	}

//	public abstract void commonInit();


	/**
	 * Set Table Column Sizes
	 */
	public void setColumnSizes() { }
}