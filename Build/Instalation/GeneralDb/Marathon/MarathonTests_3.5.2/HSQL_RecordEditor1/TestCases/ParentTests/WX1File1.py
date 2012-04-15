#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleDir() + 'wx1File.txt')
        select('Record Layout', 'wx1File')
        click('Edit')
    close()

    if frame('Table:  - wx1File.txt:0'):
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
        assert_content('JTable_24', [ ['RecordType1', '1', '1', 'P', 'P'],
['RecordType2', '3', '2', '02', '02'],
['Field21', '5', '4', '21', '21'],
['Field22', '9', '8', '22', '00000022'],
['Field23', '17', '3', '23', '23']
])
        click('Close')
    close()


    pass
