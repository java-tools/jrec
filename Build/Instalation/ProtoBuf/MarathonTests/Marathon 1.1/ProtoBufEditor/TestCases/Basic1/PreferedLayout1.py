useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protostoresales3.bin')
		click('Edit1')
##		select('JTreeTable', '')

		select_menu('File>>Recent Files>>protostoresales3.bin')
		select('JTreeTable', 'cell:Tree,0(null)')
		rightclick('JTreeTable', 'Tree,0')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Tree,1(null)')
		rightclick('JTreeTable', 'Tree,1')
		select_menu('Expand Tree')

		rightclick('JTreeTable', 'Tree,2')

		select_menu('Fully Expand Tree')
		select('JTreeTable', 'cell:Tree,0(null)')
##		select('JTreeTable', '')
		rightclick('JTreeTable', 'Tree,7')
		select_menu('Fully Expand Tree')

		select('JTreeTable', 'cell:Tree,0(null)')
##		ZZZZ
		assert_p('JTreeTable', 'Content', '[[, , 20, Store: 20], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , , ], [, , 59, Store: 59], [, , 166, Store: 166], [, , 184, Store: 184]]')
		select('JTreeTable', 'cell:Tree,0(null)')
		select('LayoutCombo', 'Prefered')
		select('JTreeTable', 'cell:Tree,2(null)')
##		assert_p('JTreeTable', 'Content', '[[, , 20, Store: 20], [, , , ], [, , 170, Department: 170], [, , , ], [, , 63604808, 40118], [, , 1, 4870], [, , 280, Department: 280], [, , 685, Department: 685], [, , , ], [, , 62684671, 40118], [, , 62684671, 40118], [, , 0, 0], [, , 929, Department: 929], [, , 957, Department: 957], [, , , ], [, , 16, 26060], [, , 59, Store: 59], [, , 166, Store: 166], [, , 184, Store: 184]]')
		assert_p('JTreeTable', 'Content', '[[, , 20, Store: 20, , ], [, , , , , ], [, , 170, Department: 170, , ], [, , , , , ], [, , 63604808, 40118, 1, 4870], [, , 1, 4870, 1, ], [, , 280, Department: 280, , ], [, , 685, Department: 685, , ], [, , , , , ], [, , 62684671, 40118, 1, 69990], [, , 62684671, 40118, -1, -69990], [, , 0, 0, 2, ], [, , 929, Department: 929, , ], [, , 957, Department: 957, , ], [, , , , , ], [, , 16, 26060, 13, ], [, , 59, Store: 59, , ], [, , 166, Store: 166, , ], [, , 184, Store: 184, , ]]')
		select('JTreeTable', 'cell:Tree,2(null)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
