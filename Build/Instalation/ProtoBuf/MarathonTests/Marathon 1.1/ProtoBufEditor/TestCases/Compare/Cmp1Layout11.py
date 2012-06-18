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
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 6, 69684558, 40118, 1333, 19000], [, New, 6, , , 1, ], [, Old, 8, 69694158, 40118, 1, 19123], [, New, 8, 69684558, , , 5010], [, , , , , , ], [, Inserted, 9, 69694158, 40118, 1, 19000], [, , , , , , ], [, Inserted, 10, 69694158, 40118, -1, -19000]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
