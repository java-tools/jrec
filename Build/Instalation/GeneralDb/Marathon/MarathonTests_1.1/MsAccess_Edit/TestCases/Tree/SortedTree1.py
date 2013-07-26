useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		commonBits.setRecordLayout(select, 'DTAR020')
		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Sorted Field Tree'))
#		select('List', 'DTAR020')
		select('Table', 'STORE-NO', commonBits.fl('Field') + ',0')
		select('Table', 'DEPT-NO', commonBits.fl('Field') + ',1')
		select('Table', 'cell:' + commonBits.fl('Field') + ',1(DEPT-NO)')
		click(commonBits.fl('Build Tree'))
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'STORE-NO,1')
		select_menu(commonBits.fl('Expand Tree'))
		select('JTreeTable', 'cell:KEYCODE-NO,3(null)')
		rightclick('JTreeTable', 'KEYCODE-NO,3')
		select_menu(commonBits.fl('Expand Tree'))
		select('JTreeTable', 'cell:KEYCODE-NO,4(68634752)')
		assert_p('JTreeTable', 'Content', '[[, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 68634752, 59, 40118, 410, 1, 8.99], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ]]')
	close()

