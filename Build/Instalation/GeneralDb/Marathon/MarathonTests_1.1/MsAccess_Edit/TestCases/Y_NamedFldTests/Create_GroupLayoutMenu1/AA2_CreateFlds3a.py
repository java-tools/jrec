useFixture(default)

def test():
	import time

	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		click('New1')

		select('RecordDef.Record Name_Txt', 'zxzxzFLD1')
		select('RecordDef.Description_Txt', 'fld tst 3')
		select('RecordDef.Lines to Insert_Txt', '3')
		click('Insert')
		select('RecordDef.Lines to Insert_Txt', '')
		assert_p('RecordFieldsJTbl', 'Content', '[[0, 0, , , 0, 0, 0, , , ], [0, 0, , , 0, 0, 0, , , ], [0, 0, , , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', '1', 'Position,0')
		select('RecordFieldsJTbl', '1', 'Length,0')
		select('RecordFieldsJTbl', 'fld 11', 'FieldName,0')
		select('RecordFieldsJTbl', '2', 'Position,1')
		select('RecordFieldsJTbl', '5', 'Length,1')
		select('RecordFieldsJTbl', 'fld 12', 'FieldName,1')
		select('RecordFieldsJTbl', '7', 'Position,2')
		select('RecordFieldsJTbl', '9', 'Length,2')
		select('RecordFieldsJTbl', 'fld 13', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordDef.Description_Txt', 'Text', 'fld tst 3')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('Save As')

		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zxzxzFLD1')
			select('OptionPane.textField', 'zxzxzFLD2')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'fld tst 3')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD2')
		select('RecordFieldsJTbl', 'cell:Description,2()')
		click('Insert')
		select('RecordFieldsJTbl', '18', 'Position,3')
		select('RecordFieldsJTbl', '10', 'Length,3')
		select('RecordFieldsJTbl', 'fld 24', 'FieldName,3')
		select('RecordFieldsJTbl', 'fld 23', 'FieldName,2')
		select('RecordFieldsJTbl', 'fld 22', 'FieldName,1')
		select('RecordFieldsJTbl', 'fld 21', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:FieldName,1(fld 22)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:FieldName,1(fld 22)')
		click('Save As')

		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zxzxzFLD2')
			select('OptionPane.textField', 'zxzxzFLD3')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 34', 'FieldName,3')
		select('RecordFieldsJTbl', 'fld 33', 'FieldName,2')
		select('RecordFieldsJTbl', 'fld 32', 'FieldName,1')
		select('RecordFieldsJTbl', 'fld 31', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,3()')
		click('Insert')
		select('RecordFieldsJTbl', '28', 'Position,4')
		select('RecordFieldsJTbl', '12', 'Length,4')
		select('RecordFieldsJTbl', 'fld 35', 'FieldName,4')
		select('RecordFieldsJTbl', 'cell:Description,3()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,3()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD3')
		select('RecordFieldsJTbl', 'cell:Description,3()')
		assert_p('RecordDef.Description_Txt', 'Text', 'fld tst 3')
		select('RecordFieldsJTbl', 'cell:Description,3()')
		select('RecordList.Record Name_Txt', 'zxzxzFLD1')

		select('RecordList.Description_Txt', '%')

		time.sleep(1.0)
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 11, , 0, 0, 0, , , ], [2, 5, fld 12, , 0, 0, 0, , , ], [7, 9, fld 13, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'fld tst 3')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD1')
		select('RecordList.Record Name_Txt', 'zxzxzFLD2')

		select('RecordList.Description_Txt', '%%')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD2')
		assert_p('RecordDef.Description_Txt', 'Text', 'fld tst 3')
		select('RecordList.Record Name_Txt', 'zxzxzFLD3')

		select('RecordList.Description_Txt', '%')

		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 31, , 0, 0, 0, , , ], [2, 5, fld 32, , 0, 0, 0, , , ], [7, 9, fld 33, , 0, 0, 0, , , ], [18, 10, fld 34, , 0, 0, 0, , , ], [28, 12, fld 35, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD3')
##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
