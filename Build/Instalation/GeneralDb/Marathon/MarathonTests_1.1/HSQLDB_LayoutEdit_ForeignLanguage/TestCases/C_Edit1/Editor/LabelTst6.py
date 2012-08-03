useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		commonBits.selectOldFilemenu(select_menu, 'Utilities', 'Cobol Copybook Analysis')
		assert_p('Label', 'Text',  commonBits.fl('Copybook'))
		assert_p('Label1', 'Text',  commonBits.fl('Cobol Dialect'))
		assert_p('Label2', 'Text',  commonBits.fl('Include Comments'))
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit System Table'))
		assert_p('TextField', 'Text', '3')
		assert_p('Label1', 'Text',  commonBits.fl('Table Id'))
		assert_p('Label2', 'Text',  commonBits.fl('Table Name'))
		assert_p('Label3', 'Text',  commonBits.fl('Description'))
		assert_p('Label4', 'Text',  commonBits.fl('Lines to Insert'))
		assert_p( commonBits.fl('Insert'), 'Text',  commonBits.fl('Insert'))

		if commonBits.isTstLanguage():
			assert_p(commonBits.fl('Delete'), 'Text',  commonBits.fl('Delete'))
			assert_p(commonBits.fl('Copy'), 'Text', commonBits.fl('Copy'))
		else:
			assert_p('Delete2', 'Text',  commonBits.fl('Delete'))
			assert_p('Copy2', 'Text', 'Copy')
	close()
