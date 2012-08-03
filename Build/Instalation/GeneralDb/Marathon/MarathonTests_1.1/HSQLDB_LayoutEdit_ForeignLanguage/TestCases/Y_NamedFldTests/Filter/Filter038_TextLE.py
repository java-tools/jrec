useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		select_menu(commonBits.fl('Edit') + '>>' + commonBits.fl('Update Csv Columns'))
		select('FieldChange_JTbl', commonBits.fl('Number'), commonBits.fl('Type') + ',5')
		select('FieldChange_JTbl', commonBits.fl('Number'), commonBits.fl('Type') + ',1')
		select('FieldChange_JTbl', 'cell:' + commonBits.fl('Type') + ',1(Number)')
		click(commonBits.fl('Apply'))
		click('Filter1')
##		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',0(null)')
		select('Filter.FieldRelationship_JTbl', 'SALE-PRICE', commonBits.fl('Field') + ',0')
		select('Filter.FieldRelationship_JTbl', commonBits.fl('<= (Text)'), commonBits.fl('Operator') + ',0')
		select('Filter.FieldRelationship_JTbl', '3.99', commonBits.fl('Value') + ',0')
##		select('Filter.FieldRelationship_JTbl', 'cell:Or,1()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',1(null)')
		select('Filter.FieldRelationship_JTbl', 'STORE-NO', commonBits.fl('Field') + ',1')
		select('Filter.FieldRelationship_JTbl', '20', commonBits.fl('Value') + ',1')
##		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , SALE-PRICE, true, ' + commonBits.fl('<= (Text)') + ', 3.99], [, And, STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [62684671, 20, 40118, 685, -1, -69.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', '59', commonBits.fl('Value') + ',1')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , SALE-PRICE, true, ' + commonBits.fl('<= (Text)') + ', 3.99], [, And, STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', '16', commonBits.fl('Value') + ',1')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30]]')
	close()
