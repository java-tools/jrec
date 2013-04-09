package net.sf.RecordEditor.edit.display.extension;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.swing.common.UpdatableItem;

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;


public class SplitPaneRecord implements TableModelListener {

	private FileView fileView;
	private int currRow = 0;

	private AbstractLine line;
	public final JXMultiSplitPane splitPane = new JXMultiSplitPane();


	private PaneDtls[] fields;
	private   double[] weight;

	private FocusAdapter focusListner = new FocusAdapter() {

		/* (non-Javadoc)
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent e) {
			if (line != null && fields != null && e != null && e.getComponent() != null) {
				for (PaneDtls p : fields) {
					if (e.getComponent() == p.fld) {
						updateRow(p, e.getComponent());
						break;
					}
				}
			}
		}
	};


	public SplitPaneRecord(FileView viewOfFile, int lineNo) {
		fileView = viewOfFile;
		currRow = lineNo;

		line = fileView.getLine(currRow);

		splitPane.setModel(new DefaultSplitPaneModel());

		fileView.addTableModelListener(this);
	}



	public void layoutFieldPane() {

		//ArrayList<PaneDtls> panes = new ArrayList<PaneDtls>();
		int idx;

		Split layoutDef = new Split();

		ArrayList<ArrayList<MultiSplitLayout.Node>> rows = new ArrayList<ArrayList<MultiSplitLayout.Node>>();
		rows.add(new ArrayList<MultiSplitLayout.Node>(fields.length * 2));
		ArrayList<MultiSplitLayout.Node> nodes = new ArrayList<MultiSplitLayout.Node>(fields.length);


		setTxtFields();

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isVisible()) {
				idx = fields[i].col;
				while (rows.size() <= idx) {
					rows.add(new ArrayList<MultiSplitLayout.Node>(fields.length));
				}
				if (rows.get(idx).size() > 0) {
					rows.get(idx).add(new MultiSplitLayout.Divider());
				}

				MultiSplitLayout.Leaf l = new MultiSplitLayout.Leaf(fields[i].name);
				rows.get(idx).add(l);
				nodes.add(l);
				if (fields[i].weight > 0) {
					l.setWeight(fields[i].weight);
				}
			}
		}

		switch (nodes.size()) {
		case 0:
			splitPane.setLayout(new MultiSplitLayout(new MultiSplitLayout.Leaf("0")));
			splitPane.add(new JPanel(), "0");
			break;
		case 1:
			splitPane.setLayout(new MultiSplitLayout(nodes.get(0)));
			break;
		default:
			for (int i = rows.size()-1; i >= 0; i--) {
				if (rows.get(i).size() == 0) {
					rows.remove(i);
				}
			}

			if (rows.size() == 1) {
				layoutDef.setRowLayout(false);
				layoutDef.setChildren(rows.get(0));
			} else {
				ArrayList<MultiSplitLayout.Node> rowDefs = new ArrayList<MultiSplitLayout.Node>(rows.size() * 2);
				Split rowDef = new Split();

				for (int i = 0; i < rows.size(); i++) {
					if (rowDefs.size() > 0) {
						rowDefs.add(new MultiSplitLayout.Divider());
					}
					switch (rows.get(i).size()) {
					case 0: break;
					case 1:
						rowDefs.add(rows.get(i).get(0));
						if (weight != null && i < weight.length && weight[i] > 0) {
							rows.get(i).get(0).setWeight(weight[i]);
						}
						break;
					default:
						rowDef = new Split();

						rowDef.setRowLayout(false);
						rowDef.setChildren(rows.get(i));

						if (weight != null && i < weight.length && weight[i] > 0) {
							rowDef.setWeight(weight[i]);
						}
						rowDefs.add(rowDef);
					}
				}

				layoutDef.setRowLayout(true);
				layoutDef.setChildren(rowDefs);
			}

			splitPane.setLayout(new MultiSplitLayout(layoutDef));
		}
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isVisible()) {
				splitPane.add(fields[i].getDisplayPane(), fields[i].name);
			}
		}
	}


	/**
	 * @return the line
	 */
	public AbstractLine getLine() {
		return line;
	}



	/**
	 * @return the currRow
	 */
	public int getCurrRow() {
		return currRow;
	}


	public void setCurrRow(int newRow) {

		if ((newRow >= 0)) {
			if (line != null && fields != null) {
				for (PaneDtls p : fields) {
					if (p.txtFld != null && p.txtFld.hasFocus() && p.fieldDef != null && ! p.isHtml) {
						try {
							line.setField(0, p.fieldDef.fieldIdx, p.txtFld.getText());
							fileView.fireRowUpdated(currRow, line);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						break;
					}
				}
			}
			line = fileView.getLine(newRow);
			if (currRow != newRow) {
				currRow = newRow;
				rowChanged();
			}
		}
	}




	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
		setCurrRow(newRow);
	}


	private void rowChanged() {
		if (currRow < 0) {
			currRow = 0;
		} else if (currRow >= fileView.getRowCount()) {
			currRow = fileView.getRowCount() - 1;
		}

		line = fileView.getLine(currRow);

		setTxtFields();
		//this.splitPane.repaint();
		this.splitPane.revalidate();
	}

	/**
	 * Set the text fields
	 */
	public final void setTxtFields() {

		String s;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].txtFld != null && fields[i].fieldDef != null) {
				s = getFieldVal(fields[i].fieldDef);
				if (fields[i].isHtml && ! Conversion.isHtml(s)) {
					s = "";
				}
				fields[i].txtFld.setText(s);
			} else if (fields[i].fld instanceof UpdatableItem) {
				((UpdatableItem) fields[i].fld).setValue(line.getField(0,fields[i].fieldDef.fieldIdx));
			}
		}
	}

	/**
	 * Define the fields by Array
	 *
	 * @param newFields the fields to set
	 */
	public void setFields(PaneDtls[] newFields, double[] newWeight) {
		if (fields != null) {
			for (PaneDtls p : fields) {
				if (p.fld != null && p.fieldDef != null  && ! p.isHtml) {
					p.fld.removeFocusListener(this.focusListner);
				}
			}
		}

		this.fields = newFields;
		this.weight = newWeight;

		for (PaneDtls p : newFields) {
			if (p.fld != null && p.fieldDef != null && ! p.isHtml) {
				p.fld.addFocusListener(this.focusListner);
			}
		}
	}

	public final String getFieldVal(FieldDef fld) {
		String s = "";
		Object o = line.getField(0, fld.fieldIdx);

		if (o != null) {
			s = o.toString();
		}

		return s;
	}


	public final boolean[] getFieldVisibility(int recordIndex) {
		if (recordIndex == 0 && line != null) {
			boolean[] b = new boolean[line.getLayout().getRecord(0).getFieldCount()];
			for (int i = 0; i < b.length; i++) {
				b[i] = false;
			}

			for (PaneDtls p : fields) {
				if (p.fieldDef != null && (! p.isHtml) && p.fieldDef.fieldIdx >= 0) {
					if (p.isVisible()) {
						b[p.fieldDef.fieldIdx] = true;
					}
				}
			}
			return b;
		}
		return null;
	}

	public final void setFieldVisibility(int recordIndex, boolean[] fieldVisibility) {

		if (recordIndex == 0 && line != null) {
			for (PaneDtls p : fields) {
				if (p.fieldDef.fieldIdx >= 0) {
					p.setVisible(fieldVisibility[p.fieldDef.fieldIdx]);
				}
//				if (p.fieldDef != null && ! p.isHtml) {
//					MultiSplitLayout.Node n = splitPane.getMultiSplitLayout().getNodeForName(p.name);
//					if (n != null) {
//						n.setVisible(fieldVisibility[p.fieldDef.fieldIdx]);
//					}
//				}
			}
			splitPane.removeAll();

			layoutFieldPane();
			splitPane.revalidate();
		}
	}
