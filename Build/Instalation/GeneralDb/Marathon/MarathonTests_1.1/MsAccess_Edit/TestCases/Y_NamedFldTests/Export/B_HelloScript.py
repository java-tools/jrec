useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		click('Edit1')
#		click('Export')
#		select('TabbedPane', 'Script')
#		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('File>>Export via Script>>Hello.js')
		click('save file')

		if window('Message'):
			click('OK')
		close()

		select_menu('Utilities>>Run Script>>Hello.js')

		if window('Message'):
			click('OK')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
