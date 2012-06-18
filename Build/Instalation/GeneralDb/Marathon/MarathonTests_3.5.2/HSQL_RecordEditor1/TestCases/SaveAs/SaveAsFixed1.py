#{{{ Marathon
from default import *
#}}} Marathon

from Modules import commonBits

def test():

    set_java_recorded_version("1.6.0_22")
    if frame(' - Open File:0'):
        select('File', commonBits.sampleDir() + 'DTAR020_tst1.bin')
        click('Edit')
    close()

    if window('Record Editor'):
        click('Export')

        if frame('Export - DTAR020_tst1.bin:0'):
##            select('JTabbedPane_16', 'Fixed')
            select('File Name_2', 'Fixed')
            select('names on first line', 'true')
            select('space between fields', 'true')
            select('Edit Output File', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            select('JTable_22', 'rows:[6],columns:[21 - 5|DATE]')
            assert_content('JTable_22', [ ['KEYCODE-NO', 'STORE-NO', ' DATE', 'DEPT-NO', 'QTY-SOLD', 'SALE-PRICE'],
['63604808', '      20', '40118', '    170', '       1', '      4.87'],
['69684558', '      20', '40118', '    280', '       1', '     19.00'],
['69684558', '      20', '40118', '    280', '      -1', '    -19.00'],
['69694158', '      20', '40118', '    280', '       1', '      5.01'],
['62684671', '      20', '40118', '    685', '       1', '     69.99'],
['62684671', '      20', '40118', '    685', '      -1', '    -69.99'],
['61664713', '      59', '40118', '    335', '       1', '     17.99'],
['61664713', '      59', '40118', '    335', '      -1', '    -17.99'],
['61684613', '      59', '40118', '    335', '       1', '     12.99'],
['68634752', '      59', '40118', '    410', '       1', '      8.99'],
['60694698', '      59', '40118', '    620', '       1', '      3.99'],
['60664659', '      59', '40118', '    620', '       1', '      3.99'],
['60614487', '      59', '40118', '    878', '       1', '      5.95'],
['68654655', '     166', '40118', '     60', '       1', '      5.08'],
['69624033', '     166', '40118', '     80', '       1', '     18.19'],
['60604100', '     166', '40118', '     80', '       1', '     13.30'],
['68674560', '     166', '40118', '    170', '       1', '      5.99']
])
            select('JTable_22', 'rows:[6],columns:[21 - 5|DATE]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '12 - 8|STORE-NO', '12 - 8|STORE-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '21 - 5|DATE', '21 - 5|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '27 - 7|DEPT-NO', '27 - 7|DEPT-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '35 - 8|QTY-SOLD', '35 - 8|QTY-SOLD')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '44 - 10|SALE-PRICE', '44 - 10|SALE-PRICE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 10|KEYCODE-NO', '1 - 10|KEYCODE-NO')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_p('JTable_22', 'Text', '62684671         20 40118     685       -1     -69.99', '{6, Full Line}')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('space between fields', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            select('JTable_22', 'rows:[5],columns:[19 - 5|DATE]')
            assert_content('JTable_22', [ ['KEYCODE-NO', 'STORE-NO', ' DATE', 'DEPT-NO', 'QTY-SOLD', 'SALE-PRICE'],
['63604808', '      20', '40118', '    170', '       1', '      4.87'],
['69684558', '      20', '40118', '    280', '       1', '     19.00'],
['69684558', '      20', '40118', '    280', '      -1', '    -19.00'],
['69694158', '      20', '40118', '    280', '       1', '      5.01'],
['62684671', '      20', '40118', '    685', '       1', '     69.99'],
['62684671', '      20', '40118', '    685', '      -1', '    -69.99'],
['61664713', '      59', '40118', '    335', '       1', '     17.99'],
['61664713', '      59', '40118', '    335', '      -1', '    -17.99'],
['61684613', '      59', '40118', '    335', '       1', '     12.99'],
['68634752', '      59', '40118', '    410', '       1', '      8.99'],
['60694698', '      59', '40118', '    620', '       1', '      3.99'],
['60664659', '      59', '40118', '    620', '       1', '      3.99'],
['60614487', '      59', '40118', '    878', '       1', '      5.95'],
['68654655', '     166', '40118', '     60', '       1', '      5.08'],
['69624033', '     166', '40118', '     80', '       1', '     18.19'],
['60604100', '     166', '40118', '     80', '       1', '     13.30'],
['68674560', '     166', '40118', '    170', '       1', '      5.99']
])
            select('JTable_22', 'rows:[5],columns:[19 - 5|DATE]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '11 - 8|STORE-NO', '11 - 8|STORE-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '39 - 10|SALE-PRICE', '39 - 10|SALE-PRICE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '31 - 8|QTY-SOLD', '31 - 8|QTY-SOLD')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '24 - 7|DEPT-NO', '24 - 7|DEPT-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '24 - 7|DEPT-NO', '24 - 7|DEPT-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '19 - 5|DATE', '19 - 5|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '11 - 8|STORE-NO', '11 - 8|STORE-NO')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[4],columns:[Full Line]')
            assert_content('JTable_22', [ ['KEYCODE-NOSTORE-NO DATEDEPT-NOQTY-SOLDSALE-PRICE'],
['63604808        2040118    170       1      4.87'],
['69684558        2040118    280       1     19.00'],
['69684558        2040118    280      -1    -19.00'],
['69694158        2040118    280       1      5.01'],
['62684671        2040118    685       1     69.99'],
['62684671        2040118    685      -1    -69.99'],
['61664713        5940118    335       1     17.99'],
['61664713        5940118    335      -1    -17.99'],
['61684613        5940118    335       1     12.99'],
['68634752        5940118    410       1      8.99'],
['60694698        5940118    620       1      3.99'],
['60664659        5940118    620       1      3.99'],
['60614487        5940118    878       1      5.95'],
['68654655       16640118     60       1      5.08'],
['69624033       16640118     80       1     18.19'],
['60604100       16640118     80       1     13.30'],
['68674560       16640118    170       1      5.99']
])
            select('JTable_22', 'rows:[4],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{4, Full Line}')
##            select('JTable_22', 'rows:[4],columns:[Full Line]')
        close()

##        window_closed('Record Editor')
    close()

    pass
