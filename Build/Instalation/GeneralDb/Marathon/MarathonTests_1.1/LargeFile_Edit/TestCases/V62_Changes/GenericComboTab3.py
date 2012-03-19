useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'zzAms_LocDownload_tab.csv')
		commonBits.setRecordLayout(select, 'Generic CSV - enter details')
		commonBits.doEdit(click)


		if window(''):
			commonBits.doSleep()
			select('CheckBox', 'false')

			click('Go')
			commonBits.doSleep()

			commonBits.doSleep()

		close()

		commonBits.doSleep()

		commonBits.doSleep()


		select('Table', 'cell:4|D,6(Penrith)')
		assert_p('Table', 'Text', 'Penrith', '4|D,6')
		select('Table', 'rows:[0,1,2],columns:[4|D]')
		select_menu('View>>Column View #{Selected Records#}')
##		select('Table2', 'rows:[0,1,2],columns:[4|D]')
		select('Table', 'cell:Row 1,3(Loc_Name)')
		assert_p('Table', 'Text', 'Loc_Name', 'Row 1,3')
		select('Table', 'cell:Row 1,3(Loc_Name)')
		assert_p('Table', 'Content', '[[Brand_Id, TAR, TAR], [Loc_Nbr, 5839, 5850], [Loc_Type, DC, DC], [Loc_Name, DC - Taras Ave, VIC West Ad Support], [Loc_Addr_Ln1, , ], [Loc_Addr_Ln2, 30-68 Taras Ave, Lot 2 Little Boundary Rd], [Loc_Addr_Ln3, Altona North, Laverton], [Loc_Postcode, 3025, 3028], [Loc_State, VIC, VIC], [Loc_Actv_Ind, A, A], [, , ]]')
	close()
