useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20050101.txt')
		click('Edit1')
		click('Filter1')
		select('Table', 'false', 'Include,0')
		select('Table', 'false', 'Include,2')
		#select('Table', 'cell:Include,2(false)')
		click('Filter1')
		select('Table', 'cell:3 - 5|Sequence Number,1(45.358)')
		assert_p('Table', 'Content', '[[H1, 45.357, 4338, 233863, 040929, , 00, , 290, 050103, 050107, , , , OPTIONS PL, C, FT], [H1, 45.358, 4338, 233872, 040929, , 00, , 290, 050103, 050107, , , , OPTIONS PL, C, FT], [H1, 45.359, 4468, 255906, 040929, , 00, , 290, 050103, 050107, , , , OPTIONS PL, C, FT], [H1, 45.360, 4448, 290908, 040929, , 00, , 290, 050103, 050107, , , , OPTIONS PL, C, FT], [H1, 45.361, 4228, 292210, 040929, , 00, , 290, 050103, 050107, , , , OPTIONS PL, C, FT], [H1, 45.362, 5220, 211984, 040929, , 00, , 200, 050103, 050107, , , , LADIES KNI, C, FT], [H1, 45.363, 5110, 211985, 040929, , 00, , 200, 050103, 050107, , , , LADIES KNI, C, FT], [H1, 45.364, 5110, 211987, 040929, , 00, , 200, 050103, 050106, , , , LADIES KNI, C, FT], [H1, 45.365, 13112, 211549, 041005, , 00, , 220, 050103, 050108, , , , LADIES KNI, C, FT], [H1, 45.366, 13312, 211555, 041005, , 00, , 220, 050103, 050107, , , , LADIES KNI, C, FT], [H1, 45.367, 12212, 222556, 041005, , 00, , 220, 050103, 050107, , , , LADIES KNI, C, FT], [H1, 45.368, 1312, 211617, 041005, , 00, , 220, 050103, 050117, , , , LADIES KNI, C, FT]]')
		select('Table', 'cell:8 - 10|Vendor,5(5220)')
		assert_p('Table', 'Text', '5220', '8 - 10|Vendor,5')
		select('Table', 'cell:8 - 10|Vendor,6(5110)')
		assert_p('Table', 'RowCount', '12')
		select('Table', 'cell:3 - 5|Sequence Number,7(45.364)')
		rightclick('Table', '3 - 5|Sequence Number,7')
		select_menu('Edit Record')
	##	select('Table1', 'cell:3 - 5|Sequence Number,7(45.364)')
		select('Table', 'cell:Data,3(211987)')
		assert_p('Table', 'Content', '[[Record Type, 1, 2, H1, H1], [Sequence Number, 3, 5, 45.364, 45364], [Vendor, 8, 10, 5110, 0000005110], [PO, 18, 12, 211987, 000000211987], [Entry Date, 30, 6, 040929, 040929], [Filler, 36, 8, , ], [beg01 code, 44, 2, 00, 00], [beg02 code, 46, 2, , ], [Department, 48, 4, 200, 200], [Expected Reciept Date, 52, 6, 050103, 050103], [Cancel by date, 58, 6, 050106, 050106], [EDI Type, 68, 1, , ], [Add Date, 69, 6, , ], [Filler, 75, 1, , ], [Department Name, 76, 10, LADIES KNI, LADIES KNI], [Prcoess Type, 86, 1, C, C], [Order Type, 87, 2, FT, FT]]')
		select('Table', 'cell:Data,9(050103)')
		assert_p('Table', 'Text', 'cell:Data,9(050103)')
	close()
