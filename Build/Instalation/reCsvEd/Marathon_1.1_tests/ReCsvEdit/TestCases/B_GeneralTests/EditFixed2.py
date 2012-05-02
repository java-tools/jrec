useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		select('FilePane$3', 'DTAR020.bin')
		select('TabbedPane', 'Fixed Width')
		select('FilePane$3', 'DTAR020.bin')
		doubleclick('FilePane$3', '2')
		select('FilePane$3', 'DTAR020.bin')
		click('FilePane$3', 3, '2')
		click('Edit3')
		select('LineList.FileDisplay_JTbl', 'rows:[4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21],columns:[9 - 2|STORE-NO,11 - 4|DATE]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('LineList.FileDisplay_JTbl1', 'rows:[5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21],columns:[9 - 2|STORE-NO,11 - 4|DATE]')
##		select('LineList.FileDisplay_JTbl1', 'rows:[5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21],columns:[9 - 2|STORE-NO,11 - 4|DATE]')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89], [63674861, 20, 40118, 957, 10, 2.70], [65674532, 20, 40118, 929, 1, 3.59], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99]]')
		select('LineList.Layouts_Txt', 'Hex 1 Line')
		assert_p('LineList.FileDisplay_JTbl', 'Text', 'f6f2f6f8f4f6f7f1020c0040118c685c000000001c00000006999c', '         +         1         +         2         +         3|Hex (1 Line),2')
		assert_p('LineList.FileDisplay_JTbl', 'Text', 'f6f6f6f2f4f4f5f8020c0040118c957c000000001c00000000089c', '         +         1         +         2         +         3|Hex (1 Line),5')
		assert_p('LineList.FileDisplay_JTbl', 'Text', 'f6f8f6f3f4f7f5f2059c0040118c410c000000001c00000000899c', '         +         1         +         2         +         3|Hex (1 Line),12')
		assert_p('LineList.FileDisplay_JTbl', 'Text', 'f6f0f6f9f4f6f9f8059c0040118c620c000000001c00000000399c', '         +         1         +         2         +         3|Hex (1 Line),15')
	close()
