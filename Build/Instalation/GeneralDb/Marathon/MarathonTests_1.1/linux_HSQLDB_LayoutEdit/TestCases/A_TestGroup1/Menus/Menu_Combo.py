useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Edit Combo Lists')
		if commonBits.isMsAccess():
			assert_p('ComboBox', 'Text', 'Key / Value Combo')
		else:
			assert_p('ComboBox', 'Text', 'Standard Combo')
		click('Label5')
		assert_p('Label5', 'Text', 'Combo Type')
		click('Label6')
		assert_p('Label6', 'Text', 'Lines to Insert')
		click('Label2')
		assert_p('Label2', 'Text', 'System')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
