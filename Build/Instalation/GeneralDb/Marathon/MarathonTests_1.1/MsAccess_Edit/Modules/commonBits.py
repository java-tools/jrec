from datetime import datetime
import time

def windows():
	return 01
##	return "a" == "a"


def Linux():
	return 'bm/Programs'
#	return 'knoppix'


def isWindowsLook():
	return 0

def isNimbusLook():
	return 0


def isMissingCol():
	return 0


def isVersion80():
	return 1

def isVersion81():
	return 1

def isVersion82():
	return 1
	
def isJava7():
	return version() == 'MsAccess'
 

def isMetalLook():
	return  isWindowsLook() != 1 & isNimbusLook() != 1

def version():
	return 'MsAccess'

def isJRecord():
	return not isRecordEditor()

def isTstLanguage():
    return 0

def isRecordEditor():
	return 1

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

def getJasperReportName():
	return r'E:\Work\RecordEdit\Jasper\untitled_report_1.jrxml'

	
def usingEditStart():
	return true

def xmlCopybookDir():
	return paramDir() + 'CopyBook' + fileSep() +'Xml' + fileSep()


def CobolCopybookDir():
	return paramDir() + 'CopyBook' + fileSep() +'Cobol' + fileSep()


def setRecordLayout(select, recordLayout):
	select('ComboBox2', recordLayout)

def setRecordLayout2(select, recordLayout):
	select('ComboBox2', recordLayout)


def setOpenCobolLayout(select, recordLayout):
	select('ComboBox2', recordLayout)

def setOpenCobolLayout2(select, recordLayout):
	select('ComboBox2', recordLayout)

def setMainframeCobolLayout(select, recordLayout):
	select('ComboBox2', recordLayout)

def setCobolLayout(select, recordLayout, format):
	select('ComboBox2', recordLayout)

def setCobolLayout2(select, recordLayout, format):
	select('ComboBox2', recordLayout)



def scriptExampleDir():
	sep = fileSep()
	return userDir() + 'Scripts' + sep + 'Examples' + sep

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
			return 'C:\\Users\\BruceTst\\RecordEditor_MSaccess\\'
		else:
			return 'C:\\JavaPrograms\\RecordEdit\\'

		##return 'C:\\Users\\mum\\RecordEditor_HSQL\\User\\'
		##return 'C:\\Users\\bm\\.RecordEditor\\' + version() + '\\User\\'
	else: 
		return '/home/bm' + '/.RecordEditor/' + version() + '/'

def selectPane():
	return 'FilePane$4'
	#return 'FilePane$3'

#	return 'File Name'

def closeWindow(click):
	click('BasicInternalFrameTitlePane$NoFocusButton2')
	return


def doEdit(click):
	click(fl('Edit') + '1')
	time.sleep(0.75)
	return


def closeWindow(click):
	if isNimbusLook():
		click('InternalFrameTitlePane.closeButton')
	else:
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	return


def doSleep():
	start = datetime.now()
#	print start
#	time.sleep(.9)
	diff = datetime.now() - start
	while diff.seconds < 1.1:
#		print diff
		time.sleep(1.0 - diff.seconds)
		diff = datetime.now() - start
	return

def selectFileName(select, keystroke, name): 
	if isJava7():
		select('File Name', name)
		##keystroke('File Name', 'Enter')
	else:
		select('File Name', name)
##	select('ComboBox2', recordLayout)
##	select('FileChooser', name


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
		click(fl('Save'))
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
		click(fl('Save') + 1)
	else:
		click('Save2')

def copy(click):
	if isTstLanguage():
		click(fl('Copy'))
	else:
		click('Copy2')
