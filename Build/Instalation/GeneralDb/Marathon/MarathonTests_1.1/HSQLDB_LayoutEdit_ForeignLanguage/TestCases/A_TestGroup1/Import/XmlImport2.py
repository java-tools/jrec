useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*7')
		select('FileChooser', commonBits.cobolDir() + 'CopySAR4180B.xml')
		select('ManagerCombo', commonBits.fl('cb2xml XML Copybook (DB)'))

		click(commonBits.fl('Go'))

		assert_p('TextArea', 'Text', commonBits.checkCopybookLoad(commonBits.cobolDir() + 'CopySAR4180B.xml', 'CopySAR4180B'))
##		assert_p('TextArea', 'Text', '''
##
##-->> ''' + commonBits.cobolDir() + '''CopySAR4180B.xml processed
##
##      Copybook: CopySAR4180B''')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('TextField', '%CopyS%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(R4180B-KEYCODE)')
		assert_p('RecordFieldsJTbl', 'Text', 'R4180B-KEYCODE', '' + commonBits.fl('FieldName') + ',1')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(R4180B-APN)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 0, R4180B-KEYCODE-DETAIL-ID, , 0, 0, 0, , , R4180B-KEYCODE-DETAIL-ID], [4, 0, R4180B-KEYCODE, , 8, 0, 0, , , R4180B-KEYCODE], [12, 0, R4180B-APN, , 8, 0, 0, , , R4180B-APN], [25, 0, R4180B-UNIT-TYPE, , 0, 0, 0, , , R4180B-UNIT-TYPE]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(R4180B-KEYCODE-DETAIL-ID)')
		assert_p('RecordFieldsJTbl', 'RowCount', '4')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(R4180B-KEYCODE-DETAIL-ID)')
		assert_p('TextField2', 'Text', 'CopySAR4180B')
		commonBits.delete3(click)

		if window(commonBits.fl('Delete: CopySAR4180B')):
			click('Yes')
		close()

		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		select('TextField', '%CopySA%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		assert_p('TextField2', 'Text', '')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Close'))
		click(commonBits.fl('Close'))
	close()
