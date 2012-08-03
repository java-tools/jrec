useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Load Cobol Copybook'))
		assert_p('Label', 'Text', commonBits.fl('Cobol Copybook'))
		assert_p('Label1', 'Text', commonBits.fl('Split Copybook'))
		assert_p('Label2', 'Text', commonBits.fl('Font Name'))
		assert_p('Label3', 'Text', commonBits.fl('Binary Format'))
		assert_p('Label4', 'Text', commonBits.fl('File Structure'))
		assert_p('SplitCombo', 'Content', '[[' + commonBits.fl('No Split') + ', ' + commonBits.fl('On Redefine') + ', ' + commonBits.fl('On 01 level') + ']]')
	close()
