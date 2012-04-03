useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_10'

	if window('Record Editor'):
		commonBits.selectOldFilemenu(select_menu, 'Edit', 'Compare Menu')
		click('*2')
		select('FileChooser', commonBits.sampleDir() + 'xmlModDTAR020.bin.xml')
		click('Right')
		select('TabbedPane', '')
		select('FileChooser', commonBits.sampleDir() + 'DTAR020.bin')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:Record,2(DTAR020)')
		select('Table1', 'cell:Equivalent Field,3()')
		select('Table', 'cell:Record,2(DTAR020)')
		select('Table1', 'KEYCODE-NO', 'Equivalent Field,3')
		select('Table1', 'STORE-NO', 'Equivalent Field,4')
		select('Table1', 'DATE', 'Equivalent Field,5')
		select('Table1', 'DEPT-NO', 'Equivalent Field,6')
##		select('Table1', 'cell:Equivalent Field,6(DEPT-NO)')
##		click('MetalScrollButton4')
		select('Table1', 'QTY-SOLD', 'Equivalent Field,7')
		select('Table1', 'SALE-PRICE', 'Equivalent Field,8')
		select('Table1', 'QTY-SOLD', 'Equivalent Field,7')
		select('Table1', 'QTY-SOLD', 'Equivalent Field,7')
##		select('Table1', 'cell:Equivalent Field,8(SALE-PRICE)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		assert_p('TextPane', 'Text', 'Files are Identical !!!')
	close()
