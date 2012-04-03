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

    if frame('Table:  - DTAR020_tst1.bin:0'):
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
        select('JTable_22', 'rows:[3,4,5,6],columns:[11 - 4|DATE]')
##        keystroke('JTable_22', 'Ctrl+D')
        keystroke('JTable_22', 'Delete')
    close()

    if window('Line Delete confirmation '):
       click('No')
    close()

    if frame('Table:  - DTAR020_tst1.bin:0'):
        select('JTable_22', 'rows:[],columns:[11 - 4|DATE]')
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
 
    close()

    pass