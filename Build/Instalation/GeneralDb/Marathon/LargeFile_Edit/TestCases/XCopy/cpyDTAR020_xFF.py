useFixture(default)

def test():
	from Modules import commonBits
	import time

	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		select_menu('File>>File Copy Menu')
		click('*3')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('FileChooser1', commonBits.sampleDir() + 'XffDTAR020.csv')
		commonBits.setRecordLayout2(select, 'DTAR020')

##		click('ScrollPane$ScrollBar', 3, 37)
		select('TextField', 'x\'ff\'')
		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')
		click('Copy2')
		assert_p('TextField1', 'Text', 'Copy Done !!! ')
		click('Open')
		select('FileChooser', commonBits.sampleDir() + 'XffDTAR020.csv')
		click('Edit1')

		if window(''):
			select('CheckBox', 'true')
			assert_p('ComboBox', 'Text', 'x\'FF')
			assert_p('Table', 'Text', '69684558', 'KEYCODE-NO,0')
			select('Table', 'cell:DATE,0(40118)')
			assert_p('Table', 'Text', '40118', 'DATE,0')
			select('Table', 'cell:DEPT-NO,1(280)')
##			assert_p('Table', 'Content', '[[69684558, 20, 40118, 280, 1], [69684558, 20, 40118, 280, -1], [69684558, 20, 40118, 280, 1], [69694158, 20, 40118, 280, 1], [69694158, 20, 40118, 280, -1], [69694158, 20, 40118, 280, 1], [63604808, 20, 40118, 170, 1], [62684671, 20, 40118, 685, 1], [62684671, 20, 40118, 685, -1], [64634429, 20, 40118, 957, 1], [66624458, 20, 40118, 957, 1], [63674861, 20, 40118, 957, 10], [65674532, 20, 40118, 929, 1], [64614401, 59, 40118, 957, 1], [64614401, 59, 40118, 957, 1], [61664713, 59, 40118, 335, 1], [61664713, 59, 40118, 335, -1], [68634752, 59, 40118, 410, 1], [60614487, 59, 40118, 878, 1], [63644339, 59, 40118, 878, 1], [60694698, 59, 40118, 620, 1], [60664659, 59, 40118, 620, 1], [62684217, 59, 40118, 957, 1], [67674686, 59, 40118, 929, 1], [61684613, 59, 40118, 335, 1], [64624770, 59, 40118, 957, 1], [69694814, 166, 40118, 360, 1], [69694814, 166, 40118, 360, 1], [69644164, 166, 40118, 193, 1]]')
			assert_p('Table', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69684558, 20, 40118, 280, 1, 5.01], [69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89], [63674861, 20, 40118, 957, 10, 2.70], [65674532, 20, 40118, 929, 1, 3.59], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [62684217, 59, 40118, 957, 1, 9.99], [67674686, 59, 40118, 929, 1, 3.99], [61684613, 59, 40118, 335, 1, 12.99], [64624770, 59, 40118, 957, 1, 2.59], [69694814, 166, 40118, 360, 1, 2.50], [69694814, 166, 40118, 360, 1, 2.50], [69644164, 166, 40118, 193, 1, 21.59]]')
			select('Table', 'cell:DEPT-NO,1(280)')
			commonBits.doSleep()


			click('Go')
			commonBits.doSleep()

		close()

		commonBits.doSleep()
		commonBits.doSleep()


		select_menu('Window>>XffDTAR020.csv>>Table: ')

		select('Table', 'cell:1|KEYCODE-NO,0(69684558)')
		assert_p('Table', 'Text', '69684558', '1|KEYCODE-NO,1')
		select('Table', 'cell:2|STORE-NO,0(20)')
		assert_p('Table', 'Text', '20', '2|STORE-NO,0')
		select('Table', 'cell:3|DATE,0(40118)')
		assert_p('Table', 'Text', '40118', '3|DATE,0')
		select('Table', 'cell:4|DEPT-NO,0(280)')
		assert_p('Table', 'Text', '280', '4|DEPT-NO,1')
		select('Table', 'cell:5|QTY-SOLD,0(1)')
		assert_p('Table', 'Text', '-1', '5|QTY-SOLD,1')
		select('Table', 'cell:1|KEYCODE-NO,2(69684558)')
		rightclick('Table', '1|KEYCODE-NO,2')
		select_menu('Edit Record')
##		select('Table1', 'cell:1|KEYCODE-NO,2(69684558)')
		select('Table', 'cell:Data,0(69684558)')
		assert_p('Table', 'Text', '20', 'Data,1')
		select('Table', 'cell:Data,0(69684558)')
##		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 69684558, 69684558], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1]]')
		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 69684558, 69684558], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 5.01, 5.01]]')
		select('Table', 'cell:Data,0(69684558)')
		click('Right')
		select('Table', 'cell:Data,0(69694158)')
