def windows():
	return 01
##	return "a" == "a"


def isMsAccess():
	return 0

def isVersion80():
	return 1

def isVersion81():
	return 1

def isVersion82():
	return 1
def Linux():
	return 'guest'
#	return 'knoppix'


def isWindowsLook():
	return 0

def isNimbusLook():
	return 0


def sampleDir():
	return utilDir() + 'SampleFiles' + separator()



def implementationSampleDir():
	return  sampleDir()
##	return '/C:/Program Files/RecordEdit/HSQLDB/SampleFiles/'
#	return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'


def cobolTestDir():
	if windows():
		return "C:\\Users\\mum\\Bruce\\CobolTestData\\"
		##return "C:\\Users\\mum\\Bruce\\CobolTestData\\"
		##return 'E:\\Work\\RecordEdit\\CobolTests\\TestData\\'
	else: 	
		return '/home/bm/Programs/open-cobol-1.0/CobolSrc/z1Test/'
##		return '/home/' + Linux() + '/reTest/'


def xmlCopybookDir():
	return copybookDir() + 'Xml' + separator()

def cobolDir():
	return copybookDir()+ 'Cobol' + separator()

def csvDir():
	return copybookDir()+ 'Csv' + separator()

def copybookDir():
	return utilDir()+ 'CopyBook' + separator()

def userDir():
	return paramDir() + 'User'  + separator()


def utilDir():
	return paramDir()

def separator():
	if windows():
		return '\\'
	else: 
		return '/'


def setRecordLayout(select, recordLayout):
	select('ComboBox2', recordLayout)

def setRecordLayout2(select, recordLayout):
	select('ComboBox2', recordLayout)


def setRecordLayoutX(select, recordLayout):
##	select('BmKeyedComboBox', '0')
	select('ComboBox', 'RecordEditor XML Copybook')
	select('FileChooser1', xmlCopybookDir() + recordLayout + '.Xml')

	##select('BmKeyedComboBox', '0')
	select('BmKeyedComboBox', 'Default')

def closeWindow(click):
	if isNimbusLook():
		click('InternalFrameTitlePane.closeButton')
	else:
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	return


def sampleDir():
	return utilDir()+ 'SampleFiles' + fileSep()
	
def paramDir():
	if windows():
		if isVersion80():
##			return 'C:\\Users\\Mum\\RecordEditor_HSQL\\'
			return 'C:\\Users\\BruceTst2\\.RecordEditor\\HSQLDB\\'
		else:
			return 'C:\\JavaPrograms\\RecordEdit\\'

		##return 'C:\\Users\\mum\\RecordEditor_HSQL\\User\\'
		##return 'C:\\Users\\bm\\.RecordEditor\\' + version() + '\\User\\'
	else: 
		return '/home/bm' + '/.RecordEditor/' + version() + '/'


def fileSep():
	if windows():
		return '\\'
	else:
		return '/'


def selectPane():
	return 'FilePane$4'
	#return 'FilePane$3'

