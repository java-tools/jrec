useFixture(default)

def test():
	java_recorded_version = '1.6.0_22'

	if window('Record Layout Definitions'):

		click('*')
		select('RecordList.Record Name_Txt', 'zxxxzFLDg543')

		select('RecordList.Description_Txt', '%')

		assert_p('ChildRecordsJTbl', 'Content', '[[, zxxxzFLD1, , , , , ], [, zxxxzFLD2, , ff, vv, , ], [, zxxxzFLD3, , 99, 99, , ]]')
##		select('ChildRecordsJTbl', '')
		rightclick('ChildRecordsJTbl', 'Field,0')
		select_menu('View Record Selections Tree')
##		select('TabbedPane1', 'Summary')
		assert_p('JTreeTable', 'Content', '[[, ,   ,   ,   , , =, ], [, , And  ,   ,   , fld 11, =, 12399], [, ,   , Or  ,   , fld 12, =, 32197], [, ,   , Or  ,   , fld 13, =, 1218765], [, ,   ,   ,   , ff, =, vv], [, , And  ,   ,   , fld 21, =, 219], [, ,   , Or  ,   , fld 22, =, 22], [, ,   ,   ,   , 99, =, 99], [, , And  ,   ,   , fld 31, =, 31]]')
		select('TabbedPane', 'zxxxzFLD3')
		assert_p('RecordSelectionJTbl2', 'Content', '[[, , fld 31, =, 31]]')
		select('TabbedPane', 'zxxxzFLD2')
		assert_p('RecordSelectionJTbl1', 'Content', '[[, , fld 21, =, 219], [Or, , fld 22, =, 22]]')
		select('TabbedPane', 'zxxxzFLD1')
		assert_p('RecordSelectionJTbl', 'Content', '[[, , fld 11, =, 12399], [Or, , fld 12, =, 32197], [Or, , fld 13, =, 1218765]]')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
		click('BasicInternalFrameTitlePane$NoFocusButton2')
	close()
