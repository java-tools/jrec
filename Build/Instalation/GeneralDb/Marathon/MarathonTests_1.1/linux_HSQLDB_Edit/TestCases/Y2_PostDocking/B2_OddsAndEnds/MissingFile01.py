useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
##		click('Edit1')
##		assert_p('TextArea', 'Text', r'invalid filename (* present) ~ C:\Users\BruceTst/RecordEditor_HSQL\SampleFiles/*')

		select('File_Txt', commonBits.sampleDir() + '*')
		click('Edit1')
		assert_p('TextArea', 'Text', 'invalid filename (* present) ~ ' + commonBits.sampleDir() + '*')
		assert_p('TextArea', 'Text', 'invalid filename (* present) ~ ' + commonBits.sampleDir() + '*')

		select('File_Txt', '')
		click('Edit1')
		assert_p('TextArea', 'Text', 'Please Enter a file name')
		select_menu('Utilities>>Cobol Copybook Analysis')
		select('Copybook_Txt', '')
		click('Display')
		assert_p('TextField', 'Text', 'You must enter a Cobol Copybook name')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
