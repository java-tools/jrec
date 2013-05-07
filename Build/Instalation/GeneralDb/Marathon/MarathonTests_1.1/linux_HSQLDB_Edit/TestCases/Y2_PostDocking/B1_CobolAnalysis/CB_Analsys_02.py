useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Utilities>>Cobol Copybook Analysis')
		select('Copybook_Txt',  commonBits.CobolCopybookDir() + 'DTAR020.cbl')
		select('Cobol Dialect_Txt', 'Open Cobol Little Endian (Intel)')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , DTAR020.cbl, , , , , , ], [, , 03, DTAR020-KCODE-STORE-KEY, 1, 10, 10, , , , ], [, , 05, DTAR020-KEYCODE-NO, 1, 8, 8, X(08), , , ], [, , 05, DTAR020-STORE-NO, 9, 2, 3, S9(03), computational-3, true, ], [, , 03, DTAR020-DATE, 11, 4, 7, S9(07), computational-3, true, ], [, , 03, DTAR020-DEPT-NO, 15, 2, 3, S9(03), computational-3, true, ], [, , 03, DTAR020-QTY-SOLD, 17, 5, 9, S9(9), computational-3, true, ], [, , 03, DTAR020-SALE-PRICE, 22, 6, 11, S9(9)V99, computational-3, true, 2]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Cobol Dialect_Txt', 'Mainframe')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , DTAR020.cbl, , , , , , ], [, , 03, DTAR020-KCODE-STORE-KEY, 1, 10, 10, , , , ], [, , 05, DTAR020-KEYCODE-NO, 1, 8, 8, X(08), , , ], [, , 05, DTAR020-STORE-NO, 9, 2, 3, S9(03), computational-3, true, ], [, , 03, DTAR020-DATE, 11, 4, 7, S9(07), computational-3, true, ], [, , 03, DTAR020-DEPT-NO, 15, 2, 3, S9(03), computational-3, true, ], [, , 03, DTAR020-QTY-SOLD, 17, 5, 9, S9(9), computational-3, true, ], [, , 03, DTAR020-SALE-PRICE, 22, 6, 11, S9(9)V99, computational-3, true, 2]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Cobol Dialect_Txt', 'Open Cobol bs2000 Little Endian (Intel)')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , DTAR020.cbl, , , , , , ], [, , 03, DTAR020-KCODE-STORE-KEY, 1, 10, 10, , , , ], [, , 05, DTAR020-KEYCODE-NO, 1, 8, 8, X(08), , , ], [, , 05, DTAR020-STORE-NO, 9, 2, 3, S9(03), computational-3, true, ], [, , 03, DTAR020-DATE, 11, 4, 7, S9(07), computational-3, true, ], [, , 03, DTAR020-DEPT-NO, 15, 2, 3, S9(03), computational-3, true, ], [, , 03, DTAR020-QTY-SOLD, 17, 5, 9, S9(9), computational-3, true, ], [, , 03, DTAR020-SALE-PRICE, 22, 6, 11, S9(9)V99, computational-3, true, 2]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Copybook_Txt',  commonBits.CobolCopybookDir() + 'DTAR1000.cbl')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , DTAR1000.cbl, , , , , ], [, , 03, DTAR1000-REC, 1, 60, 60, , , ], [, , 10, DTAR1000-STORE-NO, 1, 2, 4, S9(4), computational, true], [, , 10, DTAR1000-REGION-NO, 3, 2, 4, S9(4), computational, true], [, , 10, DTAR1000-STORE-NAME, 5, 50, 50, X(50), , ], [, , 10, DTAR1000-NEW-STORE, 55, 1, 1, X(1), , ], [, , 10, DTAR1000-ACTIVE-STORE, 56, 1, 1, X(1), , ], [, , 10, DTAR1000-CLOSED-STORE, 57, 1, 1, X(1), , ], [, , 10, DTAR1000-DC-TYPE, 58, 1, 1, X(1), , ], [, , 10, DTAR1000-SRC-TYPE, 59, 1, 1, X(1), , ], [, , 10, DTAR1000-HO-TYPE, 60, 1, 1, X(1), , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Cobol Dialect_Txt', 'Open Cobol Micro Focus (Intel)')
		click('Display')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , , DTAR1000.cbl, , , , , ], [, , 03, DTAR1000-REC, 1, 60, 60, , , ], [, , 10, DTAR1000-STORE-NO, 1, 2, 4, S9(4), computational, true], [, , 10, DTAR1000-REGION-NO, 3, 2, 4, S9(4), computational, true], [, , 10, DTAR1000-STORE-NAME, 5, 50, 50, X(50), , ], [, , 10, DTAR1000-NEW-STORE, 55, 1, 1, X(1), , ], [, , 10, DTAR1000-ACTIVE-STORE, 56, 1, 1, X(1), , ], [, , 10, DTAR1000-CLOSED-STORE, 57, 1, 1, X(1), , ], [, , 10, DTAR1000-DC-TYPE, 58, 1, 1, X(1), , ], [, , 10, DTAR1000-SRC-TYPE, 59, 1, 1, X(1), , ], [, , 10, DTAR1000-HO-TYPE, 60, 1, 1, X(1), , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
