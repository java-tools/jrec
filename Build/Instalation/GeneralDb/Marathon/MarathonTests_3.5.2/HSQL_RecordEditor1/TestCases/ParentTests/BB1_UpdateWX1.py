#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        select_menu('Record Layouts>>Edit Layout')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            select('Record Name', 'wx1%')
            select('Description', '%')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[0],columns:[Tree Parent]')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'wwProdHead', '{0, Tree Parent}')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'wwProdHead', '{1, Tree Parent}')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'wwProdHead', '{2, Tree Parent}')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'wwHeader', '{3, Tree Parent}')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'wwHeader', '{4, Tree Parent}')
##            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', ' ', '{5, Tree Parent}')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[5],columns:[Tree Parent]')
            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'wwProdHead', '{0, Tree Parent}')
            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'wwProdHead', '{2, Tree Parent}')
            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'wwHeader', '{3, Tree Parent}')
            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'wwHeader', '{4, Tree Parent}')
##            assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', '', '{5, Tree Parent}')
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[2,3,4,5],columns:[Tree Parent]')
##            assert_content('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', [ ['', '458', '0', '', '', '', '468'],
##['', '459', '0', '', '', '', '468'],
##['', '460', '0', '', '', '', '468'],
##['', '467', '0', '', '', '', '469'],
##['', '468', '0', '', '', '', '469'],
##['', '469', '0', '', '', '', '-1']
##])
            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[2,3,4,5],columns:[Tree Parent]')
            click('Close')
##            select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[2,3,4,5],columns:[Tree Parent]')
        close()

##        window_closed('Record Editor')
    close()

    pass
