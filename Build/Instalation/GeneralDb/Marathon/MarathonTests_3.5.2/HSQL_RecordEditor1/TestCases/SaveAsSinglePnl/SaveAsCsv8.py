#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', 'C:\\Users\\mum/RecordEditor_HSQL/SampleFiles/DTAR020_tst1.bin')
        click('Edit')
    close()

    if window('Record Editor'):
        select_menu('File>>Export as CSV file')

        if frame('Export1 - DTAR020_tst1.bin:0'):
            select('names on first line', 'true')
            select('Edit Output File', 'true')
            select('Keep screen open', 'true')
            select('JTable_29', 'false', '{2, Include}')
            select('JTable_29', 'false', '{3, Include}')
            select('JTable_29', 'rows:[4],columns:[Field Name]')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            assert_content('JTable_22', [ ['63604808', '20', '1', '4.87'],
['69684558', '20', '1', '19.00'],
['69684558', '20', '-1', '-19.00'],
['69694158', '20', '1', '5.01'],
['62684671', '20', '1', '69.99'],
['62684671', '20', '-1', '-69.99'],
['61664713', '59', '1', '17.99'],
['61664713', '59', '-1', '-17.99'],
['61684613', '59', '1', '12.99'],
['68634752', '59', '1', '8.99'],
['60694698', '59', '1', '3.99'],
['60664659', '59', '1', '3.99'],
['60614487', '59', '1', '5.95'],
['68654655', '166', '1', '5.08'],
['69624033', '166', '1', '18.19'],
['60604100', '166', '1', '13.30'],
['68674560', '166', '1', '5.99']
])
            select('JTable_22', 'rows:[0],columns:[2|STORE-NO]')
            select('JTable_22', 'rows:[0],columns:[2|STORE-NO]')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['63604808\t20\t1\t4.87'],
['69684558\t20\t1\t19.00'],
['69684558\t20\t-1\t-19.00'],
['69694158\t20\t1\t5.01'],
['62684671\t20\t1\t69.99'],
['62684671\t20\t-1\t-69.99'],
['61664713\t59\t1\t17.99'],
['61664713\t59\t-1\t-17.99'],
['61684613\t59\t1\t12.99'],
['68634752\t59\t1\t8.99'],
['60694698\t59\t1\t3.99'],
['60664659\t59\t1\t3.99'],
['60614487\t59\t1\t5.95'],
['68654655\t166\t1\t5.08'],
['69624033\t166\t1\t18.19'],
['60604100\t166\t1\t13.30'],
['68674560\t166\t1\t5.99']
])
        close()

##        window_closed('Record Editor')
    close()

    pass