useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')

			select('PropertiesTab', 'Test')
			select('Test Mode_Chk', 'false')
			select('Warn on Structure change_Chk', 'false')
			select('Add names to JComponents for use by testing tools_Chk', 'true')

			select('PropertiesTab', 'Behaviour')
			select('Bring log to Front_Chk', 'false')
##	select('PropertiesTab', 'File Options')
			click('Save')

		close()
	close()
