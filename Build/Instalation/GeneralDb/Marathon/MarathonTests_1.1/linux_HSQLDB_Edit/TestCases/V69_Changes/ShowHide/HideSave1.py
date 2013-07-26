useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() +  'Ams_PODownload_20041231.txt')
		commonBits.doEdit(click)
		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Show / Hide Fields'))
		select('Table', 'cell:' + commonBits.fl('Show') + ',3(true)')
		select('Table', 'cell:' + commonBits.fl('Show') + ',4(true)')
		select('Table', 'cell:' + commonBits.fl('Show') + ',7(true)')
		select('Table', 'cell:' + commonBits.fl('Show') + ',8(true)')
		click(commonBits.fl('Go'))
		select('LayoutCombo', 'ams PO Download: Detail')
##		select('Table', '')
		rightclick('Table', '38 - 1|Filler,2')
		select_menu(commonBits.fl('Hide Column'))
##		select('Table', '')
		rightclick('Table', '72 - 15|pmg dtl tech key,2')
		select_menu(commonBits.fl('Hide Column'))
##		select('Table', '')
		rightclick('Table', '3 - 9|Pack Qty,0')
		select_menu(commonBits.fl('Edit Record'))
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Show / Hide Fields'))
		commonBits.save(click)
		##select('FileChooser', commonBits.CobolCopybookDir() + 't1Hide.xml')
		commonBits.selectFileName(select, keystroke, commonBits.CobolCopybookDir() + 't1Hide.xml')
		commonBits.save1(click)
##		select_menu(commonBits.fl('Window') + '>>Menu')
##		click('MetalInternalFrameTitlePane', 215, 10)
##commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
##		click('MetalInternalFrameTitlePane', 458, 5)
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Table:'))
##		select('Table', '')
		rightclick('Table', '1 - 2|Record Type,0')
		select_menu(commonBits.fl('Edit Record'))
		assert_p('Table', 'Content', '[[Record Type, 1, 2, H1, H1], [Sequence Number, 3, 5, 45.349, 45349], [Vendor, 8, 10, 6060, 0000006060], [PO, 18, 12, 286225, 286225], [Entry Date, 30, 6, 040909, 040909], [Filler, 36, 8, , ], [beg01 code, 44, 2, 00, 00], [beg02 code, 46, 2, , ], [Department, 48, 4, 200, 200], [Expected Reciept Date, 52, 6, 050102, 050102], [Cancel by date, 58, 6, 050107, 050107], [EDI Type, 68, 1, , ], [Add Date, 69, 6, , ], [Filler, 75, 1, , ], [Department Name, 76, 10, LADIES KNI, LADIES KNI], [Prcoess Type, 86, 1, C, C], [Order Type, 87, 2, FT, FT]]')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Load Saved Hidden Fields'))
		##select('FileChooser', commonBits.CobolCopybookDir() + 't1Hide.xml')
		
		commonBits.selectFileName(select, keystroke, commonBits.CobolCopybookDir() + 't1Hide.xml')
		click(commonBits.fl('Run')
)
		select('Table', 'cell:' + commonBits.fl('Data') + ',3(286225)')
		select_menu(commonBits.fl('Window') + '>>Ams_PODownload_20041231.txt>>' + commonBits.fl('Record:'))
		select('Table', 'cell:' + commonBits.fl('Data') + ',2(6060)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, H1, H1], [Sequence Number, 3, 5, 45.349, 45349], [Vendor, 8, 10, 6060, 0000006060], [PO, 18, 12, 286225, 286225], [Entry Date, 30, 6, 040909, 040909], [Filler, 36, 8, , ], [beg01 code, 44, 2, 00, 00], [beg02 code, 46, 2, , ], [Department, 48, 4, 200, 200], [Expected Reciept Date, 52, 6, 050102, 050102], [Cancel by date, 58, 6, 050107, 050107], [EDI Type, 68, 1, , ], [Add Date, 69, 6, , ], [Filler, 75, 1, , ], [Department Name, 76, 10, LADIES KNI, LADIES KNI], [Prcoess Type, 86, 1, C, C], [Order Type, 87, 2, FT, FT]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',2(6060)')
		click('Right')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, D1, D1], [Pack Qty, 3, 9, 7.0000, 000070000], [Pack Cost, 12, 13, 0.0002, 0000000000002], [APN, 25, 13, 2222500000000, 2222500000000], [Product, 39, 8, 43314531, 43314531], [Case Pack id, 87, 15, 45614531, 45614531], [Product Name, 101, 50,  DONKEY 24-006607 SHWL WRAP CARD,  DONKEY 24-006607 SHWL WRAP CARD]]')
		click('Right')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, S1, S1], [DC Number 1, 3, 4, 5043, 5043], [Pack Quantity 1, 7, 8, 1, 00000001], [DC Number 4, 39, 4, 5076, 5076], [Pack Quantity 4, 43, 8, 1, 00000001], [DC Number 6, 63, 4, 5151, 5151], [Pack Quantity 6, 67, 8, 1, 00000001], [DC Number 7, 75, 4, 5072, 5072], [Pack Quantity 7, 79, 8, 1, 00000001], [DC Number 8, 87, 4, , ], [Pack Quantity 8, 91, 8, 0, 00000000], [DC Number 9, 99, 4, , ], [Pack Quantity 9, 103, 8, 0, 00000000], [DC Number 10, 111, 4, , ], [Pack Quantity 10, 115, 8, 0, 00000000]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
