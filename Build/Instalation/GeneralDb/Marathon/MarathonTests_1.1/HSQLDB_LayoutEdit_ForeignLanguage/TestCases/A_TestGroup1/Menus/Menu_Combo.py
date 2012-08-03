useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Combo Lists'))
		if commonBits.isMsAccess():
			assert_p('ComboBox', 'Text', commonBits.fl('Key / Value Combo'))
		else:
			assert_p('ComboBox', 'Text', commonBits.fl('Standard Combo'))
		click('Label5')
		assert_p('Label5', 'Text', commonBits.fl('Combo Type'))
		click('Label6')
		assert_p('Label6', 'Text', commonBits.fl('Lines to Insert'))
		click('Label2')
		assert_p('Label2', 'Text', commonBits.fl('System'))
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
