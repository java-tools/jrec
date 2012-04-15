useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%')

		select('TabbedPane', 'Child Records')
		select('ChildRecordsJTbl', 'cell: ,0(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zxzxzFLD1aaa')
			click('OK')
		close()


		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1aaa')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 11aaa', 'FieldName,0')
		select('RecordFieldsJTbl', 'fld 12aaa', 'FieldName,1')
		select('RecordFieldsJTbl', 'fld 13aaa', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:FieldName,2(fld 13)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aaa, , 0, 0, 0, , , ], [2, 5, fld 12aaa, , 0, 0, 0, , , ], [7, 9, fld 13aaa, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,2()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aaa, , 0, 0, 0, , , ], [2, 5, fld 12aaa, , 0, 0, 0, , , ], [7, 9, fld 13aaa, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,2()')
		click('Save1')
		click('New1')

		select('RecordDef.Record Name_Txt', 'zxzxzFLD1bbb')
		click('Insert')
		select('RecordFieldsJTbl', '1', 'Position,0')
		select('RecordFieldsJTbl', '2', 'Length,0')
		select('RecordFieldsJTbl', 'ffff', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, ffff, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ], [, zxzxzFLD1aaa, , , , , ], [, zxzxzFLD1bbb, , , , , ]]')
		select('RecordList.Description_Txt', '%%')

##		select('TabbedPane', 'Child Records')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg12')

		select('RecordList.Description_Txt', '%%%')


		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')
		select('RecordList.Description_Txt', '%%')


		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ], [, zxzxzFLD1aaa, , , , , ], [, zxzxzFLD1bbb, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,0(null)')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		click('Right')

		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD2')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		click('Right')

		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD3')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		click('Right')

		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1aaa')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aaa, , 0, 0, 0, , , ], [2, 5, fld 12aaa, , 0, 0, 0, , , ], [7, 9, fld 13aaa, , 0, 0, 0, , , ]]')
		click('Delete2')

		if window('Delete: zxzxzFLD1aaa'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('RecordList.Description_Txt', '%')

		select('TabbedPane', 'Child Records')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg13')
		select('RecordList.Description_Txt', '%%')

		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')
		select('RecordList.Description_Txt', '%')


##		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ], [, zxzxzFLD1aaa, , , , , ], [, zxzxzFLD1bbb, , , , , ]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ], [, zxzxzFLD1bbb, , , , , ]]')

		select('ChildRecordsJTbl', 'cell: ,3(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, ffff, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1bbb')
		click('Delete2')

		if window('Delete: zxzxzFLD1bbb'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell: ,4(null)')
		select('ChildRecordsJTbl', 'cell: ,4(null)')
		select('RecordList.Description_Txt', '%')

		select('RecordList.Record Name_Txt', 'zxzxzFLDg1%')
		select('RecordList.Description_Txt', '%%')

		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
