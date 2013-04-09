useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zxzxzFD1')
		select('RecordDef.Description_Txt', 'Test Field Create')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '1', commonBits.fl('Position') + ',0')
		select('RecordFieldsJTbl', '2', commonBits.fl('Length') + ',0')
		select('RecordFieldsJTbl', 'fld11', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '3', commonBits.fl('Position') + ',1')
		select('RecordFieldsJTbl', '4', commonBits.fl('Length') + ',1')
		select('RecordFieldsJTbl', 'fld12', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '7', commonBits.fl('Position') + ',2')
		select('RecordFieldsJTbl', '8', commonBits.fl('Length') + ',2')
		select('RecordFieldsJTbl', 'fld13', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld11, , 0, 0, 0, , , ], [3, 4, fld12, , 0, 0, 0, , , ], [7, 8, fld13, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordDef.Description_Txt', 'Text', 'Test Field Create')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFD1')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		commonBits.save1(click)
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zxzxzFD2')
			click('OK')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Fields')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld11, , 0, 0, 0, , , ], [3, 4, fld12, , 0, 0, 0, , , ], [7, 8, fld13, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFD2')
		assert_p('RecordDef.Description_Txt', 'Text', 'Test Field Create')
		select('RecordDef.Description_Txt', 'Test Field Create 2')
		select('RecordFieldsJTbl', 'fld21', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'fld22', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'fld23', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld21, , 0, 0, 0, , , ], [3, 4, fld22, , 0, 0, 0, , , ], [7, 8, fld23, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		commonBits.save1(click)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*')
		select('RecordList.Record Name_Txt', 'zxzxzFD1')

		select('RecordList.Description_Txt', '%')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld11, , 0, 0, 0, , , ], [3, 4, fld12, , 0, 0, 0, , , ], [7, 8, fld13, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Test Field Create')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFD1')
		select('RecordList.Record Name_Txt', 'zxzxzFD2')

		select('RecordList.Description_Txt', '%%')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld21, , 0, 0, 0, , , ], [3, 4, fld22, , 0, 0, 0, , , ], [7, 8, fld23, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Test Field Create 2')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFD2')
		commonBits.delete3(click)
		if window( commonBits.fl('Delete: zxzxzFD2')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxzxzFD1')

		select('RecordList.Description_Txt', '%')

		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFD1')
		commonBits.delete3(click)
		if window( commonBits.fl('Delete: zxzxzFD1')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
