###
###  This program loops through all the messages in the current view
###
print 'hello from python'
print outputFile
my_file = open(outputFile,'w')

for line in RecordEditorData.view:
	idx = line.getPreferredLayoutIdx()
	count = layout.getRecord(idx).getFieldCount()
	i = 0
	sep = ''
	
	while i < count:
		my_file.write(sep)
		
		v = line.getField(idx, i)
		if (v is None):
			v = ""

		my_file.write(str(v))
		sep = ','
		i = i + 1
	my_file.write('\n')

my_file.close()

##for line in records:
##	my_file.write(line.getFullLine()+'\n')
##my_file.close()
