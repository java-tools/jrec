useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')

			select('Test Mode_Chk', 'true')
			select('Warn on Structure change_Chk', 'false')
			select('Load In background_Chk', 'false')
			select('Use New Tree Expansion_Chk', 'true')
			select('On Search Screen default to "All Fields"_Chk', 'true')
			select('Add names to JComponents for use by testing tools_Chk', 'true')

			select('PropertiesTab', 'Behaviour')
			select('Bring log to Front_Chk', 'false')

			select('TabbedPane', 'Looks')
			select('Look and Feel_Txt', 'RecordEditor Default')
			click('Save')
		close()
	close()
