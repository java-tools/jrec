#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(':0'):
        click('*_2')
    close()

    if frame('Create Record Layout - Record Edit - hsqldb:1'):
        select('Record Name', 'zzTestCreate1')
        select('Description', 'CreateLayout Test')
        select('Record Type', 'Record Layout')
        select('System', 'Other')

        select('Lines to Insert', '3')
        click('Insert')
        assert_content('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', [ ['0', '0', '', '', '0', '0', '0', '', '', ''],
['0', '0', '', '', '0', '0', '0', '', '', ''],
['0', '0', '', '', '0', '0', '0', '', '', '']
])
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '01', '{0, Position}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '04', '{0, Length}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '05', '{1, Position}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '05', '{1, Length}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f1', '{0, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f2', '{1, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[1],columns:[FieldType]')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'Num (Left Justified)', '{1, FieldType}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '010', '{2, Position}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', '04', '{2, Length}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'f3', '{2, FieldName}')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[1],columns:[FieldName]')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[1],columns:[FieldName]')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_30', 'rows:[1],columns:[FieldName]')
        click('Save')
        click('Close')
    close()

    if frame(':0'):
        click('*')
    close()

    if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
        select('Record Name', 'zzTestCreate1')
        select('Description', '%')
#        assert_p('Record Name_2', 'Component.Text', 'zzTestCreate1')
#        assert_p('Description_2', 'Component.Text', 'CreateLayout Test')
#        assert_p('Description_2', 'Component.Text', 'CreateLayout Test')
#        assert_p('Record Type_2', 'Component.SelectedIndex', '5')
#        assert_p('Record Type_2', 'Component.SelectedItem', '1')
#        assert_p('Record Type_2', 'Component.SelectedObjects', '[1]')
#        select('List_2', 'true')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_55', 'rows:[1],columns:[FieldName]')
#        select('List_2', 'true')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_55', 'rows:[1],columns:[FieldName]')
        assert_content('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_55', [ ['1', '4', 'f1', '', '0', '0', '0', '', '', ''],
['5', '5', 'f2', '', '5', '0', '0', '', '', ''],
['10', '4', 'f3', '', '0', '0', '0', '', '', '']
])
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_55', 'rows:[1],columns:[FieldName]')
        click('Delete the Current Record')
        select('net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl_55', 'rows:[1],columns:[FieldName]')
    close()

    if window('Delete: zzTestCreate1'):
        ##click('OptionPane.button')
        click('Yes')
    close()

#    if window('Record Layout Definitions'):
#        window_closed('Record Layout Definitions')
#    close()

    pass
