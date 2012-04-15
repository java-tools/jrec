#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        select_menu('Record Layouts>>Edit Layout')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            select('Record Name', 'ams PO Download')
            select('Description', '%')
##            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', '', '{0, Tree Parent}')
##            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', '', '{1, Tree Parent}')
##            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', '', '{2, Tree Parent}')

            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[0],columns:[Tree Parent]')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'ams PO Download: Header', '{0, Tree Parent}')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'ams PO Download: Detail', '{2, Tree Parent}')

##            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'ams PO Download: Header', '{0, Tree Parent}')
##            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'ams PO Download: Detail', '{2, Tree Parent}')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[2],columns:[Tree Parent]')
            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'ams PO Download: Detail', '{2, Tree Parent}')
            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'ams PO Download: Header', '{0, Tree Parent}')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[2],columns:[Tree Parent]')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[2],columns:[Tree Parent]')
            click('Save')
            click('Close')
        close()

        select_menu('Record Layouts>>Edit Layout')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            select('Record Name', 'ams PO Download')
            select('Description', '%')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[0],columns:[Tree Parent]')
            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'ams PO Download: Header', '{0, Tree Parent}')
            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'ams PO Download: Detail', '{2, Tree Parent}')
        close()

    close()

    pass
