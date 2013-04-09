useFixture(default)
##
##  Depends on 31 & 38a running
##
def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Editor'):
		select_menu('Record Layouts>>Edit Layout')
		select('RecordList.Record Name_Txt', 'zx33xzFLDg65')
		select('RecordList.Description_Txt', '%')

##		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zx33xzFLD1, , ff, vv1, , zx33xzFLD3], [, zx33xzFLD2, , , , , zx33xzFLD1], [, zx33xzFLD3, , gg, vv2, , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field Value,0')
		select_menu('View Record Selections Tree')
##		select('TabbedPane1', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , ff, =, vv1], [, , And  ,   ,   , fld 13, =, 1199], [, ,   , Or  ,   , fld 11, =, 1288], [, ,   , Or  ,   , fld 12, =, 1377], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , gg, =, vv2], [, , And  ,   ,   , fld 32, =, 31987]]')
		select('TabbedPane', 'zx33xzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 32, =, 31987]]')
		select('TabbedPane', 'zx33xzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12]]')
		select('TabbedPane', 'zx33xzFLD1')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 13, =, 1199], [Or, , fld 11, =, 1288], [Or, , fld 12, =, 1377]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
