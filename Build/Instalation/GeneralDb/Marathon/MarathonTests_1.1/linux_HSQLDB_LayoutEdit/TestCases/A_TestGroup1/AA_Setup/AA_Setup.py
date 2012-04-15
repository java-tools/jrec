useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')

			select('PropertiesTab', 'Test')
#			select('Test Mode', 'true')
#			select('Warn on Structure change', 'false')
#			select('Add names to JComponents for use by testing tools', 'false')

			select('Test Mode_Chk', 'true')
			select('Warn on Structure change_Chk', 'false')
			select('Add names to JComponents for use by testing tools_Chk', 'false')

			select('PropertiesTab', 'Behaviour')
#			select('Bring log to Front', 'false')
			select('Bring log to Front_Chk', 'false')

			select('TabbedPane', 'Looks')

			select('Look and Feel_Txt', 'Default')

			click('Save')
		close()
	close()
