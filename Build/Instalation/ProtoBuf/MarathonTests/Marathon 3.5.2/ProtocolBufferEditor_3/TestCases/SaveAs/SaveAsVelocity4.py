#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.7.0_02")
    if frame(' - Open File:0'):
##        select_menu('File>>Recent Files>>dtar020_tst1.bin')

        select('File', commonBits.sampleDir() + 'DTAR020_tst1.bin')
        click('Edit')
    close()

    if window('Protocol Buffer Editor'):

##        select_menu('File>>Export via Velociy Skelton>>toCsv_Comma.vm')
        select_menu('File>>SaveAs_Velocity>>toCsv_Comma.vm')

        if frame('Export - DTAR020_tst1.bin:0'):
            select('Edit Output File', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

##        if window(''):
        if window('<NoTitle>'):
            click('Go')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            assert_content('JTable_22', [ ['63604808', '20', '40118', '170', '1', '4870'],
['69684558', '20', '40118', '280', '1', '19000'],
['69684558', '20', '40118', '280', '-1', '-19000'],
['69694158', '20', '40118', '280', '1', '5010'],
['62684671', '20', '40118', '685', '1', '69990'],
['62684671', '20', '40118', '685', '-1', '-69990'],
['61664713', '59', '40118', '335', '1', '17990'],
['61664713', '59', '40118', '335', '-1', '-17990'],
['61684613', '59', '40118', '335', '1', '12990'],
['68634752', '59', '40118', '410', '1', '8990'],
['60694698', '59', '40118', '620', '1', '3990'],
['60664659', '59', '40118', '620', '1', '3990'],
['60614487', '59', '40118', '878', '1', '5950'],
['68654655', '166', '40118', '60', '1', '5080'],
['69624033', '166', '40118', '80', '1', '18190'],
['60604100', '166', '40118', '80', '1', '13300'],
['68674560', '166', '40118', '170', '1', '5990']
])
        close()
    close()

    pass
