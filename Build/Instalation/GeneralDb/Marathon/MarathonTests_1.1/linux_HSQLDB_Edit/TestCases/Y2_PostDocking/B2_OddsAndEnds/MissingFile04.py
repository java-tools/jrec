useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Utilities>>File Copy Menu')
		click('*')
		click('Run Copy')
		assert_p('TextArea', 'Text', 'Error Loading Record Layout')
		select('New File_Txt', '')
		click('Run Copy')
		assert_p('TextArea', 'Text', 'You must Enter a filename')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Utilities>>Compare Menu')
		click('*')
		click('Run Compare')
		assert_p('TextArea', 'Text', 'Error Loading Record Layout')
		select('New File_Txt', '')
		click('Run Compare')
		assert_p('TextArea', 'Text', 'You must Enter a filename')
	close()
