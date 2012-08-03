useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*1')
		select('RecordDef.Record Name_Txt', 'xx22xx')
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		select('TabbedPane', commonBits.fl('Child Records'))
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'ams PO Download: Allocation', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'ams PO Download: Detail', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',1(ams PO Download: Detail)')
		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',2()')
		select('ChildRecordsJTbl', 'ams PO Download: Header', commonBits.fl('Child Record') + ',2')
		select('ChildRecordsJTbl', 'ams PO Download: Detail', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'ams PO Download: Header', commonBits.fl('Tree Parent') + ',1')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',1(ams PO Download: Header)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ams PO Download: Header], [, ams PO Download: Header, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',1(ams PO Download: Header)')
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'xx22xx22')
			click('OK')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Child Records')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Name') + ',1()')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ams PO Download: Header], [, ams PO Download: Header, , , , , ]]')
		select('ChildRecordsJTbl', '', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'ams PO Download: Allocation', commonBits.fl('Tree Parent') + ',1')
		select('ChildRecordsJTbl', 'ams PO Download: Header', commonBits.fl('Tree Parent') + ',2')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Field') + ',1()')
		click(commonBits.fl('Save As'))



		if window('Input'):
			click('Cancel')
		close()

		commonBits.save1(click)
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*')
		select('RecordList.Record Name_Txt', 'xx22xx')

		select('RecordList.Description_Txt', '%')

#		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ams PO Download: Header], [, ams PO Download: Header, , , , , ]]')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: xx22xx')):
			click('Yes')
		close()


		select('RecordList.Record Name_Txt', 'xx22xx22')

		select('RecordList.Description_Txt', '%%')

#		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ], [, ams PO Download: Detail, , , , , ams PO Download: Allocation], [, ams PO Download: Header, , , , , ams PO Download: Header]]')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ], [, ams PO Download: Detail, , , , , ams PO Download: Allocation], [, ams PO Download: Header, , , , , ams PO Download: Header]]')
		commonBits.delete3(click)
		if window(commonBits.fl('Delete: xx22xx22')):
			click('Yes')
		close()


		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
