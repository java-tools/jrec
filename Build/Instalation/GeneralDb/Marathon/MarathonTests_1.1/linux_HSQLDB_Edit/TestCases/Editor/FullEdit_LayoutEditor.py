useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('ChildRecordsJTbl', 'cell:Field Start,0(0)')
		rightclick('ChildRecordsJTbl', 'Field Start,0')
		select('ChildRecordsJTbl', 'cell:Field Start,0(0)')
		if commonBits.isVersion89():
			assert_p('ChildRecordsJTbl', 'Content', '[[, 353, 0, Record Type, D1, , -1], [, 356, 0, Record Type, H1, , -1], [, 357, 0, Record Type, S1, , -1]]')
		else:
			assert_p('ChildRecordsJTbl', 'Content', '[[, 353, 0, Record Type, D1, -1], [, 356, 0, Record Type, H1, -1], [, 357, 0, Record Type, S1, -1]]')
		select('ChildRecordsJTbl', 'cell:Field Start,0(0)')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		select('ChildRecordsJTbl', 'cell:Field Start,0(0)')
		select('BmKeyedComboBox', 'Record Layout')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:FieldName,0(Record Type)')
		assert_p('RecordFieldsJTbl', 'Text', 'Record Type', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:FieldName,0(Record Type)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')

	close()
