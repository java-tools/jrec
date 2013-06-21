useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'DTAR020_tst1.bin')
		click('Edit1')
		select_menu('Utilities>>Script Test Panel')
		select('Script_Txt', commonBits.scriptExampleDir() + 'SortTree_DTAR020.js')

		click('Run !!!')
##		select_menu('Window>>DTAR020_tst1.bin>>Table:')
##		select('TabbedPane', 'Tree View')
##		assert_p('TextArea', 'Text', r'''Script C:\Users\BruceTst/RecordEditor_HSQL\User/Scripts/Examples/SortTree_DTAR020.js run  !!!
## Output File: C:\Users\BruceTst/RecordEditor_HSQL\SampleFiles/DTAR020_tst1.bin.xxx''')

		assert_p('TextArea', 'Text', 'Script ' + commonBits.scriptExampleDir() + r'''SortTree_DTAR020.js run  !!!
 Output File: ''' + commonBits.sampleDir() + 'DTAR020_tst1.bin.xxx')

		assert_p('TextArea', 'Text', 'Script ' + commonBits.scriptExampleDir() + 'SortTree_DTAR020.js run  !!!\n Output File: ' + commonBits.sampleDir() + 'DTAR020_tst1.bin.xxx')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		click('Table:')
		select('TabbedPane', 'Table:')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95], [68654655, 166, 40118, 60, 1, 5.08], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30], [68674560, 166, 40118, 170, 1, 5.99]]')
		click('Tree View')
		select('TabbedPane', 'Tree View')
		assert_p('LineTree.FileDisplay_JTbl', 'Content', '[[, , , 20, , 170, 2, 9.88], [, , , 20, , 170, 1, 4.87], [, , 63604808, 20, 40118, 170, 1, 4.87], [, , , 20, , 280, 1, 5.01], [, , 69684558, 20, 40118, 280, 1, 19.00], [, , 69684558, 20, 40118, 280, -1, -19.00], [, , 69694158, 20, 40118, 280, 1, 5.01], [, , , 20, , 685, 0, 0.00], [, , 62684671, 20, 40118, 685, 1, 69.99], [, , 62684671, 20, 40118, 685, -1, -69.99], [, , , 59, , 335, 5, 35.91], [, , , 59, , 335, 1, 12.99], [, , 61664713, 59, 40118, 335, 1, 17.99], [, , 61664713, 59, 40118, 335, -1, -17.99], [, , 61684613, 59, 40118, 335, 1, 12.99], [, , , 59, , 410, 1, 8.99], [, , 68634752, 59, 40118, 410, 1, 8.99], [, , , 59, , 620, 2, 7.98], [, , 60694698, 59, 40118, 620, 1, 3.99], [, , 60664659, 59, 40118, 620, 1, 3.99], [, , , 59, , 878, 1, 5.95], [, , 60614487, 59, 40118, 878, 1, 5.95], [, , , 166, , 60, 4, 42.56], [, , , 166, , 60, 1, 5.08], [, , 68654655, 166, 40118, 60, 1, 5.08], [, , , 166, , 80, 2, 31.49], [, , 69624033, 166, 40118, 80, 1, 18.19], [, , 60604100, 166, 40118, 80, 1, 13.30], [, , , 166, , 170, 1, 5.99], [, , 68674560, 166, 40118, 170, 1, 5.99]]')
	close()
