###
###   This will loop through all the selected Records.
###   For protocol buffers / Avro Editors, child records will 
###   not be included
###

print 'hello from python'
print outputFile
my_file = open(outputFile,'w')

for line in records:
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
