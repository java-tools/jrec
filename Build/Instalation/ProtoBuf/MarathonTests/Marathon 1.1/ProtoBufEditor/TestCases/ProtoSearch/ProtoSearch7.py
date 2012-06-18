useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('FileChooser',  commonBits.sampleDir() + 'Ams_LocDownload_20041228.bin')
		click('Proto Search')
		assert_p('Table1', 'Content', r'[[' + commonBits.stdCopybookDir() + 'Ams_Location.proto, Ams_Location.proto, Locations], [' + commonBits.stdCopybookDir() + 'Ams_Location.protocomp, Ams_Location.proto, Locations]]')
		assert_p('TextField', 'Text', commonBits.sampleDir() + 'Ams_LocDownload_20041228.bin')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ComboBox2', 'Text', 'Ams_Location.proto')
		assert_p('ComboBox2', 'Content', '[[Ams_Location.proto]]')
		assert_p('ComboBox3', 'Text', 'Locations')
		assert_p('ComboBox3', 'Content', '[[Locations]]')
		assert_p('ComboBox1', 'Text', 'Compiled Proto')
		assert_p('ComboBox', 'Text', 'Delimited Messages')
	close()
