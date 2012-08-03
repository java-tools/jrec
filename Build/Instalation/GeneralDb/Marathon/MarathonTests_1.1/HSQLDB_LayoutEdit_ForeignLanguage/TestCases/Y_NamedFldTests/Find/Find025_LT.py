useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Find1')
		select('Find.Search For_Txt', '3')
		select('Find.Operator_Txt', '<')
		#click('MetalInternalFrameTitlePane', 187, 13)
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '1, 4, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '2, 4, 0')
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '2, 4, 0')
		commonBits.find(click)
		assert_p('BaseHelpPanel', 'Enabled', 'true')
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '3, 4, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '3, 5, 0')
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '3, 5, 0')
		commonBits.find(click)
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '5, 4, 0')
		commonBits.find(click)
		#click('BaseHelpPanel', 53, 267)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '6, 4, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '6, 5, 0')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
