useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		from Modules import commonBits

		click('Choose File')

		if window('Open'):
			select(commonBits.selectPane(), 'utf8a_Ams_LocDownload_20041228.txt')
			click('Open')
		close()

		commonBits.setRecordLayout(select, 'utf8_ams Store')

		click('Edit1')

		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
		rightclick('Table', '4 - 4|Loc Nbr,4')
		select_menu('Edit Record')
		select('Table', 'cell:Data,3(Bankstown)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5015, 5015], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Bankstown, Bankstown], [Loc Addr Ln1, 45, 40, Bankstown, Bankstown], [Loc Addr Ln2, 85, 40, Unit 2, 39-41 Allingham Street, Unit 2, 39-41 Allingham Street], [Loc Addr Ln3, 125, 35, Condell Park, Condell Park], [Loc Postcode, 160, 10, 2200, 2200], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5015, 5015], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Bankstown, Bankstown], [Loc Addr Ln1, 45, 40, Bankstown, Bankstown], [Loc Addr Ln2, 85, 40, Unit 2, 39-41 Allingham Street, Unit 2, 39-41 Allingham Street], [Loc Addr Ln3, 125, 35, Condell Park, Condell Park], [Loc Postcode, 160, 10, 2200, 2200], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,3(Bankstown)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Choose File')

		if window('Open'):
			select(commonBits.selectPane(), 'utf8a_Ams_PODownload_20041231.txt')
			click('Open')
		close()

		commonBits.setRecordLayout(select, 'utf8_ams PO Download')
		commonBits.doEdit(click)

		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Choose File')

		if window('Open'):
			select(commonBits.selectPane(), 'utf8a_Ams_LocDownload_20041228.txt')
			click('Open')
		close()

		##assert_p('ComboBox2', 'Text', 'ams Store')
		click('Edit1')
		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
		rightclick('Table', '10 - 35|Loc Name,5')
		select_menu('Edit Record')
		select('Table', 'cell:Data,4(Penrith)')
		assert_p('Table', 'Text', 'Penrith', 'Data,4')
		select('Table', 'cell:Data,4(Penrith)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
