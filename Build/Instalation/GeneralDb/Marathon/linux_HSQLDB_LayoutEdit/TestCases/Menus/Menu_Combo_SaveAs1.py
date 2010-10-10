useFixture(default)

def test():
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*5')
		select('TextField', 'Powers of 11')
		select('TextField1', '4')
		click('Insert')
		select('ComboBox', 'Key / Value Combo')
		select('AbsJTable', 'cell:Combo Code,0()')
		assert_p('AbsJTable', 'Content', '[[, ], [, ], [, ], [, ]]')
		select('AbsJTable', 'cell:Combo Code,0()')
		select('ComboBox', 'Standard Combo')
		select('AbsJTable', 'cell:Combo Code,0()')
		assert_p('AbsJTable', 'Content', '[[], [], [], []]')
		select('AbsJTable', 'cell:Combo Code,0()')
		select('ComboBox', 'Key / Value Combo')
		select('AbsJTable', '1', 'Combo Code,0')
		select('AbsJTable', '11 to 0', 'Combo Value,0')
		select('AbsJTable', '11', 'Combo Code,1')
		select('AbsJTable', '11', 'Combo Value,1')
		select('AbsJTable', '12', 'Combo Code,2')
		select('AbsJTable', '121', 'Combo Code,2')
		select('AbsJTable', '11 squared', 'Combo Value,2')
		select('AbsJTable', '1331', 'Combo Code,3')
		select('AbsJTable', '11 cubed', 'Combo Value,3')
		select('AbsJTable', 'cell:Combo Value,2(11 squared)')
		assert_p('AbsJTable', 'Text', '11 squared', 'Combo Value,2')
		select('AbsJTable', 'cell:Combo Value,3(11 cubed)')
		assert_p('AbsJTable', 'Content', '[[1, 11 to 0], [11, 11], [121, 11 squared], [1331, 11 cubed]]')
		select('AbsJTable', 'cell:Combo Value,3(11 cubed)')
		click('Save')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		click('*4')
		select('TextField', 'Powers of 11%')
		select('BmKeyedComboBox', 'Unkown')
		click('SaveAs')

		if window('Input'):
			select('OptionPane.textField', 'Copy Powers of 11')
			click('OK')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('*4')
		select('TextField', 'Copy Powers of 11%')
		select('BmKeyedComboBox', 'Unkown')
		select('ComboJTbl', 'cell:Combo_Name,0(Copy Powers of 11)')
		select('ComboJTbl', 'cell:Combo_Name,0(Copy Powers of 11)')
		assert_p('ComboJTbl', 'Content', '[[0, Copy Powers of 11]]')
		select('ComboJTbl', 'cell:Combo_Name,0(Copy Powers of 11)')
		assert_p('ComboJTbl', 'Text', 'Copy Powers of 11', 'Combo_Name,0')
		select('ComboJTbl', 'cell:Combo_Name,0(Copy Powers of 11)')
		assert_p('TextField1', 'Text', 'Copy Powers of 11')
		select('AbsJTable', 'cell:Combo Code,1(11)')
		assert_p('AbsJTable', 'Content', '[[1, 11 to 0], [11, 11], [121, 11 squared], [1331, 11 cubed]]')
		select('AbsJTable', 'cell:Combo Code,2(121)')
		assert_p('AbsJTable', 'Text', '121', 'Combo Code,2')
		select('AbsJTable', 'cell:Combo Code,0(1)')
		click('Delete')

		if window('Delete: Copy Powers of 11'):
			click('Yes')
		close()

		select('TextField', 'Powers of 11%')
		click('Delete1')
		click('Delete')

		if window('Delete: Powers of 11'):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		#click('ScrollPane$ScrollBar', 11, 182)
	close()
