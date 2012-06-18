useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'xx22xx')
		select('RecordDef.Record Type_Txt', 'Group of Records')

#		select('TabbedPane', 'Child Records')
		click('Insert')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,0()')
		select('ChildRecordsJTbl', 'ams PO Download: Allocation', 'Child Record,0')
		select('ChildRecordsJTbl', 'ams PO Download: Detail', 'Child Record,1')
		select('ChildRecordsJTbl', 'cell:Child Record,1(ams PO Download: Detail)')
		click('Insert')
		select('ChildRecordsJTbl', 'cell:Child Record,2()')
		select('ChildRecordsJTbl', 'ams PO Download: Header', 'Child Record,2')
		select('ChildRecordsJTbl', 'ams PO Download: Detail', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'ams PO Download: Header', 'Tree Parent,1')
		select('ChildRecordsJTbl', 'cell:Tree Parent,1(ams PO Download: Header)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ams PO Download: Header], [, ams PO Download: Header, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:Tree Parent,1(ams PO Download: Header)')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'xx22xx22')
			click('OK')
		close()

		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		select('ChildRecordsJTbl', 'cell:Child Name,1()')
		select('ChildRecordsJTbl', 'cell:Child Name,1()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ams PO Download: Header], [, ams PO Download: Header, , , , , ]]')
		select('ChildRecordsJTbl', '', 'Tree Parent,0')
		select('ChildRecordsJTbl', 'ams PO Download: Allocation', 'Tree Parent,1')
		select('ChildRecordsJTbl', 'ams PO Download: Header', 'Tree Parent,2')
		select('ChildRecordsJTbl', 'cell:Field,1()')
		click('Save As')

		if window('Input'):
			click('Cancel')
		close()

		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*')
		select('RecordList.Record Name_Txt', 'xx22xx')

		select('RecordList.Description_Txt', '%')

#		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ams PO Download: Header], [, ams PO Download: Header, , , , , ]]')
		click('Delete3')

		if window('Delete: xx22xx'):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'xx22xx22')

		select('RecordList.Description_Txt', '%%')

#		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ], [, ams PO Download: Detail, , , , , ams PO Download: Allocation], [, ams PO Download: Header, , , , , ams PO Download: Header]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ], [, ams PO Download: Detail, , , , , ams PO Download: Allocation], [, ams PO Download: Header, , , , , ams PO Download: Header]]')
		click('Delete3')

		if window('Delete: xx22xx22'):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
