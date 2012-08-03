useFixture(default)

def test():
	from Modules import commonBits
	import time
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')

		select('RecordList.Description_Txt', '%')

##		select('ChildRecordsJTbl', 'cell: ,0(sl)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',0')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		click(commonBits.fl('Save As'))




		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zx3xzFLD1')
			select('OptionPane.textField', 'zx3xzFLD1aaa')
			click('OK')
		close()

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 13aa', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'fld 12aa', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'fld 11aa', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aa, , 0, 0, 0, , , ], [2, 5, fld 12aa, , 0, 0, 0, , , ], [7, 9, fld 13aa, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD1aaa')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ], [, zx3xzFLD1aaa, , , , , ]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ], [, zx3xzFLD1aaa, , , , , ]]')



		select('ChildRecordsJTbl', 'zx3xzFLD1aaa', commonBits.fl('Tree Parent') + ',2')
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0(zx3xzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , zx3xzFLD1aaa], [, zx3xzFLD1aaa, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,3(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',3')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aa, , 0, 0, 0, , , ], [2, 5, fld 12aa, , 0, 0, 0, , , ], [7, 9, fld 13aa, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD1aaa')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zx3xzFLD1aaa')):
			click('Yes')
		close()

		select('TabbedPane1', commonBits.fl('Child Records'))
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')

		time.sleep(0.9)
		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',3()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')

##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
