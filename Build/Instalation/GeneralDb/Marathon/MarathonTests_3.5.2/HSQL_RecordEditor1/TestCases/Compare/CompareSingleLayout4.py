#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        select_menu('Utilities>>Compare Menu')


        if frame('Compare Menu - Menu:0'):
            click('*_2')
        close()

        if frame(' - Single Layout Compare:0'):
            select('Old File', commonBits.sampleDir() + 'Ams_PODownload_20041231_Compare1.txt')
            select('New File', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
            click('Right')
            select('JTabbedPane_7', 'tabIndex-0')
            click('Right')
            select('JTabbedPane_7', 'tabIndex-0')
            click('Compare')
        close()

        if frame('Table Display  - Single Layout Compare:3'):
            assert_p('JTable_14', 'Text', '2075360', '{4, pmg dtl tech key}')
            assert_p('JTable_14', 'Text', '2075360', '{4, pmg dtl tech key}')
            assert_p('JTable_14', 'Text', '2075360', '{4, pmg dtl tech key}')
            assert_content('JTable_14', [ ['', 'Old', '2', 'D1', '17.0000', '0.0102', '2222500000000', '', '43314531', '2075359', '45614531', ' DONKEY 24-006607 SHWL WRAP CARD', '', '', '', '', '', '', '', '', '', ''],
['', 'New', '2', '', '7.0000', '0.0002', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '3', 'S1', '5043', '10', '9045', '2', '5076', '1', '5079', '1', '5151', '1', '5072', '1', '', '0', '', '0', '', '0'],
['', 'New', '3', '', '', '1', '5045', '1', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '4', 'D1', '14.0000', '148.3200', '0', '', '5614944', '2075360', '5614944', ' MILK 24-006607 SHWL WRAP CARD', '', '', '', '', '', '', '', '', '', ''],
['', 'New', '4', '', '4.0000', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '5', 'S1', '5045', '11', '5076', '1', '3331', '49440001', '', '0', '', '0', '', '0', '', '0', '', '0', '', '0'],
['', 'New', '5', '', '', '1', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '6', 'D1', '8.0000', '48.3200', '0', '', '55615071', '2075361', '55615071', ' M.ROSE 24-006607 SHWL WRAP CARD', '', '', '', '', '', '', '', '', '', ''],
['', 'New', '6', '', '48.0000', '148.3200', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '7', 'S1', '5036', '6', '5043', '51', '3331', '50710003', '5065', '4', '5069', '4', '5076', '4', '5079', '2', '5094', '4', '5128', '3'],
['', 'New', '7', '', '', '3', '', '5', '', '', '', '', '', '', '', '', '', '', '', '', '', '']
])
            click('Close')
        close()

##        window_closed('Record Editor')
    close()

    pass
