useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Create Layout')
		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777')
		select('RecordDef.Record Type_Txt', 'Group of Records')

		select('RecordDef.System_Txt', 'Unkown')

		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,0()')
		select('ChildRecordsJTbl', 'zxzxzFLD1', 'Child Record,0')
		select('ChildRecordsJTbl', 'cell:Child Record,0(zxzxzFLD1)')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,1()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Child Record,1')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,1(null)')
		click('New2')

		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777aaaa')
		click('Insert')
		select('RecordFieldsJTbl', '1', 'Position,0')
		select('RecordFieldsJTbl', '1111', 'Length,0')
		select('RecordFieldsJTbl', 'fff', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fff, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777aaaa')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg777aaaa, , , , , ]]')
		select('ChildRecordsJTbl', 'zxzxzFLDg777aaaa', 'Tree Parent,1')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		click('Save As')


		if window('Input'):
			assert_p('OptionPane.textField', 'Text', 'zxzxzFLDg777aaaa')
			select('OptionPane.textField', 'zxzxzFLDg777bbbb')
			click('OK')
		close()


		select('RecordFieldsJTbl', 'cell:Description,0()')
		select('RecordFieldsJTbl', 'fffbbb', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , ]]')
		select('ChildRecordsJTbl', 'zxzxzFLDg777aaaa', 'Tree Parent,3')
		select('ChildRecordsJTbl', 'cell:Tree Parent,3(zxzxzFLDg777aaaa)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa]]')
		select('ChildRecordsJTbl', 'cell: ,3(null)')
		click('Save As')


		if window('Input'):
			select('OptionPane.textField', 'zxzxzFLDg777cccc')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777cccc, , , , , ]]')
		select('ChildRecordsJTbl', 'zxzxzFLDg777bbbb', 'Tree Parent,4')
		select('ChildRecordsJTbl', 'cell: ,4(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777dddd')
		click('Save1')

		click('Save As')


		if window('Input'):
			select('OptionPane.textField', 'zxzxzFLDg777eeee')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Text', '', 'Description,0')
		select('RecordFieldsJTbl', 'fffbbbee', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		assert_p('RecordFieldsJTbl', 'Text', '', 'Description,0')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777dddd, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , ]]')


		click('Refresh')

		select('ChildRecordsJTbl', 'cell: ,4(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777dddd')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		select('ChildRecordsJTbl', 'zxzxzFLDg777aaaa', 'Tree Parent,5')
		select('ChildRecordsJTbl', 'cell:Tree Parent,5(zxzxzFLDg777aaaa)')

##		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777cccc, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777dddd, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')


		select('ChildRecordsJTbl', 'cell: ,3(null)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1111, fffbbb, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777bbbb')
		click('Delete2')

		if window('Delete: zxzxzFLDg777bbbb'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777dddd, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')
##		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777bbbb, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777dddd, , , , , zxzxzFLDg777bbbb], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , zxzxzFLDg777aaaa], [, zxzxzFLDg777aaaa, , , , , ], [, zxzxzFLDg777dddd, , , , , ], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777aaaa]]')


		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordFieldsJTbl', 'Text', 'fff', 'FieldName,0')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777aaaa')
		click('Delete2')

		if window('Delete: zxzxzFLDg777aaaa'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg777dddd, , , , , ], [, zxzxzFLDg777eeee, , , , , ]]')

		select('ChildRecordsJTbl', 'cell:Tree Parent,3()')
		select('ChildRecordsJTbl', 'zxzxzFLDg777dddd', 'Tree Parent,3')
		select('ChildRecordsJTbl', 'cell:Field Value,2()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg777')

		select('RecordList.Description_Txt', '%')


		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg777dddd, , , , , ], [, zxzxzFLDg777eeee, , , , , zxzxzFLDg777dddd]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777dddd')
		click('Delete2')

		if window('Delete: zxzxzFLDg777dddd'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg777eeee, , , , , ]]')
		select('ChildRecordsJTbl', 'cell: ,2(null)')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg777eeee')
		click('Delete2')

		if window('Delete: zxzxzFLDg777eeee'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , zxzxzFLD2], [, zxzxzFLD2, , , , , ]]')
		click('Delete3')

		if window('Delete: zxzxzFLDg777'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
