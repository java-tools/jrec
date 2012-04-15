useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt',commonBits.sampleDir() + 'wx2File.txt')
		select('Record Layout_Txt', 'wx2File')
		click('Edit1')
		select_menu('File>>Save As')
		select('File Name', 'wx3File.txt')
		click('Save1')
		click('SaveAs')
		select('File Name', 'wx4File.txt')
		click('Save1')
		click('SaveAs')
		select('File Name', 'wx5File.txt')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
