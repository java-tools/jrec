useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'DTAR020_tst1.bin')
		click('Edit1')
		click('Export')
		select('TabbedPane', 'Script')
		select('SaveAsPnlScript.Script_Txt', '')
		click('Save File')
		assert_p('TextArea3', 'Text', 'Error: ')
		select('TabbedPane', 'XSLT Transform')
		select('SaveAsPnlXslt.Xslt File_Txt', '')
		click('Save File')
		assert_p('TextArea3', 'Text', 'Error: Could not compile stylesheet')
		select('TabbedPane', 'Velocity')
		select('SaveAsPnlVelocity.Velocity Template_Txt', '')
		click('Save File')
		assert_p('TextArea3', 'Text', 'Error: Error - cannot find template: ')
		select('File Name_Txt', '')
		click('Save File')
		assert_p('TextArea3', 'Text', 'Please Enter a file name')
		select('TabbedPane', 'CSV')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
