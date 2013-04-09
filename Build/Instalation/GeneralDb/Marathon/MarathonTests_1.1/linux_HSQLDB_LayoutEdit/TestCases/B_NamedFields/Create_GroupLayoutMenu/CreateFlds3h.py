useFixture(default)

###
###    Warning this script leaves a parent record selected when it should not
###
###

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Create Layout'))
		select('RecordDef.Record Name_Txt', 'zxzxzFLDg777')
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		select('RecordDef.System_Txt', 'Unkown')

		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(zxzxzFLD2)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1(zxzxzFLD2)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,1(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',1')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		click(commonBits.fl('Save As'))


		##select('TabbedPane1', 'Extras')
		##select('TabbedPane1', 'Extras')
		##select('TabbedPane1', 'Child Records')

		if window('Input'):
			select('OptionPane.textField', 'zxzxzFLD2a77')
			click('OK')
		close()


		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'fld 21a77', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21a77, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',1()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD2a77, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')

		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',2')
		select_menu(commonBits.fl('Edit Child Record'))

		commonBits.new1(click)

		select('RecordDef.Record Name_Txt', 'zxzxzFLDg77zxc')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', '1', commonBits.fl('Position') + ',0')
		select('RecordFieldsJTbl', '39', commonBits.fl('Length') + ',0')
		select('RecordFieldsJTbl', 'ffff', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 39, ffff, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('Description') + ',0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD2a77, , , , , ], [, zxzxzFLDg77zxc, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',2')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21a77, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLD2a77')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD2a77, , , , , ], [, zxzxzFLDg77zxc, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',2')
		select_menu(commonBits.fl('Edit Child Record'))

		
		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zxzxzFLD2a77')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLDg77zxc, , , , , ]]')
##		select('ChildRecordsJTbl', 'cell: ,2(null)')
		rightclick('ChildRecordsJTbl', commonBits.fl('Child Name') + ',2')
		select_menu(commonBits.fl('Edit Child Record'))
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 39, ffff, , 0, 0, 0, , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg77zxc')


		commonBits.delete2(click)

		if window(commonBits.fl('Delete: zxzxzFLDg77zxc')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD2, , , , , ], [, zxzxzFLD2, , , , , ]]')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxzxzFLDg777')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
