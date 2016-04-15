###   This a Jython Macro for use with RecordEditor when editing Csv files
###
###   Language: Jython
###   Author:   Bruce Martin
###   License:  GPL
###   Purpose:  Ensure all lines have the correct number of field Seperators
###
###
###   Function:
###   The program will loop through all lines in the file and make
###   sure each line has at least the correct number of field seperators.
###   note: The macro assumes there are no seperators embbeded in a line
###

changed = 0
sep = layout.getDelimiter()			### Get Field Delimiter 
fieldCount = layout.getRecord(0).getFieldCount()
for line in file:				### Loop through the lines in the file
	text = line.getFullLine()		### Get the line as Unicode Text field
	pos = 0
	count = 1

	while unicode.find(text, sep, pos) > 0:
		pos = unicode.find(text, sep, pos) + 1
		count = count + 1 

	if count < fieldCount:
		while count < fieldCount:
			text = text + sep
			count = count + 1 
		line.setData(text)              ### Setting the data in the line,
		                                ### This only works for Text files.
		changed = 1

RecordEditorData.fireScreenChanged(changed);    ### Update the display and set file
                                                ### status to changed


