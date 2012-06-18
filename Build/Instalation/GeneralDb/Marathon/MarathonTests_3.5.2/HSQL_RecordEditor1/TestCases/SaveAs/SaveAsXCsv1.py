#{{{ Marathon
from default import *
#}}} Marathon


def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        ##select_menu('File>>Recent Files>>DTAR020_tst1.bin')
        select_menu('File>>Recent Files>>DTAR020_tst1.bin_2')
        select_menu('File>>Export as CSV file')

        if frame('Export - DTAR020_tst1.bin:0'):
##            doubleclick('JTable_33', '{1, Field Name}')
##            select('JTable_33', 'false', '{1, Include}')
##            select('JTable_33', 'false', '{4, Include}')
##            select('JTable_33', 'rows:[4],columns:[Include]')
            doubleclick('JTable_30', '{1, Field Name}')
            select('JTable_30', 'false', '{1, Include}')
            select('JTable_30', 'false', '{4, Include}')
            select('JTable_30', 'rows:[4],columns:[Include]')
            select('Edit Output File', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            select('JTable_22', 'rows:[7],columns:[2|DATE]')
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

##        window_closed('Record Editor')
    close()

    pass
