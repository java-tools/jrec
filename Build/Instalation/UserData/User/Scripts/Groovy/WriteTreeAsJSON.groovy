/* --------------------------------------------------------------------------
   Purpose: Grovey Script to write a Tree as JSon
    Author: Bruce Martin
   License: LGPL
   
   Description:
     This program is designed to run from either:
       * Record Tree (View >>> Record Based Tree)
       * Sort Tree   (View >>> Sort Based Tree)
     It will dump the Tree as JSON   
        
  * -------------------------------------------------------------------------- */ 
 

import groovy.json.JsonBuilder

class RetVal {
    String key;
    def val
}

def expand(node) {
    def flds = [:]
    def line = node.getLine()
    

    println node.getLineType() 
    if (line != null) {
        def recordIndex = line.getPreferredLayoutIdx() 
            def record = line.getLayout().getRecord(recordIndex)
        
            println "field count: " + record.getFieldCount()
            
            for (j in 0..record.getFieldCount()-1) { 
                def fldVal = line.getFieldValue(recordIndex, j)
                flds.put(record.getField(j).getLookupName(), line.getFieldValue(recordIndex, j).asString());
           }
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


    if (RecordEditorData.root == null) {
        RecordEditorData.showMessage("This is not a Tree display (Sort Tree / Record Tree)")
    } else {
        def ret = expand(RecordEditorData.root)
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
    