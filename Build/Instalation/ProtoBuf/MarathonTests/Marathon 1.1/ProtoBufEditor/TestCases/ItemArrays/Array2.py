useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() +  'protoSales11.bin')
		click('Edit1')

		select('Table', 'cell:2|store,0(20)')
		rightclick('Table', '4|saleDate,2')
##		select('Table', 'cell:2|store,0(20)')
		select_menu('Edit Record')
##		select('Table1', 'cell:2|store,0(20)')
		select('Table', 'cell:Text,10([\'\',\' 0\',\' 0 1\'])')
		select('Table', 'cell:Data,10([\'\',\' 0\',\' 0 1\'])')
		click('ArrowButton')
		select('Table', 'cell:Data,1( 0)')
		select('Table', ' 0456', 'Data,1')
		select('Table', 'cell:Data,2( 0 1)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('Table', 'Text', '')
		select('Table', 'cell:Data,6(5.01)')
		assert_p('Table', 'Content', '[[keycode, 1, , 69684558, 69684558], [store, 2, , 20, 20], [department, 3, , 280, 280], [saleDate, 4, , 40118, 40118], [quantity, 5, , 1, 1], [price, 6, , 5010, 5010], [priceFloat, 7, , 5.01, 5.01], [priceDouble, 8, , 5.01, 5.01], [saleType, 9, , SALE, SALE], [paymentType, 10, , CREDIT_CARD, CREDIT_CARD], [strArray, 11, , [\'\',\' 0456\',\' 0 1\'], [\'\',\' 0456\',\' 0 1\']]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'cell:2|store,0(20)')
		select('Table', 'cell:2|store,0(20)')

		select_menu('Window>>protoSales11.bin>>Table: ')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window(r'Save Changes to file: ' + commonBits.sampleDir() +  'protoSales11.bin'):
			click('No')
		close()
	close()
