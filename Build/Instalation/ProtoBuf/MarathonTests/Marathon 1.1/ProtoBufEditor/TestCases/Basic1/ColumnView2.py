useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.bin')
		#commonBits.setRecordLayout(select, 'ams Store')
		click('Edit1')
##		select('Table', 'rows:[2,3,4,5,6,7],columns:[2|Loc_Nbr,3|Loc_Type]')
		select('Table', 'rows:[2,3,4,5,6,7],columns:[3|Loc_Type]')
		select_menu('View>>Column View #{Selected Records#}')
#		select('Table2', 'rows:[2,3,4,5,6,7],columns:[2|Loc_Nbr,3|Loc_Type]')
		select('Table', 'cell:Row 1,3(NSW North Sydney Ad Support)')
		assert_p('Table', 'Text', 'NSW North Sydney Ad Support', 'Row 1,3')
		select('Table', 'cell:Row 1,5()')
		assert_p('Table', 'Content', '[[TAR, TAR, TAR, TAR, TAR, TAR], [5853, 5866, 5015, 5019, 5033, 5035], [DC, DC, ST, ST, ST, ST], [NSW North Sydney Ad Support, WA Ad Support, Bankstown, Penrith, Blacktown, Rockdale], [, , Bankstown, Penrith, Marayong, Building B,  Portside DC], [, , Unit 2, 39-41 Allingham Street, 58 Leland Street, Dock 2, 11 Melissa Place, 2-8 Mc Pherson Street], [, , Condell Park, Penrith, Marayong, Botany], [, , 2200, 2750, 2148, 2019], [, , NSW, NSW, NSW, NSW], [A, A, A, A, A, A]]')
		select('Table', 'cell:Row 1,5()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select('Table', 'rows:[2,3,4,5,6,7],columns:[2|Loc_Nbr,3|Loc_Type]')
#		select('Table', 'rows:[2,3,4,5,6,7],columns:[2|Loc_Nbr,3|Loc_Type]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
