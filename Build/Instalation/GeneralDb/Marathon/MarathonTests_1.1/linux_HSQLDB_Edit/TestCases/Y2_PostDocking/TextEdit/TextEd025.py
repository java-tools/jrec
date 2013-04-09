useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt',  commonBits.sampleDir() + 'Ams_LocDownload_20041228abc.txt')
		click('Edit1')
		select_menu('View>>Text View')
		select('TabbedPane', 'Document View')
		assert_p('TextArea', 'Text', '''TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA
TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA
TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA
TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA
TAR5070STBass Hill                          Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA
TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA''')
		click('DisplayFrame$TabButton', 27, 22)
		select('TabbedPane', 'Table:')
		select('LineList.FileDisplay_JTbl', '', '8 - 2|Loc Type,2')
		select('LineList.FileDisplay_JTbl', '', '10 - 35|Loc Name,3')
		select('LineList.FileDisplay_JTbl', 'cell:10 - 35|Loc Name,5(Eastwood)')
		assert_p('LineList.FileDisplay_JTbl', 'Text', 'cell:10 - 35|Loc Name,5(Eastwood)')
		select('LineList.FileDisplay_JTbl', 'cell:10 - 35|Loc Name,5(Eastwood)')
		click('Document View')
		select('TabbedPane', 'Document View')
		assert_p('TextArea', 'Text', '''TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5033  Blacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035ST                                   Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA
TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA
TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA
TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA
TAR5070STBass Hill                          Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA
TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA''')
		click('DisplayFrame$TabButton', 17, 21)
		select('TabbedPane', 'Table:')
		select('LineList.FileDisplay_JTbl', 'rows:[1,2,3],columns:[1 - 3|Brand Id]')
		rightclick('LineList.FileDisplay_JTbl', '4 - 4|Loc Nbr,2')
		select_menu('Copy Record#{s#}')
		select('LineList.FileDisplay_JTbl', 'cell:4 - 4|Loc Nbr,13(5090)')
		rightclick('LineList.FileDisplay_JTbl', '4 - 4|Loc Nbr,13')
		select_menu('Paste Record#{s#}')
		select('LineList.FileDisplay_JTbl', 'cell:4 - 4|Loc Nbr,13(5090)')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, , Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, , Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, , Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, , Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5091, ST, Chatswood, Frenchs Forest, Unit 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A]]')
		select('LineList.FileDisplay_JTbl', 'cell:4 - 4|Loc Nbr,13(5090)')
		click('Document View')
		select('TabbedPane', 'Document View')
		assert_p('TextArea', 'Text', '''TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5033  Blacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035ST                                   Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA
TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA
TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA
TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA
TAR5070STBass Hill                          Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA
TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5033  Blacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035ST                                   Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA''')
		click('Table:')
		select('TabbedPane', 'Table:')
		select('LineList.FileDisplay_JTbl', '', '8 - 2|Loc Type,10')
		select('LineList.FileDisplay_JTbl', '', '4 - 4|Loc Nbr,10')
		select('LineList.FileDisplay_JTbl', '', '45 - 40|Loc Addr Ln1,10')
		select('LineList.FileDisplay_JTbl', '', '170 - 3|Loc State,10')
		select('LineList.FileDisplay_JTbl', 'cell:160 - 10|Loc Postcode,11(2131)')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, , Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, , Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, , , Warringah Mall, , Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, , A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, , Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, , Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5091, ST, Chatswood, Frenchs Forest, Unit 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A]]')
		select('LineList.FileDisplay_JTbl', 'cell:160 - 10|Loc Postcode,11(2131)')
		click('Document View')
		select('TabbedPane', 'Document View')
		assert_p('TextArea', 'Text', '''TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5033  Blacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035ST                                   Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA
TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA
TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA
TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA
TAR5070STBass Hill                          Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR      Warringah Mall                                                             Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086         A
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA
TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5033  Blacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035ST                                   Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA''')
		click('Table:')
		select('TabbedPane', 'Table:')
		select('Table', 'cell:Line,10(11)')
		select('LineList.FileDisplay_JTbl', 'rows:[10,11,12,13,14],columns:[160 - 10|Loc Postcode]')
		rightclick('LineList.FileDisplay_JTbl', '10 - 35|Loc Name,12')
		select_menu('Delete Record#{s#}')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, , Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, , Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5033, , Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, , Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5091, ST, Chatswood, Frenchs Forest, Unit 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A]]')
		click('Document View')
		select('TabbedPane', 'Document View')
		assert_p('TextArea', 'Text', '''TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5033  Blacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035ST                                   Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA
TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA
TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA
TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA
TAR5070STBass Hill                          Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5033  Blacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035ST                                   Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA''')
		click('Table:')
		select('TabbedPane', 'Table:')
		select('LineList.FileDisplay_JTbl', 'cell:10 - 35|Loc Name,10(Blacktown)')
		rightclick('LineList.FileDisplay_JTbl', '10 - 35|Loc Name,10')
		select_menu('Delete Record#{s#}')
		click('Document View')
		select('TabbedPane', 'Document View')
		assert_p('TextArea', 'Text', '''TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5033  Blacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035ST                                   Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA
TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA
TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA
TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA
TAR5070STBass Hill                          Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5035ST                                   Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA''')
		click('Table:')
		select('TabbedPane', 'Table:')
		select('LineList.FileDisplay_JTbl', 'cell:8 - 2|Loc Type,11(ST)')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

#		if window(r'Save Changes to file: C:\Users\BruceTst/RecordEditor_HSQL\SampleFiles/Ams_LocDownload_20041228abc.txt'):
#			click('No')
#		close()
	close()
