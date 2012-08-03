useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')
		select('RecordList.Description_Txt', '%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		click(commonBits.fl('Save As'))



		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zx3xzFLDg1')
			select('OptionPane.textField', 'zx3xzFLDg777')
			click('OK')
		close()


		##select('TabbedPane', 'Child Records')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777')
		assert_p('ChildRecordsJTbl', 'Text', '', commonBits.fl('Child Name') + ',1')
##		select('ChildRecordsJTbl', 'cell: ,0(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',0')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		click('Right')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD2')
		click('Right')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD3')
		click(commonBits.fl('Save As'))




		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zx3xzFLD3')
			select('OptionPane.textField', 'a777')
			click('Cancel')
		close()
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')

		click(commonBits.fl('Save As'))




		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zx3xzFLD3')
			select('OptionPane.textField', 'zx3xzFLD3a777')
			click('OK')
		close()


		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD3a777')
		select('RecordFieldsJTbl', 'fld 31 777', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'fld 32 777', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'fld 33 777', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'fld 34 777', commonBits.fl('FieldName') + ',3')
		select('RecordFieldsJTbl', 'fld 35 7777', commonBits.fl('FieldName') + ',4')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',2()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31 777, , 0, 0, 0, , , ], [2, 5, fld 32 777, , 0, 0, 0, , , ], [7, 9, fld 33 777, , 0, 0, 0, , , ], [18, 10, fld 34 777, , 0, 0, 0, , , ], [28, 12, fld 35 7777, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',2()')
		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ], [, zx3xzFLD3a777, , , , , ]]')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg7')

		select('RecordList.Description_Txt', '%')

		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')
		select('RecordList.Description_Txt', '%%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,1(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',1')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		click('Right')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		select('RecordList.Record Name_Txt', 'zx3xzFLDg777')

		##select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%')

		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ], [, zx3xzFLD3a777, , , , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',2')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		click('Right')

		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD3a777')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31 777, , 0, 0, 0, , , ], [2, 5, fld 32 777, , 0, 0, 0, , , ], [7, 9, fld 33 777, , 0, 0, 0, , , ], [18, 10, fld 34 777, , 0, 0, 0, , , ], [28, 12, fld 35 7777, , 0, 0, 0, , , ]]')


		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zx3xzFLD3a777')):
			click('Yes')
		close()


		##select('TabbedPane1', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3xzFLDg777')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
