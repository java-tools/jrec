/*

   Sample Javascript macro executing a sort tree

*/

if (layout.getRecord(0).getRecordName() == "DTAR020"
||  layout.getRecord(0).getRecordName() == "GeneratedCsvRecord") {
    RecordEditorData.executeSavedTask(
            RecordEditorData.executeConstansts.sortDir /*"SortDir"*/, 
    	    "Script_DTAR020_SortTree_1.xml");
} else {
    RecordEditorData.showMessage(
    	      "Script only works on DTAR020 files and not " 
    	    + layout.getRecord(0).getRecordName());
}
