useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click('Edit1')
		click('Find1')
##		click('WindowsInternalFrameTitlePane', 157, 19)
		select('Find.Search For_Txt', '62684671')
		select('Find.Field_Txt', 'KEYCODE-NO')
		select('Find.Operator_Txt', '<')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=7, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=8, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=9, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=11, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=12, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=13, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=16, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=18, 0, 0')
	close()
