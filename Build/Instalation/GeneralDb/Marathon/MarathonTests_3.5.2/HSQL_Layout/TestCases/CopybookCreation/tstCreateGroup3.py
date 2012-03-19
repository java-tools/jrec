#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(':0'):
        click('*_2')
    close()

    if frame('Create Record Layout - Record Edit - hsqldb:1'):
        select('Record Name', 'zzCreateGroupC1')
        select('Description', 'Group Child Record 1')
        select('Record Type', 'Record Layout')
        select('System', 'Other')
        select('Lines to Insert', '2')
        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '01', '{0, Position}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '02', '{0, Length}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f1', '{0, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '03', '{1, Position}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '04', '{1, Length}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f2', '{1, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[0],columns:[Description]')
        assert_content('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', [ ['1', '2', 'f1', '', '0', '0', '0', '', '', ''],
['3', '4', 'f2', '', '0', '0', '0', '', '', '']
])
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[0],columns:[Description]')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[0],columns:[Description]')
        click('Save')
        click('Save As')
    close()

    if window('Input'):
        select('OptionPane.textField', 'zzCreateGroupC2')
        ##click('OptionPane.button')
        click('OK')
    close()

    if frame('Create Record Layout - Record Edit - hsqldb:1'):
        select('Description', 'Group Child Record 2')
        click('Save')
        click('Save As')
    close()

    if window('Input'):
        select('OptionPane.textField', 'zzCreateGroupC3')
        ##click('OptionPane.button')
        click('OK')
    close()

    if frame('Create Record Layout - Record Edit - hsqldb:1'):
        select('Description', 'Group Child Record 3\t')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f1a', '{0, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f2a', '{1, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[0],columns:[Description]')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[0],columns:[Description]')
        click('Save')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[0],columns:[Description]')
        click('New')
        select('Record Name', 'zzCreateGroup1')
        select('Description', 'Group 1')
        select('Record Type', 'Group of Records')
        select('System', 'Unkown')
        select('Lines to Insert', '')
        select('System', 'Other')
        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC1', '{0, Child Record}')

        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC2', '{1, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        click('Insert')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC3', '{2, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC2', '{1, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC1', '{0, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC3', '{2, Child Record}')
 
        
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC2', '{1, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC3', '{2, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Field Start]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'ams PO Download: Allocation', '{1, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Child Record]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC2', '{1, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Field Start]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'zzCreateGroupC3', '{2, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[2],columns:[Field Start]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[1],columns:[Field Start]')

        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Field Start]')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', ' ', '{0, Tree Parent}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', ' ', '{1, Tree Parent}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', 'rows:[0],columns:[Field Start]')
        click('Save')
        assert_content('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_31', [ ['', '457', '0', '', '', '0'],
['', '458', '0', '', '', '0'],
['', '459', '0', '', '', '0']
])
        click('Close')
    close()

    if frame(':0'):
        click('*')
    close()

    if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
        select('Record Name', 'zzCreateGroup1')
        select('Description', '%')
        assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'zzCreateGroupC1', '{0, Child Record}')
        assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'zzCreateGroupC2', '{1, Child Record}')
        assert_p('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'Text', 'zzCreateGroupC3', '{2, Child Record}')
        select('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', 'rows:[0],columns:[ ]')
    close()

    if frame('Edit Record Layout - Current DB:1'):
        assert_p('Record Name', 'Text', 'zzCreateGroupC1')
        assert_p('Description', 'Text', 'Group Child Record 1')
        assert_p('Record Type', 'Text', 'Record Layout')
        assert_p('System', 'Text', 'Other')
        assert_content('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_40', [ ['1', '2', 'f1', '', '0', '0', '0', '', '', ''],
['3', '4', 'f2', '', '0', '0', '0', '', '', '']
])
        assert_content('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_40', [ ['1', '2', 'f1', '', '0', '0', '0', '', '', ''],
['3', '4', 'f2', '', '0', '0', '0', '', '', '']
])
        click('Right')
        assert_p('Record Name', 'Text', 'zzCreateGroupC2')
        click('Right')
        assert_content('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_40', [ ['1', '2', 'f1a', '', '0', '0', '0', '', '', ''],
['3', '4', 'f2a', '', '0', '0', '0', '', '', '']
])
        click('Close')
    close()

    if window('Record Layout Definitions'):
        select_menu('Window>>Record Edit - hsqldb>>Edit Record Layout - DB ')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            click('Close')
        close()
     
##        window_closed('Record Layout Definitions')
    close()

    if frame(':0'):
        click('*')
    close()

    if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
        select('Record Name', 'zzCreateGroup1')
        select('Description', '%')
        click('Delete the Current Record')
    close()

    if window('Delete: zzCreateGroup1'):
        click('OptionPane.button')
    close()

    if window('Record Layout Definitions'):
        select_menu('Window>>Record Edit - hsqldb>>Edit Record Layout - DB ')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            select('Record Name', 'zzCreateGroupC1')
            select('Description', '%%')
            click('Delete the Current Record')
        close()

        if window('Delete: zzCreateGroupC1'):
            click('Yes')
            ##click('OptionPane.button')
        close()

        select_menu('Window>>Record Edit - hsqldb>>Edit Record Layout - DB ')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            select('Record Name', 'zzCreateGroupC2')
            select('Description', '%')
            click('Delete the Current Record')
        close()

        if window('Delete: zzCreateGroupC2'):
            click('Yes')
            ##click('OptionPane.button')
        close()


        select_menu('Window>>Record Edit - hsqldb>>Edit Record Layout - DB ')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            select('Record Name', 'zzCreateGroupC3')
            select('Description', '%%')
            click('Delete the Current Record')
        close()

        if window('Delete: zzCreateGroupC3'):
            click('Yes')
            ##click('OptionPane.button')
        close()


        select_menu('Window>>Record Edit - hsqldb>>Edit Record Layout - DB ')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            click('Close')
        close()

##        window_closed('Record Layout Definitions')
    close()

    pass
