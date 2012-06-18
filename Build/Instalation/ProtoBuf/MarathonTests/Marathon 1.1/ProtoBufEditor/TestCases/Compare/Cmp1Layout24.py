useFixture(default)
##
## Exclude department
##
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_0'

	if window('Protocol Buffer Editor'):
		select_menu('Utilities>>Compare Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		select('FileChooser1', commonBits.sampleDir() + 'protoStoreSales3_Compare3.bin')
		click('Right')
		select('TabbedPane', '')

##
##          Exclude saleDate
##

		select('Table', 'cell:Include,3(true)')
		select('Table', 'cell:Record,0(Product)')
		select('Table1', 'cell:Include,1(true)')
		select('Table', 'cell:Record,0(Product)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 1, 20, Store: 20, ], [, New, 1, , Store: 20 a, ], [, Old, 3, 1, 4870, 1], [, New, 3, , , 11], [, Old, 5, 69684558, -1, -19000], [, New, 5, , -10, -190000], [, Old, 59, 166, Store: 166, ], [, New, 59, , Store: 166 a, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>Single Layout Compare')
		click('Left')
		select('TabbedPane', '')

##
##          Include saleDate, 
##          Exclude quantity
##

		select('Table1', 'cell:Include,1(false)')
		select('Table1', 'cell:Include,2(true)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 1, 20, Store: 20, ], [, New, 1, , Store: 20 a, ], [, Old, 2, 63604808, 40118, 4870], [, New, 2, , 20040118, ], [, Old, 3, 1, 4870, 1], [, New, 3, , , 11], [, Old, 5, 69684558, 40118, -19000], [, New, 5, , , -190000], [, Old, 7, 69694158, 40118, 19000], [, New, 7, , 20040118, ], [, Old, 59, 166, Store: 166, ], [, New, 59, , Store: 166 a, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>Single Layout Compare')
		click('Left')
		select('TabbedPane', '')
## 
##          Exclude price
##
		select('Table1', 'cell:Include,3(true)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 1, 20, Store: 20, ], [, New, 1, , Store: 20 a, ], [, Old, 2, 63604808, 40118, ], [, New, 2, , 20040118, ], [, Old, 3, 1, 4870, 1], [, New, 3, , , 11], [, , , , , ], [, Inserted, 7, 69694158, 20040118, ], [, Deleted, 9, 69694158, 40118, ], [, , , , , ], [, Old, 59, 166, Store: 166, ], [, New, 59, , Store: 166 a, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
