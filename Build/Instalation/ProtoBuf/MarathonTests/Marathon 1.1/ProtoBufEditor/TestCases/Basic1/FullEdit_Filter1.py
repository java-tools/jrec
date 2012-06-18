useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Protocol Buffer Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.bin')

		click('Choose File')

		if window('Open'):
			click('Open')
		close()

		#commonBits.setRecordLayout(select, 'ams Store')

		click('Edit1')
		click('Filter1')
		select('Table1', 'Loc_Type', 'Field,0')
		select('Table1', 'dc', 'Value,0')
		select('Table1', 'cell:Value,0()')
		click('Filter1')
		select('Table', 'cell:4|Loc_Name,2(NSW North Sydney Ad Support)')
		rightclick('Table', '4|Loc_Name,2')
		select('Table', 'cell:4|Loc_Name,2(NSW North Sydney Ad Support)')
		assert_p('Table', 'Content', '[[TAR, 5839, DC, DC - Taras Ave, , 30-68 Taras Ave, Altona North, 3025, VIC, A], [TAR, 5850, DC, VIC West Ad Support, , Lot 2 Little Boundary Rd, Laverton, 3028, VIC, A], [TAR, 5853, DC, NSW North Sydney Ad Support, , , , , , A], [TAR, 5866, DC, WA Ad Support, , , , , , A], [TAR, 5700, DC, Head Office, , , , , , A], [TAR, 5887, DC, QLD Ad Support, , , , , , A], [TAR, 5888, DC, SA Ad Support, , , , , , A], [TAR, 5109, DC, National Ad Support, , , , , , A], [TAR, 5895, DC, VIC East Ad Support, , , , , , A], [TAR, 5897, DC, Sydney Gate DC, No 2 Sydney Gate, 830 Bourke Street, Waterloo, 2017, NSW, A], [TAR, 5949, DC, Central Returns Centre, , 214-228 Blackshaws Rd, Altona North, , VIC, A], [TAR, 5951, DC, NSW West Sydney Ad Support, , , , , , A], [TAR, 5952, DC, TAS Ad Support, , , , , , A], [TAR, 5953, DC, North Geelong Warehouse, Target Head Office, 12 Thompson Road, North Geelong, 3215, VIC, A], [TAR, 5954, DC, State  Warehouse NSW, Target State Warehouse NSW (Westgate), Warehouse D Murtha Street, Arndell Park, 2148, NSW, A], [TAR, 5955, DC, State Warehouse VIC, Target State Warehouse VIC (Patricks), 180 Fitzgerald Road, Laverton, 3028, VIC, A], [TAR, 5957, DC, State Warehouse SA, Target State Warehouse  (Patricks), 180 Fitzgerald Road, Laverton, 3028, VIC, A], [TAR, 5958, DC, State Warehouse  WA, Target State Warehouse (WA) FCL, 56 Dowd Street, Welshpool, 6106, WA, A], [TAR, 5956, DC, State Warehouse QLD, Target State Warehouse QLD (RMS), 243 Bradman Street, Acacia Ridge, 4110, QLD, A], [TAR, 5959, DC, NSW  South Sydney Ad Support, South Sydney Ad Support, 753 Hume Highway, Bass Hill, , NSW, A], [TAR, 5960, DC, NSW East Sydney Ad Support,  Building B, Portside Distribution Ce, 2 - 8 Mc Pherson Street,, Botany, 2019, NSW, A], [TAR, 5963, DC, QLD South Ad Support, , , , , , A], [TAR, 5964, DC, QLD North Ad Support, , , , , , A], [TAR, 5965, DC, Canning Vale DC, Canning Vale DC, Cnr Nicholson & Bannister Rd\'s, Canning Vale, 6155, WA, A], [TAR, 5966, DC, Huntingwood DC, Huntingwood DC, 35 Huntingwood Drive, Huntingwood, 2148, NSW, A], [TAR, 5967, DC, Hendra DC, Hendra DC, Cnr Headly Ave & Nudgee Road, Hendra, 4011, QLD, A], [TAR, 5968, DC, Beverly DC, Beverly DC, 117 Main Street, Beverly, 5009, SA, A], [TAR, 5969, DC, Woodlands DC (DO NOT USE), Woodlands DC, Lot 9 Mills Road, Braeside, 3195, VIC, A]]')
		select('Table', 'cell:4|Loc_Name,2(NSW North Sydney Ad Support)')
		assert_p('Table', 'RowCount', '28')
		select('Table', 'cell:4|Loc_Name,2(NSW North Sydney Ad Support)')
		doubleclick('BaseDisplay$HeaderToolTips', '2|Loc_Nbr')
		select('Table', 'cell:4|Loc_Name,0(National Ad Support)')
		assert_p('Table', 'Content', '[[TAR, 5109, DC, National Ad Support, , , , , , A], [TAR, 5700, DC, Head Office, , , , , , A], [TAR, 5839, DC, DC - Taras Ave, , 30-68 Taras Ave, Altona North, 3025, VIC, A], [TAR, 5850, DC, VIC West Ad Support, , Lot 2 Little Boundary Rd, Laverton, 3028, VIC, A], [TAR, 5853, DC, NSW North Sydney Ad Support, , , , , , A], [TAR, 5866, DC, WA Ad Support, , , , , , A], [TAR, 5887, DC, QLD Ad Support, , , , , , A], [TAR, 5888, DC, SA Ad Support, , , , , , A], [TAR, 5895, DC, VIC East Ad Support, , , , , , A], [TAR, 5897, DC, Sydney Gate DC, No 2 Sydney Gate, 830 Bourke Street, Waterloo, 2017, NSW, A], [TAR, 5949, DC, Central Returns Centre, , 214-228 Blackshaws Rd, Altona North, , VIC, A], [TAR, 5951, DC, NSW West Sydney Ad Support, , , , , , A], [TAR, 5952, DC, TAS Ad Support, , , , , , A], [TAR, 5953, DC, North Geelong Warehouse, Target Head Office, 12 Thompson Road, North Geelong, 3215, VIC, A], [TAR, 5954, DC, State  Warehouse NSW, Target State Warehouse NSW (Westgate), Warehouse D Murtha Street, Arndell Park, 2148, NSW, A], [TAR, 5955, DC, State Warehouse VIC, Target State Warehouse VIC (Patricks), 180 Fitzgerald Road, Laverton, 3028, VIC, A], [TAR, 5956, DC, State Warehouse QLD, Target State Warehouse QLD (RMS), 243 Bradman Street, Acacia Ridge, 4110, QLD, A], [TAR, 5957, DC, State Warehouse SA, Target State Warehouse  (Patricks), 180 Fitzgerald Road, Laverton, 3028, VIC, A], [TAR, 5958, DC, State Warehouse  WA, Target State Warehouse (WA) FCL, 56 Dowd Street, Welshpool, 6106, WA, A], [TAR, 5959, DC, NSW  South Sydney Ad Support, South Sydney Ad Support, 753 Hume Highway, Bass Hill, , NSW, A], [TAR, 5960, DC, NSW East Sydney Ad Support,  Building B, Portside Distribution Ce, 2 - 8 Mc Pherson Street,, Botany, 2019, NSW, A], [TAR, 5963, DC, QLD South Ad Support, , , , , , A], [TAR, 5964, DC, QLD North Ad Support, , , , , , A], [TAR, 5965, DC, Canning Vale DC, Canning Vale DC, Cnr Nicholson & Bannister Rd\'s, Canning Vale, 6155, WA, A], [TAR, 5966, DC, Huntingwood DC, Huntingwood DC, 35 Huntingwood Drive, Huntingwood, 2148, NSW, A], [TAR, 5967, DC, Hendra DC, Hendra DC, Cnr Headly Ave & Nudgee Road, Hendra, 4011, QLD, A], [TAR, 5968, DC, Beverly DC, Beverly DC, 117 Main Street, Beverly, 5009, SA, A], [TAR, 5969, DC, Woodlands DC (DO NOT USE), Woodlands DC, Lot 9 Mills Road, Braeside, 3195, VIC, A]]')
		select('Table', 'cell:2|Loc_Nbr,2(5839)')
		assert_p('Table', 'Text', '5839', '2|Loc_Nbr,2')
		doubleclick('BaseDisplay$HeaderToolTips', '4|Loc_Name')
		select('Table', 'cell:4|Loc_Name,1(Canning Vale DC)')
		assert_p('Table', 'Content', '[[TAR, 5968, DC, Beverly DC, Beverly DC, 117 Main Street, Beverly, 5009, SA, A], [TAR, 5965, DC, Canning Vale DC, Canning Vale DC, Cnr Nicholson & Bannister Rd\'s, Canning Vale, 6155, WA, A], [TAR, 5949, DC, Central Returns Centre, , 214-228 Blackshaws Rd, Altona North, , VIC, A], [TAR, 5839, DC, DC - Taras Ave, , 30-68 Taras Ave, Altona North, 3025, VIC, A], [TAR, 5700, DC, Head Office, , , , , , A], [TAR, 5967, DC, Hendra DC, Hendra DC, Cnr Headly Ave & Nudgee Road, Hendra, 4011, QLD, A], [TAR, 5966, DC, Huntingwood DC, Huntingwood DC, 35 Huntingwood Drive, Huntingwood, 2148, NSW, A], [TAR, 5959, DC, NSW  South Sydney Ad Support, South Sydney Ad Support, 753 Hume Highway, Bass Hill, , NSW, A], [TAR, 5960, DC, NSW East Sydney Ad Support,  Building B, Portside Distribution Ce, 2 - 8 Mc Pherson Street,, Botany, 2019, NSW, A], [TAR, 5853, DC, NSW North Sydney Ad Support, , , , , , A], [TAR, 5951, DC, NSW West Sydney Ad Support, , , , , , A], [TAR, 5109, DC, National Ad Support, , , , , , A], [TAR, 5953, DC, North Geelong Warehouse, Target Head Office, 12 Thompson Road, North Geelong, 3215, VIC, A], [TAR, 5887, DC, QLD Ad Support, , , , , , A], [TAR, 5964, DC, QLD North Ad Support, , , , , , A], [TAR, 5963, DC, QLD South Ad Support, , , , , , A], [TAR, 5888, DC, SA Ad Support, , , , , , A], [TAR, 5954, DC, State  Warehouse NSW, Target State Warehouse NSW (Westgate), Warehouse D Murtha Street, Arndell Park, 2148, NSW, A], [TAR, 5958, DC, State Warehouse  WA, Target State Warehouse (WA) FCL, 56 Dowd Street, Welshpool, 6106, WA, A], [TAR, 5956, DC, State Warehouse QLD, Target State Warehouse QLD (RMS), 243 Bradman Street, Acacia Ridge, 4110, QLD, A], [TAR, 5957, DC, State Warehouse SA, Target State Warehouse  (Patricks), 180 Fitzgerald Road, Laverton, 3028, VIC, A], [TAR, 5955, DC, State Warehouse VIC, Target State Warehouse VIC (Patricks), 180 Fitzgerald Road, Laverton, 3028, VIC, A], [TAR, 5897, DC, Sydney Gate DC, No 2 Sydney Gate, 830 Bourke Street, Waterloo, 2017, NSW, A], [TAR, 5952, DC, TAS Ad Support, , , , , , A], [TAR, 5895, DC, VIC East Ad Support, , , , , , A], [TAR, 5850, DC, VIC West Ad Support, , Lot 2 Little Boundary Rd, Laverton, 3028, VIC, A], [TAR, 5866, DC, WA Ad Support, , , , , , A], [TAR, 5969, DC, Woodlands DC (DO NOT USE), Woodlands DC, Lot 9 Mills Road, Braeside, 3195, VIC, A]]')
		select('Table', 'cell:4|Loc_Name,1(Canning Vale DC)')
		rightclick('Table', '4|Loc_Name,1')
		select_menu('Edit Record')
	##	select('Table1', 'cell:4|Loc_Name,1(Canning Vale DC)')
		select('Table', 'cell:Data,5(Cnr Nicholson & Bannister Rd\'s)')
		assert_p('Table', 'Content', '[[Brand_Id, 1, , TAR, TAR], [Loc_Nbr, 2, , 5965, 5965], [Loc_Type, 3, , DC, DC], [Loc_Name, 4, , Canning Vale DC, Canning Vale DC], [Loc_Addr_Ln1, 5, , Canning Vale DC, Canning Vale DC], [Loc_Addr_Ln2, 6, , Cnr Nicholson & Bannister Rd\'s, Cnr Nicholson & Bannister Rd\'s], [Loc_Addr_Ln3, 7, , Canning Vale, Canning Vale], [Loc_Postcode, 8, , 6155, 6155], [Loc_State, 9, , WA, WA], [Loc_Actv_Ind, 10, , A, A]]')
		select('Table', 'cell:Data,5(Cnr Nicholson & Bannister Rd\'s)')
		click('Right')
		select('Table', 'cell:Data,5(214-228 Blackshaws Rd)')
		assert_p('Table', 'Text', '214-228 Blackshaws Rd', 'Data,5')
		select('Table', 'cell:Data,5(214-228 Blackshaws Rd)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		#click('WindowsInternalFrameTitlePane', 627, 14)
		click('BasicInternalFrameTitlePane$NoFocusButton2')

	close()
