useFixture(default)

def test():
	import time
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')

		select('RecordList.Description_Txt', '%')

##		select('ChildRecordsJTbl', 'cell: ,0(sl)')
		rightclick('ChildRecordsJTbl', 'Child Name,0')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		click('Save As')


		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zx3xzFLD1')
			select('OptionPane.textField', 'zx3xzFLD1aaa')
			click('OK')
		close()

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 13aa', 'FieldName,2')
		select('RecordFieldsJTbl', 'fld 12aa', 'FieldName,1')
		select('RecordFieldsJTbl', 'fld 11aa', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aa, , 0, 0, 0, , , ], [2, 5, fld 12aa, , 0, 0, 0, , , ], [7, 9, fld 13aa, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD1aaa')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ], [, zx3xzFLD1aaa, , , , , ]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ], [, zx3xzFLD1aaa, , , , , ]]')



		select('ChildRecordsJTbl', 'zx3xzFLD1aaa', 'Tree Parent,2')
		select('ChildRecordsJTbl', 'zx3xzFLD2', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0(zx3xzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , zx3xzFLD1aaa], [, zx3xzFLD1aaa, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,3(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,3')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aa, , 0, 0, 0, , , ], [2, 5, fld 12aa, , 0, 0, 0, , , ], [7, 9, fld 13aa, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD1aaa')
		click('Delete2')

		if window('Delete: zx3xzFLD1aaa'):
			click('Yes')
		close()

		select('TabbedPane1', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')

		time.sleep(0.7)
		select('ChildRecordsJTbl', '', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Field,3()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')

##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
