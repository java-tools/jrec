useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'zzAms_PODownload_20041231.txt')
		#click('Choose File')

		#if window('Open'):
		#	keystroke('File Name', 'Enter')
		#	select('FilePane$3', 'zzAms_PODownload_20041231.txt')
		#	click('Open')
		#close()

##		select('ComboBox1', 'Ams')
		commonBits.setRecordLayout(select, 'zzAms PO Download')
		click('Edit1')
		click('Filter')
		click('Filter')
## ---
		select('LayoutCombo', 'ams PO Download: Header')
		select('Table', 'cell:3 - 5|Sequence Number,1(45.350)')
		assert_p('Table', 'Text', '45.351', '3 - 5|Sequence Number,2')
		select('Table', 'cell:3 - 5|Sequence Number,1(45.350)')
		assert_p('Table', 'RowCount', '8')
		select('Table', 'cell:1 - 2|Record Type,2(H1)')
		rightclick('Table', '1 - 2|Record Type,2')
		select_menu('Edit Record')
##		select('Table1', 'cell:1 - 2|Record Type,2(H1)')
		select('Table', 'cell:Data,3(222243)')
		assert_p('Table', 'Text', '222243', 'Data,3')
		select('Table', 'cell:Data,9(050102)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, H1, H1], [Sequence Number, 3, 5, 45.351, 45351], [Vendor, 8, 10, 6228, 0000006228], [PO, 18, 12, 222243, 000000222243], [Entry Date, 30, 6, 040909, 040909], [Filler, 36, 8, , ], [beg01 code, 44, 2, 00, 00], [beg02 code, 46, 2, , ], [Department, 48, 4, 200, 200], [Expected Reciept Date, 52, 6, 050102, 050102], [Cancel by date, 58, 6, 050107, 050107], [EDI Type, 68, 1, , ], [Add Date, 69, 6, , ], [Filler, 75, 1, , ], [Department Name, 76, 10, LADIES KNI, LADIES KNI], [Prcoess Type, 86, 1, C, C], [Order Type, 87, 2, FT, FT]]')
		select('Table', 'cell:Data,9(050102)')
		click('Right')
		select('Table', 'cell:Data,1(45.352)')
		assert_p('Table', 'Text', '5341', 'Data,2')
		select('Table', 'cell:Data,1(45.352)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, H1, H1], [Sequence Number, 3, 5, 45.352, 45352], [Vendor, 8, 10, 5341, 0000005341], [PO, 18, 12, 294915, 294915], [Entry Date, 30, 6, 041013, 041013], [Filler, 36, 8, , ], [beg01 code, 44, 2, 00, 00], [beg02 code, 46, 2, , ], [Department, 48, 4, 475, 475], [Expected Reciept Date, 52, 6, 041231, 041231], [Cancel by date, 58, 6, 050107, 050107], [EDI Type, 68, 1, P, P], [Add Date, 69, 6, , ], [Filler, 75, 1, , ], [Department Name, 76, 10, WOMENS SHO, WOMENS SHO], [Prcoess Type, 86, 1, C, C], [Order Type, 87, 2, FT, FT]]')
		select('Table', 'cell:Data,1(45.352)')
		click('Right')
		select('Table', 'cell:Data,3(294987)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, H1, H1], [Sequence Number, 3, 5, 45.353, 45353], [Vendor, 8, 10, 5341, 0000005341], [PO, 18, 12, 294987, 294987], [Entry Date, 30, 6, 041013, 041013], [Filler, 36, 8, , ], [beg01 code, 44, 2, 00, 00], [beg02 code, 46, 2, , ], [Department, 48, 4, 475, 475], [Expected Reciept Date, 52, 6, 041231, 041231], [Cancel by date, 58, 6, 050107, 050107], [EDI Type, 68, 1, P, P], [Add Date, 69, 6, , ], [Filler, 75, 1, , ], [Department Name, 76, 10, WOMENS SHO, WOMENS SHO], [Prcoess Type, 86, 1, C, C], [Order Type, 87, 2, FT, FT]]')
		select('Table', 'cell:Data,3(294987)')
		click('Right')
		select('Table', 'cell:Data,3(295139)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, H1, H1], [Sequence Number, 3, 5, 45.354, 45354], [Vendor, 8, 10, 5341, 0000005341], [PO, 18, 12, 295139, 295139], [Entry Date, 30, 6, 041013, 041013], [Filler, 36, 8, , ], [beg01 code, 44, 2, 00, 00], [beg02 code, 46, 2, , ], [Department, 48, 4, 475, 475], [Expected Reciept Date, 52, 6, 041231, 041231], [Cancel by date, 58, 6, 050107, 050107], [EDI Type, 68, 1, P, P], [Add Date, 69, 6, , ], [Filler, 75, 1, , ], [Department Name, 76, 10, WOMENS SHO, WOMENS SHO], [Prcoess Type, 86, 1, C, C], [Order Type, 87, 2, FT, FT]]')
	close()
