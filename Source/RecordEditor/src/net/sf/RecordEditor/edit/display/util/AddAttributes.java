package net.sf.RecordEditor.edit.display.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.XmlConstants;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;

import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.LayoutCombo;
import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public final class AddAttributes extends ReFrame implements ActionListener  {


	private static final int FIELD_TABLE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 15 / 2;

	private String[]   heading = {"Atrribute Name"};
	private String[][] lines = {{""},{""},{""},{""},{""},{""},{""},{""},{""},{""}};

	private BaseHelpPanel pnl = new BaseHelpPanel();

	private LayoutCombo layoutSelection;

	private DefaultTableModel model = new DefaultTableModel(lines, heading);
	private JTable attributeTbl = new JTable(model);

	private JButton addBtn = SwingUtils.newButton("Add");

	//private FileDisplay source;
	private FileView<?> view;

	private KeyAdapter listner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	if (event.getKeyCode() == KeyEvent.VK_ENTER) {
        		action_100_add();
        	}
        }
};


	/**
	 * Add Attrributes to the layout
	 * @param src source form
	 * @param fileTbl view of file
	 * @param layoutIdx laaayout index
	 */
	public AddAttributes(final FileView fileTbl, final int layoutIdx) {
		super(fileTbl.getFileNameNoDirectory(), "Add Attributes",
				fileTbl.getBaseFile());

		view   = fileTbl;

		init_100_setupScreenFields(layoutIdx);

		init_200_LayoutScreen();
	}


	/**
	 * Setup screen fields
	 *
	 */
	public void init_100_setupScreenFields(int layoutIdx) {

		layoutSelection = new LayoutCombo(view.getLayout(), false, false);
		layoutSelection.setLayoutIndex(layoutIdx);

		if (! Common.TEST_MODE) {
			pnl.addReKeyListener(listner);
		}

		addBtn.addActionListener(this);
	}

	/**
	 * Layout the screen
	 *
	 */
	public void init_200_LayoutScreen() {

		pnl.addLine("Layout", layoutSelection);
		pnl.addComponent(1, 5, FIELD_TABLE_HEIGHT, BasePanel.GAP2,
				BasePanel.FULL, BasePanel.FULL,
				attributeTbl);

		pnl.addLine("", null,  addBtn);

		this.addMainComponent(pnl);
		this.setVisible(true);
	}


	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public final void actionPerformed(ActionEvent arg0) {
		action_100_add();
	}

	/**
	 * Add the new attributes to the layout
	 *
	 */
	private void action_100_add() {

		AbstractRecordDetail record = view.getLayout().getRecord(layoutSelection.getLayoutIndex());
		FieldDetail field;
		String s;

		Common.stopCellEditing(attributeTbl);

		for (int i = 0; i < model.getRowCount(); i++) {
			s = model.getValueAt(i, 0).toString();

			if (s != null && ! "".equals(s)) {
				if (! s.startsWith(XmlConstants.ATTRIBUTE_PREFIX)) {
					s = XmlConstants.ATTRIBUTE_PREFIX + s;
				}
				field = new FieldDetail(s, "", Type.ftChar, 0, "", 0, "");
				field.setPosOnly(record.getFieldCount());

				//System.out.println("---> " + s);
				record.addField(field);
			}
		}

		view.getBaseFile().fireTableStructureChanged();
		this.doDefaultCloseAction();
	}

}
