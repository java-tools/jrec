useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', commonBits.fl('Properties'))

			select('PropertiesTab', commonBits.fl('Test'))

			select('Test Mode_Chk', 'true')
			select('Warn on Structure change_Chk', 'true')
			select('Add names to JComponents for use by testing tools_Chk', 'false')
			select('Rename Search btn_Chk', 'false')

			select('PropertiesTab', commonBits.fl('Behaviour')
)
			select('Bring log to Front_Chk', 'false')

			click('Save')
		close()
	close()
