###
###   Language: Jython
###   Author:   Bruce Martin
###   License: GPL
###
###   This a Jython Macro for use with RecordEditor when editing Csv files
###
###   The program will loop through all lines in the file and make
###   sure each line ends with a Csv field seperator
###

changed = 0
sep = layout.getDelimiter()			### Get Field Delimiter 
for line in file:				### Loop through the lines in the file
	text = line.getFullLine()		### Get the line as Unicode Text field

	if not text.endswith(sep):
		text = text + sep
		line.setData(text)
		changed = 1

RecordEditorData.view.fireTableDataChanged()	### Telling the RecordEditor to redisplay all screens
if changed:
	RecordEditorData.view.setChanged(1)	### Telling the RecordEditor the file has been changed 

