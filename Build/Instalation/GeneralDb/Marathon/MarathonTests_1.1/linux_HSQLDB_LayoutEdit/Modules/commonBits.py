def windows():
	return "a" == "a"


def isVersion89():
	return 1

def Linux():
	return 'guest'
#	return 'knoppix'

def sampleDir():
	if windows():
		return utilDir()+ 'SampleFiles\\'
	else:
		return utilDir()+ 'SampleFiles/'


def implementationSampleDir():
	return  sampleDir()
##	return '/C:/Program Files/RecordEdit/HSQLDB/SampleFiles/'
#	return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'


def xmlCopybookDir():
	if windows():
		return paramDir() + 'CopyBook\Xml\\'
	else: 
		return paramDir() + 'CopyBook/Xml/'

def copybookDir():
	if windows():
		return utilDir()+ 'CopyBook\\'
	else: 
		return utilDir()+ 'CopyBook/'
def cobolDir():
	if windows():
		return utilDir()+ 'CopyBook\\Cobol\\'
	else: 
		return utilDir()+ 'CopyBook/Cobol/'

def csvDir():
	if windows():
		return utilDir()+ 'CopyBook\\Csv\\'
	else: 
		return utilDir()+ 'CopyBook/Csv/'

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
			return 'C:\\Users\\mum\\.RecordEditor\\HSQLDB\\'
		else:
			return 'C:\\JavaPrograms\\RecordEdit\\'
		##return 'C:\\Users\\mum\\RecordEditor_HSQL\\User\\'
		##return 'C:\\Users\\bm\\.RecordEditor\\' + version() + '\\User\\'
	else: 
		return '/home/bm' + '/.RecordEditor/' + version() 

def separator():
	if windows():
		return '\\'
	else: 
		return '/'
