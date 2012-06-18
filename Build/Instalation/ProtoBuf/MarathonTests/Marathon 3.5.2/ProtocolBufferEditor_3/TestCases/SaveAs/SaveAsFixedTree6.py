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
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[5],columns:[DATE]')
##            assert_p('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'Component', '[,0,0,471x540,alignmentX=0.0,alignmentY=0.0,border=,flags=251658600,maximumSize=,minimumSize=,preferredSize=,autoCreateColumnsFromModel=true,autoResizeMode=AUTO_RESIZE_OFF,cellSelectionEnabled=false,editingColumn=-1,editingRow=-1,gridColor=javax.swing.plaf.ColorUIResource[r=122,g=138,b=153],preferredViewportSize=java.awt.Dimension[width=450,height=400],rowHeight=18,rowMargin=1,rowSelectionAllowed=true,selectionBackground=javax.swing.plaf.ColorUIResource[r=184,g=207,b=229],selectionForeground=sun.swing.PrintColorUIResource[r=51,g=51,b=51],showHorizontalLines=false,showVerticalLines=false]')
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[5],columns:[DATE]')
        close()

##        click('SaveAs')

        select_menu('File>>Export')

        if frame('Export - DTAR020_tst1.bin:0'):
##            select('JTabbedPane_16', 'Fixed')
            select('File Name_2', 'Fixed')
            select('Edit Output File', 'true')
            select('Only export Nodes with Data', 'false')
            select('names on first line', 'true')
            select('names on first line', 'false')
            select('Keep screen open', 'true')
            select('space between fields', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['File', '', '', '', '', '', '', '', '', ''],
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
            select('JTable_22', 'rows:[0],columns:[6 - 3|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '45 - 6|Sale_Price', '45 - 6|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '38 - 3|Dept_No', '38 - 3|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '19 - 8|Keycode_no', '19 - 8|Keycode_no')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '10 - 3|Level_3', '10 - 3|Level_3')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['File                                              '],
['File 20                                           '],
['File 20  170                                      '],
['File 20  170      63604808 20  40118 170 1  4870  '],
['File 20  280                                      '],
['File 20  280      69684558 20  40118 280 1  19000 '],
['File 20  280      69684558 20  40118 280 -1 -19000'],
['File 20  280      69694158 20  40118 280 1  5010  '],
['File 20  685                                      '],
['File 20  685      62684671 20  40118 685 1  69990 '],
['File 20  685      62684671 20  40118 685 -1 -69990'],
['File 59                                           '],
['File 59  335                                      '],
['File 59  335      61664713 59  40118 335 1  17990 '],
['File 59  335      61664713 59  40118 335 -1 -17990'],
['File 59  335      61684613 59  40118 335 1  12990 '],
['File 59  410                                      '],
['File 59  410      68634752 59  40118 410 1  8990  '],
['File 59  620                                      '],
['File 59  620      60694698 59  40118 620 1  3990  '],
['File 59  620      60664659 59  40118 620 1  3990  '],
['File 59  878                                      '],
['File 59  878      60614487 59  40118 878 1  5950  '],
['File 166                                          '],
['File 166 60                                       '],
['File 166 60       68654655 166 40118 60  1  5080  '],
['File 166 80                                       '],
['File 166 80       69624033 166 40118 80  1  18190 '],
['File 166 80       60604100 166 40118 80  1  13300 '],
['File 166 170                                      '],
['File 166 170      68674560 166 40118 170 1  5990  ']
])
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('names on first line', 'true')
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
            select('JTable_22', 'rows:[0],columns:[9 - 7|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '76 - 10|Sale_Price', '76 - 10|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '67 - 8|Qty_Sold', '67 - 8|Qty_Sold')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '44 - 8|Store_No', '44 - 8|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '25 - 7|Level_4', '25 - 7|Level_4')
            select('Layouts', 'Full Line')
            assert_p('JTable_22', 'Text', 'File    20      280             69684558   20       40118 280     -1       -19000', '{7, Full Line}')
            assert_content('JTable_22', [ ['Level_1 Level_2 Level_3 Level_4 Keycode_no Store_No DATE  Dept_No Qty_Sold Sale_Price'],
['File                                                                                 '],
['File    20                                                                           '],
['File    20      170                                                                  '],
['File    20      170             63604808   20       40118 170     1        4870      '],
['File    20      280                                                                  '],
['File    20      280             69684558   20       40118 280     1        19000     '],
['File    20      280             69684558   20       40118 280     -1       -19000    '],
['File    20      280             69694158   20       40118 280     1        5010      '],
['File    20      685                                                                  '],
['File    20      685             62684671   20       40118 685     1        69990     '],
['File    20      685             62684671   20       40118 685     -1       -69990    '],
['File    59                                                                           '],
['File    59      335                                                                  '],
['File    59      335             61664713   59       40118 335     1        17990     '],
['File    59      335             61664713   59       40118 335     -1       -17990    '],
['File    59      335             61684613   59       40118 335     1        12990     '],
['File    59      410                                                                  '],
['File    59      410             68634752   59       40118 410     1        8990      '],
['File    59      620                                                                  '],
['File    59      620             60694698   59       40118 620     1        3990      '],
['File    59      620             60664659   59       40118 620     1        3990      '],
['File    59      878                                                                  '],
['File    59      878             60614487   59       40118 878     1        5950      '],
['File    166                                                                          '],
['File    166     60                                                                   '],
['File    166     60              68654655   166      40118 60      1        5080      '],
['File    166     80                                                                   '],
['File    166     80              69624033   166      40118 80      1        18190     '],
['File    166     80              60604100   166      40118 80      1        13300     '],
['File    166     170                                                                  '],
['File    166     170             68674560   166      40118 170     1        5990      ']
])
            click('plaf.metal.MetalInternalFrameTitlePane_28', 1419, 1)
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('JTable_28', 'false', '{2, Include}')
##            select('JTable_31', 'rows:[2],columns:[Include]')
##            select('JTable_31', 'rows:[5],columns:[Include]')
            select('JTable_28', 'rows:[5],columns:[Include]')
            select('JTable_28', 'false', '{5, Include}')
            select('JTable_28', 'false', '{2, Include}')
            select('JTable_28', 'rows:[5],columns:[Include]')
