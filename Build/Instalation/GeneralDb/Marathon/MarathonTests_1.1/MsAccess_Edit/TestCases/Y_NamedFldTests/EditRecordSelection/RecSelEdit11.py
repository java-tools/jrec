useFixture(default)
##
##  This is part of a sequence scripts
##        RecSel11 --> RecSel19
##  With  RecSel19 cleaning up
##
def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg1')

		select('RecordList.Description_Txt', '%')

		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zx33xzFLDg88')
			click('OK')
		close()


		select('RecordList.Record Name_Txt', 'zx33xzFLDg88')

		select('RecordList.Description_Txt', '%%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , , , , ], [, zx33xzFLD2, , , , , ], [, zx33xzFLD3, , , , , ]]')
		select('ChildRecordsJTbl', 'cell:rs,0(null)')
		click('Insert')
		click('Insert')
		select('RecordSelectionJTbl', 'cell:Field,0()')
		select('RecordSelectionJTbl', 'fld 11', 'Field,0')
		select('RecordSelectionJTbl', 'fld 12', 'Field,1')
		select('RecordSelectionJTbl', '11', 'Field Value,0')
		select('RecordSelectionJTbl', '12', 'Field Value,1')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   ,   ,   , , =, ], [, ,   ,   ,   , , =, ]]')
		select('TabbedPane', 'zx33xzFLD1')
		select('RecordSelectionJTbl', 'cell:Field Value,1(12)')
		click('Insert')
##		select('RecordSelectionJTbl', '')
		rightclick('RecordSelectionJTbl', 'and,2')
		select('RecordSelectionJTbl', 'cell:Field,2()')
		select('RecordSelectionJTbl', 'fld 11', 'Field,2')
		select('RecordSelectionJTbl', '121', 'Field Value,2')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, ,   ,   ,   , , =, ]]')
		select('TabbedPane', 'zx33xzFLD2')
		click('Insert1')
		select('RecordSelectionJTbl1', 'cell:Field,0()')
		select('RecordSelectionJTbl1', 'fld 21', 'Field,0')
		select('RecordSelectionJTbl1', '21', 'Field Value,0')
		select('RecordSelectionJTbl1', 'cell:Field,0(fld 21)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21]]')
		select('TabbedPane', 'zx33xzFLD1')
		select('RecordSelectionJTbl', 'cell:Field Value,1(12)')
		select('RecordSelectionJTbl', 'cell:Field Value,1(12)')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 121]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   ,   , , =, ]]')
		select('TabbedPane', 'zx33xzFLD3')
		click('Insert2')
		click('Insert2')
		select('RecordSelectionJTbl2', 'cell:Field,0()')
		select('RecordSelectionJTbl2', 'fld 31', 'Field,0')
		select('RecordSelectionJTbl2', '31', 'Field Value,0')
		select('RecordSelectionJTbl2', 'fld 32', 'Field,1')
		select('RecordSelectionJTbl2', '32', 'Field Value,1')
		select('RecordSelectionJTbl2', 'cell:Field Value,1()')
		rightclick('RecordSelectionJTbl2', 'and,1')
		select('RecordSelectionJTbl2', 'cell:Field Value,0(31)')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31], [Or, , fld 32, =, 32]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		select('ChildRecordsJTbl', 'f1', 'Field,0')
		select('ChildRecordsJTbl', 'v1', 'Field Value,0')
		select('ChildRecordsJTbl', 'cell:Field Value,1()')
		rightclick('ChildRecordsJTbl', 'Field,1')
		select_menu('View Record Selections Tree')
##		select('TabbedPane1', 'Summary')
		select('ChildRecordsJTbl', 'cell:Field Value,1()')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
