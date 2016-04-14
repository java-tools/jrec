useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window(commonBits.applicationName()):
		click('FileSearch')
		if window('Open'):
			select(commonBits.selectPaneFn(), 'Ams_LocDownload_20041228.bin')
			click('Open')
		close()

		click('Edit1')
		rightclick('LinesTbl', '4|Loc_Name,0')
		select_menu('Edit Record')
		click('Find1')
	#	click('MetalInternalFrameTitlePane', 193, 6)
		select('TextField', 'West')
		click('Find1')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,3(VIC West Ad Support)')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,3(VIC West Ad Support)')
		assert_p('BaseLineAsColumn$LineAsColTbl', 'Text', 'VIC West Ad Support', 'Data,3')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,3(VIC West Ad Support)')
		click('Find1')
		click('Find1')
##		click('Find1')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,4(Westfield Shoppingtown)')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,4(Westfield Shoppingtown)')
		assert_p('BaseLineAsColumn$LineAsColTbl', 'Text', 'Westfield Shoppingtown', 'Data,4')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,4(Westfield Shoppingtown)')
		click('Find1')
		click('Find1')
##		click('Find1')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,4(Westfield Phoenix Plaza)')
		select('BaseLineAsColumn$LineAsColTbl', 'cell:Data,4(Westfield Phoenix Plaza)')
		assert_p('BaseLineAsColumn$LineAsColTbl', 'Text', 'Westfield Phoenix Plaza', 'Data,4')
	close()
