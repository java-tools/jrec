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
        select_menu('View>>Sorted Field Tree')

        if frame('Create Sorted Tree - DTAR020_tst1.bin:0'):
            select('JTable_10', 'STORE-NO', '{0, Field}')
            select('JTable_10', 'DEPT-NO', '{1, Field}')
            select('JTable_10', 'rows:[1],columns:[Field]')
            click('Build Tree')
        close()

        if frame('Tree View - DTAR020_tst1.bin:0'):
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[5],columns:[DATE]')
##            assert_p('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'Component', '[,0,0,471x540,alignmentX=0.0,alignmentY=0.0,border=,flags=251658600,maximumSize=,minimumSize=,preferredSize=,autoCreateColumnsFromModel=true,autoResizeMode=AUTO_RESIZE_OFF,cellSelectionEnabled=false,editingColumn=-1,editingRow=-1,gridColor=javax.swing.plaf.ColorUIResource[r=122,g=138,b=153],preferredViewportSize=java.awt.Dimension[width=450,height=400],rowHeight=18,rowMargin=1,rowSelectionAllowed=true,selectionBackground=javax.swing.plaf.ColorUIResource[r=184,g=207,b=229],selectionForeground=sun.swing.PrintColorUIResource[r=51,g=51,b=51],showHorizontalLines=false,showVerticalLines=false]')
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[5],columns:[DATE]')
        close()

        click('Export')

        if frame('Export - DTAR020_tst1.bin:0'):
##            select('JTabbedPane_16', 'Fixed')
            select('File Name_2', 'Fixed')
            select('Only export Nodes with Data', 'true')
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
['File', '20', '170', '', '63604808', '20', '40118', '170', '1', '4.87'],
['File', '20', '280', '', '', '', '', '', '', ''],
['File', '20', '280', '', '69684558', '20', '40118', '280', '1', '19.00'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '-1', '-19.00'],
['File', '20', '280', '', '69694158', '20', '40118', '280', '1', '5.01'],
['File', '20', '685', '', '', '', '', '', '', ''],
['File', '20', '685', '', '62684671', '20', '40118', '685', '1', '69.99'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '-1', '-69.99'],
['File', '59', '', '', '', '', '', '', '', ''],
['File', '59', '335', '', '', '', '', '', '', ''],
['File', '59', '335', '', '61664713', '59', '40118', '335', '1', '17.99'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '-1', '-17.99'],
['File', '59', '335', '', '61684613', '59', '40118', '335', '1', '12.99'],
['File', '59', '410', '', '', '', '', '', '', ''],
['File', '59', '410', '', '68634752', '59', '40118', '410', '1', '8.99'],
['File', '59', '620', '', '', '', '', '', '', ''],
['File', '59', '620', '', '60694698', '59', '40118', '620', '1', '3.99'],
['File', '59', '620', '', '60664659', '59', '40118', '620', '1', '3.99'],
['File', '59', '878', '', '', '', '', '', '', ''],
['File', '59', '878', '', '60614487', '59', '40118', '878', '1', '5.95'],
['File', '166', '', '', '', '', '', '', '', ''],
['File', '166', '60', '', '', '', '', '', '', ''],
['File', '166', '60', '', '68654655', '166', '40118', '60', '1', '5.08'],
['File', '166', '80', '', '', '', '', '', '', ''],
['File', '166', '80', '', '69624033', '166', '40118', '80', '1', '18.19'],
['File', '166', '80', '', '60604100', '166', '40118', '80', '1', '13.30'],
['File', '166', '170', '', '', '', '', '', '', ''],
['File', '166', '170', '', '68674560', '166', '40118', '170', '1', '5.99']
])
            select('JTable_22', 'rows:[0],columns:[6 - 3|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '45 - 6|SALE-PRICE', '45 - 6|SALE-PRICE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '38 - 3|DEPT-NO', '38 - 3|DEPT-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '19 - 8|KEYCODE-NO', '19 - 8|KEYCODE-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '10 - 3|Level_3', '10 - 3|Level_3')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['File                                              '],
