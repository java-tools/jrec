package net.sf.RecordEditor.layoutEd.panels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.sf.RecordEditor.layoutEd.Record.ChildRecordsTblMdl;
import net.sf.RecordEditor.layoutEd.layout.tree.IntSelectionTest;
import net.sf.RecordEditor.layoutEd.layout.tree.RecordNode;
import net.sf.RecordEditor.layoutEd.layout.tree.SelectionTestNode;
import net.sf.RecordEditor.layoutEd.layout.tree.SelectionTreeTblMdl;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.utils.common.Common;

import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.ButtonTableRendor;
import net.sf.RecordEditor.utils.swing.ComboBoxRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeTable.JTreeTable;


public class SelectionTreeTablePnl implements IntRecordSelectPnl {
	public final BaseHelpPanel panel = new BaseHelpPanel();

	private JEditorPane tips = new JEditorPane(
			"text/html",
			"<h3>Selection Viewer</h3><br/> "
		  + "<b>Warning:</b> Changes made on this screen will not be reflected on other open screens<br/>"
		  + "and vice versa."

	);
	private final ChildRecordsTblMdl childMdl;
	private final int dbIdx;

	private SelectionTreeTblMdl treeTblMdl;
	private JTreeTable treeTbl;



	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

	private RecordNode[] nodes;

	public SelectionTreeTablePnl(int dbIdx, String dbName, ChildRecordsTblMdl childMdl) {
		super();
		this.childMdl = childMdl;
		this.dbIdx = dbIdx;

		init_100_SetupScreenFlds();
		init_200_LayoutPnl();

		init_300_table();
	}

	private void init_100_SetupScreenFlds() {
		buildTree();

		treeTblMdl = new SelectionTreeTblMdl(root, childMdl);
		treeTbl = new JTreeTable(treeTblMdl);


		treeTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		treeTbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		treeTbl.getTree().setRootVisible(false);
		treeTbl.getTree().setShowsRootHandles(true);

		expandTree();

		Common.calcColumnWidths(treeTbl, 3);
		treeTbl.addMouseListener(new MouseAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			 */
			@Override
			public void mousePressed(MouseEvent e) {
                int col = treeTbl.columnAtPoint(e.getPoint());
                int row = treeTbl.rowAtPoint(e.getPoint());
                if (treeTbl.getColumnModel().getColumn(col).getModelIndex() == 0) {
                	showSelectionEditor(row);
                } else {
                	super.mousePressed(e);
                }
			}

		});
	}

	private void init_200_LayoutPnl() {

		panel.addComponent(1, 5, SwingUtils.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));

		panel.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP1,
                BasePanel.FULL, BasePanel.FULL,
                treeTbl);
	}

	private void init_300_table() {


		TableColumn tc = treeTbl.getColumnModel().getColumn(0);
		tc.setCellRenderer(new ButtonTableRendor());
		tc.setMaxWidth(SwingUtils.TABLE_BUTTON_WIDTH);

		tc = treeTbl.getColumnModel()
				.getColumn(SelectionTreeTblMdl.SPECIAL_COLUMNS + IntSelectionTest.COLUMN_OPERATOR);

		tc.setCellRenderer(new ComboBoxRender(Common.COMPARISON_OPERATORS));
		tc.setCellEditor(new DefaultCellEditor(new JComboBox(Common.COMPARISON_OPERATORS)));

	}

	private void buildTree() {
		ChildRecordsRec rec;
		StringBuilder name;
		nodes = new RecordNode[childMdl.getRowCount()];
		for (int i = 0; i < childMdl.getRowCount(); i++) {
			rec = childMdl.getRecord(i);
			name = new StringBuilder();
			if (! "".equals(rec.getChildName())) {
				name.append(rec.getChildName())
				    .append(": ");
			}
			name.append(childMdl.getName4record(rec.getChildRecordId()));

			nodes[i] = new RecordNode(dbIdx, childMdl.getRecordId(), name.toString(), rec);
			root.add(nodes[i]);
		}
	}


	private void showSelectionEditor(int row) {
		Object n = getNodeForRow(row);
		RecordNode node = null;

		if (n instanceof RecordNode) {
			node = (RecordNode) n;
		} if (n instanceof SelectionTestNode) {
			node = (RecordNode) ((SelectionTestNode) n).getParent();
		}

		if (node != null) {
			save();
			new RecordSelectionFrm1("", dbIdx, childMdl.getRecordId(), node.getChildDef());
		}
	}


	protected final Object getNodeForRow(int row) {
		if (row >= 0) {
			TreePath treePath = treeTbl.getPathForRow(row);
			if (treePath != null) {
				return treePath.getLastPathComponent();
			}
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.panels.IntRecordSelectPnl#load()
	 */
	@Override
	public void load() {
		treeTblMdl.load();
		expandTree();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.panels.IntRecordSelectPnl#save(boolean)
	 */
	@Override
	public void save(boolean deleteBlanks) {
		save();
	}

	private void save() {
		childMdl.updateDB();

		for (RecordNode node : nodes) {
			node.update();
		}
	}

	private void expandTree() {

		for (int i = 0; i < treeTbl.getTree().getRowCount(); i++) {
			treeTbl.getTree().expandRow(i);
		}

	}
}
