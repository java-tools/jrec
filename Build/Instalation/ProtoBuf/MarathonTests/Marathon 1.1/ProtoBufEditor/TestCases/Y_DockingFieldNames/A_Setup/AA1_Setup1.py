useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', commonBits.fl('Properties'))

			select('PropertiesTab', commonBits.fl('Test'))
			select('Test Mode_Chk', 'true')

			select('Rename Search btn_Chk', 'true')
			select('Load In background_Chk', 'false')
			select('Use New Tree Expansion_Chk', 'true')
			select('On Search Screen default to "All Fields"_Chk', 'true')
			select('Add names to JComponents for use by testing tools_Chk', 'true')
			select('Rename Search btn_Chk', 'true')

			select('Include Type Name on Record Screen_Chk', 'true')

			select('Warn on Structure change_Chk', 'false')
			
			
			select('PropertiesTab', commonBits.fl('Behaviour'))
			
			select('Bring log to Front_Chk', 'false')
			select('Default to prefered layout_Chk', 'false')
			select('Show all export panels on the export Screen_Chk', 'false')
			select('Delete Selected rows with the delete key_Chk', 'true')

			select('Create Screens in seperate Windows_Chk', 'false')
			
##	select('PropertiesTab', 'File Options')
			click('Save')

		close()
	close()
