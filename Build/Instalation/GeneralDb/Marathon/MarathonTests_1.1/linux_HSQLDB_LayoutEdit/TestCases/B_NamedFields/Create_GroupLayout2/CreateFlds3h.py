useFixture(default)

###
###    Warning this script leaves a parent record selected when it should not
###
###

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
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(zx3xzFLD2)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1()')
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1(zx3xzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,1(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',0')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		click(commonBits.fl('Save As'))


		#select('TabbedPane1', 'Extras')
		#select('TabbedPane1', 'Extras')
		#select('TabbedPane1', 'Child Records')

		if window('Input'):
			select('OptionPane.textField', 'zx3xzFLD2a77')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 21a77', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21a77, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD2a77, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')

		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',2')
		select_menu(commonBits.fl('Edit Child Record'))

		commonBits.new1(click)

		select('RecordDef.Record Name_Txt', 'zx3xzFLDg77zxc')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '1', commonBits.fl('Position') + ',0')
		select('RecordFieldsJTbl', '39', commonBits.fl('Length') + ',0')
		select('RecordFieldsJTbl', 'ffff', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 39, ffff, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD2a77, , , , , ], [, zx3xzFLDg77zxc, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',2')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21a77, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLD2a77')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD2a77, , , , , ], [, zx3xzFLDg77zxc, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',2')
		select_menu(commonBits.fl('Edit Child Record'))
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zx3xzFLD2a77')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLDg77zxc, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',2')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 39, ffff, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg77zxc')
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zx3xzFLDg77zxc')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD2, , , , , ], [, zx3xzFLD2, , , , , ]]')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3xzFLDg777')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
