#{{{ Marathon
from default import *
import os
#}}} Marathon

from Modules import commonBits

def test():

##    os.remove 
    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleDir() + 'Ams_PODownload_ZZZ.txt')
        click('Edit')
    close()

    if frame('Record:  - Ams_PODownload_ZZZ.txt:0'):
        assert_p('JTable_24', 'Text', '', '{3, Data}')
        assert_content('JTable_24', [ ['Record Type', '1', '2', '', ''],
['Pack Qty', '3', '9', '', ''],
['Pack Cost', '12', '13', '', ''],
['APN', '25', '13', '', ''],
['Filler', '38', '1', '', ''],
['Product', '39', '8', '', ''],
['pmg dtl tech key', '72', '15', '', ''],
['Case Pack id', '87', '15', '', ''],
['Product Name', '101', '50', '', '']
])
        assert_p('Layouts', 'Text', 'ams PO Download: Detail')
        assert_content('Layouts', [ ['ams PO Download: Detail', 'ams PO Download: Header', 'ams PO Download: Allocation', 'Full Line']
])
        select('JTable_24', 'H1', '{0, Data}')
        select('JTable_24', '1333', '{1, Data}')
        select('JTable_24', '121', '{2, Data}')
        select('JTable_24', '1212121', '{3, Data}')
        select('JTable_24', '121', '{5, Data}')
        select('JTable_24', '12121', '{6, Data}')
        select('JTable_24', '1331', '{7, Data}')
        select('JTable_24', '121 Prd', '{8, Data}')
        select('JTable_24', 'D1', '{0, Data}')
        select('JTable_24', 'rows:[2],columns:[Data]')
        assert_content('JTable_24', [ ['Record Type', '1', '2', 'D1', 'D1'],
['Pack Qty', '3', '9', '1333.0000', '013330000'],
['Pack Cost', '12', '13', '121.0000', '0000001210000'],
['APN', '25', '13', '1212121', '0000001212121'],
['Filler', '38', '1', '\x00', '\x00'],
['Product', '39', '8', '121', '00000121'],
['pmg dtl tech key', '72', '15', '12121', '12121'],
['Case Pack id', '87', '15', '1331          1', '1331          1'],
['Product Name', '101', '50', '121 Prd', '121 Prd']
])
        select('JTable_24', 'rows:[2],columns:[Data]')
        click('Close')
##       select('JTable_24', 'rows:[2],columns:[Data]')
    close()

    if frame('Table:  - Ams_PODownload_ZZZ.txt:0'):
        assert_content('JTable_22', [ ['D1', '1.333', '0', '121000000000', '012121', '21\x0000000', '12', '1\x00', '\x00\x00\x00\x00', '\x00\x00\x00\x00\x00\x00', '\x00\x00\x00\x00\x00\x00', '\x00', '\x00\x00\x00121', '2', '1', '', '13', '', '']
])
        select('JTable_22', 'rows:[0,1,2],columns:[3 - 9|Pack Qty]')
        click('New')
##        select('JTable_22', 'rows:[0,1,2],columns:[3 - 9|Pack Qty]')
    close()

    if frame('Record:  - Ams_PODownload_ZZZ.txt:0'):
        select('Layouts', 'ams PO Download: Allocation')
        select('JTable_24', 'S1', '{0, Data}')
        select('JTable_24', '1', '{1, Data}')
        select('JTable_24', '1', '{2, Data}')
        select('JTable_24', '12', '{3, Data}')
        select('JTable_24', '12', '{4, Data}')
        select('JTable_24', '13', '{5, Data}')
        select('JTable_24', '1331', '{6, Data}')
        select('JTable_24', 'rows:[7],columns:[Data]')
        click('Close')
    close()

    if window('Record Editor'):
        select_menu('Window>>Ams_PODownload_ZZZ.txt>>Table: ')

        if frame('Table:  - Ams_PODownload_ZZZ.txt:0'):
##            select('JTable_22', 'rows:[0,1,2,3,4],columns:[3 - 9|Pack Qty]')
            click('Save')
            click('Close')
