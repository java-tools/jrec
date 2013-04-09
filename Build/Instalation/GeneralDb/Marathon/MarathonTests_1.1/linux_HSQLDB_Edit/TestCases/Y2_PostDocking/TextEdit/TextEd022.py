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
		rightclick('Document View')
		select_menu('Show Child Record')
		select('TabbedPane', 'Document View')
		assert_p('LineFrame.FileDisplay_JTbl', 'Text', 'Loc Name', 'Field,3')
		assert_p('LineFrame.Record_Txt', 'Text', '1')
		click('Find')
		select('Find.Search For_Txt', 'st')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=1, 2, 0')
		assert_p('LineFrame.Record_Txt', 'Text', '1')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=1, 3, 4')
		assert_p('LineFrame.Record_Txt', 'Text', '1')
		assert_p('TextArea', 'Background', '[r=255,g=255,b=255]')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=1, 4, 4')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[Brand Id, 1, TAR, TAR], [Loc Nbr, 4, 5015, 5015], [Loc Type, 8, ST, ST], [Loc Name, 10, Bankstown, Bankstown], [Loc Addr Ln1, 45, Bankstown, Bankstown], [Loc Addr Ln2, 85, Unit 2, 39-41 Allingham Street, Unit 2, 39-41 Allingham Street], [Loc Addr Ln3, 125, Condell Park, Condell Park], [Loc Postcode, 160, 2200, 2200], [Loc State, 170, NSW, NSW], [Loc Actv Ind, 173, A, A]]')
		assert_p('LineFrame.Record_Txt', 'Text', '1')
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
		click('Find >>')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=2, 2, 0')
		assert_p('LineFrame.Record_Txt', 'Text', '2')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[Brand Id, 1, TAR, TAR], [Loc Nbr, 4, 5019, 5019], [Loc Type, 8, ST, ST], [Loc Name, 10, Penrith, Penrith], [Loc Addr Ln1, 45, Penrith, Penrith], [Loc Addr Ln2, 85, 58 Leland Street, 58 Leland Street], [Loc Addr Ln3, 125, Penrith, Penrith], [Loc Postcode, 160, 2750, 2750], [Loc State, 170, NSW, NSW], [Loc Actv Ind, 173, A, A]]')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=2, 5, 10')
		assert_p('LineFrame.Record_Txt', 'Text', '2')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=3, 2, 0')
		assert_p('LineFrame.Record_Txt', 'Text', '3')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[Brand Id, 1, TAR, TAR], [Loc Nbr, 4, 5033, 5033], [Loc Type, 8, ST, ST], [Loc Name, 10, Blacktown, Blacktown], [Loc Addr Ln1, 45, Marayong, Marayong], [Loc Addr Ln2, 85, Dock 2, 11 Melissa Place, Dock 2, 11 Melissa Place], [Loc Addr Ln3, 125, Marayong, Marayong], [Loc Postcode, 160, 2148, 2148], [Loc State, 170, NSW, NSW], [Loc Actv Ind, 173, A, A]]')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=4, 2, 0')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=4, 5, 15')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=5, 2, 0')
		assert_p('LineFrame.Record_Txt', 'Text', '5')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[Brand Id, 1, TAR, TAR], [Loc Nbr, 4, 5037, 5037], [Loc Type, 8, ST, ST], [Loc Name, 10, Miranda, Miranda], [Loc Addr Ln1, 45, Westfield Shoppingtown, Westfield Shoppingtown], [Loc Addr Ln2, 85, Cnr. Urunga Pde & The Kingsway, Cnr. Urunga Pde & The Kingsway], [Loc Addr Ln3, 125, Miranda, Miranda], [Loc Postcode, 160, 2228, 2228], [Loc State, 170, NSW, NSW], [Loc Actv Ind, 173, A, A]]')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=5, 4, 2')
		assert_p('TextArea', 'Background', '[r=255,g=255,b=255]')
		assert_p('LineFrame.Record_Txt', 'Text', '5')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=6, 2, 0')
		assert_p('LineFrame.Record_Txt', 'Text', '6')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[Brand Id, 1, TAR, TAR], [Loc Nbr, 4, 5052, 5052], [Loc Type, 8, ST, ST], [Loc Name, 10, Eastwood, Eastwood], [Loc Addr Ln1, 45, Marayong Offsite Reserve, Marayong Offsite Reserve], [Loc Addr Ln2, 85, 11 Melissa Place, 11 Melissa Place], [Loc Addr Ln3, 125, Marayong, Marayong], [Loc Postcode, 160, 2148, 2148], [Loc State, 170, NSW, NSW], [Loc Actv Ind, 173, A, A]]')
		click('Find >>')
		click('Find >>')
		assert_p('TextField', 'Text', 'Found (line, field Num, field position)=7, 2, 0')
		assert_p('LineFrame.Record_Txt', 'Text', '7')
		assert_p('LineFrame.FileDisplay_JTbl', 'Content', '[[Brand Id, 1, TAR, TAR], [Loc Nbr, 4, 5055, 5055], [Loc Type, 8, ST, ST], [Loc Name, 10, Leichhardt, Leichhardt], [Loc Addr Ln1, 45, Marketown, Marketown], [Loc Addr Ln2, 85, Marion Street, Marion Street], [Loc Addr Ln3, 125, Leichhardt, Leichhardt], [Loc Postcode, 160, 2040, 2040], [Loc State, 170, NSW, NSW], [Loc Actv Ind, 173, A, A]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
