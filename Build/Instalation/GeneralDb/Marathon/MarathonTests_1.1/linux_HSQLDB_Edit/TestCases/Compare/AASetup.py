useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')
			select('TabbedPane1', 'Other Options')
			select('EditPropertiesPnl$BoolFld', 'true')
			select('EditPropertiesPnl$BoolFld1', 'false')
			select('EditPropertiesPnl$BoolFld2', 'false')
			if commonBits.isVersion89():
				select('EditPropertiesPnl$BoolFld6', 'false')
				select('EditPropertiesPnl$BoolFld7', 'false')
				select('EditPropertiesPnl$BoolFld8', 'false')
			select('TabbedPane1', 'Big Model Options')
			select('EditPropertiesPnl$BoolFld13', 'false')
			select('TabbedPane', 'Looks')
			select('ComboBox2', 'Default')
			click('Save')
		close()
	close()
