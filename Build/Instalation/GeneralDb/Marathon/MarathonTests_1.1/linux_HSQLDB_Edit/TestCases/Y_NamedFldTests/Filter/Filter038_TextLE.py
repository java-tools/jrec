useFixture(default)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click('Edit1')
		select_menu('Edit>>Update Csv Columns')
		select('FieldChange_JTbl', 'Number', 'Type,5')
		select('FieldChange_JTbl', 'Number', 'Type,1')
		select('FieldChange_JTbl', 'cell:Type,1(Number)')
		click('Apply')
		click('Filter1')
##		select('Filter.FieldRelationship_JTbl', 'cell:Field,0(null)')
		select('Filter.FieldRelationship_JTbl', 'SALE-PRICE', 'Field,0')
		select('Filter.FieldRelationship_JTbl', '<= (Text)', 'Operator,0')
		select('Filter.FieldRelationship_JTbl', '3.99', 'Value,0')
##		select('Filter.FieldRelationship_JTbl', 'cell:Or,1()')
		click('Filter1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Filter.FieldRelationship_JTbl', 'cell:Field,1(null)')
		select('Filter.FieldRelationship_JTbl', 'STORE-NO', 'Field,1')
		select('Filter.FieldRelationship_JTbl', '20', 'Value,1')
##		select('Filter.FieldRelationship_JTbl', 'cell:Value,2()')
		
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , SALE-PRICE, true, <= (Text), 3.99], [, And, STORE-NO, true, Contains, 20], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ]]')
		
		select('Filter.FieldRelationship_JTbl', 'cell:Value,2()')
		click('Filter1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [62684671, 20, 40118, 685, -1, -69.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', '59', 'Value,1')
		select('Filter.FieldRelationship_JTbl', 'cell:Value,2()')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , SALE-PRICE, true, <= (Text), 3.99], [, And, STORE-NO, true, Contains, 59], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:Value,2()')
		click('Filter1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', '16', 'Value,1')
		select('Filter.FieldRelationship_JTbl', 'cell:Value,2()')
		click('Filter1')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30]]')
	close()
