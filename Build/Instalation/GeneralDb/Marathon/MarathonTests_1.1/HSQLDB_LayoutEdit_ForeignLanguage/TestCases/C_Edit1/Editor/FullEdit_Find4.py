useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'ams Store')

		click(commonBits.fl('Edit') + '1')
		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
		rightclick('Table', '10 - 35|Loc Name,0')
		select_menu(commonBits.fl('Edit Record'))
		click('Find1')
		#click('MetalInternalFrameTitlePane', 217, 13)
		select('TextField', 'West')
		commonBits.find(click)


		commonBits.find(click)

		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Shoppingtown)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Shoppingtown)')
		assert_p('Table', 'Text', 'Cnr. Urunga Pde & The Kingsway', commonBits.fl('Data') + ',5')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Cnr. Urunga Pde & The Kingsway)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5037, 5037], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Miranda, Miranda], [Loc Addr Ln1, 45, 40, Westfield Shoppingtown, Westfield Shoppingtown], [Loc Addr Ln2, 85, 40, Cnr. Urunga Pde & The Kingsway, Cnr. Urunga Pde & The Kingsway], [Loc Addr Ln3, 125, 35, Miranda, Miranda], [Loc Postcode, 160, 10, 2228, 2228], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Cnr. Urunga Pde & The Kingsway)')
		commonBits.findA(click)

		select('TextField', 'West')

		commonBits.find(click)
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Northumberland Street)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5093, 5093], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Liverpool, Liverpool], [Loc Addr Ln1, 45, 40, Westfield Phoenix Plaza, Westfield Phoenix Plaza], [Loc Addr Ln2, 85, 40, Northumberland Street, Northumberland Street], [Loc Addr Ln3, 125, 35, Liverpool, Liverpool], [Loc Postcode, 160, 10, 2170, 2170], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Northumberland Street)')
		commonBits.findA(click)

		select('TextField', 'West')

		commonBits.find(click)
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(152 Bunnerong Road)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5095, 5095], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Eastgarden, Eastgarden], [Loc Addr Ln1, 45, 40, Westfield Shoppingtown Eastgardens, Westfield Shoppingtown Eastgardens], [Loc Addr Ln2, 85, 40, 152 Bunnerong Road, 152 Bunnerong Road], [Loc Addr Ln3, 125, 35, Eastgardens, Eastgardens], [Loc Postcode, 160, 10, 2036, 2036], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(152 Bunnerong Road)')
		commonBits.findA(click)

		select('ComboBox2',  commonBits.fl('Backward'))
		assert_p('ComboBox2', 'Text', commonBits.fl('Backward'))
		commonBits.find(click)
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Northumberland Street)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5093, 5093], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Liverpool, Liverpool], [Loc Addr Ln1, 45, 40, Westfield Phoenix Plaza, Westfield Phoenix Plaza], [Loc Addr Ln2, 85, 40, Northumberland Street, Northumberland Street], [Loc Addr Ln3, 125, 35, Liverpool, Liverpool], [Loc Postcode, 160, 10, 2170, 2170], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Phoenix Plaza)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5093, 5093], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Liverpool, Liverpool], [Loc Addr Ln1, 45, 40, Westfield Phoenix Plaza, Westfield Phoenix Plaza], [Loc Addr Ln2, 85, 40, Northumberland Street, Northumberland Street], [Loc Addr Ln3, 125, 35, Liverpool, Liverpool], [Loc Postcode, 160, 10, 2170, 2170], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Phoenix Plaza)')
		commonBits.findA(click)

		select('ComboBox2',  commonBits.fl('Backward'))
		commonBits.find(click)
		select('Table', 'cell:' + commonBits.fl('Data') + ',4(Westfield Shoppingtown)')
		assert_p('Table', 'Text', 'Westfield Shoppingtown', commonBits.fl('Data') + ',4')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Cnr. Urunga Pde & The Kingsway)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5037, 5037], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Miranda, Miranda], [Loc Addr Ln1, 45, 40, Westfield Shoppingtown, Westfield Shoppingtown], [Loc Addr Ln2, 85, 40, Cnr. Urunga Pde & The Kingsway, Cnr. Urunga Pde & The Kingsway], [Loc Addr Ln3, 125, 35, Miranda, Miranda], [Loc Postcode, 160, 10, 2228, 2228], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Cnr. Urunga Pde & The Kingsway)')
		commonBits.findA(click)

		select('ComboBox2',  commonBits.fl('Backward'))
		commonBits.find(click)
		select('Table', 'cell:' + commonBits.fl('Data') + ',3(VIC West Ad Support)')
		assert_p('Table', 'Text', 'cell:' + commonBits.fl('Data') + ',3(VIC West Ad Support)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Lot 2 Little Boundary Rd)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5850, 5850], [Loc Type, 8, 2, DC, DC], [Loc Name, 10, 35, VIC West Ad Support, VIC West Ad Support], [Loc Addr Ln1, 45, 40, , ], [Loc Addr Ln2, 85, 40, Lot 2 Little Boundary Rd, Lot 2 Little Boundary Rd], [Loc Addr Ln3, 125, 35, Laverton, Laverton], [Loc Postcode, 160, 10, 3028, 3028], [Loc State, 170, 3, VIC, VIC], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Lot 2 Little Boundary Rd)')
		commonBits.findA(click)

		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		#click('MetalInternalFrameTitlePane', 896, 16)
		#click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
