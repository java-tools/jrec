useFixture(default)

###
###   Warning - does not replay correctly / adjusted result
###

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt',  commonBits.sampleDir() + 'Ams_LocDownload_20041228abc.txt')
		click('Edit1')
		select_menu('View>>Text View')
		select('TabbedPane', 'Document View')
##		select('TextArea', '''TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
##TAR5019STPenrith aaa                        Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
##TAR5033STBlacktown                          Marayong      bbb                       Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
##TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
##TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA
##TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA
##TAR5055STLeichhardt   ccc                   Marketown                               Marion Street                           Leichhardt                         2040      NSWA
##TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA
##TAR5070STBass Hill                          Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA
##TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
##TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
##TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
##TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
##TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA
##TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA''')

		select('TextArea', 'TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA\nTAR5019STPenrith aaa                        Penrith                                 58 Leland Street                        Penrith                            2750      NSWA\nTAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA\nTAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA\nTAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA\nTAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA\nTAR5033STBlacktown                          Marayong      bbb                       Dock 2, 11 Melissa Place                Marayong                           2148      NSWA\nTAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA\nTAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA\nTAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA\nTAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA\nTAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA\nTAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA\nTAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA\nTAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA\nTAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA\nTAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA\nTAR5070STBass Hill    eee                   Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA\nTAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA\nTAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA\nTAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA\nTAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA\nTAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA\nTAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA')



		select_menu('Utilities>>Compare with Disk')
##		assert_p('Table', 'Content', '[[, Old, 2, TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [, New, 2, , , , Penrith aaa, , , , , , ], [, Old, 3, TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [, New, 3, , , , , Marayong      bbb, , , , , ], [, Old, 7, TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [, New, 7, , , , Leichhardt   ccc, , , , , , ]]')
		assert_p('Table', 'Content', '[[, Old, 2, TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [, New, 2, , , , Penrith aaa, , , , , , ], [, Old, 3, TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [, New, 3, , 5074, , Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, , ], [, Old, 4, TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [, New, 4, , 5078, , Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, , ], [, Old, 5, TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [, New, 5, , 5081, , Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, , ], [, , , , , , , , , , , , ], [, Inserted, 6, TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 7, TAR, 5033, ST, Blacktown, Marayong      bbb, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 8, TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 9, TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 10, TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 11, TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 12, TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 13, TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson S& The Kingsway, Miranda, 2228, NSW, A], [, Old, 9, TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [, New, 17, , , , Bass Hill    eee, , , , , , ], [, , , , , , , , , , , , ], [, Inserted, 24, TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde, , , , ]]')


##		assert_p('Table', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5019, ST, Penrith aaa, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, ST, Blacktown, Marayong      bbb, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson S& The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt   ccc, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5091, ST, Chatswood, Frenchs Forest, Unit 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde, , , , ]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Table:')
		select('TabbedPane', 'Table:')
##		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5019, ST, Penrith aaa, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5033, ST, Blacktown, Marayong      bbb, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt   ccc, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5091, ST, Chatswood, Frenchs Forest, Unit 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A]]')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5019, ST, Penrith aaa, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5033, ST, Blacktown, Marayong      bbb, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson S& The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill    eee, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5091, ST, Chatswood, Frenchs Forest, Unit 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde, , , , ]]')


		click('Document View')
		select('TabbedPane', 'Document View')
		select('TextArea', '''TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5019STPenrith aaa                        Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5033STBlacktown                          Marayong      bbb                       Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA
TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA
TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA
TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA
TAR5070STBass Hill    eee                   Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA
TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA''')

		select_menu('Utilities>>Compare with Disk')
##		assert_p('Table', 'Content', '[[, Old, 2, TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [, New, 2, , , , Penrith aaa, , , , , , ], [, Old, 3, TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [, New, 3, , 5074, , Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, , ], [, , , , , , , , , , , , ], [, Inserted, 4, TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 5, TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 6, TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 7, TAR, 5033, ST, Blacktown, Marayong      bbb, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 8, TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 9, TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 10, TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 11, TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 12, TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [, Old, 9, TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [, New, 18, , , , Bass Hill    eee, , , , , , ]]')
		assert_p('Table', 'Content', '[[, Old, 2, TAR, 5019, ST, Penrith, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [, New, 2, , , , Penrith aaa, , , , , , ], [, Old, 3, TAR, 5033, ST, Blacktown, Marayong, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [, New, 3, , 5074, , Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, , ], [, Old, 4, TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [, New, 4, , 5078, , Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, , ], [, Old, 5, TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [, New, 5, , 5081, , Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, , ], [, , , , , , , , , , , , ], [, Inserted, 6, TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 7, TAR, 5033, ST, Blacktown, Marayong      bbb, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 8, TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 9, TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 10, TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 11, TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 12, TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [, , , , , , , , , , , , ], [, Inserted, 13, TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson S& The Kingsway, Miranda, 2228, NSW, A], [, Old, 9, TAR, 5070, ST, Bass Hill, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [, New, 17, , , , Bass Hill    eee, , , , , , ], [, , , , , , , , , , , , ], [, Inserted, 24, TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde, , , , ]]')


		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Table:')
		select('TabbedPane', 'Table:')
