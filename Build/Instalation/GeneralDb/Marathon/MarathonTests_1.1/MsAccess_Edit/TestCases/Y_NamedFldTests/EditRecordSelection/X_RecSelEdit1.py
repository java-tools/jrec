useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg1')
		select('RecordList.Description_Txt', '%')


##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,0')
		select_menu('Edit Record Selections')
		click('Insert')
		click('Insert')
		select('RecordSelectionJTbl', 'cell:Field,0()')
		select('RecordSelectionJTbl', 'fld 11', 'Field,0')
		select('RecordSelectionJTbl', '11', 'Field Value,0')
		select('RecordSelectionJTbl', 'fld 12', 'Field,1')
		select('RecordSelectionJTbl', '22', 'Field Value,1')
		select('RecordSelectionJTbl', 'cell:Field Value,1()')
		click('Insert')
		select('RecordSelectionJTbl', 'cell:Field,2()')
		select('RecordSelectionJTbl', 'fld 13', 'Field,2')
		select('RecordSelectionJTbl', '33', 'Field Value,2')
		select('RecordSelectionJTbl', 'cell:Field Value,2()')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 22], [, And, fld 13, =, 33]]')
		select('RecordSelectionJTbl', 'cell:Field Value,2()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,1')
		select_menu('Edit Child Record')
		assert_p('RecordFieldsJTbl', 'Content', '[[1, 1, fld 21, , 0, 0, 0, , , ], [2, 5, fld 22, , 0, 0, 0, , , ], [7, 9, fld 23, , 0, 0, 0, , , ], [18, 10, fld 24, , 0, 0, 0, , , ]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,1')
		select_menu('Edit Record Selections')
		click('Insert1')
		click('Insert1')
		select('RecordSelectionJTbl1', 'cell:Field,0()')
		select('RecordSelectionJTbl1', 'fld 21', 'Field,0')
		select('RecordSelectionJTbl1', 'fld 23', 'Field,1')
		select('RecordSelectionJTbl1', '>', 'Operator,1')
		select('RecordSelectionJTbl1', '!=', 'Operator,0')
		select('RecordSelectionJTbl1', '21', 'Field Value,0')
		select('RecordSelectionJTbl1', '22', 'Field Value,1')
		select('RecordSelectionJTbl1', 'cell:Field Value,1()')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, !=, 21], [, And, fld 23, >, 22]]')
		select('RecordSelectionJTbl1', 'cell:Field Value,0(21)')
		click('Insert1')
		select('RecordSelectionJTbl1', 'cell:Field,1()')
		select('RecordSelectionJTbl1', 'fld 24', 'Field,1')
		select('RecordSelectionJTbl1', '24', 'Field Value,1')
		select('RecordSelectionJTbl1', 'cell:Field Value,1()')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, !=, 21], [, And, fld 24, =, 24], [, And, fld 23, >, 22]]')
		select('RecordSelectionJTbl1', 'cell:Field Value,1()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'fff', 'Field,1')
		select('ChildRecordsJTbl', 'vvvv', 'Field Value,1')
		select('ChildRecordsJTbl', 'cell:rs,2(null)')
		click('Insert2')
		select('RecordSelectionJTbl2', 'cell:Field,0()')
		select('RecordSelectionJTbl2', 'fld 32', 'Field,0')
		select('RecordSelectionJTbl2', '32', 'Field Value,0')
		select('RecordSelectionJTbl2', 'cell:Field Value,0()')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 32, =, 32]]')
		select('RecordSelectionJTbl2', 'cell:Field Value,0()')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'cell:rs,2(null)')
		select('ChildRecordsJTbl', 'cell:rs,2(null)')
		rightclick('ChildRecordsJTbl', 'Child Name,1')
		select_menu('View Record Selections Tree')
		select('ChildRecordsJTbl', 'cell:rs,2(null)')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 22], [, ,   ,   , And  , fld 13, =, 33], [, ,   ,   ,   , fff, =, vvvv], [, , And  ,   ,   , fld 21, !=, 21], [, ,   ,   , And  , fld 24, =, 24], [, ,   ,   , And  , fld 23, >, 22], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 32, =, 32]]')

	close()
