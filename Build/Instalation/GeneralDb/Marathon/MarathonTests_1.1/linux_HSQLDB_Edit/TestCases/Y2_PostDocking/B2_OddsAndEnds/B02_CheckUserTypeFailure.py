useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		click('BasicInternalFrameTitlePane$NoFocusButton')
		assert_p('TextArea', 'Text', '''

Error Loading Property: 0 tttt''')
	close()
