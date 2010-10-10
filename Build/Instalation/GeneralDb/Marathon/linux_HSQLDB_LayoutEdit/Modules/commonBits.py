def windows():
	return 0


def Linux():
	return 'bm/Programs'
#	return 'knoppix'

def sampleDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\HSQLDB\\SampleFiles\\'
	else:
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/SampleFiles/'

def implementationSampleDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\HSQLDB\\SampleFiles\\'
	else:
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/SampleFiles/'

def cobolDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\HSQLDB\\CopyBook\\Cobol\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/CopyBook/Cobol/'

def xmlCopybookDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\HSQLDB\\CopyBook\\Xml\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/CopyBook/Xml/'

def copybookDir():
	if windows():
		return 'C:\\Program Files\\RecordEdit\\HSQLDB\\CopyBook\\'
	else: 
		return '/home/' + Linux() + '/RecordEdit/HSQLDB/CopyBook/'

def userDir():
	if windows():
		return 'C:\\Users\\bm\\.RecordEditor\\HSQLDB\\User\\'
	else: 
		return '/home/bm/.RecordEditor/HSQLDB/User/'


def separator():
	if windows():
		return '\\'
	else: 
		return '/'
