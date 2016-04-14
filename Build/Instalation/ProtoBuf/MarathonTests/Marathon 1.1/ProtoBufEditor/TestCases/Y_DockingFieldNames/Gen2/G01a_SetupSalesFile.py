useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.7.0_51'

	if window(commonBits.applicationName()):
		select('File_Txt', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Edit1')
		click('SaveAs')
		select('File Name', 'protoStoreSales3_zzz.bin')

		click('Save1')

##		select('LineTreeChild.FileDisplay_JTbl', '')
		rightclick('LineTreeChild.FileDisplay_JTbl', 'Tree,39')
		select_menu('Collapse Tree')
		select('LineTreeChild.Layouts_Txt', 'Prefered')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 20, Store: 20, , ], [, , , , , ], [, , 170, Department: 170, , ], [, , , , , ], [, , 63604808, 40118, 1, 4870], [, , 1, 4870, 1, ], [, , 280, Department: 280, , ], [, , , , , ], [, , 69684558, 40118, 1, 19000], [, , 69684558, 40118, -1, -19000], [, , 69684558, 40118, 1, 5010], [, , 69694158, 40118, 1, 19000], [, , 69694158, 40118, -1, -19000], [, , 69694158, 40118, 1, 5010], [, , 2, 10020, 6, ], [, , 685, Department: 685, , ], [, , , , , ], [, , 62684671, 40118, 1, 69990], [, , 62684671, 40118, -1, -69990], [, , 0, 0, 2, ], [, , 929, Department: 929, , ], [, , , , , ], [, , 65674532, 40118, 1, 3590], [, , 1, 3590, 1, ], [, , 957, Department: 957, , ], [, , , , , ], [, , 63674861, 40118, 10, 2700], [, , 64634429, 40118, 1, 3990], [, , 66624458, 40118, 1, 890], [, , 12, 7580, 3, ], [, , , , , ], [, , 63604808, 1, , ], [, , 69684558, 1, , ], [, , 69694158, 1, , ], [, , 65674532, 1, , ], [, , 63674861, 10, , ], [, , 64634429, 1, , ], [, , 66624458, 1, , ], [, , 16, 26060, 13, ], [, , 59, Store: 59, , ], [, , 166, Store: 166, , ], [, , 184, Store: 184, , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
