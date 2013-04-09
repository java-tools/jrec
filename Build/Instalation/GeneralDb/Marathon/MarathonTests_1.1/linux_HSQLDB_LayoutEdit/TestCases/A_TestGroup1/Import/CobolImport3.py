useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*6')
		select('FileChooser', commonBits.cobolDir() + 'CopyDTAR020.cbl')
		select('ComputerOptionCombo', commonBits.fl('Mainframe'))
		select('BmKeyedComboBox1', 'Mainframe')
		select('BmKeyedComboBox', commonBits.fl('Fixed Length Binary'))

#		select('BmKeyedComboBox1', '9')
#		select('BmKeyedComboBox', '2')

		click(commonBits.fl('Go'))

		assert_p('TextArea', 'Text', commonBits.checkCopybookLoad(commonBits.cobolDir() + 'CopyDTAR020.cbl', 'CopyDTAR020'))
		assert_p('TextArea', 'Text', commonBits.checkCopybookLoad(commonBits.cobolDir() + 'CopyDTAR020.cbl', 'CopyDTAR020'))


##		assert_p('TextArea', 'Text', '''

##
##-->> ''' + commonBits.cobolDir() + '''CopyDTAR020.cbl processed

##
##      Copybook: CopyDTAR020''')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))

		select('TextField', '%CopyDTA%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(DTAR020-DATE)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 8, DTAR020-KEYCODE-NO, , 0, 0, 0, , , DTAR020-KEYCODE-NO], [9, 2, DTAR020-STORE-NO, , 31, 0, 0, , , DTAR020-STORE-NO], [11, 4, DTAR020-DATE, , 31, 0, 0, , , DTAR020-DATE], [15, 2, DTAR020-DEPT-NO, , 31, 0, 0, , , DTAR020-DEPT-NO], [17, 5, DTAR020-QTY-SOLD, , 31, 0, 0, , , DTAR020-QTY-SOLD], [22, 6, DTAR020-SALE-PRICE, , 31, 2, 0, , , DTAR020-SALE-PRICE]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(DTAR020-STORE-NO)')
		assert_p('RecordFieldsJTbl', 'RowCount', '6')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(DTAR020-STORE-NO)')
		assert_p('TextField2', 'Text', 'CopyDTAR020')
		if commonBits.isTstLanguage():
			click(commonBits.fl('Delete') + '1')
		else:
			commonBits.delete3(click)
		if window(commonBits.fl('Delete: CopyDTAR020')):
			click('Yes')
		close()

		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Close'))

##		select_menu('File>>Exit')
	close()
