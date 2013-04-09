useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zxzxFL1')
		select('RecordDef.Description_Txt', 'Test Create Layout')
		select('RecordDef.List_Chk', 'true')
		select('RecordDef.System_Txt', 'Other')
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '1', commonBits.fl('Position') + ',0')
		select('RecordFieldsJTbl', '2', commonBits.fl('Length') + ',0')
		select('RecordFieldsJTbl', 'fld11', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', '3', commonBits.fl('Position') + ',1')
		select('RecordFieldsJTbl', '4', commonBits.fl('Length') + ',1')
		select('RecordFieldsJTbl', 'fld12', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', '7', commonBits.fl('Position') + ',2')
		select('RecordFieldsJTbl', '8', commonBits.fl('Length') + ',2')
		select('RecordFieldsJTbl', 'fld13', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',2()')
		commonBits.save1(click)
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zxzxFL2')
			click('OK')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*')
		select('RecordList.Record Name_Txt', 'zxzxFL1')
		select('RecordList.Description_Txt', '%')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld11, , 0, 0, 0, , , ], [3, 4, fld12, , 0, 0, 0, , , ], [7, 8, fld13, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Test Create Layout')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxFL1')
		select('RecordList.Record Name_Txt', 'zxzxFL2')
		select('RecordList.Description_Txt', '%%')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld11, , 0, 0, 0, , , ], [3, 4, fld12, , 0, 0, 0, , , ], [7, 8, fld13, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Test Create Layout')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxFL2')
		commonBits.delete3(click)

		if window(commonBits.fl('Delete: zxzxFL2')):
			click('Yes')
		close()

		select('RecordList.Record Name_Txt', 'zxzxFL1')

		select('RecordList.Description_Txt', '%')

		commonBits.delete3(click)

		if window(commonBits.fl('Delete: zxzxFL1')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
