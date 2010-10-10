useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*7')
		select('FileChooser', commonBits.cobolDir() + 'CopyDTAR119.cbl')
		select('ComboBox', 'Cobol Copybook (DB)')
		select('ComputerOptionCombo', 'Mainframe')
		select('BmKeyedComboBox', '2')
		select('BmKeyedComboBox1', '9')
		click('Go')
		assert_p('TextArea', 'Text', '''

-->> ''' + commonBits.cobolDir() + '''CopyDTAR119.cbl processed

      Copybook: CopyDTAR119''')
		select_menu('Record Layouts>>Edit Layout')
		select('TextField', '%CopyDTAR%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:FieldName,3(DTAR119-CARD-NO)')
		assert_p('RecordFieldsJTbl', 'Text', 'DTAR119-CARD-NO', 'FieldName,3')
		select('RecordFieldsJTbl', 'cell:FieldName,2(DTAR119-TRANS-CODE)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, DTAR119-STORE-NO, , 31, 0, 0, , , DTAR119-STORE-NO], [3, 4, DTAR119-TRANS-DATE, , 31, 0, 0, , , DTAR119-TRANS-DATE], [7, 2, DTAR119-TRANS-CODE, , 31, 0, 0, , , DTAR119-TRANS-CODE], [9, 16, DTAR119-CARD-NO, , 8, 0, 0, , , DTAR119-CARD-NO], [25, 3, DTAR119-TERMINAL-ID, , 8, 0, 0, , , DTAR119-TERMINAL-ID], [28, 4, DTAR119-TRANS-NO, , 8, 0, 0, , , DTAR119-TRANS-NO], [32, 4, DTAR119-BONUS-POINTS, , 31, 0, 0, , , DTAR119-BONUS-POINTS], [36, 5, DTAR119-KEYCODE, , 31, 0, 0, , , DTAR119-KEYCODE], [41, 5, DTAR119-KEYCODE-AMT, , 31, 2, 0, , , DTAR119-KEYCODE-AMT], [46, 5, DTAR119-KEYCODE-QTY, , 31, 2, 0, , , DTAR119-KEYCODE-QTY], [51, 3, DTAR119-PROM-NO, , 31, 0, 0, , , DTAR119-PROM-NO]]')
		select('RecordFieldsJTbl', 'cell:FieldName,6(DTAR119-BONUS-POINTS)')
		assert_p('RecordFieldsJTbl', 'RowCount', '11')
		select('RecordFieldsJTbl', 'cell:FieldName,8(DTAR119-KEYCODE-AMT)')
		assert_p('RecordFieldsJTbl', 'Text', 'cell:FieldName,8(DTAR119-KEYCODE-AMT)')
		select('RecordFieldsJTbl', 'cell:FieldName,8(DTAR119-KEYCODE-AMT)')
		assert_p('TextField2', 'Text', 'CopyDTAR119')
		click('Delete3')

		if window('Delete: CopyDTAR119'):
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
