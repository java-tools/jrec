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
        ##commonBits.selectExport(select_menu,'via Velociy Skelton>>toCsv_Tab.vm')

##        select_menu('File>>Export via Velociy Skelton>>toCsv_Tab.vm')
##        select_menu('File>>SaveAs_Velocity>>toCsv_Comma.vm')
        select_menu('File>>Export via Velociy Skelton_2>>toCsv_Tab.vm')

        if frame('Export - DTAR020_tst1.bin:0'):
 ##           select('File Name', '/C:/JavaPrograms/RecordEdit/HSQL/SampleFiles/DTAR020_tst1.bin.csv')
            select('File Name', commonBits.sampleDir() + 'DTAR020_tst1.bin.csv')
            select('Edit Output File', 'true')
            click('save file')
        close()

        if window('<NoTitle>'):
            assert_content('JTable_29', [ ['63604808', '20', '40118', '170', '1', '4.87'],
['69684558', '20', '40118', '280', '1', '19.00'],
['69684558', '20', '40118', '280', '-1', '-19.00'],
['69694158', '20', '40118', '280', '1', '5.01'],
['62684671', '20', '40118', '685', '1', '69.99'],
['62684671', '20', '40118', '685', '-1', '-69.99'],
['61664713', '59', '40118', '335', '1', '17.99'],
['61664713', '59', '40118', '335', '-1', '-17.99'],
['61684613', '59', '40118', '335', '1', '12.99'],
['68634752', '59', '40118', '410', '1', '8.99'],
['60694698', '59', '40118', '620', '1', '3.99'],
['60664659', '59', '40118', '620', '1', '3.99'],
['60614487', '59', '40118', '878', '1', '5.95'],
['68654655', '166', '40118', '60', '1', '5.08'],
['69624033', '166', '40118', '80', '1', '18.19'],
['60604100', '166', '40118', '80', '1', '13.30'],
['68674560', '166', '40118', '170', '1', '5.99'],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', ''],
['', '', '', '', '', '']
])
#            assert_p('Line Number of Names', 'Text', '1')
#            assert_p('Names on Line', 'Text', 'true')
#            assert_p('Parser', 'Text', 'Basic Parser')
#            assert_p('Quote Character', 'Text', '<None>')
#            assert_p('JComboBox_9', 'Text', '<Tab>')
            click('Go')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            assert_p('JTable_22', 'Text', '61684613', '{8, 1|KEYCODE-NO}')
            assert_content('JTable_22', [ ['63604808', '20', '40118', '170', '1', '4.87'],
['69684558', '20', '40118', '280', '1', '19.00'],
['69684558', '20', '40118', '280', '-1', '-19.00'],
['69694158', '20', '40118', '280', '1', '5.01'],
['62684671', '20', '40118', '685', '1', '69.99'],
['62684671', '20', '40118', '685', '-1', '-69.99'],
['61664713', '59', '40118', '335', '1', '17.99'],
['61664713', '59', '40118', '335', '-1', '-17.99'],
['61684613', '59', '40118', '335', '1', '12.99'],
['68634752', '59', '40118', '410', '1', '8.99'],
['60694698', '59', '40118', '620', '1', '3.99'],
['60664659', '59', '40118', '620', '1', '3.99'],
['60614487', '59', '40118', '878', '1', '5.95'],
['68654655', '166', '40118', '60', '1', '5.08'],
['69624033', '166', '40118', '80', '1', '18.19'],
['60604100', '166', '40118', '80', '1', '13.30'],
['68674560', '166', '40118', '170', '1', '5.99']
])
            select('JTable_22', 'rows:[0],columns:[2|STORE-NO]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '6|SALE-PRICE', '6|SALE-PRICE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '5|QTY-SOLD', '5|QTY-SOLD')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '4|DEPT-NO', '4|DEPT-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '3|DATE', '3|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '2|STORE-NO', '2|STORE-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1|KEYCODE-NO', '1|KEYCODE-NO')
            assert_p('Layouts', 'Text', 'GeneratedCsvRecord')
            select('Layouts', 'Full Line')
            assert_p('JTable_22', 'Text', '62684671\t20\t40118\t685\t-1\t-69.99', '{5, Full Line}')
            assert_content('JTable_22', [ ['63604808\t20\t40118\t170\t1\t4.87\t'],
['69684558\t20\t40118\t280\t1\t19.00\t'],
['69684558\t20\t40118\t280\t-1\t-19.00\t'],
['69694158\t20\t40118\t280\t1\t5.01\t'],
['62684671\t20\t40118\t685\t1\t69.99\t'],
['62684671\t20\t40118\t685\t-1\t-69.99\t'],
['61664713\t59\t40118\t335\t1\t17.99\t'],
['61664713\t59\t40118\t335\t-1\t-17.99\t'],
['61684613\t59\t40118\t335\t1\t12.99\t'],
['68634752\t59\t40118\t410\t1\t8.99\t'],
['60694698\t59\t40118\t620\t1\t3.99\t'],
['60664659\t59\t40118\t620\t1\t3.99\t'],
['60614487\t59\t40118\t878\t1\t5.95\t'],
['68654655\t166\t40118\t60\t1\t5.08\t'],
['69624033\t166\t40118\t80\t1\t18.19\t'],
['60604100\t166\t40118\t80\t1\t13.30\t'],
['68674560\t166\t40118\t170\t1\t5.99\t']
])
            click('Close')
        close()

##        window_closed('Record Editor')
    close()

    pass
