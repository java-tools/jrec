useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')
			select('PropertiesTab', 'Test')

			if commonBits.isVersion806():
				select('Test Mode_Chk', 'true')
				select('Warn on Structure change_Chk', 'false')
				select('Load In background_Chk', 'false')
				select('Use New Tree Expansion_Chk', 'false')
				select('Add names to JComponents for use by testing tools_Chk', 'false')
				select('On Search Screen default to "All Fields"_Chk', 'false')

				select('PropertiesTab', 'Behaviour')
				select('Bring log to Front_Chk', 'false')
				select('Show all export panels on the export Screen_Chk', 'true')
				select('Delete Selected rows with the delete key_Chk', 'false')
			elif commonBits.isVersion80():
				select('EditPropertiesPnl$BoolFld', 'true')
				select('EditPropertiesPnl$BoolFld1', 'false')
				select('EditPropertiesPnl$BoolFld6', 'false')
				select('EditPropertiesPnl$BoolFld7', 'false')
				select('EditPropertiesPnl$BoolFld7', 'false')

				select('EditPropertiesPnl$BoolFld4', 'false')
				select('EditPropertiesPnl$BoolFld5', 'false')

				if commonBits.isVersion81():
						select('EditPropertiesPnl$BoolFld8', 'true')

						select('EditPropertiesPnl$BoolFld9', 'false')


				select('EditPropertiesPnl$BoolFld8', 'false')
			
			else:
				select('EditPropertiesPnl$BoolFld', 'true')
				select('EditPropertiesPnl$BoolFld1', 'false')

			##select('TabbedPane1', 'Big Model Options')
			##select('EditPropertiesPnl$BoolFld13', 'false')
			select('TabbedPane', 'Looks')
			select('Look and Feel_Txt', 'Default')
			click('Save')
		close()
	close()

