package net.sf.RecordEditor.edit.display.extension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.DisplayFrame;
import net.sf.RecordEditor.edit.display.util.LinePosition;
import net.sf.RecordEditor.edit.display.util.MovementBtnPnl;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;

public abstract class ZCopyOfRecordSelection extends BaseDisplay
implements IChildScreen, AbstractFileDisplayWithFieldHide, TableModelListener {

	protected int currRow = 0;
	@SuppressWarnings("rawtypes")
	protected AbstractLine line;
	protected JXMultiSplitPane splitPane = new JXMultiSplitPane();
	private MovementBtnPnl movementPnl;


	private PaneDtls[] fields;

	private FocusAdapter focusListner = new FocusAdapter() {

		/* (non-Javadoc)
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent e) {
			if (line != null && fields != null && e != null && e.getComponent() != null) {
				for (PaneDtls p : fields) {
					if (e.getComponent() == p.txtFld && p.fieldDef != null) {
						try {
							line.setField(0, p.fieldDef.fieldIdx, p.txtFld.getText());
							fileView.fireRowUpdated(currRow, null, line);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						break;
					}
				}
			}
		}
	};


	public ZCopyOfRecordSelection(
			String formType, @SuppressWarnings("rawtypes") FileView viewOfFile, int lineNo) {
		super(formType, viewOfFile, false, false, false, false, false, NO_LAYOUT_LINE);

		currRow = lineNo;
		line = fileView.getLine(currRow);

		setJTable(new JTable());
		splitPane.setModel(new DefaultSplitPaneModel());

		fileView.addTableModelListener(this);
	}


	protected final void init_200_layoutScreen() {

		movementPnl = new MovementBtnPnl(Common.getArrowIcons(), true, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				btnPressed(event);
			}
		});
		layoutFieldPane();

		actualPnl.addComponentRE(1, 3, BasePanel.FILL, BasePanel.GAP,
	            BasePanel.FULL, BasePanel.FULL, new JScrollPane(splitPane));

		actualPnl.addComponentRE(1, 5, BasePanel.PREFERRED, BasePanel.GAP,
				BasePanel.FULL, BasePanel.FULL, movementPnl);
		actualPnl.done();
	}

	private void layoutFieldPane() {

		//ArrayList<PaneDtls> panes = new ArrayList<PaneDtls>();
		int idx;
		//Split rowDef0 = new Split();
		Split layoutDef = new Split();

		ArrayList<ArrayList<MultiSplitLayout.Node>> rows = new ArrayList<ArrayList<MultiSplitLayout.Node>>();
		rows.add(new ArrayList<MultiSplitLayout.Node>(fields.length * 2));


		setTxtFields();

		for (int i = 0; i < fields.length; i++) {
			idx = fields[i].col;
			while (rows.size() <= idx) {
				rows.add(new ArrayList<MultiSplitLayout.Node>(fields.length));
			}
			if (rows.get(idx).size() > 0) {
				rows.get(idx).add(new MultiSplitLayout.Divider());
			}
			rows.get(idx).add(new MultiSplitLayout.Leaf(fields[i].name));
		}

		if (rows.size() == 1) {
			layoutDef.setRowLayout(false);
			layoutDef.setChildren(rows.get(0));
		} else {
			ArrayList<MultiSplitLayout.Node> rowDefs = new ArrayList<MultiSplitLayout.Node>();
			Split rowDef = new Split();

			rowDef.setRowLayout(false);
			rowDef.setChildren(rows.get(0));
			rowDefs.add(rowDef);
			for (int i = 0; i < rows.size(); i++) {
				rowDefs.add(new MultiSplitLayout.Divider());
				rowDef = new Split();

				rowDef.setRowLayout(false);
				rowDef.setChildren(rows.get(0));
				rowDefs.add(rowDef);
			}

			layoutDef.setRowLayout(true);
			layoutDef.setChildren(rowDefs);
		}

		splitPane.setLayout(new MultiSplitLayout(layoutDef));
		for (int i = 0; i < fields.length; i++) {
			splitPane.add(fields[i].getDisplayPane(), fields[i].name);
		}
	}

	@Override
	public void setScreenSize(boolean mainframe) {

		if (mainframe) {
			DisplayFrame parentFrame = getParentFrame();
			int preferedWidth = java.lang.Math.min(this.screenSize.width - 2,
			       80 * SwingUtils.CHAR_FIELD_WIDTH );

			parentFrame.bldScreen();
			parentFrame.setBounds(parentFrame.getY(), parentFrame.getX(), preferedWidth,
			        Math.min(parentFrame.getHeight(), this.screenSize.height - 5));
			parentFrame.show();
			parentFrame.setToMaximum(false);
			parentFrame.addCloseOnEsc(actualPnl);
		} else {
			this.actualPnl.done();
		}
	}

	private void btnPressed(ActionEvent event) {
		if (event.getSource() == movementPnl.buttons[0]) {
			currRow = 0;
			rowChanged();
		} else if (event.getSource() == movementPnl.buttons[1] && currRow > 0) {
			currRow -= 1;
			rowChanged();
		} else if (event.getSource() == movementPnl.buttons[2]) {
			currRow += 1;
			rowChanged();
		} else if (event.getSource() == movementPnl.buttons[3]) {
			currRow = getFileView().getRowCount() - 1;
			rowChanged();
		}
	}

	@Override
	protected int getInsertAfterPosition() {
		return getStandardPosition();
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getInsertAfterLine()
	 */
	@Override
	protected LinePosition getInsertAfterLine(boolean prev) {
		return super.getInsertAfterLine(currRow, prev);
	}

	@Override
	public void fireLayoutIndexChanged() {

	}

	@Override
	public int getCurrRow() {
		return currRow;
	}

	@Override
	public void setCurrRow(int newRow) {

		if ((newRow >= 0)) {
			line = fileView.getLine(newRow);
			if (currRow != newRow) {
				currRow = newRow;
				rowChanged();
			}
		}
	}

	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
		setCurrRow(newRow);
	}


	private void rowChanged() {
		line = fileView.getLine(currRow);

		setTxtFields();
		//this.splitPane.repaint();
		this.splitPane.revalidate();
	}

	protected void setTxtFields() {

		for (int i = 1; i < fields.length; i++) {
			if (fields[i].txtFld != null && fields[i].fieldDef != null) {
				fields[i].txtFld.setText(getFieldVal(fields[i].fieldDef));
			}
		}
		//htmlEdt.setText(t) = new
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(PaneDtls[] fields) {
		this.fields = fields;

		for (PaneDtls p : fields) {
			if (p.txtFld != null && p.fieldDef != null) {
				p.txtFld.addFocusListener(this.focusListner);
			}
		}
	}

	protected final String getFieldVal(FieldDef fld) {
		String s = "";
		Object o = line.getField(0, fld.fieldIdx);

		if (o != null) {
			s = o.toString();
		}

		return s;
	}

	@Override
	public boolean[] getFieldVisibility(int recordIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFieldVisibility(int recordIndex, boolean[] fieldVisibility) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent event) {

		switch (event.getType()) {
			case (TableModelEvent.INSERT):
				if (event.getFirstRow() <= currRow) {
					currRow += event.getLastRow() 	-  event.getFirstRow() + 1;
					rowChanged();
				}
				break;
			case (TableModelEvent.DELETE):
				if (currRow > event.getLastRow()) {
					currRow -= event.getLastRow() - event.getFirstRow() + 1;
					rowChanged();
				} else if (currRow > event.getFirstRow()) {
					currRow -= Math.min(fileView.getRowCount(), event.getFirstRow());
					rowChanged();
				}
				break;
			case (TableModelEvent.UPDATE):
				if (event.getFirstRow() == currRow) {
					setTxtFields();
				}
				break;
			default:
			    currRow = fileView.indexOf(line);

		}
		//setFullLine();
	}
}