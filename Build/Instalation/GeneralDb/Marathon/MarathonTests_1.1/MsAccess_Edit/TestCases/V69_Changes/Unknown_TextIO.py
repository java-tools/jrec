useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() +  'Unknown_Ams_LocDownload_20041228.txt')
##		click('ScrollPane$ScrollBar', 9, 102)
		select('ComboBox2', 'Unknown Text IO')
		click(commonBits.fl('Edit') + '1')
		select('Table', 'rows:[4,5,6,7,8,9,10,11,12,13,14,15],columns:[1 - 1|Data]')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Table View #{Selected Records#}'))
##		select('Table2', 'rows:[4,5,6,7,8,9,10,11,12,13,14,15],columns:[1 - 1|Data]')
		assert_p('Table', 'Content', '[[TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA], [TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA], [TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA], [TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA], [TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA], [TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA], [TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA], [TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA], [TAR5070STBass Hill                          Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA], [TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA], [TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA], [TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA]]')
##		select('Table', '')
		rightclick('Table', '1 - 1|Data,1')
		select_menu(commonBits.fl('Edit Record'))
		assert_p('HexThreeLineField', 'Text', '''TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
54533335556676762222222222222222222222222222566767622222222222222222222222222222222233246666625776672222222222222222222222225667676222222222222222222222222222233332222224554
41250193405e2948000000000000000000000000000005e2948000000000000000000000000000000000580c5c1e4034255400000000000000000000000005e294800000000000000000000000000002750000000e371''')
##		assert_p('Table', 'Content', '[[Data, 1, 1, TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA, T, 54]]')
		assert_p('Table', 'Content', '[[Data, 1, 1, TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA, T, 54415235303139535450656e726974682020202020202020202020202020202020202020202020202020202050656e726974682020202020202020202020202020202020202020202020202020202020202020203538204c656c616e642053747265657420202020202020202020202020202020202020202020202050656e7269746820202020202020202020202020202020202020202020202020202020323735302020202020204e535741]]')

		assert_p('Table', 'Text', 'TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA', commonBits.fl('Data') + ',0')
		click('Right')
		select('Table', 'cell:' + commonBits.fl('Data') + ',0(TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA)')
		select('Table', 'cell:' + commonBits.fl('Data') + ',0(TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA)')
		assert_p('Table', 'Text', 'TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA', commonBits.fl('Data') + ',0')
		select('Table', 'cell:' + commonBits.fl('Data') + ',0(TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA)')
		click('Right')
		assert_p('Table', 'Text', 'TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA', commonBits.fl('Data') + ',0')
	close()
