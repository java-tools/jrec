useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
##		click('Choose File')

##		if window('Open'):
##			select('FilePane$3', 'Ams_PODownload_20041231.txt')
##			click('Open')
##		close()

##		select('ComboBox2', 'zzAms PO Download')

		select('FileChooser', commonBits.sampleDir() + 'zzAms_PODownload_20041231.txt')
		commonBits.setRecordLayout(select, 'zzAms PO Download')

		click('Edit1')
		select('Table', 'cell:3 - 4|DC Number 1,0(4534)')
		rightclick('Table', '3 - 4|DC Number 1,0')
##		select('Table', 'cell:3 - 4|DC Number 1,0(4534)')
		select_menu('Edit Record')
##		select('Table1', 'cell:3 - 4|DC Number 1,0(4534)')
		select('Table', 'cell:Data,1(45.349)')
		assert_p('Table', 'Text', '45.349', 'Data,1')
		select('Table', 'cell:Data,3(286225)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, H1, H1], [Sequence Number, 3, 5, 45.349, 45349], [Vendor, 8, 10, 6060, 0000006060], [PO, 18, 12, 286225, 286225], [Entry Date, 30, 6, 040909, 040909], [Filler, 36, 8, , ], [beg01 code, 44, 2, 00, 00], [beg02 code, 46, 2, , ], [Department, 48, 4, 200, 200], [Expected Reciept Date, 52, 6, 050102, 050102], [Cancel by date, 58, 6, 050107, 050107], [EDI Type, 68, 1, , ], [Add Date, 69, 6, , ], [Filler, 75, 1, , ], [Department Name, 76, 10, LADIES KNI, LADIES KNI], [Prcoess Type, 86, 1, C, C], [Order Type, 87, 2, FT, FT]]')
		select('Table', 'cell:Data,4(040909)')
		assert_p('Table', 'RowCount', '17')
		select('Table', 'cell:Data,4(040909)')
		assert_p('LayoutCombo', 'Text', 'ams PO Download: Header')
		click('Right')
		select('Table', 'cell:Data,5(00 43314)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, D1, D1], [Sequence Number, 3, 5, 0.007, 00007], [Vendor, 8, 10, 0, 0000000000], [PO, 18, 12, 222225, 000000222225], [Entry Date, 30, 6, 000000, 000000], [Filler, 36, 8, 00 43314, 00 43314], [beg01 code, 44, 2, 53, 53], [beg02 code, 46, 2, 10, 10], [Department, 48, 4, 0000, 0000], [Expected Reciept Date, 52, 6, 005454, 005454], [Cancel by date, 58, 6, 000000, 000000], [EDI Type, 68, 1, , ], [Add Date, 69, 6,    207,    207], [Filler, 75, 1, 5, 5], [Department Name, 76, 10, 359, 359], [Prcoess Type, 86, 1, , ], [Order Type, 87, 2, 45, 45]]')
		select('Table', 'cell:Data,5(00 43314)')
		assert_p('LayoutCombo', 'Text', 'ams PO Download: Header')

		click('Right')
		select('Table', 'cell:Data,5(5076)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, S1, S1], [DC Number 1, 3, 4, 5043, 5043], [Pack Quantity 1, 7, 8, 1, 00000001], [DC Number 2, 15, 4, 5045, 5045], [Pack Quantity 2, 19, 8, 1, 00000001], [DC Number 4, 39, 4, 5076, 5076], [Pack Quantity 4, 43, 8, 1, 00000001], [DC Number 5, 51, 4, 5079, 5079], [Pack Quantity 5, 55, 8, 1, 00000001], [DC Number 6, 63, 4, 5151, 5151], [Pack Quantity 6, 67, 8, 1, 00000001], [DC Number 7, 75, 4, 5072, 5072], [Pack Quantity 7, 79, 8, 1, 00000001], [DC Number 8, 87, 4, , ], [Pack Quantity 8, 91, 8, 0, 00000000], [DC Number 9, 99, 4, , ], [Pack Quantity 9, 103, 8, 0, 00000000], [DC Number 10, 111, 4, , ], [Pack Quantity 10, 115, 8, 0, 00000000]]')
		select('Table', 'cell:Data,5(5076)')
		assert_p('LayoutCombo', 'Content', '[[ams PO Download: Detail, ams PO Download: Header, ams PO Download: Allocation, Full Line]]')
		click('Right')
		select('Table', 'cell:Data,6(49440000)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, D1, D1], [DC Number 1, 3, 4, 0, 0000], [Pack Quantity 1, 7, 8, 40000000, 40000000], [DC Number 2, 15, 4, 1, 0001], [Pack Quantity 2, 19, 8, 48320000, 48320000], [DC Number 4, 39, 4, 0561, 0561], [Pack Quantity 4, 43, 8, 49440000, 49440000], [DC Number 5, 51, 4, 5, 0005], [Pack Quantity 5, 55, 8, 45400000, 45400000], [DC Number 6, 63, 4, 4, 04], [Pack Quantity 6, 67, 8, 207,      207], [DC Number 7, 75, 4, 5360, 5360], [Pack Quantity 7, 79, 8, , ], [DC Number 8, 87, 4, 5614, 5614], [Pack Quantity 8, 91, 8, 944, 944], [DC Number 9, 99, 4, M,    M], [Pack Quantity 9, 103, 8, ILK 24-0, ILK 24-0], [DC Number 10, 111, 4, 660, 0660], [Pack Quantity 10, 115, 8, 7 SHWL W, 7 SHWL W]]')
		select('Table', 'cell:Data,6(49440000)')
		assert_p('LayoutCombo', 'Text', 'ams PO Download: Allocation')
		click('Right')
		select('Table', 'cell:Data,6(49440001)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, S1, S1], [DC Number 1, 3, 4, 5045, 5045], [Pack Quantity 1, 7, 8, 1, 00000001], [DC Number 2, 15, 4, 5076, 5076], [Pack Quantity 2, 19, 8, 1, 00000001], [DC Number 4, 39, 4, 3331, 3331], [Pack Quantity 4, 43, 8, 49440001, 49440001], [DC Number 5, 51, 4, , ], [Pack Quantity 5, 55, 8, 0, 00000000], [DC Number 6, 63, 4, , ], [Pack Quantity 6, 67, 8, 0, 00000000], [DC Number 7, 75, 4, , ], [Pack Quantity 7, 79, 8, 0, 00000000], [DC Number 8, 87, 4, , ], [Pack Quantity 8, 91, 8, 0, 00000000], [DC Number 9, 99, 4, , ], [Pack Quantity 9, 103, 8, 0, 00000000], [DC Number 10, 111, 4, , ], [Pack Quantity 10, 115, 8, 0, 00000000]]')
		select('Table', 'cell:Data,6(49440001)')
		click('Right')
		select('Table', 'cell:Data,0(D1)')
		assert_p('Table', 'Text', '4', 'Data,1')
		select('Table', 'cell:Data,4(48320000)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, D1, D1], [DC Number 1, 3, 4, 4, 0004], [Pack Quantity 1, 7, 8, 80000000, 80000000], [DC Number 2, 15, 4, 1, 0001], [Pack Quantity 2, 19, 8, 48320000, 48320000], [DC Number 4, 39, 4, 5561, 5561], [Pack Quantity 4, 43, 8, 50710000, 50710000], [DC Number 5, 51, 4, 5, 0005], [Pack Quantity 5, 55, 8, 45400000, 45400000], [DC Number 6, 63, 4, 48, 48], [Pack Quantity 6, 67, 8, 207,      207], [DC Number 7, 75, 4, 5361, 5361], [Pack Quantity 7, 79, 8, , ], [DC Number 8, 87, 4, 5561, 5561], [Pack Quantity 8, 91, 8, 5071, 5071], [DC Number 9, 99, 4, M,    M], [Pack Quantity 9, 103, 8, .ROSE 24, .ROSE 24], [DC Number 10, 111, 4, -6, -006], [Pack Quantity 10, 115, 8, 607 SHWL, 607 SHWL]]')
		select('Table', 'cell:Data,4(48320000)')
		assert_p('LayoutCombo', 'Text', 'ams PO Download: Allocation')
		click('Right')
		select('Table', 'cell:Data,0(S1)')
		assert_p('Table', 'Text', '5036', 'Data,1')
		select('Table', 'cell:Data,3(5043)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, S1, S1], [DC Number 1, 3, 4, 5036, 5036], [Pack Quantity 1, 7, 8, 3, 00000003], [DC Number 2, 15, 4, 5043, 5043], [Pack Quantity 2, 19, 8, 5, 00000005], [DC Number 4, 39, 4, 3331, 3331], [Pack Quantity 4, 43, 8, 50710003, 50710003], [DC Number 5, 51, 4, 5065, 5065], [Pack Quantity 5, 55, 8, 4, 00000004], [DC Number 6, 63, 4, 5069, 5069], [Pack Quantity 6, 67, 8, 4, 00000004], [DC Number 7, 75, 4, 5076, 5076], [Pack Quantity 7, 79, 8, 4, 00000004], [DC Number 8, 87, 4, 5079, 5079], [Pack Quantity 8, 91, 8, 2, 00000002], [DC Number 9, 99, 4, 5094, 5094], [Pack Quantity 9, 103, 8, 4, 00000004], [DC Number 10, 111, 4, 5128, 5128], [Pack Quantity 10, 115, 8, 3, 00000003]]')
		select('Table', 'cell:Data,3(5043)')
		assert_p('LayoutCombo', 'Text', 'ams PO Download: Allocation')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')

		click('Open')
##		click('Choose File')

##		if window('Open'):
##			select('FilePane$3', 'Ams_PODownload_20041231.txt')
##			click('Open')
##		close()

##		click('ScrollPane$ScrollBar', 16, 121)
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
##		select('ComboBox2', 'ams PO Download')

		commonBits.setRecordLayout(select, 'ams PO Download')
		click('Edit1')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')

	close()
