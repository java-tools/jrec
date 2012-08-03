useFixture(RecordEditor)

def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'csv2DTAR020_tst1.bin.csv')
		click(commonBits.fl('Edit') + '1')
		click('Filter1')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',0(null)')
		select('Filter.FieldRelationship_JTbl', 'STORE-NO', commonBits.fl('Field') + ',0')
		select('Filter.FieldRelationship_JTbl', '20', commonBits.fl('Value') + ',0')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',1(null)')
		select('Filter.FieldRelationship_JTbl', 'SALE-PRICE', commonBits.fl('Field') + ',1')
		select('Filter.FieldRelationship_JTbl', '<', commonBits.fl('Operator') + ',1')
		select('Filter.FieldRelationship_JTbl', '6', commonBits.fl('Value') + ',1')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',2()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, -1, -69.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',2(null)')
		select('Filter.FieldRelationship_JTbl', 'DEPT-NO', commonBits.fl('Field') + ',2')
		select('Filter.FieldRelationship_JTbl', '8', commonBits.fl('Value') + ',2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',3()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, -1, -69.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', '80', commonBits.fl('Value') + ',2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',3()')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',3()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',3()')
		rightclick('Filter.FieldRelationship_JTbl', 'And,3')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',3(null)')
		select('Filter.FieldRelationship_JTbl', 'STORE-NO', commonBits.fl('Field') + ',3')
		select('Filter.FieldRelationship_JTbl', '59', commonBits.fl('Value') + ',3')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',4()')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [Or, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',4()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [68634752, 59, 40118, 410, 1, 8.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',4(null)')
		select('Filter.FieldRelationship_JTbl', 'SALE-PRICE', commonBits.fl('Field') + ',4')
		select('Filter.FieldRelationship_JTbl', '<', commonBits.fl('Operator') + ',4')
		select('Filter.FieldRelationship_JTbl', '6', commonBits.fl('Value') + ',4')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',5()')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [Or, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, And, SALE-PRICE, true, <, 6], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',5()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99], [61684613, 59, 40118, 335, 1, 12.99], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [60614487, 59, 40118, 878, 1, 5.95]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',5(null)')
		select('Filter.FieldRelationship_JTbl', 'DEPT-NO', commonBits.fl('Field') + ',5')
		select('Filter.FieldRelationship_JTbl', '6', commonBits.fl('Value') + ',5')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',4(6)')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [Or, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 6], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',4(6)')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',4(6)')
		rightclick('Filter.FieldRelationship_JTbl', 'And,6')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',6(null)')
		select('Filter.FieldRelationship_JTbl', 'STORE-NO', commonBits.fl('Field') + ',6')
		select('Filter.FieldRelationship_JTbl', '166', commonBits.fl('Value') + ',6')
		select('Filter.FieldRelationship_JTbl', ' = ', commonBits.fl('Operator') + ',6')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Operator') + ',6( = )')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [Or, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 6], [Or, , STORE-NO, true,  = , 166], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Operator') + ',6( = )')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [68654655, 166, 40118, 60, 1, 5.08], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30], [68674560, 166, 40118, 170, 1, 5.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',7(null)')
		select('Filter.FieldRelationship_JTbl', 'SALE-PRICE', commonBits.fl('Field') + ',7')
		select('Filter.FieldRelationship_JTbl', '>=', commonBits.fl('Operator') + ',7')
		select('Filter.FieldRelationship_JTbl', '<', commonBits.fl('Operator') + ',7')
		select('Filter.FieldRelationship_JTbl', '5', commonBits.fl('Value') + ',7')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',8()')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [Or, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 6], [Or, , STORE-NO, true,  = , 166], [, And, SALE-PRICE, true, <, 5], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',8()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', '5.09', commonBits.fl('Value') + ',7')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',8()')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [Or, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 6], [Or, , STORE-NO, true,  = , 166], [, And, SALE-PRICE, true, <, 5.09], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',8()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [68654655, 166, 40118, 60, 1, 5.08], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',8(null)')
		select('Filter.FieldRelationship_JTbl', 'DEPT-NO', commonBits.fl('Field') + ',8')
		select('Filter.FieldRelationship_JTbl', '80', commonBits.fl('Value') + ',8')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',9()')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [Or, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 6], [Or, , STORE-NO, true,  = , 166], [, And, SALE-PRICE, true, <, 5.09], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',9()')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',9()')
		rightclick('Filter.FieldRelationship_JTbl', 'And,9')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',9(null)')
		select('Filter.FieldRelationship_JTbl', 'STORE-NO', commonBits.fl('Field') + ',9')
		select('Filter.FieldRelationship_JTbl', '166', commonBits.fl('Value') + ',9')
		select('Filter.FieldRelationship_JTbl', 'KEYCODE-NO', commonBits.fl('Field') + ',10')
		select('Filter.FieldRelationship_JTbl', '8', commonBits.fl('Value') + ',10')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',9(166)')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [Or, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 59], [, And, SALE-PRICE, true, <, 6], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 6], [Or, , STORE-NO, true,  = , 166], [, And, SALE-PRICE, true, <, 5.09], [, And, DEPT-NO, true, ' + commonBits.fl('Contains') + ', 80], [Or, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 166], [, And, KEYCODE-NO, true, ' + commonBits.fl('Contains') + ', 8], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ], [, And, , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',9(166)')
		commonBits.filter(click)
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [60694698, 59, 40118, 620, 1, 3.99], [60664659, 59, 40118, 620, 1, 3.99], [68654655, 166, 40118, 60, 1, 5.08], [69624033, 166, 40118, 80, 1, 18.19], [60604100, 166, 40118, 80, 1, 13.30], [68674560, 166, 40118, 170, 1, 5.99]]')
	close()
