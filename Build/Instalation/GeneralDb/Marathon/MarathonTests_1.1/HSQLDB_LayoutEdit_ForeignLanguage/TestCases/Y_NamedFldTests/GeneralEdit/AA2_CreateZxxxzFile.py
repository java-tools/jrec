useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'DTAR020_tst1.bin')
		click(commonBits.fl('Edit') + '1')
		click('SaveAs')
		select('File Name', 'zxxzDTAR020_tst1.bin')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
