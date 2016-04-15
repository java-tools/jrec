###
###   Language: Jython
###   Author:   Bruce Martin
###   License: GPL
###
###   This a Jython Macro for use with RecordEditor. It will filter DTAR020
###   record (Quantity < 0) and display in a record-screen 
###

print layout.getRecord(0).getRecordName()

if layout.getRecord(0).getRecordName() == "DTAR020":
    lineList = RecordEditorData.getNewLineList()
    
    for line in file:
    	if line.getFieldValue("QTY-SOLD").asLong() < 0:
            lineList.add(line)
    

    displayType = RecordEditorData.displayConstants.ST_LINES_AS_COLUMNS
    RecordEditorData.displayList(displayType, "Qty < 0, Column Display", lineList);
else:
    RecordEditorData.showMessage("Script only works on DTAR020 files and not " + layout.getRecord(0).getRecordName())

###
###  Other options for displayType are:
###
###  *  displayType = RecordEditorData.displayConstants.ST_RECORD_SCREEN
###        - Display on a single Record Screen
###  *  displayType = RecordEditorData.displayConstants.ST_LIST_SCREEN
###        - Display in a normal list screen
###
###