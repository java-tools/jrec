useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'zzzCsvTest5.csv')
		commonBits.setRecordLayout(select, 'zzzCsvTest5')

		click('Edit1')
		select('Table', 'cell:2|Array 1 (; and colon),1(2;3;4:1;2;3;4:4;5;6)')
		select('TextField', '2;3 55 3;4:1;2;3;4:4;5;6')
		keystroke('TextField', 'Tab')
		select('Table', 'cell:3|Field 3,1(333)')
		select('Table', 'cell:2|Array 1 (; and colon),1(2;3 55 3;4:1;2;3;4:4;5;6)')
		assert_p('TextField', 'Text', '2;3 55 3;4:1;2;3;4:4;5;6')
		select('Table', 'cell:3|Field 3,1(333)')
		assert_p('Table', 'Content', '[[111, 22, 33, 44, 55, 66, 77], [1, 2;3 55 3;4:1;2;3;4:4;5;6, 333, 2;3;4;5;6, 555, 1:2:3, 777], [111, 22, 33, 44, 55, 66, 77], [1, 2;3;4:1;2;3;4:4;5;6, 333, 2;3;4;5;6, 555, 1:2:3, 777]]')
		select('Table', 'cell:3|Field 3,1(333)')
		select('Table', 'cell:2|Array 1 (; and colon),1(2;3 55 3;4:1;2;3;4:4;5;6)')
		click('ArrowButton')
		#select('Table', 'cell:2|Array 1 (; and colon),1(2;3 55 3;4:1;2;3;4:4;5;6)')
		assert_p('Table', 'Content', '[[2, 3 55 3, 4, ], [1, 2, 3, 4], [4, 5, 6, ]]')
		click('ArrowButton')
		select('Table', 'cell:3|Field 3,1(333)')
		select('LayoutCombo', 'Full Line')
		select('Table', 'cell:Full Line,1(1|2;3 55 3;4:1;2;3;4:4;5;6|333|2;3;4;5;6|555|1:2:3|777)')
		assert_p('Table', 'Text', '1|2;3 55 3;4:1;2;3;4:4;5;6|333|2;3;4;5;6|555|1:2:3|777', 'Full Line,1')
		select('Table', 'cell:Full Line,2(111|22|33|44|55|66|77)')
		assert_p('Table', 'Text', '111|22|33|44|55|66|77', 'Full Line,2')
		select('Table', 'cell:Full Line,3(1|2;3;4:1;2;3;4:4;5;6|333|2;3;4;5;6|555|1:2:3|777)')
		assert_p('Table', 'Content', '[[111|22|33|44|55|66|77], [1|2;3 55 3;4:1;2;3;4:4;5;6|333|2;3;4;5;6|555|1:2:3|777], [111|22|33|44|55|66|77], [1|2;3;4:1;2;3;4:4;5;6|333|2;3;4;5;6|555|1:2:3|777]]')
		select('Table', 'cell:Full Line,3(1|2;3;4:1;2;3;4:4;5;6|333|2;3;4;5;6|555|1:2:3|777)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'zzzCsvTest5.csv'):
			click('No')
		close()
	close()
