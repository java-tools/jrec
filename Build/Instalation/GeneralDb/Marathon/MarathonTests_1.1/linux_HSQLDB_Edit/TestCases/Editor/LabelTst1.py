useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		assert_p('Label', 'Text', commonBits.fl('File'))
		assert_p('Label1', 'Text', commonBits.fl('Data Base'))
		assert_p('Label2', 'Text', commonBits.fl('System'))
		assert_p('Label3', 'Text', commonBits.fl('Record Layout'))
		assert_p('Label4', 'Text', commonBits.fl('Description'))
		assert_p(commonBits.fl('Choose File'), 'Text', commonBits.fl('Choose File'))
		assert_p(commonBits.fl('Reload from DB'), 'Text', commonBits.fl('Reload from DB'))
		assert_p(commonBits.fl('Layout Wizard'), 'Text', commonBits.fl('Layout Wizard'))
##		assert_p('Create Layout', 'Text', 'Create Layout')
		if commonBits.isTstLanguage():
			assert_p(commonBits.fl('Edit'), 'Text', commonBits.fl('Edit'))
		else:
			assert_p('Edit1', 'Text', commonBits.fl('Edit'))
		assert_p(commonBits.fl('Browse'), 'Text', commonBits.fl('Browse'))
	close()
