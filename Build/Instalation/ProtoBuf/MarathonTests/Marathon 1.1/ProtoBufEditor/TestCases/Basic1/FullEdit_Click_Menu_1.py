useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228_Extract.bin')
		#commonBits.setRecordLayout(select, 'ams Store')
		click('Edit1')
		select('Table', 'rows:[1,2,3],columns:[3|Loc_Type]')
		rightclick('Table', '3|Loc_Type,2')
		select_menu('Cut Record#{s#}')
		select('Table', 'cell:3|Loc_Type,2(ST)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'cell:4|Loc_Name,6(Campbelltown)')
		rightclick('Table', '4|Loc_Name,6')
		select_menu('Paste Record#{s#}')
		select('Table', 'cell:4|Loc_Name,6(Campbelltown)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'rows:[7,8],columns:[4|Loc_Name]')
		rightclick('Table', '4|Loc_Name,8')
		select_menu('Copy Record#{s#}')
		select('Table', 'cell:4|Loc_Name,11(Ashfield)')
		rightclick('Table', '4|Loc_Name,11')
		select_menu('Paste Record#{s#} Prior')
		select('Table', 'cell:4|Loc_Name,10(Warringah Mall)')
		assert_p('Table', 'RowCount', '15')
		select('Table', 'cell:4|Loc_Name,12(Blacktown)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'rows:[9,10,11,12],columns:[4|Loc_Name]')
		rightclick('Table', '4|Loc_Name,11')
		select_menu('Delete Record#{s#}')
		select('Table', 'cell:4|Loc_Name,7(Penrith)')
		assert_p('Table', 'RowCount', '11')
		select('Table', 'cell:4|Loc_Name,8(Blacktown)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'cell:4|Loc_Name,8(Blacktown)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'Ams_LocDownload_20041228_Extract.bin'):
			click('No')
		close()
	close()
