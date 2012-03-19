useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_03'

	if window('Record Editor'):
		select('FileChooser', commonBits.sampleDir() + 'zAms_LocDownload_20041228.csv')
		click('Layout Wizard')
		select('Delimited Fields', 'true')

#		select('ComboBox', 'Delimited Fields')
#		select('TextField2', 'Wizard - AmsLocation : Comma2Csv')
#		click('Right')
		select('FileChooser', commonBits.sampleDir() + 'zAms_LocDownload_20041228.csv')
		click('Right')
		select('TabbedPane', '')
		select('CheckBox', 'false')
		click('Right')
		select('TabbedPane', '')
		select('Table', 'cell:Field Name,0(A)')
		assert_p('Table', 'Text', 'C', 'Field Name,2')
		select('Table', 'cell:Field Name,0(A)')
		assert_p('Table', 'Content', '[[A, 1, 0, true], [B, 2, 0, true], [C, 3, 0, true], [D, 4, 0, true], [E, 5, 0, true], [F, 6, 0, true], [G, 7, 0, true], [H, 8, 0, true], [I, 9, 0, true], [J, 10, 0, true], [K, 11, 0, true]]')
		select('Table', 'Brand', 'Field Name,0')
		select('Table', 'Location', 'Field Name,1')
		select('Table', 'Type', 'Field Name,2')
#		select('Table', 'cell:Type,1(0)')
#		select('Table', 'cell:Type,1(0)')
#		click('ScrollPane$ScrollBar', 8, 64)
#		click('ScrollPane$ScrollBar', 9, 87)
#		select('Table', 'Type', 'Field Name,2')
		select('Table', 'Name', 'Field Name,3')
		select('Table', 'cell:Field Name,4(E)')
		select('Table1', 'cell:Type,0(Loc_Type)')
		assert_p('Table1', 'Text', 'Loc_Type', 'Type,0')
		select('Table1', 'cell:F,0(Loc_Addr_Ln2)')
		assert_p('Table1', 'Text', '30-68 Taras Ave', 'F,1')
		select('Table1', 'cell:F,0(Loc_Addr_Ln2)')
		select('Table', 'Address 1', 'Field Name,4')
		select('Table', 'Address 2', 'Field Name,5')
		select('Table', 'cell:Field Name,5(F)')
#		click('MetalScrollButton4', 3)
		select('Table', 'Address 3', 'Field Name,6')
		select('Table', 'Postcode', 'Field Name,7')
		select('Table', 'cell:Field Name,7(H)')
		select('Table1', 'cell:Address 3,1(Altona North)')
		assert_p('Table1', 'Text', 'Altona North', 'Address 3,1')
		select('Table1', 'cell:Address 2,1(30-68 Taras Ave)')
		assert_p('Table1', 'Text', '30-68 Taras Ave', 'Address 2,1')
		select('Table1', 'cell:Address 2,1(30-68 Taras Ave)')
#		click('ScrollPane$ScrollBar5', 470, 7)
#		click('ScrollPane$ScrollBar5', 570, 13)
		select('Table', 'Post Code', 'Field Name,8')
		select('Table', 'State', 'Field Name,9')
		select('Table', 'cell:Field Name,9(J)')
#		doubleclick('ScrollPane3', 642, 91)
#		click('ScrollPane$ScrollBar5', 603, 7)
#		click('ScrollPane$ScrollBar4', 10, 46)
#		click('ScrollPane$ScrollBar4', 10, 46)
#		click('ScrollPane$ScrollBar4', 10, 24)
#		click('ScrollPane$ScrollBar5', 78, 6)
#		click('ScrollPane$ScrollBar5', 604, 12)
#		click('ScrollPane$ScrollBar5', 604, 12)
		select('Table', 'cell:Field Name,8(Post Code)')
#		click('ScrollPane$ScrollBar2', 7, 18)
#		click('ScrollPane$ScrollBar2', 2, 35)
		click('Right')


		#   Save Layout panel
		#   -----------------
		select('TabbedPane', '')
		select('TextField', 'Wizard - AmsLocation : Comma2Csv')
