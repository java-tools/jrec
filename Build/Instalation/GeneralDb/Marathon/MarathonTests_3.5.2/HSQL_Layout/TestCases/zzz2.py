#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(':0'):
        click('*')
    close()

    if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
        click('Close')
    close()

    if frame(':0'):
        click('*_2')
    close()

    if frame('Create Record Layout - Record Edit - hsqldb:1'):
        select('Record Name', 'zzzz2')
        select('System', 'Other')
        select('Record Type', 'Group of Records')
        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC1', '{0, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', '1', '{0, Field Start}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', '0', '{0, Field Start}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Field]')
        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'bsComp', '{1, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC2', '{1, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Field Start]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', '2', '{1, Field Start}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Field]')
        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'bsComp', '{2, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Field Start]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', '3', '{2, Field Start}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', '0', '{1, Field Start}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', '0', '{2, Field Start}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Field Start]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC3', '{2, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Field Start]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', '03', '{2, Field Start}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', '0', '{2, Field Start}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Field Start]')
        click('Save')
        click('Close')
    close()

    if frame(':0'):
        click('*')
    close()

    if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
        select('Record Name', 'zzzz2')
        select('Description', '%')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[1],columns:[Field Start]')
        assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', '0', '{1, Field Start}')
        assert_content('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', [ ['', '491', '0', '', '', '0'],
['', '496', '0', '', '', '0'],
['', '514', '0', '', '', '0']
])
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[1],columns:[Field Start]')
        click('Delete the Current Record')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[1],columns:[Field Start]')
    close()

    if window('Delete: zzzz2'):
        click('Yes')
    close()

    if window('Record Layout Definitions'):
        window_closed('Record Layout Definitions')
    close()

    pass