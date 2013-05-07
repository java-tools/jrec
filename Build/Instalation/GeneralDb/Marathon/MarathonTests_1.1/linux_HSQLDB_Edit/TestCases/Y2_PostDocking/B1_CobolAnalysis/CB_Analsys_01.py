useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Utilities>>Cobol Copybook Analysis')
		select('Copybook_Txt', commonBits.CobolCopybookDir() + 'AmsLocation.cbl')
		select('Cobol Dialect_Txt', 'Open Cobol Little Endian (Intel)')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , AmsLocation.cbl, , , , ], [, , 01, Ams-Vendor, 1, 173, 173, , ], [, , 03, Brand, 1, 3, 3, x(3), ], [, , 03, Location-Number, 4, 4, 4, 9(4), ], [, , 03, Location-Type, 8, 2, 2, XX, ], [, , 03, Location-Name, 10, 35, 35, X(35), ], [, , 03, Address-1, 45, 40, 40, X(40), ], [, , 03, Address-2, 85, 40, 40, X(40), ], [, , 03, Address-3, 125, 35, 35, X(35), ], [, , 03, Postcode, 160, 10, 10, 9(10), ], [, , 03, State, 170, 3, 3, XXX, ], [, , 03, Location-Active, 173, 1, 1, X, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Copybook_Txt', commonBits.CobolCopybookDir() + 'AmsVendor.cbl')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , AmsVendor.cbl, , , , ], [, , 01, Ams-Vendor, 1, 76, 76, , ], [, , 03, Brand, 1, 3, 3, x(3), ], [, , 03, Vendor-Number, 4, 8, 8, 9(8), ], [, , 03, Vendor-Name, 12, 40, 40, X(40), ], [, , 03, Filler-1, 52, 15, 15, X(15), ], [, , 03, Code-850, 67, 3, 3, 999, ], [, , 03, Value-P, 70, 1, 1, X, ], [, , 03, Filler, 71, 3, 3, X(3), ], [, , 03, Zero-Value, 74, 3, 3, 999, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
