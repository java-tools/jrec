useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('File_Txt', commonBits.sampleDir() + 'protoStoreSales7.bin')
		click('Edit1')
		select('LineTreeChild.FileDisplay_JTbl', 'cell:Tree,0(null)')
		rightclick('LineTreeChild.FileDisplay_JTbl', 'Tree,0')
		select_menu('Edit Record')
		select('TabbedPane', 'Record: ')
		select('LineFrameTree.FileDisplay_JTbl', 'cell:Data,0(20)')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[store, 1, , INT32, 20, 20], [name, 2, , STRING, Store: 20, Store: 20]]')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[store, 1, , INT32, 20, 20], [name, 2, , STRING, Store: 20, Store: 20]]')
		click('Down')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[department, 1, , INT32, 170, 170], [name, 2, , STRING, Department: 170, Department: 170]]')
		click('Down')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[keycode, 1, , INT32, 63604808, 63604808], [saleDate, 2, , INT32, [40118], [40118]], [quantity, 3, , INT32, [1], [1]], [price, 4, , INT64, [4870], [4870]], [saleType, 5, , ENUM, [SALE], [SALE]], [priceFloat, 6, , FLOAT, [4.87], [4.87]], [priceDouble, 7, , DOUBLE, [4.87], [4.87]], [strArray, 8, , STRING, [\'\'], [\'\']]]')
		click('Right')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[quantity, 1, , INT32, 1, 1], [price, 2, , INT64, 4870, 4870], [count, 3, , INT32, 1, 1]]')
		click('Up')
		click('Down')
		click('Up')
		click('Right')
		click('Down')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[keycode, 1, , INT32, 69684558, 69684558], [saleDate, 2, , INT32, [40118, 40118, 40118], [40118, 40118, 40118]], [quantity, 3, , INT32, [1, -1, 1], [1, -1, 1]], [price, 4, , INT64, [19000, -19000, 5010], [19000, -19000, 5010]], [saleType, 5, , ENUM, [SALE, RETURN, SALE], [SALE, RETURN, SALE]], [priceFloat, 6, , FLOAT, [19.0, -19.0, 5.01], [19.0, -19.0, 5.01]], [priceDouble, 7, , DOUBLE, [19.0, -19.0, 5.01], [19.0, -19.0, 5.01]], [strArray, 8, , STRING, [\'\',\' -1\',\' -1 1\'], [\'\',\' -1\',\' -1 1\']]]')
	close()
