useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_20'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR1000_Store_file_std.bin')
		commonBits.setRecordLayout(select, 'DTAR1000 VB')

		click('Edit1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
