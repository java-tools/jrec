useFixture(default)
##
##  Depends on 31 & 38a running
##
def test():
	from Modules import commonBits
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):
		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg65')
		select('RecordList.Description_Txt', '%')

##		select('TabbedPane', 'Child Records')
		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , ff, vv1, , zxxxzFLD3], [, zxxxzFLD2, , , , , zxxxzFLD1], [, zxxxzFLD3, , gg, vv2, , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', commonBits.fl('Field Value') + ',0')
		select_menu(commonBits.fl('View Record Selections Tree'))
##		select('TabbedPane1', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , ff, =, vv1], [, , And  ,   ,   , fld 13, =, 1199], [, ,   , Or  ,   , fld 11, =, 1288], [, ,   , Or  ,   , fld 12, =, 1377], [, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 21, =, 21], [, ,   , Or  ,   , fld 21, =, 12], [, ,   ,   ,   , gg, =, vv2], [, , And  ,   ,   , fld 32, =, 31987]]')
		select('TabbedPane', 'zxxxzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 32, =, 31987]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 21], [Or, , fld 21, =, 12]]')
		select('TabbedPane', 'zxxxzFLD1')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 13, =, 1199], [Or, , fld 11, =, 1288], [Or, , fld 12, =, 1377]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
