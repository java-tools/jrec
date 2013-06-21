###
###   Language: Jython
###   Author:   Bruce Martin
###   License: GPL
###
###   This a Jython Macro for use with RecordEditor. It will create a view
###   containing the first record in the file and display them on the screen.
###

count = 5
if count > RecordEditorData.view.getRowCount():
    count = RecordEditorData.view.getRowCount()

if count > 0:
    list = RecordEditorData.getNewLineList()
    i = 0

    print count
    while i < count:
        list.add(RecordEditorData.view.getLine(i))
        i = i + 1; 
    

    displayType = RecordEditorData.displayConstants.ST_LIST_SCREEN
    RecordEditorData.displayList(displayType, "First 5 Rows", list);
    
###
###  Other options for displayType are:
###
###  *  displayType = RecordEditorData.displayConstants.ST_RECORD_SCREEN
###        - Display on a single Record Screen
###  *  displayType = RecordEditorData.displayConstants.ST_LINES_AS_COLUMNS
###        - Display on a screen where rows and columns are swapped
###
###