useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Protocol Buffer Editor'):
		click('Choose File')

		if window('Open'):
			select(commonBits.selectPaneFn(), 'ams_locdownload_20041228.bin')
			click('Open')
		close()

		click('Edit1')
		rightclick('Table', '4|Loc_Name,0')
		select_menu('Edit Record')
		click('Find1')
	#	click('MetalInternalFrameTitlePane', 193, 6)
		select('TextField', 'West')
		click('Find1')
		select('Table', 'cell:Data,3(VIC West Ad Support)')
		select('Table', 'cell:Data,3(VIC West Ad Support)')
		assert_p('Table', 'Text', 'VIC West Ad Support', 'Data,3')
		select('Table', 'cell:Data,3(VIC West Ad Support)')
		click('Find1')
		click('Find1')
##		click('Find1')
		select('Table', 'cell:Data,4(Westfield Shoppingtown)')
		select('Table', 'cell:Data,4(Westfield Shoppingtown)')
		assert_p('Table', 'Text', 'Westfield Shoppingtown', 'Data,4')
		select('Table', 'cell:Data,4(Westfield Shoppingtown)')
		click('Find1')
		click('Find1')
##		click('Find1')
		select('Table', 'cell:Data,4(Westfield Phoenix Plaza)')
		select('Table', 'cell:Data,4(Westfield Phoenix Plaza)')
		assert_p('Table', 'Text', 'Westfield Phoenix Plaza', 'Data,4')
	close()
