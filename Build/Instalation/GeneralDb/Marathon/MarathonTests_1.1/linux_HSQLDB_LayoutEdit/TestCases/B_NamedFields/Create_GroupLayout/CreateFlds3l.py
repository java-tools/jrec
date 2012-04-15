useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		select('ChildRecordsJTbl', 'cell:Tree Parent,0()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Tree Parent,2')
		select('ChildRecordsJTbl', 'cell:Tree Parent,2(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , zxzxzFLD2]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2aaa')
		click('Save As')


		if window('Input'):
			click('Cancel')
		close()

		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2aaa], [, zxzxzFLD2aaa, , , , , ], [, zxzxzFLD3, , , , , zxzxzFLD2aaa]]')
		select('ChildRecordsJTbl', 'cell:Tree Parent,2(zxzxzFLD2)')
		select('ChildRecordsJTbl', '', 'Tree Parent,2')
##		select('ChildRecordsJTbl', '', 'Tree Parent,2')
		select('ChildRecordsJTbl', 'cell:Tree Parent,2()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2aaa], [, zxzxzFLD2aaa, , , , , ], [, zxzxzFLD3, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zxzxzFLD2')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', '', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0()')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
