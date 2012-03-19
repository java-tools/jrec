useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		click('Edit1')
		select('Table', 'rows:[1,2,3,4,5,6,7,8,9],columns:[10 - 35|Loc Name]')
		select_menu('File>>Save HTML 1 tbl per Row')
		select('Table2', 'rows:[1,2,3,4,5,6,7,8,9],columns:[10 - 35|Loc Name]')
		assert_p('FileChooser', 'Text', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt.html')
		assert_p('TabbedPane', 'Text', 'Html')
		assert_p('TabbedPane', 'Enabled', 'true')
		select('CheckBox5', 'false')
		assert_p('Table per Row', 'Text', 'true')
		assert_p('Tree Table', 'Text', 'false')
		assert_p('CheckBox4', 'Enabled', 'true')
		assert_p('CheckBox4', 'Text', 'true')
	close()
