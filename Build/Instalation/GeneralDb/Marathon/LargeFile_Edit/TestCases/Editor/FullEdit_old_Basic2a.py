useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'ams Store')
		click('Edit1')
		assert_p('Table', 'Text', 'DC - Taras Ave', '10 - 35|Loc Name,0')
		assert_p('Table', 'Text', 'Bankstown', '10 - 35|Loc Name,4')
		doubleclick('BaseDisplay$HeaderToolTips', '10 - 35|Loc Name')
		assert_p('Table', 'Text', 'Airport West', '10 - 35|Loc Name,0')
		assert_p('Table', 'Text', 'Bass Hill', '10 - 35|Loc Name,5')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window(r'Save Changes to file: ' + commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt'):
			click('No')
		close()
	close()
