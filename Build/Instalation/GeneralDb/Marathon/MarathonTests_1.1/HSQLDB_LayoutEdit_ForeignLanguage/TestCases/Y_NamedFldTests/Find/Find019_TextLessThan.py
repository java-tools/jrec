useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Update Csv Columns'))
#		select('FieldChange_JTbl', 'cell:Source Column,5()')
#		doubleclick('FieldChange_JTbl', 'Source Column,5')
#		select('FieldChange_JTbl', 'cell:Source Column,5()')
#		doubleclick('FieldChange_JTbl', 'Source Column,5')
#		select('FieldChange_JTbl', 'cell:Source Column,4()')
#		doubleclick('FieldChange_JTbl', 'Source Column,4')
#		select('FieldChange_JTbl', 'cell:Source Column,4()')
#		click('FieldChange_JTbl', 3, 'Source Column,4')
#		select('FieldChange_JTbl', 'cell:Source Column,4()')
#		click('FieldChange_JTbl', 4, 'Source Column,4')
		select('FieldChange_JTbl', commonBits.fl('Number'), commonBits.fl('Type') + ',5')
		select('FieldChange_JTbl', 'cell:' + commonBits.fl('Type') + ',5(Number)')
		click(commonBits.fl('Apply'))
		click('Find1')
		select('Find.Search For_Txt', '19.00')
		select('Find.Operator_Txt', commonBits.fl('< (Text)'))
##		click('ScrollPane$ScrollBar', 4, 57)
##		click('ScrollPane$ScrollBar', 7, 52)
		select('Find.Field_Txt', 'SALE-PRICE')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '3, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '6, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '7, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '8, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '9, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '15, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '16, 5, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text',  commonBits.fl('Found (line, field Num, field position)=') + '18, 5, 0')
	close()
