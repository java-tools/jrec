/*
 *  This macro deletes "Dulicate records from the Current view.
 *  It does this by:
 *  1)  Creating a new temporary view
 *  2)  Create a LineCompare object (compare all fields)
 *  3)  Sorting the temporary view using the LineCompare from step 2
 *  4)  Looping through the temporary view deleting a record if it is the same as the previos record
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
    	  
    	tmpView.sort(lineCompare);
    	  
    	for (i = tmpView.getRowCount() - 2; i >= 0; i--) {
    	    if (lineCompare.compare(tmpView.getLine(i), tmpView.getLine(i+1)) == 0) {
    	   	tmpView.deleteLine(i+1);
    	   	delCount = delCount + 1;
    	    }
    	}

        RecordEditorData.showMessage(
         	           "Was " + initialSize + " Deleted " 
                              + delCount + " Records; Now " + tmpView.getRowCount());
    }
