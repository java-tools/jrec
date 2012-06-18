useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Protocol Buffer Editor'):
		select_menu('Utilities>>Compare Menu')
		click('*1')
		click('Choose File')

		if window('Open'):
			select(commonBits.selectPaneFn(), 'Ams_LocDownload_20041228_Extract.bin')
			click('Open')
		close()
		#commonBits.setRecordLayout2(select, 'ams Store')

		click('Choose File1')

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228_Extract2.bin')
			click('Open')
		close()

		click('Right')
		select('TabbedPane', '')
		select('Table1', 'cell:Include,4(true)')
		select('Table1', 'cell:Include,5(true)')
		select('Table1', 'cell:Include,6(true)')
		click('Right')
		select('TabbedPane', '')
		click('Compare')
		select('Table', 'cell:Loc_Name,6(Miranda)')
		assert_p('Table', 'Text', 'Miranda', 'Loc_Name,6')
		select('Table', 'cell:Loc_Name,6(Miranda)')
		assert_p('Table', 'Content', '[[, , , , , , , , , ], [, Inserted, 1, TAR, 5839, DC, DC - Taras Ave, 3025, VIC, A], [, , , , , , , , , ], [, Inserted, 2, TAR, 5850, DC, VIC West Ad Support, 3028, VIC, A], [, Old, 4, TAR, 5035, ST, Rockdale, 2019, NSW, A], [, New, 6, , 5096, , Canberra Civic, 2601, ACT, ], [, Old, 5, TAR, 5037, ST, Miranda, 2228, NSW, A], [, New, 7, , 5012, , Ringwood, 3134, VIC, ], [, Old, 6, TAR, 5052, ST, Eastwood, 2148, NSW, A], [, New, 8, , 5030, , Epping, 3076, VIC, ], [, Old, 7, TAR, 5055, ST, Leichhardt, 2040, NSW, A], [, New, 9, , 5054, , Highpoint City, 3028, VIC, ], [, Old, 8, TAR, 5060, ST, St Marys, 2760, NSW, A], [, New, 10, , 5062, , Castletown, 4810, QLD, ], [, Old, 9, TAR, 5070, ST, Bass Hill, 2197, NSW, A], [, New, 11, , 5138, , Cairns Central, 4870, QLD, ], [, Old, 10, TAR, 5074, ST, Campbelltown, 2560, NSW, A], [, New, 12, , 5141, , The Willows, 4817, QLD, ], [, Old, 11, TAR, 5078, ST, Warringah Mall, 2086, NSW, A], [, New, 13, , 5146, , Palmerston, 830, NT, ], [, Old, 12, TAR, 5081, ST, Ashfield, 2131, NSW, A], [, New, 14, , 5002, , Coffs Harbour, 2450, , ], [, Old, 13, TAR, 5085, ST, Roselands, 2200, NSW, A], [, New, 15, , 5966, DC, Huntingwood DC, 2148, , ], [, , , , , , , , , ], [, Inserted, 16, TAR, 5967, DC, Hendra DC, 4011, QLD, A], [, , , , , , , , , ], [, Inserted, 17, TAR, 5968, DC, Beverly DC, 5009, SA, A]]')
		select('Table', 'cell:Loc_Name,21(Coffs Harbour)')
		assert_p('Table', 'Text', 'Coffs Harbour', 'Loc_Name,21')
		select('Table', 'cell:Loc_Name,21(Coffs Harbour)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
