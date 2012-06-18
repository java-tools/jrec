useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'
	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Edit1')
		click('Filter')
		click('Uncheck Records')
		select('Table', 'cell:Include,4(false)')
		click('Filter1')
		select('Table', 'cell:2|name,1(Store: 59)')
		assert_p('Table', 'Content', '[[20, Store: 20], [59, Store: 59], [166, Store: 166], [184, Store: 184]]')
		select('Table', '5911', '1|store,1')
		select('Table', 'cell:2|name,2(Store: 166)')
		assert_p('Table', 'Content', '[[20, Store: 20], [5911, Store: 59], [166, Store: 166], [184, Store: 184]]')
		select('Table', 'cell:2|name,2(Store: 166)')
		select_menu('Window>>protoStoreSales3.bin>>Table: ')
		select('Table', 'cell:2|name,2(Store: 166)')
		select_menu('Window>>protoStoreSales3.bin>>Tree View')
		select('Table', 'cell:2|name,2(Store: 166)')
		select('JTreeTable', 'cell:Tree,1(null)')
		assert_p('JTreeTable', 'Content', '[[, , 20, Store: 20], [, , 5911, Store: 59], [, , 166, Store: 166], [, , 184, Store: 184]]')
		select('JTreeTable', 'cell:Tree,1(null)')
	close()
