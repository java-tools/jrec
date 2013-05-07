useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Utilities>>Cobol Copybook Analysis')
		select('Copybook_Txt',  commonBits.CobolCopybookDir() + 'DTAR107.cbl')
		select('Cobol Dialect_Txt', 'Mainframe')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , DTAR107.cbl, , , , , , , , ], [, , 03, DTAR107-STORE-NO, 1, 2, 3, S9(03), computational-3, true, true, , ], [, , 03, Filler, 1, 2, 2, , , , , DTAR107-STORE-NO, ], [, , 05, DTAR107-STORE-NO-REDEF, 1, 2, 2, X(2), , , , , ], [, , 03, DTAR107-TRANS-DATE, 3, 4, 6, S9(06), computational-3, , true, , ], [, , 03, DTAR107-CUST-NO, 7, 16, 16, 9(16), , , , , ], [, , 03, DTAR107-AMOUNT, 23, 5, 9, S9(07)V99, computational-3, , true, , 2], [, , 03, DTAR107-OPERATOR-NO, 28, 5, 8, S9(08), computational-3, , true, , ], [, , 03, DTAR107-TERMINAL-NO, 33, 2, 3, S9(03), computational-3, , true, , ], [, , 03, DTAR107-TIME, 35, 3, 4, S9(04), computational-3, , true, , ], [, , 03, DTAR107-TRANS-NO, 38, 3, 4, S9(04), computational-3, , true, , ], [, , 03, DTAR107-TRANS-TYPE, 41, 2, 2, 9(02), , , , , ], [, , , , DTAR107-SALE, , , , , , , , ], [, , , , , 1, , , , , , , ], [, , , , DTAR107-REFUND, , , , , , , , ], [, , , , , 2, , , , , , , ], [, , , , DTAR107-LAYBY, , , , , , , , ], [, , , , , 3, , , , , , , ], [, , , , DTAR107-VOID, , , , , , , , ], [, , , , , 4, , , , , , , ], [, , 03, DTAR107-TRANS-CODE, 43, 2, 2, 9(02), , , , , ], [, , , , DTAR107-SALE-DR, , , , , , , , ], [, , , , , 10, , , , , , , ], [, , , , DTAR107-REFUND-CR, , , , , , , , ], [, , , , , 20, , , , , , , ], [, , , , DTAR107-DR-REVERSAL, , , , , , , , ], [, , , , , 12, , , , , , , ], [, , , , DTAR107-CR-REVERSAL, , , , , , , , ], [, , , , , 22, , , , , , , ], [, , 03, DTAR107-STD-POINTS, 45, 4, 6, S9(06), computational-3, , true, , ], [, , 03, DTAR107-BONUS-POINTS, 49, 4, 6, S9(06), computational-3, , true, , ], [, , 03, DTAR107-NO-OF-TXNS, 53, 2, 2, 9(02), , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Copybook_Txt',  commonBits.CobolCopybookDir() + 'DTAR119.cbl')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , DTAR119.cbl, , , , , , ], [, , 03, DTAR119-STORE-NO, 1, 2, 3, 9(03), computational-3, , ], [, , 03, DTAR119-TRANS-DATE, 3, 4, 7, 9(07), computational-3, , ], [, , 03, DTAR119-TRANS-CODE, 7, 2, 2, 9(02), computational-3, , ], [, , 03, DTAR119-CARD-NO, 9, 16, 16, 9(16), , , ], [, , 03, DTAR119-TERMINAL-ID, 25, 3, 3, 9(03), , , ], [, , 03, DTAR119-TRANS-NO, 28, 4, 4, 9(04), , , ], [, , 03, DTAR119-BONUS-POINTS, 32, 4, 6, S9(06), computational-3, true, ], [, , 03, DTAR119-KEYCODE, 36, 5, 8, 9(08), computational-3, , ], [, , 03, DTAR119-KEYCODE-AMT, 41, 5, 9, S9(07)V99, computational-3, true, 2], [, , 03, DTAR119-KEYCODE-QTY, 46, 5, 9, S9(07)V99, computational-3, true, 2], [, , 03, DTAR119-PROM-NO, 51, 3, 5, 9(05), computational-3, , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
