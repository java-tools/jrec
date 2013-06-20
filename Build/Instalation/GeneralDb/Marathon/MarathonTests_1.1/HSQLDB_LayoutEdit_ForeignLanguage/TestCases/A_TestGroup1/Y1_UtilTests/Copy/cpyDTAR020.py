useFixture(copy)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'
	if window('File Copy'):
		click('*3')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		select('FileChooser1', commonBits.sampleDir() + 'CpyCsvDtar020.Txt')
		commonBits.setRecordLayout2(select, 'DTAR020')
		select('DelimiterCombo', commonBits.fl('<Tab>'))
		click('Right')
		select('TabbedPane', '')
		select('Table1', 'cell:' + commonBits.fl('Include') + ',3(true)')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.userDir() + 'cpy2csvDTAR020.xml')
		##commonBits.selectFileName(select, commonBits.userDir() + 'cpy2csvDTAR020.xml')
		click(commonBits.fl('Save'))
		click(commonBits.fl('Copy'))
		assert_p('TextField1', 'Text', commonBits.fl('Copy Done !!!'))
		commonBits.closeWindow(click)
	close()
