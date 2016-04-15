## ------------------------------------------------------------------
##      Purpose: Write a Selected Records to a file as JSON (Very basic) 
##       Author: Bruce Martin
## Requirements: Jython 2.7 or later (available at http://www.jython.org/)
##
## Limiotations: This is a very basic script and is only suitable for small
## Single record files.
##
## ------------------------------------------------------------------

import json
                                           
if RecordEditorData.selectedView.getRowCount() > 0:
    i = 0;
    allRecords = []
    while i < RecordEditorData.selectedView.getRowCount():
        line = RecordEditorData.selectedView.getLine(i)
        recordIndex = line.getPreferredLayoutIdx()
        flds = {}
        record = line.getLayout().getRecord(recordIndex)
            
        j = 0
        while j < record.getFieldCount():
         	  fldVal = line.getFieldValue(recordIndex, j)
         	  n = record.getField(j).getLookupName()
    
         	  v = fldVal.asString()
       
         	  flds[n] = v;
         	  j +=1;
        
        r = {record.getRecordName() : flds}
        allRecords.append(r)
    
        i = i + 1
    
    of = RecordEditorData.view.getFileName() + ".Selected.json"
    of = RecordEditorData.askForFileName(of, 0)
    if of != "":
        RecordEditorData.outputFile = of
        with open(of, 'w') as outfile:
            json.dump(allRecords, outfile, indent=4)