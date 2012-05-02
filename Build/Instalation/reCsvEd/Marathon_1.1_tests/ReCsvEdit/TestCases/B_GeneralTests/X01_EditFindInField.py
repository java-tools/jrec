useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		select('FilePane$3', 'XfdDTAR020.csv')
		doubleclick('FilePane$3', '6')

		click('Edit1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 5.01], [69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89], [63674861, 20, 40118, 957, 10, 2.70], [65674532, 20, 40118, 929, 1, 3.59], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [62684217, 59, 40118, 957, 1, 9.99], [67674686, 59, 40118, 929, 1, 3.99]]')
		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
		rightclick('LineList.FileDisplay_JTbl', '2|STORE-NO,0')
		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
		rightclick('LineList.FileDisplay_JTbl', '1|KEYCODE-NO,0')
		select_menu('Edit Record')
#		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
#		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, , 69684558, 69684558], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 5.01, 5.01]]')
		click('Find1')
		select('Find.Search For_Txt', '01')
#		click('WindowsInternalFrameTitlePane', 167, 13)
		select('Find.Field_Txt', 'SALE-PRICE')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field position)=1 2')
		click('Find1')

		assert_p('TextField', 'Text', 'Found (line, field position)=4 2')
		select_menu('Window>>XfdDTAR020.csv.csv>>Record: ')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, , 69694158, 69694158], [STORE-NO, 2, , 20, 20], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 280, 280], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 5.01, 5.01]]')
		assert_p('LineFrame.Record_Txt', 'Text', '4')
		select_menu('Window>>XfdDTAR020.csv.csv>>Find')
		select('Find.Search For_Txt', '01')
		click('Find1')
		keystroke('Find1', 'Left')
		assert_p('TextField', 'Text', 'Found (line, field position)=23 0')
		select_menu('Window>>XfdDTAR020.csv.csv>>Record: ')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, , 67674686, 67674686], [STORE-NO, 2, , 59, 59], [DATE, 3, , 40118, 40118], [DEPT-NO, 4, , 929, 929], [QTY-SOLD, 5, , 1, 1], [SALE-PRICE, 6, , 3.99, 3.99]]')
		assert_p('LineFrame.Record_Txt', 'Text', '22')

	close()
