useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

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
##		assert_p('Table', 'Content', '[[Product, 0], [Order, -1], [Summary, -1], [Deptartment, 1], [Store, 2]]')
		assert_p('Table', 'Content', '[[Product, Product], [Order,  ], [Summary,  ], [Deptartment, Deptartment], [Store, Store]]')
#		select('Table', 'cell:Record,0(Product)')
#		select('Table1', 'cell:Equivalent Field,0(keycode)')
#		select('Table', 'cell:Record,0(Product)')
#		select('Table1', '', 'Equivalent Field,0')
#		select('Table1', '', 'Equivalent Field,1')
#		select('Table1', '', 'Equivalent Field,2')
#		select('Table1', '', 'Equivalent Field,3')
#		select('Table1', 'cell:Field,0(keycode)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
##		assert_p('Table', 'Content', '[[, , , , , , ], [, Inserted, 7, 69684558, 40118, 1, 5010], [, , , , , , ], [, Inserted, 9, 69694158, 40118, -1, -19000], [, , , , , , ], [, Inserted, 14, 929, Department: 929, , ], [, , , , , , ], [, Inserted, 15, 65674532, 40118, 1, 3590], [, , , , , , ], [, Inserted, 50, 170, Department: 170, , ], [, , , , , , ], [, Inserted, 51, 68674560, 40118, 1, 5990], [, , , , , , ], [, Inserted, 55, 62694843, 40118, 1, 13590], [, , , , , , ], [, Inserted, 58, 69644164, 40118, 1, 21590]]')
		assert_p('Table', 'Content', '[[, , , , , , ], [, Inserted, 7, 69684558, 40118, 1, 5010], [, , , , , , ], [, Inserted, 9, 69694158, 40118, -1, -19000], [, , , , , , ], [, Inserted, 14, 929, Department: 929, , ], [, , , , , , ], [, Inserted, 15, 65674532, 40118, 1, 3590], [, , , , , , ], [, Inserted, 50, 170, Department: 170, , ], [, , , , , , ], [, Inserted, 51, 68674560, 40118, 1, 5990], [, , , , , , ], [, Inserted, 55, 62694843, 40118, 1, 13590], [, , , , , , ], [, Inserted, 58, 69644164, 40118, 1, 21590]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
