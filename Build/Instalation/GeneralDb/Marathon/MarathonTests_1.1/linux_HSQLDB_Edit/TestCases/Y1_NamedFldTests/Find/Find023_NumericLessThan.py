useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Update Csv Columns'))
		select('FieldChange_JTbl', commonBits.fl('Number'), commonBits.fl('Type') + ',5')
		select('FieldChange_JTbl', 'cell:' + commonBits.fl('Type') + ',5(Number)')
		click(commonBits.fl('Apply'))
		select('LineList.FileDisplay_JTbl', 'cell:1|KEYCODE-NO,0(63604808)')
		click('Find1')
		select('Find.Search For_Txt', '5.95')
		select('Find.Field_Txt', 'SALE-PRICE')
		select('Find.Operator_Txt', '<')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '3, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '4, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '6, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '8, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '11, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '12, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '14, 5, 0')
		commonBits.find(click)

		if window(''):
			click('No')
		close()

		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '18, 5, 0')
	close()
