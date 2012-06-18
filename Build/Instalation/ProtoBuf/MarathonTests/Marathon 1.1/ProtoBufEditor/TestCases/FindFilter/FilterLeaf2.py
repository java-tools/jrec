useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		click('Edit1')
		click('Filter')
		click('Uncheck Records')
		select('Table', 'cell:Include,0(false)')
		select('Table1', 'cell:Include,1(true)')
		select('Table1', 'cell:Include,3(true)')
		select('Table2', 'cell:Field,0(null)')
		select('Table2', 'keycode', 'Field,0')
		select('Table2', '66', 'Value,0')
		select('Table2', 'quantity', 'Field,1')
		select('Table2', '-1', 'Value,1')
		select('Table2', 'cell:Value,1()')
		click('Filter1')
		select('Table', 'cell:1|keycode,6(60664048)')
		assert_p('Table', 'Content', '[[61664713, -1], [68644966, -1], [60614866, -1], [60614866, -1], [60664048, -1], [60664048, -1], [60664048, -1], [66624803, -1], [66624889, -1], [69664171, -1], [62664183, -1], [69664620, -1], [68664363, -1], [64664587, -1], [66614582, -1], [69664149, -1], [69664163, -1], [66614192, -1], [66614192, -1]]')
		select_menu('Window>>protoStoreSales3.bin>>Filter Options')
		select('Table1', 'cell:Include,3(false)')
		click('Filter1')
		select('Table', 'cell:1|keycode,6(60664048)')
		assert_p('Table', 'Content', '[[61664713, -1, -17990], [68644966, -1, -12500], [60614866, -1, -4800], [60614866, -1, -4800], [60664048, -1, -4800], [60664048, -1, -4800], [60664048, -1, -4800], [66624803, -1, -2000], [66624889, -1, -2000], [69664171, -1, -27950], [62664183, -1, -20990], [69664620, -1, -11890], [68664363, -1, -29950], [64664587, -1, -16990], [66614582, -1, -15990], [69664149, -1, -5990], [69664163, -1, -1390], [66614192, -1, -1230], [66614192, -1, -1230]]')
	close()
