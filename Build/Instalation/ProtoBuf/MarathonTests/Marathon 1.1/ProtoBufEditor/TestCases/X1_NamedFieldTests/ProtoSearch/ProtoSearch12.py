useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('File_Txt', commonBits.sampleDir() + 'DTAR020_tst1.bin')
		click('Proto Search')
		assert_p('Table1', 'Content', r'[[' + commonBits.stdCopybookDir() + 'DTAR020.proto, DTAR020.proto, sale], [' + commonBits.stdCopybookDir() + 'DTAR020.protocomp, DTAR020.proto, sale], [' + commonBits.stdCopybookDir() + 'Sales.proto, Sales.proto, sale], [' + commonBits.stdCopybookDir() + 'sales.protocomp, sales.proto, sale]]')
		assert_p('File_Txt', 'Text', commonBits.sampleDir() + 'DTAR020_tst1.bin')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('Proto File_Txt', 'Text', 'DTAR020.proto')
		assert_p('Primary Message_Txt', 'Text', 'sale')
##		assert_p('Type of Definition_Txt', 'Text', 'Proto Definition')
		assert_p('File Structure_Txt', 'Text', 'Delimited Messages')
##		assert_p('Proto Definition_Txt', 'Text', commonBits.stdCopybookDir() + 'DTAR020.protocomp')
	close()
