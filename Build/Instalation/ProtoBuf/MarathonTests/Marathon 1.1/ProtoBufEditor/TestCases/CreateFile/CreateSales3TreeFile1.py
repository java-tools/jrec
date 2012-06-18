useFixture(default)

def test():
	from Modules import commonBits
	import os
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
##		os.remove(commonBits.sampleDir() + 'zzCreateSales3.bin')

		select('FileChooser', commonBits.sampleDir() + 'zzCreateSales3.bin')
		select('FileChooser1', commonBits.stdCopybookDir() +'StoreSales3.protocomp')
		select('ComboBox1', 'Compiled Proto')
		click('Edit1')
		assert_p('Table', 'Content', '[[store, 1, , 0, 0], [name, 2, , , ]]')
		select('Table', '11', 'Data,0')
		select('Table', 'name 11', 'Data,1')
		select('Table', 'cell:Data,0(11)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>zzCreateSales3.bin>>Tree View')
		select('LayoutCombo', 'Store')
		assert_p('JTreeTable', 'Content', '[[, , 11, name 11]]')
		select('JTreeTable', 'cell:Tree,0(null)')
		click('New')

		if window('Record Selection'):
			click('OK')
		close()

		select('Table', '121', 'Data,0')
		select('Table', 'name 121', 'Data,1')
		select('Table', 'cell:Data,0(121)')
		assert_p('Table', 'Content', '[[department, 1, , 121, 121], [name, 2, , name 121, name 121]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>zzCreateSales3.bin>>Tree View')
		select('LayoutCombo', 'Prefered')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,0')
		select_menu('Expand Tree')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,1')
		select_menu('Expand Tree')
		assert_p('JTreeTable', 'Content', '[[, , 11, name 11, , ], [, , , , , ], [, , 121, name 121, , ]]')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Edit1')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,1')
		select_menu('Expand Tree')
		assert_p('JTreeTable', 'Content', '[[, , 11, name 11], [, , , ], [, , , ]]')
		select('LayoutCombo', 'Prefered')
		assert_p('JTreeTable', 'Content', '[[, , 11, name 11, , ], [, , , , , ], [, , 121, name 121, , ]]')
		select('JTreeTable', 'cell:Tree,0(null)')
		click('Delete2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'zzCreateSales3.bin'):
			click('Yes')
		close()

		click('Edit1')
		assert_p('Table', 'Content', '[[store, 1, , 0, 0], [name, 2, , , ]]')
		click('Delete2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'zzCreateSales3.bin'):
			click('No')
		close()

		click('Edit1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('LayoutCombo', 'Store')
		assert_p('JTreeTable', 'Content', '[[, , 0, ]]')
		select('JTreeTable', 'cell:Tree,0(null)')
		click('Delete2')
		click('Save1')
		click('Save1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		os.remove(commonBits.sampleDir() + 'zzCreateSales3.bin')
	close()
