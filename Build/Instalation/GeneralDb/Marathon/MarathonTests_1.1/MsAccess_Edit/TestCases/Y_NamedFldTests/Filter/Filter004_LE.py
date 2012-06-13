useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		if commonBits.version() == 'MsAccess':
			select('Record Layout_Txt', 'Comma Delimited, names on the first line')
		else:
			select('System_Txt', 'CSV')
		click('Edit1')
		select_menu('Edit>>Update Csv Columns')
		select('FieldChange_JTbl', 'Number', 'Type,5')
		select('FieldChange_JTbl', 'Number', 'Type,1')
		select('FieldChange_JTbl', 'cell:Type,1(Number)')
		click('Apply')
		click('Filter1')
		select('Filter.FieldRelationship_JTbl', 'cell:Field,0(null)')
		select('Filter.FieldRelationship_JTbl', 'SALE-PRICE', 'Field,0')
		select('Filter.FieldRelationship_JTbl', '5.95', 'Value,0')
		select('Filter.FieldRelationship_JTbl', 'cell:Field,1(null)')
		click('Filter1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[60614487, 59, 40118, 878, 1, 5.95]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:Operator,0(Contains)')
##		select('Filter.FieldRelationship_JTbl', '<= ', 'Operator,0')
		select('Filter.FieldRelationship_JTbl', '<= ', 'Operator,0')
##		select('Filter.FieldRelationship_JTbl', 'cell:Operator,0(>)')
		click('Filter1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, -1, -17.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95], [68654655, 166, 40118, 60, 1, 5.08]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Filter.FieldRelationship_JTbl', 'cell:Field,1(null)')
		select('Filter.FieldRelationship_JTbl', '<= ', 'Operator,0')
		select('Filter.FieldRelationship_JTbl', 'STORE-NO', 'Field,1')
		select('Filter.FieldRelationship_JTbl', '59', 'Value,1')
		select('Filter.FieldRelationship_JTbl', '<= ', 'Operator,0')
##		select('Filter.FieldRelationship_JTbl', 'cell:Value,2()')
		click('Filter1')
##		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[61664713, 59, 40118, 335, -1, -17.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95]]')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[61664713, 59, 40118, 335, -1, -17.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95]]')

	close()
