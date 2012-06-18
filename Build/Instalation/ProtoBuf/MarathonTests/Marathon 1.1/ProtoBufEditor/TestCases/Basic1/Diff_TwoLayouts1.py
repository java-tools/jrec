useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Protocol Buffer Editor'):
		select_menu('Utilities>>Compare Menu')
		click('*2')
		click('Choose File')

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228_Extract.bin')
			click('Open')
		close()

		#commonBits.setRecordLayout(select, 'ams Store')


		click('Right')
		select('TabbedPane', '')
		click('Choose File')

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228_Extract2.bin')
			click('Open')
		close()

		#commonBits.setRecordLayout(select, 'ams Store')


		click('Right')
		select('TabbedPane', '')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		select('Table', 'cell:Loc_Name,11(Highpoint City)')
		assert_p('Table', 'Text', 'St Marys', 'Loc_Name,12')
		select('Table', 'cell:Loc_Name,14(Bass Hill)')
		assert_p('Table', 'Content', '[[, , , , , , , , , , , , ], [, Inserted, 1, TAR, 5839, DC, DC - Taras Ave, , 30-68 Taras Ave, Altona North, 3025, VIC, A], [, , , , , , , , , , , , ], [, Inserted, 2, TAR, 5850, DC, VIC West Ad Support, , Lot 2 Little Boundary Rd, Laverton, 3028, VIC, A], [, Old, 4, TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [, New, 6, , 5096, , Canberra Civic, Target Canberra, Canberra City Centre, Akuna Ave, Canberra, 2601, ACT, ], [, Old, 5, TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [, New, 7, , 5012, , Ringwood, Ringwood, Seymour Street, Ringwood, 3134, VIC, ], [, Old, 6, TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [, New, 8, , 5030, , Epping, Epping Plaza Shopping Centre, Cnr. High & Cooper Streets, Epping, 3076, VIC, ], [, Old, 7, TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [, New, 9, , 5054, , Highpoint City, Laverton, Lot 2, Cnr Lt Boundry & Old Geelong Road, Laverton, 3028, VIC, ], [, Old, 8, TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [, New, 10, , 5062, , Castletown, Townsville, Cnr. Woolcock St. & Kings Road, Townsville, 4810, QLD, ], [, Old, 9, TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [, New, 11, , 5138, , Cairns Central, Cairns, Cnr. McLeod & Aplin Streets, Cairns, 4870, QLD, ], [, Old, 10, TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [, New, 12, , 5141, , The Willows, Thuringowa Central, Cnr Thuringowa Drive &  Range Rd, Thuringowa Central, 4817, QLD, ], [, Old, 11, TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [, New, 13, , 5146, , Palmerston, Palmerston Shopping Centre, Temple Terrace, Palmerston, 830, NT, ], [, Old, 12, TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [, New, 14, , 5002, , Coffs Harbour, Coffs Harbour, Cnr. Park Beach Road & Pacific Hwy, Coffs Harbour, 2450, , ], [, Old, 13, TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [, New, 15, , 5966, DC, Huntingwood DC, Huntingwood DC, 35 Huntingwood Drive, Huntingwood, 2148, , ], [, , , , , , , , , , , , ], [, Inserted, 16, TAR, 5967, DC, Hendra DC, Hendra DC, Cnr Headly Ave & Nudgee Road, Hendra, 4011, QLD, A], [, , , , , , , , , , , , ], [, Inserted, 17, TAR, 5968, DC, Beverly DC, Beverly DC, 117 Main Street, Beverly, 5009, SA, A]]')
		select('Table', 'cell:Loc_Name,14(Bass Hill)')
		click('All Included Lines')
		select('Table', 'cell:Loc_Addr_Ln1,8(Marayong)')
		assert_p('Table', 'Content', '[[, , , , , , , , , , , , ], [, Inserted, 1, TAR, 5839, DC, DC - Taras Ave, , 30-68 Taras Ave, Altona North, 3025, VIC, A], [, , , , , , , , , , , , ], [, Inserted, 2, TAR, 5850, DC, VIC West Ad Support, , Lot 2 Little Boundary Rd, Laverton, 3028, VIC, A], [, Old, 1, TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [, New, 3, , , , , , , , , , ], [, Old, 2, TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [, New, 4, , , , , , , , , , ], [, Old, 3, TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [, New, 5, , , , , , , , , , ], [, Old, 4, TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [, New, 6, , 5096, , Canberra Civic, Target Canberra, Canberra City Centre, Akuna Ave, Canberra, 2601, ACT, ], [, Old, 5, TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [, New, 7, , 5012, , Ringwood, Ringwood, Seymour Street, Ringwood, 3134, VIC, ], [, Old, 6, TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [, New, 8, , 5030, , Epping, Epping Plaza Shopping Centre, Cnr. High & Cooper Streets, Epping, 3076, VIC, ], [, Old, 7, TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [, New, 9, , 5054, , Highpoint City, Laverton, Lot 2, Cnr Lt Boundry & Old Geelong Road, Laverton, 3028, VIC, ], [, Old, 8, TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [, New, 10, , 5062, , Castletown, Townsville, Cnr. Woolcock St. & Kings Road, Townsville, 4810, QLD, ], [, Old, 9, TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [, New, 11, , 5138, , Cairns Central, Cairns, Cnr. McLeod & Aplin Streets, Cairns, 4870, QLD, ], [, Old, 10, TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [, New, 12, , 5141, , The Willows, Thuringowa Central, Cnr Thuringowa Drive &  Range Rd, Thuringowa Central, 4817, QLD, ], [, Old, 11, TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [, New, 13, , 5146, , Palmerston, Palmerston Shopping Centre, Temple Terrace, Palmerston, 830, NT, ], [, Old, 12, TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [, New, 14, , 5002, , Coffs Harbour, Coffs Harbour, Cnr. Park Beach Road & Pacific Hwy, Coffs Harbour, 2450, , ], [, Old, 13, TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [, New, 15, , 5966, DC, Huntingwood DC, Huntingwood DC, 35 Huntingwood Drive, Huntingwood, 2148, , ], [, , , , , , , , , , , , ], [, Inserted, 16, TAR, 5967, DC, Hendra DC, Hendra DC, Cnr Headly Ave & Nudgee Road, Hendra, 4011, QLD, A], [, , , , , , , , , , , , ], [, Inserted, 17, TAR, 5968, DC, Beverly DC, Beverly DC, 117 Main Street, Beverly, 5009, SA, A]]')
		select('Table', 'cell:Loc_Addr_Ln1,8(Marayong)')
	close()
