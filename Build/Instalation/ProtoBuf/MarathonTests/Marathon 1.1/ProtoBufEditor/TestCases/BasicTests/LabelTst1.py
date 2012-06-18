useFixture(default)

def test():
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		#click('MetalInternalFrameTitlePane', 172, 16)
		assert_p('Label', 'Text', 'File')
		assert_p('Label1', 'Text', 'File Structure')
		assert_p('Label2', 'Text', 'Type of Definition')
		assert_p('Label3', 'Text', 'Proto Definition')
		assert_p('Label5', 'Text', 'Proto File')
		assert_p('ComboBox', 'Text', 'Single Message')
		#assert_p('ComboBox', 'Content', '[[Single Message, ]]')
		assert_p('ComboBox1', 'Text', 'Proto Definition')
		#assert_p('ComboBox1', 'Text', 'Avro IDL Definition')
		assert_p('Browse', 'Text', 'Browse')
		assert_p('Edit1', 'Text', 'Edit')
		assert_p('Choose Layout', 'Text', 'Choose Layout')
		assert_p('Choose File', 'Text', 'Choose File')
	close()
