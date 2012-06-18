useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() +  'protoStoreSales6.bin')
		click('Edit1')
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,0')
		select_menu('Fully Expand Tree')
		select('LayoutCombo', 'Product')
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'saleDate,9')
		select_menu('Edit Record')
		assert_p('Table', 'Content', '[[keycode, 1, , 69684558, 69684558], [saleDate, 2, , 40118, 40118], [quantity, 3, , -1, -1], [price, 4, , -19000, -19000], [saleType, 5, , RETURN, RETURN], [paymentType, 6, , CREDIT_CARD, CREDIT_CARD]]')
		select('Table', 'OTHER', 'Data,4')
		select('Table', 'cell:Text,3(-19000)')
		assert_p('Table', 'Content', '[[keycode, 1, , 69684558, 69684558], [saleDate, 2, , 40118, 40118], [quantity, 3, , -1, -1], [price, 4, , -19000, -19000], [saleType, 5, , OTHER, OTHER], [paymentType, 6, , CREDIT_CARD, CREDIT_CARD]]')
		select('Table', 'DEBIT_CARD', 'Data,5')
		select('Table', 'DEBIT_CARD', 'Data,5')
		select('Table', 'cell:Data,2(-1)')
		assert_p('Table', 'Content', '[[keycode, 1, , 69684558, 69684558], [saleDate, 2, , 40118, 40118], [quantity, 3, , -1, -1], [price, 4, , -19000, -19000], [saleType, 5, , OTHER, OTHER], [paymentType, 6, , DEBIT_CARD, DEBIT_CARD]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>protoStoreSales6.bin>>Tree View')
		assert_p('JTreeTable', 'Content', '[[, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 63604808, 40118, 1, 4870, SALE, CASH], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 69684558, 40118, 1, 19000, SALE, CHEQUE], [, , 69684558, 40118, -1, -19000, OTHER, DEBIT_CARD], [, , 69684558, 40118, 1, 5010, SALE, DEBIT_CARD], [, , 69694158, 40118, 1, 19000, SALE, ], [, , 69694158, 40118, -1, -19000, RETURN, CASH], [, , 69694158, 40118, 1, 5010, SALE, CHEQUE], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 62684671, 40118, 1, 69990, SALE, CREDIT_CARD], [, , 62684671, 40118, -1, -69990, RETURN, DEBIT_CARD], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 65674532, 40118, 1, 3590, SALE, ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 63674861, 40118, 10, 2700, SALE, CASH], [, , 64634429, 40118, 1, 3990, SALE, CHEQUE], [, , 66624458, 40118, 1, 890, SALE, CREDIT_CARD], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() +  'protoStoreSales6.bin'):
			click('No')
		close()
	close()
