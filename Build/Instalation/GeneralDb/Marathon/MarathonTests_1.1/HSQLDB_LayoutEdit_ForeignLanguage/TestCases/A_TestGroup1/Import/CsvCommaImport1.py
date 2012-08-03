useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*7')
		select('FileChooser', commonBits.cobolDir() + 'ZC_AmsPoDownloadComma.Txt')
		
		select('ManagerCombo', commonBits.fl('RecordEditor Csv Copybook (Comma Seperator)'))
		#select('BmKeyedComboBox1', '32')
		click(commonBits.fl('Go'))

		assert_p('TextArea', 'Text', commonBits.checkCopybookLoad(commonBits.cobolDir() + 'ZC_AmsPoDownloadComma.Txt', 'ZC_AmsPoDownloadComma'))

##		assert_p('TextArea', 'Text', '''

##
##-->> ''' + commonBits.cobolDir() + '''ZC_AmsPoDownloadComma.Txt processed

##
##      Copybook: ZC_AmsPoDownloadComma''')
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))
		select('TextField', 'ZC_AmsPoDownloadComma%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('TextField1', '%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(Pack Qty)')
		assert_p('RecordFieldsJTbl', 'Text', 'Pack Cost', commonBits.fl('FieldName') + ',2')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(Record Type)')
		assert_p('RecordFieldsJTbl', 'Text', 'Record Type', commonBits.fl('FieldName') + ',0')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',3(APN)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, Record Type, , 0, 0, 0, , , ], [3, 9, Pack Qty, , 8, 4, 0, , , ], [12, 13, Pack Cost, , 8, 4, 0, , , ], [25, 13, APN, , 7, 0, 0, , , ], [38, 1, Filler, , 0, 0, 0, , , ], [39, 8, Product, , 7, 0, 0, , , ], [72, 15, pmg dtl tech key, , 0, 0, 0, , , ], [87, 15, Case Pack id, , 0, 0, 0, , , ], [101, 50, Product Name, , 0, 0, 0, , , ]]')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, Record Type, , 0, 0, 0, , , ], [3, 9, Pack Qty, , 8, 4, 0, , , ], [12, 13, Pack Cost, , 8, 4, 0, , , ], [25, 13, APN, , 7, 0, 0, , , ], [38, 1, Filler, , 0, 0, 0, , , ], [39, 8, Product, , 7, 0, 0, , , ], [72, 15, pmg dtl tech key, , 0, 0, 0, , , ], [87, 15, Case Pack id, , 0, 0, 0, , , ], [101, 50, Product Name, , 0, 0, 0, , , ]]')

		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',6(pmg dtl tech key)')
		assert_p('RecordFieldsJTbl', 'Text', 'pmg dtl tech key', commonBits.fl('FieldName') + ',6')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',7(Case Pack id)')
		assert_p('RecordFieldsJTbl', 'RowCount', '9')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(Pack Cost)')
		assert_p('TextField2', 'Text', 'ZC_AmsPoDownloadComma')
		click('Delete')

		if window(commonBits.fl('Delete: ZC_AmsPoDownloadComma')):
			click('Yes')
		close()

		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		assert_p('TextField2', 'Text', '')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Close')
)

##		select_menu('File>>Exit')
	close()
