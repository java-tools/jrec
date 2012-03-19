useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() +  'DTAR020.bin')
		click('Edit1')
		select('Table', 'rows:[3,4,5,6,7,8,9,10],columns:[1 - 8|KEYCODE-NO]')
		select_menu('View>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[3,4,5,6,7,8,9,10],columns:[1 - 8|KEYCODE-NO]')
		select('Table', 'cell:9 - 2|STORE-NO,1(20)')
		assert_p('Table', 'Content', '[[69694158, 20, 40118, 280, 1, 19.00], [69694158, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [63604808, 20, 40118, 170, 1, 4.87], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [64634429, 20, 40118, 957, 1, 3.99], [66624458, 20, 40118, 957, 1, 0.89]]')
		select('Table', 'cell:9 - 2|STORE-NO,1(20)')
		select('LayoutCombo', 'Hex 1 Line')
		select('Table', 'cell:         +         1         +         2         +         3|Hex (1 Line),3([B@16ce64e)')
##		assert_p('Table', 'Content', '[[[B@16269c], [[B@1a95cf6], [[B@844c3d], [[B@16ce64e], [[B@1188793], [[B@1e5d16d], [[B@188a478], [[B@14fb35d]]')
		select('Table', 'cell:         +         1         +         2         +         3|Hex (1 Line),1([B@1a95cf6)')
		assert_p('Table', 'Text', 'f6f3f6f0f4f8f0f8020c0040118c170c000000001c00000000487c', '         +         1         +         2         +         3|Hex (1 Line),3')
		select('Table', 'cell:         +         1         +         2         +         3|Hex (1 Line),2([B@844c3d)')
		assert_p('Table', 'Text', 'f6f9f6f9f4f1f5f8020c0040118c280c000000001c00000000501c', '         +         1         +         2         +         3|Hex (1 Line),2')
		select('Table', 'cell:         +         1         +         2         +         3|Hex (1 Line),3([B@16ce64e)')
		assert_p('Table', 'Text', 'f6f2f6f8f4f6f7f1020c0040118c685c000000001c00000006999c', '         +         1         +         2         +         3|Hex (1 Line),4')
	close()
