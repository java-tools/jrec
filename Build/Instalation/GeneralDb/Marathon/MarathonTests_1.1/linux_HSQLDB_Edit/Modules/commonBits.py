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


def isVersion89():
	return 1

def isMetalLook():
	return  isWindowsLook() != 1 & isNimbusLook() != 1

def version():
	return 'HSQLDB'

def isJRecord():
	return not isRecordEditor()


def isRecordEditor():
	return 1

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
##	return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'


def velocityDir():
	if windows():
		return utilDir()+ 'SampleVelocityTemplates\\File\\'
	else: 
		return utilDir()+ 'SampleVelocityTemplates/File/'
##	return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'

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

def getJasperReportName():
	return r'E:\Work\RecordEdit\Jasper\untitled_report_1.jrxml'

	
def usingEditStart():
	return true

def xmlCopybookDir():
	if windows():
		return paramDir() + 'CopyBook\Xml\\'
	else: 
		return paramDir() + 'CopyBook/Xml/'


def CobolCopybookDir():
	if windows():
		return utilDir()+ 'CopyBook\Cobol\\'
	else: 
		return utilDir()+ 'CopyBook/Cobol/'
##	return '/union/home/guest/linux_HSQLDB_Edit/TestCase/Xml/XmlTree2.py'

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
		if isVersion89():
			return 'C:\\Users\\Mum\\.RecordEditor\\' + version() + '\\'
		else:
			return 'C:\\JavaPrograms\\RecordEdit\\'

		##return 'C:\\Users\\mum\\RecordEditor_HSQL\\User\\'
		##return 'C:\\Users\\bm\\.RecordEditor\\' + version() + '\\User\\'
	else: 
		return '/home/bm' + '/.RecordEditor/' + version() +'/'

def selectPane():
#	return 'FilePane$4'
	return 'FilePane$3'

#	return 'File Name'

def closeWindow(click):
	click('BasicInternalFrameTitlePane$NoFocusButton2')
	return


def doEdit(click):
	click('Edit1')
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
	while diff.seconds < 1.8:
#		print diff
		time.sleep(1.8 - diff.seconds)
		diff = datetime.now() - start
	return

def selectFileName(select, name):
	select('File Name', name)
##	select('ComboBox2', recordLayout)
##	select('FileChooser', name



