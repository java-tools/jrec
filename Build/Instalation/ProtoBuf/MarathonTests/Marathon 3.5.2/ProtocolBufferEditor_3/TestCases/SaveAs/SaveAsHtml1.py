#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleDir() + 'DTAR020_tst1.bin')
        click('Edit')
    close()

    if window('Protocol Buffer Editor'):
        select_menu('File>>Export as HTML 1 tbl')

        if frame('Export - DTAR020_tst1.bin:0'):
            select('Edit Output File', 'true')
            select('Keep screen open', 'true')
            select('File Name', commonBits.sampleDir() + 'DTAR020_tst1.bin.Xml')
            click('save file')
        close()

        if frame('Tree View - DTAR020_tst1.bin.Xml:0'):
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'th', '{6, Tree}')
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[6],columns:[Tree]')
            assert_content('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', [ ['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', 'Keycode_no'],
['', '', '', '', '', 'Store_No'],
['', '', '', '', '', 'DATE'],
['', '', '', '', '', 'Dept_No'],
['', '', '', '', '', 'Qty_Sold'],
['', '', '', '', '', 'Sale_Price'],
['', '', '', '', '', ''],
['', '', '', '', '', '63604808'],
['', '', '', '', '', '20'],
['', '', '', '', '', '40118'],
['', '', '', '', '', '170'],
['', '', '', '', '', '1'],
['', '', '', '', '', '4870'],
['', '', '', '', '', ''],
['', '', '', '', '', '69684558'],
['', '', '', '', '', '20'],
['', '', '', '', '', '40118'],
['', '', '', '', '', '280'],
['', '', '', '', '', '1'],
['', '', '', '', '', '19000'],
['', '', '', '', '', ''],
['', '', '', '', '', '69684558'],
['', '', '', '', '', '20'],
['', '', '', '', '', '40118'],
['', '', '', '', '', '280'],
['', '', '', '', '', '-1'],
['', '', '', '', '', '-19000'],
['', '', '', '', '', ''],
['', '', '', '', '', '69694158'],
['', '', '', '', '', '20'],
['', '', '', '', '', '40118'],
['', '', '', '', '', '280'],
['', '', '', '', '', '1'],
['', '', '', '', '', '5010'],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', '']
])
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[6],columns:[Tree]')
            click('Close')
##            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[6],columns:[Tree]')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('Edit Output File', 'false')
            select('File Name', commonBits.sampleDir() + 'DTAR020_tst1.bin.html')
            click('save file')
            click('Close')
        close()

###        window_closed('Record Editor')
    close()

    pass
