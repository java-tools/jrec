useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'utf8a_Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'utf8_ams Store')
		click('Edit1')
		select('Table', 'rows:[2,4,8,11,14],columns:[10 - 35|Loc Name]')
		select_menu('View>>Record View #{Selected Records#}')
##		select('Table1', 'rows:[2,4,8,11,14],columns:[10 - 35|Loc Name]')
		select('Table', 'cell:Data,1(5853)')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5853, 5853], [Loc Type, 8, 2, DC, DC], [Loc Name, 10, 35, NSW North Sydney Ad Support, NSW North Sydney Ad Support], [Loc Addr Ln1, 45, 40, , ], [Loc Addr Ln2, 85, 40, , ], [Loc Addr Ln3, 125, 35, , ], [Loc Postcode, 160, 10, , ], [Loc State, 170, 3, , ]]')
		else:
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5853, 5853], [Loc Type, 8, 2, DC, DC], [Loc Name, 10, 35, NSW North Sydney Ad Support, NSW North Sydney Ad Support], [Loc Addr Ln1, 45, 40, , ], [Loc Addr Ln2, 85, 40, , ], [Loc Addr Ln3, 125, 35, , ], [Loc Postcode, 160, 10, , ], [Loc State, 170, 3, , ], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,3(NSW North Sydney Ad Support)')
		assert_p('Table', 'Text', 'cell:Data,3(NSW North Sydney Ad Support)')
		select('Table', 'cell:Data,3(NSW North Sydney Ad Support)')
		click('Right')
		select('Table', 'cell:Data,4(Bankstown)')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5015, 5015], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Bankstown, Bankstown], [Loc Addr Ln1, 45, 40, Bankstown, Bankstown], [Loc Addr Ln2, 85, 40, Unit 2, 39-41 Allingham Street, Unit 2, 39-41 Allingham Street], [Loc Addr Ln3, 125, 35, Condell Park, Condell Park], [Loc Postcode, 160, 10, 2200, 2200], [Loc State, 170, 3, NSW, NSW]]')
		else:
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5015, 5015], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Bankstown, Bankstown], [Loc Addr Ln1, 45, 40, Bankstown, Bankstown], [Loc Addr Ln2, 85, 40, Unit 2, 39-41 Allingham Street, Unit 2, 39-41 Allingham Street], [Loc Addr Ln3, 125, 35, Condell Park, Condell Park], [Loc Postcode, 160, 10, 2200, 2200], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,5(Unit 2, 39-41 Allingham Street)')
		assert_p('Table', 'Text', 'cell:Data,5(Unit 2, 39-41 Allingham Street)')
		select('Table', 'cell:Data,5(Unit 2, 39-41 Allingham Street)')
		click('Right')
		select('Table', 'cell:Data,5(Cnr. Urunga Pde & The Kingsway)')
		assert_p('Table', 'Text', 'Cnr. Urunga Pde & The Kingsway', 'Data,5')
		select('Table', 'cell:Data,4(Westfield Shoppingtown)')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5037, 5037], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Miranda, Miranda], [Loc Addr Ln1, 45, 40, Westfield Shoppingtown, Westfield Shoppingtown], [Loc Addr Ln2, 85, 40, Cnr. Urunga Pde & The Kingsway, Cnr. Urunga Pde & The Kingsway], [Loc Addr Ln3, 125, 35, Miranda, Miranda], [Loc Postcode, 160, 10, 2228, 2228], [Loc State, 170, 3, NSW, NSW]]')
		else:
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5037, 5037], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Miranda, Miranda], [Loc Addr Ln1, 45, 40, Westfield Shoppingtown, Westfield Shoppingtown], [Loc Addr Ln2, 85, 40, Cnr. Urunga Pde & The Kingsway, Cnr. Urunga Pde & The Kingsway], [Loc Addr Ln3, 125, 35, Miranda, Miranda], [Loc Postcode, 160, 10, 2228, 2228], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,4(Westfield Shoppingtown)')
		click('Right')
		select('Table', 'cell:Data,5(Charles Hackett Drive)')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5060, 5060], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, St Marys, St Marys], [Loc Addr Ln1, 45, 40, St. Mary\'s, St. Mary\'s], [Loc Addr Ln2, 85, 40, Charles Hackett Drive, Charles Hackett Drive], [Loc Addr Ln3, 125, 35, St Mary\'s, St Mary\'s], [Loc Postcode, 160, 10, 2760, 2760], [Loc State, 170, 3, NSW, NSW]]')
		else:
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5060, 5060], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, St Marys, St Marys], [Loc Addr Ln1, 45, 40, St. Mary\'s, St. Mary\'s], [Loc Addr Ln2, 85, 40, Charles Hackett Drive, Charles Hackett Drive], [Loc Addr Ln3, 125, 35, St Mary\'s, St Mary\'s], [Loc Postcode, 160, 10, 2760, 2760], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,5(Charles Hackett Drive)')
		click('Right')
		select('Table', 'cell:Data,5(Units 2-3, 14 Aquatic Drive)')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5078, 5078], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Warringah Mall, Warringah Mall], [Loc Addr Ln1, 45, 40, Frenchs Forest, Frenchs Forest], [Loc Addr Ln2, 85, 40, Units 2-3, 14 Aquatic Drive, Units 2-3, 14 Aquatic Drive], [Loc Addr Ln3, 125, 35, Frenchs Forest, Frenchs Forest], [Loc Postcode, 160, 10, 2086, 2086], [Loc State, 170, 3, NSW, NSW]]')
		else:
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5078, 5078], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Warringah Mall, Warringah Mall], [Loc Addr Ln1, 45, 40, Frenchs Forest, Frenchs Forest], [Loc Addr Ln2, 85, 40, Units 2-3, 14 Aquatic Drive, Units 2-3, 14 Aquatic Drive], [Loc Addr Ln3, 125, 35, Frenchs Forest, Frenchs Forest], [Loc Postcode, 160, 10, 2086, 2086], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,5(Units 2-3, 14 Aquatic Drive)')
		click('LeftM')
		select('Table', 'cell:Data,1(5853)')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5853, 5853], [Loc Type, 8, 2, DC, DC], [Loc Name, 10, 35, NSW North Sydney Ad Support, NSW North Sydney Ad Support], [Loc Addr Ln1, 45, 40, , ], [Loc Addr Ln2, 85, 40, , ], [Loc Addr Ln3, 125, 35, , ], [Loc Postcode, 160, 10, , ], [Loc State, 170, 3, , ]]')
		else:
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5853, 5853], [Loc Type, 8, 2, DC, DC], [Loc Name, 10, 35, NSW North Sydney Ad Support, NSW North Sydney Ad Support], [Loc Addr Ln1, 45, 40, , ], [Loc Addr Ln2, 85, 40, , ], [Loc Addr Ln3, 125, 35, , ], [Loc Postcode, 160, 10, , ], [Loc State, 170, 3, , ], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,1(5853)')
		click('RightM')
		select('Table', 'cell:Data,4(Frenchs Forest)')
		if commonBits.isMissingCol():
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5078, 5078], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Warringah Mall, Warringah Mall], [Loc Addr Ln1, 45, 40, Frenchs Forest, Frenchs Forest], [Loc Addr Ln2, 85, 40, Units 2-3, 14 Aquatic Drive, Units 2-3, 14 Aquatic Drive], [Loc Addr Ln3, 125, 35, Frenchs Forest, Frenchs Forest], [Loc Postcode, 160, 10, 2086, 2086], [Loc State, 170, 3, NSW, NSW]]')
		else:
			assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5078, 5078], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Warringah Mall, Warringah Mall], [Loc Addr Ln1, 45, 40, Frenchs Forest, Frenchs Forest], [Loc Addr Ln2, 85, 40, Units 2-3, 14 Aquatic Drive, Units 2-3, 14 Aquatic Drive], [Loc Addr Ln3, 125, 35, Frenchs Forest, Frenchs Forest], [Loc Postcode, 160, 10, 2086, 2086], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
	close()
