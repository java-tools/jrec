def windows():
	return 1
##	return "a" == "a"


def Linux():
	return 'guest'
#	return 'knoppix'


def isWindowsLook():
	return 0


def isVersion80():
	return 1

def isVersion81():
	return 1


def isVersion806():
	return 1

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
		return utilDir()+ 'SampleFiles\\'
	else: 
		return utilDir()+ 'SampleFiles/'


def velocityDir():
	if windows():
		return utilDir()+ 'SampleVelocityTemplates\\File\\'
	else: 
		return utilDir()+ 'SampleVelocityTemplates/File/'

def implementationSampleDir():
	return  sampleDir()

def cobolTestDir():
	if windows():
		return "C:\\Users\\mum\\Bruce\\CobolTestData\\"
		##return "C:\\Users\\mum\\Bruce\\CobolTestData\\"
		##return 'E:\\Work\\RecordEdit\\CobolTests\\TestData\\'
	else: 	
		return '/home/bm/Programs/open-cobol-1.0/CobolSrc/z1Test/'
##		return '/home/' + Linux() + '/reTest/'

def getJasperReportName():
	return r'E:\Work\RecordEdit\Jasper\untitled_report_1.jrxml'

	
def usingEditStart():
	return true

	
def xmlCopybookDir():
	if windows():
		return paramDir() + 'CopyBook\\'
	else:
		return paramDir() + 'CopyBook/'

	
def stdCopybookDir():
	if windows():
		return paramDir() + 'CopyBook\\'
	else:
		return paramDir() + 'CopyBook/'



def CobolCopybookDir():
	if windows():
		return paramDir() + 'CopyBook\\'
	else:
		return paramDir() + 'CopyBook/'

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
		return paramDir() + 'User\\'

		##return 'C:\\Users\\mum\\RecordEditor_HSQL\\User\\'
		##return 'C:\\Users\\bm\\.RecordEditor\\' + version() + '\\User\\'

	else: 
		return paramDir() + '/User/'


def utilDir():
	return paramDir()
	
def paramDir():
	if windows():
		if isVersion80():
##			return 'C:\\Users\\Mum\\.RecordEditor\\ProtoBuf\\'
			return 'C:\\Users\\BruceTst\\.RecordEditor\\ProtoBuf\\'
		else:
			return 'C:\\JavaPrograms\\RecordEdit\\'

		##return 'C:\\Users\\mum\\RecordEditor_HSQL\\User\\'
		##return 'C:\\Users\\bm\\.RecordEditor\\' + version() + '\\User\\'
	else: 
		return '/home/bm' + '/.RecordEditor/ProtoBuf' 


def selectPane():
#	return 'File Name'
#	return 'FilePane$4'
	return 'FilePane$3'

def selectPaneFn():
	return 'File Name'

def selectFileName(select, name):
	select('File Name', name)

def selectOldFilemenu(select_menu, menu, text):
	if isVersion80():
		select_menu(menu + '>>' + text)
	else:
		select_menu('File>>' + text)


def firstField():
##	return 'store'
	return 'keycode'

def secondField():
	return 'saleDate'
##	return 'keycode'
