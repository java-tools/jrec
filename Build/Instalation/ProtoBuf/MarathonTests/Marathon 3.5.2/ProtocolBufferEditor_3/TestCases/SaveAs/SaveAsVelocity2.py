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
##        select_menu('File>>Save via Velociy Skelton>>toCsv_Tab.vm')

##        select_menu('File>>Export via Velociy Skelton>>toCsv_Tab.vm')
        select_menu('File>>SaveAs_Velocity>>toCsv_Tab.vm')

        if frame('Export - DTAR020_tst1.bin:0'):
            select('File Name', commonBits.sampleDir() + 'DTAR020_tst1.bin.csv')
            select('Edit Output File', 'true')
            click('save file')
        close()

##        if frame('Table:  - dtar020_tst1.bin.csv:0'):
##        if window('<NoTitle>'):

##        if window(''):
        if window('<NoTitle>'):
            assert_content('JTable_29', [ ['63604808', '20', '40118', '170', '1', '4870'],
['69684558', '20', '40118', '280', '1', '19000'],
['69684558', '20', '40118', '280', '-1', '-19000'],
['69694158', '20', '40118', '280', '1', '5010'],
['62684671', '20', '40118', '685', '1', '69990'],
['62684671', '20', '40118', '685', '-1', '-69990'],
['61664713', '59', '40118', '335', '1', '17990'],
['61664713', '59', '40118', '335', '-1', '-17990'],
['61684613', '59', '40118', '335', '1', '12990'],
['68634752', '59', '40118', '410', '1', '8990'],
['60694698', '59', '40118', '620', '1', '3990'],
['60664659', '59', '40118', '620', '1', '3990'],
['60614487', '59', '40118', '878', '1', '5950'],
['68654655', '166', '40118', '60', '1', '5080'
],
['69624033', '166', '40118', '80', '1', '18190'],
['60604100', '166', '40118', '80', '1', '13300'],
['68674560', '166', '40118', '170', '1', '5990'],
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
            assert_p('JTable_22', 'Text', '61684613', '{8, 1|Keycode_no}')
            assert_content('JTable_22', [ ['63604808', '20', '40118', '170', '1', '4870'],
['69684558', '20', '40118', '280', '1', '19000'],
['69684558', '20', '40118', '280', '-1', '-19000'],
['69694158', '20', '40118', '280', '1', '5010'],
['62684671', '20', '40118', '685', '1', '69990'],
['62684671', '20', '40118', '685', '-1', '-69990'],
['61664713', '59', '40118', '335', '1', '17990'],
['61664713', '59', '40118', '335', '-1', '-17990'],
['61684613', '59', '40118', '335', '1', '12990'],
['68634752', '59', '40118', '410', '1', '8990'],
['60694698', '59', '40118', '620', '1', '3990'],
['60664659', '59', '40118', '620', '1', '3990'],
['60614487', '59', '40118', '878', '1', '5950'],
['68654655', '166', '40118', '60', '1', '5080'
],
['69624033', '166', '40118', '80', '1', '18190'],
['60604100', '166', '40118', '80', '1', '13300'],
['68674560', '166', '40118', '170', '1', '5990']
])
            select('JTable_22', 'rows:[0],columns:[2|Store_No]')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '6|Sale_Price', '6|Sale_Price')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '5|Qty_Sold', '5|Qty_Sold')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '4|Dept_No', '4|Dept_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '3|DATE', '3|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '2|Store_No', '2|Store_No')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '1|Keycode_no', '1|Keycode_no')
            assert_p('Layouts', 'Text', 'GeneratedCsvRecord')
            select('Layouts', 'Full Line')
            assert_p('JTable_22', 'Text', '62684671\t20\t40118\t685\t-1\t-69990', '{5, Full Line}')
            assert_content('JTable_22', [ ['63604808\t20\t40118\t170\t1\t4870\t'],
['69684558\t20\t40118\t280\t1\t19000\t'],
['69684558\t20\t40118\t280\t-1\t-19000\t'],
['69694158\t20\t40118\t280\t1\t5010\t'],
['62684671\t20\t40118\t685\t1\t69990\t'],
['62684671\t20\t40118\t685\t-1\t-69990\t'],
['61664713\t59\t40118\t335\t1\t17990\t'],
['61664713\t59\t40118\t335\t-1\t-17990\t'],
['61684613\t59\t40118\t335\t1\t12990\t'],
['68634752\t59\t40118\t410\t1\t8990\t'],
['60694698\t59\t40118\t620\t1\t3990\t'],
['60664659\t59\t40118\t620\t1\t3990\t'],
['60614487\t59\t40118\t878\t1\t5950\t'],
['68654655\t166\t40118\t60\t1\t5080\t'],
['69624033\t166\t40118\t80\t1\t18190\t'],
['60604100\t166\t40118\t80\t1\t13300\t'],
['68674560\t166\t40118\t170\t1\t5990\t']
])
            click('Close')
        close()

##        window_closed('Record Editor')
    close()

    pass
