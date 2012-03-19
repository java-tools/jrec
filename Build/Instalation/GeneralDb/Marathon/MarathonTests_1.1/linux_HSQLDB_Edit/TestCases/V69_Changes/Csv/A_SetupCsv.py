useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		click('Edit1')
		select_menu('File>>Save as CSV file')
		select('FileChooser', commonBits.sampleDir() + 'csv_DTAR020.bin.csv')
		if commonBits.isVersion89():
			select('CheckBox1', 'true')
		else:
			select('CheckBox', 'true')
		select('ComboBox1', ',')
		select('FileChooser', commonBits.sampleDir() + 'csvB_DTAR020.bin.csv')
		
		click('save file')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('File>>Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'csvB_DTAR020.bin.csv')
		select('ComboBox2', 'Generic CSV - enter details')
		click('Right')

		if window(''):
			click('Go')
		close()

		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:Equivalent Record,0( )')
		select('Table', 'DTAR020', 'Equivalent Record,0')
		select('Table', 'cell:Equivalent Record,0(DTAR020)')
		assert_p('Table1', 'Content', '[[KEYCODE-NO, KEYCODE-NO], [STORE-NO, STORE-NO], [DATE, DATE], [DEPT-NO, DEPT-NO], [QTY-SOLD, QTY-SOLD], [SALE-PRICE, SALE-PRICE]]')
		select('Table', 'cell:Equivalent Record,0(DTAR020)')
		assert_p('Table', 'Content', '[[GeneratedCsvRecord, DTAR020]]')
		select('Table', 'cell:Equivalent Record,0(DTAR020)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('TextPane', 'Text', 'Files are Identical !!!')
	close()
