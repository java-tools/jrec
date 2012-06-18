useFixture(default)
#
#  Exclude department
#
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
		select('Table', 'cell:Include,3(true)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 1, 20, Store: 20 a, , ], [, New, 1, , Store: 20, , ], [, Old, 2, 63604808, 20040118, 1, 4870], [, New, 2, , 40118, , ], [, Old, 3, 1, 4870, 11, ], [, New, 3, , , 1, ], [, Old, 5, 69684558, 40118, -10, -190000], [, New, 5, , , -1, -19000], [, Old, 7, 69694158, 20040118, 1, 19000], [, New, 7, , 40118, , ], [, Old, 59, 166, Store: 166 a, , ], [, New, 59, , Store: 166, , ]]')
	close()
