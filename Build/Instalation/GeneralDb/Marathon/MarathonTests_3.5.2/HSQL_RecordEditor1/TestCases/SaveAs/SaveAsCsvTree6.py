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
            select('JTable_10', 'STORE-NO', '{0, Field}')
            select('JTable_10', 'DEPT-NO', '{1, Field}')
            select('JTable_10', 'rows:[1],columns:[Field]')
            click('Build Tree')
        close()

        select_menu('File>>Export as CSV file')

        if frame('Export - DTAR020_tst1.bin:0'):
            select('JTable_34', 'false', '{1, Include}')
            select('JTable_34', 'false', '{3, Include}')
            select('JTable_34', 'rows:[3],columns:[Include]')
            select('Edit Output File', 'true')
            select('Only export Nodes with Data', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            assert_content('JTable_22', [ ['File', '20', '170', '', '63604808', '40118', '1', '4.87'],
['File', '20', '280', '', '69684558', '40118', '1', '19.00'],
['File', '20', '280', '', '69684558', '40118', '-1', '-19.00'],
['File', '20', '280', '', '69694158', '40118', '1', '5.01'],
['File', '20', '685', '', '62684671', '40118', '1', '69.99'],
['File', '20', '685', '', '62684671', '40118', '-1', '-69.99'],
['File', '59', '335', '', '61664713', '40118', '1', '17.99'],
['File', '59', '335', '', '61664713', '40118', '-1', '-17.99'],
['File', '59', '335', '', '61684613', '40118', '1', '12.99'],
['File', '59', '410', '', '68634752', '40118', '1', '8.99'],
['File', '59', '620', '', '60694698', '40118', '1', '3.99'],
['File', '59', '620', '', '60664659', '40118', '1', '3.99'],
['File', '59', '878', '', '60614487', '40118', '1', '5.95'],
['File', '166', '60', '', '68654655', '40118', '1', '5.08'],
['File', '166', '80', '', '69624033', '40118', '1', '18.19'],
['File', '166', '80', '', '60604100', '40118', '1', '13.30'],
['File', '166', '170', '', '68674560', '40118', '1', '5.99']
])
            select('JTable_22', 'rows:[0],columns:[2|Level_2]')
            click('Close')
        close()
    close()

    pass
