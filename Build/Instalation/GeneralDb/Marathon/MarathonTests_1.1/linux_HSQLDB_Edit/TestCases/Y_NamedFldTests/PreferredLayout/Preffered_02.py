useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click('Edit1')
		assert_p('LineList.Layouts_Txt', 'Text', 'Prefered')
		click('Find')
		select('Find.Search For_Txt', 'D1')
		assert_p('Find.Record Layout_Txt', 'Text', 'ams PO Download: Allocation')
		assert_p('Find.Field_Txt', 'Text', 'All Fields')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=2, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=4, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=6, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=9, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=12, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=15, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=17, 0, 0')
		click('Find1')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=22, 0, 0')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
