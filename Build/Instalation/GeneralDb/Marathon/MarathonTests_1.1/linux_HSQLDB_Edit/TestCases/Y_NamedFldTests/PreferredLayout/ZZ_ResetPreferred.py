useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')

			select('PropertiesTab', 'Behaviour')
			select('Bring log to Front_Chk', 'false')
			select('Default to prefered layout_Chk', 'false')

			click('Save')
			click('Save')
		close()
	close()
