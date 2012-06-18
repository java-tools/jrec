#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        select_menu('File>>Recent Files>>DTAR020_tst1.bin_2')
        click('Export')

        if frame('Export - DTAR020_tst1.bin:0'):
            select('File Name_2', 'CSV')
##            select('JTabbedPane_16', 'CSV')
#            select('JTable_31', 'rows:[1],columns:[Include]')
#            select('JTable_31', 'rows:[4],columns:[Include]')
            select('JTable_30', 'false', '{1, Include}')
            select('JTable_30', 'false', '{4, Include}')
            select('Edit Output File', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            assert_content('JTable_22', [ ['63604808', '40118', '170', '4.87'],
['69684558', '40118', '280', '19.00'],
['69684558', '40118', '280', '-19.00'],
['69694158', '40118', '280', '5.01'],
['62684671', '40118', '685', '69.99'],
['62684671', '40118', '685', '-69.99'],
['61664713', '40118', '335', '17.99'],
['61664713', '40118', '335', '-17.99'],
['61684613', '40118', '335', '12.99'],
['68634752', '40118', '410', '8.99'],
['60694698', '40118', '620', '3.99'],
['60664659', '40118', '620', '3.99'],
['60614487', '40118', '878', '5.95'],
['68654655', '40118', '60', '5.08'],
['69624033', '40118', '80', '18.19'],
['60604100', '40118', '80', '13.30'],
['68674560', '40118', '170', '5.99']
])
        close()

    close()

    pass
