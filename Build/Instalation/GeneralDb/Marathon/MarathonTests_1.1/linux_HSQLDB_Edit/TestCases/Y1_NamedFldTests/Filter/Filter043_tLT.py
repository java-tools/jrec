useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
##		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',0(null)')
		select('Fields.FieldRelationship_JTbl', 'SALE-PRICE', commonBits.fl('Field') + ',0')
		select('Fields.FieldRelationship_JTbl', '<', commonBits.fl('Operator') + ',0')
		select('Fields.FieldRelationship_JTbl', '4', commonBits.fl('Value') + ',0')
##		select('Fields.FieldRelationship_JTbl', 'cell:Or,1()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',1(null)')
		select('Fields.FieldRelationship_JTbl', 'STORE-NO', commonBits.fl('Field') + ',1')
		select('Fields.FieldRelationship_JTbl', '20', commonBits.fl('Value') + ',1')
##		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		
		assert_p('Fields.FieldRelationship_JTbl', 'Content', '[[, , SALE-PRICE, true, <, 4], [, ' + commonBits.fl('And') + ', STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ]]')
		
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [62684671, 20, 40118, 685, -1, -69.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Fields.FieldRelationship_JTbl', '59', commonBits.fl('Value') + ',1')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		assert_p('Fields.FieldRelationship_JTbl', 'Content', '[[, , SALE-PRICE, true, <, 4], [, ' + commonBits.fl('And') + ', STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99]]')

		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Fields.FieldRelationship_JTbl', '16', commonBits.fl('Value') + ',1')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30]]')
	close()
