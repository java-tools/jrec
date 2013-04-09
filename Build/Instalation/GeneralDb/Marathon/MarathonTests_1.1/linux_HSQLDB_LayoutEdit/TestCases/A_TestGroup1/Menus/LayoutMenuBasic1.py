useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('TextField', 'ams PO Download%')
		select('TextField1', '%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		select('TextField1', '%')
		##select('TabbedPane', 'Extras')
		##select('TabbedPane', 'Child Records')
		assert_p('TextField2', 'Text', 'ams PO Download')
##		select('ChildRecordsJTbl', 'cell:,0(0)')
		assert_p('ChildRecordsJTbl', 'RowCount', '3')
##		select('ChildRecordsJTbl', 'cell:,0(0)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Create Layout'))
		if commonBits.isVersion80():
			assert_p('BmKeyedComboBox', 'Text',  commonBits.fl('Record Layout'))
		else:
			assert_p('BmKeyedComboBox', 'Text', 'XML')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Layout Wizard'))
##		assert_p('Label8', 'Text', 'Layout Name')

		assert_p('Label', 'Text',  commonBits.fl('File Name'))
		assert_p('Label1', 'Text',  commonBits.fl('File Structure'))
		assert_p('Label3', 'Text',  commonBits.fl('Record Type'))
		assert_p('Label4', 'Text',  commonBits.fl('Font Name'))
		assert_p('BmKeyedComboBox', 'Text',  commonBits.fl('Default'))
		
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Load Cobol Copybook'))
		assert_p('SplitCombo', 'Text',  commonBits.fl('No Split'))
		assert_p('ComputerOptionCombo', 'Text',  commonBits.fl('Intel'))
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit System Table'))
		assert_p('TextField1', 'Text', 'System')
		assert_p('TextField', 'Text', '3')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
