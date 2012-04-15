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
            select('Add Quote to all Text Fields', 'true')
            select('Edit Output File', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            assert_content('JTable_22', [ ['63604808', '20', '40118', '170', '1', '4.87'],
['69684558', '20', '40118', '280', '1', '19.00'],
['69684558', '20', '40118', '280', '-1', '-19.00'],
['69694158', '20', '40118', '280', '1', '5.01'],
['62684671', '20', '40118', '685', '1', '69.99'],
['62684671', '20', '40118', '685', '-1', '-69.99'],
['61664713', '59', '40118', '335', '1', '17.99'],
['61664713', '59', '40118', '335', '-1', '-17.99'],
['61684613', '59', '40118', '335', '1', '12.99'],
['68634752', '59', '40118', '410', '1', '8.99'],
['60694698', '59', '40118', '620', '1', '3.99'],
['60664659', '59', '40118', '620', '1', '3.99'],
['60614487', '59', '40118', '878', '1', '5.95'],
['68654655', '166', '40118', '60', '1', '5.08'],
['69624033', '166', '40118', '80', '1', '18.19'],
['60604100', '166', '40118', '80', '1', '13.30'],
['68674560', '166', '40118', '170', '1', '5.99']
])
#            select('JTable_22', 'rows:[0],columns:[2|STORE-NO]')
            click('Close')
#            select('JTable_22', '', '{0, 2|STORE-NO}')
#            select('JTable_22', 'rows:[0],columns:[2|STORE-NO]')
        close()

#        window_closed('Record Editor')
    close()

    pass