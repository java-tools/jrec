useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777')
		click('Insert')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('Delete2')
		select('RecordDef.Record Type_Txt', 'Group of Records')

		click('Insert')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,0()')
		select('ChildRecordsJTbl', 'zxzxzFLD1', 'Child Record,0')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Child Record,1')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', '')
##		select('ChildRecordsJTbl', 'cell: ,0(null)')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2aaa')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2aaa], [, zxzxzFLD2aaa, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2asd')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2asd], [, zxzxzFLD2asd, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Field,1()')
		click('Delete2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Field,0()')
		click('Delete2')
		click('Delete3')

		if window('Delete: zxzxzFLDg777'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
