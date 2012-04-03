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

    if window('Record Editor'):
        select_menu('View>>Sorted Field Tree')

        if frame('Create Sorted Tree - DTAR020_tst1.bin:0'):
            select('JTable_10', 'DEPT-NO', '{0, Field}')
            select('JTable_10', 'STORE-NO', '{1, Field}')
            select('JTable_10', 'rows:[1],columns:[Field]')
            click('Build Tree')
        close()

        if frame('Tree View - DTAR020_tst1.bin:0'):
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[11],columns:[DEPT-NO]')
            assert_content('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', [ ['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '68654655', '166', '40118', '60', '1', '5.08'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '69624033', '166', '40118', '80', '1', '18.19'],
['', '', '60604100', '166', '40118', '80', '1', '13.30'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '63604808', '20', '40118', '170', '1', '4.87'],
['', '', '', '', '', '', '', ''],
['', '', '68674560', '166', '40118', '170', '1', '5.99'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '69684558', '20', '40118', '280', '1', '19.00'],
['', '', '69684558', '20', '40118', '280', '-1', '-19.00'],
['', '', '69694158', '20', '40118', '280', '1', '5.01'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '61664713', '59', '40118', '335', '1', '17.99'],
['', '', '61664713', '59', '40118', '335', '-1', '-17.99'],
['', '', '61684613', '59', '40118', '335', '1', '12.99'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '68634752', '59', '40118', '410', '1', '8.99'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '60694698', '59', '40118', '620', '1', '3.99'],
['', '', '60664659', '59', '40118', '620', '1', '3.99'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '62684671', '20', '40118', '685', '1', '69.99'],
['', '', '62684671', '20', '40118', '685', '-1', '-69.99'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '60614487', '59', '40118', '878', '1', '5.95']
])
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[11],columns:[DEPT-NO]')
        close()

        click('Export')

        if frame('Export - DTAR020_tst1.bin:0'):
            select('JTabbedPane_16', 'Fixed')
            select('Edit Output File', 'true')
            select('space between fields', 'true')
            select('JTable_29', 'rows:[5],columns:[Include]')
            select('space between fields', 'true')
            select('JTable_29', 'rows:[2],columns:[Include]')
            select('space between fields', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('JTable_29', 'rows:[4],columns:[Include]')
            select('JTable_29', 'rows:[5],columns:[Include]')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('names on first line', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            click('Close')
        close()



##        window_closed('Record Editor')
    close()

    pass
