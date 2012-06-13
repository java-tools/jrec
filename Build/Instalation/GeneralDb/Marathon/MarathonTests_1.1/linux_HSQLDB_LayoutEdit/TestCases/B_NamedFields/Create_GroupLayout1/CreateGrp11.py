useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'xxzzxx')
		select('RecordDef.Record Type_Txt', 'Group of Records')

		select('TabbedPane', 'Child Records')
		click('Insert')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,0()')
		select('ChildRecordsJTbl', 'ams PO Download: Allocation', 'Child Record,0')
		select('ChildRecordsJTbl', 'ams PO Download: Detail', 'Child Record,1')
		select('ChildRecordsJTbl', 'ams PO Download: Detail', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0(ams PO Download: Detail)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Tree Parent,0(ams PO Download: Detail)')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'xxzzxx11')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Child Record,0(ams PO Download: Allocation)')
		select('ChildRecordsJTbl', 'cell:Child Record,0(ams PO Download: Allocation)')
		assert_p('ChildRecordsJTbl', 'Text', 'cell:Child Record,0(ams PO Download: Allocation)')
		select('ChildRecordsJTbl', 'cell:Child Record,0(ams PO Download: Allocation)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*')


		select('RecordList.Record Name_Txt', 'xxzzxx')

		select('RecordList.Description_Txt', '%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		select('RecordList.Record Name_Txt', 'xxzzxx11')

		select('RecordList.Description_Txt', '%%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