##            select('JTable_31', 'false', '{5, Include}')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['Level_1', 'Level_2', 'Level_3', 'Level_4', 'Keycode_no', 'Store_No', 'Dept_No', 'Qty_Sold'],
['File', '', '', '', '', '', '', ''],
['File', '20', '', '', '', '', '', ''],
['File', '20', '170', '', '', '', '', ''],
['File', '20', '170', '', '63604808', '20', '170', '1'],
['File', '20', '280', '', '', '', '', ''],
['File', '20', '280', '', '69684558', '20', '280', '1'],
['File', '20', '280', '', '69684558', '20', '280', '-1'],
['File', '20', '280', '', '69694158', '20', '280', '1'],
['File', '20', '685', '', '', '', '', ''],
['File', '20', '685', '', '62684671', '20', '685', '1'],
['File', '20', '685', '', '62684671', '20', '685', '-1'],
['File', '59', '', '', '', '', '', ''],
['File', '59', '335', '', '', '', '', ''],
['File', '59', '335', '', '61664713', '59', '335', '1'],
['File', '59', '335', '', '61664713', '59', '335', '-1'],
['File', '59', '335', '', '61684613', '59', '335', '1'],
['File', '59', '410', '', '', '', '', ''],
['File', '59', '410', '', '68634752', '59', '410', '1'],
['File', '59', '620', '', '', '', '', ''],
['File', '59', '620', '', '60694698', '59', '620', '1'],
['File', '59', '620', '', '60664659', '59', '620', '1'],
['File', '59', '878', '', '', '', '', ''],
['File', '59', '878', '', '60614487', '59', '878', '1'],
['File', '166', '', '', '', '', '', ''],
['File', '166', '60', '', '', '', '', ''],
['File', '166', '60', '', '68654655', '166', '60', '1'],
['File', '166', '80', '', '', '', '', ''],
['File', '166', '80', '', '69624033', '166', '80', '1'],
['File', '166', '80', '', '60604100', '166', '80', '1'],
['File', '166', '170', '', '', '', '', ''],
['File', '166', '170', '', '68674560', '166', '170', '1']
])


            select('JTable_22', 'rows:[0],columns:[9 - 7|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '61 - 8|Qty_Sold', '61 - 8|Qty_Sold')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '53 - 7|Dept_No', '53 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '44 - 8|Store_No', '44 - 8|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '33 - 10|Keycode_no', '33 - 10|Keycode_no')
            select('Layouts', 'Full Line')
            assert_p('JTable_22', 'Text', 'File    20      685             62684671   20       685     1', '{10, Full Line}')
            assert_content('JTable_22', [ ['Level_1 Level_2 Level_3 Level_4 Keycode_no Store_No Dept_No Qty_Sold'],
['File                                                                '],
['File    20                                                          '],
['File    20      170                                                 '],
['File    20      170             63604808   20       170     1       '],
['File    20      280                                                 '],
['File    20      280             69684558   20       280     1       '],
['File    20      280             69684558   20       280     -1      '],
['File    20      280             69694158   20       280     1       '],
['File    20      685                                                 '],
['File    20      685             62684671   20       685     1       '],
['File    20      685             62684671   20       685     -1      '],
['File    59                                                          '],
['File    59      335                                                 '],
['File    59      335             61664713   59       335     1       '],
['File    59      335             61664713   59       335     -1      '],
['File    59      335             61684613   59       335     1       '],
['File    59      410                                                 '],
['File    59      410             68634752   59       410     1       '],
['File    59      620                                                 '],
['File    59      620             60694698   59       620     1       '],
['File    59      620             60664659   59       620     1       '],
['File    59      878                                                 '],
['File    59      878             60614487   59       878     1       '],
['File    166                                                         '],
['File    166     60                                                  '],
['File    166     60              68654655   166      60      1       '],
['File    166     80                                                  '],
['File    166     80              69624033   166      80      1       '],
['File    166     80              60604100   166      80      1       '],
['File    166     170                                                 '],
['File    166     170             68674560   166      170     1       ']
])
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('names on first line', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['File', '', '', '', '', '', '', ''],
['File', '20', '', '', '', '', '', ''],
['File', '20', '170', '', '', '', '', ''],
['File', '20', '170', '', '63604808', '20', '170', '1'],
['File', '20', '280', '', '', '', '', ''],
['File', '20', '280', '', '69684558', '20', '280', '1'],
['File', '20', '280', '', '69684558', '20', '280', '-1'],
['File', '20', '280', '', '69694158', '20', '280', '1'],
['File', '20', '685', '', '', '', '', ''],
['File', '20', '685', '', '62684671', '20', '685', '1'],
['File', '20', '685', '', '62684671', '20', '685', '-1'],
['File', '59', '', '', '', '', '', ''],
['File', '59', '335', '', '', '', '', ''],
['File', '59', '335', '', '61664713', '59', '335', '1'],
['File', '59', '335', '', '61664713', '59', '335', '-1'],
['File', '59', '335', '', '61684613', '59', '335', '1'],
['File', '59', '410', '', '', '', '', ''],
['File', '59', '410', '', '68634752', '59', '410', '1'],
['File', '59', '620', '', '', '', '', ''],
['File', '59', '620', '', '60694698', '59', '620', '1'],
['File', '59', '620', '', '60664659', '59', '620', '1'],
['File', '59', '878', '', '', '', '', ''],
['File', '59', '878', '', '60614487', '59', '878', '1'],
['File', '166', '', '', '', '', '', ''],
['File', '166', '60', '', '', '', '', ''],
['File', '166', '60', '', '68654655', '166', '60', '1'],
['File', '166', '80', '', '', '', '', ''],
['File', '166', '80', '', '69624033', '166', '80', '1'],
['File', '166', '80', '', '60604100', '166', '80', '1'],
['File', '166', '170', '', '', '', '', ''],
['File', '166', '170', '', '68674560', '166', '170', '1']
])
            select('JTable_22', 'rows:[0],columns:[6 - 3|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '47 - 8|Qty_Sold', '47 - 8|Qty_Sold')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '39 - 7|Dept_No', '39 - 7|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '30 - 8|Store_No', '30 - 8|Store_No')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['File                                                  '],
