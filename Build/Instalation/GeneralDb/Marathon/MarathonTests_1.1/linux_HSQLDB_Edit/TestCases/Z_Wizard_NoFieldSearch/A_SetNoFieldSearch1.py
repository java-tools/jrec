useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Edit>>Edit Options')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')
			select('TabbedPane1', 'Layout Wizard Options')
			if commonBits.isVersion89():
				select('EditPropertiesPnl$BoolFld9', 'false')
			else:
				select('EditPropertiesPnl$BoolFld5', 'false')
			click('Save')
			
			#click('Button2')
		close()
	close()
