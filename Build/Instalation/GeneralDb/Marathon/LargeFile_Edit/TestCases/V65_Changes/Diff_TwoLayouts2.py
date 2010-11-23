useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		select_menu('File>>Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		commonBits.setRecordLayout(select, 'ams PO Download')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser',  commonBits.sampleDir() +  'xmlAms_PODownload_20041231.txt.xml')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:Equivalent Record,0(-1)')
		select('Table', 'ams_PO_Download__Detail', 'Equivalent Record,0')
		select('Table', 'ams_PO_Download__Header', 'Equivalent Record,1')
		select('Table', 'ams_PO_Download__Allocation', 'Equivalent Record,2')
		select('Table', 'cell:Equivalent Record,0(3)')
		select('Table1', 'cell:Equivalent Field,0()')
		select('Table', 'cell:Equivalent Record,0(3)')
		select('Table1', 'Record_Type', 'Equivalent Field,0')
		select('Table1', 'Pack_Qty', 'Equivalent Field,1')
		select('Table1', 'Pack_Cost', 'Equivalent Field,2')
		select('Table1', 'APN', 'Equivalent Field,3')
		select('Table1', 'cell:Equivalent Field,5()')
#		click('ScrollPane$ScrollBar', 6, 40)
		select('Table1', '', 'Equivalent Field,5')
#		select('Table1', '')
		select('Table', 'cell:Record,1(ams PO Download: Header)')
		select('Table1', 'cell:Equivalent Field,0()')
		select('Table', 'cell:Record,1(ams PO Download: Header)')
		select('Table1', 'Record_Type', 'Equivalent Field,0')
		select('Table1', 'Sequence_Number', 'Equivalent Field,1')
		select('Table1', 'Vendor', 'Equivalent Field,2')
		select('Table1', 'PO', 'Equivalent Field,3')
		select('Table1', 'cell:Field,2(Vendor)')
###		assert_p('Table1', 'Content', '[[Record Type, Record_Type], [Sequence Number, Sequence_Number], [Vendor, Vendor], [PO, PO], [Entry Date, ], [Filler, ], [beg01 code, ], [beg02 code, ], [Department, ], [Expected Reciept Date, ], [Cancel by date, ], [EDI Type, ], [Add Date, ], [Filler, ], [Department Name, ], [Prcoess Type, ], [Order Type, ]]')
		assert_p('Table1', 'Content', '[[Record Type, Record_Type], [Sequence Number, Sequence_Number], [Vendor, Vendor], [PO, PO], [Entry Date, Entry_Date], [Filler, ], [beg01 code, beg01_code], [beg02 code, ], [Department, Department], [Expected Reciept Date, Expected_Reciept_Date], [Cancel by date, Cancel_by_date], [EDI Type, EDI_Type], [Add Date, ], [Filler, ], [Department Name, Department_Name], [Prcoess Type, Prcoess_Type], [Order Type, Order_Type]]')
#		select('Table1', '')
		select('Table', 'cell:Record,2(ams PO Download: Allocation)')
##		assert_p('Table', 'Content', '[[ams PO Download: Detail, 3], [ams PO Download: Header, 2], [ams PO Download: Allocation, 4]]')
		assert_p('Table', 'Content', '[[ams PO Download: Detail, ams_PO_Download__Detail], [ams PO Download: Header, ams_PO_Download__Header], [ams PO Download: Allocation, ams_PO_Download__Allocation]]')

		select('Table', 'cell:Record,2(ams PO Download: Allocation)')
		select('Table1', 'cell:Equivalent Field,0()')
		select('Table', 'cell:Record,2(ams PO Download: Allocation)')
		select('Table1', 'Record_Type', 'Equivalent Field,0')
		select('Table1', 'DC_Number_1', 'Equivalent Field,1')
		select('Table1', 'Pack_Quantity_1', 'Equivalent Field,2')
		select('Table1', 'DC_Number_2', 'Equivalent Field,3')
		select('Table1', 'Pack_Quantity_2', 'Equivalent Field,4')
		select('Table1', 'cell:Field,3(DC Number 2)')
