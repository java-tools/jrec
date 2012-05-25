useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_PODownload_20041231.txt')
		click('Edit1')
		click('Find')
		assert_p('LayoutCombo', 'Text', 'ams PO Download: Allocation')
		select('TextField', 'D1')
		select('ComboBox', 'Record Type')
###		click('ScrollPane$ScrollBar', 12, 32)
		click('Find1')
		assert_p('TextField2', 'Text', 'Found (line, field Num, field position)=2, 0, 0')
		click('Find1')
		assert_p('TextField2', 'Text', 'Found (line, field Num, field position)=4, 0, 0')
		click('Find1')
		assert_p('TextField2', 'Text', 'Found (line, field Num, field position)=6, 0, 0')
		click('Find1')
		assert_p('TextField2', 'Text', 'Found (line, field Num, field position)=9, 0, 0')
		click('Find1')
		assert_p('TextField2', 'Text', 'Found (line, field Num, field position)=12, 0, 0')
		click('Find1')
		assert_p('TextField2', 'Text', 'Found (line, field Num, field position)=15, 0, 0')
		assert_p('LayoutCombo', 'Text', 'ams PO Download: Allocation')
		assert_p('ComboBox', 'Text', 'Record Type')
		click('Find1')
		assert_p('TextField2', 'Text', 'Found (line, field Num, field position)=17, 0, 0')
		assert_p('LayoutCombo', 'Text', 'ams PO Download: Allocation')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
