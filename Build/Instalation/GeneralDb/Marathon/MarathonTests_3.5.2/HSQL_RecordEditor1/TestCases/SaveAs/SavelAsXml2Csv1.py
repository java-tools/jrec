#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleXmlDir() + 'AmsLocationTest2.xml')
        click('Edit')
    close()

    if frame('Tree View - AmsLocationTest2.xml:0'):
        assert_content('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', [ ['', '', '', '', 'AMSLOCATIONTEST1.cbl', '', '', '', '', '', '', '', ''],
['', '', '******************************', '', '', '', '', '', '', '', '', '', ''],
['', '', '* Location Download', '', '', '', '', '', '', '', '', '', ''],
['', '', '******************************', '', '', '', '', '', '', '', '', '', ''],
['', '', '', '', '173', '01', 'Ams-Vendor', '1', '173', '', '', '', ''],
['', '', '', '', '3', '03', 'Brand', '1', '3', 'x(3)', '', 'True', ''],
['', '', '', '', '41', '03', 'Location-Details', '4', '41', '', '', '', ''],
['', '', '', '', '4', '05', 'Location-Number', '4', '4', '9(4)', 'true', 'True', ''],
['', '', '', '', '2', '05', 'Location-Type', '8', '2', 'XX', '', 'True', ''],
['', '', '', '', '35', '05', 'Location-Name', '10', '35', 'X(35)', '', 'True', ''],
['', '', '', '', '128', '03', 'Full-Address', '45', '128', '', '', '', ''],
['', '', '', '', '115', '05', 'Address-Lines', '45', '115', '', '', '', ''],
['', '', '', '', '40', '07', 'Address-1', '45', '40', 'X(40)', '', 'True', ''],
['', '', '', '', '40', '07', 'Address-2', '85', '40', 'X(40)', '', 'True', ''],
['', '', '', '', '35', '07', 'Address-3', '125', '35', 'X(35)', '', 'True', ''],
['', '', '', '', '10', '05', 'Postcode', '160', '10', '9(10)', 'true', 'True', ''],
['', '', '', '', '3', '05', 'State', '170', '3', 'XXX', '', 'True', ''],
['', '', '', '', '1', '03', 'Location-Active', '173', '1', 'X', '', 'True', '']
])
    close()

    if window('Record Editor'):
        select_menu('File>>Save as CSV file')

        if frame('Save as - AmsLocationTest2.xml:0'):
            select('Edit Output File', 'true')
            select('Delimiter', ':')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - AmsLocationTest2.xml.csv:0'):
            assert_content('JTable_22', [ ['copybook', '', '', '', '', '', '', '', 'AMSLOCATIONTEST1.cbl', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '******************************', '', '', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '* Location Download', '', '', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '******************************', '', '', '', '', '', '', '', ''],
['copybook', 'item', '', '', '', '', '', '', '173', '01', 'Ams-Vendor', '1', '173', '', ''],
['copybook', 'item', 'item', '', '', '', '', '', '3', '03', 'Brand', '1', '3', 'x(3)', ''],
['copybook', 'item', 'item', '', '', '', '', '', '41', '03', 'Location-Details', '4', '41', '', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '4', '05', 'Location-Number', '4', '4', '9(4)', 'true'],
['copybook', 'item', 'item', 'item', '', '', '', '', '2', '05', 'Location-Type', '8', '2', 'XX', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '35', '05', 'Location-Name', '10', '35', 'X(35)', ''],
['copybook', 'item', 'item', '', '', '', '', '', '128', '03', 'Full-Address', '45', '128', '', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '115', '05', 'Address-Lines', '45', '115', '', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '40', '07', 'Address-1', '45', '40', 'X(40)', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '40', '07', 'Address-2', '85', '40', 'X(40)', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '35', '07', 'Address-3', '125', '35', 'X(35)', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '10', '05', 'Postcode', '160', '10', '9(10)', 'true'],
['copybook', 'item', 'item', 'item', '', '', '', '', '3', '05', 'State', '170', '3', 'XXX', ''],
['copybook', 'item', 'item', '', '', '', '', '', '1', '03', 'Location-Active', '173', '1', 'X', '']
])
            select('JTable_22', 'rows:[0],columns:[2|Level_2]')
            select('JTable_24', 'rows:[4],columns:[Sl]')
        close()

        if frame('Table:  - AmsLocationTest2.xml.csv:1'):
            select('JTable_22', 'rows:[4],columns:[2|Level_2]')
            select('JTable_24', 'rows:[4],columns:[Sl]')
        close()

        if frame('Record:  - AmsLocationTest2.xml.csv:0'):
            assert_content('JTable_24', [ ['Level_1', '1', '', 'copybook', 'copybook'],
['Level_2', '2', '', 'item', 'item'],
['Level_3', '3', '', '', ''],
['Level_4', '4', '', '', ''],
['Level_5', '5', '', '', ''],
['Following~Text', '6', '', '', ''],
['Xml~Prefix', '7', '', '', ''],
['Xml~Namespace', '8', '', '', ''],
['display-length', '9', '', '173', '173'],
['level', '10', '', '01', '01'],
['name', '11', '', 'Ams-Vendor', 'Ams-Vendor'],
['position', '12', '', '1', '1'],
['storage-length', '13', '', '173', '173'],
['picture', '14', '', '', ''],
['numeric', '15', '', '', '']
])
            click('Close')
        close()

        if frame('Table:  - AmsLocationTest2.xml.csv:0'):
            select('JTable_24', 'rows:[4],columns:[Sl]')
            select('JTable_24', 'rows:[4],columns:[Sl]')
        close()

        select_menu('Window>>AmsLocationTest2.xml.csv>>Table: ')

        if frame('Table:  - AmsLocationTest2.xml.csv:0'):
            select('JTable_24', 'rows:[4],columns:[Sl]')
            click('Close')
        close()

        select_menu('Window>>AmsLocationTest2.xml>>Save as')

        if frame('Save as - AmsLocationTest2.xml:0'):
            select('Only export Nodes with Data', 'false')
            click('save file')
        close()

        if frame('Table:  - AmsLocationTest2.xml.csv:0'):
            assert_p('JTable_22', 'Text', '', '{11, 6|Following~Text}')
            assert_content('JTable_22', [ ['', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
['copybook', '', '', '', '', '', '', '', 'AMSLOCATIONTEST1.cbl', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '******************************', '', '', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '* Location Download', '', '', '', '', '', '', '', ''],
['copybook', 'XML Comment', '', '', '', '', '******************************', '', '', '', '', '', '', '', ''],
['copybook', 'item', '', '', '', '', '', '', '173', '01', 'Ams-Vendor', '1', '173', '', ''],
['copybook', 'item', 'item', '', '', '', '', '', '3', '03', 'Brand', '1', '3', 'x(3)', ''],
['copybook', 'item', 'item', '', '', '', '', '', '41', '03', 'Location-Details', '4', '41', '', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '4', '05', 'Location-Number', '4', '4', '9(4)', 'true'],
['copybook', 'item', 'item', 'item', '', '', '', '', '2', '05', 'Location-Type', '8', '2', 'XX', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '35', '05', 'Location-Name', '10', '35', 'X(35)', ''],
['copybook', 'item', 'item', '', '', '', '', '', '128', '03', 'Full-Address', '45', '128', '', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '115', '05', 'Address-Lines', '45', '115', '', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '40', '07', 'Address-1', '45', '40', 'X(40)', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '40', '07', 'Address-2', '85', '40', 'X(40)', ''],
['copybook', 'item', 'item', 'item', 'item', '', '', '', '35', '07', 'Address-3', '125', '35', 'X(35)', ''],
['copybook', 'item', 'item', 'item', '', '', '', '', '10', '05', 'Postcode', '160', '10', '9(10)', 'true'],
['copybook', 'item', 'item', 'item', '', '', '', '', '3', '05', 'State', '170', '3', 'XXX', ''],
['copybook', 'item', 'item', '', '', '', '', '', '1', '03', 'Location-Active', '173', '1', 'X', '']
])
            select('JTable_22', 'rows:[0],columns:[2|Level_2]')
            click('Close')
##            select('JTable_22', 'rows:[0],columns:[2|Level_2]')
        close()

        if frame('Save as - AmsLocationTest2.xml:0'):
            select('JTable_37', 'rows:[3],columns:[Include]')
            select('JTable_37', 'rows:[2],columns:[Include]')
            select('JTable_37', 'rows:[6],columns:[Include]')
            click('save file')
        close()

        if frame('Table:  - AmsLocationTest2.xml.csv:0'):
            select('JTable_22', 'rows:[0],columns:[2|Level_2]')
            select('JTable_24', 'rows:[7],columns:[Sl]')
        close()

        if frame('Table:  - AmsLocationTest2.xml.csv:1'):
            select('JTable_22', 'rows:[7],columns:[2|Level_2]')
            select('JTable_24', 'rows:[7],columns:[Sl]')
        close()

    pass