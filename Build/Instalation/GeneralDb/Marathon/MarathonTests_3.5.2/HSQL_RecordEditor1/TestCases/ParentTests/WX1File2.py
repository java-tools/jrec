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

        if frame('Tree View - wx1File.txt:0'):
            assert_content('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', [ ['', '', 'H', 'HD', '2010', '1010000', '000'],
['', '', 'P', 'HD', '121', '12121', ''],
['', '', 'P', '01', '21', '22', '23'],
['', '', 'P', '02', '21', '22', '23'],
['', '', 'P', '05', '21', '22', '23'],
['', '', 'P', '02', '21', '22', '23'],
['', '', 'P', '01', '21', '22', '23'],
['', '', 'P', '01', '21', '22', '23'],
['', '', 'P', 'HD', '121', '12121', ''],
['', '', 'P', '01', '21', '22', '23'],
['', '', 'P', '02', '21', '22', '23'],
['', '', 'P', '05', '21', '22', '23'],
['', '', 'T', 'TR', '11', '0', '000']
])
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[0],columns:[ ]')
        close()

        if frame('Record:  - wx1File.txt:0'):
            assert_content('JTable_24', [ ['RecordType1', '1', '1', 'H', 'H'],
['RecordType2', '3', '2', 'HD', 'HD'],
['RunDate', '5', '8', '20100101', '20100101'],
['RunNumber', '13', '8', '1', '00000001']
])
            assert_p('Layouts', 'Text', 'wwHeader')
            click('Right')
            assert_p('JTable_24', 'Text', '121', '{2, Data}')
            assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', 'HD', 'HD'],
['Dept', '5', '4', '121', '121'],
['Product', '9', '8', '12121', '00012121']
])
            assert_p('Layouts', 'Text', 'wwProdHead')
            click('Right')
            assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', '01', '01'],
['Field11', '5', '4', '21', '21'],
['Field12', '9', '8', '22', '00000022'],
['Field13', '17', '3', '23', '23']
])
            assert_p('Layouts', 'Text', 'wwProd01')
            click('Right')
            assert_p('JTable_24', 'Text', '22', '{3, Data}')
            assert_p('JTable_24', 'Text', '22', '{3, Data}')
            assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', '02', '02'],
['Field21', '5', '4', '21', '21'],
['Field22', '9', '8', '22', '00000022'],
['Field23', '17', '3', '23', '23']
])
            assert_p('Layouts', 'Text', 'wwProd02')
            click('Right')
            assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', '05', '05'],
['Field51', '5', '6', '21  00', '21  00'],
['Field52', '11', '9', '2223', '00002223'],
['Field53', '26', '3', '', '']
])
            click('Right')
            assert_p('JTable_24', 'Text', '22', '{3, Data}')
            assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', '02', '02'],
['Field21', '5', '4', '21', '21'],
['Field22', '9', '8', '22', '00000022'],
['Field23', '17', '3', '23', '23']
])
            click('RightM')
            assert_content('JTable_24', [ ['RecordType1', '1', '1', 'T', 'T'],
['RecordType2', '3', '2', 'TR', 'TR'],
['Count', '17', '8', '123', '00000123']
])
            assert_p('Layouts', 'Text', 'wwTrailer')
            click('Left')
            assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', '05', '05'],
['Field51', '5', '6', '21  00', '21  00'],
['Field52', '11', '9', '2223', '00002223'],
['Field53', '26', '3', '', '']
])
        close()

##        window_closed('Record Editor')
    close()

    pass