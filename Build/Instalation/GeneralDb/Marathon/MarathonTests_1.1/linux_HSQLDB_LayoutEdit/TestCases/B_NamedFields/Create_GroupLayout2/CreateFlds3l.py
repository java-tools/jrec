useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')

		select('ChildRecordsJTbl', 'cell:Tree Parent,0()')
		select('ChildRecordsJTbl', 'zx3xzFLD2', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'zx3xzFLD2', 'Tree Parent,2')
		select('ChildRecordsJTbl', 'cell:Tree Parent,2(zx3xzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , zx3xzFLD2]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2aaa')
		click('Save As')


		if window('Input'):
			click('Cancel')
		close()

		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2aaa], [, zx3xzFLD2aaa, , , , , ], [, zx3xzFLD3, , , , , zx3xzFLD2aaa]]')
		select('ChildRecordsJTbl', 'cell:Tree Parent,2(zx3xzFLD2)')
		select('ChildRecordsJTbl', '', 'Tree Parent,2')
##		select('ChildRecordsJTbl', '', 'Tree Parent,2')
		select('ChildRecordsJTbl', 'cell:Tree Parent,2()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2aaa], [, zx3xzFLD2aaa, , , , , ], [, zx3xzFLD3, , , , , ]]')
##		select('ChildRecordsJTbl', '')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD2')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', '', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0()')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
