useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window(commonBits.applicationName()):
		select('FileNameTxtFld', commonBits.sampleDir() + 'protoSales.bin')
		##commonBits.setRecordLayout(select, 'DTAR020')
		click('Edit1')
		select_menu('View>>Sorted Field Tree')
		##select('List', 'sale')
		select('Table', 'store', 'Field,0')
		##select('Table', 'department', 'Field,1')
		select('Table', 'department', 'Field,1')
		select('Table', 'cell:Field,1(department)')
		click('Build Tree')
		#select('JTreeTable', '')
		rightclick('JTreeTable', 'keycode,1')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:Tree,2(null)')
		select_menu('View>>Record View #{Selected Records#}')
##		select('JTreeTable', 'cell:Tree,2(null)')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,0(61664713)')
		assert_p('BaseLineAsColumn$LineAsColTbl', 'Text', '59', 'Data,1')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,0(61664713)')
		click('RightM')
		doubleclick('TextField')
		assert_p('TextField', 'Text', '3')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,0(61684613)')
		assert_p('BaseLineAsColumn$LineAsColTbl', 'Text', 'cell:Data,0(61684613)')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,0(61684613)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
