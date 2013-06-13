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
package net.sf.RecordEditor.edit.display;


import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.util.BaseFieldSelection;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.TreeParserField;
import net.sf.RecordEditor.utils.common.Common;

/**
 * Display sorting options to the user
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class CreateFieldTree extends BaseFieldSelection {

    /**
     * Display sorting options to the user
     *
     * @param src Display source
     * @param fileTbl file to be sorted
     */
    public CreateFieldTree(final AbstractFileDisplay src, final FileView fileTbl) {
    	super(src, fileTbl, "Create Field Tree View", Common.ID_SUMMARY_ICON, "Build Tree",
    			1, true, true, EditorTask.TASK_FIELD_TREE);
		super.setHelpURL(Common.formatHelpURL(Common.HELP_FIELD_TREE));
    }


    /**
     * Get sort details
     * @param fieldList field index list
     * @param descending wether it is a descending (or ascending sort
     * @param layout Record Layout definition
     * @return
     */
    protected AbstractFileDisplay doAction(FileView view, int recordIndex, AbstractFileDisplay src,
    		int[] fieldList, boolean[] descending, AbstractLayoutDetails layout) {

    	FileView newView = getNewView();

        if (newView != null) {
        	TreeParserField parser = new TreeParserField(recordIndex, fieldList, summaryMdl.getFieldSummary());

        	return DisplayBuilderFactory.newLineTree(getSourceDisplay().getParentFrame(), newView, parser, false, 0);
        }
        return null;
    }
}
