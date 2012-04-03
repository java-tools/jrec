useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		commonBits.selectOldFilemenu(select_menu, 'Data', 'Cobol Copybook Analysis')
		assert_p('Label', 'Text', 'Copybook')
		assert_p('Label1', 'Text', 'Cobol Dialect')
		assert_p('Label2', 'Text', 'Include Comments')
		select_menu('Record Layouts>>Edit System Table')
		assert_p('TextField', 'Text', '3')
		assert_p('Label1', 'Text', 'Table Id')
		assert_p('Label2', 'Text', 'Table Name')
		assert_p('Label3', 'Text', 'Description')
		assert_p('Label4', 'Text', 'Lines to Insert')
		assert_p('Insert', 'Text', 'Insert')
		assert_p('Delete2', 'Text', 'Delete')
		assert_p('Copy2', 'Text', 'Copy')
	close()
