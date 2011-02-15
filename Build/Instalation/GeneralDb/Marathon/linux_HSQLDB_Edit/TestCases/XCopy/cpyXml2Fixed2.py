useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		select_menu('File>>File Copy Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'xmlModDTAR020.bin.xml')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020_fromXmlMod.bin')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:Record,2(DTAR020)')
		#click('ScrollPane$ScrollBar2', 7, 67)
		select('Table1', 'cell:Equivalent Field,3()')
		select('Table', 'cell:Record,2(DTAR020)')
		select('Table1', 'KEYCODE-NO', 'Equivalent Field,3')
		select('Table1', 'STORE-NO', 'Equivalent Field,4')
		select('Table1', 'DATE', 'Equivalent Field,5')
		select('Table1', 'DEPT-NO', 'Equivalent Field,6')
		select('Table1', 'QTY-SOLD', 'Equivalent Field,7')
		select('Table1', 'SALE-PRICE', 'Equivalent Field,8')
		select('Table1', 'QTY-SOLD', 'Equivalent Field,7')
		select('Table1', 'cell:Equivalent Field,8(SALE-PRICE)')
		click('Right')
		select('TabbedPane', '')
		click('Copy2')
		assert_p('TextField1', 'Text', 'Copy Done !!! ')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

### ---------------------------------------------------------------
### ---  Edit
### ---------------------------------------------------------------

		click('Open')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020_fromXmlMod.bin')
		click('Edit1')
		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11,12],columns:[1 - 8|KEYCODE-NO]')
		select_menu('View>>Table View #{Selected Records#}')
###		select('Table2', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11,12],columns:[1 - 8|KEYCODE-NO]')
		select('Table', 'cell:9 - 2|STORE-NO,4(20)')
		assert_p('Table', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69684558, 20, 40118, 280, 1, 5.01], [69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89], [63674861, 20, 40118, 957, 10, 2.70], [65674532, 20, 40118, 929, 1, 3.59]]')
		select('Table', 'cell:9 - 2|STORE-NO,4(20)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11,12],columns:[1 - 8|KEYCODE-NO]')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8,9,10,11,12],columns:[1 - 8|KEYCODE-NO]')
		select_menu('Window>>DTAR020_fromXmlMod.bin>>Table: ')
		select('Table', 'rows:[14,15,16,17,18,19,20,21,22,23,24,25,26,27],columns:[1 - 8|KEYCODE-NO]')
		select_menu('View>>Table View #{Selected Records#}')
###		select('Table2', 'rows:[14,15,16,17,18,19,20,21,22,23,24,25,26,27],columns:[1 - 8|KEYCODE-NO]')
		select('Table', 'cell:9 - 2|STORE-NO,3(59)')
		assert_p('Table', 'Content', '[[64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [62684217, 59, 40118, 957, 1, 9.99], [67674686, 59, 40118, 929, 1, 3.99], [61684613, 59, 40118, 335, 1, 12.99], [64624770, 59, 40118, 957, 1, 2.59], [69694814, 166, 40118, 360, 1, 2.50], [69694814, 166, 40118, 360, 1, 2.50]]')
		select('Table', 'cell:9 - 2|STORE-NO,3(59)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[14,15,16,17,18,19,20,21,22,23,24,25,26,27],columns:[1 - 8|KEYCODE-NO]')
##		select('Table', 'rows:[14,15,16,17,18,19,20,21,22,23,24,25,26,27],columns:[1 - 8|KEYCODE-NO]')
		select_menu('Window>>DTAR020_fromXmlMod.bin>>Table: ')
		select('Table', 'cell:1 - 8|KEYCODE-NO,35(63634260)')
		rightclick('Table', '1 - 8|KEYCODE-NO,35')
		select_menu('Edit Record')
###		select('Table1', 'cell:1 - 8|KEYCODE-NO,35(63634260)')
		select('Table', 'cell:Data,0(63634260)')
		assert_p('Table', 'Content', '''[[KEYCODE-NO, 1, 8, 63634260, 63634260, f6f3f6f3f4f2f6f0], [STORE-NO, 9, 2, 166, %, 166c], [DATE, 11, 4, 40118,   �, 0040118c], [DEPT-NO, 15, 2, 320, , 320c], [QTY-SOLD, 17, 5, 1,     , 000000001c], [SALE-PRICE, 22, 6, 5.59,     ��, 00000000559c]]''')
		select('Table', 'cell:Data,0(63634260)')
		click('Right')
		select('Table', 'cell:Data,0(64684534)')
		assert_p('Table', 'Content', '''[[KEYCODE-NO, 1, 8, 64684534, 64684534, f6f4f6f8f4f5f3f4], [STORE-NO, 9, 2, 166, %, 166c], [DATE, 11, 4, 40118,   �, 0040118c], [DEPT-NO, 15, 2, 440, �, 440c], [QTY-SOLD, 17, 5, 1,     , 000000001c], [SALE-PRICE, 22, 6, 14.99,    ��, 00000001499c]]''')
		select('Table', 'cell:Data,0(64684534)')
		click('Right')
		select('Table', 'cell:Data,0(64674965)')
##		assert_p('Table', 'Content', '[[KEYCODE-NO, 1, 8, 64674965, 64674965, f6f4f6f7f4f9f6f5], [STORE-NO, 9, 2, 166, %, 166c], [DATE, 11, 4, 40118,   �, 0040118c], [DEPT-NO, 15, 2, 235, ?*, 235c], [QTY-SOLD, 17, 5, 1,     , 000000001c], [SALE-PRICE, 22, 6, 19.99,    r�, 00000001999c]]')
		select('Table', 'cell:Data,0(64674965)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select('Table', 'cell:1 - 8|KEYCODE-NO,35(63634260)')
		select('Table', 'cell:1 - 8|KEYCODE-NO,35(63634260)')
		select_menu('Window>>DTAR020_fromXmlMod.bin>>Table: ')
		select('Table', 'cell:1 - 8|KEYCODE-NO,35(63634260)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

### ------------------------------------------------------------------
### ---  File Compare
### ------------------------------------------------------------------
		select_menu('File>>Compare Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('FileChooser1', commonBits.sampleDir() + 'DTAR020_fromXmlMod.bin')
		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('TextPane', 'Text', 'Files are Identical !!!')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

#		select_menu('Window>>Single Layout Compare')
#		click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select_menu('Window>>Menu>>Copy Menu')
#		click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select_menu('Window>>Menu>>Compare Menu')
#		click('BasicInternalFrameTitlePane$NoFocusButton2')

	close()
