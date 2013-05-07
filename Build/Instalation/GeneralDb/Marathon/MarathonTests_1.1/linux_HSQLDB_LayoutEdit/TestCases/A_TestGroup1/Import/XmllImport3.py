useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*7')
		select('FileChooser', commonBits.cobolDir() + 'CopySAR4180C.xml')
		select('ManagerCombo', commonBits.fl('cb2xml XML Copybook (DB)'))

		click(commonBits.fl('Go'))

		assert_p('TextArea', 'Text', commonBits.checkCopybookLoad(commonBits.cobolDir() + 'CopySAR4180C.xml', 'CopySAR4180C'))
##		assert_p('TextArea', 'Text', '''
##
##-->> ''' + commonBits.cobolDir() + '''CopySAR4180C.xml processed
##
##      Copybook: CopySAR4180C''')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('TextField', '%CopyS%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(R4180C-LOCATION-DETAIL-ID)')
##		assert_p('RecordFieldsJTbl', 'Content', '[[1, 0, R4180C-LOCATION-DETAIL-ID, , 2, 0, 0, , , R4180C-LOCATION-DETAIL-ID], [4, 0, R4180C-LOCATION-NUMBER, , 3, 0, 0, , , R4180C-LOCATION-NUMBER], [8, 0, R4180C-QUANTITY, , 6, 0, 0, , , R4180C-QUANTITY]]')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 0, R4180C-LOCATION-DETAIL-ID, , 2, 0, 0, , , R4180C-LOCATION-DETAIL-ID], [4, 0, R4180C-LOCATION-NUMBER, , 3, 0, 0, , , R4180C-LOCATION-NUMBER], [8, 0, R4180C-QUANTITY, , 7, 0, 0, , , R4180C-QUANTITY]]')


		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(R4180C-QUANTITY)')
		assert_p('RecordFieldsJTbl', 'RowCount', '3')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(R4180C-LOCATION-NUMBER)')
		assert_p('RecordFieldsJTbl', 'Text', 'cell:' + commonBits.fl('FieldName') + ',1(R4180C-LOCATION-NUMBER)')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(R4180C-LOCATION-NUMBER)')
		assert_p('TextField2', 'Text', 'CopySAR4180C')
		commonBits.delete3(click)

		if window(commonBits.fl('Delete: CopySAR4180C')):
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
