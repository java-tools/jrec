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
        select_menu('File>>Export as CSV file')

        if frame('Export1 - DTAR020_tst1.bin:0'):
            select('names on first line', 'true')
            select('Edit Output File', 'true')
            select('Keep screen open', 'true')
            select('JTable_28', 'false', '{2, Include}')
            select('JTable_28', 'false', '{3, Include}')
            select('JTable_28', 'rows:[4],columns:[Field Name]')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            assert_content('JTable_22', [ ['63604808', '20', '1', '4870'],
['69684558', '20', '1', '19000'],
['69684558', '20', '-1', '-19000'],
['69694158', '20', '1', '5010'],
['62684671', '20', '1', '69990'],
['62684671', '20', '-1', '-69990'],
['61664713', '59', '1', '17990'],
['61664713', '59', '-1', '-17990'],
['61684613', '59', '1', '12990'],
['68634752', '59', '1', '8990'],
['60694698', '59', '1', '3990'],
['60664659', '59', '1', '3990'],
['60614487', '59', '1', '5950'],
['68654655', '166', '1', '5080'],
['69624033', '166', '1', '18190'],
['60604100', '166', '1', '13300'],
['68674560', '166', '1', '5990']
])
##            select('JTable_22', 'rows:[0],columns:[2|STORE-NO]')
##            select('JTable_22', 'rows:[0],columns:[2|STORE-NO]')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['63604808\t20\t1\t4870'],
['69684558\t20\t1\t19000'],
['69684558\t20\t-1\t-19000'],
['69694158\t20\t1\t5010'],
['62684671\t20\t1\t69990'],
['62684671\t20\t-1\t-69990'],
['61664713\t59\t1\t17990'],
['61664713\t59\t-1\t-17990'],
['61684613\t59\t1\t12990'],
['68634752\t59\t1\t8990'],
['60694698\t59\t1\t3990'],
['60664659\t59\t1\t3990'],
['60614487\t59\t1\t5950'],
['68654655\t166\t1\t5080'],
['69624033\t166\t1\t18190'],
['60604100\t166\t1\t13300'],
['68674560\t166\t1\t5990']
])
        close()

##        window_closed('Record Editor')
    close()

    pass
