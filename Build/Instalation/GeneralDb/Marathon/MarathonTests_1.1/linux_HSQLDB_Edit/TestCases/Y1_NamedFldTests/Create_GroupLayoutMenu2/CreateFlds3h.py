useFixture(default)

###
###    Warning this script leaves a parent record selected when it should not
###
###

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Create Layout')
		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777')
		select('RecordDef.Record Type_Txt', 'Group of Records')

		select('RecordDef.System_Txt', 'Unkown')

		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,0()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Child Record,0')
		select('ChildRecordsJTbl', 'cell:Child Record,0(zxzxzFLD2)')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,1()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Child Record,1')
		select('ChildRecordsJTbl', 'cell:Child Record,1(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		click('Save As')
		select('TabbedPane1', 'Extras')
		select('TabbedPane1', 'Extras')
		select('TabbedPane1', 'Child Records')

		if window('Input'):
			select('OptionPane.textField', 'zxzxzFLD2a77')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 21a77', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21a77, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD2a77, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')

		click('New2')

		select('RecordDef.Record Name_Txt', 'zxzxzFLDg77zxc')
		click('Insert')
		select('RecordFieldsJTbl', '1', 'Position,0')
		select('RecordFieldsJTbl', '39', 'Length,0')
		select('RecordFieldsJTbl', 'ffff', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 39, ffff, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD2a77, , , , , ], [, zxzxzFLDg77zxc, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21a77, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD2a77')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD2a77, , , , , ], [, zxzxzFLDg77zxc, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		click('Delete2')

		if window('Delete: zxzxzFLD2a77'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg77zxc, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 39, ffff, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg77zxc')
		click('Delete2')

		if window('Delete: zxzxzFLDg77zxc'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ]]')
		click('Delete3')

		if window('Delete: zxzxzFLDg777'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
