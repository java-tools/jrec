useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv3DTAR020_tst1.bin.csv')
		select('System_Txt', 'CSV')
		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('Window') + '>>' + commonBits.fl('Show Child Record'))
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 63604808, 63604808], [STORE-NO, 2, 20, 20], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 170, 170], [QTY-SOLD, 5, 1, 1], [SALE-PRICE, 6, 4.87, 4.87]]')
		assert_p('LineFrame.Record_Txt', 'Text', '1')
		select('LineList.FileDisplay_JTbl', 'cell:1|KEYCODE-NO,6(61664713)')
		assert_p('LineFrame.Record_Txt', 'Text', '7')
		select('LineList.FileDisplay_JTbl', 'cell:1|KEYCODE-NO,6(61664713)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 61664713, 61664713], [STORE-NO, 2, 59, 59], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 335, 335], [QTY-SOLD, 5, 1, 1], [SALE-PRICE, 6, 17.99, 17.99]]')
		select('LineList.FileDisplay_JTbl', 'cell:4|DEPT-NO,11(620)')
		assert_p('LineFrame.Record_Txt', 'Text', '12')
		select('LineList.FileDisplay_JTbl', 'cell:4|DEPT-NO,11(620)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 60664659, 60664659], [STORE-NO, 2, 59, 59], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 620, 620], [QTY-SOLD, 5, 1, 1], [SALE-PRICE, 6, 3.99, 3.99]]')
		select('LineList.FileDisplay_JTbl', 'cell:4|DEPT-NO,11(620)')
		select_menu(commonBits.fl('Window') + '>>' + commonBits.fl('Remove Child Record'))
	close()
