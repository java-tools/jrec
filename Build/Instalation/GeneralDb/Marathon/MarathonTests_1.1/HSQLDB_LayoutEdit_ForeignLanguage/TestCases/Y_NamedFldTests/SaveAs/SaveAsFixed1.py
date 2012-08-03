useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'DTAR020_tst1.bin')
		click(commonBits.fl('Edit') + '1')
		select('LineList.FileDisplay_JTbl', 'rows:[3,4,5,6,7,8,9,10,11],columns:[1 - 8|KEYCODE-NO]')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Table View #{Selected Records#}'))
##		select('LineList.FileDisplay_JTbl1', 'rows:[3,4,5,6,7,8,9,10,11],columns:[1 - 8|KEYCODE-NO]')
		click('Export')
		select('TabbedPane', commonBits.fl('Fixed')
)
		select('names on first line_Chk', 'true')
		select('space between fields_Chk', 'true')
		select('Edit Output File_Chk', 'true')
		select('Keep screen open_Chk', 'true')
		click(commonBits.fl('Save File'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, STORE-NO,  DATE, DEPT-NO, QTY-SOLD, SALE-PRICE], [69694158,       20, 40118,     280,        1,       5.01], [62684671,       20, 40118,     685,        1,      69.99], [62684671,       20, 40118,     685,       -1,     -69.99], [61664713,       59, 40118,     335,        1,      17.99], [61664713,       59, 40118,     335,       -1,     -17.99], [61684613,       59, 40118,     335,        1,      12.99], [68634752,       59, 40118,     410,        1,       8.99], [60694698,       59, 40118,     620,        1,       3.99], [60664659,       59, 40118,     620,        1,       3.99]]')
		select('LineList.Layouts_Txt', commonBits.fl('Full Line'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO STORE-NO  DATE DEPT-NO QTY-SOLD SALE-PRICE], [69694158         20 40118     280        1       5.01], [62684671         20 40118     685        1      69.99], [62684671         20 40118     685       -1     -69.99], [61664713         59 40118     335        1      17.99], [61664713         59 40118     335       -1     -17.99], [61684613         59 40118     335        1      12.99], [68634752         59 40118     410        1       8.99], [60694698         59 40118     620        1       3.99], [60664659         59 40118     620        1       3.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('What to Save_Txt', commonBits.fl('File'))
		click(commonBits.fl('Save File'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, STORE-NO,  DATE, DEPT-NO, QTY-SOLD, SALE-PRICE], [63604808,       20, 40118,     170,        1,       4.87], [69684558,       20, 40118,     280,        1,      19.00], [69684558,       20, 40118,     280,       -1,     -19.00], [69694158,       20, 40118,     280,        1,       5.01], [62684671,       20, 40118,     685,        1,      69.99], [62684671,       20, 40118,     685,       -1,     -69.99], [61664713,       59, 40118,     335,        1,      17.99], [61664713,       59, 40118,     335,       -1,     -17.99], [61684613,       59, 40118,     335,        1,      12.99], [68634752,       59, 40118,     410,        1,       8.99], [60694698,       59, 40118,     620,        1,       3.99], [60664659,       59, 40118,     620,        1,       3.99], [60614487,       59, 40118,     878,        1,       5.95], [68654655,      166, 40118,      60,        1,       5.08], [69624033,      166, 40118,      80,        1,      18.19], [60604100,      166, 40118,      80,        1,      13.30], [68674560,      166, 40118,     170,        1,       5.99]]')
		select('LineList.Layouts_Txt', commonBits.fl('Full Line'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO STORE-NO  DATE DEPT-NO QTY-SOLD SALE-PRICE], [63604808         20 40118     170        1       4.87], [69684558         20 40118     280        1      19.00], [69684558         20 40118     280       -1     -19.00], [69694158         20 40118     280        1       5.01], [62684671         20 40118     685        1      69.99], [62684671         20 40118     685       -1     -69.99], [61664713         59 40118     335        1      17.99], [61664713         59 40118     335       -1     -17.99], [61684613         59 40118     335        1      12.99], [68634752         59 40118     410        1       8.99], [60694698         59 40118     620        1       3.99], [60664659         59 40118     620        1       3.99], [60614487         59 40118     878        1       5.95], [68654655        166 40118      60        1       5.08], [69624033        166 40118      80        1      18.19], [60604100        166 40118      80        1      13.30], [68674560        166 40118     170        1       5.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('FixedColNames_JTbl', 'cell:' + commonBits.fl('Include') + ',2(true)')
		select('FixedColNames_JTbl', 'cell:' + commonBits.fl('Include') + ',4(true)')
		click(commonBits.fl('Save File'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, STORE-NO, DEPT-NO, SALE-PRICE], [63604808,       20,     170,       4.87], [69684558,       20,     280,      19.00], [69684558,       20,     280,     -19.00], [69694158,       20,     280,       5.01], [62684671,       20,     685,      69.99], [62684671,       20,     685,     -69.99], [61664713,       59,     335,      17.99], [61664713,       59,     335,     -17.99], [61684613,       59,     335,      12.99], [68634752,       59,     410,       8.99], [60694698,       59,     620,       3.99], [60664659,       59,     620,       3.99], [60614487,       59,     878,       5.95], [68654655,      166,      60,       5.08], [69624033,      166,      80,      18.19], [60604100,      166,      80,      13.30], [68674560,      166,     170,       5.99]]')
		select('LineList.Layouts_Txt', commonBits.fl('Full Line'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO STORE-NO DEPT-NO SALE-PRICE], [63604808         20     170       4.87], [69684558         20     280      19.00], [69684558         20     280     -19.00], [69694158         20     280       5.01], [62684671         20     685      69.99], [62684671         20     685     -69.99], [61664713         59     335      17.99], [61664713         59     335     -17.99], [61684613         59     335      12.99], [68634752         59     410       8.99], [60694698         59     620       3.99], [60664659         59     620       3.99], [60614487         59     878       5.95], [68654655        166      60       5.08], [69624033        166      80      18.19], [60604100        166      80      13.30], [68674560        166     170       5.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('What to Save_Txt', commonBits.fl('Current View'))
		click(commonBits.fl('Save File'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, STORE-NO, DEPT-NO, SALE-PRICE], [69694158,       20,     280,       5.01], [62684671,       20,     685,      69.99], [62684671,       20,     685,     -69.99], [61664713,       59,     335,      17.99], [61664713,       59,     335,     -17.99], [61684613,       59,     335,      12.99], [68634752,       59,     410,       8.99], [60694698,       59,     620,       3.99], [60664659,       59,     620,       3.99]]')
		select('LineList.Layouts_Txt', commonBits.fl('Full Line'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO STORE-NO DEPT-NO SALE-PRICE], [69694158         20     280       5.01], [62684671         20     685      69.99], [62684671         20     685     -69.99], [61664713         59     335      17.99], [61664713         59     335     -17.99], [61684613         59     335      12.99], [68634752         59     410       8.99], [60694698         59     620       3.99], [60664659         59     620       3.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('LineList.FileDisplay_JTbl', 'rows:[2,3,4,5],columns:[1 - 8|KEYCODE-NO]')
		click('Export')
		select('TabbedPane', commonBits.fl('Fixed')
)
		select('names on first line_Chk', 'true')
		select('space between fields_Chk', 'true')
		select('Edit Output File_Chk', 'true')
		select('Keep screen open_Chk', 'true')
		select('What to Save_Txt', commonBits.fl('Selected Records'))
		click(commonBits.fl('Save File'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, STORE-NO,  DATE, DEPT-NO, QTY-SOLD, SALE-PRICE], [62684671,       20, 40118,     685,       -1,     -69.99], [61664713,       59, 40118,     335,        1,      17.99], [61664713,       59, 40118,     335,       -1,     -17.99], [61684613,       59, 40118,     335,        1,      12.99]]')
		select('LineList.Layouts_Txt', commonBits.fl('Full Line'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO STORE-NO  DATE DEPT-NO QTY-SOLD SALE-PRICE], [62684671         20 40118     685       -1     -69.99], [61664713         59 40118     335        1      17.99], [61664713         59 40118     335       -1     -17.99], [61684613         59 40118     335        1      12.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('FixedColNames_JTbl', 'cell:' + commonBits.fl('Include') + ',2(true)')
		select('FixedColNames_JTbl', 'cell:' + commonBits.fl('Include') + ',4(true)')
		click(commonBits.fl('Save File'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO, STORE-NO, DEPT-NO, SALE-PRICE], [62684671,       20,     685,     -69.99], [61664713,       59,     335,      17.99], [61664713,       59,     335,     -17.99], [61684613,       59,     335,      12.99]]')
		select('LineList.Layouts_Txt', commonBits.fl('Full Line'))
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[KEYCODE-NO STORE-NO DEPT-NO SALE-PRICE], [62684671         20     685     -69.99], [61664713         59     335      17.99], [61664713         59     335     -17.99], [61684613         59     335      12.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
