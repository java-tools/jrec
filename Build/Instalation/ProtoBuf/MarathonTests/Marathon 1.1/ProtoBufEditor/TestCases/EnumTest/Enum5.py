useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() +  'protoStoreSales6.bin')
		click('Edit1')
		##select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,0')
		select_menu('Fully Expand Tree')
		select('LayoutCombo', 'Product')
		assert_p('JTreeTable', 'Content', '[[, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 63604808, 40118, 1, 4870, SALE, CASH], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 69684558, 40118, 1, 19000, SALE, CHEQUE], [, , 69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [, , 69684558, 40118, 1, 5010, SALE, DEBIT_CARD], [, , 69694158, 40118, 1, 19000, SALE, ], [, , 69694158, 40118, -1, -19000, RETURN, CASH], [, , 69694158, 40118, 1, 5010, SALE, CHEQUE], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 62684671, 40118, 1, 69990, SALE, CREDIT_CARD], [, , 62684671, 40118, -1, -69990, RETURN, DEBIT_CARD], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 65674532, 40118, 1, 3590, SALE, ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 63674861, 40118, 10, 2700, SALE, CASH], [, , 64634429, 40118, 1, 3990, SALE, CHEQUE], [, , 66624458, 40118, 1, 890, SALE, CREDIT_CARD], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ]]')
		select('LayoutCombo', 'Prefered')
		assert_p('JTreeTable', 'Content', '[[, , 20, Store: 20, , , , ], [, , , , , , , ], [, , 170, Department: 170, , , , ], [, , , , , , , ], [, , 63604808, 40118, 1, 4870, SALE, CASH], [, , 1, 4870, 1, , , ], [, , 280, Department: 280, , , , ], [, , , , , , , ], [, , 69684558, 40118, 1, 19000, SALE, CHEQUE], [, , 69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [, , 69684558, 40118, 1, 5010, SALE, DEBIT_CARD], [, , 69694158, 40118, 1, 19000, SALE, ], [, , 69694158, 40118, -1, -19000, RETURN, CASH], [, , 69694158, 40118, 1, 5010, SALE, CHEQUE], [, , 2, 10020, 6, , , ], [, , 685, Department: 685, , , , ], [, , , , , , , ], [, , 62684671, 40118, 1, 69990, SALE, CREDIT_CARD], [, , 62684671, 40118, -1, -69990, RETURN, DEBIT_CARD], [, , 0, 0, 2, , , ], [, , 929, Department: 929, , , , ], [, , , , , , , ], [, , 65674532, 40118, 1, 3590, SALE, ], [, , 1, 3590, 1, , , ], [, , 957, Department: 957, , , , ], [, , , , , , , ], [, , 63674861, 40118, 10, 2700, SALE, CASH], [, , 64634429, 40118, 1, 3990, SALE, CHEQUE], [, , 66624458, 40118, 1, 890, SALE, CREDIT_CARD], [, , 12, 7580, 3, , , ], [, , , , , , , ], [, , 63604808, 1, , , , ], [, , 69684558, 1, , , , ], [, , 69694158, 1, , , , ], [, , 65674532, 1, , , , ], [, , 63674861, 10, , , , ], [, , 64634429, 1, , , , ], [, , 66624458, 1, , , , ], [, , 16, 26060, 13, , , ], [, , 59, Store: 59, , , , ], [, , 166, Store: 166, , , , ], [, , 184, Store: 184, , , , ]]')
		select('JTreeTable', 'rows:[7,8,9,10,11,12,13],columns:[Tree,keycode]')

		
		select_menu('View>>Table View #{Selected Records#}')
		
