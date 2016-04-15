/*

   Sample Javascript RecordEditor macro to
   1) Create a view of records where Quantity < 0
   2) Sort the view on Keycode, Store using the saved sort Script_DTAR020_Sort_Sku_Store.xml

   Author: Bruce Martin  (LGPL)
*/

if (layout.getRecord(0).getRecordName() == "DTAR020"
||  layout.getRecord(0).getRecordName() == "GeneratedCsvRecord") {
	/* Setting up a line using field names */
    var line = RecordEditorData.newLine()
    line.setField("KEYCODE-NO", "00000000");
    line.setField("STORE-NO",   "0");  /* you are best using Strings         */
    line.setField("DATE",       "0");  /* and letting the RecordEditor       */ 
    line.setField("DEPT-NO",    "0");  /* do the Conversion                  */
    line.setField("QTY-SOLD",   "0");
    line.setField("SALE-PRICE", "0.0");
    RecordEditorData.view.addLine(0, line)

	/* You can also use Record-Number, Field-Number */
    var line = RecordEditorData.newLine()
    line.setField(0, 0, "33333333");
    line.setField(0, 1, "333");
    line.setField(0, 2, "330133");
    line.setField(0, 3, "333");
    line.setField(0, 4, "33");
    line.setField(0, 5, "33.33");
    RecordEditorData.view.addLine(3, line)
    
    RecordEditorData.fireScreenChanged(true); /* when you update the screen data,
                                                 you need to notify the RecordEditor 
                                                 that the data has changed.*/
} else {
    RecordEditorData.showMessage("Script only works on DTAR020 files and not: " + layout.getRecord(0).getRecordName() + "files");
}
