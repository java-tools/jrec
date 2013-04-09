useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*5')
		select('TextField', 'Powers of 11')
		select('TextField1', '4')
		click(commonBits.fl('Insert'))


		select('ComboBox', commonBits.fl('Key / Value Combo'))
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Code') + ',0()')
		assert_p('AbsJTable', 'Content', '[[, ], [, ], [, ], [, ]]')
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Code') + ',0()')
		select('ComboBox', commonBits.fl('Standard Combo'))
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Code') + ',0()')
		assert_p('AbsJTable', 'Content', '[[], [], [], []]')
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Code') + ',0()')
		select('ComboBox', commonBits.fl('Key / Value Combo'))
		select('AbsJTable', '1', commonBits.fl('Combo Code') + ',0')
		select('AbsJTable', '11 to 0', commonBits.fl('Combo Value') + ',0')
		select('AbsJTable', '11', commonBits.fl('Combo Code') + ',1')
		select('AbsJTable', '11', commonBits.fl('Combo Value') + ',1')
		select('AbsJTable', '12', commonBits.fl('Combo Code') + ',2')
		select('AbsJTable', '121', commonBits.fl('Combo Code') + ',2')
		select('AbsJTable', '11 squared', commonBits.fl('Combo Value') + ',2')
		select('AbsJTable', '1331', commonBits.fl('Combo Code') + ',3')
		select('AbsJTable', '11 cubed', commonBits.fl('Combo Value') + ',3')
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Value') + ',2(11 squared)')
		assert_p('AbsJTable', 'Text', '11 squared', commonBits.fl('Combo Value') + ',2')
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Value') + ',3(11 cubed)')
		assert_p('AbsJTable', 'Content', '[[1, 11 to 0], [11, 11], [121, 11 squared], [1331, 11 cubed]]')
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Value') + ',3(11 cubed)')
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
		select('ComboJTbl', 'cell:' + commonBits.fl('Combo_Name') + ',0(Copy Powers of 11)')
		select('ComboJTbl', 'cell:' + commonBits.fl('Combo_Name') + ',0(Copy Powers of 11)')
		assert_p('ComboJTbl', 'Content', '[[0, Copy Powers of 11]]')
		select('ComboJTbl', 'cell:' + commonBits.fl('Combo_Name') + ',0(Copy Powers of 11)')
		assert_p('ComboJTbl', 'Text', 'Copy Powers of 11', commonBits.fl('Combo_Name') + ',0')
		select('ComboJTbl', 'cell:' + commonBits.fl('Combo_Name') + ',0(Copy Powers of 11)')
		assert_p('TextField1', 'Text', 'Copy Powers of 11')
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Code') + ',1(11)')
		assert_p('AbsJTable', 'Content', '[[1, 11 to 0], [11, 11], [121, 11 squared], [1331, 11 cubed]]')
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Code') + ',2(121)')
		assert_p('AbsJTable', 'Text', '121', commonBits.fl('Combo Code') + ',2')
		select('AbsJTable', 'cell:' + commonBits.fl('Combo Code') + ',0(1)')
		click('Delete')

		if window(commonBits.fl('Delete: Copy Powers of 11')):
			click('Yes')
		close()

		select('TextField', 'Powers of 11%')
		click(commonBits.fl('Delete'))
		click('Delete')

		if window(commonBits.fl('Delete: Powers of 11')):
			click('Yes')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		#click('ScrollPane$ScrollBar', 11, 182)
	close()
