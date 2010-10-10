useFixture(diff)

def test():
##	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('File Compare'):
		click('*1')
		click('Choose File')

		if window('Open'):
			select('FilePane$3', 'Ams_LocDownload_20041228_Extract.txt')
			click('Open')
		close()

		click('Choose File1')

		if window('Open'):
			select('FilePane$3', 'Ams_LocDownload_20041228_Extract2.txt')
			click('Open')
		close()

		click('Right')
		select('TabbedPane', '')
		select('Table1', 'cell:Include,2(true)')
		select('Table1', 'cell:Include,5(true)')
		select('Table1', 'cell:Include,8(true)')
		select('Table1', 'cell:Field,5(Loc Addr Ln2)')
		assert_p('Table1', 'Content', '[[Brand Id, true], [Loc Nbr, true], [Loc Type, false], [Loc Name, true], [Loc Addr Ln1, true], [Loc Addr Ln2, false], [Loc Addr Ln3, true], [Loc Postcode, true], [Loc State, false], [Loc Actv Ind, true]]')
		select('Table1', 'cell:Field,5(Loc Addr Ln2)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		select('Table', 'cell:Loc Addr Ln1,8(Marayong Offsite Reserve)')
		assert_p('Table', 'Text', 'Marketown', 'Loc Addr Ln1,10')
		select('Table', 'cell:Loc Addr Ln1,14(Bass Hill Plaza)')
		assert_p('Table', 'Content', '[[, , , , , , , , , ], [, Inserted, 1, TAR, 5839, DC - Taras Ave, , Altona North, 3025, A], [, , , , , , , , , ], [, Inserted, 2, TAR, 5850, VIC West Ad Support, , Laverton, 3028, A], [, Old, 4, TAR, 5035, Rockdale, Building B,  Portside DC, Botany, 2019, A], [, New, 6, , 5096, Canberra Civic, Target Canberra, Canberra, 2601, ], [, Old, 5, TAR, 5037, Miranda, Westfield Shoppingtown, Miranda, 2228, A], [, New, 7, , 5012, Ringwood, Ringwood, Ringwood, 3134, ], [, Old, 6, TAR, 5052, Eastwood, Marayong Offsite Reserve, Marayong, 2148, A], [, New, 8, , 5030, Epping, Epping Plaza Shopping Centre, Epping, 3076, ], [, Old, 7, TAR, 5055, Leichhardt, Marketown, Leichhardt, 2040, A], [, New, 9, , 5054, Highpoint City, Laverton, Laverton, 3028, ], [, Old, 8, TAR, 5060, St Marys, St. Mary\'s, St Mary\'s, 2760, A], [, New, 10, , 5062, Castletown, Townsville, Townsville, 4810, ], [, Old, 9, TAR, 5070, Bass Hill, Bass Hill Plaza, Bass Hill, 2197, A], [, New, 11, , 5138, Cairns Central, Cairns, Cairns, 4870, ], [, Old, 10, TAR, 5074, Campbelltown, Campbelltown Mall, Campbelltown, 2560, A], [, New, 12, , 5141, The Willows, Thuringowa Central, Thuringowa Central, 4817, ], [, Old, 11, TAR, 5078, Warringah Mall, Frenchs Forest, Frenchs Forest, 2086, A], [, New, 13, , 5146, Palmerston, Palmerston Shopping Centre, Palmerston, 0830, ], [, Old, 12, TAR, 5081, Ashfield, Ashfield Mall, Ashfield, 2131, A], [, New, 14, , 5002, Coffs Harbour, Coffs Harbour, Coffs Harbour, 2450, ], [, Old, 13, TAR, 5085, Roselands, Condell park, Condell Park, 2200, A], [, New, 15, , 5966, Huntingwood DC, Huntingwood DC, Huntingwood, 2148, ], [, , , , , , , , , ], [, Inserted, 16, TAR, 5967, Hendra DC, Hendra DC, Hendra, 4011, A], [, , , , , , , , , ], [, Inserted, 17, TAR, 5968, Beverly DC, Beverly DC, Beverly, 5009, A]]')
		select('Table', 'cell:Loc Postcode,15(4870)')
		assert_p('Table', 'Text', '4817', 'Loc Postcode,17')
		select('Table', 'cell:Loc Postcode,15(4870)')
		click('All Included Lines')
		select('Table', 'cell:Loc Addr Ln3,8(Marayong)')
		assert_p('Table', 'Text', '', 'Loc Addr Ln3,9')
		select('Table', 'cell:Loc Addr Ln3,6(Penrith)')
		assert_p('Table', 'RowCount', '34')
		select('Table', 'cell:Loc Addr Ln1,6(Penrith)')
		assert_p('Table', 'Content', '[[, , , , , , , , , ], [, Inserted, 1, TAR, 5839, DC - Taras Ave, , Altona North, 3025, A], [, , , , , , , , , ], [, Inserted, 2, TAR, 5850, VIC West Ad Support, , Laverton, 3028, A], [, Old, 1, TAR, 5015, Bankstown, Bankstown, Condell Park, 2200, A], [, New, 3, , , , , , , ], [, Old, 2, TAR, 5019, Penrith, Penrith, Penrith, 2750, A], [, New, 4, , , , , , , ], [, Old, 3, TAR, 5033, Blacktown, Marayong, Marayong, 2148, A], [, New, 5, , , , , , , ], [, Old, 4, TAR, 5035, Rockdale, Building B,  Portside DC, Botany, 2019, A], [, New, 6, , 5096, Canberra Civic, Target Canberra, Canberra, 2601, ], [, Old, 5, TAR, 5037, Miranda, Westfield Shoppingtown, Miranda, 2228, A], [, New, 7, , 5012, Ringwood, Ringwood, Ringwood, 3134, ], [, Old, 6, TAR, 5052, Eastwood, Marayong Offsite Reserve, Marayong, 2148, A], [, New, 8, , 5030, Epping, Epping Plaza Shopping Centre, Epping, 3076, ], [, Old, 7, TAR, 5055, Leichhardt, Marketown, Leichhardt, 2040, A], [, New, 9, , 5054, Highpoint City, Laverton, Laverton, 3028, ], [, Old, 8, TAR, 5060, St Marys, St. Mary\'s, St Mary\'s, 2760, A], [, New, 10, , 5062, Castletown, Townsville, Townsville, 4810, ], [, Old, 9, TAR, 5070, Bass Hill, Bass Hill Plaza, Bass Hill, 2197, A], [, New, 11, , 5138, Cairns Central, Cairns, Cairns, 4870, ], [, Old, 10, TAR, 5074, Campbelltown, Campbelltown Mall, Campbelltown, 2560, A], [, New, 12, , 5141, The Willows, Thuringowa Central, Thuringowa Central, 4817, ], [, Old, 11, TAR, 5078, Warringah Mall, Frenchs Forest, Frenchs Forest, 2086, A], [, New, 13, , 5146, Palmerston, Palmerston Shopping Centre, Palmerston, 0830, ], [, Old, 12, TAR, 5081, Ashfield, Ashfield Mall, Ashfield, 2131, A], [, New, 14, , 5002, Coffs Harbour, Coffs Harbour, Coffs Harbour, 2450, ], [, Old, 13, TAR, 5085, Roselands, Condell park, Condell Park, 2200, A], [, New, 15, , 5966, Huntingwood DC, Huntingwood DC, Huntingwood, 2148, ], [, , , , , , , , , ], [, Inserted, 16, TAR, 5967, Hendra DC, Hendra DC, Hendra, 4011, A], [, , , , , , , , , ], [, Inserted, 17, TAR, 5968, Beverly DC, Beverly DC, Beverly, 5009, A]]')
	close()
