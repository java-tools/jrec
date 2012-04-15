useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zxzxFL1')
		select('RecordDef.Description_Txt', 'Test Create Layout')
		select('RecordDef.List_Chk', 'true')
		select('RecordDef.System_Txt', 'Other')
		click('Insert')
		click('Insert')
		click('Insert')
		select('RecordFieldsJTbl', '1', 'Position,0')
		select('RecordFieldsJTbl', '2', 'Length,0')
		select('RecordFieldsJTbl', 'fld11', 'FieldName,0')
		select('RecordFieldsJTbl', '3', 'Position,1')
		select('RecordFieldsJTbl', '4', 'Length,1')
		select('RecordFieldsJTbl', 'fld12', 'FieldName,1')
		select('RecordFieldsJTbl', '7', 'Position,2')
		select('RecordFieldsJTbl', '8', 'Length,2')
		select('RecordFieldsJTbl', 'fld13', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:Description,2()')
		click('Save1')
		click('Save As')

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
		click('Delete3')

		if window('Delete: zxzxFL2'):
			click('Yes')
		close()

		select('RecordList.Record Name_Txt', 'zxzxFL1')

		select('RecordList.Description_Txt', '%')

		click('Delete3')

		if window('Delete: zxzxFL1'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
