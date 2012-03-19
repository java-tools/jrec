useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Edit>>Edit Options')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')
			select('TabbedPane1', 'Layout Wizard Options')
			select('EditPropertiesPnl$BoolFld5', 'false')
			click('Save')
			#click('Button2')
		close()
	close()
