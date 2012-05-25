useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'DTAR020.bin')
		click('Edit1')
		assert_p('LineList.Layouts_Txt', 'Text', 'DTAR020')
		click('Find')
		select('Find.Search For_Txt', '80')
		assert_p('Find.Record Layout_Txt', 'Text', 'DTAR020')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=1, 3, 1')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=2, 3, 1')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=3, 3, 1')
		click('Find1')
		click('Find1')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=6, 3, 1')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=7, 0, 5')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=45, 3, 0')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