['File 20                                               '],
['File 20  170                                          '],
['File 20  170      63604808   20       170     1       '],
['File 20  280                                          '],
['File 20  280      69684558   20       280     1       '],
['File 20  280      69684558   20       280     -1      '],
['File 20  280      69694158   20       280     1       '],
['File 20  685                                          '],
['File 20  685      62684671   20       685     1       '],
['File 20  685      62684671   20       685     -1      '],
['File 59                                               '],
['File 59  335                                          '],
['File 59  335      61664713   59       335     1       '],
['File 59  335      61664713   59       335     -1      '],
['File 59  335      61684613   59       335     1       '],
['File 59  410                                          '],
['File 59  410      68634752   59       410     1       '],
['File 59  620                                          '],
['File 59  620      60694698   59       620     1       '],
['File 59  620      60664659   59       620     1       '],
['File 59  878                                          '],
['File 59  878      60614487   59       878     1       '],
['File 166                                              '],
['File 166 60                                           '],
['File 166 60       68654655   166      60      1       '],
['File 166 80                                           '],
['File 166 80       69624033   166      80      1       '],
['File 166 80       60604100   166      80      1       '],
['File 166 170                                          '],
['File 166 170      68674560   166      170     1       ']
])
            click('Close')
        close()

##        window_closed('Record Editor')
    close()

    pass
