#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if window('Record Editor'):
        select_menu('Edit>>Compare Menu')

        if frame('Compare Menu - Menu:0'):
            click('*_2')
        close()

        if frame(' - Single Layout Compare:0'):
            select('Old File', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
            select('New File', commonBits.sampleDir() + 'Ams_PODownload_20041231_Compare1.txt')
            click('Right')
            select('JTabbedPane_7', 'tabIndex-0')
            click('Right')
            select('JTabbedPane_7', 'tabIndex-0')
            click('Compare')
        close()

        if frame('Table Display  - Single Layout Compare:3'):
            assert_content('JTable_14', [ ['', 'Old', '2', 'D1', '7.0000', '0.0002', '2222500000000', '', '43314531', '2075359', '45614531', ' DONKEY 24-006607 SHWL WRAP CARD', '', '', '', '', '', '', '', '', '', ''],
['', 'New', '2', '', '17.0000', '0.0102', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '3', 'S1', '5043', '1', '5045', '1', '5076', '1', '5079', '1', '5151', '1', '5072', '1', '', '0', '', '0', '', '0'],
['', 'New', '3', '', '', '10', '9045', '2', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '4', 'D1', '4.0000', '148.3200', '0', '', '5614944', '2075360', '5614944', ' MILK 24-006607 SHWL WRAP CARD', '', '', '', '', '', '', '', '', '', ''],
['', 'New', '4', '', '14.0000', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '5', 'S1', '5045', '1', '5076', '1', '3331', '49440001', '', '0', '', '0', '', '0', '', '0', '', '0', '', '0'],
['', 'New', '5', '', '', '11', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '6', 'D1', '48.0000', '148.3200', '0', '', '55615071', '2075361', '55615071', ' M.ROSE 24-006607 SHWL WRAP CARD', '', '', '', '', '', '', '', '', '', ''],
['', 'New', '6', '', '8.0000', '48.3200', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '7', 'S1', '5036', '3', '5043', '5', '3331', '50710003', '5065', '4', '5069', '4', '5076', '4', '5079', '2', '5094', '4', '5128', '3'],
['', 'New', '7', '', '', '6', '', '51', '', '', '', '', '', '', '', '', '', '', '', '', '', '']
])
 ##           select('JTable_14', 'rows:[0],columns:[]')
            click('JTable_14', 11, 9)
        close()

        if frame('Record Display  - Line:3'):
            assert_content('JTable_14', [ ['Line Number', '', '', '2', '2'],
['Record Type', '1', '2', 'D1', ''],
['Pack Qty', '3', '9', '7.0000', '17.0000'],
['Pack Cost', '12', '13', '0.0002', '0.0102'],
['APN', '25', '13', '2222500000000', ''],
['Filler', '38', '1', '', ''],
['Product', '39', '8', '43314531', ''],
['pmg dtl tech key', '72', '15', '2075359', ''],
['Case Pack id', '87', '15', '45614531', ''],
['Product Name', '101', '50', ' DONKEY 24-006607 SHWL WRAP CARD', '']
])
            assert_p('JTable_14', 'Background', '[r=255,g=255,b=255]', '{2, Old}')
            assert_p('JTable_14', 'Background', '[r=255,g=255,b=255]', '{3, Old}')
            doubleclick('JTable_14', '{3, Old}')
            select('JTable_14', 'rows:[3],columns:[Old]')
            click('Right')
            click('Right')
            click('Close')
        close()



##        window_closed('Record Editor')
    close()

    pass
