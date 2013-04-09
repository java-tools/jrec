useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv3DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		click('Find1')
		select('Find.Search For_Txt', '555')
		commonBits.find(click)

		if window(''):
			keystroke('Yes', 'Escape')
		close()

		assert_p('TextField', 'Text', commonBits.fl('Found (line, field Num, field position)=') + '18, 6, 0')
		select('Find.Direction_Txt', commonBits.fl('Backward'))
		commonBits.find(click)

		if window(''):
			keystroke('Yes', 'Escape')
		close()

		assert_p('TextField', 'Text', '')
	close()
