useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'protoStoreSales3.bin')
		select('FileChooser1',  commonBits.stdCopybookDir() + 'StoreSales3.protocomp')
		click('Edit1')
		click('Filter')
		click('Uncheck Records')
		select('Table', 'cell:Include,0(false)')
		select('Table2', 'cell:Field,0(null)')
		select('Table2', 'keycode', 'Field,0')
		select('Table2', '66', 'Value,0')
		select('Table2', 'quantity', 'Field,1')
		select('Table2', '-1', 'Value,1')
		select('Table2', 'cell:Value,1()')
		click('Filter1')
		select('Table', 'cell:2|saleDate,4(40118)')
		assert_p('Table', 'Content', '[[61664713, 40118, -1, -17990], [68644966, 40118, -1, -12500], [60614866, 40118, -1, -4800], [60614866, 40118, -1, -4800], [60664048, 40118, -1, -4800], [60664048, 40118, -1, -4800], [60664048, 40118, -1, -4800], [66624803, 40118, -1, -2000], [66624889, 40118, -1, -2000], [69664171, 40118, -1, -27950], [62664183, 40118, -1, -20990], [69664620, 40118, -1, -11890], [68664363, 40118, -1, -29950], [64664587, 40118, -1, -16990], [66614582, 40118, -1, -15990], [69664149, 40118, -1, -5990], [69664163, 40118, -1, -1390], [66614192, 40118, -1, -1230], [66614192, 40118, -1, -1230]]')
	close()