//
//	public final AbstractLine getInsertAfterLine(boolean prev) {
//
//		if (prev) {
//			if (currRow > 0) {
//				return fileView.getLine(currRow - 1);
//			}
//			return null;
//		}
//		return fileView.getLine(currRow);
//	}

	public final void flush() {
		for (PaneDtls p : fields) {
			if (p.fld != null && p.fld.hasFocus()) {
				updateRow(p, p.fld);
			}
		}
	}

	private void updateRow(PaneDtls p, Object component) {
		try {
			Object val;
			Object oldVal = line.getField(0, p.fieldDef.fieldIdx);
			if (component == p.txtFld && p.fieldDef != null && ! p.isHtml) {
				val = p.txtFld.getText();
			} else if (p.fld instanceof UpdatableItem) {
				val = ((UpdatableItem) p.fld).getValue();
			} else {
				return;
			}
			if (val == oldVal
			||  (val != null && val.equals(oldVal))) {

			} else {
				line.setField(0, p.fieldDef.fieldIdx, val);
				fileView.setChanged(true);
				fileView.fireRowUpdated(currRow, line);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

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
				//System.out.println(" ~~~~~~~~ " + currRow + " " + event.getLastRow() + " " + event.getFirstRow());
				if (currRow > event.getLastRow()) {
					currRow -= event.getLastRow() - event.getFirstRow() + 1;
				//	rowChanged();
				} else if (currRow > event.getFirstRow()) {
					currRow -= Math.min(fileView.getRowCount(), event.getFirstRow());
				//	rowChanged();
				}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						rowChanged();
					}
				});
				break;
			case (TableModelEvent.UPDATE):
				if (event.getFirstRow() <= currRow && event.getLastRow() >= currRow) {
					setTxtFields();
				}
				break;
			default:
			    currRow = fileView.indexOf(line);
			    rowChanged();
		}
		//setFullLine();
	}
}