useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('File_Txt', commonBits.sampleDir() + 'protoStoreSales7.bin')
		click('Edit1')
##		select('LineTreeChild.FileDisplay_JTbl', '')
		rightclick('LineTreeChild.FileDisplay_JTbl', 'Tree,0')
		select_menu('Edit Record')
		select('TabbedPane', 'Record: ')
		click('Tree View')
		select('TabbedPane', 'Tree View')
		click('Filter1')
		select('Filter.RecordSelection_JTbl', 'false', 'Include,3')
		select('Filter.RecordSelection_JTbl', 'false', 'Include,2')
		select('Filter.RecordSelection_JTbl', 'false', 'Include,1')
		select('Filter.RecordSelection_JTbl', 'false', 'Include,0')
##		select('Filter.RecordSelection_JTbl', 'cell:Include,0(false)')
		click('Filter1')
##		select('TabbedPane1', 'Table:')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[20, Store: 20], [59, Store: 59], [166, Store: 166], [184, Store: 184]]')
		click('Record:  Store')
		select('TabbedPane', 'Record: ')
		assert_p('LineFrameTree.FileDisplay_JTbl', 'Content', '[[store, 1, , INT32, 20, 20], [name, 2, , STRING, Store: 20, Store: 20]]')
		click('Tree View')
		select('TabbedPane', 'Tree View')
		select('LineTreeChild.Layouts_Txt', 'Prefered')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 20, Store: 20, , , , , , ], [, , , , , , , , , ], [, , 170, Department: 170, , , , , , ], [, , , , , , , , , ], [, , 63604808, [40118], [1], [4870], [SALE], [4.87], [4.87], [\'\']], [, , 1, 4870, 1, , , , , ], [, , 280, Department: 280, , , , , , ], [, , , , , , , , , ], [, , 69684558, [40118, 40118, 40118], [1, -1, 1], [19000, -19000, 5010], [SALE, RETURN, SALE], [19.0, -19.0, 5.01], [19.0, -19.0, 5.01], [\'\',\' -1\',\' -1 1\']], [, , 69694158, [40118, 40118, 40118], [1, -1, 1], [19000, -19000, 5010], [SALE, RETURN, SALE], [19.0, -19.0, 5.01], [19.0, -19.0, 5.01], [\'\',\' -1\',\' -1 1\']], [, , 2, 10020, 6, , , , , ], [, , 685, Department: 685, , , , , , ], [, , , , , , , , , ], [, , 62684671, [40118, 40118], [1, -1], [69990, -69990], [SALE, RETURN], [69.99, -69.99], [69.99, -69.99], [\'\',\' -1\']], [, , 0, 0, 2, , , , , ], [, , 929, Department: 929, , , , , , ], [, , , , , , , , , ], [, , 65674532, [40118], [1], [3590], [SALE], [3.59], [3.59], [\'\']], [, , 1, 3590, 1, , , , , ], [, , 957, Department: 957, , , , , , ], [, , , , , , , , , ], [, , 63674861, [40118], [10], [2700], [SALE], [2.7], [2.7], [\'\']], [, , 64634429, [40118], [1], [3990], [SALE], [3.99], [3.99], [\'\']], [, , 66624458, [40118], [1], [890], [SALE], [0.89], [0.89], [\'\']], [, , 12, 7580, 3, , , , , ], [, , , , , , , , , ], [, , 63604808, 1, , , , , , ], [, , 69684558, 1, , , , , , ], [, , 69694158, 1, , , , , , ], [, , 65674532, 1, , , , , , ], [, , 63674861, 10, , , , , , ], [, , 64634429, 1, , , , , , ], [, , 66624458, 1, , , , , , ], [, , 16, 26060, 13, , , , , ], [, , 59, Store: 59, , , , , , ], [, , , , , , , , , ], [, , 335, Department: 335, , , , , , ], [, , , , , , , , , ], [, , 61664713, [40118, 40118], [1, -1], [17990, -17990], [SALE, RETURN], [17.99, -17.99], [17.99, -17.99], [\'\',\' -1\']], [, , 61684613, [40118], [1], [12990], [SALE], [12.99], [12.99], [\'\']], [, , 1, 12990, 3, , , , , ], [, , 410, Department: 410, , , , , , ], [, , 620, Department: 620, , , , , , ], [, , 878, Department: 878, , , , , , ], [, , 929, Department: 929, , , , , , ], [, , 957, Department: 957, , , , , , ], [, , , , , , , , , ], [, , 11, 69110, 13, , , , , ], [, , 166, Store: 166, , , , , , ], [, , 184, Store: 184, , , , , , ]]')
	close()
