useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'DTAR020_tst1.bin')
		click('Edit1')
		click('Export')
		select('File Name_Txt', '')
		click('Save File')
		assert_p('TextArea3', 'Text', 'Please Enter a file name')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