##		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5019, ST, Penrith aaa, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5033, ST, Blacktown, Marayong      bbb, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson Street, Botany, 2019, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde & The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill    eee, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5091, ST, Chatswood, Frenchs Forest, Unit 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A]]')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[TAR, 5015, ST, Bankstown, Bankstown, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5019, ST, Penrith aaa, Penrith, 58 Leland Street, Penrith, 2750, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5033, ST, Blacktown, Marayong      bbb, Dock 2, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5035, ST, Rockdale, Building B,  Portside DC, 2-8 Mc Pherson S& The Kingsway, Miranda, 2228, NSW, A], [TAR, 5052, ST, Eastwood, Marayong Offsite Reserve, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5055, ST, Leichhardt, Marketown, Marion Street, Leichhardt, 2040, NSW, A], [TAR, 5060, ST, St Marys, St. Mary\'s, Charles Hackett Drive, St Mary\'s, 2760, NSW, A], [TAR, 5070, ST, Bass Hill    eee, Bass Hill Plaza, 753 Hume Highway, Bass Hill, 2197, NSW, A], [TAR, 5074, ST, Campbelltown, Campbelltown Mall, 303 Queen Street, Campbelltown, 2560, NSW, A], [TAR, 5078, ST, Warringah Mall, Frenchs Forest, Units 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5081, ST, Ashfield, Ashfield Mall, Knox Street, Ashfield, 2131, NSW, A], [TAR, 5085, ST, Roselands, Condell park, Unit 2, 39-41 Allingham Street, Condell Park, 2200, NSW, A], [TAR, 5090, ST, Carlingford Court, , Dock 1, 11 Melissa Place, Marayong, 2148, NSW, A], [TAR, 5091, ST, Chatswood, Frenchs Forest, Unit 2-3, 14 Aquatic Drive, Frenchs Forest, 2086, NSW, A], [TAR, 5037, ST, Miranda, Westfield Shoppingtown, Cnr. Urunga Pde, , , , ]]')



		click('Document View')
		select('TabbedPane', 'Document View')
		select('TextArea', '''TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5019STPenrith aaa                        Penrith                                 58 Leland Street                        Penrith                            2750      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5033STBlacktown                          Marayong      bbb                       Dock 2, 11 Melissa Place                Marayong                           2148      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA
TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA
TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA
TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA
TAR5055STLeichhardt                         Marketown                               Marion Street                           Leichhardt                         2040      NSWA
TAR5060STSt Marys                           St. Mary\'s                              Charles Hackett Drive                   St Mary\'s                          2760      NSWA
TAR5070STBass Hill    eee                   Bass Hill Plaza                         753 Hume Highway                        Bass Hill                          2197      NSWA
TAR5074STCampbelltown                       Campbelltown Mall                       303 Queen Street                        Campbelltown                       2560      NSWA
TAR5078STWarringah Mall                     Frenchs Forest                          Units 2-3, 14 Aquatic Drive             Frenchs Forest                     2086      NSWA
TAR5081STAshfield                           Ashfield Mall                           Knox Street                             Ashfield                           2131      NSWA
TAR5085STRoselands                          Condell park                            Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA
TAR5090STCarlingford Court                                                          Dock 1, 11 Melissa Place                Marayong                           2148      NSWA
TAR5091STChatswood                          Frenchs Forest                          Unit 2-3, 14 Aquatic Drive              Frenchs Forest                     2086      NSWA''')

##		if window(r'Save Changes to file: C:\Users\BruceTst/RecordEditor_HSQL\SampleFiles/Ams_LocDownload_20041228abc.txt'):
##			click('No')
##		close()
	close()
