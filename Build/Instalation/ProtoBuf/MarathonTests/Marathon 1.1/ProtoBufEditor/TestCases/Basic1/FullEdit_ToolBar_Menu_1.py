useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228_Extract.bin')
		#commonBits.setRecordLayout(select, 'ams Store')
		click('Edit1')
		select('Table', 'rows:[1,2,3],columns:[4|Loc_Name]')
		click('Cut1')
		select('Table', 'cell:4|Loc_Name,1(Miranda)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'cell:4|Loc_Name,5(Bass Hill)')
		click('PasteUp')
		select('Table', 'cell:5|Loc_Addr_Ln1,4(St. Mary\'s)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'rows:[6,7,8],columns:[5|Loc_Addr_Ln1]')
		click('Delete1')
		select('Table', 'cell:5|Loc_Addr_Ln1,5(Penrith)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'rows:[1,2,3],columns:[5|Loc_Addr_Ln1]')
		click('Paste1')
		select('Table', 'cell:5|Loc_Addr_Ln1,10(Frenchs Forest)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'rows:[3,4,5],columns:[5|Loc_Addr_Ln1]')
		click('Delete1')
		select('Table', 'cell:5|Loc_Addr_Ln1,3(Marketown)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'rows:[4,5,6],columns:[4|Loc_Name]')
		click('Copy1')
		select('Table', 'cell:5|Loc_Addr_Ln1,8(Ashfield Mall)')
		click('PasteUp')
		select('Table', 'cell:5|Loc_Addr_Ln1,4(St. Mary\'s)')
		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A]]')
		select('Table', 'cell:5|Loc_Addr_Ln1,4(St. Mary\'s)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

		if window('Save Changes to file: ' + commonBits.sampleDir() + 'Ams_LocDownload_20041228_Extract.bin'):
			click('No')
		close()
	close()
