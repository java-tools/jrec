#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        select_menu('Record Layouts>>Edit Layout')

        if frame('Edit Record Layout - DB  - Record Edit - hsqldb:1'):
            select('Record Name', 'ww%')
            select('Description', '%')
            assert_content('JTable_23', [ ['wwFile', 'ww File Def'],
['wwHeader', 'File Header'],
['wwProd01', 'Prod 01 Record'],
['wwProd02', ''],
['wwProd05', ''],
['wwProd07', ''],
['wwProd09', ''],
['wwProd11', ''],
['wwProd12', ''],
['wwProd15', ''],
['wwProdFoot', ''],
['wwProdHead', ''],
['wwTrailer', '']
])
            select('JTable_23', 'rows:[0],columns:[Record Name]')
            
            assert_content('net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl_56', [ ['', 'wwProd01', '', '', '', '', ''],
['', 'wwProd02', '', '', '', '', ''],
['', 'wwProd05', '', '', '', '', ''],
['', 'wwProd07', '', '', '', '', ''],
['', 'wwProd09', '', '', '', '', ''],
['', 'wwProd11', '', '', '', '', ''],
['', 'wwProd12', '', '', '', '', ''],
['', 'wwProd15', '', '', '', '', ''],
['', 'wwProdFoot', '', '', '', '', ''],
['', 'wwTrailer', '', '', '', '', ''],
['', 'wwProdHead', '', '', '', '', ''],
['', 'wwHeader', '', '', '', '', 'wwProd01']
])
        close()

    close()

    pass
