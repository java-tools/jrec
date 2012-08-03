useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.5.0_11'

	if window('Record Layout Definitions'):
		click('*6')
		select('FileChooser', commonBits.cobolDir() + 'AmsLocation.cbl')
		click(commonBits.fl('Go')
)

		assert_p('TextArea', 'Text', '\n\n' + commonBits.fl('-->> ' + commonBits.cobolDir() + 'AmsLocation.cbl processed\n\n      Copybook: AmsLocation'))
		
		assert_p('TextArea', 'Text', commonBits.checkCopybookLoad(commonBits.cobolDir() + 'AmsLocation.cbl', 'AmsLocation'))

##		assert_p('TextArea', 'Text', ('''
##
##-->> ''' + commonBits.cobolDir() + '''AmsLocation.cbl processed
##
##      Copybook: AmsLocation''')

		
		select_menu(commonBits.fl('Record Layouts') + '>>' + commonBits.fl('Edit Layout'))

		select('TextField', 'AmsLo%')
		select('TabbedPane', commonBits.fl('Extras'))
		select('TextField1', '%')
		#select('TabbedPane', 'Fields')
		###select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Fields')
		#assert_p('TextField2', 'Text', 'AmsLocation')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',0(Brand)')
		assert_p('RecordFieldsJTbl', 'Text', 'cell:' + commonBits.fl('FieldName') + ',0(Brand)')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',1(Location-Number)')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 3, Brand, , 0, 0, 0, , , Brand], [4, 4, Location-Number, , 8, 0, 0, , , Location-Number], [8, 2, Location-Type, , 0, 0, 0, , , Location-Type], [10, 35, Location-Name, , 0, 0, 0, , , Location-Name], [45, 40, Address-1, , 0, 0, 0, , , Address-1], [85, 40, Address-2, , 0, 0, 0, , , Address-2], [125, 35, Address-3, , 0, 0, 0, , , Address-3], [160, 10, Postcode, , 8, 0, 0, , , Postcode], [170, 3, State, , 0, 0, 0, , , State], [173, 1, Location-Active, , 0, 0, 0, , , Location-Active]]')
		select('RecordFieldsJTbl', 'cell:' + commonBits.fl('FieldName') + ',2(Location-Type)')
		assert_p('RecordFieldsJTbl', 'RowCount', '10')

		if commonBits.isTstLanguage():
			click(commonBits.fl('Delete') + '1')

		else:
			commonBits.delete3(click)

		if window(commonBits.fl('Delete: AmsLocation')):
			click('Yes')
		close()

		###select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		###select('TabbedPane', 'Extras')
		#select('TabbedPane', 'Child Records')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click(commonBits.fl('Close')
)

##		select_menu('File>>Exit')
	close()
