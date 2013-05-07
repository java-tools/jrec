useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
##		select('File_Txt', commonBits.sampleDir() + 'csvDTAR020comma.csv')
		select('File_Txt', commonBits.sampleDir() + 'csv3DTAR020_tst1.bin.csv')
		select('Record Layout_Txt', 'Generic CSV - enter details')
		click('Edit1')
 
		if window(''):
			select('TextField', 'aa')
			select('Line Number of Names_Txt', '11')
			select('Line Number of Names_Txt', '1')
			assert_p('TextField2', 'Text', 'Invalid Delimiter, should be a single character or a hex character')
			select('TextField', 'x\'ww\'')
			select('Line Number of Names_Txt', '01')
			assert_p('TextField2', 'Text', 'Invalid Delimiter - Invalid  hex string: w')
			select('TextField', 'x\'111\'')
			select('Line Number of Names_Txt', '1')
			assert_p('TextField2', 'Text', 'Invalid Delimiter, should be a single character or a hex character')
			select('TextField', 'x\'z0\'')
			select('Line Number of Names_Txt', '01')
			assert_p('TextField2', 'Text', 'Invalid Delimiter - Invalid  hex string: z')
			select('TabbedPane', 'Unicode')
			select('TextField1', 'aa')
			select('Line Number of Names_Txt1', '01')
			assert_p('TextField2', 'Text', 'Invalid Delimiter, should be a single character')
			select('TextField1', 'x\'00\'')
			select('Line Number of Names_Txt1', '0001')
			assert_p('TextField2', 'Text', 'Invalid Delimiter, should be a single character')
			select('TabbedPane', 'Normal')
			select('TextField', '')
			click('Go')
		close()

		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
		rightclick('LineList.FileDisplay_JTbl', '1|KEYCODE-NO,1')
		select('LineList.FileDisplay_JTbl', 'cell:2|STORE-NO,0(20)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
