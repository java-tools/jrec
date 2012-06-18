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
#            select('JTabbedPane_16', 'Fixed')
            select('File Name_2', 'Fixed')
            select('Edit Output File', 'true')
            select('names on first line', 'true')
            select('space between fields', 'true')
            select('Keep screen open', 'true')
#            assert_content('JTable_27', [ ['Keycode_no', 'true', '10'],
#['Store_No', 'true', '8'],
            assert_content('JTable_24', [ ['Keycode_no', 'true', '10'],
['Store_No', 'true', '8'],
['DATE', 'true', '5'],
['Dept_No', 'true', '7'],
['Qty_Sold', 'true', '8'],
['Sale_Price', 'true', '10']
])
##            select('JTable_27', 'false', '{2, Include}')
##            select('JTable_27', 'false', '{4, Include}')
##            select('JTable_27', 'rows:[4],columns:[Include]')
##            assert_content('JTable_27', [ ['Keycode_no', 'true', '10'],
##['Store_No', 'true', '8'],
            select('JTable_24', 'false', '{2, Include}')
            select('JTable_24', 'false', '{4, Include}')
            select('JTable_24', 'rows:[4],columns:[Include]')
            assert_content('JTable_24', [ ['Keycode_no', 'true', '10'],
['Store_No', 'true', '8'],
['DATE', 'false', '5'],
['Dept_No', 'true', '7'],
['Qty_Sold', 'false', '8'],
['Sale_Price', 'true', '10']
])
##            select('JTable_27', 'rows:[4],columns:[Include]')
            select('JTable_24', 'rows:[4],columns:[Include]')
            click('save file')
##          select('JTable_27', 'rows:[4],columns:[Include]')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['Keycode_no', 'Store_No', 'Dept_No', 'Sale_Price'],
['  63604808', '      20', '    170', '      4870'],
['  69684558', '      20', '    280', '     19000'],
['  69684558', '      20', '    280', '    -19000'],
['  69694158', '      20', '    280', '      5010'],
['  62684671', '      20', '    685', '     69990'],
['  62684671', '      20', '    685', '    -69990'],
['  61664713', '      59', '    335', '     17990'],
['  61664713', '      59', '    335', '    -17990'],
['  61684613', '      59', '    335', '     12990'],
['  68634752', '      59', '    410', '      8990'],
['  60694698', '      59', '    620', '      3990'],
['  60664659', '      59', '    620', '      3990'],
['  60614487', '      59', '    878', '      5950'],
['  68654655', '     166', '     60', '      5080'
],
['  69624033', '     166', '     80', '     18190'],
['  60604100', '     166', '     80', '     13300'],
['  68674560', '     166', '    170', '      5990']
])
            select('JTable_22', 'rows:[0],columns:[12 - 8|Store_No]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '29 - 10|Sale_Price', '29 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '21 - 7|Dept_No', '21 - 7|Dept_No')
