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
        click('SaveAs')

        if frame('Save as - DTAR020_tst1.bin:0'):
            select('JTabbedPane_16', 'Fixed')
            select('Edit Output File', 'true')
            select('names on first line', 'true')
            select('space between fields', 'true')
            select('Keep screen open', 'true')
            assert_content('JTable_27', [ ['KEYCODE-NO', 'true', '10'],
['STORE-NO', 'true', '8'],
['DATE', 'true', '5'],
['DEPT-NO', 'true', '7'],
['QTY-SOLD', 'true', '8'],
['SALE-PRICE', 'true', '10']
])
            select('JTable_27', 'false', '{2, Include}')
            select('JTable_27', 'false', '{4, Include}')
            select('JTable_27', 'rows:[4],columns:[Include]')
            assert_content('JTable_27', [ ['KEYCODE-NO', 'true', '10'],
['STORE-NO', 'true', '8'],
['DATE', 'false', '5'],
['DEPT-NO', 'true', '7'],
['QTY-SOLD', 'false', '8'],
['SALE-PRICE', 'true', '10']
])
            select('JTable_27', 'rows:[4],columns:[Include]')
            click('save file')
##          select('JTable_27', 'rows:[4],columns:[Include]')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['KEYCODE-NO', 'STORE-NO', 'DEPT-NO', 'SALE-PRICE'],
['63604808', '      20', '    170', '      4.87'],
['69684558', '      20', '    280', '     19.00'],
['69684558', '      20', '    280', '    -19.00'],
['69694158', '      20', '    280', '      5.01'],
['62684671', '      20', '    685', '     69.99'],
['62684671', '      20', '    685', '    -69.99'],
['61664713', '      59', '    335', '     17.99'],
['61664713', '      59', '    335', '    -17.99'],
['61684613', '      59', '    335', '     12.99'],
['68634752', '      59', '    410', '      8.99'],
['60694698', '      59', '    620', '      3.99'],
['60664659', '      59', '    620', '      3.99'],
['60614487', '      59', '    878', '      5.95'],
['68654655', '     166', '     60', '      5.08'],
['69624033', '     166', '     80', '     18.19'],
['60604100', '     166', '     80', '     13.30'],
['68674560', '     166', '    170', '      5.99']
])
            select('JTable_22', 'rows:[0],columns:[12 - 8|STORE-NO]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '29 - 10|SALE-PRICE', '29 - 10|SALE-PRICE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '21 - 7|DEPT-NO', '21 - 7|DEPT-NO')
