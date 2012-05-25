useFixture(default)
##
##  Testing Sort Tree   & Show2 / hide
##
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select('File_Txt', commonBits.sampleDir() + 'DTAR020_tst1.bin')
		click('Edit1')
		assert_p('LineList.Layouts_Txt', 'Text', 'DTAR020')
		click('Filter1')
		select('Filter.FieldRelationship_JTbl', 'cell:Field,0(null)')
		select('Filter.FieldRelationship_JTbl', 'STORE-NO', 'Field,0')
		select('Filter.FieldRelationship_JTbl', '20', 'Value,0')
		select('Filter.FieldRelationship_JTbl', 'cell:Value,1()')
#		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[STORE-NO, true, Contains, 20], [, true, Contains, ], [, true, Contains, ], [, true, Contains, ]]')
		assert_p('Filter.FieldRelationship_JTbl', 'Content', '[[, , STORE-NO, true, Contains, 20], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ], [, And, , true, Contains, ]]')
		select('Filter.FieldRelationship_JTbl', 'cell:Value,1()')
		click('Filter1')
		assert_p('LineList.Layouts_Txt', 'Text', 'DTAR020')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[63604808, 20, 40118, 170, 1, 4.87], [69684558, 20, 40118, 280, 1, 19.00], [69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select_menu('Window>>DTAR020_tst1.bin>>Table: ')
		select('LineList.FileDisplay_JTbl', 'rows:[1,2,3,4,5,6,7],columns:[9 - 2|STORE-NO,11 - 4|DATE]')
		select_menu('View>>Table View #{Selected Records#}')
		assert_p('LineList.Layouts_Txt', 'Text', 'DTAR020')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99]]')
		assert_p('LineList.FileDisplay_JTbl', 'Content', '[[69684558, 20, 40118, 280, -1, -19.00], [69694158, 20, 40118, 280, 1, 5.01], [62684671, 20, 40118, 685, 1, 69.99], [62684671, 20, 40118, 685, -1, -69.99], [61664713, 59, 40118, 335, 1, 17.99], [61664713, 59, 40118, 335, -1, -17.99]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
