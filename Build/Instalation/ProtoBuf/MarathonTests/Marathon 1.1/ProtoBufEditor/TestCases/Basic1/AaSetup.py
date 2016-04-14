useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window(commonBits.applicationName()):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Properties')

			select('PropertiesTab', 'Test')

			select('Test Mode_Chk', 'true')
			select('Warn on Structure change_Chk', 'false')
			select('Load In background_Chk', 'false')
			select('Use New Tree Expansion_Chk', 'false')
			select('On Search Screen default to "All Fields"_Chk', 'false')
			select('Use New Tree Expansion_Chk', 'false')
			select('Add names to JComponents for use by testing tools_Chk', 'false')
			select('Rename Search btn_Chk', 'false')
			select('Include Type Name on Record Screen_Chk', 'false')

			click('Save')

			select('PropertiesTab', commonBits.fl('Behaviour'))

			select('Bring log to Front_Chk', 'false')
			select('Default to prefered layout_Chk', 'false')
			select('Show all export panels on the export Screen_Chk', 'true')
			select('Delete Selected rows with the delete key_Chk', 'false')
			select('Create Screens in seperate Windows_Chk', 'true')

##			select('PropertiesTab', 'Layout Wizard')
##			select('Run the field search Automatically_Chk', 'true')

			select('TabbedPane', commonBits.fl('Looks'))

			select('Look and Feel_Txt', 'Default')
			click('Save')

#			select('TabbedPane', commonBits.fl('Looks')
#)

#			select('Look and Feel_Txt', 'Default')

#			
#			select('TabbedPane1', 'Other Options')
#			select('EditPropertiesPnl$BoolFld', 'true')
#			select('EditPropertiesPnl$BoolFld1', 'false')  
#
#			if commonBits.isVersion80():
#				select('EditPropertiesPnl$BoolFld6', 'false')
#				select('EditPropertiesPnl$BoolFld7', 'false')
#				select('EditPropertiesPnl$BoolFld7', 'false')
#
#				select('EditPropertiesPnl$BoolFld4', 'false')
#				select('EditPropertiesPnl$BoolFld5', 'false')
#
#				if commonBits.isVersion81():
#						select('EditPropertiesPnl$BoolFld8', 'true')
#
#						select('EditPropertiesPnl$BoolFld9', 'false')
#                        
#
#				select('EditPropertiesPnl$BoolFld8', 'false')

##			##select('TabbedPane1', 'Big Model Options')
##			##select('EditPropertiesPnl$BoolFld13', 'false')
##			select('TabbedPane', 'Looks')
##			select('ComboBox1', 'Default')
			click('Save')
		close()
	close()
