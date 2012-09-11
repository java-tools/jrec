package net.sf.RecordEditor.po.display;


import javax.swing.event.TableModelListener;

import net.sf.RecordEditor.edit.display.AbstractCreateChildScreen;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.display.common.AbstractRowChangedListner;
import net.sf.RecordEditor.edit.display.extension.EditPaneListScreen;
import net.sf.RecordEditor.po.def.PoLayoutMgr;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;


public class PoList extends EditPaneListScreen
implements AbstractRowChangedListner, TableModelListener, AbstractFileDisplayWithFieldHide, AbstractCreateChildScreen {

	protected static final boolean[] DEFAULT_FIELD_VISIBILITY;

    static {
    	boolean[] defaultFieldVisibility;
    	try {
    		defaultFieldVisibility= new boolean[PoLayoutMgr.PO_LAYOUT.getRecord(0).getFieldCount()];
    		for (int i = 0; i < defaultFieldVisibility.length; i++) {
    			defaultFieldVisibility[i] = (i >= 1 && i <= 3);
    		}
    	} catch (Exception e) {
			e.printStackTrace();
			defaultFieldVisibility = new boolean[0];
		}

    	DEFAULT_FIELD_VISIBILITY = defaultFieldVisibility;
    }



	public PoList(@SuppressWarnings("rawtypes") FileView viewOfFile, boolean primary) {
		super("PO List", viewOfFile, primary, false, false, false, false,
				NO_LAYOUT_LINE);
//		TableColumnModel mdl = getJTable().getColumnModel();
//
//		for (int i = 0; i < mdl.getColumnCount(); i++) {
//			if (mdl.getColumn(i).getModelIndex() == PoField.msgstrPlural.fieldIdx) {
//				mdl.getColumn(i).setCellRenderer(new ArrayRender());
//				mdl.getColumn(i).setCellEditor(new ArrayTableEditor());
//				break;
//			}
//		}
		setFieldVisibility(0, DEFAULT_FIELD_VISIBILITY);
        //getJTable().setDefaultRenderer(ArrayInterface.class, new ArrayRender());
        //getAlternativeTbl().setDefaultRenderer(ArrayInterface.class, new ArrayRender());
	}


    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.AbstractRowChangedImplementor#createChildScreen()
	 */
	@Override
	public AbstractFileDisplay createChildScreen(int position) {
		if (childScreen != null) {
			removeChildScreen();
		}

		currChildScreenPosition = position;
		if (position == CS_RIGHT) {
			childScreen =  PoChildRecordScreen.newRightHandScreen(fileView, Math.max(0, getCurrRow()));
		} else {
			childScreen = PoChildRecordScreen.newBottomScreen(fileView, Math.max(0, getCurrRow()));
		}
		setKeylistner(tblDetails);
		setKeylistner(getAlternativeTbl());

		return childScreen;
	}


	@Override
	protected BaseDisplay getNewDisplay(@SuppressWarnings("rawtypes") FileView view) {
		return new PoList(view, false);
	}

	public int getAvailableChildScreenPostion() {
		return CS_BOTH;
	}
}
