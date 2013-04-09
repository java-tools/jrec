useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		click('Find1')
##		click('MetalInternalFrameTitlePane', 88, 16)
		select('Find.Search For_Txt', '69')
		select('Find.Operator_Txt', commonBits.fl('Starts With'))
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '2, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '3, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '4, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '5, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '15, 0, 0')
		commonBits.find(click)

		if window(''):
			click('No')
		close()

		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '18, 6, 0')
	close()
