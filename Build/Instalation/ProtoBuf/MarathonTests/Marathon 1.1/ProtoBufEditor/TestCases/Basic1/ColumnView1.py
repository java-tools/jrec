useFixture(default)

## Check
def test():
	from Modules import commonBits

	java_recorded_version = '1.6.0_03'

	if window('Protocol Buffer Editor'):
		click('Choose File')

		if window('Open'):
##			select('FilePane$3', 'ams_locdownload_20041228.bin')
			select(commonBits.selectPaneFn(), 'ams_locdownload_20041228.bin')
			click('Open')
		close()

		select('FileChooser1', commonBits.stdCopybookDir() + 'Ams_Location.protocomp')

		#commonBits.setRecordLayout(select, 'ams Store')

		click('Edit1')
##		select('Table', 'rows:[0,1,2,3,4,5],columns:[2|Loc_Nbr,3|Loc_Type]')
		select('Table', 'rows:[0,1,2,3,4,5],columns:[3|Loc_Type]')
		select_menu('View>>Column View #{Selected Records#}')
##		select('Table2', 'rows:[0,1,2,3,4,5],columns:[2|Loc_Nbr,3|Loc_Type]')
		select('Table', 'cell:Row 1,0(TAR)')
		assert_p('Table1', 'Text', '', 'Len,0')

		select('Table', 'cell:Row 1,1(5839)')
		assert_p('Table', 'Content', '[[TAR, TAR, TAR, TAR, TAR, TAR], [5839, 5850, 5853, 5866, 5015, 5019], [DC, DC, DC, DC, ST, ST], [DC - Taras Ave, VIC West Ad Support, NSW North Sydney Ad Support, WA Ad Support, Bankstown, Penrith], [, , , , Bankstown, Penrith], [30-68 Taras Ave, Lot 2 Little Boundary Rd, , , Unit 2, 39-41 Allingham Street, 58 Leland Street], [Altona North, Laverton, , , Condell Park, Penrith], [3025, 3028, , , 2200, 2750], [VIC, VIC, , , NSW, NSW], [A, A, A, A, A, A]]')
		select('Table', 'cell:Row 1,1(5839)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'rows:[0,1,2,3,4,5],columns:[2|Loc_Nbr,3|Loc_Type]')
		select('Table', 'rows:[8,11,15],columns:[2|Loc_Nbr,3|Loc_Type]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
