useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		commonBits.setRecordLayout(select, 'DTAR020')
		click('Edit1')
		select_menu('View>>Sorted Field Tree')
		select('List', 'DTAR020')
		select('Table', 'STORE-NO', 'Field,0')
		select('Table', 'DEPT-NO', 'Field,1')
		select('Table', 'cell:Field,1(DEPT-NO)')
		click('Build Tree')
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'STORE-NO,1')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:KEYCODE-NO,3(null)')
		rightclick('JTreeTable', 'KEYCODE-NO,3')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:KEYCODE-NO,4(68634752)')
		assert_p('JTreeTable', 'Content', '[[, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 68634752, 59, 40118, 410, 1, 8.99], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ]]')
	close()

