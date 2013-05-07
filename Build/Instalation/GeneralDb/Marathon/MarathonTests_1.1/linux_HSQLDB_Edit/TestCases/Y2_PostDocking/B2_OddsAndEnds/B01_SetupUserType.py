useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane', 'Extensions')
			select('ExtensionsTab', 'User Types')
			select('Table10', '1001', 'Type Number,0')
			select('Table10', 'tttt', 'Type Name,0')
			select('Table10', 'tttt', 'Type Class,0')
			select('Table10', 'cell:Type Class,0(tttt)')
			click('Save')

##			click('Button2')
		close()
	close()
