#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_22")

    if window('Record Editor'):
        select_menu('File>>Compare Menu')

        if frame('Compare Menu - Menu:0'):
            click('*_3')
        close()

        if frame(' - Two Layout Compare:0'):
            select('Old File Name', '/C:/JavaPrograms/RecordEdit/HSQL/SampleFiles/Ams_PODownload_20041231.txt')
            click('Right')
            select('JTabbedPane_7', 'tabIndex-0')
            select('New File Name', '/C:/JavaPrograms/RecordEdit/HSQL/SampleFiles/xmlAms_PODownload_20041231.txt.xml')
            click('Right')
            select('JTabbedPane_7', 'tabIndex-0')
            select('JTable_20', 'rows:[0],columns:[Equivalent Record]')
            select('JTable_20', 'ams_PO_Download__Detail', '{0, Equivalent Record}')
            select('JTable_20', 'ams_PO_Download__Header', '{1, Equivalent Record}')
            select('JTable_20', 'ams_PO_Download__Allocation', '{2, Equivalent Record}')
            select('JTable_20', 'rows:[2],columns:[Equivalent Record]')
            click('Right')
            select('JTabbedPane_7', 'tabIndex-0')
            click('Compare')

        close()

        if frame('Table Display  - Single Layout Compare:3'):
            assert_p('JTable_14', 'Text', '', '{5, Case Pack id}')
            assert_content('JTable_14', [ ['', 'Old', '3', 'S1', '5043', '1', '5045', '1', '5076', '1', '5079', '1', '5151', '1', '5072', '1', '', '0', '', '0', '', '0'],
['', 'New', '3', '', '', '', '', '11', '', '12', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '4', 'D1', '4.0000', '148.3200', '0', '5614944', '2075360', '5614944', ' MILK 24-006607 SHWL WRAP CARD', '', '', '', '', '', '', '', '', '', '', ''],
['', 'New', '4', '', '', '', '111', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['', 'Old', '5', 'S1', '5045', '1', '5076', '1', '3331', '49440001', '', '0', '', '0', '', '0', '', '0', '', '0', '', '0'],
['', 'New', '5', '', '', '', '', '', '', '', '', '22', '', '33', '', '', '', '', '', '', '', ''],
['', 'Old', '12', 'D1', '16.0000', '6228148.3200', '2222', '2224531', '2075348', '5614531', ' DONKEY 24-006607 SHWL WRAP CARD', '', '', '', '', '', '', '', '', '', '', ''],
['', 'New', '12', '', '', '62281483200', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']
])
            click('Close')
        close()

        window_closed('Record Editor')
    close()

    pass
