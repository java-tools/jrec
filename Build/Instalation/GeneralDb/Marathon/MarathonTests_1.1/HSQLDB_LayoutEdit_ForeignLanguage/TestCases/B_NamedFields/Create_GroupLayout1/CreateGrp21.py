useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Create Layout'))
		select('RecordDef.Record Name_Txt', 'xx11xx')
		select('RecordDef.Record Type_Txt', commonBits.fl('Group of Records'))

		##select('TabbedPane', 'Child Records')
		click(commonBits.fl('Insert'))


		click(commonBits.fl('Insert'))


		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0()')
		select('ChildRecordsJTbl', 'ams PO Download: Allocation', commonBits.fl('Child Record') + ',0')
		select('ChildRecordsJTbl', 'ams PO Download: Detail', commonBits.fl('Child Record') + ',1')
		select('ChildRecordsJTbl', 'ams PO Download: Detail', commonBits.fl('Tree Parent') + ',0')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0(ams PO Download: Detail)')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Tree Parent') + ',0(ams PO Download: Detail)')
		click(commonBits.fl('Save As'))



		if window('Input'):
			select('OptionPane.textField', 'xx11xx11')
			click('OK')
		close()

		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(ams PO Download: Allocation)')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(ams PO Download: Allocation)')
		assert_p('ChildRecordsJTbl', 'Text', 'cell:' + commonBits.fl('Child Record') + ',0(ams PO Download: Allocation)')
		select('ChildRecordsJTbl', 'cell:' + commonBits.fl('Child Record') + ',0(ams PO Download: Allocation)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*')


		select('RecordList.Record Name_Txt', 'xx11xx')

		select('RecordList.Description_Txt', '%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		select('RecordList.Record Name_Txt', 'xx11xx11')

		select('RecordList.Description_Txt', '%%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Allocation, , , , , ams PO Download: Detail], [, ams PO Download: Detail, , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
