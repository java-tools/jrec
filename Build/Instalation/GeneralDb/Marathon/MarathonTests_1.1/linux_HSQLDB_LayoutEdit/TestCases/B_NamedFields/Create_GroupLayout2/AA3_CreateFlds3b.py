useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		commonBits.new1(click)

		select('RecordDef.Record Name_Txt', 'zx3xzFLDg1')
		select('RecordDef.Description_Txt', 'Group Test 1')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0()')
		commonBits.delete2(click)
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		select('TabbedPane', commonBits.fl('Child Records'))
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zx3xzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(zx3xzFLD1)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1()')
		select('ChildRecordsJTbl', 'zx3xzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1(zx3xzFLD2)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2()')
		select('ChildRecordsJTbl', 'zx3xzFLD3', commonBits.fl('Child Record') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2(zx3xzFLD3)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2(zx3xzFLD3)')
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zx3xzFLDg2')
			click('OK')
		close()


		select('TabbedPane', commonBits.fl('Child Records'))
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		select('RecordDef.Description_Txt', 'Group Test 2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		commonBits.delete2(click)
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD3, , , , , ]]')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')

		##select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%')

		select('TabbedPane', commonBits.fl('Child Records'))
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 1')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg1')
		select('RecordList.Record Name_Txt', 'zx3xzFLDg2')

		##select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%%')

		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 2')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg2')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zx3xzFLDg2')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx3xzFLDg1')

		select('RecordList.Description_Txt', '%')

		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx3xzFLD1, , , , , ], [, zx3xzFLD2, , , , , ], [, zx3xzFLD3, , , , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx3xzFLDg1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
