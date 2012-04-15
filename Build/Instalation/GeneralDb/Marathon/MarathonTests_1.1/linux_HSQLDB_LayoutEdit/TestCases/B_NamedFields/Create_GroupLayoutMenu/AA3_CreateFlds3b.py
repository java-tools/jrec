useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Edit Layout')
		click('New1')

		select('RecordDef.Record Name_Txt', 'zxzxzFLDg1')
		select('RecordDef.Description_Txt', 'Group Test 1')
		click('Insert')
		select('RecordFieldsJTbl', 'cell:FieldName,0()')
		click('Delete2')
		select('RecordDef.Record Type_Txt', 'Group of Records')

		select('TabbedPane', 'Child Records')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,0()')
		select('ChildRecordsJTbl', 'zxzxzFLD1', 'Child Record,0')
		select('ChildRecordsJTbl', 'cell:Child Record,0(zxzxzFLD1)')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,1()')
		select('ChildRecordsJTbl', 'zxzxzFLD2', 'Child Record,1')
		select('ChildRecordsJTbl', 'cell:Child Record,1(zxzxzFLD2)')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,2()')
		select('ChildRecordsJTbl', 'zxzxzFLD3', 'Child Record,2')
		select('ChildRecordsJTbl', 'cell:Child Record,2(zxzxzFLD3)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Child Record,2(zxzxzFLD3)')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zxzxzFLDg2')
			click('OK')
		close()


		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		select('RecordDef.Description_Txt', 'Group Test 2')
		select('ChildRecordsJTbl', 'cell:Child Name,1()')
		click('Delete2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD3, , , , , ]]')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%')

		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 1')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg1')
		select('RecordList.Record Name_Txt', 'zxzxzFLDg2')

		select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%%')

		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 2')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg2')
		click('Delete3')

		if window('Delete: zxzxzFLDg2'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zxzxzFLDg1')

		select('RecordList.Description_Txt', '%')

		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxzxzFLD1, , , , , ], [, zxzxzFLD2, , , , , ], [, zxzxzFLD3, , , , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zxzxzFLDg1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
