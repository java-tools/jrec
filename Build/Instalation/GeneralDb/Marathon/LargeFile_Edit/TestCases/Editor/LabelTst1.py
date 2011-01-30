useFixture(default)

def test():
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		assert_p('Label1', 'Text', 'File')
		assert_p('Label2', 'Text', 'Data Base')
		assert_p('Label3', 'Text', 'System')
		assert_p('Label4', 'Text', 'Record Layout')
		assert_p('Label5', 'Text', 'Description')
		assert_p('Choose File', 'Text', 'Choose File')
		assert_p('Reload from DB', 'Text', 'Reload from DB')
		assert_p('Layout Wizard', 'Text', 'Layout Wizard')
##		assert_p('Create Layout', 'Text', 'Create Layout')
		assert_p('Edit1', 'Text', 'Edit')
		assert_p('Browse', 'Text', 'Browse')
	close()
