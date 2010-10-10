useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'zzzCsvTest4.csv')
		commonBits.setRecordLayout(select, 'zzzCsvTest4')
		click('Edit1')
		select('Table', 'cell:2|Array 1 (; and |),0(22)')
		rightclick('Table', '1|Field 1,0')
		select_menu('Edit Record')
		select('Table', 'cell:Data,0(111)')
		assert_p('Table', 'Text', '111', 'Data,0')
		select('Table', 'cell:Text,0(111)')
		assert_p('Table', 'Content', '[[Field 1, 1, , 111, 111], [Array 1 (; and |), 2, , 22, 22], [Field 3, 3, , 33, 33], [Array 2 (;), 4, , 44, 44], [Field 5, 5, , 55, 55], [Array 3 (:), 6, , 66, 66], [Array 4 (|), 7, , 77, 77]]')
		select('Table', '11144', 'Data,0')
		select('Table', 'cell:Text,0(11144)')
		assert_p('Table', 'Text', '11144', 'Text,0')
		doubleclick('TextArea')
		click('TextArea')
		rightclick('TextArea')
		#assert_p('TextArea', 'Text', '11144223344556677')
		select('Table', '111\'55', 'Data,0')
		select('Table', 'cell:Text,0(111\'55)')
		assert_p('Table', 'Text', '111\'55', 'Text,0')
		click('Right')
		select('Table', 'cell:Text,0(1)')
		assert_p('Table', 'Content', '[[Field 1, 1, , 1, 1], [Array 1 (; and |), 2, , 2;3;4|1;2;3;4|4;5;6, 2;3;4|1;2;3;4|4;5;6], [Field 3, 3, , 333, 333], [Array 2 (;), 4, , 2;3;4;5;6, 2;3;4;5;6], [Field 5, 5, , 555, 555], [Array 3 (:), 6, , 1:2:3, 1:2:3], [Array 4 (|), 7, , 1|33|33|11, 1|33|33|11]]')
		select('Table', 'cell:Text,1(2;3;4|1;2;3;4|4;5;6)')
		assert_p('Table', 'Text', '2;3;4|1;2;3;4|4;5;6', 'Text,1')
		select('Table', 'cell:Text,2(333)')
		doubleclick('TextArea')
		click('TextArea')
		#assert_p('TextArea', 'Text', '1"2;3;4|1;2;3;4|4;5;6"3332;3;4;5;65551:2:3"1|33|33|11"')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'zzzCsvTest4.csv'):
			click('No')
		close()
	close()
