useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', commonBits.fl('Properties'))

			select('PropertiesTab', commonBits.fl('Test'))
			select('Test Mode_Chk', 'false')
			select('Add names to JComponents for use by testing tools_Chk', 'true')
			select('Rename Search btn_Chk', 'true')
			select('Include Type Name on Record Screen_Chk', 'false')


			select('Warn on Structure change_Chk', 'false')
			select('PropertiesTab', commonBits.fl('Behaviour'))
			select('Bring log to Front_Chk', 'false')
##	select('PropertiesTab', 'File Options')
			select('Create Screens in seperate Windows_Chk', 'true')

			click('Save')

		close()
	close()
