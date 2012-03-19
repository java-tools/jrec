useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')

		click('Edit1')
		select_menu('View>>Sorted Field Tree')
#		select('List', 'DTAR020')
#		select('List', 'DTAR020')
		select('Table', 'cell:Field,0( )')
#		select('List', 'DTAR020')
		select('Table', 'DEPT-NO', 'Field,0')
		select('Table', 'DATE', 'Field,1')
		select('Table', 'cell:Field,1(DATE)')
		select('Table1', 'cell:Function,2()')
		select('Table', 'cell:Field,1(DATE)')
		select('Table1', 'Maximum', 'Function,2')
		select('Table1', 'Maximum', 'Function,3')
		select('Table1', 'Sum', 'Function,4')
		select('Table1', 'Sum', 'Function,5')
		select('Table1', 'cell:Function,5(Sum)')
		click('Save1')
		##select('FileChooser', commonBits.userDir() +  'SortTree'  + commonBits.fileSep() + 'xx2')
		commonBits.selectFileName(select, commonBits.userDir() +  'SortTree'  + commonBits.fileSep() + 'xx2')
		click('Save1')
		select_menu('Window>>DTAR020.bin>>Create Sorted Tree')
		click('Build Tree')
		select('JTreeTable', 'cell:DATE,0(40118)')
		assert_p('JTreeTable', 'Text', '40118', 'DATE,0')
		select('JTreeTable', 'cell:DEPT-NO,0(60)')
		assert_p('JTreeTable', 'Text', '5', 'QTY-SOLD,2')
		select('JTreeTable', 'cell:SALE-PRICE,0(8.74)')
		assert_p('JTreeTable', 'Text', '166.94', 'SALE-PRICE,1')
		select('JTreeTable', 'cell:SALE-PRICE,1(166.94)')
		assert_p('JTreeTable', 'Text', '166.94', 'SALE-PRICE,1')
		select('JTreeTable', 'cell:Tree,1(null)')
		rightclick('JTreeTable', 'Tree,1')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:QTY-SOLD,2(8)')
		assert_p('JTreeTable', 'Text', '5', 'QTY-SOLD,3')
		select('JTreeTable', 'cell:Tree,2(null)')
		rightclick('JTreeTable', 'Tree,2')
		select_menu('Expand Tree')
		select('JTreeTable', 'cell:SALE-PRICE,3(12.99)')
		assert_p('JTreeTable', 'Text', '12.99', 'SALE-PRICE,4')
		select('JTreeTable', 'cell:Tree,14(null)')
		rightclick('JTreeTable', 'Tree,14')
		select_menu('Edit Record')
		select('JTreeTable', 'cell:Tree,14(null)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>DTAR020.bin>>Table: ')
		select_menu('View>>Execute Sort Tree')
		##select('FileChooser', commonBits.userDir() +  'SortTree'  + commonBits.fileSep() + 'xx2')
		commonBits.selectFileName(select, commonBits.userDir() +  'SortTree'  + commonBits.fileSep() + 'xx2')
		click('Run')
		select('JTreeTable', 'cell:SALE-PRICE,1(166.94)')
		assert_p('JTreeTable', 'Text', '166.94', 'SALE-PRICE,1')
		select('JTreeTable', 'cell:SALE-PRICE,2(87.46)')
		assert_p('JTreeTable', 'Text', '87.46', 'SALE-PRICE,2')
		select('JTreeTable', 'cell:SALE-PRICE,3(-15.85)')
		assert_p('JTreeTable', 'Text', '-15.85', 'SALE-PRICE,3')
		select('JTreeTable', 'cell:SALE-PRICE,9(-4.00)')
		select('JTreeTable', 'cell:SALE-PRICE,7(14.09)')
		assert_p('JTreeTable', 'Text', '17.73', 'SALE-PRICE,8')
	close()

