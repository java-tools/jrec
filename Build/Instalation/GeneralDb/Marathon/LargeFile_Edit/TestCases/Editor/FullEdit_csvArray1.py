useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'zzzCsvTest4.csv')
		commonBits.setRecordLayout(select, 'zzzCsvTest4')

		click('Edit1')
		select('Table', 'cell:2|Array 1 (; and |),1(2;3;4|1;2;3;4|4;5;6)')
		click('ArrowButton')
		assert_p('Table', 'Content', '[[2, 3, 4, ], [1, 2, 3, 4], [4, 5, 6, ]]')
		click('ArrowButton')
		select('Table', 'cell:4|Array 2 (;),1(2;3;4;5;6)')
		click('ArrowButton')
		#select('Table', 'cell:A,2(4)')
		#select('Table', 'cell:A,2(4)')
		#select('Table', 'cell:A,2(4)')
		#select('Table', 'cell:A,2(4)')
		#select('Table', 'cell:A,2(4)')
		#select('Table', 'cell:A,2(4)')
		assert_p('Table', 'Content', '[[2], [3], [4], [5], [6]]')
		click('ArrowButton')
		select('Table', 'cell:4|Array 2 (;),1(2;3;4;5;6)')
		assert_p('TextField', 'Text', '2;3;4;5;6')
		#click('ArrowButton')
		#assert_p('Table', 'Content', '[[111, 22, 33, 44, 55, 66, 77], [1, 2;3 44 5;4|1;2;3;4|4;5;6, 333, 2;3;4 55 66;5;6, 555, 1:2:3, 1|33|33|11], [111, 22, 33, 44, 55, 66, 1|2|1], [1, 2;3;4|1;2;3;4|4;5;6, 333, 2;3;4;5;6, 555, 1:2:3, 11|2|11]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
