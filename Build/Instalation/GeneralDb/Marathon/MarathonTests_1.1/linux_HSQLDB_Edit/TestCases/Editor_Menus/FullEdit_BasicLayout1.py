useFixture(default)

def test():
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Create Layout')
		assert_p('BmKeyedComboBox', 'Text', '6')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Create Layout Wizard')
		assert_p('BmKeyedComboBox', 'Text', '0')
		##assert_p('TextField2', 'Text', 'Wizard_')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
