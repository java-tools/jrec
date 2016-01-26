package net.sf.RecordEditor.edit.display;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LineCompare;
import net.sf.RecordEditor.edit.display.util.BaseFieldSelection;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.TreeParserField;
import net.sf.RecordEditor.utils.common.Common;


/**
 *
 *  Create a sorted tree view based on user field selection
 *
 */
@SuppressWarnings("serial")
public class CreateSortedTree extends BaseFieldSelection {


	/**
	 * Create a sorted tree view based on user field selection
	 * @param src calling form
	 * @param view file view
	 */
	public CreateSortedTree(AbstractFileDisplay src, FileView view) {
		super(src, view, "Create Sorted Tree", Common.ID_SORT_SUM_ICON, "Build Tree", 2, true, true, EditorTask.TASK_SORT_TREE);

		super.setHelpURL(Common.formatHelpURL(Common.HELP_SORT_TREE));
	}

	/**
	 *
	 * @return
	 * @see net.sf.RecordEditor.edit.display.util.SortFrame#doAction(net.sf.RecordEditor.re.file.FileView, int, net.sf.RecordEditor.re.display.AbstractFileDisplay, int[], boolean[], net.sf.JRecord.Details.LayoutDetail)
	 */
	@Override
    protected AbstractFileDisplay doAction(FileView view, int recordIndex, AbstractFileDisplay src,
    		int[] fieldList, boolean[] descending, AbstractLayoutDetails layout) {

		FileView newView = getNewView();

        if (newView != null) {
        	TreeParserField parser = new TreeParserField(recordIndex, fieldList, summaryMdl.getFieldSummary());

            newView.sort(
            		new LineCompare(layout, recordIndex, fieldList, descending));

            return DisplayBuilderFactory.newLineTree(getSourceDisplay().getParentFrame(), newView, parser, false, 0);
        }

        return null;
    }
}
