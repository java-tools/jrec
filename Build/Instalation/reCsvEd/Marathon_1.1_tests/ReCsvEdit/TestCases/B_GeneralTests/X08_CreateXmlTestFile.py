useFixture(default)

def test():
	from Modules import CommonBits
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		select('FilePane$3', 'XfdDTAR020.csv')
		doubleclick('FilePane$3', '6')

		click('Edit1')
		click('Export')
		select('TabbedPane', 'Xml')
		select('File Name_Txt', CommonBits.sampleDir() + 'ZC_xml_XfdDTAR020.csv.csv.xml')
		select('Edit Output File_Chk', 'true')
		select('Keep screen open_Chk', 'true')
		click('save file')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , UTF-8, 1.0, false, , , , , , , ], [, , , , , , , , , , , ], [, , , , 69684558, 20, 40118, 280, 1, 5.01, True, ], [, , , , 69694158, 20, 40118, 280, 1, 19.00, True, ], [, , , , 69694158, 20, 40118, 280, -1, -19.00, True, ], [, , , , 69694158, 20, 40118, 280, 1, 5.01, True, ], [, , , , 63604808, 20, 40118, 170, 1, 4.87, True, ], [, , , , 62684671, 20, 40118, 685, 1, 69.99, True, ], [, , , , 62684671, 20, 40118, 685, -1, -69.99, True, ], [, , , , 64634429, 20, 40118, 957, 1, 3.99, True, ], [, , , , 66624458, 20, 40118, 957, 1, 0.89, True, ], [, , , , 63674861, 20, 40118, 957, 10, 2.70, True, ], [, , , , 65674532, 20, 40118, 929, 1, 3.59, True, ], [, , , , 64614401, 59, 40118, 957, 1, 1.99, True, ], [, , , , 64614401, 59, 40118, 957, 1, 1.99, True, ], [, , , , 61664713, 59, 40118, 335, 1, 17.99, True, ], [, , , , 61664713, 59, 40118, 335, -1, -17.99, True, ], [, , , , 68634752, 59, 40118, 410, 1, 8.99, True, ], [, , , , 60614487, 59, 40118, 878, 1, 5.95, True, ], [, , , , 63644339, 59, 40118, 878, 1, 12.65, True, ], [, , , , 60694698, 59, 40118, 620, 1, 3.99, True, ], [, , , , 60664659, 59, 40118, 620, 1, 3.99, True, ], [, , , , 62684217, 59, 40118, 957, 1, 9.99, True, ], [, , , , 67674686, 59, 40118, 929, 1, 3.99, True, ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
