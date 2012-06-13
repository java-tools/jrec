useFixture(default)

###
###    Warning this script leaves a parent record selected when it should not
###
###

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'zx3xzFLDg777')
		select('RecordDef.Record Type_Txt', 'Group of Records')
		select('RecordDef.System_Txt', 'Unkown')

		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,0()')
		select('ChildRecordsJTbl', 'zx3xzFLD2', 'Child Record,0')
		select('ChildRecordsJTbl', 'cell:Child Record,0(zx3xzFLD2)')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,1()')
		select('ChildRecordsJTbl', 'zx3xzFLD2', 'Child Record,1')
		select('ChildRecordsJTbl', 'cell:Child Record,1(zx3xzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,1(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,0')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		click('Save As')
		select('TabbedPane1', 'Extras')
		select('TabbedPane1', 'Extras')
		select('TabbedPane1', 'Child Records')

		if window('Input'):
			select('OptionPane.textField', 'zx3xzFLD2a77')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 21a77', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21a77, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,1()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD2a77, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')

		rightclick('ChildRecordsJTbl', 'Child Name,2')
		select_menu('Edit Child Record')

		click('New1')

		select('RecordDef.Record Name_Txt', 'zx3xzFLDg77zxc')
		click('Insert')
		select('RecordFieldsJTbl', '1', 'Position,0')
		select('RecordFieldsJTbl', '39', 'Length,0')
		select('RecordFieldsJTbl', 'ffff', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 39, ffff, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:Description,0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD2a77, , , , , ], [, zx3xzFLDg77zxc, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,2')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21a77, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD2a77')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD2a77, , , , , ], [, zx3xzFLDg77zxc, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,2')
		select_menu('Edit Child Record')
		click('Delete2')

		if window('Delete: zx3xzFLD2a77'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLDg77zxc, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,2')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 39, ffff, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg77zxc')
		click('Delete2')

		if window('Delete: zx3xzFLDg77zxc'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ]]')
		click('Delete3')

		if window('Delete: zx3xzFLDg777'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
