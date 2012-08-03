useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		click(commonBits.fl('Choose File'))

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228.txt')
			click('Open')
		close()

		click(commonBits.fl('Edit') + '1')
		rightclick('Table', '10 - 35|Loc Name,0')
		select_menu(commonBits.fl('Edit Record'))
		click('Find1')
	#	click('MetalInternalFrameTitlePane', 193, 6)
		select('TextField', 'West')
		commonBits.find(click)
		select('Table', 'cell:' + commonBits.fl('Data') + ',3(VIC West Ad Support)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',3(VIC West Ad Support)')
		assert_p('Table', 'Text', 'VIC West Ad Support', commonBits.fl('Data') + ',3')
		select('Table', 'cell:' + commonBits.fl('Data') + ',3(VIC West Ad Support)')
		commonBits.findA(click)
		commonBits.find(click)
#		commonBits.find(click)
		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Shoppingtown)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Shoppingtown)')
		assert_p('Table', 'Text', 'Westfield Shoppingtown', commonBits.fl('Data') + ',4')
		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Shoppingtown)')
		commonBits.findA(click)
		commonBits.find(click)
#		commonBits.find(click)
		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Phoenix Plaza)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Phoenix Plaza)')
		assert_p('Table', 'Text', 'Westfield Phoenix Plaza', commonBits.fl('Data') + ',4')
	close()
