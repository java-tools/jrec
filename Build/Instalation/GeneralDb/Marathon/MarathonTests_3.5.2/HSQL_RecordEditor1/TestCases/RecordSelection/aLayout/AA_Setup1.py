#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits
import time

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        select_menu('Record Layouts>>Load Copybook')

        if frame(' - Load Record Layout using selectedLoaderRecord Edit - hsqldb:0'):
            select('User Selected Copybook', commonBits.xmlCopybookDir() + 'wwFile.Xml')
            select('System', 'Other')
            click('Go')
            assert_p('JTextArea_40', 'Text', '-->> ' + commonBits.xmlCopybookDir() + 'wwFile.Xml processed\n      Copybook: wwFile')
            click('Close')
        close()

        select_menu('Record Layouts>>Edit Layout')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            select('Record Name', 'wwF%')
            select('Description', '%%')
            assert_content('JTable_23', [ ['wwFile', 'ww File Def']
])

        close()

    close()

    pass