useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_0'

	if window('Protocol Buffer Editor'):
		select_menu('Utilities>>Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3_Compare.bin')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales.bin')
		select('FileChooser1', commonBits.stdCopybookDir() + 'StoreSales.protocomp')
		click('Right')
		select('TabbedPane', '')
		assert_p('Table', 'Content', '[[Product, Product], [Order,  ], [Summary,  ], [Deptartment, Deptartment], [Store, Store]]')
		assert_p('Table1', 'Content', '[[keycode, keycode], [saleDate, saleDate], [quantity, quantity], [price, price]]')
		select('Table', 'cell:Equivalent Record,0(Product)')
		select('Table', ' ', 'Equivalent Record,0')
		select('Table', 'cell:Equivalent Record,0( )')
		assert_p('Table1', 'Content', '[[keycode, ], [saleDate, ], [quantity, ], [price, ]]')
		select('Table', 'cell:Equivalent Record,0( )')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, , , , ], [, Inserted, 5, 929, Department: 929], [, , , , ], [, Inserted, 17, 170, Department: 170]]')
	close()
