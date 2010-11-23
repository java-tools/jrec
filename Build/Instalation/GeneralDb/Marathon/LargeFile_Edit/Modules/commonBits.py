import time

def windows():
	return 1
##	return "a" == "a"


def Linux():
	return 'guest'
#	return 'knoppix'


def isWindowsLook():
	return 0

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
		return 'C:\\Program Files\\RecordEdit\\' + version() + '\\SampleFiles\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/' + version() + '/SampleFiles/'
##	return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'


def velocityDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\' + version() + '\\SampleVelocityTemplates\\File\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/' + version() + '/SampleVelocityTemplates/File/'
##	return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'

def implementationSampleDir():
	return 'C:\\Program Files\\RecordEdit\\' + version() + '\\SampleFiles\\'
##	return '/C:/Program Files/RecordEdit/HSQLDB/SampleFiles/'
#	return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'

def cobolTestDir():
	if windows():
		return 'E:\\Work\\RecordEdit\\CobolTestData\\'
#		return 'E:\\Work\\RecordEdit\\CobolTests\\TestData\\'
	else: 
		return '/home/' + Linux() + '/reTest/'

def getJasperReportName():
	return r'E:\Work\RecordEdit\Jasper\untitled_report_1.jrxml'

	
def usingEditStart():
	return true

def xmlCopybookDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\' + version() + '\\CopyBook\Xml\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/' + version() + '/CopyBook/Xml/'


def CobolCopybookDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\' + version() + '\\CopyBook\Cobol\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/' + version() + '/CopyBook/Cobol/'
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
		return 'C:\\Documents and Settings\\b\\.RecordEditor\\' + version() + '\\User\\'
##		return 'C:\\Users\\bm\\.RecordEditor\\' + version() + '\\User\\'
	else: 
		return '/home/' + Linux() + '/.RecordEditor/' + version() + '/User/'

def selectPane():
	return 'File Name'
#	return 'FilePane$4'
##	return 'FilePane$3'

def doEdit(click):
	click('Edit1')
	time.sleep(0.5)
	return

def doSleep():

	time.sleep(1.5)
	return

