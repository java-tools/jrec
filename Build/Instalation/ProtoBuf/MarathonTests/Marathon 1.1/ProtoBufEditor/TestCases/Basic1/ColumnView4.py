useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.bin')
		#commonBits.setRecordLayout(select, 'ams Store')
		click('Edit1')
		select('Table', 'rows:[2,3,4,5,6],columns:[3|Loc_Type]')
		select_menu('View>>Column View #{Selected Records#}')
##		select('Table2', 'rows:[2,3,4,5,6],columns:[3|Loc_Type]')
		select('Table', 'cell:Row 1,3(NSW North Sydney Ad Support)')
		assert_p('Table', 'Text', 'DC', 'Row 1,2')
		select('Table', 'cell:Row 2,3(WA Ad Support)')
		assert_p('Table', 'Text', 'WA Ad Support', 'Row 2,3')
		select('Table', 'cell:Row 3,3(Bankstown)')
		assert_p('Table', 'Text', 'Condell Park', 'Row 3,6')
		select('Table', 'cell:Row 4,3(Penrith)')
		assert_p('Table', 'Text', 'Penrith', 'Row 4,6')
		select('Table', 'cell:Row 3,4(Bankstown)')
		assert_p('Table', 'Content', '[[TAR, TAR, TAR, TAR, TAR], [5853, 5866, 5015, 5019, 5033], [DC, DC, ST, ST, ST], [NSW North Sydney Ad Support, WA Ad Support, Bankstown, Penrith, Blacktown], [, , Bankstown, Penrith, Marayong], [, , Unit 2, 39-41 Allingham Street, 58 Leland Street, Dock 2, 11 Melissa Place], [, , Condell Park, Penrith, Marayong], [, , 2200, 2750, 2148], [, , NSW, NSW, NSW], [A, A, A, A, A]]')
		select('Table', 'cell:Row 3,4(Bankstown)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'rows:[2,3,4,5,6],columns:[3|Loc_Type]')
	close()
