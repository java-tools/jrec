useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'zxxzDTAR020_tst1.bin')
		select('Record Layout_Txt', 'FileWizard')
		assert_p('Record Layout_Txt', 'Text', 'FileWizard')
		click('Edit1')

		if window(''):
			assert_p('File Name_Txt', 'Text', commonBits.sampleDir() + 'zxxzDTAR020_tst1.bin')
			click('Right')
			select('TabbedPane', '')
			select('Length_Txt', '27')
			assert_p('Length_Txt', 'Text', '27')
			click('Right')
			select('TabbedPane', '')
			click('Right')
			select('TabbedPane', '')
			assert_p('Table', 'Content', '[[, 1, 8, 6, 0, true], [, 9, 2, 31, 0, true], [, 11, 4, 31, 0, true], [, 15, 2, 31, 0, true], [, 17, 5, 31, 0, true], [, 22, 6, 31, 0, true]]')
			assert_p('Table1', 'Content', '[[63604808, 20, 40118, 170, 1, 487], [69684558, 20, 40118, 280, 1, 1900], [69684558, 20, 40118, 280, -1, -1900], [69694158, 20, 40118, 280, 1, 501], [62684671, 20, 40118, 685, 1, 6999], [62684671, 20, 40118, 685, -1, -6999], [61664713, 59, 40118, 335, 1, 1799], [61664713, 59, 40118, 335, -1, -1799], [61684613, 59, 40118, 335, 1, 1299], [68634752, 59, 40118, 410, 1, 899], [60694698, 59, 40118, 620, 1, 399], [60664659, 59, 40118, 620, 1, 399], [60614487, 59, 40118, 878, 1, 595], [68654655, 166, 40118, 60, 1, 508], [69624033, 166, 40118, 80, 1, 1819], [60604100, 166, 40118, 80, 1, 1330], [68674560, 166, 40118, 170, 1, 599]]')
			click('Right')
			select('TabbedPane', '')
			select('Layout Name_Txt', 'Wizard_zxxxz_DTAR020')
			select('System_Txt', 'Mainframe')
			click('Right')
##			assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 487], [69684558, 20, 40118, 280, 1, 1900], [69684558, 20, 40118, 280, -1, -1900], [69694158, 20, 40118, 280, 1, 501], [62684671, 20, 40118, 685, 1, 6999], [62684671, 20, 40118, 685, -1, -6999], [61664713, 59, 40118, 335, 1, 1799], [61664713, 59, 40118, 335, -1, -1799], [61684613, 59, 40118, 335, 1, 1299], [68634752, 59, 40118, 410, 1, 899], [60694698, 59, 40118, 620, 1, 399], [60664659, 59, 40118, 620, 1, 399], [60614487, 59, 40118, 878, 1, 595], [68654655, 166, 40118, 60, 1, 508], [69624033, 166, 40118, 80, 1, 1819], [60604100, 166, 40118, 80, 1, 1330], [68674560, 166, 40118, 170, 1, 599]]')
		close()

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('Record Layout_Txt', 'Text', 'Wizard_zxxxz_DTAR020')
	close()
