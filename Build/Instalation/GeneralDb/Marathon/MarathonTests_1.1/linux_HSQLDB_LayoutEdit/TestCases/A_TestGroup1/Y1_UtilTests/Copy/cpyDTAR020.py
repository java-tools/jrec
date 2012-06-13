useFixture(copy)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'
	if window('File Copy'):
		click('*3')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('FileChooser1', commonBits.sampleDir() + 'CpyCsvDtar020.Txt')
		commonBits.setRecordLayout2(select, 'DTAR020')
		select('ComboBox3', '<Tab>')
		click('Right')
		select('TabbedPane', '')
		select('Table1', 'cell:Include,3(true)')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.userDir() + 'cpy2csvDTAR020.xml')
		##commonBits.selectFileName(select, commonBits.userDir() + 'cpy2csvDTAR020.xml')
		click('Save')
		click('Copy')
		assert_p('TextField1', 'Text', 'Copy Done !!! ')
		commonBits.closeWindow(click)
	close()