##            assert_content('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', [ ['1 - 10|Keycode_no', '12 - 8|Store_No', '21 - 7|DATE', '29 - 10|Qty_Sold']
##])
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 10|Keycode_no', '1 - 10|Keycode_no')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['Keycode_no Store_No Dept_No Sale_Price'],
['  63604808       20     170       4870'],
['  69684558       20     280      19000'],
['  69684558       20     280     -19000'],
['  69694158       20     280       5010'],
['  62684671       20     685      69990'],
['  62684671       20     685     -69990'],
['  61664713       59     335      17990'],
['  61664713       59     335     -17990'],
['  61684613       59     335      12990'],
['  68634752       59     410       8990'],
['  60694698       59     620       3990'],
['  60664659       59     620       3990'],
['  60614487       59     878       5950'],
['  68654655      166      60       5080'
],
['  69624033      166      80      18190'],
['  60604100      166      80      13300'],
['  68674560      166     170       5990']
])
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('space between fields', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['Keycode_no', 'Store_No', 'Dept_No', 'Sale_Price'],
['  63604808', '      20', '    170', '      4870'],
['  69684558', '      20', '    280', '     19000'],
['  69684558', '      20', '    280', '    -19000'],
['  69694158', '      20', '    280', '      5010'],
['  62684671', '      20', '    685', '     69990'],
['  62684671', '      20', '    685', '    -69990'],
['  61664713', '      59', '    335', '     17990'],
['  61664713', '      59', '    335', '    -17990'],
['  61684613', '      59', '    335', '     12990'],
['  68634752', '      59', '    410', '      8990'],
['  60694698', '      59', '    620', '      3990'],
['  60664659', '      59', '    620', '      3990'],
['  60614487', '      59', '    878', '      5950'],
['  68654655', '     166', '     60', '      5080'
],
['  69624033', '     166', '     80', '     18190'],
['  60604100', '     166', '     80', '     13300'],
['  68674560', '     166', '    170', '      5990']
])
            select('JTable_22', 'rows:[0],columns:[11 - 8|Store_No]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '26 - 10|Sale_Price', '26 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '19 - 7|Dept_No', '19 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '11 - 8|Store_No', '11 - 8|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 10|Keycode_no', '1 - 10|Keycode_no')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['Keycode_noStore_NoDept_NoSale_Price'],
['  63604808      20    170      4870'],
['  69684558      20    280     19000'],
['  69684558      20    280    -19000'],
['  69694158      20    280      5010'],
['  62684671      20    685     69990'],
['  62684671      20    685    -69990'],
['  61664713      59    335     17990'],
['  61664713      59    335    -17990'],
['  61684613      59    335     12990'],
['  68634752      59    410      8990'],
['  60694698      59    620      3990'],
['  60664659      59    620      3990'],
['  60614487      59    878      5950'],
['  68654655     166     60      5080'
],
['  69624033     166     80     18190'],
['  60604100     166     80     13300'],
['  68674560     166    170      5990']
])
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('names on first line', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            select('JTable_22', 'rows:[7],columns:[11 - 8|Store_No]')
            assert_content('JTable_22', [ ['  63604808', '      20', '    170', '      4870'],
['  69684558', '      20', '    280', '     19000'],
['  69684558', '      20', '    280', '    -19000'],
['  69694158', '      20', '    280', '      5010'],
['  62684671', '      20', '    685', '     69990'],
['  62684671', '      20', '    685', '    -69990'],
['  61664713', '      59', '    335', '     17990'],
['  61664713', '      59', '    335', '    -17990'],
['  61684613', '      59', '    335', '     12990'],
['  68634752', '      59', '    410', '      8990'],
['  60694698', '      59', '    620', '      3990'],
['  60664659', '      59', '    620', '      3990'],
['  60614487', '      59', '    878', '      5950'],
['  68654655', '     166', '     60', '      5080'
],
['  69624033', '     166', '     80', '     18190'],
['  60604100', '     166', '     80', '     13300'],
['  68674560', '     166', '    170', '      5990']
])
            select('JTable_22', 'rows:[7],columns:[11 - 8|Store_No]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '26 - 10|Sale_Price', '26 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '19 - 7|Dept_No', '19 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '11 - 8|Store_No', '11 - 8|Store_No')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_content('JTable_22', [ ['  63604808      20    170      4870'],
['  69684558      20    280     19000'],
['  69684558      20    280    -19000'],
['  69694158      20    280      5010'],
['  62684671      20    685     69990'],
['  62684671      20    685    -69990'],
['  61664713      59    335     17990'],
['  61664713      59    335    -17990'],
['  61684613      59    335     12990'],
['  68634752      59    410      8990'],
['  60694698      59    620      3990'],
['  60664659      59    620      3990'],
['  60614487      59    878      5950'],
['  68654655     166     60      5080'
],
['  69624033     166     80     18190'],
['  60604100     166     80     13300'],
['  68674560     166    170      5990']
])
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{6, Full Line}')
##            select('JTable_22', 'rows:[6],columns:[Full Line]')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('space between fields', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            select('JTable_22', 'rows:[6],columns:[12 - 8|Store_No]')
            select('JTable_22', 'rows:[6],columns:[12 - 8|Store_No]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '29 - 10|Sale_Price', '29 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '21 - 7|Dept_No', '21 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '12 - 8|Store_No', '12 - 8|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 10|Keycode_no', '1 - 10|Keycode_no')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_content('JTable_22', [ ['  63604808       20     170       4870'],
['  69684558       20     280      19000'],
['  69684558       20     280     -19000'],
['  69694158       20     280       5010'],
['  62684671       20     685      69990'],
['  62684671       20     685     -69990'],
['  61664713       59     335      17990'],
['  61664713       59     335     -17990'],
['  61684613       59     335      12990'],
['  68634752       59     410       8990'],
['  60694698       59     620       3990'],
['  60664659       59     620       3990'],
['  60614487       59     878       5950'],
['  68654655      166      60       5080'
],
['  69624033      166      80      18190'],
['  60604100      166      80      13300'],
['  68674560      166     170       5990']
])
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{6, Full Line}')
##            select('JTable_22', 'rows:[6],columns:[Full Line]')
        close()

##        window_closed('Record Editor')
    close()

    pass
