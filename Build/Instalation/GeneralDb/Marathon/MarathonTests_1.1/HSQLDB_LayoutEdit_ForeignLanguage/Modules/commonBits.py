from datetime import datetime
import time

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
	
def isJava7():
	return version() == 'MsAccess'
 

def Linux():
	return 'guest'
#	return 'knoppix'


def isTstLanguage():
    return 1

def isWindowsLook():
	return 0

def isNimbusLook():
	return 0

def fileSep():
	if windows():
		return '\\'
	else:
		return '/'

def sampleDir():
	return utilDir()+ 'SampleFiles' + fileSep()


def fl(txt):
	if isTstLanguage():
		return '`!' + txt + '!`'
	else:
		return txt

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
##	select('ComboBox', fl('RecordEditor XML Copybook'))
	select('ManagerCombo', fl('RecordEditor XML Copybook'))
	select('FileChooser1', xmlCopybookDir() + recordLayout + '.Xml')

	##select('BmKeyedComboBox', '0')
	select('BmKeyedComboBox', fl('Default'))

def closeWindow(click):
	if isNimbusLook():
		click('InternalFrameTitlePane.closeButton')
	else:
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	return


def sampleDir():
	return utilDir()+ 'SampleFiles' + fileSep()


def reCsvEditParamDir():
	if windows():
##		return 'C:\\Users\\BruceTst2/.RecordEditor/reCsvEd/SampleFiles/'
		return 'C:\\Users\\BruceTst2\\.RecordEditor\\reCsvEd\\SampleFiles\\'  
	else:
		return '/home/bm' + '/.RecordEditor/' + version() + '/'


def paramDir():
	if windows():
		if isVersion80():
##			return 'C:\\Users\\Mum\\RecordEditor_HSQL\\'
			return 'C:\\Users\\BruceTst2\\.RecordEditor\\HSQLDB\\'
			return 'C:\\Users\\BruceTst2\\.RecordEditor\\reCsvEd\\SampleFiles\\'  
		else:
			return 'C:\\JavaPrograms\\RecordEdit\\'

		##return 'C:\\Users\\mum\\RecordEditor_HSQL\\User\\'
		##return 'C:\\Users\\bm\\.RecordEditor\\' + version() + '\\User\\'
	else: 
		return '/home/bm' + '/.RecordEditor/' + version() + '/'



def selectPane():
	##return 'FilePane$4'
	return 'FilePane$3'

def doEdit(click):
	click(fl('Edit') + '1')
	##time.sleep(0.5)
	return


def doSleep():
	return

def version():
	return 'HSQLDB'

def CobolCopybookDir():
	return paramDir() + 'CopyBook' + fileSep() +'Cobol' + fileSep()


def velocityDir():
	if windows():
		return paramDir()+ 'SampleVelocityTemplates\\File\\'
	else: 
		return paramDir()+ 'SampleVelocityTemplates/File/'

def paramDir():
	if windows():
		return 'C:\\Users\\BruceTst2\\.RecordEditor\\HSQLDB\\'
##		return 'C:\\Users\\\\.RecordEditor\\HSQLDB\\'
	else: 
		return '/home/bm' + '/.RecordEditor/' + version() + '/'

def fileSep():
	if windows():
		return '\\'
	else:
		return '/'

def selectFileName(select, name):
	select('File Name', name)

def setMainframeCobolLayout(select, recordLayout):
	select('ComboBox2', recordLayout)

def checkCopybookLoad(file, copybook):
	return '\n\n' + fl('-->> ' + file + ' processed\n\n      Copybook: ' + copybook)

def isMissingCol():
	return 0

def isJRecord():
	return 0

def isRecordEditor():
	return 0


def selectOldFilemenu(select_menu, menu, text):
	if isVersion80():
		if isTstLanguage():
			select_menu(fl(menu) + '>>' + fl(text))
		else:
			select_menu(menu + '>>' + text)
	else:
		select_menu('File>>' + text)

def delete3(click):
	if isTstLanguage():
		click(fl('Delete') + '1')
	else:
		click('Delete3')

def delete2(click):
	if isTstLanguage():
		click(fl('Delete'))
	else:
		click('Delete2')

def paste2(click):
	if isTstLanguage():
		click(fl('Paste'))
	else:
		click('Paste2')

def copy2(click):
	if isTstLanguage():
		click(fl('Copy'))
	else:
		click('Copy2')

def cut2(click):
	if isTstLanguage():
		click(fl('Cut'))
	else:
		click('Cut2')

def save1(click):
	if isTstLanguage():
		click(fl('Save')
)
	else:
		click('Save1')


def new1(click):
	if isTstLanguage():
		click(fl('New'))
	else:
		click('New1')


def find(click):
	click(fl('Find') + " >>")

#	if isTstLanguage():
#		click(fl('Find'))
#	else:
#		click('Find1')

def findA(click):
	if isTstLanguage() == 0:
		click('Find1')


def filter(click):
	if isTstLanguage():
		click(fl('Filter'))
	else:
		click('Filter1')

def filter2(click):
	if isTstLanguage():
		click(fl('Filter') + '1')
	else:
		click('Filter2')


def sort(click):
	if isTstLanguage():
		click(fl('Sort'))
	else:
		click('Sort1')

def save(click):
	if isTstLanguage():
		click(fl('Save'))
	else:
		click('Save1')

def save2(click):
	if isTstLanguage():
		click(fl('Save') + '1')
	else:
		click('Save2')

def copy(click):
	if isTstLanguage():
		click(fl('Copy'))
	else:
		click('Copy2')
