useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		assert_p('Label', 'Text', commonBits.fl('Record Name'))
		assert_p('Label1', 'Text', commonBits.fl('Description'))
		assert_p('Label2', 'Text', commonBits.fl('Record Type'))
		assert_p('Label3', 'Text', commonBits.fl('System'))
		assert_p('Label5', 'Text', commonBits.fl('List'))
		assert_p('Label6', 'Text', commonBits.fl('Record Name'))
		assert_p('Label7', 'Text', commonBits.fl('Description'))
		assert_p('Label8', 'Text', commonBits.fl('Record Type'))
		assert_p('Label9', 'Text', commonBits.fl('System'))
		assert_p('Label11', 'Text', commonBits.fl('List'))
		assert_p('Label12', 'Text', commonBits.fl('Lines to Insert'))
		if commonBits.isTstLanguage():
			assert_p(commonBits.fl('Save'), 'Text', commonBits.fl('Save'))
			assert_p(commonBits.fl('New'), 'Text', commonBits.fl('New'))
			assert_p(commonBits.fl('Delete'), 'Text', commonBits.fl('Delete'))
			assert_p(commonBits.fl('Delete'), 'Text', commonBits.fl('Delete'))
			assert_p(commonBits.fl('Copy'), 'Text', commonBits.fl('Copy'))
			assert_p(commonBits.fl('Cut'), 'Text', commonBits.fl('Cut'))
		else:
			assert_p('Save1', 'Text', commonBits.fl('Save'))
			assert_p('New2', 'Text', commonBits.fl('New'))
			assert_p('Delete3', 'Text', 'Delete')
			assert_p('Delete2', 'Text', 'Delete')
			assert_p('Copy2', 'Text', 'Copy')
			assert_p('Cut2', 'Text', 'Cut')
		assert_p( commonBits.fl('Save As'), 'Text', commonBits.fl('Save As'))
		assert_p(commonBits.fl('Insert'), 'Text', commonBits.fl('Insert'))
		##assert_p('PasteUp', 'Text', 'Paste')
		assert_p( commonBits.fl('Edit Child'), 'Text',  commonBits.fl('Edit Child'))
	close()
