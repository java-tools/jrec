/*

     This macro will create a view for each RecordType
     and display the appropriate lines in the view


*/

    if (layout.getRecordCount() < 2) {
	RecordEditorData.showMessage("This macro only makes sense when there are > 1 Record Type");
    } else if (RecordEditorData.view.getRowCount() < 2) {
	RecordEditorData.showMessage("This macro only makes sense when there are > 1 line in the View");
    } else{
        var recordArray = new Array();
        for (i = 0; i < layout.getRecordCount(); i++) {
            recordArray[i] = RecordEditorData.getNewLineList();	
        }
        
        for (i = 0; i < RecordEditorData.view.getRowCount(); i++) {
            line = RecordEditorData.view.getLine(i);
            recordArray[line.getPreferredLayoutIdx()].add(line);
        }
        
        var displayType = RecordEditorData.displayConstants.ST_LIST_SCREEN;
        for (i = 0; i < layout.getRecordCount(); i++) {
           if (recordArray[i].size() > 0) {
                var displayType = RecordEditorData.displayConstants.ST_LIST_SCREEN;
                if (recordArray[i].size() == 1) {
                    displayType = RecordEditorData.displayConstants.ST_RECORD_SCREEN;
                }
       	        var screen = RecordEditorData.displayList(displayType, layout.getRecord(i).getRecordName(), recordArray[i]);
       	    }
        }
    }