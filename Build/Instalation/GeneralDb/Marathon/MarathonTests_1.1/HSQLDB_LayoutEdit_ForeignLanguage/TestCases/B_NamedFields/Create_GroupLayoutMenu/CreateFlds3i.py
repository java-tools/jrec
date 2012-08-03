useFixture(default)

def test():
	from Modules import commonBits
	import time
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Create Layout'))
		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777')
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		select('RecordDef.System_Txt', 'Unkown')

		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zxzxzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(zxzxzFLD1)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		commonBits.new1(click)

		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777aaaa')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '1', commonBits.fl('Position') + ',0')
		select('RecordFieldsJTbl', '1111', commonBits.fl('Length') + ',0')
		select('RecordFieldsJTbl', 'fff', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fff, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777aaaa')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg777aaaa, , , , , ]]')
		select('ChildRecordsJTbl', 'zxzxzFLDg777aaaa', commonBits.fl('Tree Parent') + ',1')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		click(commonBits.fl('Save As'))




		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zxzxzFLDg777aaaa')
			select('OptionPane.textField', 'zxzxzFLDg777bbbb')
			click('OK')
		close()


		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		select('RecordFieldsJTbl', 'fffbbb', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , ]]')
		select('ChildRecordsJTbl', 'zxzxzFLDg777aaaa', commonBits.fl('Tree Parent') + ',3')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',3(zxzxzFLDg777aaaa)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa]]')
		select('ChildRecordsJTbl', 'cell: ,3(null)')
		click(commonBits.fl('Save As'))




		if window('Input'):
			select('OptionPane.textField', 'zxzxzFLDg777cccc')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777cccc, , , , , ]]')
		select('ChildRecordsJTbl', 'zxzxzFLDg777bbbb', commonBits.fl('Tree Parent') + ',4')
		select('ChildRecordsJTbl', 'cell: ,4(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777dddd')
		commonBits.save1(click)

		click(commonBits.fl('Save As'))




		if window('Input'):
			select('OptionPane.textField', 'zxzxzFLDg777eeee')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Text', '', commonBits.fl('Description') + ',0')
		select('RecordFieldsJTbl', 'fffbbbee', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordFieldsJTbl', 'Text', '', commonBits.fl('Description') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777dddd, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , ]]')


		click(commonBits.fl('Refresh')
)

		select('ChildRecordsJTbl', 'cell: ,4(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777dddd')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		select('ChildRecordsJTbl', 'zxzxzFLDg777aaaa', commonBits.fl('Tree Parent') + ',5')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',5(zxzxzFLDg777aaaa)')

##		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777cccc, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777dddd, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')


		select('ChildRecordsJTbl', 'cell: ,3(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777bbbb')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zxzxzFLDg777bbbb')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777dddd, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777dddd, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777dddd, , , , , ], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')


		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordFieldsJTbl', 'Text', 'fff', commonBits.fl('FieldName') + ',0')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777aaaa')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zxzxzFLDg777aaaa')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg777dddd, , , , , ], [, zxzxzFLDg777eeee, , , , , ]]')

		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',3()')
		select('ChildRecordsJTbl', 'zxzxzFLDg777dddd', commonBits.fl('Tree Parent') + ',3')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field Value') + ',2()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('RecordList.Record Name_Txt', 'zxzxzFLDg777')

		select('RecordList.Description_Txt', '%')


		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg777dddd, , , , , ], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777dddd]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777dddd')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zxzxzFLDg777dddd')):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg777eeee, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777eeee')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zxzxzFLDg777eeee')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ]]')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxzxzFLDg777')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
