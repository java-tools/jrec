useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		commonBits.selectOldFilemenu(select_menu, 'Utilities', 'Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20041231_Compare.txt')
		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 2, D1, 7.0000, 0.0002, 2222500000000, , 43314531, 2075359, 45614531,  DONKEY 24-006607 SHWL WRAP CARD, , , , , , , , , , ], [, New, 2, , 17.0000, 0.0102, , , , , , , , , , , , , , , , ], [, Old, 3, S1, 5043, 1, 5045, 1, 5076, 1, 5079, 1, 5151, 1, 5072, 1, , 0, , 0, , 0], [, New, 3, , , 10, 9045, 2, , , , , , , , , , , , , , ], [, Old, 4, D1, 4.0000, 148.3200, 0, , 5614944, 2075360, 5614944,  MILK 24-006607 SHWL WRAP CARD, , , , , , , , , , ], [, New, 4, , 14.0000, , , , , , , , , , , , , , , , , ], [, Old, 5, S1, 5045, 1, 5076, 1, 3331, 49440001, , 0, , 0, , 0, , 0, , 0, , 0], [, New, 5, , , 11, , , , , , , , , , , , , , , , ], [, Old, 6, D1, 48.0000, 148.3200, 0, , 55615071, 2075361, 55615071,  M.ROSE 24-006607 SHWL WRAP CARD, , , , , , , , , , ], [, New, 6, , 8.0000, 48.3200, , , , , , , , , , , , , , , , ], [, Old, 7, S1, 5036, 3, 5043, 5, 3331, 50710003, 5065, 4, 5069, 4, 5076, 4, 5079, 2, 5094, 4, 5128, 3], [, New, 7, , , 6, , 51, , , , , , , , , , , , , , ]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Left')
		select('TabbedPane', '')
		select('Table', 'cell:Equivalent Record,0(ams PO Download: Detail)')
		select('Table', ' ', 'Equivalent Record,0')
		select('Table', 'cell:Equivalent Record,0( )')
		select('Table1', 'cell:Field,1(Pack Qty)')
		assert_p('Table', 'Content', '[[ams PO Download: Detail,  ], [ams PO Download: Header, ams PO Download: Header], [ams PO Download: Allocation, ams PO Download: Allocation]]')
		select('Table1', 'cell:Field,1(Pack Qty)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 2, S1, 5043, 1, 5045, 1, 5076, 1, 5079, 1, 5151, 1, 5072, 1, , 0, , 0, , 0], [, New, 2, , , 10, 9045, 2, , , , , , , , , , , , , , ], [, Old, 3, S1, 5045, 1, 5076, 1, 3331, 49440001, , 0, , 0, , 0, , 0, , 0, , 0], [, New, 3, , , 11, , , , , , , , , , , , , , , , ], [, Old, 4, S1, 5036, 3, 5043, 5, 3331, 50710003, 5065, 4, 5069, 4, 5076, 4, 5079, 2, 5094, 4, 5128, 3], [, New, 4, , , 6, , 51, , , , , , , , , , , , , , ]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Left')
		select('TabbedPane', '')
		assert_p('Table1', 'Content', '[[Record Type, ], [Pack Qty, ], [Pack Cost, ], [APN, ], [Filler, ], [Product, ], [pmg dtl tech key, ], [Case Pack id, ], [Product Name, ]]')
		select('Table', 'cell:Equivalent Record,0( )')
		select('Table', 'ams PO Download: Detail', 'Equivalent Record,0')
		select('Table', 'cell:Equivalent Record,0(ams PO Download: Detail)')
		assert_p('Table1', 'Content', '[[Record Type, Record Type], [Pack Qty, Pack Qty], [Pack Cost, Pack Cost], [APN, APN], [Filler, Filler], [Product, Product], [pmg dtl tech key, pmg dtl tech key], [Case Pack id, Case Pack id], [Product Name, Product Name]]')
		select('Table', 'cell:Equivalent Record,0(ams PO Download: Detail)')
		select('Table1', 'cell:Equivalent Field,1(Pack Qty)')
		select('Table', 'cell:Equivalent Record,0(ams PO Download: Detail)')
		
		select('Table1', 'cell:Equivalent Field,1(Pack Qty)')
##		click('ScrollPane$ScrollBar', 8, 51)
		if commonBits.isNimbusLook():
			select('Table1', ' ', 'Equivalent Field,2')
			select('Table1', ' ', 'Equivalent Field,3')
		else:
			select('Table1', 'cell:Equivalent Field,2(Pack Qty)')

			select('Table1', '', 'Equivalent Field,2')
			select('Table1', 'cell:Field,4(Filler)')
##			select('Table1', '', 'Equivalent Field,2')
##			select('Table1', 'cell:Field,4(Filler)')
			
