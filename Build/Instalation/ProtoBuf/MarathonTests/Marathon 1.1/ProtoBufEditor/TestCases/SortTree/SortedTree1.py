useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoSales.bin')
		##commonBits.setRecordLayout(select, 'DTAR020')
		click('Edit1')
		select_menu('View>>Sorted Field Tree')
		##select('List', 'sale')
		select('Table', 'store', 'Field,0')
		select('Table', 'department', 'Field,1')
		select('Table', 'cell:Field,1(department)')
		click('Build Tree')
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'store,1')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:keycode,3(null)')
		rightclick('JTreeTable', 'keycode,3')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:keycode,4(68634752)')
		##assert_p('JTreeTable', 'Content', '[[, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 68634752, 59, 40118, 410, 1, 8990], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ]]')
		assert_p('JTreeTable', 'Content', '[[, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 68634752, 59, 410, 40118, 1, 8990], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ]]')
	close()

