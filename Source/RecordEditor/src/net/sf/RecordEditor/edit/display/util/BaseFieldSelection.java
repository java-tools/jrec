package net.sf.RecordEditor.edit.display.util;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.edit.display.models.SortFieldMdl;
import net.sf.RecordEditor.edit.util.ReMessages;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.jibx.compare.SortTree;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.IChildDisplay;
import net.sf.RecordEditor.re.display.IUpdateExecute;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.FieldSummaryDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.ComboBoxRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.saveRestore.ISaveUpdateDetails;
import net.sf.RecordEditor.utils.swing.saveRestore.SaveLoadPnl;

@SuppressWarnings("serial")
public abstract class BaseFieldSelection extends ReFrame
implements ListSelectionListener, ISaveUpdateDetails<EditorTask>, IUpdateExecute<EditorTask>, IChildDisplay {

//	protected static final int RECORD_LIST_HEIGHT = SwingConstants.TABLE_ROW_HEIGHT * 18;
//	protected static final int FIELD_TABLE_HEIGHT = SwingConstants.TABLE_ROW_HEIGHT * 8;
	protected static final int FIELD_TABLE_SIZE = 5;
	private static final String[] WHAT_TO_SORT = LangConversion.convertComboItms(
			"File SelectionOptions",
			new String[] {"Whole View", "Selected Records"});
	private BaseHelpPanel pnlTop = new BaseHelpPanel();
	private BaseHelpPanel pnlBottom = new BaseHelpPanel();
	@SuppressWarnings("rawtypes")
	protected JList records;
	protected SortFieldMdl model = new SortFieldMdl(FIELD_TABLE_SIZE);
	protected JTable fldTable, fldSummaryTbl;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel[] fldModel = {
	            new DefaultComboBoxModel(), new DefaultComboBoxModel()
	    };
	protected JComboBox whatToSelect = new JComboBox(WHAT_TO_SORT);
	protected JButton executeBtn = new JButton();
	protected FileView fileView;
	protected int lastSelection = 0;
	protected final AbstractFileDisplay source;
	protected SortFieldSummaryMdl summaryMdl;

	private SaveLoadPnl<EditorTask> saveLoadPnl = new SaveLoadPnl<EditorTask>(
			this, Parameters.getFileName(Parameters.SORT_TREE_SAVE_DIRECTORY), EditorTask.class);
//	private SaveButton<EditorTask> saveBtn
//				= new SaveButton<EditorTask>(this,
//						Parameters.getFileName(Parameters.SORT_TREE_SAVE_DIRECTORY));

	private final String saveId;

	private KeyAdapter listner = new KeyAdapter() {
	        /**
	         * @see java.awt.event.KeyAdapter#keyReleased
	         */
	        public final void keyReleased(KeyEvent event) {

	        	switch (event.getKeyCode()) {
	        	case KeyEvent.VK_ENTER:		doAction();										break;
	        	case KeyEvent.VK_ESCAPE:	BaseFieldSelection.this.doDefaultCloseAction();	break;
	        	}
	        }
	};


	/**
	 * Display sorting options to the user
	 *
	 * @param src Display source
	 * @param fileTbl file to be sorted
	 * @param id screen identifier
	 */
	public BaseFieldSelection(final AbstractFileDisplay src, final FileView fileTbl,
			final String id, final int icondId, final String btnText, final int columnCount,
			final boolean addFieldSummary, final boolean showRecordList, String saveIdentifier) {
		super(fileTbl.getFileNameNoDirectory(), id,
				fileTbl.getBaseFile());
		Rectangle screenSize = ReMainFrame.getMasterFrame().getDesktop().getBounds();
		saveId = saveIdentifier;


		int recCount, height;

		int desktopHeight = screenSize.height - SwingUtils.COMBO_TABLE_ROW_HEIGHT * 6;
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.setBorder(BorderFactory.createEmptyBorder());
		pnlTop.setBorder(BorderFactory.createEmptyBorder());
		pnlBottom.setBorder(BorderFactory.createEmptyBorder());

		super.addCloseOnEsc(pnlTop);
		super.addCloseOnEsc(pnlBottom);



		source   = src;
		fileView = src.getFileView();
		recCount = fileView.getLayout().getRecordCount();
		//helpPresent = true;

		model.setColumnCount(columnCount);

		//System.out.println("==> column count " + columnCount);

		init(icondId, btnText);

		pnlTop.registerComponent(pnl);
		if (showRecordList && recCount > 1) {
			height = SwingUtils.calculateTableHeight(recCount, desktopHeight * 3 / 10);
			desktopHeight -= height;
			pnlTop.addComponent(1, 5,
					height,
					BasePanel.GAP0,
					BasePanel.FULL, BasePanel.FULL,
					new JScrollPane(records));
			pnlTop.setComponentName(records, "records");
		}

		if (addFieldSummary) {
			height = SwingUtils.calculateComboTableHeight(fldTable.getRowCount(), desktopHeight * 7 / 20);
			desktopHeight -= height;
			pnlTop.addComponent(1, 5, height, BasePanel.GAP0,
					BasePanel.FULL, BasePanel.FULL,
					fldTable);

			height = SwingUtils.calculateComboTableHeight(fldSummaryTbl.getRowCount(), desktopHeight * 8 / 10);
			pnlTop.addComponent(1, 5, height, BasePanel.GAP,
					BasePanel.FULL, BasePanel.FULL,
					fldSummaryTbl);

			pnlTop.setComponentName(fldSummaryTbl, "fieldSummary");
		} else {
			height = SwingUtils.calculateComboTableHeight(fldTable.getRowCount(), desktopHeight * 4 / 5);
			pnlTop.addComponent(1, 5, height, BasePanel.GAP,
					BasePanel.FULL, BasePanel.FULL,
					fldTable);
		}
		pnlTop.setComponentName(fldTable, "fields");

		pnlBottom.addLine("Use", whatToSelect);
		pnlBottom.setGap(BasePanel.GAP0);

		JPanel p = new JPanel();
		p.add(executeBtn);
		pnlBottom.addLine("", saveLoadPnl.panel, p);
		pnlBottom.setGap(3);

		pnl.add(BorderLayout.CENTER, new JScrollPane(pnlTop));
		pnl.add(BorderLayout.SOUTH, pnlBottom);
		this.addMainComponent(pnl);

		setBounds(getY(), getX(), Math.min(getWidth() + 25, screenSize.width -10),
		        Math.min(getHeight(), screenSize.height - 5));

		this.setVisible(true);
		setToMaximum(false);
		super.addCloseOnEsc(pnlTop);
	}

	/**
	 * Initialis screen fields
	 *
	 */
	protected void init(final int icondId, String btnText) {
	    int i;
	    TableColumnModel tcm;
	    TableColumn tc;
	    AbstractLayoutDetails layout = fileView.getLayout();
	    String[] recordName = new String[layout.getRecordCount()];
	    JComboBox fieldList;
	    JComboBox OperatorList = new JComboBox(FieldSummaryDetails.FOREIGN_OPERATOR_NAMES);

		summaryMdl = new SortFieldSummaryMdl(fileView.getLayout());
		fldSummaryTbl = new JTable(summaryMdl);

	    fldTable = new JTable(model);

	    for (i = 0; i < layout.getRecordCount(); i++) {
	        recordName[i] = layout.getRecord(i).getRecordName();
	    }
	    records = new JList(recordName);
	    records.addListSelectionListener(this);

	    pnlTop.addReKeyListener(listner);

	    fieldList = new JComboBox(fldModel[1]);
	    fldTable.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);
	    tcm = fldTable.getColumnModel();
		tc = tcm.getColumn(0);
		tc.setCellRenderer(new ComboBoxRender(fldModel[0]));
		tc.setCellEditor(new DefaultCellEditor(fieldList));

		if (model.getColumnCount() > 1) {
			tc = tcm.getColumn(1);
			tc.setCellRenderer(new CheckBoxTableRender());
			tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));
		}

		fldSummaryTbl.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);
	    tcm = fldSummaryTbl.getColumnModel();
		tc = tcm.getColumn(1);
		tc.setCellRenderer(new ComboBoxRender((new JComboBox(FieldSummaryDetails.FOREIGN_OPERATOR_NAMES)).getModel()));
		tc.setCellEditor(new DefaultCellEditor(OperatorList));

		//if (helpPresent) {
		pnlTop.registerComponent(fieldList);
		//}

	    setFieldCombos(lastSelection);
	    records.setSelectedIndex(lastSelection);

	    executeBtn.setAction(new ReAbstractAction(btnText, icondId) {
	        public void actionPerformed(ActionEvent e) {
	            doAction();
	        }
	    });
	}

	/**
	 * Set the Table Field Combo models up
	 * @param index index of the new record
	 */
	protected final void setFieldCombos(int index) {
	    int i, j;
	    AbstractRecordDetail rec = fileView.getLayout().getRecord(index);

	    lastSelection = index;

	    for (i = 0; i < fldModel.length; i++) {
	        fldModel[i].removeAllElements();
	        fldModel[i].addElement(" ");
	        for (j = 0; j < rec.getFieldCount(); j++) {
	            fldModel[i].addElement(rec.getField(j).getName());
	        }
	    }
	    model.resetFields();

	    summaryMdl.setRecordIndex(index);
	}

    /**
     * Sort the file
     *
     */
    public final AbstractFileDisplay doAction() {
        int numSortFields = getNumberOfSortFields();
        AbstractFileDisplay ret = null;

        if (numSortFields > 0) {
            int[] fieldList = new int[numSortFields];
            boolean[] descending = new boolean[numSortFields];;
            AbstractLayoutDetails layout = fileView.getLayout();

            String s;
            AbstractRecordDetail record = layout.getRecord(lastSelection);
            int j = 0;

            for (int i = 0; i < FIELD_TABLE_SIZE; i++) {
                s = model.getFieldName(i);
                if (s != null && ! "".equals(s.trim())) {
                    descending[j]  = ! model.getAscending(i);
                    fieldList[j++] = record.getFieldIndex(model.getFieldName(i));
                }
            }
            ret = doAction(fileView, lastSelection, source, fieldList, descending, layout);
        }

        try {
            this.setClosed(true);
        } catch (Exception e) {
        }

        return ret;
    }


    /**
     * Execute required action
     *
     * @param view file view to use
     * @param recordIndex record index
     * @param src source (or calling screen)
     * @param fieldList list of fields to use
     * @param descending wether to use descending sequence
     * @param layout record layout
     */
	protected abstract AbstractFileDisplay doAction(FileView view, int recordIndex, AbstractFileDisplay src,
    		int[] fieldList, boolean[] descending, AbstractLayoutDetails layout);



	/**
	 * Get the number of sort fields
	 * @return number of sort fields
	 */
	protected int getNumberOfSortFields() {
		int numSortFields = 0;
		String s;
	    for (int i = 0; i < FIELD_TABLE_SIZE; i++) {
	        s = model.getFieldName(i);
	        if (s != null && ! "".equals(s.trim())) {
	            numSortFields += 1;
	        }
	    }

	    return numSortFields;
	}

	protected FileView getNewView() {
		FileView view = source.getFileView();
    	FileView newView = null;

        if (selectWholeFile()) {
        	newView = view.getView();
        } else {
        	int[] selected = source.getSelectedRows();

        	if (selected != null && selected.length > 0) {
               	newView = view.getView(selected);
        	}
        }
        return newView;
	}

	protected boolean selectWholeFile() {
		return whatToSelect.getSelectedIndex() == 0;
	}

	/**
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
	    int idx = records.getSelectedIndex();
	    if (lastSelection != idx && idx >= 0) {
	        setFieldCombos(idx);
	    }
	}


	/**
	 *  Execute standard RecordEditor actions
	 *
	 * @param action action to perform
	 */
	public void executeAction(int action) {
		if (action == ReActionHandler.HELP) {
		    pnlTop.showHelp();
		} else {
			super.executeAction(action);
		}
	}

	/**
	 * Check if action is available
	 *
	 * @param action action to be checked
	 *
	 * @return wether action is available
	 */
	public boolean isActionAvailable(final int action) {
		return  (action == ReActionHandler.HELP) // && helpPresent)
		    ||  super.isActionAvailable(action);
	}

	/**
	 * get the sort Tree definition (for saving as XML)
	 * @return sort Tree definition
	 */
	public final EditorTask getSaveDetails() {
		SortTree sortTree = new SortTree();
		Object rn = records.getSelectedValue();

		if (rn != null) {
			sortTree.recordName = rn.toString();
			sortTree.sortFields = model.getSortFields();
			sortTree.sortSummary = summaryMdl.getFieldSummary().getSummary();
			return (new EditorTask())
					.setSortTree(saveId, fileView.getLayout().getLayoutName(), sortTree);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.saveRestore.IUpdateDetails#update(java.lang.Object)
	 */
	@Override
	public void update(EditorTask serialisedData) {

		if (serialisedData == null || serialisedData.sortTree == null) {
			Common.logMsgRaw(ReMessages.NOT_A_SORT.get(), null);
		} else {
			setFromSavedDetails(serialisedData);
			model.fireTableDataChanged();
			if (summaryMdl !=null) {
				summaryMdl.fireTableDataChanged();
			}
		}
	}

	/**
	 * @see net.sf.RecordEditor.re.display.IChildDisplay#getSourceDisplay()
	 * @return the source
	 */
	public AbstractFileDisplay getSourceDisplay() {
		return source;
	}

	/**
	 * Restore saved details
	 * @param details being restored
	 */
	@Override
	public void setFromSavedDetails(EditorTask details) {
		int idx = fileView.getLayout().getRecordIndex(details.sortTree.recordName);

		model.setSortTree(details.sortTree.sortFields);

		if (idx >= 0) {
			records.setSelectedIndex(idx);

			summaryMdl.getFieldSummary().setSummary(idx, details.sortTree.sortSummary);
		}
	}

	/**
	 * @param helpUrl
	 * @see net.sf.RecordEditor.utils.swing.BaseHelpPanel#setHelpURL(java.lang.String)
	 */
	public final void setHelpURL(URL helpUrl) {
		pnlTop.setHelpURL(helpUrl);
		pnlBottom.setHelpURL(helpUrl);
	}
}