/* --------------------------------------------------------------------------
   Purpose: Grovey Script to write the view as JSon. It works best with 
   if there is only one record in the file
    Author: Bruce Martin
   License: LGPL
   
     Logic:
          Loop through the fields i
 * -------------------------------------------------------------------------- */ 
 

import groovy.json.JsonBuilder


class RetVal {
	String key;
	def val
}

def putFields(flds, line, record, recordIndex) {
       for (j in 0..record.getFieldCount()-1) { 
            def fldVal = line.getFieldValue(recordIndex, j)
            flds.put(record.getField(j).getLookupName(), line.getFieldValue(recordIndex, j).asString());
        }	
}
def WriteSingleRecordFile() {
    def allRecords = []
    for (i in 0..RecordEditorData.view.getRowCount()-1) {
        def line = RecordEditorData.view.getLine(i)
        def recordIndex = line.getPreferredLayoutIdx()
        def flds = [:]
        def record = line.getLayout().getRecord(recordIndex)
            
        putFields(flds, line, record, recordIndex)
//        for (j in 0..record.getFieldCount()-1) { 
//            def fldVal = line.getFieldValue(recordIndex, j)
//            flds.put(record.getField(j).getLookupName(), line.getFieldValue(recordIndex, j).asString());
//        }
        
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

def expand(parents) {
	    def flds = [:]
    def line = node.getLine()
    
    println node.getLineType() 
    if (line != null) {
        def recordIndex = line.getPreferredLayoutIdx() 
            def record = line.getLayout().getRecord(recordIndex)
        
 //           println "field count: " + record.getFieldCount()
            
            putFields(flds, line, record, recordIndex)
//            for (j in 0..record.getFieldCount()-1) { 
//                def fldVal = line.getFieldValue(recordIndex, j)
//                flds.put(record.getField(j).getLookupName(), line.getFieldValue(recordIndex, j).asString());
//           }
    }
    
    if (! node.isLeaf()) {
       def children=[:]
           for (i in 0..node.getChildCount()-1) {
            def child=node.getChildAt(i)
            if (child != null) {
                def cn = expand(node.getChildAt(i))
                if (children.containsKey(cn.key)) {
                    list = children.get(cn.key) 
                } else {
                    list = []
                    children.put(cn.key, list) 
                }
                list.add(cn.val)
            }
       }
       flds.putAll(children)
    }
    
    RetVal r = new RetVal()
    r.key = node.getLineType()
    r.val = flds
    r
}

def writeMultiRecordFile(parents) {

    last = null	
    lastRecordIndex = -1
    first = null
    for (i in 0..RecordEditorData.view.getRowCount()-1) {
        def line = RecordEditorData.view.getLine(i)
        def recordIndex = line.getPreferredLayoutIdx()
        def record = line.getLayout().getRecord(recordIndex)
            
        if (last != null && parents[recordIndex] < 0) {
        	
        }
        def flds = [:]
        putFields(flds, line, record, recordIndex)
    }
    def of = RecordEditorData.view.getFileName() + ".tree.json"
    of = RecordEditorData.askForFileName(of, false)
    if (of != "") {
       RecordEditorData.outputFile = of
       def json = new JsonBuilder()
       def root = json{
           tree  ret.val
       } 
       new File(of).write(json.toPrettyString())
    }
}

    if (RecordEditorData.view != null && RecordEditorData.view.getRowCount() > 0) {
    	layout = RecordEditorData.view.getLayout()
    	recordCount = layout.getRecordCount
	if (recordCount == 1) {
	    WriteSingleRecordFile();		
        } else {
            def treePresent = false
            def parents = []
            for (i in 0..<recordCount) {
            	if (layout.getRecord(i).getParentRecordIndex() >= 0) {
            	    treePresent = true
            	    parents[i] = getRecord(i).getParentRecordIndex()
            	} else {
            	    parents[i] = - 1;
            	}
            }
            
            if (! treePresent) {
             	parents = RecordEditorData.getRecordHierarchy()
            }
            
            if (parents != null) {
            	writeMultiRecordFile()
            }
        }
    }