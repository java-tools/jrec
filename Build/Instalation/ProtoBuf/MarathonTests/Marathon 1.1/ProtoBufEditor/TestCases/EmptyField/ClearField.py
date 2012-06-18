useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoSales11.bin')
		click('Edit1')
		select('Table', 'cell:2|store,0(20)')
		rightclick('Table', '7|priceFloat,2')
##		select('Table', 'cell:2|store,0(20)')
		select_menu('Clear Field')
		select('Table', 'cell:2|store,0(20)')
		assert_p('Table', 'Text', '', '7|priceFloat,2')
		select('Table', 'cell:2|store,0(20)')
		assert_p('Table', 'Text', 'cell:2|store,0(20)')
		select('Table', 'cell:2|store,0(20)')
		assert_p('Table', 'Text', 'cell:2|store,0(20)')
		select('Table', 'cell:2|store,0(20)')
		rightclick('Table', '8|priceDouble,2')
		select_menu('Clear Field')
		select('Table', 'cell:2|store,0(20)')
		assert_p('Table', 'Text', 'cell:2|store,0(20)')
		select('Table', 'cell:2|store,0(20)')
		assert_p('Table', 'Text', 'cell:2|store,0(20)')
		select('Table', 'cell:2|store,0(20)')
		assert_p('Table', 'Text', 'DEBIT_CARD', '10|paymentType,3')
##		select('Table', 'cell:2|store,0(20)')
##		select_menu('Clear Field')
		select('Table', 'cell:2|store,0(20)')
		assert_p('Table', 'Text', 'cell:2|store,0(20)')

	close()
