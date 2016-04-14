useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window(commonBits.applicationName()):
		select('FileNameTxtFld', commonBits.sampleDir() + 'protoStoreSales3SDim.bin')
		click('Edit1')
		rightclick('JTreeTable', 'Tree,1')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Tree,2(null)')
		assert_p('JTreeTable', 'Content', '[[, , 20, Store: 20], [, , 59, Store: 59], [, , , ], [, , , ], [, , , ], [, , 166, Store: 166], [, , 184, Store: 184]]')
##		assert_p('JTreeTable', 'Content', '[[, , 20, Store: 20], [, , 59, Store: 59], [, , , ], [, , , ], [, , , ], [, , 166, Store: 166], [, , 184, Store: 184]]')
#3		zzzz
		select('JTreeTable', 'cell:Tree,2(null)')
		rightclick('JTreeTable', 'Tree,2')
		select_menu('Expand Tree')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,3')
		select_menu('Edit Record')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,0(335)')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,0(335)')
		assert_p('BaseLineAsColumn$LineAsColTbl', 'Content', '[[department, 1, , 335, 335], [name, 2, , Department: 335, Department: 335]]')
		click('New')

		if window('Record Selection'):
			assert_p('OptionPane.comboBox', 'Text', 'product')
			assert_p('OptionPane.comboBox', 'Content', '[[product, department]]')
			click('OK')
		close()

		select('BaseLineAsColumn$LineAsColTbl', '0123', 'Data,0')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,0(123)')

		select('BaseLineAsColumn$LineAsColTbl', '0456', 'Data,1')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,1(456)')

		select('BaseLineAsColumn$LineAsColTbl', '0789', 'Data,2')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,2(789)')

		select('BaseLineAsColumn$LineAsColTbl', '6780', 'Data,3')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,2(789)')
		assert_p('BaseLineAsColumn$LineAsColTbl', 'Content', '[[keycode, 1, , 123, 123], [saleDate, 2, , 456, 456], [quantity, 3, , 789, 789], [price, 4, , 6780, 6780]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

##		if window('Save Changes to file: ' + commonBits.sampleDir() + 'protoStoreSales3SDim.bin'):
##			click('No')
##		close()
	close()
