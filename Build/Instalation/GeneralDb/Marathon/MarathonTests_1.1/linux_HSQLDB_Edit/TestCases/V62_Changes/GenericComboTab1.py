useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'zzAms_LocDownload_tab.csv')
		commonBits.setRecordLayout(select, 'Generic CSV - enter details')
		commonBits.doEdit(click)


		if window(''):
			select('CheckBox', 'true')
			select('Table', 'rows:[0,1],columns:[Brand_Id,Loc_Nbr,Loc_Type,Loc_Name]')
##			rightclick('Table', 'Loc_Name,1')
			select('Table', 'cell:Loc_Name,3(WA Ad Support)')
			assert_p('Table', 'Text', 'WA Ad Support', 'Loc_Name,3')
			select('Table', 'cell:Loc_Name,6(Blacktown)')
			assert_p('Table', 'Text', 'Blacktown', 'Loc_Name,6')
			select('Table', 'cell:Loc_Name,6(Blacktown)')
			commonBits.doSleep()

			click('Go')
			commonBits.doSleep()

			commonBits.doSleep()

		close()

		commonBits.doSleep()

		commonBits.doSleep()


		select('Table', 'cell:5|Loc_Addr_Ln1,8(Westfield Shoppingtown)')
		assert_p('Table', 'Text', 'Campbelltown Mall', '5|Loc_Addr_Ln1,13')
		select('Table', 'cell:5|Loc_Addr_Ln1,13(Campbelltown Mall)')
		assert_p('Table', 'Text', 'Campbelltown Mall', '5|Loc_Addr_Ln1,13')
		select('Table', 'rows:[5,6,7],columns:[4|Loc_Name]')
		select_menu('View>>Column View #{Selected Records#}')
##		select('Table2', 'rows:[5,6,7],columns:[4|Loc_Name]')
		select('Table', 'cell:Row 1,5(58 Leland Street)')
		assert_p('Table', 'Text', '58 Leland Street', 'Row 1,5')
		select('Table', 'cell:Row 2,6( 11 Melissa Place)')
		assert_p('Table', 'Text', 'Marayong', 'Row 2,7')
		select('Table', 'cell:Row 2,6( 11 Melissa Place)')
		assert_p('Table', 'Content', '[[TAR, TAR, TAR], [5019, 5033, 5035], [ST, ST, ST], [Penrith, Blacktown, Rockdale], [Penrith, Marayong, Building B], [58 Leland Street, Dock 2,   Portside DC], [Penrith,  11 Melissa Place, 2-8 Mc Pherson Street], [2750, Marayong, Botany], [NSW, 2148, 2019], [A, NSW, NSW], [, A, A]]')
		select('Table', 'cell:Row 2,6( 11 Melissa Place)')
		assert_p('Table', 'ColumnCount', '3')
		select('Table', 'cell:Row 3,6(2-8 Mc Pherson Street)')
		assert_p('Table', 'RowCount', '11')
		select('Table', 'cell:Row 3,2(ST)')
		select('Table1', 'cell:Field,2(Loc_Type)')
		assert_p('Table1', 'Content', '[[Brand_Id, 1, ], [Loc_Nbr, 2, ], [Loc_Type, 3, ], [Loc_Name, 4, ], [Loc_Addr_Ln1, 5, ], [Loc_Addr_Ln2, 6, ], [Loc_Addr_Ln3, 7, ], [Loc_Postcode, 8, ], [Loc_State, 9, ], [Loc_Actv_Ind, 10, ], [K, 11, ]]')
		select('Table1', 'cell:Field,2(Loc_Type)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'rows:[5,6,7],columns:[4|Loc_Name]')
	close()
