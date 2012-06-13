useFixture(runVelocity)

def test():
	java_recorded_version = '1.6.0_22'

	if window(''):
		assert_p('Label', 'Text', 'Input File')
		assert_p('Label1', 'Text', 'Template File')
		assert_p('Label2', 'Text', 'Data Base')
		assert_p('Label3', 'Text', 'System')
		assert_p('Label4', 'Text', 'Record Layout')
		assert_p('Label5', 'Text', 'Description')
		assert_p('Label6', 'Text', 'Output File')
		assert_p('Choose Output File', 'Text', 'Choose Output File')
		assert_p('Reload from DB', 'Text', 'Reload from DB')
		assert_p('Choose Velocity Template', 'Text', 'Choose Velocity Template')
		assert_p('Choose File', 'Text', 'Choose File')

	close()
