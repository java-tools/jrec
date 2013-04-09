useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv4DTAR020_tst1.bin.csv')
		click('Edit1')
		select_menu('View>>Text View')
		select('TabbedPane', 'Document View')
		rightclick('Document View')
		select_menu('Show Child Record')
		select('TabbedPane', 'Document View')
		assert_p('LineFrame.Record_Txt', 'Text', '1')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 63604808, 63604808], [STORE-NO, 2, 20, 20], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 170, 170], [QTY-SOLD, 5, 1, 1], [SALE-PRICE, 6, 4.87, 4.87]]')
		click('Find')
		select('Find.Search For_Txt', '-1')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=3, 4, 0')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 69684558, 69684558], [STORE-NO, 2, 20, 20], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 280, 280], [QTY-SOLD, 5, -1, -1], [SALE-PRICE, 6, -19.00, -19.00]]')
		assert_p('LineFrame.Record_Txt', 'Text', '3')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=3, 5, 0')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 69684558, 69684558], [STORE-NO, 2, 20, 20], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 280, 280], [QTY-SOLD, 5, -1, -1], [SALE-PRICE, 6, -19.00, -19.00]]')
		assert_p('LineFrame.Record_Txt', 'Text', '3')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=6, 4, 0')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 62684671, 62684671], [STORE-NO, 2, 20, 20], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 685, 685], [QTY-SOLD, 5, -1, -1], [SALE-PRICE, 6, -69.99, -69.99]]')
		assert_p('LineFrame.Record_Txt', 'Text', '6')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=8, 4, 0')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 61664713, 61664713], [STORE-NO, 2, 59, 59], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 335, 335], [QTY-SOLD, 5, -1, -1], [SALE-PRICE, 6, -17.99, -17.99]]')
		assert_p('LineFrame.Record_Txt', 'Text', '8')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=8, 5, 0')
		assert_p('LineFrame.Record_Txt', 'Text', '8')
		click('Find >>')

		if window(''):
			click('No')
		close()
	close()
