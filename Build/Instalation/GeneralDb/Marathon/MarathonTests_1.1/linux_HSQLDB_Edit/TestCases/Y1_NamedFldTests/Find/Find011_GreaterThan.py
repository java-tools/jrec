useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		click('Find1')
##		click('WindowsInternalFrameTitlePane', 156, 21)
		select('Find.Search For_Txt', '62684671')
		select('Find.Operator_Txt', '>')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '2, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '3, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '4, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '10, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '14, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '15, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '17, 0, 0')
		commonBits.find(click)

		if window(''):
			click('No')
		close()

		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '18, 6, 0')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
