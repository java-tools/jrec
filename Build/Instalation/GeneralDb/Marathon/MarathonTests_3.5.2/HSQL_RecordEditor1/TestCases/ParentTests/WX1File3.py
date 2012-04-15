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

    if frame('Table:  - wx1File.txt:0'):
        assert_content('JTable_22', [ ['H', 'HD', '2010', '1010000', '000'],
['P', 'HD', '121', '12121', ''],
['P', '01', '21', '22', '23'],
['P', '02', '21', '22', '23'],
['P', '05', '21', '22', '23'],
['P', '02', '21', '22', '23'],
['P', '01', '21', '22', '23'],
['P', '01', '21', '22', '23'],
['P', 'HD', '121', '12121', ''],
['P', '01', '21', '22', '23'],
['P', '02', '21', '22', '23'],
['P', '05', '21', '22', '23'],
['T', 'TR', '11', '0', '000']
])
        select('JTable_22', 'rows:[0],columns:[3 - 2|RecordType2]')
        select('JTable_24', 'rows:[0],columns:[Sl]')
    close()

    if frame('Table:  - wx1File.txt:1'):
        select('JTable_22', 'rows:[0],columns:[3 - 2|RecordType2]')
        select('JTable_24', 'rows:[0],columns:[Sl]')
    close()

    if frame('Record:  - wx1File.txt:0'):
        assert_content('JTable_24', [ ['RecordType1', '1', '1', 'H', 'H'],
['RecordType2', '3', '2', 'HD', 'HD'],
['RunDate', '5', '8', '20100101', '20100101'],
['RunNumber', '13', '8', '1', '00000001']
])
        assert_p('Layouts', 'Text', 'wwHeader')
        click('Right')
        select('JTable_24', 'rows:[2],columns:[Data]')
        assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', 'HD', 'HD'],
['Dept', '5', '4', '121', '121'],
['Product', '9', '8', '12121', '00012121']
])
        assert_p('Layouts', 'Text', 'wwProdHead')
        select('JTable_24', 'rows:[2],columns:[Data]')
        click('Right')
        select('JTable_24', 'rows:[2],columns:[Data]')
        assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', '01', '01'],
['Field11', '5', '4', '21', '21'],
['Field12', '9', '8', '22', '00000022'],
['Field13', '17', '3', '23', '23']
])
        assert_p('Layouts', 'Text', 'wwProd01')
        click('Right')
        assert_p('JTable_24', 'Text', '22', '{3, Data}')
        assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', '02', '02'],
['Field21', '5', '4', '21', '21'],
['Field22', '9', '8', '22', '00000022'],
['Field23', '17', '3', '23', '23']
])
        assert_p('Layouts', 'Text', 'wwProd02')
        click('Right')
        assert_p('JTable_24', 'Text', '2223', '{3, Data}')
        assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', '05', '05'],
['Field51', '5', '6', '21  00', '21  00'],
['Field52', '11', '9', '2223', '00002223'],
['Field53', '26', '3', '', '']
])
        assert_p('Layouts', 'Text', 'wwProd05')
        click('Right')
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
    close()

    pass