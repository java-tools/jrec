useFixture(copy)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'
	if window('File Copy'):
		click('*2')
		select('FileChooser', commonBits.cobolTestDir() + 'mfComp.bin')
		select('FileChooser1', commonBits.cobolTestDir() + 'zzFjComp.bin')
		select('FileChooser2', commonBits.cobolTestDir() + 'mfComp.cbl')
		select('ComputerOptionCombo', commonBits.fl('Open Cobol Micro Focus (Intel)')
)
		select('ComputerOptionCombo1', commonBits.fl('Fujitsu')
)
##		select('BmKeyedComboBox1', '2')
		select('BmKeyedComboBox', commonBits.fl('Fixed Length Binary')
)

##		select('BmKeyedComboBoxxxxx', '2')
		click('Right')
		select('TabbedPane', '')
		click(commonBits.fl('Copy')
)
		assert_p('TextField1', 'Text', commonBits.fl('Copy Done !!!, check log for errors')
)
		commonBits.closeWindow(click)
	close()
