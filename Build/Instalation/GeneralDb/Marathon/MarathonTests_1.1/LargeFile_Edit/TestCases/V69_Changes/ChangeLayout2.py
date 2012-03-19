useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Unknown_Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'Unknown Text IO')

		click('Edit1')
		click('Find1')
##		click('MetalInternalFrameTitlePane', 177, 12)
		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Table: ')
		select('Table', 'rows:[4,5,6,7,8,9,10,11,12,13,14,15],columns:[1 - 1|Data]')
		select_menu('View>>Column View #{Selected Records#}')
###		select('Table2', 'rows:[4,5,6,7,8,9,10,11,12,13,14,15],columns:[1 - 1|Data]')
		select('Table', 'cell:Row 2,0(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		rightclick('Table', 'Row 2,0')
		select_menu('Edit Record')
		select('Table1', 'cell:Row 2,0(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		click('Find1')
		commonBits.doSleep()

		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Table: ')
		select('Table', 'rows:[4,5,6,7,8,9,10,11,12,13,14,15],columns:[1 - 1|Data]')
##		select('Table', 'rows:[4,5,6,7,8,9,10,11,12,13,14,15],columns:[1 - 1|Data]')
		select_menu('Edit>>Change Layout')
##		select('Table', 'rows:[4,5,6,7,8,9,10,11,12,13,14,15],columns:[1 - 1|Data]')
##		select('ComboBox2', 'ams PO Download')
		select('ComboBox2', 'ams Store')
		click('Go')
##		select('Table', '')
		rightclick('Table', '10 - 35|Loc Name,9')
		select_menu('Edit Record')
		select('Table', 'cell:Data,5(11 Melissa Place)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5052, 5052], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln2, 85, 40, 11 Melissa Place, 11 Melissa Place], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
		select('Table', 'cell:Data,5(11 Melissa Place)')
		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Column Table')
##		select('Table2', 'cell:Data,5(11 Melissa Place)')
		assert_p('Table', 'Content', '[[TAR, TAR, TAR, TAR, TAR, TAR, TAR, TAR, TAR, TAR, TAR, TAR], [5015, 5019, 5033, 5035, 5037, 5052, 5055, 5060, 5070, 5074, 5078, 5081], [ST, ST, ST, ST, ST, ST, ST, ST, ST, ST, ST, ST], [Bankstown, Penrith, Blacktown, Rockdale, Miranda, Eastwood, Leichhardt, St Marys, Bass Hill, Campbelltown, Warringah Mall, Ashfield], [Bankstown, Penrith, Marayong, Building B,  Portside DC, Westfield Shoppingtown, Marayong Offsite Reserve, Marketown, St. Mary\'s, Bass Hill Plaza, Campbelltown Mall, Frenchs Forest, Ashfield Mall], [Unit 2, 39-41 Allingham Street, 58 Leland Street, Dock 2, 11 Melissa Place, 2-8 Mc Pherson Street, Cnr. Urunga Pde & The Kingsway, 11 Melissa Place, Marion Street, Charles Hackett Drive, 753 Hume Highway, 303 Queen Street, Units 2-3, 14 Aquatic Drive, Knox Street], [Condell Park, Penrith, Marayong, Botany, Miranda, Marayong, Leichhardt, St Mary\'s, Bass Hill, Campbelltown, Frenchs Forest, Ashfield], [2200, 2750, 2148, 2019, 2228, 2148, 2040, 2760, 2197, 2560, 2086, 2131], [NSW, NSW, NSW, NSW, NSW, NSW, NSW, NSW, NSW, NSW, NSW, NSW], [A, A, A, A, A, A, A, A, A, A, A, A]]')
		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Record: 1')
		select('Table', 'cell:Data,5(11 Melissa Place)')
		select('Table', 'cell:Data,5(11 Melissa Place)')
		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR], [Loc Nbr, 4, 4, 5052, 5052], [Loc Type, 8, 2, ST, ST], [Loc Name, 10, 35, Eastwood, Eastwood], [Loc Addr Ln1, 45, 40, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln2, 85, 40, 11 Melissa Place, 11 Melissa Place], [Loc Addr Ln3, 125, 35, Marayong, Marayong], [Loc Postcode, 160, 10, 2148, 2148], [Loc State, 170, 3, NSW, NSW], [Loc Actv Ind, 173, 1, A, A]]')
##		select('Table', 'cell:Data,5(11 Melissa Place)')
		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Find')
##		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Find')
##		zzzz
##		select('Table', 'cell:Data,5(11 Melissa Place)')

##		Error in replay
##		assert_p('LayoutCombo', 'Content', '[[ams Store]]')


		assert_p('LayoutCombo', 'Text', 'ams Store')
		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Find1')
##		assert_p('LayoutCombo', 'Text', 'ams Store')

##		Error in replay
##		assert_p('LayoutCombo', 'Content', '[[ams Store]]')
		select('TextField', 'st')
##		select('ComboBox', 'Loc Type')
		click('Find1')
		click('Find1')
		select_menu('Window>>Unknown_Ams_LocDownload_20041228.txt>>Record: ')
##		assert_p('Table', 'Content', '[[Brand Id, 1, 3, TAR, TAR, 544152], [Loc Nbr, 4, 4, 5035, 5035, 35303335], [Loc Type, 8, 2, ST, ST, 5354], [Loc Name, 10, 35, Rockdale, Rockdale, 526f636b64616c65202020202020202020202020202020202020202020202020202020], [Loc Addr Ln1, 45, 40, Building B,  Portside DC, Building B,  Portside DC, 4275696c64696e6720422c2020506f72747369646520444320202020202020202020202020202020], [Loc Addr Ln2, 85, 40, 2-8 Mc Pherson Street, 2-8 Mc Pherson Street, 322d38204d632050686572736f6e2053747265657420202020202020202020202020202020202020], [Loc Addr Ln3, 125, 35, Botany, Botany, 426f74616e792020202020202020202020202020202020202020202020202020202020], [Loc Postcode, 160, 10, 2019, 2019, 32303139202020202020], [Loc State, 170, 3, NSW, NSW, 4e5357], [Loc Actv Ind, 173, 1, A, A, 41]]')
	close()
