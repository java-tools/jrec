/*
 * @Author Bruce Martin
 * Created on 23/01/2007
 *
 * Purpose:
 * Display sorting options to the user
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Refactoring changes from moving classes to new packages, and
 *     creation of JRecord
 *
 * # Version 0.62 Bruce Martin 2007/04/30
 *   - adding support for enter key
 */
package net.sf.RecordEditor.edit.display.util;


import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LineCompare;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;

/**
 * Display sorting options to the user
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public final class SortFrame extends BaseFieldSelection {

    /**
     * Display sorting options to the user
     *
     * @param src Display source
     * @param fileTbl file to be sorted
     */
    public SortFrame(final AbstractFileDisplay src, final FileView fileTbl) {
    	this(src, fileTbl, "Sort Options", Common.ID_SORT_ICON, "Sort");
    }


    /**
     * Display sorting options to the user
     *
     * @param src Display source
     * @param fileTbl file to be sorted
     * @param id screen identifier
     */
    protected SortFrame(final AbstractFileDisplay src, final FileView fileTbl,
    		final String id, final int icondId, final String btnText) {
        super(src, fileTbl,  id, icondId, btnText, 2, false, ! fileTbl.getLayout().hasChildren(), EditorTask.TASK_SORT);
		super.setHelpURL(Common.formatHelpURL(Common.HELP_SORT));

		if (fileTbl.getRowCount() == 0) {
			super.doDefaultCloseAction();
			Common.logMsg("Nothing to sort", null);
			return;
		}

		if (fileTbl.getLayout().hasChildren()) {
			int idx = fileTbl.getLine(0).getPreferredLayoutIdx();

			for (int i =1; i < fileTbl.getRowCount(); i++) {
				if (idx != fileTbl.getLine(0).getPreferredLayoutIdx()) {
					Common.logMsg("You can only sort when all records are of the same type", null);
					super.doDefaultCloseAction();
					return;
				}
			}
			records.setSelectedIndex(idx);
			setFieldCombos(idx);
		}
    }


    /**
     * Get sort details
     * @param fieldList field index list
     * @param descending wether it is a descending (or ascending sort
     * @param layout Record Layout definition
     * @return
     */
    protected AbstractFileDisplay doAction(FileView view, int selection, AbstractFileDisplay src,
    		int[] fieldList, boolean[] descending, AbstractLayoutDetails layout) {

        if (selectWholeFile()) {
        	view.sort(new LineCompare(layout, selection,
                fieldList, descending));
        } else {
        	int[] selected = src.getSelectedRows();
        	if (selected != null && selected.length > 0) {
        		view.sort(selected, new LineCompare(layout, selection,
                        fieldList, descending));
        	}
        }
        return super.source;
    }
}
