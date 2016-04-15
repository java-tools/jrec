/* --------------------------------------------------------------------------
   Purpose: Grovey Script to write the view as JSon. It works best with 
   if there is only one record in the file
    Author: Bruce Martin
   License: LGPL
   
     Logic:
          Loop through the fields i
 * -------------------------------------------------------------------------- */ 
 

import groovy.json.JsonBuilder

if (RecordEditorData.view != null && RecordEditorData.view.getRowCount() > 0) {
    def allRecords = []
    for (i in 0..RecordEditorData.view.getRowCount()-1) {
        def line = RecordEditorData.view.getLine(i)
        def recordIndex = line.getPreferredLayoutIdx()
        def flds = [:]
        def record = line.getLayout().getRecord(recordIndex)
            
        for (j in 0..record.getFieldCount()-1) { 
            def fldVal = line.getFieldValue(recordIndex, j)
            flds.put(record.getField(j).getLookupName(), line.getFieldValue(recordIndex, j).asString());
        }
        
        def r = [ : ]
        r.put(record.getRecordName(), flds)
        allRecords.add(r)
    }
   
    
    def of = RecordEditorData.view.getFileName() + ".json"
    of = RecordEditorData.askForFileName(of, false)
    if (of != "") {
        RecordEditorData.outputFile = of
        def json = new JsonBuilder()
        def root = json{
            records  allRecords
        } 
        new File(of).write(json.toPrettyString())
    }    
}