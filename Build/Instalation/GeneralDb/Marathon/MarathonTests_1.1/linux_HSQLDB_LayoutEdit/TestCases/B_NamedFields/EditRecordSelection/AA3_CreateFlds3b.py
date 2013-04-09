useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		commonBits.new1(click)

		select('RecordDef.Record Name_Txt', 'zxxxzFLDg1')
		select('RecordDef.Description_Txt', 'Group Test 1')
		click(commonBits.fl('Insert'))


		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0()')
		commonBits.delete2(click)
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		select('TabbedPane', commonBits.fl('Child Records'))
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'zxxxzFLD1', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(zxxxzFLD1)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1()')
		select('ChildRecordsJTbl', 'zxxxzFLD2', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1(zxxxzFLD2)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2()')
		select('ChildRecordsJTbl', 'zxxxzFLD3', commonBits.fl('Child Record') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2(zxxxzFLD3)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2(zxxxzFLD3)')
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'zxxxzFLDg2')
			click('OK')
		close()


		#select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD3, , , , , ]]')
		select('RecordDef.Description_Txt', 'Group Test 2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		commonBits.delete2(click)
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD3, , , , , ]]')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg1')

		#select('TabbedPane', commonBits.fl('Child Records'))
		select('RecordList.Description_Txt', '%')

		#select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 1')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxxxzFLDg1')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg2')

		#select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%%')

		#select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 2')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxxxzFLDg2')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: zxxxzFLDg2')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxxxzFLDg1')

		select('RecordList.Description_Txt', '%')

		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , , , , ], [, zxxxzFLD3, , , , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxxxzFLDg1')
##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
