useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Proto Search')
		assert_p('Table1', 'Content', r'[[' + commonBits.stdCopybookDir() + 'StoreSales3.proto, StoreSales3.proto, Store], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\StoreSales3.protocomp, StoreSales3.proto, Store], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\StoreSales3im.proto, StoreSales3im.proto, Store], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\StoreSales3im.protocomp, StoreSales3im.proto, Store], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\StoreSales5.proto, StoreSales5.proto, Store], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\StoreSales5.protocomp, StoreSales5.proto, Store], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\StoreSales5SD.proto, StoreSales5SD.proto, Store], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\StoreSales5SD.protocomp, StoreSales5SD.proto, Store], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\StoreSales7.proto, StoreSales7.proto, Store], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\StoreSales7.protocomp, StoreSales7.proto, Store]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('FileChooser1', 'Text', commonBits.stdCopybookDir() + 'StoreSales3.protocomp')
		assert_p('ComboBox', 'Text', 'Delimited Messages')
		assert_p('ComboBox1', 'Text', 'Compiled Proto')
		assert_p('ComboBox2', 'Text', 'StoreSales3.proto')
		assert_p('ComboBox2', 'Content', '[[StoreSales3.proto]]')
		assert_p('ComboBox3', 'Text', 'Store')
		assert_p('ComboBox3', 'Content', '[[Product, Order, Summary, Deptartment, Store]]')
	close()
