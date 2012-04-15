useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Edit>>Edit Options')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')
			
			if commonBits.isVersion81():
				select('PropertiesTab', 'Test')
				select('Test Mode_Chk', 'true')

				select('Add names to JComponents for use by testing tools_Chk', 'false')
				select('PropertiesTab', 'Layout Wizard')
				select('Run the field search Automatically_Chk', 'true')
			elif commonBits.isVersion80():
				select('TabbedPane1', 'Layout Wizard Options')
				select('EditPropertiesPnl$BoolFld8', 'true')
			else:
				select('TabbedPane1', 'Layout Wizard Options')
				select('EditPropertiesPnl$BoolFld5', 'true')
			click('Save')
			
			#click('Button2')
		close()
	close()
