#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        select_menu('Record Layouts>>Load Copybook')

        if frame(' - Load Record Layout using selectedLoaderRecord Edit - hsqldb:0'):
            select('User Selected Copybook', commonBits.xmlCopybookDir() + 'wx1File.Xml')
            select('System', 'Other')
            click('Go')
            assert_p('JTextArea_40', 'Text', '-->> ' + commonBits.xmlCopybookDir() + 'wx1File.Xml processed\n      Copybook: wx1File')
            click('Close')
        close()

        select_menu('Record Layouts>>Edit Layout')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
##            select('Record Name', 'ff1%')
##            select('Description', '%')
            select('Record Name', 'wx1%')
            select('Description', '%')
##            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', '', '{2, Field}')
            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'RowCount', '6')
        close()

##        window_closed('Record Editor')
    close()

    pass