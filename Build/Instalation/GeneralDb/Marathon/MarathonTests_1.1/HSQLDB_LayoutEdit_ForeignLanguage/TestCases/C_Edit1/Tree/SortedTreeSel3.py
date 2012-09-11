useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Sorted Field Tree'))
#		select('List', 'DTAR020')
		select('Table', 'STORE-NO', commonBits.fl('Field') + ',0')
		select('Table', 'DEPT-NO', commonBits.fl('Field') + ',1')
		select('Table', 'cell:' + commonBits.fl('Field') + ',1(DEPT-NO)')
		click(commonBits.fl('Build Tree'))
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'KEYCODE-NO,1')
		select_menu(commonBits.fl('Expand Tree'))
		select('JTreeTable', 'cell:' + commonBits.fl('Tree') + ',4(null)')
		assert_p('JTreeTable', 'RowCount', '10')
		select('JTreeTable', 'cell:' + commonBits.fl('Tree') + ',3(null)')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Table View #{Selected Records#}'))
		select('JTreeTable', 'cell:' + commonBits.fl('Tree') + ',3(null)')
		select('Table', 'cell:9 - 2|STORE-NO,0(59)')
		assert_p('Table', 'Content', '[[68634752, 59, 40118, 410, 1, 8.99]]')
		select('Table', 'cell:9 - 2|STORE-NO,0(59)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'rows:[4,6],columns:[' + commonBits.fl('Tree') + ']')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Table View #{Selected Records#}'))
		select('JTreeTable', 'rows:[4,6],columns:[' + commonBits.fl('Tree') + ']')
		select('Table', 'cell:9 - 2|STORE-NO,1(59)')
		select('Table', 'cell:11 - 4|DATE,0(40118)')
		assert_p('Table', 'Content', '[[60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [67674686, 59, 40118, 929, 1, 3.99]]')
		select('Table', 'cell:11 - 4|DATE,0(40118)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'rows:[4,6],columns:[' + commonBits.fl('Tree') + ']')
		rightclick('JTreeTable', 'KEYCODE-NO,3')
		select_menu(commonBits.fl('Expand Tree'))
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'KEYCODE-NO,6')
		select('JTreeTable', 'cell:KEYCODE-NO,4(68634752)')
		select('JTreeTable', 'cell:KEYCODE-NO,4(68634752)')
		assert_p('JTreeTable', 'Content', '[[, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 68634752, 59, 40118, 410, 1, 8.99], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ]]')
		select('JTreeTable', 'cell:KEYCODE-NO,6(null)')
		rightclick('JTreeTable', 'KEYCODE-NO,6')
		select_menu(commonBits.fl('Expand Tree'))
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'KEYCODE-NO,10')
		select_menu(commonBits.fl('Expand Tree'))
		select('JTreeTable', 'cell:KEYCODE-NO,12(64614401)')
		assert_p('JTreeTable', 'Content', '[[, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , , , , , , ], [, , 68634752, 59, 40118, 410, 1, 8.99], [, , , , , , , ], [, , , , , , , ], [, , 60614487, 59, 40118, 878, 1, 5.95], [, , 63644339, 59, 40118, 878, 1, 12.65], [, , , , , , , ], [, , , , , , , ], [, , 64614401, 59, 40118, 957, 1, 1.99], [, , 64614401, 59, 40118, 957, 1, 1.99], [, , 62684217, 59, 40118, 957, 1, 9.99], [, , 64624770, 59, 40118, 957, 1, 2.59], [, , , , , , , ], [, , , , , , , ]]')
		select('JTreeTable', 'cell:KEYCODE-NO,13(62684217)')
		assert_p('JTreeTable', 'RowCount', '17')
		select('JTreeTable', 'rows:[4,7,12,14],columns:[KEYCODE-NO]')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Table View #{Selected Records#}'))
		select('JTreeTable', 'rows:[4,7,12,14],columns:[KEYCODE-NO]')
		select('Table', 'cell:9 - 2|STORE-NO,1(59)')
		assert_p('Table', 'Content', '[[68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [64614401, 59, 40118, 957, 1, 1.99], [64624770, 59, 40118, 957, 1, 2.59]]')
		select('Table', 'cell:1 - 8|KEYCODE-NO,3(64624770)')
		assert_p('Table', 'Text', '59', '9 - 2|STORE-NO,3')
		select('Table', 'cell:1 - 8|KEYCODE-NO,3(64624770)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Table View #{Selected Records#}'))
		select('Table', 'cell:9 - 2|STORE-NO,1(59)')
		assert_p('Table', 'Content', '[[68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [64614401, 59, 40118, 957, 1, 1.99], [64624770, 59, 40118, 957, 1, 2.59]]')
		select('Table', 'cell:9 - 2|STORE-NO,1(59)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'rows:[2,9,11],columns:[' + commonBits.fl('Tree') + ']')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Table View #{Selected Records#}'))
		select('JTreeTable', 'rows:[2,9,11],columns:[' + commonBits.fl('Tree') + ']')
		select('Table', 'cell:9 - 2|STORE-NO,2(59)')
		assert_p('Table', 'Content', '[[61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [67674686, 59, 40118, 929, 1, 3.99], [64614401, 59, 40118, 957, 1, 1.99]]')
	close()