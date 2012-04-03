useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR1000_Store_file_std.bin')
		click('Edit1')
		if commonBits.isVersion80():
			select_menu('File>>Export as CSV file')
			select('ComboBox2', '"')
			select('CheckBox', 'true')
			select('CheckBox1', 'true')
		elif commonBits.isVersion80():
			select_menu('File>>Export as CSV file')
			select('ComboBox2', '"')
			select_menu('File>>Save as CSV file')
			select('ComboBox2', '"')
			select('CheckBox1', 'true')
			select('CheckBox2', 'true')
		else:
			select_menu('File>>Save as CSV file')
			select('ComboBox2', '"')
			select('CheckBox', 'true')
			select('CheckBox1', 'true')
		select('FileChooser', commonBits.sampleDir() + 'csv_DTAR1000_Store_file_std.bin.csv')
		
		click('save file')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.selectOldFilemenu(select_menu, 'Edit', 'Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'csv_DTAR1000_Store_file_std.bin.csv')
		select('ComboBox1', 'CSV')
		select('ComboBox2', 'Generic CSV - enter details')
		click('Right')

		if window(''):
			click('Go')
		close()

		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'DTAR1000_Store_file_std.bin')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:Equivalent Record,0( )')
		select('Table', 'DTAR1000 VB', 'Equivalent Record,0')
		select('Table', 'cell:Equivalent Record,0(DTAR1000 VB)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('TextPane', 'Text', 'Files are Identical !!!')
	close()
