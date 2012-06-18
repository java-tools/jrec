useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3im.bin')
		click('Edit1')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,1')
		select_menu('Expand Tree')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,2')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Tree,6(null)')
		assert_p('JTreeTable', 'Content', '[[, , 20, Store: 20], [, , 59, Store: 59], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , 166, Store: 166], [, , 184, Store: 184]]')
		select('JTreeTable', 'cell:Tree,6(null)')
		rightclick('JTreeTable', 'Tree,3')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Tree,4(null)')
		rightclick('JTreeTable', 'Tree,4')
		select_menu('Expand Tree')
		select('LayoutCombo', 'Deptartment')
		select('JTreeTable', 'cell:Tree,3(null)')
		assert_p('JTreeTable', 'Content', '[[, , , ], [, , , ], [, , , ], [, , 335, Department: 335], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , 410, Department: 410], [, , 620, Department: 620], [, , 878, Department: 878], [, , 929, Department: 929], [, , 957, Department: 957], [, , , ], [, , , ], [, , , ], [, , , ]]')
		select('JTreeTable', 'cell:Tree,3(null)')
		select('LayoutCombo', 'Product')
		select('JTreeTable', 'cell:Tree,3(null)')
		assert_p('JTreeTable', 'Content', '[[, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , 61664713, 40118, 1, 17990], [, , 61664713, 40118, -1, -17990], [, , 61684613, 40118, 1, 12990], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ]]')
		select('JTreeTable', 'cell:Tree,3(null)')
		click('New')

		if window('Record Selection'):
			assert_p('OptionPane.comboBox', 'Content', '[[product, department]]')
			click('OK')
		close()

		select('Table', '345', 'Data,0')
		select('Table', '678', 'Data,1')
		select('Table', '89', 'Data,2')
		select('Table', '11234', 'Data,3')
		select('Table', 'cell:Data,2(89)')
		assert_p('Table', 'Content', '[[keycode, 1, , 345, 345], [saleDate, 2, , 678, 678], [quantity, 3, , 89, 89], [price, 4, , 11234, 11234]]')
		click('New')
		select('Table', '987', 'Data,0')
		select('Table', '876', 'Data,1')
		select('Table', '654', 'Data,2')
		select('Table', '4321', 'Data,3')
		select('Table', 'cell:Data,2(654)')
		assert_p('Table', 'Content', '[[keycode, 1, , 987, 987], [saleDate, 2, , 876, 876], [quantity, 3, , 654, 654], [price, 4, , 4321, 4321]]')
		select_menu('Utilities>>Compare with Disk')
		select('Table', 'cell:Line No,1()')
		select('Table', 'cell:Line No,1()')
##		assert_p('Table', 'Content', '[[, Deleted, 38, 987, 876, 654, 4321], [, , , , , , ], [, Deleted, 39, 345, 678, 89, 11234], [, , , , , , ]]')
##		assert_p('Table', 'Content', '[[, , , , , , ], [, Inserted, 38, 987, 876, 654, 4321], [, , , , , , ], [, Inserted, 39, 345, 678, 89, 11234]]')

		assert_p('Table', 'Content', '[[, , , , , , ], [, Inserted, 38, 345, 678, 89, 11234], [, , , , , , ], [, Inserted, 39, 987, 876, 654, 4321]]')

		select('Table', 'cell:Line No,1()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>protoStoreSales3im.bin>>Tree View')
		select('JTreeTable', 'cell:keycode,6(61664713)')
##		assert_p('JTreeTable', 'Content', '[[, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , 61664713, 40118, 1, 17990], [, , 61664713, 40118, -1, -17990], [, , 61684613, 40118, 1, 12990], [, , 987, 876, 654, 4321], [, , 345, 678, 89, 11234], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ]]')
		assert_p('JTreeTable', 'Content', '[[, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , 61664713, 40118, 1, 17990], [, , 61664713, 40118, -1, -17990], [, , 61684613, 40118, 1, 12990], [, , 345, 678, 89, 11234], [, , 987, 876, 654, 4321], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ], [, , , , , ]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'protoStoreSales3im.bin'):
			click('No')
		close()


##		assert_p('TextArea', 'Text', '''
##
##Error updating Message in parent (missing required fields ???): Message missing required fields: saleDate, quantity, price
##
##Error updating Message in parent (missing required fields ???): Message missing required fields: quantity, price
##
##Error updating Message in parent (missing required fields ???): Message missing required fields: price
##
##Error updating Message in parent (missing required fields ???): Message missing required fields: saleDate, quantity, price
##
##Error updating Message in parent (missing required fields ???): Message missing required fields: quantity, price
##
##Error updating Message in parent (missing required fields ???): Message missing required fields: price''')
	close()
