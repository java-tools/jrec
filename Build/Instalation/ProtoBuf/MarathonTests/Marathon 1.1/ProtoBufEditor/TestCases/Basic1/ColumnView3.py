useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.bin')
		#commonBits.setRecordLayout(select, 'ams Store')
		click('Edit1')
		select('Table', 'rows:[5,8,11,14],columns:[4|Loc_Name]')
#		rightclick('Table', '4|Loc_Name,8')
#		select('Table', 'rows:[5,8,11,14],columns:[4|Loc_Name]')
		select_menu('View>>Column View #{Selected Records#}')
#		select('Table2', 'rows:[5,8,11,14],columns:[4|Loc_Name]')
		select('Table', 'cell:Row 1,3(Penrith)')
		assert_p('Table', 'Text', '58 Leland Street', 'Row 1,5')
		select('Table', 'cell:Row 2,3(Miranda)')
		assert_p('Table', 'Text', 'NSW', 'Row 1,8')
		select('Table', 'cell:Row 3,3(St Marys)')
		assert_p('Table', 'Text', '2760', 'Row 3,7')
		select('Table', 'cell:Row 2,5(Cnr. Urunga Pde & The Kingsway)')
		assert_p('Table', 'Text', 'Miranda', 'Row 2,6')
		select('Table', 'cell:Row 3,5(Charles Hackett Drive)')
		assert_p('Table', 'Text', 'St Mary\'s', 'Row 3,6')
		select('Table', 'cell:Row 2,6(Miranda)')
		assert_p('Table', 'Content', '[[TAR, TAR, TAR, TAR], [5019, 5037, 5060, 5078], [ST, ST, ST, ST], [Penrith, Miranda, St Marys, Warringah Mall], [Penrith, Westfield Shoppingtown, St. Mary\'s, Frenchs Forest], [58 Leland Street, Cnr. Urunga Pde & The Kingsway, Charles Hackett Drive, Units 2-3, 14 Aquatic Drive], [Penrith, Miranda, St Mary\'s, Frenchs Forest], [2750, 2228, 2760, 2086], [NSW, NSW, NSW, NSW], [A, A, A, A]]')
		select('Table', 'cell:Row 2,6(Miranda)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'rows:[5,8,11,14],columns:[4|Loc_Name]')
	close()
