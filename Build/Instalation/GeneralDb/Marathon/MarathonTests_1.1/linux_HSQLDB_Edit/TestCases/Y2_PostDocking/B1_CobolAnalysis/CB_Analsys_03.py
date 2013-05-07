useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Utilities>>Cobol Copybook Analysis')
		select('Copybook_Txt',  commonBits.CobolCopybookDir() + 'Master_Record.cbl')
		select('Cobol Dialect_Txt', 'Open Cobol Little Endian (Intel)')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , Master_Record.cbl, , , , ], [, , 01, MAST-RECORD, 1, 39, 39, , ], [, , 05, M-SOC-SEC-NUMBER, 1, 9, 9, X(9), ], [, , 05, M-NAME, 10, 17, 17, , ], [, , 10, M-LAST-NAME, 10, 15, 15, X(15), ], [, , 10, M-INITIALS, 25, 2, 2, XX, ], [, , 05, M-LOCATION-CODE, 27, 3, 3, X(3), ], [, , 05, M-COMMISSION-RATE, 30, 2, 2, 99, ], [, , 05, M-YEAR-TO-DATE-SALES, 32, 8, 8, 9(8), ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Copybook_Txt',  commonBits.CobolCopybookDir() + 'Rental_Record.cbl')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , Rental_Record.cbl, , , , ], [, , 01, RENTAL-RECORD, 1, 56, 56, , ], [, , 05, REN-CONTRACT-NO, 1, 6, 6, 9(6), ], [, , 05, REN-NAME, 7, 26, 26, , ], [, , 10, REN-LAST-NAME, 7, 15, 15, X(15), ], [, , 10, REN-FIRST-NAME, 22, 10, 10, X(10), ], [, , 10, REN-INITIAL, 32, 1, 1, X, ], [, , 05, REN-RETURNED-DATE, 33, 6, 6, , ], [, , 10, REN-RETURNED-YEAR, 33, 2, 2, 9(2), ], [, , 10, REN-RETURNED-MONTH, 35, 2, 2, 9(2), ], [, , , , VALID-MONTHS, , , , ], [, , , , , 12, 1, , ], [, , , , FEBRUARY, , , , ], [, , , , , , 2, , ], [, , , , D30-DAY-MONTH, , , , ], [, , , , , , 4, , ], [, , , , , , 6, , ], [, , , , , , 9, , ], [, , , , , , 11, , ], [, , , , D31-DAY-MONTH, , , , ], [, , , , , , 1, , ], [, , , , , , 3, , ], [, , , , , , 5, , ], [, , , , , , 7, , ], [, , , , , , 8, , ], [, , , , , , 10, , ], [, , , , , , 12, , ], [, , 10, REN-RETURNED-DAY, 37, 2, 2, 9(2), ], [, , 05, REN-CAR-TYPE, 39, 1, 1, X, ], [, , , , VALID-CAR-TYPES, , , , ], [, , , , , , E, , ], [, , , , , , C, , ], [, , , , , , M, , ], [, , , , , , F, , ], [, , , , , , L, , ], [, , 05, REN-DAYS-RENTED, 40, 2, 2, 99, ], [, , , , ZERO-DAYS-RENTED, , , , ], [, , , , , , 0, , ], [, , , , VALID-DAYS-RENTED, , , , ], [, , , , , 35, 1, , ], [, , 05, REN-MILEAGE, 42, 14, 14, , ], [, , 10, REN-MILES-IN, 42, 6, 6, 9(6), ], [, , 10, REN-MILES-OUT, 48, 6, 6, 9(6), ], [, , 10, REN-MILEAGE-RATE, 54, 2, 2, 99, ], [, , , , VALID-MILEAGE-RATES, , , , ], [, , , , , 50, 00, , ], [, , 05, REN-INSURANCE, 56, 1, 1, X, ], [, , , , VALID-INSURANCE, , , , ], [, , , , , , Y, , ], [, , , , , , N, , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Copybook_Txt',  commonBits.CobolCopybookDir() + 'Transaction_Record.cbl')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , Transaction_Record.cbl, , , , ], [, , 01, WS-TRANSACTION-RECORD, 1, 37, 37, , ], [, , 05, TR-SOC-SEC-NUMBER, 1, 9, 9, X(9), ], [, , 05, TR-NAME, 10, 17, 17, , ], [, , 10, TR-LAST-NAME, 10, 15, 15, X(15), ], [, , 10, TR-INITIALS, 25, 2, 2, XX, ], [, , 05, TR-LOCATION-CODE, 27, 3, 3, X(3), ], [, , 05, TR-COMMISSION-RATE, 30, 2, 2, 99, ], [, , 05, TR-SALES-AMOUNT, 32, 5, 5, 9(5), ], [, , 05, TR-TRANSACTION-CODE, 37, 1, 1, X, ], [, , , , ADDITION, , , , ], [, , , , , A, , , ], [, , , , CORRECTION, , , , ], [, , , , , C, , , ], [, , , , DELETION, , , , ], [, , , , , D, , , ], [, , , , VALID-CODES, , , , ], [, , , , , A, , , ], [, , , , , C, , , ], [, , , , , D, , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
