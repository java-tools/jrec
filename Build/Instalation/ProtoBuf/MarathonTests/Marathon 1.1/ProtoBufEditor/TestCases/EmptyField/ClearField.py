useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window(commonBits.applicationName()):
		select('FileNameTxtFld', commonBits.sampleDir() + 'protoSales11.bin')
		click('Edit1')
		select('LinesTbl', 'cell:2|store,0(20)')
		rightclick('LinesTbl', '7|priceFloat,2')
##		select('LinesTbl', 'cell:2|store,0(20)')
		select_menu('Clear Field')
		select('LinesTbl', 'cell:2|store,0(20)')
		assert_p('LinesTbl', 'Text', '', '7|priceFloat,2')
		select('LinesTbl', 'cell:2|store,0(20)')
		assert_p('LinesTbl', 'Text', 'cell:2|store,0(20)')
		select('LinesTbl', 'cell:2|store,0(20)')
		assert_p('LinesTbl', 'Text', 'cell:2|store,0(20)')
		select('LinesTbl', 'cell:2|store,0(20)')
		rightclick('LinesTbl', '8|priceDouble,2')
		select_menu('Clear Field')
		select('LinesTbl', 'cell:2|store,0(20)')
		assert_p('LinesTbl', 'Text', 'cell:2|store,0(20)')
		select('LinesTbl', 'cell:2|store,0(20)')
		assert_p('LinesTbl', 'Text', 'cell:2|store,0(20)')
		select('LinesTbl', 'cell:2|store,0(20)')
		assert_p('LinesTbl', 'Text', 'DEBIT_CARD', '10|paymentType,3')
##		select('LinesTbl', 'cell:2|store,0(20)')
##		select_menu('Clear Field')
		select('LinesTbl', 'cell:2|store,0(20)')
		assert_p('LinesTbl', 'Text', 'cell:2|store,0(20)')

	close()