##		assert_p('Table1', 'Content', '[[Record Type, Record_Type], [DC Number 1, DC_Number_1], [Pack Quantity 1, Pack_Quantity_1], [DC Number 2, DC_Number_2], [Pack Quantity 2, Pack_Quantity_2], [DC Number 4, ], [Pack Quantity 4, ], [DC Number 5, ], [Pack Quantity 5, ], [DC Number 6, ], [Pack Quantity 6, ], [DC Number 7, ], [Pack Quantity 7, ], [DC Number 8, ], [Pack Quantity 8, ], [DC Number 9, ], [Pack Quantity 9, ], [DC Number 10, ], [Pack Quantity 10, ]]')
		assert_p('Table1', 'Content', '[[Record Type, Record_Type], [DC Number 1, DC_Number_1], [Pack Quantity 1, Pack_Quantity_1], [DC Number 2, DC_Number_2], [Pack Quantity 2, Pack_Quantity_2], [DC Number 4, DC_Number_4], [Pack Quantity 4, Pack_Quantity_4], [DC Number 5, DC_Number_5], [Pack Quantity 5, Pack_Quantity_5], [DC Number 6, DC_Number_6], [Pack Quantity 6, Pack_Quantity_6], [DC Number 7, DC_Number_7], [Pack Quantity 7, Pack_Quantity_7], [DC Number 8, DC_Number_8], [Pack Quantity 8, Pack_Quantity_8], [DC Number 9, DC_Number_9], [Pack Quantity 9, Pack_Quantity_9], [DC Number 10, DC_Number_10], [Pack Quantity 10, Pack_Quantity_10]]')
		select('Table1', 'cell:Field,3(DC Number 2)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		select('Table', 'cell:Pack Qty,0(5043)')
		assert_p('Table', 'Text', '5043', 'Pack Qty,0')
		select('Table', 'cell:Pack Qty,2(4.0000)')
##		assert_p('Table', 'Content', '[[, Old, 3, S1, 5043, 1, 5045, 1], [, New, 3, , , , , 11], [, Old, 4, D1, 4.0000, 148.3200, 0, 5614944], [, New, 4, , , , 111, ]]')
##		assert_p('Table', 'Content', '[[, Old, 3, S1, 5043, 1, 5045, 1], [, New, 3, , , , , 11], [, Old, 4, D1, 4.0000, 148.3200, 0, ], [, New, 4, , , , 111, ]]')
##		assert_p('Table', 'Content', '[[, Old, 3, S1, 5043, 1, 5045, 1], [, New, 3, , , , , 11], [, Old, 4, D1, 4.0000, 148.3200, 0, ], [, New, 4, , , , 111, ], [, Old, 12, D1, 16.0000, 6228148.3200, 2222, ], [, New, 12, , , 62281483200, , ]]')
		assert_p('Table', 'Content', '[[, Old, 3, S1, 5043, 1, 5045, 1, 5076, 1, 5079, 1, 5151, 1, 5072, 1, , 0, , 0, , 0], [, New, 3, , , , , 11, , 12, , , , , , , , , , , , ], [, Old, 4, D1, 4.0000, 148.3200, 0, 2075360, 5614944,  MILK 24-006607 SHWL WRAP CARD, , , , , , , , , , , , ], [, New, 4, , , , 111, , , , , , , , , , , , , , , ], [, Old, 5, S1, 5045, 1, 5076, 1, 3331, 49440001, , 0, , 0, , 0, , 0, , 0, , 0], [, New, 5, , , , , , , , , 22, , 33, , , , , , , , ], [, Old, 12, D1, 16.0000, 6228148.3200, 2222, 2075348, 5614531,  DONKEY 24-006607 SHWL WRAP CARD, , , , , , , , , , , , ], [, New, 12, , , 62281483200, , , , , , , , , , , , , , , , ]]')
		select('Table', 'cell:Pack Qty,2(4.0000)')
		click('All Included Lines')
		select('Table', 'cell:Pack Qty,2(7.0000)')
		assert_p('Table', 'Text', '7.0000', 'Pack Qty,2')
		select('Table', 'cell:Pack Qty,2(7.0000)')
		assert_p('Table', 'RowCount', '140')
		select('Table', 'cell:Pack Qty,10(48.0000)')
		assert_p('Table', 'Text', 'cell:Pack Qty,10(48.0000)')
		select('Table', 'cell:Pack Qty,10(48.0000)')
		click('ScrollPane$ScrollBar', 3, 124)
##		select('Table', 'cell:Product,6(5614944)')
##		assert_p('Table', 'Text', '1', 'Product,8')
	close()
