useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		commonBits.setRecordLayout(select, 'DTAR020')
		click('Edit1')
		select_menu('View>>Field Based Tree')
		select('List', 'DTAR020')
		select('Table', 'QTY-SOLD', 'Field,0')
		select('Table', 'DEPT-NO', 'Field,1')
		select('Table', 'cell:Field,1(DEPT-NO)')
		click('Build Tree')
		select('JTreeTable', 'cell:DEPT-NO,7(null)')
		assert_p('JTreeTable', 'RowCount', '95')
		select('JTreeTable', 'cell:KEYCODE-NO,0(null)')
		select_menu('View>>Table View #{Selected Records#}')
		select('JTreeTable', 'cell:KEYCODE-NO,0(null)')
		select('Table', 'cell:9 - 2|STORE-NO,0(20)')
		select('Table', 'cell:1 - 8|KEYCODE-NO,0(69684558)')
		select('Table', 'cell:11 - 4|DATE,0(40118)')
		select('Table', 'cell:22 - 6|SALE-PRICE,0(19.00)')
		assert_p('Table', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00]]')
		select('Table', 'cell:22 - 6|SALE-PRICE,0(19.00)')
	close()
