useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_0'

	if window('Protocol Buffer Editor'):
		select_menu('Utilities>>Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales.bin')
		select('FileChooser1', commonBits.stdCopybookDir() + 'StoreSales.protocomp')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3_Compare2.bin')
		click('Right')
		select('TabbedPane', '')
		assert_p('Table', 'Content', '[[Product, Product], [Deptartment, Deptartment], [Store, Store]]')
		select('Table', 'cell:Record,0(Product)')
		assert_p('Table1', 'Content', '[[keycode, keycode], [saleDate, saleDate], [quantity, quantity], [price, price]]')
		select('Table', 'cell:Record,0(Product)')
		select('Table1', 'cell:Equivalent Field,2(quantity)')
		select('Table', 'cell:Record,0(Product)')
		select('Table1', '', 'Equivalent Field,2')
		select('Table1', 'cell:Equivalent Field,2()')
		assert_p('Table1', 'Content', '[[keycode, keycode], [saleDate, saleDate], [quantity, ], [price, price]]')
		select('Table1', 'cell:Equivalent Field,2()')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 7, 69684558, 40118, 5010], [, New, 7, 69694158, , 19123], [, Deleted, 8, 69694158, 40118, 19000], [, , , , , ], [, Deleted, 9, 69694158, 40118, -19000], [, , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
