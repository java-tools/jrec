useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		select('FilePane$3', 'XfdDTAR020.csv')
		doubleclick('FilePane$3', '6')
		click('Edit1')
		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
		rightclick('LineList.FileDisplay_JTbl', '2|STORE-NO,6')
		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,3(20)')
		rightclick('LineList.FileDisplay_JTbl', '2|STORE-NO,3')
		select_menu('Hide Column')
		assert_p('LineList.FileDisplay_JTbl', 'Enabled', 'true')
		select('LineList.FileDisplay_JTbl', 'cell:3|DATE,4(40118)')
		rightclick('LineList.FileDisplay_JTbl', '3|DATE,4')
		select_menu('Show Column>>STORE-NO')
		select('LineList.FileDisplay_JTbl', 'cell:3|DATE,4(40118)')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 5.01], [69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89], [63674861, 20, 40118, 957, 10, 2.70], [65674532, 20, 40118, 929, 1, 3.59], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [62684217, 59, 40118, 957, 1, 9.99], [67674686, 59, 40118, 929, 1, 3.99]]')
	close()