##            select('JTable_22', 'rows:[0,1,2,3,4],columns:[3 - 9|Pack Qty]')
        close()

##        if window('Save Changes to file: C:\\Users\\mum/RecordEditor_HSQL\\SampleFiles/Ams_PODownload_ZZZ.txt'):
##            click('OptionPane.button')
##        close()

        if frame(' - Open File:0'):
            click('Edit')
        close()

        if frame('Table:  - Ams_PODownload_ZZZ.txt:0'):
            assert_content('JTable_22', [ ['D1', '1333.0000', '121.0000', '1212121', '\x00', '121', '12121', '1331          1', '121 Prd', '', '', '', '', '', '', '', '', '', ''],
['S1', '1', '1', '12', '12', '13', '1331', '', '', '', '', '', '', '', '', '', '', '', '']
])
            select('JTable_22', 'rows:[0],columns:[3 - 9|Pack Qty]')
            select('JTable_24', 'rows:[0],columns:[Sl]')
        close()

        if frame('Table:  - Ams_PODownload_ZZZ.txt:1'):
            select('JTable_22', 'rows:[0],columns:[3 - 9|Pack Qty]')
            select('JTable_24', 'rows:[0],columns:[Sl]')
        close()

        if frame('Record:  - Ams_PODownload_ZZZ.txt:0'):
            assert_content('JTable_24', [ ['Record Type', '1', '2', 'D1', 'D1'],
['Pack Qty', '3', '9', '1333.0000', '013330000'],
['Pack Cost', '12', '13', '121.0000', '0000001210000'],
['APN', '25', '13', '1212121', '0000001212121'],
['Filler', '38', '1', '\x00', '\x00'],
['Product', '39', '8', '121', '00000121'],
['pmg dtl tech key', '72', '15', '12121', '12121'],
['Case Pack id', '87', '15', '1331          1', '1331          1'],
['Product Name', '101', '50', '121 Prd', '121 Prd']
])
            assert_p('Layouts', 'Text', 'ams PO Download: Detail')
            assert_content('Layouts', [ ['ams PO Download: Detail', 'ams PO Download: Header', 'ams PO Download: Allocation', 'Full Line']
])
            click('Right')
            select('JTable_24', 'rows:[4],columns:[Data]')
            assert_content('JTable_24', [ ['Record Type', '1', '2', 'S1', 'S1'],
['DC Number 1', '3', '4', '1', '0001'],
['Pack Quantity 1', '7', '8', '1', '00000001'],
['DC Number 2', '15', '4', '12', '0012'],
['Pack Quantity 2', '19', '8', '12', '00000012'],
['DC Number 4', '39', '4', '13', '13'],
['Pack Quantity 4', '43', '8', '1331', '00001331'],
['DC Number 5', '51', '4', '', ''],
['Pack Quantity 5', '55', '8', '', ''],
['DC Number 6', '63', '4', '', ''],
['Pack Quantity 6', '67', '8', '', ''],
['DC Number 7', '75', '4', '', ''],
['Pack Quantity 7', '79', '8', '', ''],
['DC Number 8', '87', '4', '', ''],
['Pack Quantity 8', '91', '8', '', ''],
['DC Number 9', '99', '4', '', ''],
['Pack Quantity 9', '103', '8', '', ''],
['DC Number 10', '111', '4', '', ''],
['Pack Quantity 10', '115', '8', '', '']
])
            assert_p('Layouts', 'Text', 'ams PO Download: Allocation')
            assert_content('Layouts', [ ['ams PO Download: Detail', 'ams PO Download: Header', 'ams PO Download: Allocation', 'Full Line']
])
            select('JTable_24', 'rows:[4],columns:[Data]')
            click('Delete')
            select('JTable_24', 'rows:[4],columns:[Data]')
            click('Delete')
        close()

        if frame('Table:  - Ams_PODownload_ZZZ.txt:0'):
            click('Save')
        close()

    close()

    pass
