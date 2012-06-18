useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3SD.bin')
		click('Edit1')
		select('JTreeTable', 'cell:Tree,0(null)')
		rightclick('JTreeTable', 'Tree,0')
		select_menu('Edit Record')
##		select('JTreeTable', 'cell:Tree,0(null)')
		click('Find1')
		select('TextField', '66')
		select('ComboBox', 'All Fields')
		click('Find1')
		select_menu('Window>>protoStoreSales3SD.bin>>Record: ')
		select('Table', 'cell:Data,1(40118)')
		assert_p('Table', 'Content', '[[keycode, 1, , 66624458, 66624458], [saleDate, 2, , 40118, 40118], [quantity, 3, , 1, 1], [price, 4, , 890, 890]]')
		select('Table', 'cell:Data,1(40118)')
#		select_menu('Window>>protostoresales3.bin>>Find')
#		select_menu('Window>>protostoresales3.bin>>Find')
#		zzzzz
		click('Find1')
		select('ComboBox', 'All Fields')
		click('Find1')

		select_menu('Window>>protoStoreSales3SD.bin>>Record: ')
##		select('Table', 'cell:Data,0(66624458)')
		assert_p('Table', 'Content', '[[keycode, 1, , 66624458, 66624458], [quantity, 2, , 1, 1]]')
		assert_p('Table', 'Content', '[[keycode, 1, , 66624458, 66624458], [quantity, 2, , 1, 1]]')
##		select('JTreeTable', 'cell:Tree,0(null)')
##		select('JTreeTable', 'cell:Tree,0(null)')
	close()
