useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('File_Txt',  commonBits.sampleDir() + 'protoSales.bin')
		click('Edit1')
		select('LineList.FileDisplay_JTbl', 'cell:2|store,0(20)')
		rightclick('LineList.FileDisplay_JTbl', '1|keycode,2')
##		select('LineList.FileDisplay_JTbl', 'cell:2|store,0(20)')
		select_menu('Edit Record')
		select('TabbedPane', 'Record:')
		select('LineFrame.FileDisplay_JTbl', 'cell:Data,2(280)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, , INT32, 69684558, 69684558], [store, 2, , INT32, 20, 20], [department, 3, , INT32, 280, 280], [saleDate, 4, , INT32, 40118, 40118], [quantity, 5, , INT32, 1, 1], [price, 6, , INT64, 5010, 5010]]')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, , INT32, 69694158, 69694158], [store, 2, , INT32, 20, 20], [department, 3, , INT32, 280, 280], [saleDate, 4, , INT32, 40118, 40118], [quantity, 5, , INT32, 1, 1], [price, 6, , INT64, 19000, 19000]]')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, , INT32, 69694158, 69694158], [store, 2, , INT32, 20, 20], [department, 3, , INT32, 280, 280], [saleDate, 4, , INT32, 40118, 40118], [quantity, 5, , INT32, -1, -1], [price, 6, , INT64, -19000, -19000]]')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, , INT32, 69694158, 69694158], [store, 2, , INT32, 20, 20], [department, 3, , INT32, 280, 280], [saleDate, 4, , INT32, 40118, 40118], [quantity, 5, , INT32, 1, 1], [price, 6, , INT64, 5010, 5010]]')
		click('RightM')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, , INT32, 69664668, 69664668], [store, 2, , INT32, 184, 184], [department, 3, , INT32, 903, 903], [saleDate, 4, , INT32, 40118, 40118], [quantity, 5, , INT32, 1, 1], [price, 6, , INT64, 8950, 8950]]')
		click('LeftM')
		select('LineFrame.FileDisplay_JTbl', 'cell:Data,3(40118)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, , INT32, 69684558, 69684558], [store, 2, , INT32, 20, 20], [department, 3, , INT32, 280, 280], [saleDate, 4, , INT32, 40118, 40118], [quantity, 5, , INT32, 1, 1], [price, 6, , INT64, 19000, 19000]]')
		click('Right')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode, 1, , INT32, 69684558, 69684558], [store, 2, , INT32, 20, 20], [department, 3, , INT32, 280, 280], [saleDate, 4, , INT32, 40118, 40118], [quantity, 5, , INT32, -1, -1], [price, 6, , INT64, -19000, -19000]]')
		click('Table:')
		select('TabbedPane', 'Table:')
		assert_p('LineList.FileDisplay_JTbl', 'RowCount', '379')
	close()
