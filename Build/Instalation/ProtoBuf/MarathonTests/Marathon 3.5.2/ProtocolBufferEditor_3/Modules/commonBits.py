#{{{ Marathon
from marathon.playback import *
#}}} Marathon

from datetime import datetime
import time

def windows():
    return 1
##    return "a" == "a"


def Linux():
    return 'bm/Programs'
#    return 'knoppix'


def isWindowsLook():
    return 0

def isNimbusLook():
    return 0


def isVersion80():
    return 1

def isVersion81():
    return 1

def isMetalLook():
    return  isWindowsLook() != 1 & isNimbusLook() != 1

def version():
    return 'HSQL'

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
##    return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'

def sampleXmlDir():
    if windows():
        return utilDir()+ '\\SampleFiles\\Xml\\'
    else: 
        return utilDir()+ '/SampleFiles/Xml/'
#

def velocityDir():
    if windows():
        return utilDir()+ 'SampleVelocityTemplates\\File\\'
    else: 
        return utilDir()+ 'SampleVelocityTemplates/File/'
##    return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'

def implementationSampleDir():
    return  sampleDir()
##    return '/C:/Program Files/RecordEdit/HSQLDB/SampleFiles/'
#    return '/home/knoppix/RecordEdit/HSQLDB/SampleFiles/'

def cobolTestDir():
    if windows():
        return "C:\\Users\\mum\\Bruce\\CobolTestData\\"
        ##return 'E:\\Work\\RecordEdit\\CobolTests\\TestData\\'
    else:     
        return '/home/bm/Programs/open-cobol-1.0/CobolSrc/z1Test/'
##        return '/home/' + Linux() + '/reTest/'

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



def setRecordLayout(select, recordLayout):
##    select('BmKeyedComboBox', '0')
##    select('ComboBox', 'ProtoBuffer Delimited Messages')
##    select('ComboBox1', 'Compiled Proto')
##    select('FileChooser1', xmlCopybookDir() + recordLayout + '.protocomp')

    select('File Structure', 'Delimited Messages')
    select('Type of Definition', 'Compiled Proto')

    select('Proto Definition', xmlCopybookDir() + recordLayout + '.protocomp')


    ##    select('ComboBox2', recordLayout)
##    select('ComboBox', 'Default Reader')
##    select('ComboBox1', 'RecordEditor XML Copybook')
##    select('FileChooser1', xmlCopybookDir() + recordLayout + '.Xml')

def setRecordLayout2(select, recordLayout):
    select('BmKeyedComboBox', '0')
    select('ComboBox', 'RecordEditor XML Copybook')
    select('FileChooser2', xmlCopybookDir() + recordLayout + '.protocomp')


    ##    select('ComboBox2', recordLayout)
#    select('ComboBox', 'Default Reader')
#    select('ComboBox1', 'RecordEditor XML Copybook')
#    select('FileChooser2', xmlCopybookDir() + recordLayout + '.Xml')

def setOpenCobolLayout(select, recordLayout):
    setCobolLayout(select, recordLayout, 'Open Cobol Little Endian (Intel)')

def setOpenCobolLayout2(select, recordLayout):
    setCobolLayout2(select, recordLayout, 'Open Cobol Little Endian (Intel)')

#    select('BmKeyedComboBox', '0')
#    select('ComboBox', 'Cobol Copybook')
#    select('FileChooser2', xmlCopybookDir() + recordLayout + '.cbl')
#    select('ComputerOptionCombo', 'Open Cobol Little Endian (Intel)')


def setOpenCobolLayout(recordLayout):
    select('ComboBox2', recordLayout)

def setOpenCobolLayout2(recordLayout):
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
        if isVersion80():
            return 'C:\\Users\\BruceTst\\.RecordEditor\\ProtoBuf\\'
##            return 'C:\\Users\\Mum\\.RecordEditor\\ProtoBuf\\'
        else:
            return 'C:\\JavaPrograms\\RecordEdit\\'

        ##return 'C:\\Users\\mum\\RecordEditor_HSQL\\User\\'
        ##return 'C:\\Users\\bm\\.RecordEditor\\' + version() + '\\User\\'
    else: 
        return '/home/bm' + '/.RecordEditor/' + version() 

def selectPane():
#    return 'FilePane$4'
    return 'FilePane$3'

#    return 'File Name'

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
#    print start
#    time.sleep(.9)
    diff = datetime.now() - start
    while diff.seconds < 1.8:
#        print diff
        time.sleep(1.8 - diff.seconds)
        diff = datetime.now() - start
    return

def selectFileName(select, name):
    select('File Name', name)
##    select('ComboBox2', recordLayout)
##    select('FileChooser', name


