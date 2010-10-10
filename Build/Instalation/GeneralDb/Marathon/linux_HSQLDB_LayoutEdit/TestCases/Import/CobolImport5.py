useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*6')
		select('FileChooser', commonBits.cobolDir() + 'CopyDTAR192.cbl')
		select('ComputerOptionCombo', 'Mainframe')
		select('BmKeyedComboBox1', '9')
		click('Go')
		assert_p('TextArea', 'Text', '''

-->> ''' + commonBits.cobolDir() + '''CopyDTAR192.cbl processed

      Copybook: CopyDTAR192''')
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', '%CopyDTAR1%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:FieldName,1(DTAR192-Date)')
		assert_p('RecordFieldsJTbl', 'Text', 'DTAR192-Date', 'FieldName,1')
		select('RecordFieldsJTbl', 'cell:FieldName,2(DTAR192-Days)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, DTAR192-Code, , 35, 0, 0, , , DTAR192-Code], [3, 4, DTAR192-Date, , 31, 0, 0, , , DTAR192-Date], [7, 2, DTAR192-Days, , 35, 0, 0, , , DTAR192-Days]]')
		select('RecordFieldsJTbl', 'cell:FieldName,0(DTAR192-Code)')
		assert_p('RecordFieldsJTbl', 'RowCount', '3')
		select('RecordFieldsJTbl', 'cell:FieldName,0(DTAR192-Code)')
		assert_p('TextField2', 'Text', 'CopyDTAR192')
		click('Delete3')

		if window('Delete: CopyDTAR192'):
			click('Yes')
		close()

		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ComputerOptionCombo', 'Intel')
		click('Go')
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', '%CopyDTAR1%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:FieldName,1(DTAR192-Date)')
		assert_p('RecordFieldsJTbl', 'Text', 'DTAR192-Date', 'FieldName,1')
		select('RecordFieldsJTbl', 'cell:FieldName,2(DTAR192-Days)')
		assert_p('RecordFieldsJTbl', 'Text', 'cell:FieldName,2(DTAR192-Days)')
		select('RecordFieldsJTbl', 'cell:FieldName,0(DTAR192-Code)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, DTAR192-Code, , 15, 0, 0, , , DTAR192-Code], [3, 4, DTAR192-Date, , 31, 0, 0, , , DTAR192-Date], [7, 2, DTAR192-Days, , 15, 0, 0, , , DTAR192-Days]]')
		select('RecordFieldsJTbl', 'cell:FieldName,2(DTAR192-Days)')
		assert_p('RecordFieldsJTbl', 'RowCount', '3')
		select('RecordFieldsJTbl', 'cell:FieldName,2(DTAR192-Days)')
		assert_p('TextField2', 'Text', 'CopyDTAR192')
		click('Delete3')

		if window('Delete: CopyDTAR192'):
			click('Yes')
		close()

		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Close')

##		select_menu('File>>Exit')
	close()
