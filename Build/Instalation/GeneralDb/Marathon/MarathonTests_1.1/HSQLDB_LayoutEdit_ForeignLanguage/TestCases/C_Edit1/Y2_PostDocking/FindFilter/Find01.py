useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv3DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		click('Find1')
		select('Find.Search For_Txt', '60')
		select('Find.Field_Txt', 'KEYCODE-NO')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '1, 0, 2')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '11, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '12, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '13, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '16, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '16, 0, 2')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '17, 0, 6')
		commonBits.find(click)

		if window(''):
			click('Yes')
		close()

		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '1, 0, 2')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '11, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '12, 0, 0')
		select('Find.Direction_Txt', commonBits.fl('Backward'))
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '11, 0, 0')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '1, 0, 2')
		commonBits.find(click)

		if window(''):
			click('Yes')
		close()

		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '17, 0, 6')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '16, 0, 2')
		select('Find.Direction_Txt', commonBits.fl('Forward'))
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '17, 0, 6')
		commonBits.find(click)

		if window(''):
			click('Yes')
		close()

		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '1, 0, 2')
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '11, 0, 0')
		select('Find.Direction_Txt', commonBits.fl('Backward'))
		commonBits.find(click)
		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '1, 0, 2')
		commonBits.find(click)

		if window(''):
			click('Yes')
		close()

		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '17, 0, 6')
	close()
