useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')
		select('RecordList.Description_Txt', '%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		click('Save As')

		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zx3xzFLDg1')
			select('OptionPane.textField', 'zx3xzFLDg777')
			click('OK')
		close()


		select('TabbedPane', 'Child Records')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777')
		assert_p('ChildRecordsJTbl', 'Text', '', 'Child Name,1')
##		select('ChildRecordsJTbl', 'cell: ,0(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,0')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		click('Right')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD2')
		click('Right')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD3')
		click('Save As')


		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zx3xzFLD3')
			select('OptionPane.textField', 'a777')
			click('Cancel')
		close()
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')

		click('Save As')


		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zx3xzFLD3')
			select('OptionPane.textField', 'zx3xzFLD3a777')
			click('OK')
		close()


		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD3a777')
		select('RecordFieldsJTbl', 'fld 31 777', 'FieldName,0')
		select('RecordFieldsJTbl', 'fld 32 777', 'FieldName,1')
		select('RecordFieldsJTbl', 'fld 33 777', 'FieldName,2')
		select('RecordFieldsJTbl', 'fld 34 777', 'FieldName,3')
		select('RecordFieldsJTbl', 'fld 35 7777', 'FieldName,4')
		select('RecordFieldsJTbl', 'cell:Description,2()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31 777, , 0, 0, 0, , , ], [2, 5, fld 32 777, , 0, 0, 0, , , ], [7, 9, fld 33 777, , 0, 0, 0, , , ], [18, 10, fld 34 777, , 0, 0, 0, , , ], [28, 12, fld 35 7777, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,2()')
		click('Save1')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ], [, zx3xzFLD3a777, , , , , ]]')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg7')

		select('RecordList.Description_Txt', '%')

		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')
		select('RecordList.Description_Txt', '%%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,1(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,1')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		click('Right')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		select('RecordList.Record Name_Txt', 'zx3xzFLDg777')

		select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%')

		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ], [, zx3xzFLD3a777, , , , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,2')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		click('Right')

		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD3a777')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31 777, , 0, 0, 0, , , ], [2, 5, fld 32 777, , 0, 0, 0, , , ], [7, 9, fld 33 777, , 0, 0, 0, , , ], [18, 10, fld 34 777, , 0, 0, 0, , , ], [28, 12, fld 35 7777, , 0, 0, 0, , , ]]')


		click('Delete2')

		if window('Delete: zx3xzFLD3a777'):
			click('Yes')
		close()


		select('TabbedPane1', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		click('Delete3')

		if window('Delete: zx3xzFLDg777'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
