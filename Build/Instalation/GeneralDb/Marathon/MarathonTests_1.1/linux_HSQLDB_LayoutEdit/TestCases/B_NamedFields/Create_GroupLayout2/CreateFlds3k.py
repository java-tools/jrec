useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zx3xzFLDg777')
		click('Insert')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('Delete2')
		select('RecordDef.Record Type_Txt', 'Group of Records')
		select('RecordDef.System_Txt', 'Unkown')

		click('Insert')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,0()')
		select('ChildRecordsJTbl', 'zx3xzFLD1', 'Child Record,0')
		select('ChildRecordsJTbl', 'zx3xzFLD2', 'Child Record,1')
		select('ChildRecordsJTbl', 'zx3xzFLD2', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0(zx3xzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', '')
##		select('ChildRecordsJTbl', 'cell: ,0(null)')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2aaa')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2aaa], [, zx3xzFLD2aaa, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2asd')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2asd], [, zx3xzFLD2asd, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Field,1()')
		click('Delete2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Field,0()')
		click('Delete2')
		click('Delete3')

		if window('Delete: zx3xzFLDg777'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
