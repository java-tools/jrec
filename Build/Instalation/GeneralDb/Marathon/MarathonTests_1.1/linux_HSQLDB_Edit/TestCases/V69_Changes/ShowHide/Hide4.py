useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() +  'Ams_LocDownload_20041228.txt')
		click('Edit1')
		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
		rightclick('Table', '1 - 3|Brand Id,9')
##		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
		select_menu('Edit Record')
##		select('Table1', 'cell:4 - 4|Loc Nbr,0(5839)')
##		select('Table', '')
		rightclick('Table', 'Data,0')
		select_menu('Hide Column')
##		select('Table', '')
		rightclick('Table', 'Data,1')
		select_menu('Hide Column')
##		select('Table', '')
		rightclick('Table', 'Data,3')
		select_menu('Hide Column')
##		select('Table', '')
		rightclick('Table', 'Data,6')
		select_menu('Hide Column')
		select('Table', 'cell:Data,3(Marayong)')
		assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5052, 5052], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW]]')
		select('Table', 'cell:Data,3(Marayong)')
		select_menu('Edit>>Show / Hide Fields')
##		select('Table1', 'cell:Data,3(Marayong)')
##		assert_p('Table', 'Content', '[[Brand Id, false], [Loc Nbr, true], [Loc Type, false], [Loc Name, true], [Loc Addr Ln1, true], [Loc Addr Ln2, false], [Loc Addr Ln3, true], [Loc Postcode, true], [Loc State, true], [Loc Actv Ind, true]]')
		assert_p('Table', 'Content', '[[Brand Id, false], [Loc Nbr, true], [Loc Type, false], [Loc Name, true], [Loc Addr Ln1, true], [Loc Addr Ln2, false], [Loc Addr Ln3, true], [Loc Postcode, true], [Loc State, true], [Loc Actv Ind, false]]')

		
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'cell:Data,3(Marayong)')
		select('Table', 'cell:Data,3(Marayong)')
		select_menu('Window>>Ams_LocDownload_20041228.txt>>Record: ')
		select('Table', 'cell:Data,3(Marayong)')
		rightclick('Table', 'Data,4')
		select_menu('Show ams Store Fields>>Loc Actv Ind')
		assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5052, 5052], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
##		select('Table', '')
		rightclick('Table', 'Data,4')
		select_menu('Show ams Store Fields>>Loc Addr Ln2')
		select('Table', 'cell:Data,5(2148)')
		assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5052, 5052], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln2, 85, 40, 11 Melissa Place, 11 Melissa Place], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,5(2148)')
		rightclick('Table', 'Data,6')
		select_menu('Show ams Store Fields>>Loc Type')
		select('Table', 'cell:Data,5(Marayong)')
		assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5052, 5052], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln2, 85, 40, 11 Melissa Place, 11 Melissa Place], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,5(Marayong)')
		select_menu('Edit>>Show / Hide Fields')
##		select('Table1', 'cell:Data,5(Marayong)')
		assert_p('Table', 'Content', '[[Brand Id, false], [Loc Nbr, true], [Loc Type, true], [Loc Name, true], [Loc Addr Ln1, true], [Loc Addr Ln2, true], [Loc Addr Ln3, true], [Loc Postcode, true], [Loc State, true], [Loc Actv Ind, true]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'cell:Data,5(Marayong)')
#		select('Table', 'cell:Data,5(Marayong)')
		select_menu('Window>>Ams_LocDownload_20041228.txt>>Record: ')
		select('Table', 'cell:Data,5(Marayong)')
		rightclick('Table', 'Data,6')
		select_menu('Show ams Store Fields>>Brand Id')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5052, 5052], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln2, 85, 40, 11 Melissa Place, 11 Melissa Place], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
	close()
