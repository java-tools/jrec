useFixture(RecordEditor)
##
##  Testing Sort Tree   & Show2 / hide
##
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'DTAR020_tst1.bin')
		click(commonBits.fl('Edit') + '1')
		assert_p('LineList.Layouts_Txt', 'Text', 'DTAR020')
		click('Filter1')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Field') + ',0(null)')
		select('Fields.FieldRelationship_JTbl', 'STORE-NO', commonBits.fl('Field') + ',0')
		select('Fields.FieldRelationship_JTbl', '20', commonBits.fl('Value') + ',0')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',1()')
#		assert_p('Fields.FieldRelationship_JTbl', 'Content', '[[STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, true, ' + commonBits.fl('Contains') + ', ], [, true, ' + commonBits.fl('Contains') + ', ], [, true, ' + commonBits.fl('Contains') + ', ]]')
		assert_p('Fields.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, ' + commonBits.fl('Contains') + ', 20], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ], [, ' + commonBits.fl('And') + ', , true, ' + commonBits.fl('Contains') + ', ]]')
		select('Fields.FieldRelationship_JTbl', 'cell:' + commonBits.fl('Value') + ',1()')
		commonBits.filter(click)
		assert_p('LineList.Layouts_Txt', 'Text', 'DTAR020')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu(commonBits.fl('Window') + '>>DTAR020_tst1.bin>>' + commonBits.fl('Table:'))
		select('LineList.FileDisplay_JTbl', 'rows:[1,2,3,4,5,6,7],columns:[9 - 2|STORE-NO,11 - 4|DATE]')
		select_menu(commonBits.fl('View') + '>>' + commonBits.fl('Table View #{Selected Records#}'))
		assert_p('LineList.Layouts_Txt', 'Text', 'DTAR020')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99]]')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
