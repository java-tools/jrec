useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zx3xzFLDg777')
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		select('RecordDef.System_Txt', 'Unkown')

		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zx3xzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(zx3xzFLD1)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1()')
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0(zx3xzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		commonBits.new1(click)

		select('RecordDef.Record Name_Txt', 'zx3xzFLDg777aaaa')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '1', commonBits.fl('Position') + ',0')
		select('RecordFieldsJTbl', '1111', commonBits.fl('Length') + ',0')
		select('RecordFieldsJTbl', 'fff', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fff, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777aaaa')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLDg777aaaa, , , , , ]]')
		select('ChildRecordsJTbl', 'zx3xzFLDg777aaaa', commonBits.fl('Tree Parent') + ',1')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		click(commonBits.fl('Save As'))




		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zx3xzFLDg777aaaa')
			select('OptionPane.textField', 'zx3xzFLDg777bbbb')
			click('OK')
		close()


		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		select('RecordFieldsJTbl', 'fffbbb', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777aaaa, , , , , ], [, zx3xzFLDg777bbbb, , , , , ]]')
		select('ChildRecordsJTbl', 'zx3xzFLDg777aaaa', commonBits.fl('Tree Parent') + ',3')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',3(zx3xzFLDg777aaaa)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777aaaa, , , , , ], [, zx3xzFLDg777bbbb, , , , , zx3xzFLDg777aaaa]]')
		select('ChildRecordsJTbl', 'cell: ,3(null)')
		click(commonBits.fl('Save As'))




		if window('Input'):
			select('OptionPane.textField', 'zx3xzFLDg777cccc')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777aaaa, , , , , ], [, zx3xzFLDg777bbbb, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777cccc, , , , , ]]')
		select('ChildRecordsJTbl', 'zx3xzFLDg777bbbb', commonBits.fl('Tree Parent') + ',4')
		select('ChildRecordsJTbl', 'cell: ,4(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		select('RecordDef.Record Name_Txt', 'zx3xzFLDg777dddd')
		commonBits.save1(click)

		click(commonBits.fl('Save As'))




		if window('Input'):
			select('OptionPane.textField', 'zx3xzFLDg777eeee')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Text', '', commonBits.fl('Description') + ',0')
		select('RecordFieldsJTbl', 'fffbbbee', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordFieldsJTbl', 'Text', '', commonBits.fl('Description') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777aaaa, , , , , ], [, zx3xzFLDg777bbbb, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777dddd, , , , , zx3xzFLDg777bbbb], [, zx3xzFLDg777eeee, , , , , ]]')


		click(commonBits.fl('Refresh'))

		select('ChildRecordsJTbl', 'cell: ,4(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777dddd')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		select('ChildRecordsJTbl', 'zx3xzFLDg777aaaa', commonBits.fl('Tree Parent') + ',5')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',5(zx3xzFLDg777aaaa)')

##		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777aaaa, , , , , ], [, zx3xzFLDg777bbbb, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777cccc, , , , , zx3xzFLDg777bbbb], [, zx3xzFLDg777eeee, , , , , zx3xzFLDg777aaaa]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777aaaa, , , , , ], [, zx3xzFLDg777bbbb, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777dddd, , , , , zx3xzFLDg777bbbb], [, zx3xzFLDg777eeee, , , , , zx3xzFLDg777aaaa]]')


		select('ChildRecordsJTbl', 'cell: ,3(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777bbbb')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zx3xzFLDg777bbbb')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777aaaa, , , , , ], [, zx3xzFLDg777bbbb, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777dddd, , , , , zx3xzFLDg777bbbb], [, zx3xzFLDg777eeee, , , , , zx3xzFLDg777aaaa]]')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777aaaa, , , , , ], [, zx3xzFLDg777bbbb, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777dddd, , , , , zx3xzFLDg777bbbb], [, zx3xzFLDg777eeee, , , , , zx3xzFLDg777aaaa]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , zx3xzFLDg777aaaa], [, zx3xzFLDg777aaaa, , , , , ], [, zx3xzFLDg777dddd, , , , , ], [, zx3xzFLDg777eeee, , , , , zx3xzFLDg777aaaa]]')


		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordFieldsJTbl', 'Text', 'fff', commonBits.fl('FieldName') + ',0')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777aaaa')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zx3xzFLDg777aaaa')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLDg777dddd, , , , , ], [, zx3xzFLDg777eeee, , , , , ]]')

		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',3()')
		select('ChildRecordsJTbl', 'zx3xzFLDg777dddd', commonBits.fl('Tree Parent') + ',3')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field Value') + ',2()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg777')

		select('RecordList.Description_Txt', '%')


		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLDg777dddd, , , , , ], [, zx3xzFLDg777eeee, , , , , zx3xzFLDg777dddd]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777dddd')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zx3xzFLDg777dddd')):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ], [, zx3xzFLDg777eeee, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg777eeee')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zx3xzFLDg777eeee')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , zx3xzFLD2], [, zx3xzFLD2, , , , , ]]')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3xzFLDg777')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
