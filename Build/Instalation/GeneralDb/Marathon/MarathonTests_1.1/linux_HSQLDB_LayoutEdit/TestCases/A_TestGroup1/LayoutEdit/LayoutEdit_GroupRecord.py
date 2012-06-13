useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*')
		select('TextField', 'ams PO Download%')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		select('BmComboBox', 'Yes')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Child Records')
		select('Table', 'cell:Description,0()')
##		select('ChildRecordsJTbl', 'cell:,1(0)')
##		assert_p('ChildRecordsJTbl', 'Text', '0', ',1')
##		select('ChildRecordsJTbl', 'cell:,2(0)')
		if commonBits.isVersion80():
#			assert_p('ChildRecordsJTbl', 'Content', '[[, 353, 0, Record Type, D1, , -1], [, 356, 0, Record Type, H1, , -1], [, 357, 0, Record Type, S1, , -1]]')
##			assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Detail, 0, Record Type, D1, , ], [, ams PO Download: Header, 0, Record Type, H1, , ], [, ams PO Download: Allocation, 0, Record Type, S1, , ]]')

			assert_p('ChildRecordsJTbl', 'Content', '[[, ams PO Download: Detail, , Record Type, D1, , ], [, ams PO Download: Header, , Record Type, H1, , ], [, ams PO Download: Allocation, , Record Type, S1, , ]]')


		else:
			assert_p('ChildRecordsJTbl', 'Content', '[[, 353, 0, Record Type, D1, -1], [, 356, 0, Record Type, H1, -1], [, 357, 0, Record Type, S1, -1]]')
#		select('ChildRecordsJTbl', 'cell:,0(0)')
		assert_p('ChildRecordsJTbl', 'RowCount', '3')
#		select('ChildRecordsJTbl', 'cell:,0(0)')
		click('Edit Child')
		select('RecordFieldsJTbl', 'cell:FieldName,0(Record Type)')
		assert_p('RecordFieldsJTbl', 'Text', 'Record Type', 'FieldName,0')
		select('RecordFieldsJTbl', 'cell:FieldName,1(Pack Qty)')
		if commonBits.isMsAccess():
			assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, Record Type, , 0, 0, 15, Ams PO Download, , ], [3, 9, Pack Qty, , 8, 4, 0, , , ], [12, 13, Pack Cost, , 8, 4, 0, , , ], [25, 13, APN, , 7, 0, 0, , , ], [38, 1, Filler, , 0, 0, 0, , , ], [39, 8, Product, , 7, 0, 0, , , ], [72, 15, pmg dtl tech key, , 0, 0, 0, , , ], [87, 15, Case Pack id, , 0, 0, 0, , , ], [101, 50, Product Name, , 0, 0, 0, , , ]]')
		else:
			assert_p('RecordFieldsJTbl', 'Content', '[[1, 2, Record Type, , 0, 0, 0, , , ], [3, 9, Pack Qty, , 8, 4, 0, , , ], [12, 13, Pack Cost, , 8, 4, 0, , , ], [25, 13, APN, , 7, 0, 0, , , ], [38, 1, Filler, , 0, 0, 0, , , ], [39, 8, Product, , 7, 0, 0, , , ], [72, 15, pmg dtl tech key, , 0, 0, 0, , , ], [87, 15, Case Pack id, , 0, 0, 0, , , ], [101, 50, Product Name, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:FieldName,4(Filler)')
		assert_p('RecordFieldsJTbl', 'RowCount', '9')
		select('RecordFieldsJTbl', 'cell:FieldName,5(Product)')
		assert_p('RecordFieldsJTbl', 'ColumnCount', '10')
		select('RecordFieldsJTbl', 'cell:FieldName,5(Product)')
		click('Right')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:FieldName,1(Sequence Number)')
		assert_p('RecordFieldsJTbl', 'Text', 'cell:FieldName,1(Sequence Number)')
		select('RecordFieldsJTbl', 'cell:FieldName,2(Vendor)')
		assert_p('RecordFieldsJTbl', 'Text', 'Vendor', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:FieldName,2(Vendor)')
		click('Right')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		click('Left')
		select('TabbedPane', 'Extras')
		select('TabbedPane', 'Fields')
		select('RecordFieldsJTbl', 'cell:FieldName,1(Sequence Number)')
		assert_p('RecordFieldsJTbl', 'Text', 'cell:FieldName,1(Sequence Number)')
		select('RecordFieldsJTbl', 'cell:FieldName,1(Sequence Number)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
