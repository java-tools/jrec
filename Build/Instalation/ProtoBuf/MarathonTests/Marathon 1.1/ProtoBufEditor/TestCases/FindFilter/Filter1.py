useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Edit1')
		click('Filter')
		click('Uncheck Records')
		select('Table', 'cell:Include,3(false)')
		select('Table2', 'cell:Field,0(null)')
		select('Table2', 'department', 'Field,0')
		select('Table2', '80', 'Value,0')
		select('Table2', 'cell:Value,1()')
		click('Filter1')
		select('Table', 'cell:2|name,1(Department: 80)')
		assert_p('Table', 'Content', '[[280, Department: 280], [80, Department: 80], [280, Department: 280], [801, Department: 801], [805, Department: 805], [80, Department: 80], [280, Department: 280], [801, Department: 801], [805, Department: 805]]')
	close()
