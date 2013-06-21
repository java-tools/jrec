/*

   Sample Javascript RecordEditor macro to
   1) Create a view of records where Quantity < 0
   2) Sort the view on Keycode, Store using the saved sort Script_DTAR020_Sort_Sku_Store.xml

   Author: Bruce Martin  (LGPL)
*/

if (layout.getRecord(0).getRecordName() == "DTAR020"
||  layout.getRecord(0).getRecordName() == "GeneratedCsvRecord") {
    var list = RecordEditorData.getNewLineList()  /* Get empty list of lines */
    var line;
    
    for (i = 0; i < RecordEditorData.view.getRowCount(); i++) {
        line = RecordEditorData.view.getLine(i);
        if (line.getFieldValue("QTY-SOLD").asLong() < 0) {
            list.add(line);
        }
    }

    var displayType = RecordEditorData.displayConstants.ST_LIST_SCREEN;
    print(displayType + " " + list.size() + " " + list.getClass().getName());
    var screen = RecordEditorData.displayList(displayType, "Qty < 0", list); /* create new display from the list that has been createrd.*/

    RecordEditorData.executeSavedTask(screen, 
    	                              RecordEditorData.executeConstansts.sortDir /*"SortDir"*/, 
    	                              "Script_DTAR020_Sort_Sku_Store.xml"); /* sort the data on the newly created screen */
    
    RecordEditorData.executeSavedTask(screen, 
    	                              RecordEditorData.executeConstansts.fieldDir  /*"FieldDir"*/,
    	                              "DTAR020_HideDate.xml");    /* Hide Date Column  */

} else {
    RecordEditorData.showMessage("Script only works on DTAR020 files and not: " + layout.getRecord(0).getRecordName() + "files");
}
