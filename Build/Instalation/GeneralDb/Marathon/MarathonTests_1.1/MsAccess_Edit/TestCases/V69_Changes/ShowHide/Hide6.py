useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() +  'Ams_LocDownload_20041228.txt')

		click('Edit1')
		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
		rightclick('Table', '4 - 4|Loc Nbr,9')
##		select('Table', 'cell:4 - 4|Loc Nbr,0(5839)')
		select_menu('Edit Record')
##		select('Table1', 'cell:4 - 4|Loc Nbr,0(5839)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5052, 5052], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln2, 85, 40, 11 Melissa Place, 11 Melissa Place], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select_menu('Edit>>Show / Hide Fields')
		assert_p('Table', 'Content', '[[Brand Id, true], [Loc Nbr, true], [Loc Type, true], [Loc Name, true], [Loc Addr Ln1, true], [Loc Addr Ln2, true], [Loc Addr Ln3, true], [Loc Postcode, true], [Loc State, true], [Loc Actv Ind, true]]')
		select('Table', 'cell:Show,0(true)')
		select('Table', 'cell:Show,2(true)')
		select('Table', 'cell:Show,5(true)')
		select('Table', 'cell:Show,7(true)')
		select('Table', 'cell:Show,8(true)')
		select('Table', 'cell:Show,9(true)')
		assert_p('Table', 'Content', '[[Brand Id, false], [Loc Nbr, true], [Loc Type, false], [Loc Name, true], [Loc Addr Ln1, true], [Loc Addr Ln2, false], [Loc Addr Ln3, true], [Loc Postcode, false], [Loc State, false], [Loc Actv Ind, false]]')
		click('Go')
		assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5052, 5052], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln3, 125, 35, Marayong, Marayong]]')
##		select('Table', '')
		rightclick('Table', 'Data,2')
		select_menu('Show ams Store Fields>>Loc Actv Ind')
		assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5052, 5052], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Actv Ind, 173, 1, A, A]]')
##		select('Table', '')
		rightclick('Table', 'Data,3')
		select_menu('Show ams Store Fields>>Loc State')
		assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5052, 5052], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
##		select('Table', '')
		rightclick('Table', 'Data,2')
		select_menu('Hide Column')
		assert_p('Table', 'Content', '[[Loc Nbr, 4, 4, 5052, 5052], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select_menu('Edit>>Show / Hide Fields')

##		assert_p('Table', 'Content', '[[Brand Id, true], [Loc Nbr, true], [Loc Type, false], [Loc Name, true], [Loc Addr Ln1, false], [Loc Addr Ln2, false], [Loc Addr Ln3, true], [Loc Postcode, false], [Loc State, true], [Loc Actv Ind, true]]')
		assert_p('Table', 'Content', '[[Brand Id, false], [Loc Nbr, true], [Loc Type, false], [Loc Name, true], [Loc Addr Ln1, false], [Loc Addr Ln2, false], [Loc Addr Ln3, true], [Loc Postcode, false], [Loc State, true], [Loc Actv Ind, true]]')
		select('Table', 'cell:Show,7(false)')
		select('Table', 'cell:Show,4(false)')
		assert_p('Table', 'Content', '[[Brand Id, false], [Loc Nbr, true], [Loc Type, false], [Loc Name, true], [Loc Addr Ln1, true], [Loc Addr Ln2, false], [Loc Addr Ln3, true], [Loc Postcode, true], [Loc State, true], [Loc Actv Ind, true]]')
		select('Table', 'cell:Show,0(true)')

		click('Go')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5052, 5052], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')

##		select('Table', '')
		rightclick('Table', 'Data,5')
		select_menu('Show ams Store Fields>>Loc Addr Ln2')
##		click('MetalInternalFrameTitlePane', 277, 18)
##		select('Table', '')
		rightclick('Table', 'Data,6')
		select_menu('Hide Column')
		select_menu('Edit>>Show / Hide Fields')
		assert_p('Table', 'Content', '[[Brand Id, true], [Loc Nbr, true], [Loc Type, false], [Loc Name, true], [Loc Addr Ln1, true], [Loc Addr Ln2, true], [Loc Addr Ln3, true], [Loc Postcode, false], [Loc State, true], [Loc Actv Ind, true]]')
		select('Table', 'cell:Show,7(false)')
		select('Table', 'cell:Show,2(false)')
		click('Go')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5052, 5052], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln2, 85, 40, 11 Melissa Place, 11 Melissa Place], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
	close()