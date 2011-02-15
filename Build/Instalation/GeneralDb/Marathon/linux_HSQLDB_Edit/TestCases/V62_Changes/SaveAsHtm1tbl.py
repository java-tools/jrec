useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')

		click('Edit1')
		select('Table', 'rows:[0,1,2,3,4,5,6],columns:[4 - 4|Loc Nbr]')
		select_menu('File>>Save HTML 1 tbl')
##		select('Table', 'rows:[0,1,2,3,4,5,6],columns:[4 - 4|Loc Nbr]')
		assert_p('ComboBox1', 'Text', 'HTML: 1 Table')
##		assert_p('ComboBox1', 'Content', '[[Data, HTML: 1 Table, HTML: 1 Row per Table, Delimited]]')
##		assert_p('ComboBox1', 'Content', '[[Data, HTML: 1 Table, HTML: 1 Row per Table, Delimited, XML]]')
		assert_p('ComboBox1', 'Content', '[[Data, HTML: 1 Table, HTML: 1 Row per Table, HTML: Tree, Delimited, XML, Velocity]]')
		assert_p('CheckBox1', 'Text', 'true')
		assert_p('CheckBox', 'Enabled', 'false')
##		click('ComboPopup.popup', 116, 37)
		select('FileChooser', r'e:\Work\temp\Ams_LocDownload_20041228.htm')
		click('save file')
	close()
