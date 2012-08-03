useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*6')
		select('FileChooser', commonBits.cobolDir() + 'CopyDTAR192.cbl')
		select('ComputerOptionCombo', commonBits.fl('Mainframe')
)
		select('BmKeyedComboBox1', 'Mainframe')

#		select('BmKeyedComboBox1', '9')
		click(commonBits.fl('Go')
)

		assert_p('TextArea', 'Text', commonBits.checkCopybookLoad(commonBits.cobolDir() + 'CopyDTAR192.cbl', 'CopyDTAR192'))

##		assert_p('TextArea', 'Text', '''

##
##-->> ''' + commonBits.cobolDir() + '''CopyDTAR192.cbl processed

##
##      Copybook: CopyDTAR192''')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('TextField', '%CopyDTAR1%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(DTAR192-Date)')
		assert_p('RecordFieldsJTbl', 'Text', 'DTAR192-Date', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(DTAR192-Days)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, DTAR192-Code, , 35, 0, 0, , , DTAR192-Code], [3, 4, DTAR192-Date, , 31, 0, 0, , , DTAR192-Date], [7, 2, DTAR192-Days, , 35, 0, 0, , , DTAR192-Days]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(DTAR192-Code)')
		assert_p('RecordFieldsJTbl', 'RowCount', '3')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(DTAR192-Code)')
		assert_p('TextField2', 'Text', 'CopyDTAR192')
		commonBits.delete3(click)
##		if commonBits.isTstLanguage():
##			click(commonBits.fl('Delete') + '1')
##		else:
##			commonBits.delete3(click)
		if window(commonBits.fl('Delete: CopyDTAR192')):
			click('Yes')
		close()

		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ComputerOptionCombo', commonBits.fl('Intel')
)
		click(commonBits.fl('Go')
)
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))

		select('TextField', '%CopyDTAR1%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(DTAR192-Date)')
		assert_p('RecordFieldsJTbl', 'Text', 'DTAR192-Date', commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(DTAR192-Days)')
		assert_p('RecordFieldsJTbl', 'Text', 'cell:' + commonBits.fl('FieldName') + ',2(DTAR192-Days)')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(DTAR192-Code)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, DTAR192-Code, , 15, 0, 0, , , DTAR192-Code], [3, 4, DTAR192-Date, , 31, 0, 0, , , DTAR192-Date], [7, 2, DTAR192-Days, , 15, 0, 0, , , DTAR192-Days]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(DTAR192-Days)')
		assert_p('RecordFieldsJTbl', 'RowCount', '3')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(DTAR192-Days)')
		assert_p('TextField2', 'Text', 'CopyDTAR192')
##		commonBits.delete3(click)
		commonBits.delete3(click)

		if window(commonBits.fl('Delete: CopyDTAR192')):
			click('Yes')
		close()

		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Close'))

##		select_menu('File>>Exit')
	close()
