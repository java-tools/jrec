useFixture(reCsvEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		click('Preferences')

		if window('Record Editor Options Editor'):
			select('TabbedPane',  commonBits.fl('Properties'))
			select('Directory where the Editor Starts in #{if no file specified#}_Txt', commonBits.reCsvEditParamDir() + '*')
			click('Save')
			click('Button2')
		close()
	close()
