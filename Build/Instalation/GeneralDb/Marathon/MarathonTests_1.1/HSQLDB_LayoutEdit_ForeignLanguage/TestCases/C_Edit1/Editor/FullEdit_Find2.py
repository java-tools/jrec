useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		click(commonBits.fl('Choose File'))

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228.txt')
			click('Open')
		close()
		commonBits.setRecordLayout(select, 'ams Store')


		click(commonBits.fl('Edit') + '1')
		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
		rightclick('Table', '4 - 4|Loc Nbr,0')
		select_menu(commonBits.fl('Edit Record'))
		click('Find')
		#click('MetalInternalFrameTitlePane', 194, 3)
		select('TextField', 'Shopping Centre')
		select('ComboBox1', commonBits.fl('Contains')
)
		##click('Find1')
		commonBits.find(click)

		select('Table', 'cell:' + commonBits.fl('Data') + ',3(Kalgoorlie (not yet open))')
##		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5193, 5193], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Kalgoorlie (not yet open), Kalgoorlie (not yet open)], [Loc Addr Ln1, 45, 40, Shopping Centre, Shopping Centre], [Loc Addr Ln2, 85, 40, Cnr Cassidy and Egan Streets, Cnr Cassidy and Egan Streets], [Loc Addr Ln3, 125, 35, Kalgoorlie, Kalgoorlie], [Loc Postcode, 160, 10, 6430, 6430], [Loc State, 170, 3, WA, WA], [Loc Actv Ind, 173, 1, A, A]]')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5169, 5169], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Hornsby, Hornsby], [Loc Addr Ln1, 45, 40, Westfield Shopping Centre, Westfield Shopping Centre], [Loc Addr Ln2, 85, 40, George Street, George Street], [Loc Addr Ln3, 125, 35, Hornsby, Hornsby], [Loc Postcode, 160, 10, 2077, 2077], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',3(Kalgoorlie (not yet open))')
		select('ComboBox1', commonBits.fl('Contains')
)
		select('ComboBox2',  commonBits.fl('Backward')
)
		##click('Find1')
		commonBits.find(click)


		select('Table', 'cell:' + commonBits.fl('Data') + ',3(Carindale)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5169, 5169], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Hornsby, Hornsby], [Loc Addr Ln1, 45, 40, Westfield Shopping Centre, Westfield Shopping Centre], [Loc Addr Ln2, 85, 40, George Street, George Street], [Loc Addr Ln3, 125, 35, Hornsby, Hornsby], [Loc Postcode, 160, 10, 2077, 2077], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
##		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5174, 5174], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Carindale, Carindale], [Loc Addr Ln1, 45, 40, Carindale Shopping Centre, Carindale Shopping Centre], [Loc Addr Ln2, 85, 40, Creek Rd, Creek Rd], [Loc Addr Ln3, 125, 35, Carindale, Carindale], [Loc Postcode, 160, 10, 4152, 4152], [Loc State, 170, 3, QLD, QLD], [Loc Actv Ind, 173, 1, A, A]]')
		#select('Table', 'cell:' + commonBits.fl('Data') + ',3(Carindale)')
		#click('BasicInternalFrameTitlePane$NoFocusButton5')
		#click('BasicInternalFrameTitlePane$NoFocusButton2')
		#click('BasicInternalFrameTitlePane$NoFocusButton2')
	#close()
