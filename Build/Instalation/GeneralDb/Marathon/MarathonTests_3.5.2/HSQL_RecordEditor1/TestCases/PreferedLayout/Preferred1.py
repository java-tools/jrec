#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
        click('Edit')
    close()

    if frame('Table:  - Ams_PODownload_20041231.txt:0'):
        click('New')
    close()

    if frame('Table:  - Ams_PODownload_20041231.txt:1'):
        assert_content('Layouts', [ ['ams PO Download: Detail', 'ams PO Download: Header', 'ams PO Download: Allocation', 'Prefered', 'Full Line']
])
        assert_content('Layouts', [ ['ams PO Download: Detail', 'ams PO Download: Header', 'ams PO Download: Allocation', 'Prefered', 'Full Line']
])
        assert_p('Layouts', 'Text', 'Prefered')
    close()

    if frame('Record:  - Ams_PODownload_20041231.txt:0'):
        select('JTable_24', 'rows:[3],columns:[Data]')
        assert_p('JTable_24', 'Text', '', '{4, Text}')
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
        click('Close')
##        select('JTable_24', 'rows:[3],columns:[Data]')
    close()

    if frame('Table:  - Ams_PODownload_20041231.txt:0'):
        click('Close')
    close()

    pass