##		select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[Tree,keycode]')
		assert_p('Table', 'Content', '[[69684558, 40118, 1, 19000, SALE, CHEQUE], [69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [69684558, 40118, 1, 5010, SALE, DEBIT_CARD], [69694158, 40118, 1, 19000, SALE, ], [69694158, 40118, -1, -19000, RETURN, CASH], [69694158, 40118, 1, 5010, SALE, CHEQUE]]')
		select('Table', 'OTHER', '5|saleType,2')
		select('Table', 'cell:2|saleDate,2(40118)')
		assert_p('Table', 'Content', '[[69684558, 40118, 1, 19000, SALE, CHEQUE], [69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [69684558, 40118, 1, 5010, OTHER, DEBIT_CARD], [69694158, 40118, 1, 19000, SALE, ], [69694158, 40118, -1, -19000, RETURN, CASH], [69694158, 40118, 1, 5010, SALE, CHEQUE]]')
		select('Table', '', '6|paymentType,2')
		select('Table', 'cell:2|saleDate,2(40118)')
		assert_p('Table', 'Content', '[[69684558, 40118, 1, 19000, SALE, CHEQUE], [69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [69684558, 40118, 1, 5010, OTHER, ], [69694158, 40118, 1, 19000, SALE, ], [69694158, 40118, -1, -19000, RETURN, CASH], [69694158, 40118, 1, 5010, SALE, CHEQUE]]')
		select('Table', 'RETURN', '5|saleType,3')
		select('Table', 'cell:2|saleDate,3(40118)')
		assert_p('Table', 'Content', '[[69684558, 40118, 1, 19000, SALE, CHEQUE], [69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [69684558, 40118, 1, 5010, OTHER, ], [69694158, 40118, 1, 19000, RETURN, ], [69694158, 40118, -1, -19000, RETURN, CASH], [69694158, 40118, 1, 5010, SALE, CHEQUE]]')
		select('Table', 'CHEQUE', '6|paymentType,3')
		select('Table', 'cell:2|saleDate,3(40118)')
		assert_p('Table', 'Content', '[[69684558, 40118, 1, 19000, SALE, CHEQUE], [69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [69684558, 40118, 1, 5010, OTHER, ], [69694158, 40118, 1, 19000, RETURN, CHEQUE], [69694158, 40118, -1, -19000, RETURN, CASH], [69694158, 40118, 1, 5010, SALE, CHEQUE]]')
		select('Table', 'SALE', '5|saleType,4')
		select('Table', 'cell:3|quantity,4(-1)')
		assert_p('Table', 'Content', '[[69684558, 40118, 1, 19000, SALE, CHEQUE], [69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [69684558, 40118, 1, 5010, OTHER, ], [69694158, 40118, 1, 19000, RETURN, CHEQUE], [69694158, 40118, -1, -19000, SALE, CASH], [69694158, 40118, 1, 5010, SALE, CHEQUE]]')
		select('Table', '', '6|paymentType,4')
		select('Table', 'cell:3|quantity,4(-1)')
		assert_p('Table', 'Content', '[[69684558, 40118, 1, 19000, SALE, CHEQUE], [69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [69684558, 40118, 1, 5010, OTHER, ], [69694158, 40118, 1, 19000, RETURN, CHEQUE], [69694158, 40118, -1, -19000, SALE, ], [69694158, 40118, 1, 5010, SALE, CHEQUE]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[Tree,keycode]')
		select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[Tree,keycode]')
		select_menu('Window>>protoStoreSales6.bin>>Tree View')
		select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[Tree,keycode]')
		assert_p('JTreeTable', 'Content', '[[, , 20, Store: 20, , , , ], [, , , , , , , ], [, , 170, Department: 170, , , , ], [, , , , , , , ], [, , 63604808, 40118, 1, 4870, SALE, CASH], [, , 1, 4870, 1, , , ], [, , 280, Department: 280, , , , ], [, , , , , , , ], [, , 69684558, 40118, 1, 19000, SALE, CHEQUE], [, , 69684558, 40118, -1, -19000, RETURN, CREDIT_CARD], [, , 69684558, 40118, 1, 5010, OTHER, ], [, , 69694158, 40118, 1, 19000, RETURN, CHEQUE], [, , 69694158, 40118, -1, -19000, SALE, ], [, , 69694158, 40118, 1, 5010, SALE, CHEQUE], [, , 2, 10020, 6, , , ], [, , 685, Department: 685, , , , ], [, , , , , , , ], [, , 62684671, 40118, 1, 69990, SALE, CREDIT_CARD], [, , 62684671, 40118, -1, -69990, RETURN, DEBIT_CARD], [, , 0, 0, 2, , , ], [, , 929, Department: 929, , , , ], [, , , , , , , ], [, , 65674532, 40118, 1, 3590, SALE, ], [, , 1, 3590, 1, , , ], [, , 957, Department: 957, , , , ], [, , , , , , , ], [, , 63674861, 40118, 10, 2700, SALE, CASH], [, , 64634429, 40118, 1, 3990, SALE, CHEQUE], [, , 66624458, 40118, 1, 890, SALE, CREDIT_CARD], [, , 12, 7580, 3, , , ], [, , , , , , , ], [, , 63604808, 1, , , , ], [, , 69684558, 1, , , , ], [, , 69694158, 1, , , , ], [, , 65674532, 1, , , , ], [, , 63674861, 10, , , , ], [, , 64634429, 1, , , , ], [, , 66624458, 1, , , , ], [, , 16, 26060, 13, , , ], [, , 59, Store: 59, , , , ], [, , 166, Store: 166, , , , ], [, , 184, Store: 184, , , , ]]')
		select_menu('Utilities>>Compare with Disk')
		assert_p('Table', 'Content', '[[, Old, 8, 69684558, 40118, 1, 5010, SALE, DEBIT_CARD], [, New, 8, , , , , OTHER, ], [, Old, 9, 69694158, 40118, 1, 19000, SALE, ], [, New, 9, , , , , RETURN, CHEQUE], [, Old, 10, 69694158, 40118, -1, -19000, RETURN, CASH], [, New, 10, , , , , SALE, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() +  'protoStoreSales6.bin'):
			click('No')
		close()
	close()
