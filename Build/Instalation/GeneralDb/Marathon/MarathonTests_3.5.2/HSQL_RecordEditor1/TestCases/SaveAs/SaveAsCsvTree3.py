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
            assert_p('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'Text', 'rows:[5],columns:[DATE]')
            select('net.sf.RecordEditor.utils.swing.treeTable.JTreeTable_10', 'rows:[5],columns:[DATE]')
        close()


        select_menu('File>>Export as CSV file')

        if frame('Export - DTAR020_tst1.bin:0'):

            select('Only export Nodes with Data', 'true')
            select('Edit Output File', 'true')
            select('names on first line', 'true')
            select('Keep screen open', 'true')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            select('JTable_22', 'rows:[7],columns:[5|KEYCODE-NO]')
            assert_content('JTable_22', [ ['File', '20', '170', '', '63604808', '20', '40118', '170', '1', '4.87'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '1', '19.00'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '-1', '-19.00'],
['File', '20', '280', '', '69694158', '20', '40118', '280', '1', '5.01'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '1', '69.99'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '-1', '-69.99'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '1', '17.99'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '-1', '-17.99'],
['File', '59', '335', '', '61684613', '59', '40118', '335', '1', '12.99'],
['File', '59', '410', '', '68634752', '59', '40118', '410', '1', '8.99'],
['File', '59', '620', '', '60694698', '59', '40118', '620', '1', '3.99'],
['File', '59', '620', '', '60664659', '59', '40118', '620', '1', '3.99'],
['File', '59', '878', '', '60614487', '59', '40118', '878', '1', '5.95'],
['File', '166', '60', '', '68654655', '166', '40118', '60', '1', '5.08'],
['File', '166', '80', '', '69624033', '166', '40118', '80', '1', '18.19'],
['File', '166', '80', '', '60604100', '166', '40118', '80', '1', '13.30'],
['File', '166', '170', '', '68674560', '166', '40118', '170', '1', '5.99']
])
            select('JTable_22', 'rows:[7],columns:[5|KEYCODE-NO]')
            select('JTable_22', 'rows:[7],columns:[5|KEYCODE-NO]')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_content('JTable_22', [ ['File\t20\t170\t\t63604808\t20\t40118\t170\t1\t4.87'],
['File\t20\t280\t\t69684558\t20\t40118\t280\t1\t19.00'],
['File\t20\t280\t\t69684558\t20\t40118\t280\t-1\t-19.00'],
['File\t20\t280\t\t69694158\t20\t40118\t280\t1\t5.01'],
['File\t20\t685\t\t62684671\t20\t40118\t685\t1\t69.99'],
['File\t20\t685\t\t62684671\t20\t40118\t685\t-1\t-69.99'],
['File\t59\t335\t\t61664713\t59\t40118\t335\t1\t17.99'],
['File\t59\t335\t\t61664713\t59\t40118\t335\t-1\t-17.99'],
['File\t59\t335\t\t61684613\t59\t40118\t335\t1\t12.99'],
['File\t59\t410\t\t68634752\t59\t40118\t410\t1\t8.99'],
['File\t59\t620\t\t60694698\t59\t40118\t620\t1\t3.99'],
['File\t59\t620\t\t60664659\t59\t40118\t620\t1\t3.99'],
['File\t59\t878\t\t60614487\t59\t40118\t878\t1\t5.95'],
['File\t166\t60\t\t68654655\t166\t40118\t60\t1\t5.08'],
['File\t166\t80\t\t69624033\t166\t40118\t80\t1\t18.19'],
['File\t166\t80\t\t60604100\t166\t40118\t80\t1\t13.30'],
['File\t166\t170\t\t68674560\t166\t40118\t170\t1\t5.99']
])
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            click('Close')
##            select('JTable_22', '', '{6, Full Line}')
##            select('JTable_22', 'rows:[6],columns:[Full Line]')
        close()

        if frame('Export - DTAR020_tst1.bin:0'):
            select('Delimiter', ',')
            select('names on first line', 'false')
            click('save file')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            select('JTable_22', 'rows:[7],columns:[5|KEYCODE-NO]')
            assert_content('JTable_22', [ ['File', '20', '170', '', '63604808', '20', '40118', '170', '1', '4.87'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '1', '19.00'],
['File', '20', '280', '', '69684558', '20', '40118', '280', '-1', '-19.00'],
['File', '20', '280', '', '69694158', '20', '40118', '280', '1', '5.01'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '1', '69.99'],
['File', '20', '685', '', '62684671', '20', '40118', '685', '-1', '-69.99'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '1', '17.99'],
['File', '59', '335', '', '61664713', '59', '40118', '335', '-1', '-17.99'],
['File', '59', '335', '', '61684613', '59', '40118', '335', '1', '12.99'],
['File', '59', '410', '', '68634752', '59', '40118', '410', '1', '8.99'],
['File', '59', '620', '', '60694698', '59', '40118', '620', '1', '3.99'],
['File', '59', '620', '', '60664659', '59', '40118', '620', '1', '3.99'],
['File', '59', '878', '', '60614487', '59', '40118', '878', '1', '5.95'],
['File', '166', '60', '', '68654655', '166', '40118', '60', '1', '5.08'],
['File', '166', '80', '', '69624033', '166', '40118', '80', '1', '18.19'],
['File', '166', '80', '', '60604100', '166', '40118', '80', '1', '13.30'],
['File', '166', '170', '', '68674560', '166', '40118', '170', '1', '5.99']
])
            select('JTable_22', 'rows:[7],columns:[5|KEYCODE-NO]')
            select('JTable_22', 'rows:[7],columns:[5|KEYCODE-NO]')
            select('Layouts', 'Full Line')
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            assert_content('JTable_22', [ ['File,20,170,,63604808,20,40118,170,1,4.87'],
['File,20,280,,69684558,20,40118,280,1,19.00'],
['File,20,280,,69684558,20,40118,280,-1,-19.00'],
['File,20,280,,69694158,20,40118,280,1,5.01'],
['File,20,685,,62684671,20,40118,685,1,69.99'],
['File,20,685,,62684671,20,40118,685,-1,-69.99'],
['File,59,335,,61664713,59,40118,335,1,17.99'],
['File,59,335,,61664713,59,40118,335,-1,-17.99'],
['File,59,335,,61684613,59,40118,335,1,12.99'],
['File,59,410,,68634752,59,40118,410,1,8.99'],
['File,59,620,,60694698,59,40118,620,1,3.99'],
['File,59,620,,60664659,59,40118,620,1,3.99'],
['File,59,878,,60614487,59,40118,878,1,5.95'],
['File,166,60,,68654655,166,40118,60,1,5.08'],
['File,166,80,,69624033,166,40118,80,1,18.19'],
['File,166,80,,60604100,166,40118,80,1,13.30'],
['File,166,170,,68674560,166,40118,170,1,5.99']
])
            select('JTable_22', 'rows:[6],columns:[Full Line]')
            click('Close')
##           select('JTable_22', '', '{6, Full Line}')
##            select('JTable_22', 'rows:[6],columns:[Full Line]')
        close()



    pass
