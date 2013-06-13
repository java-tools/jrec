package net.sf.RecordEditor.po.display;


import javax.swing.event.TableModelListener;

import net.sf.RecordEditor.edit.display.AbstractCreateChildScreen;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.common.AbstractRowChangedListner;
import net.sf.RecordEditor.edit.display.extension.EditPaneListScreen;
import net.sf.RecordEditor.po.def.PoLayoutMgr;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;


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



	public PoList(FileView viewOfFile, boolean primary) {
		this("PO List", viewOfFile, primary);
	}


	public PoList(String name, FileView viewOfFile, boolean primary) {
		super(name, viewOfFile, primary, false, false, false, false, NO_LAYOUT_LINE);
		setFieldVisibility(0, DEFAULT_FIELD_VISIBILITY);

		ReAbstractAction duplicateAction = DuplicateFilter.getDuplicateAction(this);
		super.mainPopup.getPopup().addSeparator();
		super.mainPopup.getPopup().add(duplicateAction);
		super.fixedPopupMenu.getPopup().addSeparator();
		super.fixedPopupMenu.getPopup().add(duplicateAction);
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
			childScreen =  PoChildRecordScreen.newRightHandScreen(getJTable(), fileView, Math.max(0, getCurrRow()));
		} else {
			childScreen = PoChildRecordScreen.newBottomScreen(getJTable(), fileView, Math.max(0, getCurrRow()));
		}
		setKeylistner(tblDetails);
		setKeylistner(getAlternativeTbl());

		return childScreen;
	}


	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		return new PoList(view, false);
	}

	public int getAvailableChildScreenPostion() {
		return CS_BOTH;
	}
}
