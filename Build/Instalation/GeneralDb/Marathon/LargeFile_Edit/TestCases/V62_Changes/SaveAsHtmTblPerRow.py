useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')

		click('Edit1')
		select('Table', 'rows:[0,1,2,3,4,5,6,7,8],columns:[4 - 4|Loc Nbr]')
		select_menu('File>>Save HTML 1 tbl per Row')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8],columns:[4 - 4|Loc Nbr]')
		select('FileChooser', commonBits.sampleDir() +  'Ams_LocDownload_20041228.htm')
		assert_p('ComboBox1', 'Text', 'HTML: 1 Row per Table')
		assert_p('CheckBox', 'Text', 'true')
		assert_p('BaseHelpPanel', 'Enabled', 'true')
		assert_p('CheckBox', 'Text', 'true')
		assert_p('CheckBox', 'Enabled', 'true')
		assert_p('CheckBox1', 'Text', 'true')
		assert_p('ComboBox2', 'Enabled', 'false')
		click('save file')
##		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Table', 'rows:[0,1,2,3,4,5,6,7,8],columns:[4 - 4|Loc Nbr]')
	close()
