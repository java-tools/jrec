def windows():
	return 0
##	return "a" == "a"


def Linux():
	return 'bm'
#	return 'knoppix'


def isWindowsLook():
	return 0


def isJRecord():
	return not isRecordEditor()


def isRecordEditor():
	return 0

def fileSep():
	if windows():
		return '\\'
	else:
		return '/'

def sampleDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\Avro\\SampleFiles\\'
	else:
		return '/home/' + Linux() + '/Programs/RecordEdit/Avro/SampleFiles/'

def velocityDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\Avro\\VelocityTemplates\\File\\'
	else: 
		return '/home/' + Linux() + '/Programs/RecordEdit/Avro/VelocityTemplates/File/'

def implementationSampleDir():
	if windows():
		return 'C:\\Program Files\\Avro\\SampleFiles\\'
	else:
		return '/home/' + Linux() + '/Programs/RecordEdit/Avro/SampleFiles/'
##	return '/C:/Program Files/Programs/RecordEdit/HSQLDB/SampleFiles/'
#	return '/home/knoppix/Programs/RecordEdit/HSQLDB/SampleFiles/'

def cobolTestDir():
	if windows():
		return 'E:\\Work\\RecordEdit\\CobolTests\\TestData\\'
	else: 
		return '/home/' + Linux() + '/reTest/'

def getJasperReportName():
	return r'E:\Work\RecordEdit\Jasper\untitled_report_1.jrxml'

	
def usingEditStart():
	return true

	
def stdCopybookDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\Avro\\CopyBook\\'
	else:
		return '/home/' + Linux() + '/Programs/RecordEdit/Avro/CopyBook/'


	
def xmlCopybookDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\Avro\\CopyBook\\'
	else:
		return '/home/' + Linux() + '/Programs/RecordEdit/Avro/CopyBook/'

	
def CobolCopybookDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\Avro\\CopyBook\\'
	else:
		return '/home/' + Linux() + '/Programs/RecordEdit/Avro/CopyBook/'

##	return '/union/home/guest/linux_HSQLDB_Edit/TestCase/Xml/XmlTree2.py'

def setRecordLayout(select, recordLayout):
##	select('BmKeyedComboBox', '0')
	select('ComboBox', 'ProtoBuffer Delimited Messages')
	select('ComboBox1', 'Compiled Proto')
	select('FileChooser1', xmlCopybookDir() + recordLayout + '.protocomp')

	select('BmKeyedComboBox', '0')

	##	select('ComboBox2', recordLayout)
##	select('ComboBox', 'Default Reader')
##	select('ComboBox1', 'RecordEditor XML Copybook')
##	select('FileChooser1', xmlCopybookDir() + recordLayout + '.Xml')

def setRecordLayout2(select, recordLayout):
	select('BmKeyedComboBox', '0')
	select('ComboBox', 'RecordEditor XML Copybook')
	select('FileChooser2', xmlCopybookDir() + recordLayout + '.protocomp')


	##	select('ComboBox2', recordLayout)
#	select('ComboBox', 'Default Reader')
#	select('ComboBox1', 'RecordEditor XML Copybook')
#	select('FileChooser2', xmlCopybookDir() + recordLayout + '.Xml')

def setOpenCobolLayout(select, recordLayout):
	setCobolLayout(select, recordLayout, 'Open Cobol Little Endian (Intel)')

def setOpenCobolLayout2(select, recordLayout):
	setCobolLayout2(select, recordLayout, 'Open Cobol Little Endian (Intel)')

#	select('BmKeyedComboBox', '0')
#	select('ComboBox', 'Cobol Copybook')
#	select('FileChooser2', xmlCopybookDir() + recordLayout + '.cbl')
#	select('ComputerOptionCombo', 'Open Cobol Little Endian (Intel)')

def setMainframeCobolLayout(select, recordLayout):
	select('BmKeyedComboBox', '0')
	select('ComboBox', 'Cobol Copybook')
	select('FileChooser1', CobolCopybookDir() + recordLayout + '.protocomp')
	select('ComputerOptionCombo', 'Mainframe')


def setCobolLayout(select, recordLayout, format):
	select('BmKeyedComboBox', '0')
	select('ComboBox', 'Cobol Copybook')
	select('FileChooser1', CobolCopybookDir() + recordLayout + '.protocomp')
	select('ComputerOptionCombo', format)


def setCobolLayout2(select, recordLayout, format):
	select('BmKeyedComboBox', '0')
	select('ComboBox', 'Cobol Copybook')
	select('FileChooser2', CobolCopybookDir() + recordLayout + '.protocomp')
	select('ComputerOptionCombo', format)

#	select('ComboBox', 'Default Reader')
#	select('ComboBox1', 'RecordEditor XML Copybook')
#	select('FileChooser1', xmlCopybookDir() + recordLayout + '.Xml')

def userDir():
	if windows():
		return 'C:\\Users\\bm\\.RecordEditor\\Avro\\User\\'
	else:
		return '/home/' + Linux() + '/.RecordEditor/Avro/User/'

def selectPane():
	return 'FilePane$4'
#	return 'FilePane$3'

def firstField():
	return 'store'
##	return 'keycode'

def secondField():
	return 'store'
##	return 'keycode'
