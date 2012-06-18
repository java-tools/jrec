useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		click('Layout Wizard')
##		select('TextField2', 'Wizard - ZZ record 1')
		click('Right')
		click('Right')
		select('Table', 'Field 1', 'Field Name,0')
		select('Table', 'cell:Field Name,0(Field 1)')
		rightclick('Table', 'Field Name,0')
		assert_p('Table', 'Content', '[[Field 1, 1, 173, 0, 0, true]]')
		select('Table', 'cell:Field Name,0(Field 1)')
		assert_p('Table', 'Text', 'cell:Field Name,0(Field 1)')
		select('Table', 'cell:Field Name,0(Field 1)')
		select('Table1', 'cell:Field 1,0(TAR5839DCDC - Taras Ave                                                             30-68 Taras Ave                         Altona North                       3025      VICA)')
		assert_p('Table1', 'Text', 'cell:Field 1,0(TAR5839DCDC - Taras Ave                                                             30-68 Taras Ave                         Altona North                       3025      VICA)')
		select('Table1', 'cell:Field 1,0(TAR5839DCDC - Taras Ave                                                             30-68 Taras Ave                         Altona North                       3025      VICA)')
		click('Right')


		#   Save Layout panel
		#   -----------------
		select('TabbedPane', '')
		select('TextField', 'Wizard - ZZ record 1')
##		select('BmKeyedComboBox', '7')
		click('Right')


		select('Table', 'cell:1 - 173|Field 1,1(TAR5850DCVIC West Ad Support                                                        Lot 2 Little Boundary Rd                Laverton                           3028      VICA)')
		assert_p('Table', 'Text', 'cell:1 - 173|Field 1,1(TAR5850DCVIC West Ad Support                                                        Lot 2 Little Boundary Rd                Laverton                           3028      VICA)')
		select('Table', 'rows:[0,1,2,3,4,5,6,7,8],columns:[1 - 173|Field 1]')
		select_menu('Data>>Table View #{Selected Records#}')
##		select('Table2', 'rows:[0,1,2,3,4,5,6,7,8],columns:[1 - 173|Field 1]')
		select('Table', 'cell:1 - 173|Field 1,1(TAR5850DCVIC West Ad Support                                                        Lot 2 Little Boundary Rd                Laverton                           3028      VICA)')
		assert_p('Table', 'Content', '[[TAR5839DCDC - Taras Ave                                                             30-68 Taras Ave                         Altona North                       3025      VICA], [TAR5850DCVIC West Ad Support                                                        Lot 2 Little Boundary Rd                Laverton                           3028      VICA], [TAR5853DCNSW North Sydney Ad Support                                                                                                                                        A], [TAR5866DCWA Ad Support                                                                                                                                                      A], [TAR5015STBankstown                          Bankstown                               Unit 2, 39-41 Allingham Street          Condell Park                       2200      NSWA], [TAR5019STPenrith                            Penrith                                 58 Leland Street                        Penrith                            2750      NSWA], [TAR5033STBlacktown                          Marayong                                Dock 2, 11 Melissa Place                Marayong                           2148      NSWA], [TAR5035STRockdale                           Building B,  Portside DC                2-8 Mc Pherson Street                   Botany                             2019      NSWA], [TAR5037STMiranda                            Westfield Shoppingtown                  Cnr. Urunga Pde & The Kingsway          Miranda                            2228      NSWA]]')
		select('Table', 'cell:1 - 173|Field 1,1(TAR5850DCVIC West Ad Support                                                        Lot 2 Little Boundary Rd                Laverton                           3028      VICA)')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')

		select('FileChooser', commonBits.sampleDir() + 'Ams_LocDownload_20041228.txt')
		commonBits.setRecordLayout(select, 'ams Store')
		click('Edit1')
		#click('BasicInternalFrameTitlePane$NoFocusButton2')


		select_menu('Record Layouts>>Edit Layout')
		select('TextField', 'Wizard - ZZ record 1%')
		select('TextField1', '%')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		select('Table', 'cell:Record Name,0(Wizard - ZZ record 1)')
		select('RecordFieldsJTbl', 'cell:FieldName,0(Field 1)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 173, Field 1, , 0, 0, 0, , , ]]')
		select('RecordFieldsJTbl', 'cell:FieldName,0(Field 1)')
		click('Delete3')

		if window('Delete: Wizard - ZZ record 1'):
			click('Yes')
		close()

		select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		#select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		select('TextField', 'Wi%')
		select('TextField1', '%')
		select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
		
	close()
