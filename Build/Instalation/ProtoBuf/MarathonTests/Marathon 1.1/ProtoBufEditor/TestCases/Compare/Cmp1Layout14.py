useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_0'

	if window('Protocol Buffer Editor'):
		select_menu('Utilities>>Compare Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3_Compare2.bin')
		select('FileChooser1', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Right')
		select('TabbedPane', '')
		assert_p('Table', 'Content', '[[Product, true], [Order, true], [Summary, true], [Deptartment, true], [Store, true]]')
		select('Table', 'cell:Record,0(Product)')
		assert_p('Table1', 'Content', '[[keycode, true], [saleDate, true], [quantity, true], [price, true]]')
		select('Table', 'cell:Record,0(Product)')
		select('Table1', 'cell:Include,2(true)')
		select('Table', 'cell:Record,0(Product)')
		select('Table1', 'cell:Include,3(true)')
		select('Table', 'cell:Record,0(Product)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, , , , , ], [, Inserted, 8, 69684558, 40118, ], [, , , , , ], [, Inserted, 11, 69694158, 40118, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
