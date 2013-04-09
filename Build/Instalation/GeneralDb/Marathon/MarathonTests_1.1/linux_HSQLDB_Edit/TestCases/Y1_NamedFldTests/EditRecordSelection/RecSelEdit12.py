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
		select('RecordList.Record Name_Txt', 'zx33xzFLDg88')

		select('RecordList.Description_Txt', '%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , f1, v1, , ], [, zx33xzFLD2, , , , , ], [, zx33xzFLD3, , , , , ]]')
#		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field Value,0')
		select_menu('Edit Record Selections')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 121]]')
		select('TabbedPane', 'zx33xzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21]]')
		select('TabbedPane', 'zx33xzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31], [Or, , fld 32, =, 32]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		select('TabbedPane', 'zx33xzFLD2')
		click('Insert1')
##		select('RecordSelectionJTbl1', '')
		rightclick('RecordSelectionJTbl1', 'and,1')
		select('RecordSelectionJTbl1', 'cell:Field,1()')
		select('RecordSelectionJTbl1', 'fld 21', 'Field,1')
		select('RecordSelectionJTbl1', '12', 'Field Value,1')
		select('RecordSelectionJTbl1', 'cell:Field Value,0(21)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('Save As')

		if window('Input'):
			select('OptionPane.textField', 'zx33xzFLDg89')
			click('OK')
		close()


		select('RecordList.Record Name_Txt', 'zx33xzFLDg89')

		select('RecordList.Description_Txt', '%%')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field Value,1')
		select_menu('Edit Child Record')
		click('BasicInternalFrameTitlePane$NoFocusButton2')

##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field Value,0')
		select_menu('Edit Record Selections')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 121]]')
		select('TabbedPane', 'zx33xzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12]]')
		select('TabbedPane', 'zx33xzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31], [Or, , fld 32, =, 32]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		select('TabbedPane', 'zx33xzFLD2')
		select('RecordSelectionJTbl1', 'cell:Field Value,1(12)')
		click('Insert1')
##		select('RecordSelectionJTbl1', '')
		rightclick('RecordSelectionJTbl1', 'and,2')
		select('RecordSelectionJTbl1', 'cell:Field,2()')
		select('RecordSelectionJTbl1', 'fld 21', 'Field,2')
		select('RecordSelectionJTbl1', '123', 'Field Value,2')
		select('RecordSelectionJTbl1', 'cell:Field Value,1(12)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12], [Or, , fld 21, =, 123]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , And  ,   ,   , fld 11, =, 11], [, ,   ,   , And  , fld 12, =, 12], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   , Or  ,   , fld 21, =, 123], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