##		select('BmKeyedComboBox', '7')
		click('Right')



		select('Table', 'cell:4|Name,3(NSW North Sydney Ad Support)')
		assert_p('Table', 'Text', 'WA Ad Support', '4|Name,4')
		select('Table', 'cell:5|Address 1,10(Marayong Offsite Reserve)')
		assert_p('Table', 'Text', 'Marayong Offsite Reserve', '5|Address 1,10')
		select('Table', 'cell:6|Address 2,12(Charles Hackett Drive)')
		assert_p('Table', 'Text', 'Charles Hackett Drive', '6|Address 2,12')
		select('Table', 'rows:[9,10,11,12],columns:[4|Name]')
		select_menu('View>>Column View #{Selected Records#}')
##		select('Table2', 'rows:[9,10,11,12],columns:[4|Name,5|Address 1]')
		select('Table', 'cell:Row 1,3(Miranda)')
		assert_p('Table', 'Text', 'Westfield Shoppingtown', 'Row 1,4')
		select('Table', 'cell:Row 2,4(Marayong Offsite Reserve)')
		assert_p('Table', 'Text', 'Marayong Offsite Reserve', 'Row 2,4')
		select('Table', 'cell:Row 2,4(Marayong Offsite Reserve)')
		select('Table', 'cell:Row 1,5(Cnr. Urunga Pde & The Kingsway)')
		assert_p('Table', 'Content', '[[TAR, TAR, TAR, TAR], [5037, 5052, 5055, 5060], [ST, ST, ST, ST], [Miranda, Eastwood, Leichhardt, St Marys], [Westfield Shoppingtown, Marayong Offsite Reserve, Marketown, St. Mary\'s], [Cnr. Urunga Pde & The Kingsway, 11 Melissa Place, Marion Street, Charles Hackett Drive], [Miranda, Marayong, Leichhardt, St Mary\'s], [2228, 2148, 2040, 2760], [NSW, NSW, NSW, NSW], [A, A, A, A], [, , , ]]')
		select('Table', 'cell:Row 1,1(5037)')
		select('Table1', 'cell:Field,1(Location)')
		assert_p('Table1', 'Text', 'Location', 'Field,1')
		select('Table1', 'cell:Field,5(Address 2)')
		assert_p('Table1', 'Text', 'Address 2', 'Field,5')
		select('Table1', 'cell:Field,5(Address 2)')
		select('Table1', 'cell:Field,6(Address 3)')
		assert_p('Table1', 'Content', '[[Brand, 1, ], [Location, 2, ], [Type, 3, ], [Name, 4, ], [Address 1, 5, ], [Address 2, 6, ], [Address 3, 7, ], [Postcode, 8, ], [Post Code, 9, ], [State, 10, ], [K, 11, ]]')
		select('Table1', 'cell:Field,6(Address 3)')
		select_menu('Record Layouts>>Edit Layout')
		select('Table2', 'cell:Field,6(Address 3)')
		select('TextField', 'Wizard - AmsLocation : Comma2Csv%')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
		select('TextField1', '%')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
		assert_p('TextField2', 'Text', 'Wizard - AmsLocation : Comma2Csv')
		select('RecordFieldsJTbl', 'cell:FieldName,1(Location)')
		assert_p('RecordFieldsJTbl', 'Text', 'Type', 'FieldName,2')
		select('RecordFieldsJTbl', 'cell:FieldName,8(Post Code)')
		assert_p('RecordFieldsJTbl', 'Text', 'Post Code', 'FieldName,8')
		select('RecordFieldsJTbl', 'cell:FieldName,8(Post Code)')
		click('Delete3')

		if window('Delete: Wizard - AmsLocation : Comma2Csv'):
			click('Yes')
		close()

#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Extras')
#		select('TabbedPane', 'Fields')
		commonBits.closeWindow(click)
		##click('BasicInternalFrameTitlePane$NoFocusButton2')
#		select('Table1', 'cell:Field,6(Address 3)')
	close()
