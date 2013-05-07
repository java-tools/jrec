useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.bin')
		click('Edit1')

		if commonBits.isVersion80():
			click('Export')
		else:
			click('SaveAs1')
		assert_p('Label', 'Text', 'File Name')
		assert_p('Label1', 'Text', 'What to Save')

		if commonBits.isVersion80():
			assert_p('Label1', 'Text', 'What to Save')
			assert_p('Label2', 'Text', 'Output Format:')
			#assert_p('Label4', 'Text', 'Only Data Column')
			assert_p('Label4', 'Text', 'Quote')
			assert_p('Label3', 'Text', 'Delimiter')
			assert_p('Label6', 'Text', 'Add Quote to all Text Fields')
		else:
			assert_p('Label3', 'Text', 'Output Format:')
			#assert_p('Label4', 'Text', 'Only Data Column')
			assert_p('Label5', 'Text', 'Quote')
			assert_p('Label4', 'Text', 'Delimiter')
			assert_p('Label7', 'Text', 'Add Quote to all Text Fields')
		
		#assert_p('Label8', 'Text', 'Velocity Template')
		assert_p('ComboBox', 'Content', '[[File, Selected Records]]')
		assert_p('ComboBox', 'Text', 'File')
##		assert_p('ComboBox1', 'Text', '<Tab>')
		assert_p('DelimiterCombo', 'Text', '<Tab>')
	close()
