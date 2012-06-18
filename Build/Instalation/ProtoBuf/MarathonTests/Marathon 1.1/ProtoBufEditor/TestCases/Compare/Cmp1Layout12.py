useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_0'

	if window('Protocol Buffer Editor'):
##		click('PopupMenu$Separator5', 73, 0)
		select_menu('Utilities>>Compare Menu')
		click('*1')
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		select('FileChooser1', commonBits.sampleDir() + 'protoStoreSales3_Compare2.bin')
		click('Right')
		select('TabbedPane', '')
		assert_p('Table', 'Content', '[[Product, true], [Order, true], [Summary, true], [Deptartment, true], [Store, true]]')
		select('Table', 'cell:Record,0(Product)')
		select('Table1', 'cell:Include,2(true)')
#		select('Table', ' ', 'Equivalent Record,3')
#		select('Table', 'cell:Record,2(Product)')
#		select('Table1', 'cell:Equivalent Field,2(quantity)')
#		select('Table', 'cell:Record,2(Product)')
#		select('Table1', '', 'Equivalent Field,2')

		select('Table', 'cell:Record,0(Product)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('Table', 'Content', '[[, Old, 8, 69684558, 40118, 5010], [, New, 8, 69694158, , 19123], [, Deleted, 9, 69694158, 40118, 19000], [, , , , , ], [, Deleted, 10, 69694158, 40118, -19000], [, , , , , ]]')
	close()
