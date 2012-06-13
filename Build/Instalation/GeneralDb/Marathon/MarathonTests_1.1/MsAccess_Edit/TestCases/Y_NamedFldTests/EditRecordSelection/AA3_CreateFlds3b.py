useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
##		click('*')
		select_menu('Record Layouts>>Edit Layout')
		click('New1')

		select('RecordDef.Record Name_Txt', 'zx33xzFLDg1')
		select('RecordDef.Description_Txt', 'Group Test 1')
		click('Insert')
		select('RecordFieldsJTbl', 'cell:FieldName,0()')
		click('Delete2')
		select('RecordDef.Record Type_Txt', 'Group of Records')

		select('TabbedPane', 'Child Records')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,0()')
		select('ChildRecordsJTbl', 'zx33xzFLD1', 'Child Record,0')
		select('ChildRecordsJTbl', 'cell:Child Record,0(zx33xzFLD1)')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,1()')
		select('ChildRecordsJTbl', 'zx33xzFLD2', 'Child Record,1')
		select('ChildRecordsJTbl', 'cell:Child Record,1(zx33xzFLD2)')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,2()')
		select('ChildRecordsJTbl', 'zx33xzFLD3', 'Child Record,2')
		select('ChildRecordsJTbl', 'cell:Child Record,2(zx33xzFLD3)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , ], [, zx33xzFLD2, , , , , ], [, zx33xzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Child Record,2(zx33xzFLD3)')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zx33xzFLDg2')
			click('OK')
		close()


		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , ], [, zx33xzFLD2, , , , , ], [, zx33xzFLD3, , , , , ]]')
		select('RecordDef.Description_Txt', 'Group Test 2')
		select('ChildRecordsJTbl', 'cell:Child Name,1()')
		click('Delete2')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , ], [, zx33xzFLD3, , , , , ]]')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg1')

		select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%')

		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , ], [, zx33xzFLD2, , , , , ], [, zx33xzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 1')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx33xzFLDg1')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg2')

##		select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%%')

##		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , ], [, zx33xzFLD3, , , , , ]]')
		assert_p('RecordDef.Description_Txt', 'Text', 'Group Test 2')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx33xzFLDg2')
		click('Delete3')

		if window('Delete: zx33xzFLDg2'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'zx33xzFLDg1')

		select('RecordList.Description_Txt', '%')

		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , ], [, zx33xzFLD2, , , , , ], [, zx33xzFLD3, , , , , ]]')
		assert_p('RecordDef.Record Name_Txt', 'Text', 'zx33xzFLDg1')
##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
