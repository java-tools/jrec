useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR1000_Store_file_std.bin')
		click(commonBits.fl('Edit') + '1')
		if commonBits.isVersion80():
			select_menu(commonBits.fl('File') + '>>' + commonBits.fl('Export as CSV file'))
			select('QuoteCombo', '"')
			select('CheckBox', 'true')
			select('CheckBox1', 'true')
		elif commonBits.isVersion80():
			select_menu(commonBits.fl('File') + '>>' + commonBits.fl('Export as CSV file'))
			select('ComboBox2', '"')
			select_menu('File>>Save as CSV file')
			select('QuoteCombo', '"')
			select('CheckBox1', 'true')
			select('CheckBox2', 'true')
		else:
			select_menu('File>>Save as CSV file')
			select('ComboBox2', '"')
			select('CheckBox', 'true')
			select('CheckBox1', 'true')
		select('FileChooser', commonBits.sampleDir() + 'csv_DTAR1000_Store_file_std.bin.csv')
		
		click(commonBits.fl('Save File'))
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.selectOldFilemenu(select_menu, 'Utilities', 'Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'csv_DTAR1000_Store_file_std.bin.csv')
		select('ComboBox1', 'CSV')
		select('ComboBox2', 'Generic CSV - enter details')
		click('Right')

		if window(''):
			click(commonBits.fl('Go'))
		close()

		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'DTAR1000_Store_file_std.bin')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:' + commonBits.fl('Equivalent Record') + ',0( )')
		select('Table', 'DTAR1000 VB', commonBits.fl('Equivalent Record') + ',0')
		select('Table', 'cell:' + commonBits.fl('Equivalent Record') + ',0(DTAR1000 VB)')
		click('Right')
		select('TabbedPane', '')
		click(commonBits.fl('Compare'))
		assert_p('TextPane', 'Text', 'Files are Identical !!!')
	close()
