/*
 *  This macro Displays duplicate records in a new view.
 *  It does this by:
 *  1)  Creating a new view
 *  2)  Create a LineCompare object (compare all fields)
 *  3)  Sorting the view using the LineCompare from step 2
 *  4)  Looping through the view removing a record if it is not the same as the previos record
 *      The lineCompare object is used to do the check
 * 
 */
    if (layout.getRecordCount() > 1) {
        RecordEditorData.showMessage("This macro only runs when there is only 1 Record Type");
    } else {
    	var tmpView = RecordEditorData.view.getView();
    	var lineCompare = RecordEditorData.getLineCompareAllFields(RecordEditorData.view.getLayout(), 0);
    	var delCount = 0;
    	var initialSize = tmpView.getRowCount();
    	var i = tmpView.getRowCount() - 2
    	  
    	tmpView.sort(lineCompare);
    	  
    	while (i >= 0) {
    	    if (lineCompare.compare(tmpView.getLine(i), tmpView.getLine(i+1)) == 0) {
    	   	i -= 1;
    	   	while (i >= 0 && lineCompare.compare(tmpView.getLine(i), tmpView.getLine(i+1)) == 0) {
    	   	    i -= 1;
    	   	}
    	    } else {
    	        tmpView.removeLineFromView(i+1);
    	    }
    	    i -= 1;
    	}
    	if (tmpView.getRowCount() > 1) {
    	    if (lineCompare.compare(tmpView.getLine(0), tmpView.getLine(1)) != 0) {
    	        tmpView.removeLineFromView(0);
    	    }
            var displayType = RecordEditorData.displayConstants.ST_LIST_SCREEN;
            var screen = RecordEditorData.displayView(displayType, "Duplicate-View", tmpView); 
   	}
    }
