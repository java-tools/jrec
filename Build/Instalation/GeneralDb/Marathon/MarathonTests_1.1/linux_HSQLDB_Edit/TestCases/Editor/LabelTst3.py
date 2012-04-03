useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		click('Edit1')
		if commonBits.isVersion81():
			click('Export')
		else:
			click('SaveAs1')
		assert_p('Label1', 'Text', 'File Name')
		assert_p('Label2', 'Text', 'What to Save')
		if commonBits.isVersion81():
			assert_p('Label2', 'Text', 'What to Save')
			assert_p('Label3', 'Text', 'Output Format:')
			assert_p('Label4', 'Text', 'Delimiter')
			assert_p('Label5', 'Text', 'Quote')
			assert_p('Label6', 'Text', 'names on first line')
			assert_p('Label7', 'Text', 'Add Quote to all Text Fields')
			assert_p('Label8', 'Text', 'names on first line')
			assert_p('Label9', 'Text', 'space between fields')
		elif commonBits.isVersion80():
			assert_p('Label3', 'Text', 'Edit Output File')
			assert_p('Label4', 'Text', 'Output Format:')
			assert_p('Label5', 'Text', 'Delimiter')
			assert_p('Label6', 'Text', 'Quote')
			assert_p('Label7', 'Text', 'names on first line')
			assert_p('Label8', 'Text', 'Add Quote to all Text Fields')
			assert_p('Label9', 'Text', 'names on first line')
		else:
			assert_p('Label3', 'Text', 'Output Format:')
			assert_p('Label4', 'Text', 'Delimiter')
			assert_p('Label5', 'Text', 'Quote')
			assert_p('Label6', 'Text', 'names on first line')
			assert_p('Label7', 'Text', 'Add Quote to all Text Fields')
			assert_p('Label8', 'Text', 'names on first line')
		assert_p('ComboBox', 'Content', '[[File, Selected Records]]')
		assert_p('ComboBox', 'Text', 'File')
		assert_p('ComboBox1', 'Text', '<Tab>')
	close()
