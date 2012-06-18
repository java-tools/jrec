useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_0'

	if window('Protocol Buffer Editor'):
		select_menu('Utilities>>Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3_Compare2.bin')
		click('Right')
		select('TabbedPane', '')
		assert_p('Table', 'Content', '[[Product, Product], [Order, Order], [Summary, Summary], [Deptartment, Deptartment], [Store, Store]]')
		assert_p('Table1', 'Content', '[[keycode, keycode], [saleDate, saleDate], [quantity, quantity], [price, price]]')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 6, 69684558, 40118, 1, 19000], [, New, 6, , , 1333, ], [, Old, 8, 69684558, 40118, 1, 5010], [, New, 8, 69694158, , , 19123], [, Deleted, 9, 69694158, 40118, 1, 19000], [, , , , , , ], [, Deleted, 10, 69694158, 40118, -1, -19000], [, , , , , , ]]')
	close()
