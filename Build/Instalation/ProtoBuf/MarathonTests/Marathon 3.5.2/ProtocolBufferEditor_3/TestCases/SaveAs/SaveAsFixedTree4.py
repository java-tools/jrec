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
        select_menu('View>>Sorted Field Tree')

        if frame('Create Sorted Tree - DTAR020_tst1.bin:0'):
            select('JTable_10', 'Store_No', '{0, Field}')
            select('JTable_10', 'Dept_No', '{1, Field}')
            select('JTable_10', 'rows:[1],columns:[Field]')
            click('Build Tree')
        close()

        if frame('Tree View - DTAR020_tst1.bin:0'):
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[8],columns:[DATE]')
            assert_content('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', [ ['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '63604808', '20', '40118', '170', '1', '4870'],
['', '', '', '', '', '', '', ''],
['', '', '69684558', '20', '40118', '280', '1', '19000'],
['', '', '69684558', '20', '40118', '280', '-1', '-19000'],
['', '', '69694158', '20', '40118', '280', '1', '5010'],
['', '', '', '', '', '', '', ''],
['', '', '62684671', '20', '40118', '685', '1', '69990'],
['', '', '62684671', '20', '40118', '685', '-1', '-69990'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '61664713', '59', '40118', '335', '1', '17990'],
['', '', '61664713', '59', '40118', '335', '-1', '-17990'],
['', '', '61684613', '59', '40118', '335', '1', '12990'],
['', '', '', '', '', '', '', ''],
['', '', '68634752', '59', '40118', '410', '1', '8990'],
['', '', '', '', '', '', '', ''],
['', '', '60694698', '59', '40118', '620', '1', '3990'],
['', '', '60664659', '59', '40118', '620', '1', '3990'],
['', '', '', '', '', '', '', ''],
['', '', '60614487', '59', '40118', '878', '1', '5950'],
['', '', '', '', '', '', '', ''],
['', '', '', '', '', '', '', ''],
['', '', '68654655', '166', '40118', '60', '1', '5080'
],
['', '', '', '', '', '', '', ''],
['', '', '69624033', '166', '40118', '80', '1', '18190'],
['', '', '60604100', '166', '40118', '80', '1', '13300'],
['', '', '', '', '', '', '', ''],
['', '', '68674560', '166', '40118', '170', '1', '5990']
])
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[8],columns:[DATE]')
        close()

##        click('SaveAs')

        select_menu('File>>Export')


        if frame('Export - DTAR020_tst1.bin:0'):
##            select('JTabbedPane_16', 'Fixed')
            select('File Name_2', 'Fixed')
            select('Only export Nodes with Data', 'true')
            select('names on first line', 'true')
            select('space between fields', 'true')
            select('JTable_28', 'rows:[2],columns:[Field Name]')
            assert_content('JTable_28', [ ['Keycode_no', 'true', '10'],
['Store_No', 'true', '8'],
['DATE', 'true', '5'],
['Dept_No', 'true', '7'],
['Qty_Sold', 'true', '8'],
['Sale_Price', 'true', '10']
])
            select('JTable_28', 'rows:[2],columns:[Field Name]')
            select('Keep screen open', 'false')
            select('JTable_28', 'rows:[2],columns:[Field Name]')
            select('Keep screen open', 'true')
            select('Edit Output File', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            select('JTable_22', 'rows:[7],columns:[33 - 10|Keycode_no]')
            assert_content('JTable_22', [ ['Level_1', 'Level_2', 'Level_3', 'Level_4', 'Keycode_no', 'Store_No', 'DATE', 'Dept_No', 'Qty_Sold', 'Sale_Price'],
['File', '20', '170', '', '63604808', '20', '40118', '170', '1', '4870'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '1', '19000'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '-1', '-19000'],
['File', '20', '280', '', '69694158', '20', '40118', '280', '1', '5010'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '1', '69990'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '-1', '-69990'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '1', '17990'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '-1', '-17990'],
['File', '59', '335', '', '61684613', '59', '40118', '335', '1', '12990'],
['File', '59', '410', '', '68634752', '59', '40118', '410', '1', '8990'],
['File', '59', '620', '', '60694698', '59', '40118', '620', '1', '3990'],
['File', '59', '620', '', '60664659', '59', '40118', '620', '1', '3990'],
['File', '59', '878', '', '60614487', '59', '40118', '878', '1', '5950'],
['File', '166', '60', '', '68654655', '166', '40118', '60', '1', '5080'
],
['File', '166', '80', '', '69624033', '166', '40118', '80', '1', '18190'],
['File', '166', '80', '', '60604100', '166', '40118', '80', '1', '13300'],
['File', '166', '170', '', '68674560', '166', '40118', '170', '1', '5990']
])
            select('JTable_22', 'rows:[7],columns:[33 - 10|Keycode_no]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '76 - 10|Sale_Price', '76 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '67 - 8|Qty_Sold', '67 - 8|Qty_Sold')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '59 - 7|Dept_No', '59 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '53 - 5|DATE', '53 - 5|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '44 - 8|Store_No', '44 - 8|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '33 - 10|Keycode_no', '33 - 10|Keycode_no')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '25 - 7|Level_4', '25 - 7|Level_4')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '17 - 7|Level_3', '17 - 7|Level_3')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '9 - 7|Level_2', '9 - 7|Level_2')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 7|Level_1', '1 - 7|Level_1')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 7|Level_1', '1 - 7|Level_1')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['Level_1 Level_2 Level_3 Level_4 Keycode_no Store_No DATE  Dept_No Qty_Sold Sale_Price'],
['File    20      170             63604808   20       40118 170     1        4870      '],
['File    20      280             69684558   20       40118 280     1        19000     '],
['File    20      280             69684558   20       40118 280     -1       -19000    '],
['File    20      280             69694158   20       40118 280     1        5010      '],
['File    20      685             62684671   20       40118 685     1        69990     '],
['File    20      685             62684671   20       40118 685     -1       -69990    '],
['File    59      335             61664713   59       40118 335     1        17990     '],
['File    59      335             61664713   59       40118 335     -1       -17990    '],
['File    59      335             61684613   59       40118 335     1        12990     '],
['File    59      410             68634752   59       40118 410     1        8990      '],
['File    59      620             60694698   59       40118 620     1        3990      '],
['File    59      620             60664659   59       40118 620     1        3990      '],
['File    59      878             60614487   59       40118 878     1        5950      '],
['File    166     60              68654655   166      40118 60      1        5080      '],
['File    166     80              69624033   166      40118 80      1        18190     '],
['File    166     80              60604100   166      40118 80      1        13300     '],
['File    166     170             68674560   166      40118 170     1        5990      ']
])
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('space between fields', 'false')
            select('Only export Nodes with Data', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['Level_1', 'Level_2', 'Level_3', 'Level_4', 'Keycode_no', 'Store_No', 'DATE', 'Dept_No', 'Qty_Sold', 'Sale_Price'],
['File', '20', '170', '', '63604808', '20', '40118', '170', '1', '4870'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '1', '19000'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '-1', '-19000'],
['File', '20', '280', '', '69694158', '20', '40118', '280', '1', '5010'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '1', '69990'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '-1', '-69990'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '1', '17990'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '-1', '-17990'],
['File', '59', '335', '', '61684613', '59', '40118', '335', '1', '12990'],
['File', '59', '410', '', '68634752', '59', '40118', '410', '1', '8990'],
['File', '59', '620', '', '60694698', '59', '40118', '620', '1', '3990'],
['File', '59', '620', '', '60664659', '59', '40118', '620', '1', '3990'],
['File', '59', '878', '', '60614487', '59', '40118', '878', '1', '5950'],
['File', '166', '60', '', '68654655', '166', '40118', '60', '1', '5080'
],
['File', '166', '80', '', '69624033', '166', '40118', '80', '1', '18190'],
['File', '166', '80', '', '60604100', '166', '40118', '80', '1', '13300'],
['File', '166', '170', '', '68674560', '166', '40118', '170', '1', '5990']
])
            select('JTable_22', 'rows:[0],columns:[8 - 7|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '67 - 10|Sale_Price', '67 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '59 - 8|Qty_Sold', '59 - 8|Qty_Sold')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '52 - 7|Dept_No', '52 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '47 - 5|DATE', '47 - 5|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '39 - 8|Store_No', '39 - 8|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '29 - 10|Keycode_no', '29 - 10|Keycode_no')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '22 - 7|Level_4', '22 - 7|Level_4')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '8 - 7|Level_2', '8 - 7|Level_2')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['Level_1Level_2Level_3Level_4Keycode_noStore_NoDATE Dept_NoQty_SoldSale_Price'],