##            assert_content('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', [ ['1 - 10|KEYCODE-NO', '12 - 8|STORE-NO', '21 - 7|DATE', '29 - 10|QTY-SOLD']
##])
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 10|KEYCODE-NO', '1 - 10|KEYCODE-NO')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['KEYCODE-NO STORE-NO DEPT-NO SALE-PRICE'],
['63604808         20     170       4.87'],
['69684558         20     280      19.00'],
['69684558         20     280     -19.00'],
['69694158         20     280       5.01'],
['62684671         20     685      69.99'],
['62684671         20     685     -69.99'],
['61664713         59     335      17.99'],
['61664713         59     335     -17.99'],
['61684613         59     335      12.99'],
['68634752         59     410       8.99'],
['60694698         59     620       3.99'],
['60664659         59     620       3.99'],
['60614487         59     878       5.95'],
['68654655        166      60       5.08'],
['69624033        166      80      18.19'],
['60604100        166      80      13.30'],
['68674560        166     170       5.99']
])
            click('Close')
        close()

        if frame('Save as - DTAR020_tst1.bin:0'):
            select('space between fields', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['KEYCODE-NO', 'STORE-NO', 'DEPT-NO', 'SALE-PRICE'],
['63604808', '      20', '    170', '      4.87'],
['69684558', '      20', '    280', '     19.00'],
['69684558', '      20', '    280', '    -19.00'],
['69694158', '      20', '    280', '      5.01'],
['62684671', '      20', '    685', '     69.99'],
['62684671', '      20', '    685', '    -69.99'],
['61664713', '      59', '    335', '     17.99'],
['61664713', '      59', '    335', '    -17.99'],
['61684613', '      59', '    335', '     12.99'],
['68634752', '      59', '    410', '      8.99'],
['60694698', '      59', '    620', '      3.99'],
['60664659', '      59', '    620', '      3.99'],
['60614487', '      59', '    878', '      5.95'],
['68654655', '     166', '     60', '      5.08'],
['69624033', '     166', '     80', '     18.19'],
['60604100', '     166', '     80', '     13.30'],
['68674560', '     166', '    170', '      5.99']
])
            select('JTable_22', 'rows:[0],columns:[11 - 8|STORE-NO]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '26 - 10|SALE-PRICE', '26 - 10|SALE-PRICE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '19 - 7|DEPT-NO', '19 - 7|DEPT-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '11 - 8|STORE-NO', '11 - 8|STORE-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 10|KEYCODE-NO', '1 - 10|KEYCODE-NO')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['KEYCODE-NOSTORE-NODEPT-NOSALE-PRICE'],
['63604808        20    170      4.87'],
['69684558        20    280     19.00'],
['69684558        20    280    -19.00'],
['69694158        20    280      5.01'],
['62684671        20    685     69.99'],
['62684671        20    685    -69.99'],
['61664713        59    335     17.99'],
['61664713        59    335    -17.99'],
['61684613        59    335     12.99'],
['68634752        59    410      8.99'],
['60694698        59    620      3.99'],
['60664659        59    620      3.99'],
['60614487        59    878      5.95'],
['68654655       166     60      5.08'],
['69624033       166     80     18.19'],
['60604100       166     80     13.30'],
['68674560       166    170      5.99']
])
            click('Close')
        close()

        if frame('Save as - DTAR020_tst1.bin:0'):
            select('names on first line', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            select('JTable_22', 'rows:[7],columns:[11 - 8|STORE-NO]')
            assert_content('JTable_22', [ ['63604808', '      20', '    170', '      4.87'],
['69684558', '      20', '    280', '     19.00'],
['69684558', '      20', '    280', '    -19.00'],
['69694158', '      20', '    280', '      5.01'],
['62684671', '      20', '    685', '     69.99'],
['62684671', '      20', '    685', '    -69.99'],
['61664713', '      59', '    335', '     17.99'],
['61664713', '      59', '    335', '    -17.99'],
['61684613', '      59', '    335', '     12.99'],
['68634752', '      59', '    410', '      8.99'],
['60694698', '      59', '    620', '      3.99'],
['60664659', '      59', '    620', '      3.99'],
['60614487', '      59', '    878', '      5.95'],
['68654655', '     166', '     60', '      5.08'],
['69624033', '     166', '     80', '     18.19'],
['60604100', '     166', '     80', '     13.30'],
['68674560', '     166', '    170', '      5.99']
])
            select('JTable_22', 'rows:[7],columns:[11 - 8|STORE-NO]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '26 - 10|SALE-PRICE', '26 - 10|SALE-PRICE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '19 - 7|DEPT-NO', '19 - 7|DEPT-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '11 - 8|STORE-NO', '11 - 8|STORE-NO')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_content('JTable_22', [ ['63604808        20    170      4.87'],
['69684558        20    280     19.00'],
['69684558        20    280    -19.00'],
['69694158        20    280      5.01'],
['62684671        20    685     69.99'],
['62684671        20    685    -69.99'],
['61664713        59    335     17.99'],
['61664713        59    335    -17.99'],
['61684613        59    335     12.99'],
['68634752        59    410      8.99'],
['60694698        59    620      3.99'],
['60664659        59    620      3.99'],
['60614487        59    878      5.95'],
['68654655       166     60      5.08'],
['69624033       166     80     18.19'],
['60604100       166     80     13.30'],
['68674560       166    170      5.99']
])
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{6, Full Line}')
##            select('JTable_22', 'rows:[6],columns:[Full Line]')
        close()

        if frame('Save as - DTAR020_tst1.bin:0'):
            select('space between fields', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            select('JTable_22', 'rows:[6],columns:[12 - 8|STORE-NO]')
            select('JTable_22', 'rows:[6],columns:[12 - 8|STORE-NO]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '29 - 10|SALE-PRICE', '29 - 10|SALE-PRICE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '21 - 7|DEPT-NO', '21 - 7|DEPT-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '12 - 8|STORE-NO', '12 - 8|STORE-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 10|KEYCODE-NO', '1 - 10|KEYCODE-NO')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_content('JTable_22', [ ['63604808         20     170       4.87'],
['69684558         20     280      19.00'],
['69684558         20     280     -19.00'],
['69694158         20     280       5.01'],
['62684671         20     685      69.99'],
['62684671         20     685     -69.99'],
['61664713         59     335      17.99'],
['61664713         59     335     -17.99'],
['61684613         59     335      12.99'],
['68634752         59     410       8.99'],
['60694698         59     620       3.99'],
['60664659         59     620       3.99'],
['60614487         59     878       5.95'],
['68654655        166      60       5.08'],
['69624033        166      80      18.19'],
['60604100        166      80      13.30'],
['68674560        166     170       5.99']
])
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{6, Full Line}')
##            select('JTable_22', 'rows:[6],columns:[Full Line]')
        close()

##        window_closed('Record Editor')
    close()

    pass
