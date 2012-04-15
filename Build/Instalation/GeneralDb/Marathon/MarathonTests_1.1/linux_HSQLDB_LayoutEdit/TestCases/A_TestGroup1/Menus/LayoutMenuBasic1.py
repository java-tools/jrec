useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'ams PO Download%')
		select('TextField1', '%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		assert_p('TextField2', 'Text', 'ams PO Download')
##		select('ChildRecordsJTbl', 'cell:,0(0)')
		assert_p('ChildRecordsJTbl', 'RowCount', '3')
##		select('ChildRecordsJTbl', 'cell:,0(0)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Create Layout')
		if commonBits.isVersion89():
			assert_p('BmKeyedComboBox', 'Text', 'Record Layout')
		else:
			assert_p('BmKeyedComboBox', 'Text', 'XML')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Layout Wizard')
##		assert_p('Label8', 'Text', 'Layout Name')

		assert_p('Label', 'Text', 'File Name')
		assert_p('Label1', 'Text', 'File Structure')
		assert_p('Label3', 'Text', 'Record Type')
		assert_p('Label4', 'Text', 'Font Name')
		assert_p('BmKeyedComboBox', 'Text', 'Default')
		
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Load Cobol Copybook')
		assert_p('SplitCombo', 'Text', 'No Split')
		assert_p('ComputerOptionCombo', 'Text', 'Intel')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Record Layouts>>Edit System Table')
		assert_p('TextField1', 'Text', 'System')
		assert_p('TextField', 'Text', '3')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()