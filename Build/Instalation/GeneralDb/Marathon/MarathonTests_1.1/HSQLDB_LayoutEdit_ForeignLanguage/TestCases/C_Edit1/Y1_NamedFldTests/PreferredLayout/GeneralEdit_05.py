useFixture(RecordEditor)
##
##   Testing Hide with find !!!
##
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv1DTAR020_tst1.bin.csv')
		select('System_Txt', 'CSV')
		click(commonBits.fl('Edit') + '1')
		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
		rightclick('LineList.FileDisplay_JTbl', '1|KEYCODE-NO,1')
#		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
#		rightclick('LineTree.FileDisplay_JTbl', 'STORE-NO,2')
		select_menu(commonBits.fl('Edit Record'))
##		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Show / Hide Fields'))
		select('Table', 'cell:' + commonBits.fl('Show') + ',2(true)')
		click(commonBits.fl('Go'))
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, , 69684558, 69684558], [STORE-NO, 2, , 20, 20], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 19.00, 19.00]]')
		click('Find1')
##		click('MetalInternalFrameTitlePane', 204, 13)
		select('Find.Search For_Txt', '20')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '2, 1, 0')
		select_menu(commonBits.fl('Window') + '>>csv1DTAR020_tst1.bin.csv>>' + commonBits.fl('Record:'))
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, , 69684558, 69684558], [STORE-NO, 2, , 20, 20], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 19.00, 19.00]]')
		select_menu(commonBits.fl('Window') + '>>csv1DTAR020_tst1.bin.csv>>' + commonBits.fl('Find'))
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '3, 1, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '4, 1, 0')
		select_menu(commonBits.fl('Window') + '>>csv1DTAR020_tst1.bin.csv>>' + commonBits.fl('Record:'))
		assert_p('LineFrame.Record_Txt', 'Text', '4')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, , 69694158, 69694158], [STORE-NO, 2, , 20, 20], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 5.01, 5.01]]')
		select_menu(commonBits.fl('Window') + '>>csv1DTAR020_tst1.bin.csv>>' + commonBits.fl('Find'))
		commonBits.find(click)

		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '5, 1, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '6, 1, 0')
		select_menu(commonBits.fl('Window') + '>>csv1DTAR020_tst1.bin.csv>>' + commonBits.fl('Record:'))
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, , 62684671, 62684671], [STORE-NO, 2, , 20, 20], [DEPT-NO, 4, , 685, 685], [QTY-SOLD, 5, , -1, -1], [SALE-PRICE, 6, , -69.99, -69.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