['File   20     170           63604808  20      40118170    1       4870      '],
['File   20     280           69684558  20      40118280    1       19000     '],
['File   20     280           69684558  20      40118280    -1      -19000    '],
['File   20     280           69694158  20      40118280    1       5010      '],
['File   20     685           62684671  20      40118685    1       69990     '],
['File   20     685           62684671  20      40118685    -1      -69990    '],
['File   59     335           61664713  59      40118335    1       17990     '],
['File   59     335           61664713  59      40118335    -1      -17990    '],
['File   59     335           61684613  59      40118335    1       12990     '],
['File   59     410           68634752  59      40118410    1       8990      '],
['File   59     620           60694698  59      40118620    1       3990      '],
['File   59     620           60664659  59      40118620    1       3990      '],
['File   59     878           60614487  59      40118878    1       5950      '],
['File   166    60            68654655  166     4011860     1       5080      '],
['File   166    80            69624033  166     4011880     1       18190     '],
['File   166    80            60604100  166     4011880     1       13300     '],
['File   166    170           68674560  166     40118170    1       5990      ']
])
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('Only export Nodes with Data', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['Level_1', 'Level_2', 'Level_3', 'Level_4', 'Keycode_no', 'Store_No', 'DATE', 'Dept_No', 'Qty_Sold', 'Sale_Price'],
['File', '', '', '', '', '', '', '', '', ''],
['File', '20', '', '', '', '', '', '', '', ''],
['File', '20', '170', '', '', '', '', '', '', ''],
['File', '20', '170', '', '63604808', '20', '40118', '170', '1', '4870'],
['File', '20', '280', '', '', '', '', '', '', ''],
['File', '20', '280', '', '69684558', '20', '40118', '280', '1', '19000'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '-1', '-19000'],
['File', '20', '280', '', '69694158', '20', '40118', '280', '1', '5010'],
['File', '20', '685', '', '', '', '', '', '', ''],
['File', '20', '685', '', '62684671', '20', '40118', '685', '1', '69990'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '-1', '-69990'],
['File', '59', '', '', '', '', '', '', '', ''],
['File', '59', '335', '', '', '', '', '', '', ''],
['File', '59', '335', '', '61664713', '59', '40118', '335', '1', '17990'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '-1', '-17990'],
['File', '59', '335', '', '61684613', '59', '40118', '335', '1', '12990'],
['File', '59', '410', '', '', '', '', '', '', ''],
['File', '59', '410', '', '68634752', '59', '40118', '410', '1', '8990'],
['File', '59', '620', '', '', '', '', '', '', ''],
['File', '59', '620', '', '60694698', '59', '40118', '620', '1', '3990'],
['File', '59', '620', '', '60664659', '59', '40118', '620', '1', '3990'],
['File', '59', '878', '', '', '', '', '', '', ''],
['File', '59', '878', '', '60614487', '59', '40118', '878', '1', '5950'],
['File', '166', '', '', '', '', '', '', '', ''],
['File', '166', '60', '', '', '', '', '', '', ''],
['File', '166', '60', '', '68654655', '166', '40118', '60', '1', '5080'
],
['File', '166', '80', '', '', '', '', '', '', ''],
['File', '166', '80', '', '69624033', '166', '40118', '80', '1', '18190'],
['File', '166', '80', '', '60604100', '166', '40118', '80', '1', '13300'],
['File', '166', '170', '', '', '', '', '', '', ''],
['File', '166', '170', '', '68674560', '166', '40118', '170', '1', '5990']
])
            select('JTable_22', 'rows:[0],columns:[8 - 7|Level_2]')
            click('Close')
##            select('JTable_22', '', '{0, 8 - 7|Level_2}')
##            select('JTable_22', 'rows:[0],columns:[8 - 7|Level_2]')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('Export Tree', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
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
            select('JTable_22', 'rows:[0],columns:[11 - 8|Store_No]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '39 - 10|Sale_Price', '39 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '31 - 8|Qty_Sold', '31 - 8|Qty_Sold')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '24 - 7|Dept_No', '24 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '19 - 5|DATE', '19 - 5|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '11 - 8|Store_No', '11 - 8|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1 - 10|Keycode_no', '1 - 10|Keycode_no')
            select('Layouts', 'Full Line')
            assert_p('JTable_22', 'Text', '62684671      2040118    685      -1    -69990', '{6, Full Line}')
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
            click('Close')
        close()

##        window_closed('Record Editor')
    close()

    pass
