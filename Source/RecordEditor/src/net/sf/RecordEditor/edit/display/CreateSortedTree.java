package net.sf.RecordEditor.edit.display;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LineCompare;
import net.sf.RecordEditor.edit.display.util.BaseFieldSelection;
import net.sf.RecordEditor.edit.open.DisplayBuilderFactory;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
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
	public CreateSortedTree(AbstractFileDisplay src, @SuppressWarnings("rawtypes") FileView view) {
		super(src, view, "Create Sorted Tree", Common.ID_SORT_SUM_ICON, "Build Tree", 2, true, true);

		super.setHelpURL(Common.formatHelpURL(Common.HELP_SORT_TREE));
	}

	/**
	 *
	 * @see net.sf.RecordEditor.edit.display.util.SortFrame#doAction(net.sf.RecordEditor.re.file.FileView, int, net.sf.RecordEditor.re.script.AbstractFileDisplay, int[], boolean[], net.sf.JRecord.Details.LayoutDetail)
	 */
	@SuppressWarnings("rawtypes")
	@Override
    protected void doAction(FileView view, int recordIndex, AbstractFileDisplay src,
    		int[] fieldList, boolean[] descending, AbstractLayoutDetails layout) {

		FileView newView = getNewView();

        if (newView != null) {
        	TreeParserField parser = new TreeParserField(recordIndex, fieldList, summaryMdl.getFieldSummary());

            newView.sort(new LineCompare(layout, recordIndex,
                    fieldList, descending));

            DisplayBuilderFactory.newLineTree(getSource().getParentFrame(), newView, parser, false, 0);
        }
    }
}
