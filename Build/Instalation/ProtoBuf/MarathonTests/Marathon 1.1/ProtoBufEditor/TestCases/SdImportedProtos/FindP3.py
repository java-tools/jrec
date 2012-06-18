useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3SDim.bin')
		click('Edit1')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,2')
		select_menu('Edit Record')
		click('Find1')
##		click('MetalInternalFrameTitlePane', 170, 15)
		select('TextField', '66')
		select('ComboBox', 'All Fields')
		select('ComboBox2', 'Backward')
		click('Find1')
		select_menu('Window>>protoStoreSales3SDim.bin>>Record: ')
		select('Table', 'cell:Data,0(60664659)')
		select('Table', 'cell:Data,1(1)')
		assert_p('Table', 'Content', '[[keycode, 1, , 60664659, 60664659], [quantity, 2, , 1, 1]]')
		select_menu('Window>>protoStoreSales3SDim.bin>>Find')
##		click('MetalInternalFrameTitlePane', 196, 16)


		##click('Find1')
		select('ComboBox', 'All Fields')
		click('Find1')


		select_menu('Window>>protoStoreSales3SDim.bin>>Record: ')
		select('Table', 'cell:Data,1(40118)')
		assert_p('Table', 'Content', '[[keycode, 1, , 60664659, 60664659], [saleDate, 2, , 40118, 40118], [quantity, 3, , 1, 1], [price, 4, , 3990, 3990]]')
##		click('BaseHelpPanel', 267, 0)
		click('Find1')
##		select_menu('Window>>protoStoreSales3SDim.bin>>Record: ')
##		select_menu('Window>>protoStoreSales3SDim.bin>>Find')
		select('ComboBox', 'All Fields')
		click('Find1')
		select_menu('Window>>protoStoreSales3SDim.bin>>Record: ')
		select('Table', 'cell:Data,0(61664713)')
		assert_p('Table', 'Content', '[[keycode, 1, , 61664713, 61664713], [saleDate, 2, , 40118, 40118], [quantity, 3, , -1, -1], [price, 4, , -17990, -17990]]')

		click('Find1')
		select('ComboBox', 'All Fields')
		click('Find1')

		select_menu('Window>>protoStoreSales3SDim.bin>>Record: ')
		select('Table', 'cell:Data,0(61664713)')
		assert_p('Table', 'Content', '[[keycode, 1, , 61664713, 61664713], [saleDate, 2, , 40118, 40118], [quantity, 3, , 1, 1], [price, 4, , 17990, 17990]]')

#		if window('Save Changes to file: ' + commonBits.sampleDir() + 'protoStoreSales3SDim.bin'):
#			click('No')
#		close()
	close()
