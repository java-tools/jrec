useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', commonBits.fl('Properties')
)

			if commonBits.isVersion81():
				select('PropertiesTab', commonBits.fl('Test'))

				select('Test Mode_Chk', 'true')
				select('Warn on Structure change_Chk', 'false')
				select('Load In background_Chk', 'false')
				select('Use New Tree Expansion_Chk', 'false')
				select('On Search Screen default to "All Fields"_Chk', 'false')
				select('Add names to JComponents for use by testing tools_Chk', 'false')
				select('Rename Search btn_Chk', 'true')

				select('Include Type Name on Record Screen_Chk', 'false')

				click('Save')

				select('PropertiesTab', commonBits.fl('Behaviour'))

				select('Bring log to Front_Chk', 'false')
				select('Default to prefered layout_Chk', 'false')
				select('Show all export panels on the export Screen_Chk', 'true')
				select('Delete Selected rows with the delete key_Chk', 'false')

				select('Create Screens in seperate Windows_Chk', 'true')

				select('PropertiesTab', commonBits.fl('Layout Wizard'))
				select('Run the field search Automatically_Chk', 'true')
				click('Save')

				if commonBits.isBigModelVBtest():

					select('PropertiesTab', 'Big Model')
					select('Big File Percentage_Txt', '0')
					select('Chunk Size #{KB#}_Txt', '1')
					select('Use Large VB Model_Txt', 'Test')
					select('Use Fixed Length Model_Chk', 'false')
					select('Storing chunks on Disk_Chk', 'false')
					select('PropertiesTab', 'Defaults')

				elif commonBits.isBigModelVBandFixedtest():

					select('PropertiesTab', 'Big Model')
					select('Big File Percentage_Txt', '0')
					select('Chunk Size #{KB#}_Txt', '1')
					select('Use Large VB Model_Txt', 'Test')
					select('Use Fixed Length Model_Chk', 'true')

					select('Storing chunks on Disk_Chk', 'false')
					select('PropertiesTab', 'Defaults')


				elif commonBits.isBigModelDiskTest():

					select('PropertiesTab', 'Big Model')
					select('Big File Percentage_Txt', '0')
					select('Chunk Size #{KB#}_Txt', '1')
					select('Use Large VB Model_Txt', 'Test')
					select('Use Fixed Length Model_Chk', 'true')
					select('Storing chunks on Disk_Chk', 'true')

					select('PropertiesTab', 'Defaults')


##				click('Save')

				select('TabbedPane', commonBits.fl('Looks')
)

				select('Look and Feel_Txt', 'Default')
			elif commonBits.isVersion80():
				select('TabbedPane1', 'Other Options')
				select('EditPropertiesPnl$BoolFld', 'true')
				select('EditPropertiesPnl$BoolFld1', 'false')

				select('EditPropertiesPnl$BoolFld6', 'false')
				select('EditPropertiesPnl$BoolFld7', 'false')
				select('EditPropertiesPnl$BoolFld7', 'false')

				select('EditPropertiesPnl$BoolFld4', 'false')
				select('EditPropertiesPnl$BoolFld5', 'false')

				select('TabbedPane1', 'Big Model Options')
				select('EditPropertiesPnl$BoolFld13', 'false')

				select('TabbedPane', 'Looks')
				select('ComboBox2', 'Default')
			else:
				select('TabbedPane1', 'Other Options')
				select('EditPropertiesPnl$BoolFld', 'true')
				select('EditPropertiesPnl$BoolFld1', 'false')

				select('TabbedPane1', 'Big Model Options')
				select('EditPropertiesPnl$BoolFld13', 'false')

				select('TabbedPane', 'Looks')
				select('ComboBox2', 'Default')



##			select('TabbedPane', 'Looks')
##			select('ComboBox2', 'Default')
			click('Save')
		close()
	close()
