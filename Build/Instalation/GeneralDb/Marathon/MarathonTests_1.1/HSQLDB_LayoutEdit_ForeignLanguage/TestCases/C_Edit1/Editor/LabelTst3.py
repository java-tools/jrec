useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		click(commonBits.fl('Edit') + '1')
		if commonBits.isVersion81():
			click('Export')
		else:
			click('SaveAs1')
		assert_p('Label', 'Text', commonBits.fl('File Name')
)
		assert_p('Label1', 'Text', commonBits.fl('What to Save')
)
		if commonBits.isVersion81():
			assert_p('Label1', 'Text', commonBits.fl('What to Save'))
			assert_p('Label2', 'Text', commonBits.fl('Output Format:')
)
			assert_p('Label3', 'Text', commonBits.fl('Delimiter')
)
			assert_p('Label4', 'Text', commonBits.fl('Quote')
)
			assert_p('Label5', 'Text', commonBits.fl('names on first line')
)
			assert_p('Label6', 'Text', commonBits.fl('Add Quote to all Text Fields')
)
			assert_p('Label7', 'Text', commonBits.fl('names on first line')
)
			assert_p('Label8', 'Text', commonBits.fl('space between fields')
)
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
		assert_p('ComboBox', 'Content', '[[' + commonBits.fl('File') + ', ' + commonBits.fl('Selected Records') + ']]')
		assert_p('ComboBox', 'Text', commonBits.fl('File'))
##		assert_p('ComboBox1', 'Text', commonBits.fl('<Tab>'))
		assert_p('DelimitierCombo', 'Text', commonBits.fl('<Tab>'))
		assert_p('QuoteCombo', 'Text', commonBits.fl('<None>'))
##		assert_p('DelimitierCombo', 'Content', r'[[<Tab>, <Space>, ,, ;, :, |, /, \, ~, !, *, #, @, x\'00\', x\'01\', x\'02\', x\'FD\', x\'FE\', x\'FF]]')
##		assert_p('DelimitierCombo', 'Content', r'[[<Tab>, <Space>, ,, ;, :, |, /, \, ~, !, *, #, @, x\'00\', x\'01\', x\'02\', x\'FD\', x\'FE\', x\'FF]]')
		assert_p('QuoteCombo', 'Content', '[['+ commonBits.fl('<None>') + ', ' + commonBits.fl('<Default>') + ', ", \', `]]')
	close()
