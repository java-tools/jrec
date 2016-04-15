/* --------------------------------------------------------------------------
   Purpose: Grovey Script to write the selected records as JSon
    Author: Bruce Martin
   License: LGPL
   
     Logic:
          Loop through the fields i
 * -------------------------------------------------------------------------- */ 
 

import groovy.json.JsonBuilder

if (RecordEditorData.selectedView != null && RecordEditorData.selectedView.getRowCount() > 0) {
    def allRecords = []
    for (i in 0..RecordEditorData.selectedView.getRowCount()-1) {
        def line = RecordEditorData.selectedView.getLine(i)
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
   
    
    def of = RecordEditorData.view.getFileName() + ".Selected.json"
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