useFixture(default)

def test():
	from Modules import commonBits
	import time
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		select('RecordList.Description_Txt', '%')

##		select('ChildRecordsJTbl', 'cell: ,0(sl)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',0')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		click(commonBits.fl('Save As'))




		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zxzxzFLD1')
			select('OptionPane.textField', 'zxzxzFLD1aaa')
			click('OK')
		close()

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 13aa', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'fld 12aa', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'fld 11aa', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aa, , 0, 0, 0, , , ], [2, 5, fld 12aa, , 0, 0, 0, , , ], [7, 9, fld 13aa, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1aaa')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		commonBits.save1(click)

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ], [, zxzxzFLD1aaa, , , , , ]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ], [, zxzxzFLD1aaa, , , , , ]]')



		select('ChildRecordsJTbl', 'zxzxzFLD1aaa', commonBits.fl('Tree Parent') + ',2')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , zxzxzFLD1aaa], [, zxzxzFLD1aaa, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,3(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',3')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11aa, , 0, 0, 0, , , ], [2, 5, fld 12aa, , 0, 0, 0, , , ], [7, 9, fld 13aa, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1aaa')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zxzxzFLD1aaa')):
			click('Yes')
		close()

		select('TabbedPane1', commonBits.fl('Child Records')
)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')

		time.sleep(0.9)
		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',3()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')

##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
