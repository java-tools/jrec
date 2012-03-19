#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(':0'):
        click('*_2')
    close()

    if frame('Create Record Layout - Record Edit - hsqldb:1'):
        select('Record Name', 'zzCreate2')
        select('Description', 'Create Test2')
        select('Record Type', 'Record Layout')
        select('System', 'Other')
        select('Lines to Insert', '3')
        click('Insert')
        assert_content('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', [ ['0', '0', '', '', '0', '0', '0', '', '', ''],
['0', '0', '', '', '0', '0', '0', '', '', ''],
['0', '0', '', '', '0', '0', '0', '', '', '']
])
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '01', '{0, Position}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '02', '{0, Length}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f1', '{0, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '03', '{1, Position}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '04', '{1, Length}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f2', '{1, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[1],columns:[FieldType]')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'Num (Left Justified)', '{1, FieldType}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '07', '{2, Position}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '04', '{2, Length}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f3', '{2, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[1],columns:[Description]')
        assert_content('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', [ ['1', '2', 'f1', '', '0', '0', '0', '', '', ''],
['3', '4', 'f2', '', '5', '0', '0', '', '', ''],
['7', '4', 'f3', '', '0', '0', '0', '', '', '']
])
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[1],columns:[Description]')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[1],columns:[Description]')
        assert_p('Description', 'Component.Text', 'Create Test2')
        assert_content('Record Type', [ ['2', '3', '10', '9', '0', '1', '6']
])
        assert_p('net.sf.RecordEditor.layoutEd.Record.RecordPnl_5', 'Enabled', 'true')
        click('Save')

        click('Close')
    close()

    if frame(':0'):
        click('*')
    close()

    if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
        select('Record Name', 'zzCreate2')
        select('Description', '%')
        assert_content('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_55', [ ['1', '2', 'f1', '', '0', '0', '0', '', '', ''],
['3', '4', 'f2', '', '5', '0', '0', '', '', ''],
['7', '4', 'f3', '', '0', '0', '0', '', '', '']
])
        assert_p('Description_2', 'Text', 'Create Test2')
        assert_p('Description_2', 'Text', 'Create Test2')
        assert_p('Record Type_2', 'Text', 'Record Layout')
        assert_p('System_2', 'Text', 'Other')
        click('Delete the Current Record')
    close()

    if window('Delete: zzCreate2'):
        click('Yes')
        ##click('OptionPane.button')
    close()

    if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
        click('Close')
    close()

    pass
