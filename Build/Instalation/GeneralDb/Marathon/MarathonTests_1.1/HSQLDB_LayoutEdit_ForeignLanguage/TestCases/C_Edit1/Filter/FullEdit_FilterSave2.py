useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		click(commonBits.fl('Choose File'))

		if window('Open'):
			select(commonBits.selectPane(), 'Ams_LocDownload_20041228.txt')
			click('Open')
		close()

		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		select('Table1', 'Loc Type', commonBits.fl('Field') + ',0')
		select('Table1', 'st', commonBits.fl('Value') + ',0')
		select('Table1', 'cell:' + commonBits.fl('Field') + ',2(null)')
		commonBits.save1(click)
		##select('FileChooser', commonBits.userDir() + 'Filter'  + commonBits.fileSep() + 'xx2')
		commonBits.selectFileName(select, commonBits.userDir() + 'Filter'  + commonBits.fileSep() + 'xx2')
		commonBits.save1(click)


		select_menu(commonBits.fl('Window') + '>>Ams_LocDownload_20041228.txt>>' + commonBits.fl('Filter Options'))
		commonBits.filter(click)
		select('Table', 'cell:10 - 35|Loc Name,0(Bankstown)')
		assert_p('Table', 'Text', 'Bankstown', '10 - 35|Loc Name,0')
		select('Table', 'cell:8 - 2|Loc Type,1(ST)')
		rightclick('Table', '8 - 2|Loc Type,1')
		select_menu(commonBits.fl('Edit Record'))
##		select('Table1', 'cell:8 - 2|Loc Type,1(ST)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(58 Leland Street)')
		assert_p('Table', 'Text', 'Penrith', commonBits.fl('Data') + ',6')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(58 Leland Street)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5019, 5019], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Penrith, Penrith], [Loc Addr Ln1, 45, 40, Penrith, Penrith], [Loc Addr Ln2, 85, 40, 58 Leland Street, 58 Leland Street], [Loc Addr Ln3, 125, 35, Penrith, Penrith], [Loc Postcode, 160, 10, 2750, 2750], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(58 Leland Street)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'cell:8 - 2|Loc Type,1(ST)')
		select('Table', 'cell:8 - 2|Loc Type,1(ST)')
		select_menu(commonBits.fl('Window') + '>>Ams_LocDownload_20041228.txt>>' + commonBits.fl('Table:'))
		select('Table2', 'cell:8 - 2|Loc Type,1(ST)')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Execute Saved Filter'))
		##select('FileChooser', commonBits.userDir() + 'Filter'  + commonBits.fileSep() + 'xx2')
		commonBits.selectFileName(select, commonBits.userDir() + 'Filter'  + commonBits.fileSep() + 'xx2')
		click(commonBits.fl('Run Dialog'))
		

		select('Table1', 'cell:' + commonBits.fl('Value') + ',0(st)')
		rightclick('Table1', commonBits.fl('Value') + ',0')
		select('Table1', 'cell:' + commonBits.fl('Value') + ',0(st)')
		rightclick('Table1', commonBits.fl('Value') + ',0')
		assert_p('Table1', 'Text', 'st', commonBits.fl('Value') + ',0')

		select('Table1', 'cell:' + commonBits.fl('Value') + ',0(st)')
##		assert_p('Table1', 'Content', '[[Loc Type, true, Contains, st], [, true, Contains, ], [, true, Contains, ], [, true, Contains, ]]')
##		assert_p('Table1', 'Content', '[[, , Loc Type, true, Contains, st], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ], [, ' + commonBits.fl('And') + ', , true, Contains, ]]');

		assert_p('Table1', 'Content', '[[, , Loc Type, true, ' + commonBits.fl('Contains') + ', st], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ]]');

		assert_p('Table1', 'Content', '[[, , Loc Type, true, ' + commonBits.fl('Contains') + ', st], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ]]');


##		assert_p('Table1', 'Content', '[[, , Loc Type, true, ' + commonBits.fl('Contains') + ', st], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ]]');



		select('Table1', 'cell:' + commonBits.fl('Value') + ',0(st)')
		commonBits.filter(click)

		select('Table', 'cell:10 - 35|Loc Name,6(Leichhardt)')
		assert_p('Table', 'Text', 'Leichhardt', '10 - 35|Loc Name,6')
		select('Table', 'cell:10 - 35|Loc Name,13(Carlingford Court)')
		rightclick('Table', '10 - 35|Loc Name,13')
		select_menu(commonBits.fl('Edit Record'))
##		select('Table1', 'cell:10 - 35|Loc Name,13(Carlingford Court)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Dock 1, 11 Melissa Place)')
		assert_p('Table', 'Text', 'Dock 1, 11 Melissa Place', commonBits.fl('Data') + ',5')
		select('Table', 'cell:' + commonBits.fl('Data') + ',5(Dock 1, 11 Melissa Place)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5090, 5090], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Carlingford Court, Carlingford Court], [Loc Addr Ln1, 45, 40, , ], [Loc Addr Ln2, 85, 40, Dock 1, 11 Melissa Place, Dock 1, 11 Melissa Place], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
	close()

