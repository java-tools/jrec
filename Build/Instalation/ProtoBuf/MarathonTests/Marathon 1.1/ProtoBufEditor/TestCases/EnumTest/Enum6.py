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
		select('JTreeTable', 'rows:[7,8,9,10,11,12,13],columns:[keycode]')
		select_menu('View>>Column View #{Selected Records#}')
		##select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[keycode]')
		assert_p('Table', 'Content', '[[69684558, 69684558, 69684558, 69694158, 69694158, 69694158], [40118, 40118, 40118, 40118, 40118, 40118], [1, -1, 1, 1, -1, 1], [19000, -19000, 5010, 19000, -19000, 5010], [SALE, RETURN, SALE, SALE, RETURN, SALE], [CHEQUE, CREDIT_CARD, DEBIT_CARD, , CASH, CHEQUE]]')
		select('Table', 'OTHER', 'Row 1,4')
		select('Table', 'cell:Row 1,2(1)')
		assert_p('Table', 'Content', '[[69684558, 69684558, 69684558, 69694158, 69694158, 69694158], [40118, 40118, 40118, 40118, 40118, 40118], [1, -1, 1, 1, -1, 1], [19000, -19000, 5010, 19000, -19000, 5010], [OTHER, RETURN, SALE, SALE, RETURN, SALE], [CHEQUE, CREDIT_CARD, DEBIT_CARD, , CASH, CHEQUE]]')

		select('Table', 'cell:Row 2,5(CREDIT_CARD)')
		select('Table', '', 'Row 2,5')

		select('Table', 'cell:Row 2,2(-1)')

		assert_p('Table', 'Content', '[[69684558, 69684558, 69684558, 69694158, 69694158, 69694158], [40118, 40118, 40118, 40118, 40118, 40118], [1, -1, 1, 1, -1, 1], [19000, -19000, 5010, 19000, -19000, 5010], [OTHER, RETURN, SALE, SALE, RETURN, SALE], [CHEQUE, , DEBIT_CARD, , CASH, CHEQUE]]')

		select('Table', 'cell:Row 3,4(CREDIT_CARD)')
		select('Table', 'RETURN', 'Row 3,4')

		select('Table', 'cell:Row 3,2(1)')
		assert_p('Table', 'Content', '[[69684558, 69684558, 69684558, 69694158, 69694158, 69694158], [40118, 40118, 40118, 40118, 40118, 40118], [1, -1, 1, 1, -1, 1], [19000, -19000, 5010, 19000, -19000, 5010], [OTHER, RETURN, RETURN, SALE, RETURN, SALE], [CHEQUE, , DEBIT_CARD, , CASH, CHEQUE]]')

		select('Table', 'cell:Row 4,5(CREDIT_CARD)')
		select('Table', 'CREDIT_CARD', 'Row 4,5')
		select('Table', 'cell:Row 4,2(1)')
		assert_p('Table', 'Content', '[[69684558, 69684558, 69684558, 69694158, 69694158, 69694158], [40118, 40118, 40118, 40118, 40118, 40118], [1, -1, 1, 1, -1, 1], [19000, -19000, 5010, 19000, -19000, 5010], [OTHER, RETURN, RETURN, SALE, RETURN, SALE], [CHEQUE, , DEBIT_CARD, CREDIT_CARD, CASH, CHEQUE]]')

		select('Table', 'cell:Row 5,5(CREDIT_CARD)')
		select('Table', 'DEBIT_CARD', 'Row 5,5')

		select('Table', 'cell:Row 5,2(-1)')
		assert_p('Table', 'Content', '[[69684558, 69684558, 69684558, 69694158, 69694158, 69694158], [40118, 40118, 40118, 40118, 40118, 40118], [1, -1, 1, 1, -1, 1], [19000, -19000, 5010, 19000, -19000, 5010], [OTHER, RETURN, RETURN, SALE, RETURN, SALE], [CHEQUE, , DEBIT_CARD, CREDIT_CARD, DEBIT_CARD, CHEQUE]]')

		select('Table', 'cell:Row 6,4(CREDIT_CARD)')
		select('Table', 'OTHER', 'Row 6,4')
		select('Table', 'cell:Row 5,2(-1)')
		keystroke('Table', 'Ctrl+F6', 'Row 5,2')
##		select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[keycode]')
##		select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[keycode]')
		select_menu('Window>>protoStoreSales6.bin>>Column Table')
		select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[keycode]')
		select('Table', 'cell:Row 5,2(-1)')
		select('Table', 'cell:Row 5,2(-1)')
		assert_p('Table', 'Content', '[[69684558, 69684558, 69684558, 69694158, 69694158, 69694158], [40118, 40118, 40118, 40118, 40118, 40118], [1, -1, 1, 1, -1, 1], [19000, -19000, 5010, 19000, -19000, 5010], [OTHER, RETURN, RETURN, SALE, RETURN, OTHER], [CHEQUE, , DEBIT_CARD, CREDIT_CARD, DEBIT_CARD, CHEQUE]]')
		select('Table', 'cell:Row 5,2(-1)')
		select_menu('Window>>protoStoreSales6.bin>>Tree View')
		select('Table', 'cell:Row 5,2(-1)')
		select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[keycode]')
		select('JTreeTable', 'rows:[8,9,10,11,12,13],columns:[keycode]')
		assert_p('JTreeTable', 'Content', '[[, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 63604808, 40118, 1, 4870, SALE, CASH], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 69684558, 40118, 1, 19000, OTHER, CHEQUE], [, , 69684558, 40118, -1, -19000, RETURN, ], [, , 69684558, 40118, 1, 5010, RETURN, DEBIT_CARD], [, , 69694158, 40118, 1, 19000, SALE, CREDIT_CARD], [, , 69694158, 40118, -1, -19000, RETURN, DEBIT_CARD], [, , 69694158, 40118, 1, 5010, OTHER, CHEQUE], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 62684671, 40118, 1, 69990, SALE, CREDIT_CARD], [, , 62684671, 40118, -1, -69990, RETURN, DEBIT_CARD], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 65674532, 40118, 1, 3590, SALE, ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 63674861, 40118, 10, 2700, SALE, CASH], [, , 64634429, 40118, 1, 3990, SALE, CHEQUE], [, , 66624458, 40118, 1, 890, SALE, CREDIT_CARD], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() +  'protoStoreSales6.bin'):
			click('No')
		close()
	close()
