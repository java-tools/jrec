useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_0'

	if window('Protocol Buffer Editor'):
		select_menu('Utilities>>Compare Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3_Compare3.bin')
		select('FileChooser1', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 1, 20, Store: 20 a, , ], [, New, 1, , Store: 20, , ], [, Old, 2, 170, Department: 170 a, , ], [, New, 2, , Department: 170, , ], [, Old, 3, 63604808, 20040118, 1, 4870], [, New, 3, , 40118, , ], [, Old, 4, 1, 4870, 11, ], [, New, 4, , , 1, ], [, Old, 7, 69684558, 40118, -10, -190000], [, New, 7, , , -1, -19000], [, Old, 9, 69694158, 20040118, 1, 19000], [, New, 9, , 40118, , ], [, Old, 70, 166, Store: 166 a, , ], [, New, 70, , Store: 166, , ], [, Old, 82, 170, Department: 170 a, , ], [, New, 82, , Department: 170, , ]]')
	close()
