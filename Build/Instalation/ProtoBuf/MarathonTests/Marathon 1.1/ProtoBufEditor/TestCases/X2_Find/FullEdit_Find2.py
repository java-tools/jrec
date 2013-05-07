useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Protocol Buffer Editor'):
		click('Choose File')

		if window('Open'):
			select(commonBits.selectPaneFn(), 'ams_locdownload_20041228.bin')
			click('Open')
		close()
		#commonBits.setRecordLayout(select, 'ams Store')


		click('Edit1')
		select('Table', 'cell:2|Loc_Nbr,0(5839)')
		rightclick('Table', '2|Loc_Nbr,0')
		select_menu('Edit Record')
		click('Find')
		#click('MetalInternalFrameTitlePane', 194, 3)
		select('TextField', 'Shopping Centre')
		select('ComboBox1', 'Contains')
		click('Find1')
		select('Table', 'cell:Data,3(Kalgoorlie (not yet open))')
##		assert_p('Table', 'Content', '[[Brand_Id, 1, , TAR, TAR], [Loc_Nbr, 2, , 5193, 5193], [Loc_Type, 3, , ST, ST], [Loc_Name, 4, , Kalgoorlie (not yet open), Kalgoorlie (not yet open)], [Loc_Addr_Ln1, 5, , Shopping Centre, Shopping Centre], [Loc_Addr_Ln2, 6, , Cnr Cassidy and Egan Streets, Cnr Cassidy and Egan Streets], [Loc_Addr_Ln3, 7, , Kalgoorlie, Kalgoorlie], [Loc_Postcode, 8, , 6430, 6430], [Loc_State, 9, , WA, WA], [Loc_Actv_Ind, 10, , A, A]]')
		assert_p('Table', 'Content', '[[Brand_Id, 1, , TAR, TAR], [Loc_Nbr, 2, , 5169, 5169], [Loc_Type, 3, , ST, ST], [Loc_Name, 4, , Hornsby, Hornsby], [Loc_Addr_Ln1, 5, , Westfield Shopping Centre, Westfield Shopping Centre], [Loc_Addr_Ln2, 6, , George Street, George Street], [Loc_Addr_Ln3, 7, , Hornsby, Hornsby], [Loc_Postcode, 8, , 2077, 2077], [Loc_State, 9, , NSW, NSW], [Loc_Actv_Ind, 10, , A, A]]')
		select('Table', 'cell:Data,3(Kalgoorlie (not yet open))')
		select('ComboBox1', 'Contains')
		select('ComboBox2', 'Backward')
		click('Find1')

		select('Table', 'cell:Data,3(Carindale)')
		assert_p('Table', 'Content', '[[Brand_Id, 1, , TAR, TAR], [Loc_Nbr, 2, , 5169, 5169], [Loc_Type, 3, , ST, ST], [Loc_Name, 4, , Hornsby, Hornsby], [Loc_Addr_Ln1, 5, , Westfield Shopping Centre, Westfield Shopping Centre], [Loc_Addr_Ln2, 6, , George Street, George Street], [Loc_Addr_Ln3, 7, , Hornsby, Hornsby], [Loc_Postcode, 8, , 2077, 2077], [Loc_State, 9, , NSW, NSW], [Loc_Actv_Ind, 10, , A, A]]')
##		assert_p('Table', 'Content', '[[Brand_Id, 1, , TAR, TAR], [Loc_Nbr, 2, , 5174, 5174], [Loc_Type, 3, , ST, ST], [Loc_Name, 4, , Carindale, Carindale], [Loc_Addr_Ln1, 5, , Carindale Shopping Centre, Carindale Shopping Centre], [Loc_Addr_Ln2, 6, , Creek Rd, Creek Rd], [Loc_Addr_Ln3, 7, , Carindale, Carindale], [Loc_Postcode, 8, , 4152, 4152], [Loc_State, 9, , QLD, QLD], [Loc_Actv_Ind, 10, , A, A]]')
		#select('Table', 'cell:Data,3(Carindale)')
		#click('BasicInternalFrameTitlePane$NoFocusButton5')
		#click('BasicInternalFrameTitlePane$NoFocusButton2')
		#click('BasicInternalFrameTitlePane$NoFocusButton2')
	#close()
