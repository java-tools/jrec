useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*7')
		select('FileChooser', commonBits.cobolDir() + 'CopySAR4180A.xml')
		select('ManagerCombo', commonBits.fl('cb2xml XML Copybook (DB)'))
		#select('BmKeyedComboBox1', '9')
		select('BmKeyedComboBox1', 'Mainframe')
		click(commonBits.fl('Go'))
		
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('TextField', 'CopySA%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(R4180A-DEPARTMENT-NO)')
		assert_p('RecordFieldsJTbl', 'Text', 'R4180A-DEPARTMENT-NO', '' + commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',3(R4180A-DATE-FROM)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 0, R4180A-HEADER-ID, , 0, 0, 0, , , R4180A-HEADER-ID], [4, 0, R4180A-SUPPLIER, , 8, 0, 0, , , R4180A-SUPPLIER], [14, 0, R4180A-DEPARTMENT-NO, , 8, 0, 0, , , R4180A-DEPARTMENT-NO], [18, 0, R4180A-DATE-FROM, , 0, 0, 0, , , R4180A-DATE-FROM], [26, 0, R4180A-DATE-TO, , 0, 0, 0, , , R4180A-DATE-TO], [34, 0, R4180A-LEVEL-IND, , 0, 0, 0, , , R4180A-LEVEL-IND]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(R4180A-SUPPLIER)')
		assert_p('RecordFieldsJTbl', 'RowCount', '6')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(R4180A-SUPPLIER)')
		assert_p('TextField2', 'Text', 'CopySAR4180A')
		commonBits.delete3(click)

		if window(commonBits.fl('Delete: CopySAR4180A')):
			click('Yes')
		close()

		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Close'))
	close()
