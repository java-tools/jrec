useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoSales.bin')
		##commonBits.setRecordLayout(select, 'DTAR020')
		click('Edit1')
		select_menu('View>>Sorted Field Tree')
		##select('List', 'sale')
		select('Table', 'store', 'Field,0')
		select('Table', 'department', 'Field,1')
		select('Table', 'cell:Field,1(department)')
		click('Build Tree')
		select('JTreeTable', 'cell:Tree,1(null)')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'cell:Tree,1(null)')
		select('Table', 'cell:1|keycode,2(61684613)')
##		assert_p('Table', 'Content', '[[61664713, 59, 40118, 335, 1, 17990], [61664713, 59, 40118, 335, -1, -17990], [61684613, 59, 40118, 335, 1, 12990], [68634752, 59, 40118, 410, 1, 8990], [60694698, 59, 40118, 620, 1, 3990], [60664659, 59, 40118, 620, 1, 3990], [60614487, 59, 40118, 878, 1, 5950], [63644339, 59, 40118, 878, 1, 12650], [67674686, 59, 40118, 929, 1, 3990], [64614401, 59, 40118, 957, 1, 1990], [64614401, 59, 40118, 957, 1, 1990], [62684217, 59, 40118, 957, 1, 9990], [64624770, 59, 40118, 957, 1, 2590]]')
		assert_p('Table', 'Content', '[[61664713, 59, 335, 40118, 1, 17990], [61664713, 59, 335, 40118, -1, -17990], [61684613, 59, 335, 40118, 1, 12990], [68634752, 59, 410, 40118, 1, 8990], [60694698, 59, 620, 40118, 1, 3990], [60664659, 59, 620, 40118, 1, 3990], [60614487, 59, 878, 40118, 1, 5950], [63644339, 59, 878, 40118, 1, 12650], [67674686, 59, 929, 40118, 1, 3990], [64614401, 59, 957, 40118, 1, 1990], [64614401, 59, 957, 40118, 1, 1990], [62684217, 59, 957, 40118, 1, 9990], [64624770, 59, 957, 40118, 1, 2590]]')

		select('Table', 'cell:1|keycode,2(61684613)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('JTreeTable', 'cell:Tree,2(null)')
	close()
