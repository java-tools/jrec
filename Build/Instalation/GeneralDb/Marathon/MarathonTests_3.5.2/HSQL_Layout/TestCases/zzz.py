#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(':0'):
        click('*_2')
    close()

    if frame('Create Record Layout - Record Edit - hsqldb:1'):
        select('Record Name', 'zzz1')
        select('Record Type', 'Group of Records')
        select('System', 'Other')
        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC1', '{0, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Field]')
        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'bsComp', '{1, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC2', '{1, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Field Start]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Field]')
        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'bsComp', '{2, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC3', '{2, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Field]')
        click('Save')
        click('Close')
    close()

    if frame(':0'):
        click('*')
    close()

    if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
        select('Record Name', 'zzz1')
        select('Description', '%%')

        click('Delete the Current Record')
    close()

    if window('Delete: zzz1'):
        click('Yes')
    close()

    pass