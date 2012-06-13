useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() +  'DTAR020_tst1.bin')
		select('ComboBox2', 'Unknown Format')
		commonBits.doEdit(click)

		if window(''):
			select('TextField1', 'cp037')
			select('Table', 'cell:A,1(6)')
##			select('Table', '')
			if commonBits.isVersion82():
				assert_p('TextField', 'Text', '27')
			elif commonBits.isVersion80():
				assert_p('TextField', 'Text', '138')
			else:
				assert_p('TextField', 'Text', '100')

			##select('BmKeyedComboBox', '2')
			select('BmKeyedComboBox', 'Fixed Length Binary')

			select('TextField', '40')
			select('TextField1', 'cp037')
			assert_p('Table', 'RowCount', '12')

			assert_p('Table', 'ColumnCount', '40')
			select('TextField', '54')
			select('TextField1', 'cp037')
##			select('Table', '')
			assert_p('TextField', 'Text', '54')
			select('TextField1', 'cp037')
			assert_p('Table', 'RowCount', '9')
			assert_p('Table', 'ColumnCount', '54')
			select('TextField', '27')
			select('TextField1', 'cp037')
			assert_p('Table', 'RowCount', '17')
			commonBits.doSleep()
			click('Go')
			commonBits.doSleep()



		close()


		rightclick('Table', '1 - 27|Data,1')
		select_menu('Edit Record')
##		select('Table1', '''cell:1 - 27|Data,1(69684558		assert_p('Table', 'Text', 'f6f9f6f8f4f5f5f8020c0040118c280c000000001c00000001900c', 'Hex,0')
		select_menu('Window>>DTAR020_tst1.bin>>Table: ')

		select('LayoutCombo', 'Hex 1 Line')
##		assert_p('Table', 'Content', '[[[B@cd2bfc], [[B@8fb561], [[B@1304ef4], [[B@fa8cc6], [[B@2d14a], [[B@1cf6930], [[B@14bf534], [[B@9bee93], [[B@23c5ff], [[B@496381], [[B@f02db7], [[B@6d3209], [[B@fc7ceb], [[B@22d5b5], [[B@1b5080a], [[B@5e95ae], [[B@1ada1e0]]')
		select_menu('Edit>>Change Layout')
		select('ComboBox1', 'Mainframe')
		click('Go')
##		click('MetalInternalFrameTitlePane', 464, 10)
##commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>DTAR020_tst1.bin>>Table: ')

		assert_p('Table', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95], [68654655, 166, 40118, 60, 1, 5.08], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30], [68674560, 166, 40118, 170, 1, 5.99]]')
		assert_p('Table', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95], [68654655, 166, 40118, 60, 1, 5.08], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30], [68674560, 166, 40118, 170, 1, 5.99]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Open')
		select('ComboBox1', 'Mainframe')
		click('Edit1')
		assert_p('Table', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95], [68654655, 166, 40118, 60, 1, 5.08], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30], [68674560, 166, 40118, 170, 1, 5.99]]')
	close()

