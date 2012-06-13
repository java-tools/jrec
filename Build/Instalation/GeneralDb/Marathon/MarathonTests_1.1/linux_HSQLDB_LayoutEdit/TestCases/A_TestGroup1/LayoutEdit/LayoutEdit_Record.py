useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*')
		select('TextField', 'ams PO Download: Detail%')
		select('TextField1', '%')

		assert_p('Label7', 'Text', 'Description')
		select('RecordFieldsJTbl', 'cell:FieldName,0(Record Type)')
		rightclick('RecordFieldsJTbl', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:FieldName,0(Record Type)')
		assert_p('RecordFieldsJTbl', 'Text', 'Record Type', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:FieldName,1(Pack Qty)')
		assert_p('RecordFieldsJTbl', 'Text', 'cell:FieldName,1(Pack Qty)')
		select('RecordFieldsJTbl', 'cell:FieldName,2(Pack Cost)')
		if commonBits.isMsAccess():
			assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, Record Type, , 0, 0, 15, Ams PO Download, , ], [3, 9, Pack Qty, , 8, 4, 0, , , ], [12, 13, Pack Cost, , 8, 4, 0, , , ], [25, 13, APN, , 7, 0, 0, , , ], [38, 1, Filler, , 0, 0, 0, , , ], [39, 8, Product, , 7, 0, 0, , , ], [72, 15, pmg dtl tech key, , 0, 0, 0, , , ], [87, 15, Case Pack id, , 0, 0, 0, , , ], [101, 50, Product Name, , 0, 0, 0, , , ]]')
		else:
			assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, Record Type, , 0, 0, 0, , , ], [3, 9, Pack Qty, , 8, 4, 0, , , ], [12, 13, Pack Cost, , 8, 4, 0, , , ], [25, 13, APN, , 7, 0, 0, , , ], [38, 1, Filler, , 0, 0, 0, , , ], [39, 8, Product, , 7, 0, 0, , , ], [72, 15, pmg dtl tech key, , 0, 0, 0, , , ], [87, 15, Case Pack id, , 0, 0, 0, , , ], [101, 50, Product Name, , 0, 0, 0, , , ]]')
			assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, Record Type, , 0, 0, 0, , , ], [3, 9, Pack Qty, , 8, 4, 0, , , ], [12, 13, Pack Cost, , 8, 4, 0, , , ], [25, 13, APN, , 7, 0, 0, , , ], [38, 1, Filler, , 0, 0, 0, , , ], [39, 8, Product, , 7, 0, 0, , , ], [72, 15, pmg dtl tech key, , 0, 0, 0, , , ], [87, 15, Case Pack id, , 0, 0, 0, , , ], [101, 50, Product Name, , 0, 0, 0, , , ]]')
#		assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, Record Type, , 0, 0, 0, , , ], [3, 9, Pack Qty, , 8, 4, 0, , , ], [12, 13, Pack Cost, , 8, 4, 0, , , ], [25, 13, APN, , 7, 0, 0, , , ], [38, 1, Filler, , 0, 0, 0, , , ], [39, 8, Product, , 7, 0, 0, , , ], [72, 15, pmg dtl tech key, , 0, 0, 0, , , ], [87, 15, Case Pack id, , 0, 0, 0, , , ], [101, 50, Product Name, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:FieldName,3(APN)')
		assert_p('RecordFieldsJTbl', 'RowCount', '9')
		select('RecordFieldsJTbl', 'cell:FieldName,3(APN)')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:FieldName,3(APN)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
