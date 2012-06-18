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
            assert_p('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'Text', 'rows:[5],columns:[DATE]')
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[5],columns:[DATE]')
        close()


        select_menu('File>>Export as CSV file')

        if frame('Export1 - DTAR020_tst1.bin:0'):

            select('Edit Output File', 'true')
            select('Only export Nodes with Data', 'true')
            select('names on first line', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            select('JTable_22', 'rows:[7],columns:[5|Keycode_no]')
            assert_content('JTable_22', [ ['File', '20', '170', '', '63604808', '20', '40118', '170', '1', '4870'],
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
            select('JTable_22', 'rows:[7],columns:[5|Keycode_no]')
            select('JTable_22', 'rows:[7],columns:[5|Keycode_no]')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_content('JTable_22', [ ['File\t20\t170\t\t63604808\t20\t40118\t170\t1\t4870'],
['File\t20\t280\t\t69684558\t20\t40118\t280\t1\t19000'],
['File\t20\t280\t\t69684558\t20\t40118\t280\t-1\t-19000'],
['File\t20\t280\t\t69694158\t20\t40118\t280\t1\t5010'],
['File\t20\t685\t\t62684671\t20\t40118\t685\t1\t69990'],
['File\t20\t685\t\t62684671\t20\t40118\t685\t-1\t-69990'],
['File\t59\t335\t\t61664713\t59\t40118\t335\t1\t17990'],
['File\t59\t335\t\t61664713\t59\t40118\t335\t-1\t-17990'],
['File\t59\t335\t\t61684613\t59\t40118\t335\t1\t12990'],
['File\t59\t410\t\t68634752\t59\t40118\t410\t1\t8990'],
['File\t59\t620\t\t60694698\t59\t40118\t620\t1\t3990'],
['File\t59\t620\t\t60664659\t59\t40118\t620\t1\t3990'],
['File\t59\t878\t\t60614487\t59\t40118\t878\t1\t5950'],
['File\t166\t60\t\t68654655\t166\t40118\t60\t1\t5080'
],
['File\t166\t80\t\t69624033\t166\t40118\t80\t1\t18190'],
['File\t166\t80\t\t60604100\t166\t40118\t80\t1\t13300'],
['File\t166\t170\t\t68674560\t166\t40118\t170\t1\t5990']
])
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{6, Full Line}')
##            select('JTable_22', 'rows:[6],columns:[Full Line]')
        close()

        if frame('Export1 - DTAR020_tst1.bin:0'):
            select('Delimiter', ',')
            select('names on first line', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            select('JTable_22', 'rows:[7],columns:[5|Keycode_no]')
            assert_content('JTable_22', [ ['File', '20', '170', '', '63604808', '20', '40118', '170', '1', '4870'],
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
            select('JTable_22', 'rows:[7],columns:[5|Keycode_no]')
            select('JTable_22', 'rows:[7],columns:[5|Keycode_no]')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_content('JTable_22', [ ['File,20,170,,63604808,20,40118,170,1,4870'],
['File,20,280,,69684558,20,40118,280,1,19000'],
['File,20,280,,69684558,20,40118,280,-1,-19000'],
['File,20,280,,69694158,20,40118,280,1,5010'],
['File,20,685,,62684671,20,40118,685,1,69990'],
['File,20,685,,62684671,20,40118,685,-1,-69990'],
['File,59,335,,61664713,59,40118,335,1,17990'],
['File,59,335,,61664713,59,40118,335,-1,-17990'],
['File,59,335,,61684613,59,40118,335,1,12990'],
['File,59,410,,68634752,59,40118,410,1,8990'],
['File,59,620,,60694698,59,40118,620,1,3990'],
['File,59,620,,60664659,59,40118,620,1,3990'],
['File,59,878,,60614487,59,40118,878,1,5950'],
['File,166,60,,68654655,166,40118,60,1,5080'
],
['File,166,80,,69624033,166,40118,80,1,18190'],
['File,166,80,,60604100,166,40118,80,1,13300'],
['File,166,170,,68674560,166,40118,170,1,5990']
])
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            click('Close')
##           select('JTable_22', '', '{6, Full Line}')
##            select('JTable_22', 'rows:[6],columns:[Full Line]')
        close()

        if frame('Export1 - DTAR020_tst1.bin:0'):
            select('JTable_32', 'false', '{2, Include}')
            select('JTable_32', 'false', '{5, Include}')
##            select('JTable_37', 'rows:[5],columns:[Include]')
##            select('JTable_37', 'rows:[2],columns:[Include]')
##            select('JTable_37', 'rows:[2],columns:[Include]')
##            select('JTable_37', 'rows:[2],columns:[Include]')
##            select('JTable_37', 'rows:[5],columns:[Include]')
##            select('JTable_37', 'rows:[5],columns:[Include]')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            select('JTable_22', 'rows:[8],columns:[5|Keycode_no]')
            assert_content('JTable_22', [ ['File', '20', '170', '', '63604808', '20', '170', '1'],
['File', '20', '280', '', '69684558', '20', '280', '1'],
['File', '20', '280', '', '69684558', '20', '280', '-1'],
['File', '20', '280', '', '69694158', '20', '280', '1'],
['File', '20', '685', '', '62684671', '20', '685', '1'],
['File', '20', '685', '', '62684671', '20', '685', '-1'],
['File', '59', '335', '', '61664713', '59', '335', '1'],
['File', '59', '335', '', '61664713', '59', '335', '-1'],
['File', '59', '335', '', '61684613', '59', '335', '1'],
['File', '59', '410', '', '68634752', '59', '410', '1'],
['File', '59', '620', '', '60694698', '59', '620', '1'],
['File', '59', '620', '', '60664659', '59', '620', '1'],
['File', '59', '878', '', '60614487', '59', '878', '1'],
['File', '166', '60', '', '68654655', '166', '60', '1'],
['File', '166', '80', '', '69624033', '166', '80', '1'],
['File', '166', '80', '', '60604100', '166', '80', '1'],
['File', '166', '170', '', '68674560', '166', '170', '1']
])
            select('JTable_22', 'rows:[8],columns:[5|Keycode_no]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '5|Keycode_no', '5|Keycode_no')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '4|Level_4', '4|Level_4')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '6|Store_No', '6|Store_No')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[5],columns:[Full Line]')
            assert_content('JTable_22', [ ['File,20,170,,63604808,20,170,1'],
['File,20,280,,69684558,20,280,1'],
['File,20,280,,69684558,20,280,-1'],
['File,20,280,,69694158,20,280,1'],
['File,20,685,,62684671,20,685,1'],
['File,20,685,,62684671,20,685,-1'],
['File,59,335,,61664713,59,335,1'],
['File,59,335,,61664713,59,335,-1'],
['File,59,335,,61684613,59,335,1'],
['File,59,410,,68634752,59,410,1'],
['File,59,620,,60694698,59,620,1'],
['File,59,620,,60664659,59,620,1'],
['File,59,878,,60614487,59,878,1'],
['File,166,60,,68654655,166,60,1'],
['File,166,80,,69624033,166,80,1'],
['File,166,80,,60604100,166,80,1'],
['File,166,170,,68674560,166,170,1']
])
            select('JTable_22', 'rows:[5],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{5, Full Line}')
##            select('JTable_22', 'rows:[5],columns:[Full Line]')
        close()

        if frame('Export1 - DTAR020_tst1.bin:0'):
            select('names on first line', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            select('JTable_22', 'rows:[8],columns:[5|Keycode_no]')
            assert_content('JTable_22', [ ['File', '20', '170', '', '63604808', '20', '170', '1'],
['File', '20', '280', '', '69684558', '20', '280', '1'],
['File', '20', '280', '', '69684558', '20', '280', '-1'],
['File', '20', '280', '', '69694158', '20', '280', '1'],
['File', '20', '685', '', '62684671', '20', '685', '1'],
['File', '20', '685', '', '62684671', '20', '685', '-1'],
['File', '59', '335', '', '61664713', '59', '335', '1'],
['File', '59', '335', '', '61664713', '59', '335', '-1'],
['File', '59', '335', '', '61684613', '59', '335', '1'],
['File', '59', '410', '', '68634752', '59', '410', '1'],
['File', '59', '620', '', '60694698', '59', '620', '1'],
['File', '59', '620', '', '60664659', '59', '620', '1'],
['File', '59', '878', '', '60614487', '59', '878', '1'],
['File', '166', '60', '', '68654655', '166', '60', '1'],
['File', '166', '80', '', '69624033', '166', '80', '1'],
['File', '166', '80', '', '60604100', '166', '80', '1'],
['File', '166', '170', '', '68674560', '166', '170', '1']
])
            select('JTable_22', 'rows:[8],columns:[5|Keycode_no]')
##            assert_content('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', [ ['1|Level_1', '2|Level_2', '3|Level_3', '4|Level_4', '5|Keycode_no', '6|Store_No', '7|DATE', '8|Qty_Sold', '9|Sale_Price']
##])
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[5],columns:[Full Line]')
            assert_content('JTable_22', [ ['File,20,170,,63604808,20,170,1'],
['File,20,280,,69684558,20,280,1'],
['File,20,280,,69684558,20,280,-1'],
['File,20,280,,69694158,20,280,1'],
['File,20,685,,62684671,20,685,1'],
['File,20,685,,62684671,20,685,-1'],
['File,59,335,,61664713,59,335,1'],
['File,59,335,,61664713,59,335,-1'],
['File,59,335,,61684613,59,335,1'],
['File,59,410,,68634752,59,410,1'],
['File,59,620,,60694698,59,620,1'],
['File,59,620,,60664659,59,620,1'],
['File,59,878,,60614487,59,878,1'],
['File,166,60,,68654655,166,60,1'],
['File,166,80,,69624033,166,80,1'],
['File,166,80,,60604100,166,80,1'],
['File,166,170,,68674560,166,170,1']
])
            select('JTable_22', 'rows:[5],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{5, Full Line}')
##            select('JTable_22', 'rows:[5],columns:[Full Line]')
        close()

##        window_closed('Record Editor')
    close()

    pass
