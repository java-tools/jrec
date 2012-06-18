useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_0'

	if window('Protocol Buffer Editor'):
		select_menu('Utilities>>Compare Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3_Compare.bin')
		select('FileChooser1', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Right')
		select('TabbedPane', '')
##		select('Table', 'cell:Include,0(true)')
##		select('Table', 'cell:Include,2(true)')
#		select('Table', ' ', 'Equivalent Record,2')
#		select('Table', ' ', 'Equivalent Record,3')
		select('Table', 'false', 'Include,0')
		select('Table', 'false', 'Include,2')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, , , , ], [, Inserted, 5, 929, Department: 929], [, , , , ], [, Inserted, 8, 69684558, 1], [, , , , ], [, Inserted, 10, 65674532, 1], [, , , , ], [, Inserted, 34, 170, Department: 170]]')
##		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
