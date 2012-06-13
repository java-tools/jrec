useFixture(copy)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'
	if window('File Copy'):
		click('*3')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('FileChooser1', commonBits.sampleDir() + 'XfeDTAR020.csv')
		commonBits.setRecordLayout2(select, 'DTAR020')

##		click('ScrollPane$ScrollBar', 3, 37)
##		select('TextField', 'x\'fe\'')
		select('ComboBox3', 'x\'FE\'')
		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')
		click('Copy')
		assert_p('TextField1', 'Text', 'Copy Done !!! ')
		commonBits.closeWindow(click)
	close()
