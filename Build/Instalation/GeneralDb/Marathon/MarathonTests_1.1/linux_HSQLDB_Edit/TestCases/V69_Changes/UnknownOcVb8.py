useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_17'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'OC_VB_Test.bin')
		select('ComboBox2', 'Unknown Open Cobol VB')
		click('Edit1')
		select('Table', 'cell:1 - 1|Data,1(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		assert_p('Table', 'Content', '[[TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA], [TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA], [TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA], [TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA], [TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA], [TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA]]')
		select('Table', 'cell:1 - 1|Data,1(TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA)')
		select('LayoutCombo', 'Full Line')
		assert_p('Table', 'Content', '[[TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA], [TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA], [TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA], [TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA], [TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA], [TAR5052STEastwood                           Marayong Offsite Reserve                11 Melissa Place                        Marayong                           2148      NSWA]]')
##		select('Table', '')
		rightclick('Table', 'Full Line,2')
		select_menu('Edit Record')
		assert_p('Table', 'Text', 'TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA', 'Data,0')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
