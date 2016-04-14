useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window(commonBits.applicationName()):
		select('FileNameTxtFld', commonBits.sampleDir() + 'protoSales.bin')
		click('Edit1')
		select_menu('View>>Sorted Field Tree')
		##select('List', 'sale')
		select('Table', 'quantity', 'Field,0')
		select('Table', 'store', 'Field,1')
		select('Table', 'cell:Field,1(store)')
		click('Build Tree')
		select('JTreeTable', 'cell:Tree,2(null)')
		select_menu('View>>Record View #{Selected Records#}')
##		select('JTreeTable', 'cell:Tree,2(null)')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,0(63674861)')
		assert_p('BaseLineAsColumn$LineAsColTbl', 'Text', 'cell:Data,0(63674861)')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,5(2700)')
		assert_p('BaseLineAsColumn$LineAsColTbl', 'Text', '2700', 'Data,5')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,5(2700)')
		doubleclick('TextField')
		assert_p('TextField', 'Text', '1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
