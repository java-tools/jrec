def windows():
	return "a" == "a"


def Linux():
	return 'guest'
#	return 'knoppix'

def sampleDir():
	if windows():
		return 'C:\\JavaPrograms\\RecordEdit\\HSQL\\SampleFiles\\'
	else:
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/SampleFiles/'

def implementationSampleDir():
	if windows():
		return 'C:\\JavaPrograms\\RecordEdit\\HSQL\\SampleFiles\\'
	else:
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/SampleFiles/'

def cobolDir():
	if windows():
		return 'C:\\JavaPrograms\\RecordEdit\\HSQL\\CopyBook\\Cobol\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/CopyBook/Cobol/'

def csvDir():
	if windows():
		return 'C:\\JavaPrograms\\RecordEdit\\HSQL\\CopyBook\\Cobol\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/CopyBook/Csv/'

def xmlCopybookDir():
	if windows():
		return 'C:\\JavaPrograms\\RecordEdit\\HSQL\\CopyBook\\Xml\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/CopyBook/Xml/'

def copybookDir():
	if windows():
		return 'C:\\JavaPrograms\\RecordEdit\\HSQL\\CopyBook\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/CopyBook/'

def userDir():
	if windows():
##		return 'C:\\Users\\mum\\.RecordEditor\\HSQLDB\\User\\'
		return 'C:\\Users\\bm\\RecordEditor_HSQL\\User\\'
	else: 
		return '/home/' + Linux() + '/.RecordEditor/HSQLDB/User/'


def separator():
	if windows():
		return '\\'
	else: 
		return '/'
