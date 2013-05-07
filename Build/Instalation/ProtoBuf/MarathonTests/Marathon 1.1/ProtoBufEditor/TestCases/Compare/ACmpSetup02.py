useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Protocol Buffer Editor'):

		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Edit1')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,0')
		select_menu('Fully Expand Tree')
		select('LayoutCombo', 'Product')
		select('JTreeTable', 'rows:[10,12],columns:[saleDate]')
		click('Delete2')
		select('JTreeTable', '1333', 'quantity,8')
		select('JTreeTable', '19123', 'price,10')
##		select('JTreeTable', '')
		assert_p('JTreeTable', 'Content', '[[, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , 63604808, 40118, 1, 4870], [, , , , , ], [, , , , , ], [, , , , , ], [, , 69684558, 40118, 1333, 19000], [, , 69684558, 40118, -1, -19000], [, , 69694158, 40118, 1, 19123], [, , 69694158, 40118, 1, 5010], [, , , , , ], [, , , , , ], [, , , , , ], [, , 62684671, 40118, 1, 69990], [, , 62684671, 40118, -1, -69990], [, , , , , ], [, , , , , ], [, , , , , ], [, , 65674532, 40118, 1, 3590], [, , , , , ], [, , , , , ], [, , , , , ], [, , 63674861, 40118, 10, 2700], [, , 64634429, 40118, 1, 3990], [, , 66624458, 40118, 1, 890], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ]]')
		select_menu('Utilities>>Compare with Disk')
		assert_p('Table', 'Content', '[[, Old, 6, 69684558, 40118, 1, 19000], [, New, 6, , , 1333, ], [, Old, 8, 69684558, 40118, 1, 5010], [, New, 8, 69694158, , , 19123], [, Deleted, 9, 69694158, 40118, 1, 19000], [, , , , , , ], [, Deleted, 10, 69694158, 40118, -1, -19000], [, , , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('File>>Export')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3_Compare2.bin')
		click('Save File')
#		select_menu('Window>>protoStoreSales3.bin>>Tree View')
#		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
