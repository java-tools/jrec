useFixture(default)

def test():
	import time
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		select('RecordList.Description_Txt', '%')

##		select('ChildRecordsJTbl', 'cell: ,0(sl)')
		rightclick('ChildRecordsJTbl', 'Child Name,0')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		click('Save As')


		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zxzxzFLD1')
			select('OptionPane.textField', 'zxzxzFLD1aaa')
			click('OK')
		close()

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 13aa', 'FieldName,2')
		select('RecordFieldsJTbl', 'fld 12aa', 'FieldName,1')
		select('RecordFieldsJTbl', 'fld 11aa', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aa, , 0, 0, 0, , , ], [2, 5, fld 12aa, , 0, 0, 0, , , ], [7, 9, fld 13aa, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1aaa')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ], [, zxzxzFLD1aaa, , , , , ]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ], [, zxzxzFLD1aaa, , , , , ]]')



		select('ChildRecordsJTbl', 'zxzxzFLD1aaa', 'Tree Parent,2')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , zxzxzFLD1aaa], [, zxzxzFLD1aaa, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,3(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,3')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aa, , 0, 0, 0, , , ], [2, 5, fld 12aa, , 0, 0, 0, , , ], [7, 9, fld 13aa, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1aaa')
		click('Delete2')

		if window('Delete: zxzxzFLD1aaa'):
			click('Yes')
		close()

		select('TabbedPane1', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')

		time.sleep(0.7)
		select('ChildRecordsJTbl', '', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Field,3()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')

##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
