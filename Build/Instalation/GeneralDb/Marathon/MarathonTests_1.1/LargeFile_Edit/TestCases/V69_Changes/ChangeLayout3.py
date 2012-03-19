useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Unknown_Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'Unknown Text IO')

		click('Edit1')
##		click('Find1')
##		select('TextField', 'Cnr')
##commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Table: ')
		click('Filter')
		select('Table1', 'Data', 'Field,0')
		select('Table1', 'Cnr', 'Value,0')
		select('Table1', 'cell:Value,0()')
		click('Filter1')
		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Table: ')
		select_menu('Edit>>Change Layout')
		select('ComboBox2', 'ams Store')
##		click('ScrollPane$ScrollBar', 5, 22)
		click('Go')
##		select('Table', '')
		rightclick('Table', '10 - 35|Loc Name,7')
		select_menu('Edit Record')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5035, 5035], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Rockdale, Rockdale], [Loc Addr Ln1, 45, 40, Building B,  Portside DC, Building B,  Portside DC], [Loc Addr Ln2, 85, 40, 2-8 Mc Pherson Street, 2-8 Mc Pherson Street], [Loc Addr Ln3, 125, 35, Botany, Botany], [Loc Postcode, 160, 10, 2019, 2019], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Table: 1')
##		select('Table', '')
		rightclick('Table', '10 - 35|Loc Name,7')
		select_menu('Edit Record')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5163, 5163], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Queanbeyan, Queanbeyan], [Loc Addr Ln1, 45, 40, Riverside Plaza Shopping Centre, Riverside Plaza Shopping Centre], [Loc Addr Ln2, 85, 40, Cnr Monaro St & Fallick Lane, Cnr Monaro St & Fallick Lane], [Loc Addr Ln3, 125, 35, Queanbeyan, Queanbeyan], [Loc Postcode, 160, 10, 2620, 2620], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
	close()
