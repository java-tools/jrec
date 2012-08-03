useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		commonBits.new1(click)

		select('RecordDef.Record Name_Txt', 'zxzxzFLDg1')
		select('RecordDef.Description_Txt', 'Group Test 1')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0()')
		commonBits.delete2(click)
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		##select('TabbedPane', 'Child Records')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zxzxzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(zxzxzFLD1)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1(zxzxzFLD2)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2()')
		select('ChildRecordsJTbl', 'zxzxzFLD3', commonBits.fl('Child Record') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2(zxzxzFLD3)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2(zxzxzFLD3)')
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zxzxzFLDg2')
			click('OK')
		close()


		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		select('RecordDef.Description_Txt', 'Group Test 2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		commonBits.delete2(click)
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD3, , , , , ]]')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		#select('TabbedPane', commonBits.fl('Child Records'))
		select('RecordList.Description_Txt', '%')

		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 1')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg1')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg2')

		#select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%%')

		select('TabbedPane', commonBits.fl('Child Records'))
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 2')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg2')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxzxzFLDg2')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		select('RecordList.Description_Txt', '%')

		select('TabbedPane', commonBits.fl('Child Records')
)

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
