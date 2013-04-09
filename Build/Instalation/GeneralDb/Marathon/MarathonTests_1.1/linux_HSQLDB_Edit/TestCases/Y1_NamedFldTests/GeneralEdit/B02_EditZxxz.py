useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'zxxzDTAR020_tst1.bin')
		click(commonBits.fl('Edit') + '1')
		select('LineList.FileDisplay_JTbl', 'cell:11 - 4|Field_2,7(40118)')
		assert_p('LineList.FileDisplay_JTbl', 'Text', 'cell:11 - 4|Field_2,7(40118)')
		select('LineList.FileDisplay_JTbl', 'cell:15 - 2|Field_3,8(335)')
		keystroke('LineList.FileDisplay_JTbl', 'Context Menu', '15 - 2|Field_3,8')
		select('LineList.FileDisplay_JTbl', 'cell:15 - 2|Field_3,8(335)')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 487], [69684558, 20, 40118, 280, 1, 1900], [69684558, 20, 40118, 280, -1, -1900], [69694158, 20, 40118, 280, 1, 501], [62684671, 20, 40118, 685, 1, 6999], [62684671, 20, 40118, 685, -1, -6999], [61664713, 59, 40118, 335, 1, 1799], [61664713, 59, 40118, 335, -1, -1799], [61684613, 59, 40118, 335, 1, 1299], [68634752, 59, 40118, 410, 1, 899], [60694698, 59, 40118, 620, 1, 399], [60664659, 59, 40118, 620, 1, 399], [60614487, 59, 40118, 878, 1, 595], [68654655, 166, 40118, 60, 1, 508], [69624033, 166, 40118, 80, 1, 1819], [60604100, 166, 40118, 80, 1, 1330], [68674560, 166, 40118, 170, 1, 599]]')
		select('LineList.FileDisplay_JTbl', 'cell:15 - 2|Field_3,8(335)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('Record Layout_Txt', 'Text', 'Wizard_zxxxz_DTAR020')
	close()
