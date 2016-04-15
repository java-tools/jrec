#####################################################################
# Example Script to generate numeric data for a file
# 
#  It is best to run this script from a Single Record Screen rather than a 
#  Table screen
#
#####################################################################

var rec = layout.getRecord(0)

var lines = RecordEditorData.view.createLines(20)
for (lineNo = 0; lineNo < 20; lineNo++) {
	print(lineNo);
	for (i=0; i < rec.getFieldCount(); i++) {
		try {
		   lines[lineNo].getFieldValue(0, i).set(lineNo * 100 + i)	
		} catch(err) {
			lines[lineNo].getFieldValue(0, i).set(i % 10)	
		}
	}
}

RecordEditorData.view.addLines(-1,1, lines)