##			select('Table1', '', 'Equivalent Field,2')
##			select('Table1', '', 'Equivalent Field,2')
		select('Table1', 'cell:Field,0(Record Type)')
		assert_p('Table1', 'Content', '[[Record Type, Record Type], [Pack Qty, ], [Pack Cost, ], [APN, APN], [Filler, Filler], [Product, Product], [pmg dtl tech key, pmg dtl tech key], [Case Pack id, Case Pack id], [Product Name, Product Name]]')
		
		select('Table1', 'cell:Field,0(Record Type)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 3, S1, 5043, 1, 5045, 1, 5076, 1, 5079, 1, 5151, 1, 5072, 1, , 0, , 0, , 0], [, New, 3, , , 10, 9045, 2, , , , , , , , , , , , , , ], [, Old, 5, S1, 5045, 1, 5076, 1, 3331, 49440001, , 0, , 0, , 0, , 0, , 0, , 0], [, New, 5, , , 11, , , , , , , , , , , , , , , , ], [, Old, 7, S1, 5036, 3, 5043, 5, 3331, 50710003, 5065, 4, 5069, 4, 5076, 4, 5079, 2, 5094, 4, 5128, 3], [, New, 7, , , 6, , 51, , , , , , , , , , , , , , ]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Left')
		select('TabbedPane', '')


		select('Table', 'cell:Record,2(ams PO Download: Allocation)')

		select('Table1', 'cell:Equivalent Field,2()')
		select('Table1', 'Pack Quantity 1', 'Equivalent Field,2')
##		select('Table1', '')
		select('Table', 'cell:Record,2(ams PO Download: Allocation)')
		select('Table1', 'cell:Equivalent Field,1(DC Number 1)')
		select('Table', 'cell:Record,2(ams PO Download: Allocation)')

		if commonBits.isNimbusLook():
			select('Table1', ' ', 'Equivalent Field,1')
			select('Table1', ' ', 'Equivalent Field,2')
		else:
			select('Table1', '', 'Equivalent Field,1')
			select('Table1', '', 'Equivalent Field,2')
		select('Table1', 'cell:Field,3(DC Number 2)')
		assert_p('Table1', 'Content', '[[Record Type, Record Type], [DC Number 1, ], [Pack Quantity 1, ], [DC Number 2, DC Number 2], [Pack Quantity 2, Pack Quantity 2], [DC Number 4, DC Number 4], [Pack Quantity 4, Pack Quantity 4], [DC Number 5, DC Number 5], [Pack Quantity 5, Pack Quantity 5], [DC Number 6, DC Number 6], [Pack Quantity 6, Pack Quantity 6], [DC Number 7, DC Number 7], [Pack Quantity 7, Pack Quantity 7], [DC Number 8, DC Number 8], [Pack Quantity 8, Pack Quantity 8], [DC Number 9, DC Number 9], [Pack Quantity 9, Pack Quantity 9], [DC Number 10, DC Number 10], [Pack Quantity 10, Pack Quantity 10]]')
		select('Table1', 'cell:Field,3(DC Number 2)')
		assert_p('Table', 'Content', '[[ams PO Download: Detail, ams PO Download: Detail], [ams PO Download: Header, ams PO Download: Header], [ams PO Download: Allocation, ams PO Download: Allocation]]')
		select('Table1', 'cell:Field,3(DC Number 2)')

		select('Table', 'cell:Equivalent Record,0(ams PO Download: Detail)')

#		select('Table1', 'Pack Qty', 'Equivalent Field,1')
		select('Table1', 'Pack Cost', 'Equivalent Field,2')

		click('Right')
		select('TabbedPane', '')
		click('Compare')
##		assert_p('Table', 'Content', '[[, Old, 2, D1, 0.0002, 2222500000000, , 43314531, 2075359, 45614531,  DONKEY 24-006607 SHWL WRAP CARD, , , , , , , , , ], [, New, 2, , 0.0102, , , , , , , , , , , , , , , ], [, Old, 3, S1, 5045, 1, 5076, 1, 5079, 1, 5151, 1, 5072, 1, , 0, , 0, , 0], [, New, 3, , 9045, 2, , , , , , , , , , , , , , ], [, Old, 6, D1, 148.3200, 0, , 55615071, 2075361, 55615071,  M.ROSE 24-006607 SHWL WRAP CARD, , , , , , , , , ], [, New, 6, , 48.3200, , , , , , , , , , , , , , , ], [, Old, 7, S1, 5043, 5, 3331, 50710003, 5065, 4, 5069, 4, 5076, 4, 5079, 2, 5094, 4, 5128, 3], [, New, 7, , , 51, , , , , , , , , , , , , , ]]')
		assert_p('Table', 'Content', '[[, Old, 2, D1, 0.0002, 2222500000000, , 43314531, 2075359, 45614531,  DONKEY 24-006607 SHWL WRAP CARD, , , , , , , , , ], [, New, 2, , 0.0102, , , , , , , , , , , , , , , ], [, Old, 3, S1, 5045, 1, 5076, 1, 5079, 1, 5151, 1, 5072, 1, , 0, , 0, , 0], [, New, 3, , 9045, 2, , , , , , , , , , , , , , ], [, Old, 6, D1, 148.3200, 0, , 55615071, 2075361, 55615071,  M.ROSE 24-006607 SHWL WRAP CARD, , , , , , , , , ], [, New, 6, , 48.3200, , , , , , , , , , , , , , , ], [, Old, 7, S1, 5043, 5, 3331, 50710003, 5065, 4, 5069, 4, 5076, 4, 5079, 2, 5094, 4, 5128, 3], [, New, 7, , , 51, , , , , , , , , , , , , , ]]')
	close()
