package net.sf.RecordEditor.utils.charsets;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.ButtonTableRendor;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class CharsetSelection {

	private static String[] COLUMN_NAMES = {"", "Character set", "Description"};
	
	private String charset;
	private JDialog dialog;
	private BaseHelpPanel pnl = new BaseHelpPanel();
	private TblMdl tblMdl = new TblMdl();

	private JTextField charsetTxt = new JTextField();
	
	private ButtonTableRendor tableBtn = new ButtonTableRendor();
	private JTable tbl = new JTable(tblMdl);
	//private JButton goBtn = new JButton("Go");
	
	public CharsetSelection(Frame owner, String characterSet) {
		this.charset = characterSet;
		this.charsetTxt.setText(charset);

        TableColumn tc = tbl.getColumnModel().getColumn(0);
        tc.setCellRenderer(tableBtn);
        tc.setPreferredWidth(5);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(16 * SwingUtils.CHAR_FIELD_WIDTH);
        tbl.getColumnModel().getColumn(2).setPreferredWidth(59 * SwingUtils.CHAR_FIELD_WIDTH);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		pnl.addLineRE("Selected Characterset", charsetTxt)
			.setGapRE(BasePanel.GAP1);
		
	    pnl.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP,
                   BasePanel.FULL, BasePanel.FULL,
                   tbl);
	   
	
		dialog = new JDialog(owner, true);
		dialog.getContentPane().add(pnl);
		
		charsetTxt.setEditable(false);
		
		tbl.addMouseListener(new MouseAdapter() {
	           @Override public void mousePressed(MouseEvent m) {
	                int row = tbl.rowAtPoint(m.getPoint());

	                accept(tblMdl.charsets.get(row).id);
	           }
		});
        
		pnl.done();

        dialog.pack();
        dialog.setVisible(true);
	}
	
	private void accept(String c) {
		charset = c;
		dialog.setVisible(false);
	}
	
	/**
	 * @return the charset
	 */
	public final String getCharset() {
		return charset;
	}

	@SuppressWarnings("serial")
	private static class TblMdl extends AbstractTableModel {

		private List<CharsetDtls> charsets = CharsetMgr.getCharsets();
		
		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return charsets.size();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 3;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0: return "";
			case 1:	return charsets.get(rowIndex).id;
			default:
				return charsets.get(rowIndex).description;
			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return COLUMN_NAMES[column];
		}
		
	}
}
