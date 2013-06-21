useFixture(reCsvEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		select('FilePane$3', 'DTAR020.csv')
		doubleclick('FilePane$3', '3')
		assert_p('Table', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69684558, 20, 40118, 280, 1, 5.01], [69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89], [63674861, 20, 40118, 957, 10, 2.70], [65674532, 20, 40118, 929, 1, 3.59], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [68634752, 59, 40118, 410, 1, 8.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [62684217, 59, 40118, 957, 1, 9.99], [67674686, 59, 40118, 929, 1, 3.99], [61684613, 59, 40118, 335, 1, 12.99], [64624770, 59, 40118, 957, 1, 2.59], [69694814, 166, 40118, 360, 1, 2.50], [69694814, 166, 40118, 360, 1, 2.50], [69644164, 166, 40118, 193, 1, 21.59]]')
		select('FilePane$3', 'DTAR020.csv')
		assert_p('Parser_Txt', 'Text', commonBits.fl('Basic Parser'))
		select('FilePane$3', 'DTAR020.csv')
		assert_p('Quote Character_Txt', 'Text', commonBits.fl('<None>'))
		select('FilePane$3', 'DTAR020.csv')
		assert_p('DelimiterCombo', 'Text', commonBits.fl('<Tab>'))
		select('FilePane$3', 'DTAR020.csv')
		click(commonBits.fl('Edit') + '1')

		assert_p('LineList.Layouts_Txt', 'Text', 'GeneratedCsvRecord')
		select('LineList.FileDisplay_JTbl', 'rows:[2,3,4,5,6,7,8,9,10,11,12,13,14],columns:[1|keycode-no]')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Table View #{Selected Records#}'))
##		select('TabbedPane', 'Table:')
		select('LineList.FileDisplay_JTbl1', 'cell:3|Date,5(40118)')
		assert_p('LineList.FileDisplay_JTbl1', 'Content', '[[69684558, 20, 40118, 280, 1, 5.01], [69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89], [63674861, 20, 40118, 957, 10, 2.70], [65674532, 20, 40118, 929, 1, 3.59], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99]]')
		select('LineList.FileDisplay_JTbl1', 'cell:3|Date,5(40118)')
		rightclick('LineList.FileDisplay_JTbl1', '1|keycode-no,4')
		select_menu(commonBits.fl('Edit Record'))
		select('TabbedPane', 'Record:')
		select('LineFrame.FileDisplay_JTbl', 'cell:' + commonBits.fl('Data') + ',3(170)')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[keycode-no, 1, , ' + commonBits.fl('Char') + ', 63604808, 63604808], [Store-No, 2, , ' + commonBits.fl('Char') + ', 20, 20], [Date, 3, , ' + commonBits.fl('Char') + ', 40118, 40118], [Dept-No, 4, , ' + commonBits.fl('Char') + ', 170, 170], [Qty-Sold, 5, , ' + commonBits.fl('Char') + ', 1, 1], [Sale-Price, 6, , ' + commonBits.fl('Char') + ', 4.87, 4.87]]')
	close()
