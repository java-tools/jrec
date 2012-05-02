useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('reCsv Editor'):
		select('FilePane$3', 'DTAR020.bin')
##		select('TabbedPane', 'Fixed Width')
##		select('FilePane$3', 'DTAR020.bin')
##		select('FilePane$3', 'DTAR020.bin')
		doubleclick('FilePane$3', '2')
##		select('FilePane$3', 'DTAR020.bin')
		click('Edit3')
		click('SaveAs')
		select('File name', 'zzzDTAR020.bin$')
		click('Save1')
	close()
