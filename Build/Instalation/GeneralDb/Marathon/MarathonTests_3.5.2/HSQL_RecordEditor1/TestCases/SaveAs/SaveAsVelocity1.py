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
        select_menu('File>>Save via Velociy Skelton>>toCsv_Comma.vm')

        if frame('Save as - DTAR020_tst1.bin:0'):
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
            assert_p('JComboBox_9', 'Text', ',')
            assert_p('Parser', 'Text', 'Basic Parser')
            assert_p('Names on Line', 'Text', 'true')
            assert_p('Line Number of Names', 'Text', '1')
            click('Go')
        close()

        if frame('Table:  - DTAR020_tst1.bin.csv:0'):
            assert_p('JTable_22', 'Text', '62684671', '{4, 1|KEYCODE-NO}')
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
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '5|QTY-SOLD', '5|QTY-SOLD')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '4|DEPT-NO', '4|DEPT-NO')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '3|DATE', '3|DATE')
            assert_p('net.sf.RecordEditor.edit.display.BaseDisplay$HeaderToolTips_27', 'Text', '2|STORE-NO', '2|STORE-NO')
            select('Layouts', 'Full Line')
            assert_content('JTable_22', [ ['63604808,20,40118,170,1,4.87,'],
['69684558,20,40118,280,1,19.00,'],
['69684558,20,40118,280,-1,-19.00,'],
['69694158,20,40118,280,1,5.01,'],
['62684671,20,40118,685,1,69.99,'],
['62684671,20,40118,685,-1,-69.99,'],
['61664713,59,40118,335,1,17.99,'],
['61664713,59,40118,335,-1,-17.99,'],
['61684613,59,40118,335,1,12.99,'],
['68634752,59,40118,410,1,8.99,'],
['60694698,59,40118,620,1,3.99,'],
['60664659,59,40118,620,1,3.99,'],
['60614487,59,40118,878,1,5.95,'],
['68654655,166,40118,60,1,5.08,'],
['69624033,166,40118,80,1,18.19,'],
['60604100,166,40118,80,1,13.30,'],
['68674560,166,40118,170,1,5.99,']
])
            click('Close')
        close()

##        if frame('Table:  - DTAR020_tst1.bin:0'):
##            click('Close')
##        close()

##        window_closed('Record Editor')
    close()

    pass