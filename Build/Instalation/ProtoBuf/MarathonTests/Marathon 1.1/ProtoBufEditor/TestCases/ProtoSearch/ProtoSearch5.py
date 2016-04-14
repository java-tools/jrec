useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window(commonBits.applicationName()):
		select('FileNameTxtFld', commonBits.sampleDir() + 'protoStoreSales5.bin')
		click('Proto Search')
		assert_p('Table1', 'Content', r'[[' + commonBits.stdCopybookDir() + 'StoreSales5.proto, StoreSales5.proto, Store], [' + commonBits.stdCopybookDir() + 'StoreSales5.protocomp, StoreSales5.proto, Store], [' + commonBits.stdCopybookDir() + 'StoreSales5SD.proto, StoreSales5SD.proto, Store], [' + commonBits.stdCopybookDir() + 'StoreSales5SD.protocomp, StoreSales5SD.proto, Store], [' + commonBits.stdCopybookDir() + 'StoreSales6.proto, StoreSales6.proto, Store], [' + commonBits.stdCopybookDir() + 'StoreSales6.protocomp, StoreSales6.proto, Store], [' + commonBits.stdCopybookDir() + 'StoreSales6SD.proto, StoreSales6SD.proto, Store], [' + commonBits.stdCopybookDir() + 'StoreSales7.proto, StoreSales7.proto, Store], [' + commonBits.stdCopybookDir() + 'StoreSales7.protocomp, StoreSales7.proto, Store]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('FileNameTxtFld1', 'Text', commonBits.stdCopybookDir() + 'StoreSales5.protocomp')
		assert_p('ComboBox2', 'Text', 'StoreSales5.proto')
		assert_p('ComboBox3', 'Text', 'Store')
		select('FileNameTxtFld', commonBits.sampleDir() + 'protoStoreSales7.bin')
		click('Proto Search')
		assert_p('Table1', 'Content', r'[[' + commonBits.stdCopybookDir() + 'StoreSales7.proto, StoreSales7.proto, Store], [' + commonBits.stdCopybookDir() + 'StoreSales7.protocomp, StoreSales7.proto, Store]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('FileNameTxtFld1', 'Text', commonBits.stdCopybookDir() + 'StoreSales7.protocomp')
		assert_p('ComboBox2', 'Text', 'StoreSales7.proto')
		assert_p('ComboBox2', 'Content', '[[StoreSales7.proto]]')
		assert_p('ComboBox3', 'Text', 'Store')
		assert_p('ComboBox3', 'Content', '[[Product, Order, Summary, Deptartment, Store]]')
		assert_p('ComboBox1', 'Text', 'Compiled Proto')
		assert_p('ComboBox', 'Text', 'Delimited Messages')
	close()
