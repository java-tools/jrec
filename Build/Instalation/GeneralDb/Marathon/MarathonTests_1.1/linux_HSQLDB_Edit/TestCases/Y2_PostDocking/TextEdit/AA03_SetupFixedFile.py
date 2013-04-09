useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		click('Edit1')
		select('LineList.FileDisplay_JTbl', 'rows:[4,5,6,7,8,9,10,11,12,13,14,15,16,17,18],columns:[10 - 35|Loc Name]')
		click('Export')
		select('What to Save_Txt', 'Selected Records')
		select('File Name_Txt', commonBits.sampleDir() + 'Ams_LocDownload_20041228abc.txt')
		select('Edit Output File_Chk', 'true')
		click('Save File')
		select('LineList.FileDisplay_JTbl', 'cell:45 - 40|Loc Addr Ln1,6(Marketown)')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5091, ST, Chatswood, Frenchs Forest, Unit 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A]]')
	close()
