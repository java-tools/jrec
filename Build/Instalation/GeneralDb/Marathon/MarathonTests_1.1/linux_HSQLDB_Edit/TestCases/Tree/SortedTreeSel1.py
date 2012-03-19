useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		commonBits.setRecordLayout(select, 'DTAR020')
		click('Edit1')
		select_menu('View>>Sorted Field Tree')
		select('List', 'DTAR020')
		select('Table', 'STORE-NO', 'Field,0')
		select('Table', 'DEPT-NO', 'Field,1')
		select('Table', 'cell:Field,1(DEPT-NO)')
		click('Build Tree')
		select('JTreeTable', 'cell:Tree,1(null)')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'cell:Tree,1(null)')
		select('Table', 'cell:11 - 4|DATE,1(40118)')
		assert_p('Table', 'Content', '[[61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95], [63644339, 59, 40118, 878, 1, 12.65], [67674686, 59, 40118, 929, 1, 3.99], [64614401, 59, 40118, 957, 1, 1.99], [64614401, 59, 40118, 957, 1, 1.99], [62684217, 59, 40118, 957, 1, 9.99], [64624770, 59, 40118, 957, 1, 2.59]]')
		select('Table', 'cell:11 - 4|DATE,3(40118)')
		assert_p('Table', 'RowCount', '13')
		select('Table', 'cell:1 - 8|KEYCODE-NO,4(60694698)')
		assert_p('Table', 'Text', '60694698', '1 - 8|KEYCODE-NO,4')
		select('Table', 'cell:1 - 8|KEYCODE-NO,4(60694698)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
