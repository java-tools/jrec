useFixture(default)

def test():
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		assert_p('Label', 'Text', 'Record Name')
		assert_p('Label1', 'Text', 'Description')
		assert_p('Label2', 'Text', 'Record Type')
		assert_p('Label3', 'Text', 'System')
		assert_p('Label5', 'Text', 'List')
		assert_p('Label6', 'Text', 'Record Name')
		assert_p('Label7', 'Text', 'Description')
		assert_p('Label8', 'Text', 'Record Type')
		assert_p('Label9', 'Text', 'System')
		assert_p('Label11', 'Text', 'List')
		assert_p('Label12', 'Text', 'Lines to Insert')
		assert_p('Save1', 'Text', 'Save')
		assert_p('Save As', 'Text', 'Save As')
		assert_p('New1', 'Text', 'New')
		assert_p('Delete3', 'Text', 'Delete')
		assert_p('Insert', 'Text', 'Insert')
		assert_p('Delete2', 'Text', 'Delete')
		assert_p('Copy2', 'Text', 'Copy')
		assert_p('Cut2', 'Text', 'Cut')
		##assert_p('PasteUp', 'Text', 'Paste')
		assert_p('Edit Child', 'Text', 'Edit Child')
	close()
