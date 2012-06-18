useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('FileChooser',  commonBits.sampleDir() + 'protoSales11.bin')
		click('Proto Search')
		assert_p('Table1', 'Content', r'[[' + commonBits.stdCopybookDir() + 'Sales11.proto, Sales11.proto, sale11], [C:\Users\BruceTst\.RecordEditor\ProtoBuf\CopyBook\Sales11.protocomp, Sales11.proto, sale11]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('FileChooser1', 'Text', commonBits.stdCopybookDir() + 'Sales11.protocomp')
		assert_p('ComboBox2', 'Text', 'Sales11.proto')
		assert_p('ComboBox2', 'Content', '[[Sales11.proto]]')
		assert_p('ComboBox3', 'Text', 'sale11')
		assert_p('ComboBox3', 'Content', '[[sale11]]')
		assert_p('ComboBox1', 'Text', 'Compiled Proto')
		assert_p('ComboBox', 'Text', 'Delimited Messages')
	close()
