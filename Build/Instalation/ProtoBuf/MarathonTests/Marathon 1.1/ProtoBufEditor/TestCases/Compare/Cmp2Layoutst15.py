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
		select('Table', 'cell:Record,0(Product)')
		assert_p('Table', 'Content', '[[Product, Product], [Deptartment, Deptartment], [Store, Store]]')
		select('Table', 'cell:Record,0(Product)')
		assert_p('Table1', 'Content', '[[keycode, keycode], [saleDate, saleDate], [quantity, quantity], [price, price]]')
		select('Table', 'cell:Record,0(Product)')
		select('Table1', 'cell:Equivalent Field,3(price)')
		select('Table', 'cell:Record,0(Product)')
		select('Table1', '', 'Equivalent Field,3')
		select('Table1', 'cell:Field,1(saleDate)')
		assert_p('Table1', 'Content', '[[keycode, keycode], [saleDate, saleDate], [quantity, quantity], [price, ]]')
		select('Table1', 'cell:Field,1(saleDate)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 5, 69684558, 40118, 1], [, New, 5, , , 1333], [, Deleted, 7, 69684558, 40118, 1], [, , , , , ], [, Deleted, 9, 69694158, 40118, -1], [, , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
