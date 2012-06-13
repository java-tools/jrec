useFixture(copy)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'
	if window('File Copy'):
		click('*2')
		select('FileChooser', commonBits.cobolTestDir() + 'mfComp.bin')
		select('FileChooser1', commonBits.cobolTestDir() + 'zzFjComp.bin')
		select('FileChooser2', commonBits.cobolTestDir() + 'mfComp.cbl')
		select('ComputerOptionCombo', 'Open Cobol Micro Focus (Intel)')
		select('ComputerOptionCombo1', 'Fujitsu')
##		select('BmKeyedComboBox1', '2')
		select('BmKeyedComboBox', 'Fixed Length Binary')

##		select('BmKeyedComboBoxxxxx', '2')
		click('Right')
		select('TabbedPane', '')
		click('Copy')
		assert_p('TextField1', 'Text', 'Copy Done !!! ')
		commonBits.closeWindow(click)
	close()
