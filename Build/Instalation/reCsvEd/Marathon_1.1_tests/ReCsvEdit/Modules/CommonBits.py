
def windows():
	return 1


def xmlCopybookDir():
	return paramDir() + 'CopyBook' + fileSep() +'Xml' + fileSep()

def sampleDir():
	return paramDir()+ 'SampleFiles' + fileSep()


def userDir():
	return paramDir() + 'User' + fileSep()


def paramDir():
	if windows():
		return 'C:\\Users\\BruceTst2\\.RecordEditor\\reCsvEd\\'
	else: 
		return '/home/bm' + '/.RecordEditor/' + version() + '/'



def fileSep():
	if windows():
		return '\\'
	else:
		return '/'

