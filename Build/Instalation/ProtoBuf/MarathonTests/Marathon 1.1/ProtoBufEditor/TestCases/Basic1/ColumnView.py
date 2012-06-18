useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Protocol Buffer Editor'):
		select('FileChooser',  commonBits.sampleDir() + 'Ams_LocDownload_20041228.bin')
		select('FileChooser1', commonBits.stdCopybookDir() + 'Ams_Location.protocomp')

		click('Edit1')
		select('Table', 'rows:[0,1,2,3,4,5,6,7],columns:[2|Loc_Nbr,3|Loc_Type,4|Loc_Name]')
		select_menu('View>>Column View #{Selected Records#}')
##		select('Table2', 'rows:[0,1,2,3,4,5,6,7],columns:[2|Loc_Nbr,3|Loc_Type,4|Loc_Name]')
		select('Table', 'cell:Row 1,3(DC - Taras Ave)')
		assert_p('Table', 'Text', 'DC', 'Row 1,2')
		select('Table', 'cell:Row 2,3(VIC West Ad Support)')
		assert_p('Table', 'Text', 'VIC West Ad Support', 'Row 2,3')
		select('Table', 'cell:Row 3,3(NSW North Sydney Ad Support)')
		assert_p('Table', 'Text', 'VIC West Ad Support', 'Row 2,3')
		select('Table', 'cell:Row 2,5(Lot 2 Little Boundary Rd)')
		assert_p('Table', 'Text', 'Lot 2 Little Boundary Rd', 'Row 2,5')
		select('Table', 'cell:Row 1,5(30-68 Taras Ave)')
		assert_p('Table', 'Content', '[[TAR, TAR, TAR, TAR, TAR, TAR, TAR, TAR], [5839, 5850, 5853, 5866, 5015, 5019, 5033, 5035], [DC, DC, DC, DC, ST, ST, ST, ST], [DC - Taras Ave, VIC West Ad Support, NSW North Sydney Ad Support, WA Ad Support, Bankstown, Penrith, Blacktown, Rockdale], [, , , , Bankstown, Penrith, Marayong, Building B,  Portside DC], [30-68 Taras Ave, Lot 2 Little Boundary Rd, , , Unit 2, 39-41 Allingham Street, 58 Leland Street, Dock 2, 11 Melissa Place, 2-8 Mc Pherson Street], [Altona North, Laverton, , , Condell Park, Penrith, Marayong, Botany], [3025, 3028, , , 2200, 2750, 2148, 2019], [VIC, VIC, , , NSW, NSW, NSW, NSW], [A, A, A, A, A, A, A, A]]')
		select('Table', 'cell:Row 5,5(Unit 2, 39-41 Allingham Street)')
		assert_p('Table', 'Text', '58 Leland Street', 'Row 6,5')
		select('Table', 'cell:Row 6,5(58 Leland Street)')
		assert_p('Table', 'Text', 'Penrith', 'Row 6,6')
		select('Table', 'cell:Row 6,5(58 Leland Street)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'rows:[0,1,2,3,4,5,6,7],columns:[2|Loc_Nbr,3|Loc_Type,4|Loc_Name]')
	close()
