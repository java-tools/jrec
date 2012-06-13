useFixture(default)

def test():
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu('Record Layouts>>Load Cobol Copybook')
		assert_p('Label', 'Text', 'Cobol Copybook')
		assert_p('Label1', 'Text', 'Split Copybook')
		assert_p('Label2', 'Text', 'Font Name')
		assert_p('Label3', 'Text', 'Binary Format')
		assert_p('Label4', 'Text', 'File Structure')
		assert_p('SplitCombo', 'Content', '[[No Split, On Redefine, On 01 level]]')
	close()
