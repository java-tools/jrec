useFixture(default)

def test():
	from Modules import commonBits

	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Combo Lists')
		assert_p('ComboBox', 'Text', 'Standard Combo')
##		assert_p('ComboBox', 'Text', 'Key / Value Combo')
		click('Label5')
		assert_p('Label5', 'Text', 'Combo Type')
		click('Label6')
		assert_p('Label6', 'Text', 'Lines to Insert')
		click('Label2')
		assert_p('Label2', 'Text', 'System')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
