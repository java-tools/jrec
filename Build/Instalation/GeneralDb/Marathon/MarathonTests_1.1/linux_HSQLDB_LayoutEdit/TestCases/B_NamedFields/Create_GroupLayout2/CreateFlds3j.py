useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')

		select('RecordList.Description_Txt', '%')


		select('ChildRecordsJTbl', 'cell:Tree Parent,0()')
		select('ChildRecordsJTbl', 'zx3xzFLD2', 'Tree Parent,0')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')


		select('ChildRecordsJTbl', 'cell:Tree Parent,0()')
		select('ChildRecordsJTbl', 'zx3xzFLD3', 'Tree Parent,0')

##		select('ChildRecordsJTbl', 'zx3xzFLD3', 'Tree Parent,0')
##		select('ChildRecordsJTbl', '')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,2')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD3')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD3aaaa')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD3aaaa], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3aaaa, , , , , ]]')
##		select('ChildRecordsJTbl', 'rows:[0,1,2],columns:[Field]')

		rightclick('ChildRecordsJTbl', 'Child Name,2')
		select_menu('Edit Child Record')

		select('RecordDef.Record Name_Txt', 'zx3xzFLD3bbb')

		
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD3bbb], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3bbb, , , , , ]]')
##		select('ChildRecordsJTbl', '')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,2')
		select_menu('Edit Child Record')
		select('RecordDef.Record Name_Txt', 'zx3xzFLD3')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD3], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', '', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0()')
		click('Save1')
##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
