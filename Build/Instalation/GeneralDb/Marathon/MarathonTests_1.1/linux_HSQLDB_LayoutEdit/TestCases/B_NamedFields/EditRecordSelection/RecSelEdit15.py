useFixture(default)
##
##  This is part of a sequence scripts
##        RecSel11 --> RecSel19
##  With  RecSel19 cleaning up
##
def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg88')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Child Records')
		select('RecordList.Description_Txt', '%')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Extras')
##		select('TabbedPane', 'Child Records')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,0')
		select_menu('Edit Record Selections')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [, And, fld 12, =, 12], [Or, , fld 11, =, 121]]')
		select('RecordSelectionJTbl', 'cell:Field Value,1(12)')
		click('Delete2')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 11], [Or, , fld 11, =, 121]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		select('TabbedPane', 'zxxxzFLD2')
		select('RecordSelectionJTbl1', 'cell:Field Value,1(12)')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12]]')
		select('RecordSelectionJTbl1', 'cell:Field Value,0(21)')
		click('Delete3')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 12]]')
		select('TabbedPane', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , f1, =, v1], [, , And  ,   ,   , fld 11, =, 11], [, ,   , Or  ,   , fld 11, =, 121], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 12], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 31, =, 31], [, ,   , Or  ,   , fld 32, =, 32]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
