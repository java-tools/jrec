useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')

		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Sorted Field Tree'))
#		select('List', 'DTAR020')
#		select('List', 'DTAR020')
		select('Table', 'cell:' + commonBits.fl('Field') + ',0( )')
#		select('List', 'DTAR020')
		select('Table', 'DEPT-NO', commonBits.fl('Field') + ',0')
		select('Table', 'DATE', commonBits.fl('Field') + ',1')
		select('Table', 'cell:' + commonBits.fl('Field') + ',1(DATE)')
		select('Table1', 'cell:' + commonBits.fl('Function') + ',2()')
		select('Table', 'cell:' + commonBits.fl('Field') + ',1(DATE)')
		select('Table1', commonBits.fl('Maximum'), commonBits.fl('Function') + ',2')
		select('Table1', commonBits.fl('Maximum'), commonBits.fl('Function') + ',3')
		select('Table1', commonBits.fl('Sum'), commonBits.fl('Function') + ',4')
		select('Table1', commonBits.fl('Sum'), commonBits.fl('Function') + ',5')
		select('Table1', 'cell:' + commonBits.fl('Function') + ',5(Sum)')
		##commonBits.save1(click)
		commonBits.save(click)

		##select('FileChooser', commonBits.userDir() +  'SortTree'  + commonBits.fileSep() + 'xx2')
		commonBits.selectFileName(select, keystroke, commonBits.userDir() +  'SortTree'  + commonBits.fileSep() + 'xx2')
		commonBits.save1(click)
		select_menu(commonBits.fl('Window') + '>>DTAR020.bin>>' + commonBits.fl('Create Sorted Tree'))
		click(commonBits.fl('Build Tree'))
		select('JTreeTable', 'cell:DATE,0(40118)')
		assert_p('JTreeTable', 'Text', '40118', 'DATE,0')
		select('JTreeTable', 'cell:DEPT-NO,0(60)')
		assert_p('JTreeTable', 'Text', '5', 'QTY-SOLD,2')
		select('JTreeTable', 'cell:SALE-PRICE,0(8.74)')
		assert_p('JTreeTable', 'Text', '166.94', 'SALE-PRICE,1')
		select('JTreeTable', 'cell:SALE-PRICE,1(166.94)')
		assert_p('JTreeTable', 'Text', '166.94', 'SALE-PRICE,1')
		select('JTreeTable', 'cell:' + commonBits.fl('Tree') + ',1(null)')
		rightclick('JTreeTable', commonBits.fl('Tree') + ',1')
		select_menu(commonBits.fl('Expand Tree'))
		select('JTreeTable', 'cell:QTY-SOLD,2(8)')
		assert_p('JTreeTable', 'Text', '5', 'QTY-SOLD,3')
		select('JTreeTable', 'cell:' + commonBits.fl('Tree') + ',2(null)')
		rightclick('JTreeTable', commonBits.fl('Tree') + ',2')
		select_menu(commonBits.fl('Expand Tree'))
		select('JTreeTable', 'cell:SALE-PRICE,3(12.99)')
		assert_p('JTreeTable', 'Text', '12.99', 'SALE-PRICE,4')
		select('JTreeTable', 'cell:' + commonBits.fl('Tree') + ',14(null)')
		rightclick('JTreeTable', commonBits.fl('Tree') + ',14')
		select_menu(commonBits.fl('Edit Record'))
		select('JTreeTable', 'cell:' + commonBits.fl('Tree') + ',14(null)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Window') + '>>DTAR020.bin>>' + commonBits.fl('Table:'))
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Execute Sort Tree'))
		##select('FileChooser', commonBits.userDir() +  'SortTree'  + commonBits.fileSep() + 'xx2')
		commonBits.selectFileName(select, keystroke, commonBits.userDir() +  'SortTree'  + commonBits.fileSep() + 'xx2')
		click(commonBits.fl('Run')
)
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

