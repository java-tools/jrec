useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click(commonBits.fl('Edit') + '1')
		assert_p('LineList.Layouts_Txt', 'Text', commonBits.fl('Prefered'))
		click('Find')
		select('Find.Search For_Txt', 'D1')
		assert_p('Find.Record Layout_Txt', 'Text', 'ams PO Download: Allocation')
		assert_p('Find.Field_Txt', 'Text', commonBits.fl('All Fields'))
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '2, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '4, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '6, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '9, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '12, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '15, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '17, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '22, 0, 0')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
