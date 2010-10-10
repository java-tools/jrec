useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*7')
		select('FileChooser', commonBits.cobolDir() + 'CopySAR4180C.xml')
		select('ComboBox', 'cb2xml XML Copybook (DB)')
		click('Go')
		assert_p('TextArea', 'Text', '''

-->> ''' + commonBits.cobolDir() + '''CopySAR4180C.xml processed

      Copybook: CopySAR4180C''')
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', '%CopyS%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
		select('RecordFieldsJTbl', 'cell:FieldName,0(R4180C-LOCATION-DETAIL-ID)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 0, R4180C-LOCATION-DETAIL-ID, , 2, 0, 0, , , R4180C-LOCATION-DETAIL-ID], [4, 0, R4180C-LOCATION-NUMBER, , 3, 0, 0, , , R4180C-LOCATION-NUMBER], [8, 0, R4180C-QUANTITY, , 6, 0, 0, , , R4180C-QUANTITY]]')
		select('RecordFieldsJTbl', 'cell:FieldName,2(R4180C-QUANTITY)')
		assert_p('RecordFieldsJTbl', 'RowCount', '3')
		select('RecordFieldsJTbl', 'cell:FieldName,1(R4180C-LOCATION-NUMBER)')
		assert_p('RecordFieldsJTbl', 'Text', 'cell:FieldName,1(R4180C-LOCATION-NUMBER)')
		select('RecordFieldsJTbl', 'cell:FieldName,1(R4180C-LOCATION-NUMBER)')
		assert_p('TextField2', 'Text', 'CopySAR4180C')
		click('Delete3')

		if window('Delete: CopySAR4180C'):
			click('Yes')
		close()

		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Close')

	close()
