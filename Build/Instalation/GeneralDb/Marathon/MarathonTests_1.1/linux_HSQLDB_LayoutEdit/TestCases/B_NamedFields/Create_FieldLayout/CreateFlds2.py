useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zxzxzFD1')
		select('RecordDef.Description_Txt', 'Test Field Create')
		click('Insert')
		select('RecordFieldsJTbl', '1', 'Position,0')
		select('RecordFieldsJTbl', '2', 'Length,0')
		select('RecordFieldsJTbl', 'fld11', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('Insert')
		select('RecordFieldsJTbl', '3', 'Position,1')
		select('RecordFieldsJTbl', '4', 'Length,1')
		select('RecordFieldsJTbl', 'fld12', 'FieldName,1')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('Insert')
		select('RecordFieldsJTbl', '7', 'Position,2')
		select('RecordFieldsJTbl', '8', 'Length,2')
		select('RecordFieldsJTbl', 'fld13', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld11, , 0, 0, 0, , , ], [3, 4, fld12, , 0, 0, 0, , , ], [7, 8, fld13, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordDef.Description_Txt', 'Text', 'Test Field Create')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFD1')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('Save1')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zxzxzFD2')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld11, , 0, 0, 0, , , ], [3, 4, fld12, , 0, 0, 0, , , ], [7, 8, fld13, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFD2')
		assert_p('RecordDef.Description_Txt', 'Text', 'Test Field Create')
		select('RecordDef.Description_Txt', 'Test Field Create 2')
		select('RecordFieldsJTbl', 'fld21', 'FieldName,0')
		select('RecordFieldsJTbl', 'fld22', 'FieldName,1')
		select('RecordFieldsJTbl', 'fld23', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, fld21, , 0, 0, 0, , , ], [3, 4, fld22, , 0, 0, 0, , , ], [7, 8, fld23, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('Save1')
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
		click('Delete3')

		if window('Delete: zxzxzFD2'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxzxzFD1')

		select('RecordList.Description_Txt', '%')

		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFD1')
		click('Delete3')

		if window('Delete: zxzxzFD1'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