['File 20                                           '],
['File 20  170                                      '],
['File 20  170      63604808 20  40118 170 1  4.87  '],
['File 20  280                                      '],
['File 20  280      69684558 20  40118 280 1  19.00 '],
['File 20  280      69684558 20  40118 280 -1 -19.00'],
['File 20  280      69694158 20  40118 280 1  5.01  '],
['File 20  685                                      '],
['File 20  685      62684671 20  40118 685 1  69.99 '],
['File 20  685      62684671 20  40118 685 -1 -69.99'],
['File 59                                           '],
['File 59  335                                      '],
['File 59  335      61664713 59  40118 335 1  17.99 '],
['File 59  335      61664713 59  40118 335 -1 -17.99'],
['File 59  335      61684613 59  40118 335 1  12.99 '],
['File 59  410                                      '],
['File 59  410      68634752 59  40118 410 1  8.99  '],
['File 59  620                                      '],
['File 59  620      60694698 59  40118 620 1  3.99  '],
['File 59  620      60664659 59  40118 620 1  3.99  '],
['File 59  878                                      '],
['File 59  878      60614487 59  40118 878 1  5.95  '],
['File 166                                          '],
['File 166 60                                       '],
['File 166 60       68654655 166 40118 60  1  5.08  '],
['File 166 80                                       '],
['File 166 80       69624033 166 40118 80  1  18.19 '],
['File 166 80       60604100 166 40118 80  1  13.30 '],
['File 166 170                                      '],
['File 166 170      68674560 166 40118 170 1  5.99  ']
])
            click('Close')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('names on first line', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.txt:0'):
            assert_content('JTable_22', [ ['Level_1', 'Level_2', 'Level_3', 'Level_4', 'KEYCODE-NO', 'STORE-NO', 'DATE', 'DEPT-NO', 'QTY-SOLD', 'SALE-PRICE'],
['File', '', '', '', '', '', '', '', '', ''],
['File', '20', '', '', '', '', '', '', '', ''],
['File', '20', '170', '', '', '', '', '', '', ''],
['File', '20', '170', '', '63604808', '20', '40118', '170', '1', '4.87'],
['File', '20', '280', '', '', '', '', '', '', ''],
['File', '20', '280', '', '69684558', '20', '40118', '280', '1', '19.00'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '-1', '-19.00'],
['File', '20', '280', '', '69694158', '20', '40118', '280', '1', '5.01'],
['File', '20', '685', '', '', '', '', '', '', ''],
['File', '20', '685', '', '62684671', '20', '40118', '685', '1', '69.99'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '-1', '-69.99'],
['File', '59', '', '', '', '', '', '', '', ''],
['File', '59', '335', '', '', '', '', '', '', ''],
['File', '59', '335', '', '61664713', '59', '40118', '335', '1', '17.99'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '-1', '-17.99'],
['File', '59', '335', '', '61684613', '59', '40118', '335', '1', '12.99'],
['File', '59', '410', '', '', '', '', '', '', ''],
['File', '59', '410', '', '68634752', '59', '40118', '410', '1', '8.99'],
['File', '59', '620', '', '', '', '', '', '', ''],
['File', '59', '620', '', '60694698', '59', '40118', '620', '1', '3.99'],
['File', '59', '620', '', '60664659', '59', '40118', '620', '1', '3.99'],
['File', '59', '878', '', '', '', '', '', '', ''],
['File', '59', '878', '', '60614487', '59', '40118', '878', '1', '5.95'],
['File', '166', '', '', '', '', '', '', '', ''],
['File', '166', '60', '', '', '', '', '', '', ''],
['File', '166', '60', '', '68654655', '166', '40118', '60', '1', '5.08'],
['File', '166', '80', '', '', '', '', '', '', ''],
['File', '166', '80', '', '69624033', '166', '40118', '80', '1', '18.19'],
['File', '166', '80', '', '60604100', '166', '40118', '80', '1', '13.30'],
['File', '166', '170', '', '', '', '', '', '', ''],
['File', '166', '170', '', '68674560', '166', '40118', '170', '1', '5.99']
])
            select('JTable_22', 'rows:[0],columns:[9 - 7|Level_2]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '76 - 10|SALE-PRICE', '76 - 10|SALE-PRICE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '67 - 8|QTY-SOLD', '67 - 8|QTY-SOLD')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '44 - 8|STORE-NO', '44 - 8|STORE-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '25 - 7|Level_4', '25 - 7|Level_4')
            select('Layouts', 'Full Line')
            assert_p('JTable_22', 'Text', 'File    20      280             69684558   20       40118 280     -1       -19.00', '{7, Full Line}')
            assert_content('JTable_22', [ ['Level_1 Level_2 Level_3 Level_4 KEYCODE-NO STORE-NO DATE  DEPT-NO QTY-SOLD SALE-PRICE'],
['File                                                                                 '],
['File    20                                                                           '],
['File    20      170                                                                  '],
['File    20      170             63604808   20       40118 170     1        4.87      '],
['File    20      280                                                                  '],
['File    20      280             69684558   20       40118 280     1        19.00     '],
['File    20      280             69684558   20       40118 280     -1       -19.00    '],
['File    20      280             69694158   20       40118 280     1        5.01      '],
['File    20      685                                                                  '],
['File    20      685             62684671   20       40118 685     1        69.99     '],
['File    20      685             62684671   20       40118 685     -1       -69.99    '],
['File    59                                                                           '],
['File    59      335                                                                  '],
['File    59      335             61664713   59       40118 335     1        17.99     '],
['File    59      335             61664713   59       40118 335     -1       -17.99    '],
['File    59      335             61684613   59       40118 335     1        12.99     '],
['File    59      410                                                                  '],
['File    59      410             68634752   59       40118 410     1        8.99      '],
['File    59      620                                                                  '],
['File    59      620             60694698   59       40118 620     1        3.99      '],
['File    59      620             60664659   59       40118 620     1        3.99      '],
['File    59      878                                                                  '],
['File    59      878             60614487   59       40118 878     1        5.95      '],
['File    166                                                                          '],
['File    166     60                                                                   '],
['File    166     60              68654655   166      40118 60      1        5.08      '],
['File    166     80                                                                   '],
['File    166     80              69624033   166      40118 80      1        18.19     '],
['File    166     80              60604100   166      40118 80      1        13.30     '],
['File    166     170                                                                  '],
['File    166     170             68674560   166      40118 170     1        5.99      ']
])
            click('plaf.metal.MetalInternalFrameTitlePane_28', 1419, 1)
            click('Close')
        close()


    pass
