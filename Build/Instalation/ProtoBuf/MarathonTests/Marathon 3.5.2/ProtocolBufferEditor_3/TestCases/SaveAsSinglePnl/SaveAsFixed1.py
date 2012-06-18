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

    if window('Protocol Buffer Editor'):
##        click('SaveAs')

        select_menu('File>>Export')

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
            assert_content('JTable_22', [ ['Keycode_no', 'Store_No', ' DATE', 'Dept_No', 'Qty_Sold', 'Sale_Price'],
['  63604808', '      20', '40118', '    170', '       1', '      4870'],
['  69684558', '      20', '40118', '    280', '       1', '     19000'],
['  69684558', '      20', '40118', '    280', '      -1', '    -19000'],
['  69694158', '      20', '40118', '    280', '       1', '      5010'],
['  62684671', '      20', '40118', '    685', '       1', '     69990'],
['  62684671', '      20', '40118', '    685', '      -1', '    -69990'],
['  61664713', '      59', '40118', '    335', '       1', '     17990'],
['  61664713', '      59', '40118', '    335', '      -1', '    -17990'],
['  61684613', '      59', '40118', '    335', '       1', '     12990'],
['  68634752', '      59', '40118', '    410', '       1', '      8990'],
['  60694698', '      59', '40118', '    620', '       1', '      3990'],
['  60664659', '      59', '40118', '    620', '       1', '      3990'],
['  60614487', '      59', '40118', '    878', '       1', '      5950'],
['  68654655', '     166', '40118', '     60', '       1', '      5080'
],
['  69624033', '     166', '40118', '     80', '       1', '     18190'],
['  60604100', '     166', '40118', '     80', '       1', '     13300'],
['  68674560', '     166', '40118', '    170', '       1', '      5990']
])
            select('JTable_22', 'rows:[6],columns:[21 - 5|DATE]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '12 - 8|Store_No', '12 - 8|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '21 - 5|DATE', '21 - 5|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '27 - 7|Dept_No', '27 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '35 - 8|Qty_Sold', '35 - 8|Qty_Sold')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '44 - 10|Sale_Price', '44 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 10|Keycode_no', '1 - 10|Keycode_no')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_p('JTable_22', 'Text', '62684671       20 40118     685       -1     -69990', '{6, Full Line}')
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
            assert_content('JTable_22', [ ['Keycode_no', 'Store_No', ' DATE', 'Dept_No', 'Qty_Sold', 'Sale_Price'],
['  63604808', '      20', '40118', '    170', '       1', '      4870'],
['  69684558', '      20', '40118', '    280', '       1', '     19000'],
['  69684558', '      20', '40118', '    280', '      -1', '    -19000'],
['  69694158', '      20', '40118', '    280', '       1', '      5010'],
['  62684671', '      20', '40118', '    685', '       1', '     69990'],
['  62684671', '      20', '40118', '    685', '      -1', '    -69990'],
['  61664713', '      59', '40118', '    335', '       1', '     17990'],
['  61664713', '      59', '40118', '    335', '      -1', '    -17990'],
['  61684613', '      59', '40118', '    335', '       1', '     12990'],
['  68634752', '      59', '40118', '    410', '       1', '      8990'],
['  60694698', '      59', '40118', '    620', '       1', '      3990'],
['  60664659', '      59', '40118', '    620', '       1', '      3990'],
['  60614487', '      59', '40118', '    878', '       1', '      5950'],
['  68654655', '     166', '40118', '     60', '       1', '      5080'
],
['  69624033', '     166', '40118', '     80', '       1', '     18190'],
['  60604100', '     166', '40118', '     80', '       1', '     13300'],
['  68674560', '     166', '40118', '    170', '       1', '      5990']
])
            select('JTable_22', 'rows:[5],columns:[19 - 5|DATE]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '11 - 8|Store_No', '11 - 8|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '39 - 10|Sale_Price', '39 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '31 - 8|Qty_Sold', '31 - 8|Qty_Sold')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '24 - 7|Dept_No', '24 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '24 - 7|Dept_No', '24 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '19 - 5|DATE', '19 - 5|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '11 - 8|Store_No', '11 - 8|Store_No')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[4],columns:[Full Line]')
            assert_content('JTable_22', [ ['Keycode_noStore_No DATEDept_NoQty_SoldSale_Price'],
['  63604808      2040118    170       1      4870'],
['  69684558      2040118    280       1     19000'],
['  69684558      2040118    280      -1    -19000'],
['  69694158      2040118    280       1      5010'],
['  62684671      2040118    685       1     69990'],
['  62684671      2040118    685      -1    -69990'],
['  61664713      5940118    335       1     17990'],
['  61664713      5940118    335      -1    -17990'],
['  61684613      5940118    335       1     12990'],
['  68634752      5940118    410       1      8990'],
['  60694698      5940118    620       1      3990'],
['  60664659      5940118    620       1      3990'],
['  60614487      5940118    878       1      5950'],
['  68654655     16640118     60       1      5080'
],
['  69624033     16640118     80       1     18190'],
['  60604100     16640118     80       1     13300'],
['  68674560     16640118    170       1      5990']
])
            select('JTable_22', 'rows:[4],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{4, Full Line}')
##            select('JTable_22', 'rows:[4],columns:[Full Line]')
        close()

##        window_closed('Record Editor')
    close()

    pass
