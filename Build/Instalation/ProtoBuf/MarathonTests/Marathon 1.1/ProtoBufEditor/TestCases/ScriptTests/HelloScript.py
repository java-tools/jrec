useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3xxx_Compare.bin')
		click('Edit1')
		select_menu('File>>Export via Script>>Hello.js')
		##assert_p('FileChooser1', 'Text', commonBits.paramDir() + r'User/ExportScripts/Hello.js')
		assert_p('TabbedPane', 'Text', 'Script')
		assert_p('TabbedPane', 'Background', '[r=184,g=207,b=229]')
		click('save file')

		if window('Message'):
			assert_p('OK', 'Text', 'OK')
			assert_p('OptionPane.label', 'Text', 'Hello, world!')
			click('OK')
		close()

		select_menu('Utilities>>Run Script>>Hello.js')

		if window('Message'):
			assert_p('OK', 'Text', 'OK')
			assert_p('OptionPane.label', 'Text', 'Hello, world!')
			click('OK')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
