useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		commonBits.setRecordLayout(select, 'DTAR020')

		click('Edit1')
		select_menu('View>>Field Based Tree')
##		select('List', 'DTAR020')
		select('Table', 'STORE-NO', 'Field,0')
		select('Table', 'DEPT-NO', 'Field,1')
		select('Table', 'cell:Field,1(DEPT-NO)')
		#click('MetalInternalFrameTitlePane', 153, 11)
		#click('MetalInternalFrameTitlePane', 146, 3)
		#click('MetalInternalFrameTitlePane', 199, 6)
		click('Build Tree')
		select('JTreeTable', 'cell:STORE-NO,2(null)')
		assert_p('JTreeTable', 'RowCount', '4')
		select('JTreeTable', 'cell:STORE-NO,2(null)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