##		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 69694158, 69694158], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1]]')
		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 69694158, 69694158], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 19.00, 19.00]]')
		select('Table', 'cell:Data,0(69694158)')
		click('Right')
		select('Table', 'cell:Data,0(69694158)')
##		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 69694158, 69694158], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , -1, -1]]')
		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 69694158, 69694158], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , -1, -1], [SALE-PRICE, 6, , -19.00, -19.00]]')
		select('Table', 'cell:Data,0(69694158)')
		click('Right')
		select('Table', 'cell:Data,3(280)')
		assert_p('Table', 'Text', '1', 'Data,4')
		select('Table', 'cell:Data,0(69694158)')
##		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 69694158, 69694158], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1]]')
		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 69694158, 69694158], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 5.01, 5.01]]')
		select('Table', 'cell:Data,0(69694158)')
		click('Right')
		select('Table', 'cell:Data,0(63604808)')
##		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 63604808, 63604808], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 170, 170], [QTY-SOLD, 5, , 1, 1]]')
		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, , 63604808, 63604808], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 170, 170], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 4.87, 4.87]]')
		select('Table', 'cell:Data,0(63604808)')
		select_menu('Window>>XffDTAR020.csv>>Table: ')
##		select('Table2', 'cell:Data,0(63604808)')
		select('Table', 'cell:1|KEYCODE-NO,2(69684558)')
		select('Table', 'cell:1|KEYCODE-NO,4(69694158)')
		assert_p('Table', 'RowCount', '379')
		select('Table', 'cell:1|KEYCODE-NO,8(62684671)')
		assert_p('Table', 'ColumnCount', '6')
		select('Table', 'cell:1|KEYCODE-NO,8(62684671)')
		select_menu('File>>Compare Menu')
##		select('Table', 'cell:1|KEYCODE-NO,8(62684671)')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('FileChooser1', commonBits.sampleDir() + 'SampleFiles' + commonBits.fileSep())
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'XffDTAR020.csv')
		click('Right')

		if window(''):
			select('TextField', 'x\'FF\'')
			select('CheckBox', 'true')
##			select('Table', '')
			assert_p('Table', 'Text', '20', 'STORE-NO,0')
			select('Table', 'cell:DATE,1(40118)')
###			assert_p('Table', 'Content', '[[69684558, 20, 40118, 280, 1], [69684558, 20, 40118, 280, -1], [69684558, 20, 40118, 280, 1], [69694158, 20, 40118, 280, 1], [69694158, 20, 40118, 280, -1], [69694158, 20, 40118, 280, 1], [63604808, 20, 40118, 170, 1], [62684671, 20, 40118, 685, 1], [62684671, 20, 40118, 685, -1], [64634429, 20, 40118, 957, 1], [66624458, 20, 40118, 957, 1], [63674861, 20, 40118, 957, 10], [65674532, 20, 40118, 929, 1], [64614401, 59, 40118, 957, 1], [64614401, 59, 40118, 957, 1], [61664713, 59, 40118, 335, 1], [61664713, 59, 40118, 335, -1], [68634752, 59, 40118, 410, 1], [60614487, 59, 40118, 878, 1], [63644339, 59, 40118, 878, 1], [60694698, 59, 40118, 620, 1], [60664659, 59, 40118, 620, 1], [62684217, 59, 40118, 957, 1], [67674686, 59, 40118, 929, 1], [61684613, 59, 40118, 335, 1], [64624770, 59, 40118, 957, 1], [69694814, 166, 40118, 360, 1], [69694814, 166, 40118, 360, 1], [69644164, 166, 40118, 193, 1]]')
			assert_p('Table', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69684558, 20, 40118, 280, 1, 5.01], [69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89], [63674861, 20, 40118, 957, 10, 2.70], [65674532, 20, 40118, 929, 1, 3.59], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [62684217, 59, 40118, 957, 1, 9.99], [67674686, 59, 40118, 929, 1, 3.99], [61684613, 59, 40118, 335, 1, 12.99], [64624770, 59, 40118, 957, 1, 2.59], [69694814, 166, 40118, 360, 1, 2.50], [69694814, 166, 40118, 360, 1, 2.50], [69644164, 166, 40118, 193, 1, 21.59]]')
			select('Table', 'cell:DATE,1(40118)')
			click('Go')
		close()

		select('TabbedPane', '')
		select('Table', 'cell:Equivalent Record,0(-1)')
		select('Table', 'GeneratedCsvRecord', 'Equivalent Record,0')
		select('Table', 'cell:Equivalent Record,0(0)')
		select('Table1', 'cell:Equivalent Field,5()')
		select('Table', 'cell:Equivalent Record,0(0)')
		select('Table1', 'cell:Equivalent Field,5()')
		click('MetalInternalFrameTitlePane', 379, 15)
		click('MetalInternalFrameTitlePane', 345, 11)
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('TextPane', 'Text', 'Files are Identical !!!')
	close()


