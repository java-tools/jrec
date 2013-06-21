###
###   Language: Jython
###   Author:   Bruce Martin
###   License:  LGPL
###
###   This a Jython Macro for use with RecordEditor/ReCsvEdito. 
###   It will write returns (Quantity < 0) to a new file 
###

lineList = RecordEditorData.getNewLineList()
    
for line in file:
    if line.getFieldValue("QTY-SOLD").asLong() < 0:
       lineList.add(line)
           
if lineList.size() > 0:
   RecordEditorData.write("C:\Temp\DTAR020_Returns.bin", lineList);
