useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		click(commonBits.fl('Edit') + '1')
#		click('Export')
#		select('TabbedPane', 'Script')
#		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('File') + '>>' + commonBits.fl('Export via Script') + '>>Hello.js')
		click(commonBits.fl('Save File'))

		if window('Message'):
			click('OK')
		close()

		select_menu(commonBits.fl('Utilities') + '>>' + commonBits.fl('Run Script') + '>>Hello.js')

		if window('Message'):
			click('OK')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
