useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')
			select('TabbedPane1', 'Layout Wizard Options')
			select('EditPropertiesPnl$BoolFld5', 'true')
			click('Save')
			click('Button2')
		close()
	close()