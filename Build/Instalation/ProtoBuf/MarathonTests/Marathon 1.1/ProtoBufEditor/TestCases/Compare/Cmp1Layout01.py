useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
##		select('FileChooser', '/C:/Program Files/RecordEdit/ProtoBuf/SampleFiles/protoStoreSales3_Compare.bin')
		select_menu('Utilities>>Compare Menu')
		click('*1')
##		select('FileChooser', '/C:/Program Files/RecordEdit/ProtoBuf/SampleFiles/protoStoreSales3_Compare.bin')
		select('FileChooser1', commonBits.sampleDir() + 'protoStoreSales3_Compare.bin')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Right')
		select('TabbedPane', '')
		assert_p('Table', 'Content', '[[Product, true], [Order, true], [Summary, true], [Deptartment, true], [Store, true]]')
		assert_p('Table1', 'Content', '[[keycode, true], [saleDate, true], [quantity, true], [price, true]]')
		select('Table', 'cell:Record,1(Order)')
		assert_p('Table1', 'Content', '[[keycode, true], [quantity, true]]')
		select('Table', 'cell:Record,3(Deptartment)')
		assert_p('Table1', 'Content', '[[department, true], [name, true]]')
		select('Table', 'cell:Record,3(Deptartment)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Deleted, 8, 69684558, 40118, 1, 5010], [, , , , , , ], [, Deleted, 10, 69694158, 40118, -1, -19000], [, , , , , , ], [, Deleted, 17, 929, Department: 929, , ], [, , , , , , ], [, Deleted, 18, 65674532, 40118, 1, 3590], [, , , , , , ], [, Deleted, 19, 1, 3590, 1, ], [, , , , , , ], [, Deleted, 26, 69684558, 1, , ], [, , , , , , ], [, Deleted, 28, 65674532, 1, , ], [, , , , , , ], [, Deleted, 82, 170, Department: 170, , ], [, , , , , , ], [, Deleted, 83, 68674560, 40118, 1, 5990], [, , , , , , ], [, Deleted, 84, 1, 5990, 1, ], [, , , , , , ], [, Deleted, 88, 62694843, 40118, 1, 13590], [, , , , , , ], [, Deleted, 91, 69644164, 40118, 1, 21590], [, , , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
