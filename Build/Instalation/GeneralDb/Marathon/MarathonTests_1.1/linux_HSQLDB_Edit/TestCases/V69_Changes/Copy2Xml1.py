useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu('File>>File Copy Menu')
		click('*4')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('FileChooser1', commonBits.sampleDir() + 'xmlCopyDTAR020.xml')
		select('ComboBox1', 'Mainframe')
		
		click('Right')
		select('TabbedPane', '')
		assert_p('Table1', 'Content', '[[KEYCODE-NO, true], [STORE-NO, true], [DATE, true], [DEPT-NO, true], [QTY-SOLD, true], [SALE-PRICE, true]]')
		assert_p('Table', 'Content', '[[DTAR020, true]]')
		click('Right')
		select('TabbedPane', '')
		click('Copy2')

		assert_p('TextField1', 'Text', 'Copy Done !!! ')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('File>>Compare Menu')
		click('*1')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>Menu>>Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('ComboBox1', 'Mainframe')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'xmlCopyDTAR020.xml')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:Equivalent Record,0(2)')
		select('Table', 'cell:Equivalent Record,0(2)')
		select('Table1', 'cell:Field,2(DATE)')
		assert_p('Table1', 'Content', '[[KEYCODE-NO, KEYCODE-NO], [STORE-NO, STORE-NO], [DATE, DATE], [DEPT-NO, DEPT-NO], [QTY-SOLD, QTY-SOLD], [SALE-PRICE, SALE-PRICE]]')
		select('Table1', 'cell:Field,2(DATE)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
#		click('BaseHelpPanel', 344, 61)
		assert_p('BaseHelpPanel', 'Enabled', 'true')
#		click('BaseHelpPanel', 1212, 97)
		assert_p('TextPane', 'Text', 'Files are Identical !!!')
#commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
