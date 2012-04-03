useFixture(default)

def test():
	from Modules import commonBits

	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Create Layout')
#		assert_p('BmKeyedComboBox', 'Text', '6')
		if commonBits.isVersion80():
			assert_p('BmKeyedComboBox', 'Text', 'Record Layout')
		else:
			assert_p('BmKeyedComboBox', 'Text', 'XML')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Layout Wizard')
#		assert_p('BmKeyedComboBox', 'Text', '0')
		assert_p('BmKeyedComboBox', 'Text', 'Default')
		##assert_p('TextField2', 'Text', 'Wizard_')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
