#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleDir() + 'Ams_PODownload_ZZZ.txt')
        click('Edit')
    close()

    if frame('Record:  - Ams_PODownload_ZZZ.txt:0'):
        select('JTable_24', 'rows:[3],columns:[Data]')
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
        assert_p('Layouts', 'Content[0]', '[ams PO Download: Detail, ams PO Download: Header, ams PO Download: Allocation, Full Line]')
        select('JTable_24', 'rows:[3],columns:[Data]')
        click('Close')
 ##       select('JTable_24', 'rows:[3],columns:[Data]')
    close()

    if frame('Table:  - Ams_PODownload_ZZZ.txt:0'):
        assert_content('JTable_22', [ ['', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']
])
        select('JTable_22', 'rows:[0,1,2],columns:[3 - 9|Pack Qty]')
        click('Close')
    close()

 

    pass