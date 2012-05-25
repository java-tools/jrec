useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('Record Layout_Txt', 'ams PO Download')
		assert_p('Record Layout_Txt', 'Text', 'ams PO Download')
		select('File_Txt',commonBits.sampleDir() + 'xxxcvxx1121')
		assert_p('Record Layout_Txt', 'Text', 'ams PO Download')
	close()
