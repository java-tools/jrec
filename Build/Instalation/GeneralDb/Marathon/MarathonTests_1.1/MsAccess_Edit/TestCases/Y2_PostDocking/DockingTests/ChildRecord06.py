useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv3DTAR020_tst1.bin.csv')

		if commonBits.version() == 'MsAccess':
			select('Record Layout_Txt', 'Comma Delimited, names on the first line')
		else:
			select('System_Txt', 'CSV')

		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Sorted Field Tree'))
		select('fields_JTbl', 'STORE-NO', commonBits.fl('Field') + ',0')
		select('fields_JTbl', 'cell:' + commonBits.fl('Field') + ',0(STORE-NO)')
		select('fieldSummary_JTbl', 'cell:' + commonBits.fl('Function') + ',1()')
		select('fields_JTbl', 'cell:' + commonBits.fl('Field') + ',0(STORE-NO)')
		select('fieldSummary_JTbl', commonBits.fl('Maximum'), commonBits.fl('Function') + ',1')
##
##
		select('fieldSummary_JTbl', commonBits.fl('Sum'), commonBits.fl('Function') + ',4')
		select('fieldSummary_JTbl', commonBits.fl('Sum'), commonBits.fl('Function') + ',5')
		select('fieldSummary_JTbl', 'cell:' + commonBits.fl('Function') + ',5(' + commonBits.fl('Sum') + ')')
		click(commonBits.fl('Build Tree'))
		select('TabbedPane', 'Tree View')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , 166, , , 4, 42.56], [, , 68654655, 166, 40118, 60, 1, 5.08], [, , 69624033, 166, 40118, 80, 1, 18.19], [, , 60604100, 166, 40118, 80, 1, 13.30], [, , 68674560, 166, 40118, 170, 1, 5.99], [, , , 20, , , 2, 9.88], [, , 63604808, 20, 40118, 170, 1, 4.87], [, , 69684558, 20, 40118, 280, 1, 19.00], [, , 69684558, 20, 40118, 280, -1, -19.00], [, , 69694158, 20, 40118, 280, 1, 5.01], [, , 62684671, 20, 40118, 685, 1, 69.99], [, , 62684671, 20, 40118, 685, -1, -69.99], [, , , 59, , , 5, 35.91], [, , 61664713, 59, 40118, 335, 1, 17.99], [, , 61664713, 59, 40118, 335, -1, -17.99], [, , 61684613, 59, 40118, 335, 1, 12.99], [, , 68634752, 59, 40118, 410, 1, 8.99], [, , 60694698, 59, 40118, 620, 1, 3.99], [, , 60664659, 59, 40118, 620, 1, 3.99], [, , 60614487, 59, 40118, 878, 1, 5.95]]')
##		select_menu(commonBits.fl('Window') + '>>' + commonBits.fl('Show Child Record'))

		rightclick(commonBits.fl('Tree View'))
		select_menu(commonBits.fl('Show Child Record'))

		select('TabbedPane', 'Tree View')
		assert_p('LineFrame.Record_Txt', 'Text', '1')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 68654655, 68654655], [STORE-NO, 2, 166, 166], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 60, 60], [QTY-SOLD, 5, 1, 1], [SALE-PRICE, 6, 5.08, 5.08]]')
		select('LineTree.FileDisplay_JTbl', 'cell:KEYCODE-NO,4(68674560)')
		assert_p('LineFrame.Record_Txt', 'Text', '4')
		select('LineTree.FileDisplay_JTbl', 'cell:KEYCODE-NO,4(68674560)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 68674560, 68674560], [STORE-NO, 2, 166, 166], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 170, 170], [QTY-SOLD, 5, 1, 1], [SALE-PRICE, 6, 5.99, 5.99]]')
		select('LineTree.FileDisplay_JTbl', 'cell:DATE,7(40118)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 69684558, 69684558], [STORE-NO, 2, 20, 20], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 280, 280], [QTY-SOLD, 5, 1, 1], [SALE-PRICE, 6, 19.00, 19.00]]')
		select('LineTree.FileDisplay_JTbl', 'cell:DATE,7(40118)')
		assert_p('LineFrame.Record_Txt', 'Text', '6')
		select('LineTree.FileDisplay_JTbl', 'cell:DATE,11(40118)')
		assert_p('LineFrame.Record_Txt', 'Text', '10')
		select('LineTree.FileDisplay_JTbl', 'cell:DATE,11(40118)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, 1, 62684671, 62684671], [STORE-NO, 2, 20, 20], [DATE, 3, 40118, 40118], [DEPT-NO, 4, 685, 685], [QTY-SOLD, 5, -1, -1], [SALE-PRICE, 6, -69.99, -69.99]]')
		select('LineTree.FileDisplay_JTbl', 'cell:DATE,11(40118)')
		select_menu(commonBits.fl('Window') + '>>' + commonBits.fl('Remove Child Record'))
		select('TabbedPane', 'Tree View')
		select('LineTree.FileDisplay_JTbl', 'cell:DATE,11(40118)')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , 166, , , 4, 42.56], [, , 68654655, 166, 40118, 60, 1, 5.08], [, , 69624033, 166, 40118, 80, 1, 18.19], [, , 60604100, 166, 40118, 80, 1, 13.30], [, , 68674560, 166, 40118, 170, 1, 5.99], [, , , 20, , , 2, 9.88], [, , 63604808, 20, 40118, 170, 1, 4.87], [, , 69684558, 20, 40118, 280, 1, 19.00], [, , 69684558, 20, 40118, 280, -1, -19.00], [, , 69694158, 20, 40118, 280, 1, 5.01], [, , 62684671, 20, 40118, 685, 1, 69.99], [, , 62684671, 20, 40118, 685, -1, -69.99], [, , , 59, , , 5, 35.91], [, , 61664713, 59, 40118, 335, 1, 17.99], [, , 61664713, 59, 40118, 335, -1, -17.99], [, , 61684613, 59, 40118, 335, 1, 12.99], [, , 68634752, 59, 40118, 410, 1, 8.99], [, , 60694698, 59, 40118, 620, 1, 3.99], [, , 60664659, 59, 40118, 620, 1, 3.99], [, , 60614487, 59, 40118, 878, 1, 5.95]]')
		select('LineTree.FileDisplay_JTbl', 'cell:DATE,11(40118)')
		click('X1')
##		select('TabbedPane', 'Table:')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95], [68654655, 166, 40118, 60, 1, 5.08], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30], [68674560, 166, 40118, 170, 1, 5.99]]')
	close()
