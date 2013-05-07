useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('File_Txt',  commonBits.sampleDir() + 'protoSales.bin')
		click('Edit1')
		select_menu('View>>Sorted Field Tree')
		select('fields_JTbl', 'store', 'Field,0')
		select('fields_JTbl', 'department', 'Field,1')
		select('fields_JTbl', 'cell:Field,1(department)')
		select('fieldSummary_JTbl', 'cell:Function,4()')
		select('fields_JTbl', 'cell:Field,1(department)')
		select('fieldSummary_JTbl', 'Sum', 'Function,4')
		select('fieldSummary_JTbl', 'Sum', 'Function,5')
		select('fieldSummary_JTbl', 'cell:Function,5(Sum)')
		click('Build Tree')
		select('TabbedPane', 'Tree View')
		select_menu('Window>>Show Child Record')
		select('TabbedPane', 'Tree View')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, 63604808, 63604808], [store, 2, 20, 20], [department, 3, 170, 170], [saleDate, 4, 40118, 40118], [quantity, 5, 1, 1], [price, 6, 4870, 4870]]')
		click('Find')
		select('Find.Search For_Txt', '57')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=11, 2, 1')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, 64634429, 64634429], [store, 2, 20, 20], [department, 3, 957, 957], [saleDate, 4, 40118, 40118], [quantity, 5, 1, 1], [price, 6, 3990, 3990]]')
		assert_p('LineFrame.Record_Txt', 'Text', '11')
##		click('WindowsInternalFrameTitlePane', 154, 12)
		click('Find >>')
		keystroke('Find >>', 'Context Menu')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=12, 2, 1')
		assert_p('LineFrame.Record_Txt', 'Text', '12')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, 66624458, 66624458], [store, 2, 20, 20], [department, 3, 957, 957], [saleDate, 4, 40118, 40118], [quantity, 5, 1, 1], [price, 6, 890, 890]]')
		select_menu('Window>>protoSales.bin>>Find')
		click('Find >>')
		assert_p('LineFrame.Record_Txt', 'Text', '13')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, 63674861, 63674861], [store, 2, 20, 20], [department, 3, 957, 957], [saleDate, 4, 40118, 40118], [quantity, 5, 10, 10], [price, 6, 2700, 2700]]')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=13, 2, 1')
		select_menu('Window>>protoSales.bin>>Find')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=13, 2, 1')
		click('Find >>')
		select_menu('Window>>protoSales.bin>>Table:')
		assert_p('LineFrame.Record_Txt', 'Text', '23')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, 64614401, 64614401], [store, 2, 59, 59], [department, 3, 957, 957], [saleDate, 4, 40118, 40118], [quantity, 5, 1, 1], [price, 6, 1990, 1990]]')
		select_menu('Window>>protoSales.bin>>Find')
##		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=23, 2, 1')
	close()
