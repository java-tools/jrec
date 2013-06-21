useFixture(reCsvEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		select('FilePane$3', 'XfdDTAR020.csv.csv')
		doubleclick('FilePane$3', '6')
		select('FilePane$3', 'XfdDTAR020.csv.csv')
		click('FilePane$3', 3, '6')
		click(commonBits.fl('Edit') + '1')
		select('LineList.FileDisplay_JTbl', 'cell:3|DATE,8(40118)')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 5.01], [69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89], [63674861, 20, 40118, 957, 10, 2.70], [65674532, 20, 40118, 929, 1, 3.59], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [62684217, 59, 40118, 957, 1, 9.99], [67674686, 59, 40118, 929, 1, 3.99]]')
		select('LineList.FileDisplay_JTbl', 'cell:3|DATE,8(40118)')
		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Update Csv Columns'))
		select('LineList.FileDisplay_JTbl', 'cell:3|DATE,8(40118)')
##		select('FieldChange_JTbl', '')
		rightclick('FieldChange_JTbl', commonBits.fl('Decimal Places') + ',0')
		select_menu(commonBits.fl('Move ...') + '>>' + commonBits.fl('After DATE'))
		assert_p('FieldChange_JTbl', 'Content', '[[STORE-NO, true, ' + commonBits.fl('Text') + ', , , ], [DATE, true, ' + commonBits.fl('Text') + ', , , ], [KEYCODE-NO, true, ' + commonBits.fl('Text') + ', , , ], [DEPT-NO, true, ' + commonBits.fl('Text') + ', , , ], [QTY-SOLD, true, ' + commonBits.fl('Text') + ', , , ], [SALE-PRICE, true, ' + commonBits.fl('Text') + ', , , ]]')
##		select('FieldChange_JTbl', '')
		rightclick('FieldChange_JTbl', commonBits.fl('Decimal Places') + ',5')
		select_menu(commonBits.fl('Move ...') + '>>' + commonBits.fl('Before STORE-NO'))
		assert_p('FieldChange_JTbl', 'Content', '[[SALE-PRICE, true, ' + commonBits.fl('Text') + ', , , ], [STORE-NO, true, ' + commonBits.fl('Text') + ', , , ], [DATE, true, ' + commonBits.fl('Text') + ', , , ], [KEYCODE-NO, true, ' + commonBits.fl('Text') + ', , , ], [DEPT-NO, true, ' + commonBits.fl('Text') + ', , , ], [QTY-SOLD, true, ' + commonBits.fl('Text') + ', , , ]]')
##		select('FieldChange_JTbl', '')
		rightclick('FieldChange_JTbl', commonBits.fl('Decimal Places') + ',2')
		select_menu(commonBits.fl('Move ...') + '>>' + commonBits.fl('After QTY-SOLD'))
		assert_p('FieldChange_JTbl', 'Content', '[[SALE-PRICE, true, ' + commonBits.fl('Text') + ', , , ], [STORE-NO, true, ' + commonBits.fl('Text') + ', , , ], [KEYCODE-NO, true, ' + commonBits.fl('Text') + ', , , ], [DEPT-NO, true, ' + commonBits.fl('Text') + ', , , ], [QTY-SOLD, true, ' + commonBits.fl('Text') + ', , , ], [DATE, true, ' + commonBits.fl('Text') + ', , , ]]')
		click(commonBits.fl('Apply'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[5.01, 20, 69684558, 280, 1, 40118], [19.00, 20, 69694158, 280, 1, 40118], [-19.00, 20, 69694158, 280, -1, 40118], [5.01, 20, 69694158, 280, 1, 40118], [4.87, 20, 63604808, 170, 1, 40118], [69.99, 20, 62684671, 685, 1, 40118], [-69.99, 20, 62684671, 685, -1, 40118], [3.99, 20, 64634429, 957, 1, 40118], [0.89, 20, 66624458, 957, 1, 40118], [2.70, 20, 63674861, 957, 10, 40118], [3.59, 20, 65674532, 929, 1, 40118], [1.99, 59, 64614401, 957, 1, 40118], [1.99, 59, 64614401, 957, 1, 40118], [17.99, 59, 61664713, 335, 1, 40118], [-17.99, 59, 61664713, 335, -1, 40118], [8.99, 59, 68634752, 410, 1, 40118], [5.95, 59, 60614487, 878, 1, 40118], [12.65, 59, 63644339, 878, 1, 40118], [3.99, 59, 60694698, 620, 1, 40118], [3.99, 59, 60664659, 620, 1, 40118], [9.99, 59, 62684217, 957, 1, 40118], [3.99, 59, 67674686, 929, 1, 40118]]')
		select('LineList.Layouts_Txt', commonBits.fl('Full Line'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[5.01;20;69684558;280;1;40118], [19.00;20;69694158;280;1;40118], [-19.00;20;69694158;280;-1;40118], [5.01;20;69694158;280;1;40118], [4.87;20;63604808;170;1;40118], [69.99;20;62684671;685;1;40118], [-69.99;20;62684671;685;-1;40118], [3.99;20;64634429;957;1;40118], [0.89;20;66624458;957;1;40118], [2.70;20;63674861;957;10;40118], [3.59;20;65674532;929;1;40118], [1.99;59;64614401;957;1;40118], [1.99;59;64614401;957;1;40118], [17.99;59;61664713;335;1;40118], [-17.99;59;61664713;335;-1;40118], [8.99;59;68634752;410;1;40118], [5.95;59;60614487;878;1;40118], [12.65;59;63644339;878;1;40118], [3.99;59;60694698;620;1;40118], [3.99;59;60664659;620;1;40118], [9.99;59;62684217;957;1;40118], [3.99;59;67674686;929;1;40118]]')

##		if window(rcommonBits.fl('Save Changes to file: C:\Users\BruceTst2\.RecordEditor\reCsvEd\SampleFiles\XfdDTAR020.csv.csv')):
##			click('No')
##		close()
	close()
