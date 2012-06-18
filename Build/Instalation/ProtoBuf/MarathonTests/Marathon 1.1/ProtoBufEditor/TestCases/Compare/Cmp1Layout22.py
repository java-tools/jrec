useFixture(default)
###
###  Compare all except store
###
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
		select('Table', 'cell:Include,4(true)')
		assert_p('Table', 'Content', '[[Product, true], [Order, true], [Summary, true], [Deptartment, true], [Store, false]]')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 1, 170, Department: 170 a, , ], [, New, 1, , Department: 170, , ], [, Old, 2, 63604808, 20040118, 1, 4870], [, New, 2, , 40118, , ], [, Old, 3, 1, 4870, 11, ], [, New, 3, , , 1, ], [, Old, 6, 69684558, 40118, -10, -190000], [, New, 6, , , -1, -19000], [, Old, 8, 69694158, 20040118, 1, 19000], [, New, 8, , 40118, , ], [, Old, 79, 170, Department: 170 a, , ], [, New, 79, , Department: 170, , ]]')
	close()
