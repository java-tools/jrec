useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'zzAms_LocDownload_tab.csv')
		commonBits.setRecordLayout(select, 'Generic CSV - enter details')
		commonBits.doEdit(click)


		if window(''):


			select('CheckBox', 'false')

			click('Go')




		close()


		select('Table', 'cell:4|D,0(Loc_Name)')
		assert_p('Table', 'Text', 'WA Ad Support', '4|D,4')
		select('Table', 'cell:6|F,0(Loc_Addr_Ln2)')
		assert_p('Table', 'Text', 'Loc_Addr_Ln2', '6|F,0')
		select('Table', 'cell:6|F,9(Cnr. Urunga Pde & The Kingsway)')
		assert_p('Table', 'Text', 'Cnr. Urunga Pde & The Kingsway', '6|F,9')
		select('Table', 'cell:4|D,6(Penrith)')
		assert_p('Table', 'Text', 'Penrith', '4|D,6')
		select('Table', 'rows:[0,1,2],columns:[4|D]')
		select_menu('View>>Column View #{Selected Records#}')
###		select('Table2', 'rows:[0,1,2],columns:[1|A,2|B]')
		select('Table', 'cell:Row 1,2(Loc_Type)')
		assert_p('Table', 'Text', 'Altona North', 'Row 2,6')
		select('Table', 'cell:Row 1,6(Loc_Addr_Ln3)')
		assert_p('Table', 'Text', 'Loc_Postcode', 'Row 1,7')
		select('Table', 'cell:Row 2,5(30-68 Taras Ave)')
		assert_p('Table', 'Text', '30-68 Taras Ave', 'Row 2,5')
		select('Table', 'cell:Row 2,5(30-68 Taras Ave)')
		assert_p('Table', 'Content', '[[Brand_Id, TAR, TAR], [Loc_Nbr, 5839, 5850], [Loc_Type, DC, DC], [Loc_Name, DC - Taras Ave, VIC West Ad Support], [Loc_Addr_Ln1, , ], [Loc_Addr_Ln2, 30-68 Taras Ave, Lot 2 Little Boundary Rd], [Loc_Addr_Ln3, Altona North, Laverton], [Loc_Postcode, 3025, 3028], [Loc_State, VIC, VIC], [Loc_Actv_Ind, A, A], [, , ]]')
		select('Table', 'cell:Row 1,7(Loc_Postcode)')
		assert_p('Table', 'RowCount', '11')
		select('Table', 'cell:Row 1,7(Loc_Postcode)')
		assert_p('Table', 'ColumnCount', '3')
		select('Table', 'cell:Row 1,3(Loc_Name)')
		select('Table1', 'cell:Field,3(D)')
		assert_p('Table1', 'Text', 'D', 'Field,3')
		select('Table1', 'cell:Field,3(D)')
		assert_p('Table1', 'Content', '[[A, 1, ], [B, 2, ], [C, 3, ], [D, 4, ], [E, 5, ], [F, 6, ], [G, 7, ], [H, 8, ], [I, 9, ], [J, 10, ], [K, 11, ]]')
	close()
