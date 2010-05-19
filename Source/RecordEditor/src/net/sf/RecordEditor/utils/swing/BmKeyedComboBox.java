/*
 * Created on 14/09/2004
 *
 */
package net.sf.RecordEditor.utils.swing;

import javax.swing.JComboBox;
import javax.swing.table.TableCellRenderer;



//import swingUtils.*;

/**
 * This class implements a Keyed Combo Model
 * ie  each row in the combo model consists of <ol compact>
 * <li>a key value the program see's but not the user
 * <li>A display value the user see's
 * </ol>
 *
 * A typical use might be a color ComboModel conisting of value pairs like
 * <ul>
 * <li>1  -  Red
 * <li>2  -  Blue
 * <li>3  -  Green
 * </ul>
 *
 * The Program would use the integer values 1, 2 .. while the user would
 * see Red, green, blue etc.
 * <br/><br/>
 *
 *
 * @author Bruce Martin
 *
 */
public class BmKeyedComboBox extends JComboBox {

	private BmKeyedComboModel model;
	private BmKeyedComboModel newModel;
	//private boolean editable;
	private BmKeyedComboBoxRender cellRender = null;


	private BmKeyedComboAgent agent = null;



	/**
	 * This class implements a Keyed Combo Model
	 * ie  each row in the combo model consists of <ol compact>
	 * <li>a key value the program see's but not the user
	 * <li>A display value the user see's
	 * </ol>
	 *
	 * A typical use might be a color ComboModel conisting of value pairs like
	 * <ul>
	 * <li>1  -  Red
	 * <li>2  -  Blue
	 * <li>3  -  Green
	 * </ul>
	 *
	 * The Program would use the integer values 1, 2 .. while the user would
	 * see Red, green, blue etc.
	 * <br/><br/>
	 *
	 * @param combModel KeyedCombo model
	 * @param isEditable wether user should be able to edit the combo box
	 */
	public BmKeyedComboBox(final BmKeyedComboModel combModel,
	        			   final boolean isEditable) {
		super(combModel);

		init(combModel, isEditable);
	}


	/**
	 * This class implements a Keyed Combo Model
	 * ie  each row in the combo model consists of <ol compact>
	 * <li>a key value the program see's but not the user
	 * <li>A display value the user see's
	 * </ol>
	 *
	 * A typical use might be a color ComboModel conisting of value pairs like
	 * <ul>
	 * <li>1  -  Red
	 * <li>2  -  Blue
	 * <li>3  -  Green
	 * </ul>
	 *
	 * The Program would use the integer values 1, 2 .. while the user would
	 * see Red, green, blue etc.
	 * <br/><br/>
	 *
	 *
	 * @param list Row List used for Drop Downlist
	 * @param isEditable wether Text Part of the combo box is editable
	 */
	public BmKeyedComboBox(final AbstractRowList list, final boolean isEditable) {
		super();

		BmKeyedComboModel tempModel = new BmKeyedComboModel(list);
		this.setModel(tempModel);

		init(tempModel, isEditable);
	}


	/**
	 * Common Class initialise
	 *
	 * @param comboModel Combo Model used to get Dropdown list
	 * @param editable wether the user can edit the actual Text field
	 * 				part of the combobox
	 */
	private void init(BmKeyedComboModel comboModel, boolean editable) {

		this.model = comboModel;
		//this.editable = isEditable;

		//model.setTableCombo(true);
		super.setRenderer(comboModel);

		if (editable) {
			this.setEditable(editable);
			agent = new BmKeyedComboAgent(this, comboModel.getList(), comboModel.isSorted());
		}
	}


	/**
	 * Get the Table Cell render being used to display the Drop Down list
	 *
	 * @return Table Cell render
	 */
	public TableCellRenderer getTableCellRenderer() {

		if (cellRender == null) {
			newModel = new BmKeyedComboModel(model.getList());
			cellRender = new BmKeyedComboBoxRender(newModel, false);

			if (agent != null) {
				agent.setCellRender(true);
			}
		}

		return cellRender;
	}
	
	public void setSelectedDisplay(String displTxt) {

		AbstractRowList list = model.getList();
		if (displTxt != null) {
			for (int i = 0; i < list.getSize(); i++) {
				//o = list.getFieldAt(i);
				if (list.getFieldAt(i) != null && displTxt.equalsIgnoreCase(list.getFieldAt(i).toString())) {
					this.setSelectedIndex(i);
					break;
				}
			}
		}
	}
}
