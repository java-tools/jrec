useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoSales.bin')
		click('Proto Search')
		keystroke('EditorPane', 'Context Menu')
		select('Table1', r'cell:Proto Filename,2(' + commonBits.stdCopybookDir() + 'Sales.proto)')
		keystroke('Table1', 'Context Menu', 'Proto Filename,2')
		select('Table1', r'cell:Proto Filename,2(' + commonBits.stdCopybookDir() + 'Sales.proto)')
		keystroke('Table1', 'Context Menu', 'Proto Filename,2')
		select('Table1', r'cell:Proto Filename,2(' + commonBits.stdCopybookDir() + 'Sales.proto)')
		keystroke('Table1', 'Context Menu', 'Proto Filename,2')
		select('Table1', r'cell:Proto Filename,2(' + commonBits.stdCopybookDir() + 'Sales.proto)')
		keystroke('Table1', 'Context Menu', 'Proto Filename,2')
		select('Table1', r'cell:Proto Filename,2(' + commonBits.stdCopybookDir() + 'Sales.proto)')
		assert_p('Table1', 'Content', r'[[' + commonBits.stdCopybookDir() + 'DTAR020.proto, DTAR020.proto, sale], [' + commonBits.stdCopybookDir() + 'DTAR020.protocomp, DTAR020.proto, sale], [' + commonBits.stdCopybookDir() + 'Sales.proto, Sales.proto, sale], [' + commonBits.stdCopybookDir() + 'sales.protocomp, sales.proto, sale]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		assert_p('FileChooser1', 'Text', commonBits.stdCopybookDir() + 'sales.protocomp')
		assert_p('ComboBox2', 'Text', 'sales.proto')
		assert_p('ComboBox3', 'Text', 'sale')
		assert_p('ComboBox', 'Text', 'Delimited Messages')
		assert_p('ComboBox1', 'Text', 'Compiled Proto')
		assert_p('ComboBox2', 'Text', 'sales.proto')
		assert_p('ComboBox2', 'Content', '[[sales.proto]]')
		assert_p('ComboBox3', 'Text', 'sale')
		assert_p('ComboBox3', 'Content', '[[sale]]')
	close()
