useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.7.0_79'

	if window('Protocol Buffer Editor'):
		select('File_Txt',  commonBits.sampleDir() + 'protoStoreSalesMap.bin')
		select('Proto Definition_Txt', commonBits.stdCopybookDir() + 'StoreSalesMap.proto')

		select('Primary Message_Txt', 'Store')
		click('Edit1')
		select('LineTreeChild.Layouts_Txt', 'Prefered')
		assert_p('LineTreeChild.FileDisplay_JTbl', 'Content', '[[, , 20, Store: 20, , , ], [, , , , , , ], [, , 170, , , , ], [, , Department: 170, , , , ], [, , , , , , ], [, , 69684558, , , , ], [, , , , , , ], [, , , , , , ], [, , 40118, 1, 4870, SALE, 4.87], [, , 280, , , , ], [, , Department: 280, , , , ], [, , , , , , ], [, , 69694158, , , , ], [, , , , , , ], [, , , , , , ], [, , 40118, 1, 19000, SALE, 19.0], [, , 40118, -1, -19000, , -19.0], [, , 40118, 1, 5010, SALE, 5.01], [, , 62684671, , , , ], [, , , , , , ], [, , , , , , ], [, , 40118, 1, 19000, SALE, 19.0], [, , 40118, -1, -19000, , -19.0], [, , 40118, 1, 5010, SALE, 5.01], [, , 685, , , , ], [, , Department: 685, , , , ], [, , , , , , ], [, , 65674532, , , , ], [, , , , , , ], [, , , , , , ], [, , 40118, 1, 69990, SALE, 69.99], [, , 40118, -1, -69990, , -69.99], [, , 929, , , , ], [, , Department: 929, , , , ], [, , , , , , ], [, , 63674861, , , , ], [, , , , , , ], [, , , , , , ], [, , 40118, 1, 3590, SALE, 3.59], [, , 957, , , , ], [, , Department: 957, , , , ], [, , , , , , ], [, , 64634429, , , , ], [, , , , , , ], [, , 66624458, , , , ], [, , 61664713, , , , ], [, , , , , , ], [, , 59, Store: 59, , , ], [, , 166, Store: 166, , , ], [, , 184, Store: 184, , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
