#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleDir() + 'wx1File.txt')
        click('Edit')
    close()

    if window('Record Editor'):
        select_menu('View>>Record Layout Tree')
        click('Export')

        if frame('Export - wx1File.txt:0'):
            select('JTabbedPane_16', 'Xml')
            select('Edit Output File', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Tree View - wx1File.txt.xml:0'):
            assert_content('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', [ ['', '', 'UTF-8', '1.0', 'false', '', '', '', '', '', ''],
['', '', '', '', '', '', '', '', '', '', ''],
['', '', '', '', 'H', 'HD', '20100101', '1', '', '', ''],
['', '', '', '', 'P', 'HD', '121', '12121', '', '', ''],
['', '', '', '', 'P', '01', '21', '22', '23', 'True', ''],
['', '', '', '', 'P', '02', '21', '22', '23', 'True', ''],
['', '', '', '', 'P', '05', '21  00', '2223', '', 'True', ''],
['', '', '', '', 'P', '02', '21', '22', '23', 'True', ''],
['', '', '', '', 'P', '01', '21', '22', '23', 'True', ''],
['', '', '', '', 'P', '01', '21', '22', '23', 'True', ''],
['', '', '', '', 'P', 'HD', '121', '12121', '', '', ''],
['', '', '', '', 'P', '01', '21', '22', '23', 'True', ''],
['', '', '', '', 'P', '02', '21', '22', '23', 'True', ''],
['', '', '', '', 'P', '05', '21  00', '2223', '', 'True', ''],
['', '', '', '', 'T', 'TR', '123', '', '', 'True', '']
])
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[1],columns:[ ]')
        close()

        if frame('Record:  - wx1File.txt.xml:0'):
            click('Right')
            assert_content('JTable_24', [ ['Xml~Name', '0', '', 'wwHeader', 'wwHeader'],
['Xml~Prefix', '3', '', '', ''],
['Xml~Namespace', '4', '', '', ''],
['RecordType1', '5', '', 'H', 'H'],
['RecordType2', '6', '', 'HD', 'HD'],
['RunDate', '7', '', '20100101', '20100101'],
['RunNumber', '8', '', '1', '1'],
['Xml~End', '1', '', '', ''],
['Following~Text', '2', '', '', '']
])
            click('Right')
            assert_content('JTable_24', [ ['Xml~Name', '0', '', 'wwProdHead', 'wwProdHead'],
['Xml~Prefix', '3', '', '', ''],
['Xml~Namespace', '4', '', '', ''],
['RecordType1', '5', '', 'P', 'P'],
['RecordType2', '6', '', 'HD', 'HD'],
['Dept', '7', '', '121', '121'],
['Product', '8', '', '12121', '12121'],
['Xml~End', '1', '', '', ''],
['Following~Text', '2', '', '', '']
])
            click('Right')
            assert_content('JTable_24', [ ['Xml~Name', '0', '', 'wwProd01', 'wwProd01'],
['Xml~Prefix', '3', '', '', ''],
['Xml~Namespace', '4', '', '', ''],
['RecordType1', '5', '', 'P', 'P'],
['RecordType2', '6', '', '01', '01'],
['Field11', '7', '', '21', '21'],
['Field12', '8', '', '22', '22'],
['Field13', '9', '', '23', '23'],
['Xml~End', '1', '', 'True', 'True'],
['Following~Text', '2', '', '', '']
])
            click('Right')
            select('JTable_24', 'rows:[7],columns:[Data]')
            select('JTable_24', 'rows:[7],columns:[Data]')
            assert_content('JTable_24', [ ['Xml~Name', '0', '', 'wwProd02', 'wwProd02'],
['Xml~Prefix', '3', '', '', ''],
['Xml~Namespace', '4', '', '', ''],
['RecordType1', '5', '', 'P', 'P'],
['RecordType2', '6', '', '02', '02'],
['Field21', '7', '', '21', '21'],
['Field22', '8', '', '22', '22'],
['Field23', '9', '', '23', '23'],
['Xml~End', '1', '', 'True', 'True'],
['Following~Text', '2', '', '', '']
])
            select('JTable_24', 'rows:[7],columns:[Data]')
            click('Right')
            assert_p('JTable_24', 'Text', '2223', '{6, Data}')
            click('Right')
            assert_content('JTable_24', [ ['Xml~Name', '0', '', 'wwProd02', 'wwProd02'],
['Xml~Prefix', '3', '', '', ''],
['Xml~Namespace', '4', '', '', ''],
['RecordType1', '5', '', 'P', 'P'],
['RecordType2', '6', '', '02', '02'],
['Field21', '7', '', '21', '21'],
['Field22', '8', '', '22', '22'],
['Field23', '9', '', '23', '23'],
['Xml~End', '1', '', 'True', 'True'],
['Following~Text', '2', '', '', '']
])
            assert_p('Layouts', 'Text', 'wwProd02')
            click('Right')
            assert_content('JTable_24', [ ['Xml~Name', '0', '', 'wwProd01', 'wwProd01'],
['Xml~Prefix', '3', '', '', ''],
['Xml~Namespace', '4', '', '', ''],
['RecordType1', '5', '', 'P', 'P'],
['RecordType2', '6', '', '01', '01'],
['Field11', '7', '', '21', '21'],
['Field12', '8', '', '22', '22'],
['Field13', '9', '', '23', '23'],
['Xml~End', '1', '', 'True', 'True'],
['Following~Text', '2', '', '', '']
])
            assert_p('Layouts', 'Text', 'wwProd01')
            click('RightM')
            assert_content('JTable_24', [ ['Xml~Name', '0', '', '/wx1File', '/wx1File'],
['Xml~End', '1', '', '', ''],
['Following~Text', '2', '', '', '']
])
            assert_p('Layouts', 'Text', '/wx1File')
            click('Left')
            assert_content('JTable_24', [ ['Xml~Name', '0', '', '/wwHeader', '/wwHeader'],
['Xml~End', '1', '', '', ''],
['Following~Text', '2', '', '', '']
])
            click('Left')
            assert_content('JTable_24', [ ['Xml~Name', '0', '', 'wwTrailer', 'wwTrailer'],
['Xml~Prefix', '3', '', '', ''],
['Xml~Namespace', '4', '', '', ''],
['RecordType1', '5', '', 'T', 'T'],
['RecordType2', '6', '', 'TR', 'TR'],
['Count', '7', '', '123', '123'],
['Xml~End', '1', '', 'True', 'True'],
['Following~Text', '2', '', '', '']
])
            assert_p('Layouts', 'Text', 'wwTrailer')
        close()
    close()

    pass
