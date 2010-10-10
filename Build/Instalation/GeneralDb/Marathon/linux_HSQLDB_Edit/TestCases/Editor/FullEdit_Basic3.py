useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		click('Choose File')

		if window('Open'):
			click('Open')
		close()

		commonBits.setRecordLayout(select, 'ams Store')

		click('Edit1')
		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
		rightclick('Table', '10 - 35|Loc Name,0')
		select_menu('Edit Record')
		select('Table', 'cell:Data,3(DC - Taras Ave)')
		assert_p('Table', 'Text', 'DC - Taras Ave', 'Data,3')
		select('Table', 'cell:Data,3(DC - Taras Ave)')
		select('Table', 'cell:Data,5(30-68 Taras Ave)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5839, 5839], [Loc Type, 8, 2, DC, DC], [Loc Name, 10, 35, DC - Taras Ave, DC - Taras Ave], [Loc Addr Ln1, 45, 40, , ], [Loc Addr Ln2, 85, 40, 30-68 Taras Ave, 30-68 Taras Ave], [Loc Addr Ln3, 125, 35, Altona North, Altona North], [Loc Postcode, 160, 10, 3025, 3025], [Loc State, 170, 3, VIC, VIC], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,5(30-68 Taras Ave)')
		click('Right')
		select('Table', 'cell:Data,3(VIC West Ad Support)')
		assert_p('Table', 'Text', 'VIC West Ad Support', 'Data,3')
		select('Table', 'cell:Data,3(VIC West Ad Support)')
		click('RightM')
		select('Table', 'cell:Data,3(Kalgoorlie (not yet open))')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5193, 5193], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Kalgoorlie (not yet open), Kalgoorlie (not yet open)], [Loc Addr Ln1, 45, 40, Shopping Centre, Shopping Centre], [Loc Addr Ln2, 85, 40, Cnr Cassidy and Egan Streets, Cnr Cassidy and Egan Streets], [Loc Addr Ln3, 125, 35, Kalgoorlie, Kalgoorlie], [Loc Postcode, 160, 10, 6430, 6430], [Loc State, 170, 3, WA, WA], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,3(Kalgoorlie (not yet open))')
		click('LeftM')
		select('Table', 'cell:Data,3(DC - Taras Ave)')
		assert_p('Table', 'Text', 'DC - Taras Ave', 'Data,3')
		select('Table', 'cell:Data,3(DC - Taras Ave)')
		click('Right')
		click('Right')
		click('Right')
		click('Right')
		select('Table', 'cell:Data,3(Bankstown)')
		assert_p('Table', 'Text', 'Bankstown', 'Data,3')
		select('Table', 'cell:Data,3(Bankstown)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
